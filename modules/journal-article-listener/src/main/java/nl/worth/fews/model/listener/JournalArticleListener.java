package nl.worth.fews.model.listener;


import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
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
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import java.util.Map;
import nl.worth.fews.configuration.JournalArticleManagementConfiguration;
import nl.worth.fews.constants.JournalArticleManagementConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;


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
        String.valueOf(groupId))) {

      String structureKey = model.getDDMStructureKey();

      if (structure_folderJsonMap.has(structureKey)) {

        String folderName = Validator.isNotNull(structure_folderJsonMap.getString(structureKey))
            ? structure_folderJsonMap.getString(structureKey) : structureKey;

        JournalFolder journalFolder = fetchOrCreateJournalFolder(model.getUserId(), groupId,
            folderName);

        model.setFolderId(journalFolder.getFolderId());
      }
    }
  }

  private JournalFolder fetchOrCreateJournalFolder(long userId, long groupId, String name) {

    JournalFolder journalFolder = _journalFolderLocalService.fetchFolder(groupId, name);

    if (Validator.isNull(journalFolder)) {
      try {
        ServiceContext serviceContext = new ServiceContext();
        journalFolder = _journalFolderLocalService
            .addFolder(userId, groupId, 0, name, StringPool.BLANK,
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
}