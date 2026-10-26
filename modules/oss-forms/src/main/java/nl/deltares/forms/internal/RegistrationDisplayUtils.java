package nl.deltares.forms.internal;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.util.HashMap;
import java.util.Map;

public class RegistrationDisplayUtils {

    public static JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                          String ddmTemplateKey, String articleId, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    themeDisplay.getScopeGroupId(), articleId, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    articleId, ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    public static String[] getStructureKeys(DSDSiteConfiguration configuration) {
        if (configuration == null) return new String[0];
        String structureList = configuration.dsdRegistrationStructures();
        if (structureList != null && !structureList.isEmpty()){
            return StringUtil.split(structureList, ' ');
        }
        return new String[0];
    }

    public static Map<String, String> getTypeTranslations(String dsdRegistrationTypeField,
                                                          String[] structureKeys, DsdJournalArticleUtils _dsdJournalArticleUtils,
                                                          ThemeDisplay themeDisplay) throws PortalException {

        Map<String, String> typeTranslations = new HashMap<>();
        for (String structureKey : structureKeys) {
            typeTranslations.putAll(_dsdJournalArticleUtils.getStructureFieldOptions(themeDisplay.getSiteGroupId(),
                    structureKey, dsdRegistrationTypeField, themeDisplay.getLocale()));
        }
        return typeTranslations;
    }
    private static final Log LOG = LogFactoryUtil.getLog(RegistrationDisplayUtils.class);
}
