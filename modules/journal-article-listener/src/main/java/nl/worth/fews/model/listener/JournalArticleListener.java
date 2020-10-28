package nl.worth.fews.model.listener;


import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.worth.fews.configuration.JournalArticleManagementConfiguration;
import nl.worth.fews.constants.JournalArticleManagementConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
        immediate = true,
        configurationPid = JournalArticleManagementConstants.JOURNAL_ARTICLE_MANAGEMENT_CONFIGURATION_PID,
        service = ModelListener.class
)
public class JournalArticleListener extends BaseModelListener<JournalArticle> {

    private static final Log LOGGER = LogFactoryUtil.getLog(JournalArticleListener.class);

    @Override
    public void onBeforeRemove(JournalArticle model) throws ModelListenerException {

        //When removing an article clean up records in registrations table.
        if (DsdParserUtils.isDsdArticle(model)) {
            try {
                AbsDsdArticle instance = dsdParserUtils.toDsdArticle(model);
                if (instance instanceof Registration) {
                    dsdSessionUtils.deleteRegistrationsFor((Registration)instance);
                }
            } catch (PortalException e) {
                LOG.warn("Error removing journal article: " + e.getMessage());
            }
        }
    }

    @Override
    public void onBeforeCreate(JournalArticle model) throws ModelListenerException {

        long groupId = model.getGroupId();

        if (StringUtil.contains(journalArticleManagementConfiguration.configuredGroupIds(),
                String.valueOf(groupId)) || isDSDSite(groupId)) {

            AbsDsdArticle dsdArticle;
            try {
                dsdArticle = dsdParserUtils.toDsdArticle(model);
            } catch (PortalException e) {
                String msg = String.format("Error parsing DSD Article %s: %s", model.getTitle(), e.getMessage());
                LOG.error(msg);
                throw new ModelListenerException(msg);
            }

            try {
                dsdArticle.validate();
            } catch (PortalException e) {
                String msg = String.format("Validation error for %s: %s", model.getTitle(), e.getMessage());
                LOG.error(msg);
                throw new ModelListenerException(msg);
            }

            String structureKey = dsdArticle.getStructureKey();
            if (structure_folderJsonMap.has(structureKey)) {

                String folderName = Validator.isNotNull(structure_folderJsonMap.getString(structureKey))
                        ? structure_folderJsonMap.getString(structureKey) : structureKey;

                if (dsdArticle.storeInParentSite()) {
                    groupId = journalArticleManagementConfiguration.dsdParentSiteID();
                    model.setGroupId(groupId);

                    try {
                        JournalArticleResource articleResource = model.getArticleResource();
                        articleResource.setGroupId(groupId);
                        JournalArticleResourceLocalServiceUtil.updateJournalArticleResource(articleResource);
                    } catch (PortalException e) {
                        LOG.debug("Could not update article resource for [" + model + "]");
                    }
                }

                JournalFolder journalFolder = fetchOrCreateJournalFolder(model.getUserId(), groupId, folderName);

                model.setFolderId(journalFolder.getFolderId());
                model.setTreePath(journalFolder.getTreePath());
            }
        }
    }

    private boolean isDSDSite(long groupId) {
        boolean isDSDSite = false;

        try {
            Group site = _groupLocalService.getGroup(groupId);
            if (Validator.isNotNull(journalArticleManagementConfiguration.dsdParentSiteID())) {
                isDSDSite = (site.getParentGroupId() == journalArticleManagementConfiguration.dsdParentSiteID() ||
                        groupId == journalArticleManagementConfiguration.dsdParentSiteID());
            }
        } catch (PortalException e) {
            LOGGER.error("Could not find site [" + groupId + "]", e);
        }

        return isDSDSite;
    }

    private JournalFolder fetchOrCreateJournalFolder(long userId, long groupId, String name) {

        JournalFolder journalFolder = _journalFolderLocalService.fetchFolder(groupId, name);

        if (Validator.isNull(journalFolder)) {
            try {
                ServiceContext serviceContext = new ServiceContext();
                journalFolder = _journalFolderLocalService
                        .addFolder(userId, groupId, 0, name, "",
                                serviceContext);
            } catch (PortalException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }

        return journalFolder;
    }

    @Activate
    @Modified
    public void activate(Map<Object, Object> properties) {
        journalArticleManagementConfiguration = ConfigurableUtil
                .createConfigurable(JournalArticleManagementConfiguration.class, properties);

        if (Validator.isNotNull(journalArticleManagementConfiguration.structureKeyToFolderJsonMap())) {
            try {
                structure_folderJsonMap = JSONFactoryUtil
                        .createJSONObject(journalArticleManagementConfiguration.structureKeyToFolderJsonMap());
            } catch (JSONException e) {

                structure_folderJsonMap = JSONFactoryUtil.createJSONObject();
                LOGGER.error("Error when parsing journalArticleManagementConfiguration ");
            }
        } else {
            structure_folderJsonMap = JSONFactoryUtil.createJSONObject();
        }
    }

    private volatile JournalArticleManagementConfiguration journalArticleManagementConfiguration;

    private JSONObject structure_folderJsonMap;

    @Reference
    private JournalFolderLocalService _journalFolderLocalService;

    @Reference
    private GroupLocalService _groupLocalService;

    @Reference
    private DsdSessionUtils dsdSessionUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    private static final Log LOG = LogFactoryUtil.getLog(JournalArticleListener.class);
}