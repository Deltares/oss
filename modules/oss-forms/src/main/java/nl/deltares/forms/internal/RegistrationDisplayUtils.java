package nl.deltares.forms.internal;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

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

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationDisplayUtils.class);
}
