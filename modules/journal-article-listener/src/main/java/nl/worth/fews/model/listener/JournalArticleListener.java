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
import nl.deltares.portal.utils.DsdValidationUtils;
import nl.deltares.portal.utils.XmlContentParserUtils;
import nl.worth.fews.configuration.JournalArticleManagementConfiguration;
import nl.worth.fews.constants.JournalArticleManagementConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.w3c.dom.Document;

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
    public void onBeforeCreate(JournalArticle model) throws ModelListenerException {

        long groupId = model.getGroupId();

        if (StringUtil.contains(journalArticleManagementConfiguration.configuredGroupIds(),
                String.valueOf(groupId)) || isDSDSite(groupId)) {

            String structureKey = model.getDDMStructureKey();

            if (structureKey.matches("([A-Z])+-(\\d+\\.)(\\d+\\.)(\\d+)")) {
                structureKey = structureKey.substring(0, 1).toUpperCase()
                        + structureKey.substring(1, structureKey.lastIndexOf("-")).toLowerCase();
            }

            Document parsedContent = null; //cache content for re-use
            if (isDSDSession(structureKey)) {
                try {
                    parsedContent = XmlContentParserUtils.parseContent(model);
                    DsdValidationUtils.validateSessionCapacityMatchesRoomCapacity(model, parsedContent);
                } catch (PortalException e) {
                    LOG.error("Error validating session capacity: " + e.getMessage());
                    throw new ModelListenerException(e.getMessage());
                }
            }

            if (structure_folderJsonMap.has(structureKey)) {

                String folderName = Validator.isNotNull(structure_folderJsonMap.getString(structureKey))
                        ? structure_folderJsonMap.getString(structureKey) : structureKey;

                if (isDSDSite(groupId) && storeInParentSite(model, parsedContent)) {
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

    private boolean storeInParentSite(JournalArticle article, Document parsedContent) throws ModelListenerException{
        long groupId = article.getGroupId();
        try {
            Group site = _groupLocalService.getGroup(groupId);
            Group parentGroup = site.getParentGroup();
            if (parentGroup == null) return false;

            if (parsedContent == null) {
                parsedContent = XmlContentParserUtils.parseContent(article);
            }
            Object storeInParentSite = XmlContentParserUtils.getNodeValue(parsedContent, "storeInParentSite", true);
            return storeInParentSite != null && (Boolean) storeInParentSite;
        } catch (PortalException e) {
            LOGGER.error("Could not find site [" + groupId + "]", e);
        }
        return false;
    }

    private boolean isDSDSession(String structureKey)  {
        if (Validator.isNotNull(journalArticleManagementConfiguration.dsdSessionStructureKey())){
            return journalArticleManagementConfiguration.dsdSessionStructureKey().equals(structureKey);
        }
        LOGGER.error("Session structure key value not configured!");
        return false;
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

    private static final Log LOG = LogFactoryUtil.getLog(JournalArticleListener.class);
}