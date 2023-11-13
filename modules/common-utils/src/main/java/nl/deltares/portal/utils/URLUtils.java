package nl.deltares.portal.utils;

import com.liferay.portal.kernel.theme.ThemeDisplay;

public interface URLUtils {
    String getShoppingCartURL(ThemeDisplay themeDisplay);
    String getDownloadCartURL(ThemeDisplay themeDisplay);
    String setUrlParameter(String url, String namespace, String paramKey, String paramValue);
}
