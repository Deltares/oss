package nl.deltares.portal.utils;

import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletRequest;
import java.util.Map;

public interface URLUtils {
    String getUnregisterURL(PortletRequest portletRequest, String articleId, Long userId, String redirect) throws Exception;
    String getShoppingCartURL(ThemeDisplay themeDisplay);
    String getDownloadCartURL(ThemeDisplay themeDisplay);
    String setUrlParameter(String url, String namespace, String paramKey, String paramValue);

    String getRegistrationFormSuccessUrl(PortletRequest portletRequest, String action, String redirect) throws Exception;
    String getRegistrationFormFailUrl(PortletRequest portletRequest, String action, String redirect) throws Exception;
    Map<String, String> parseQueryParameters(String queryString);
}
