package nl.deltares.portal.utils.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.utils.URLUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component(
        immediate = true,
        service = URLUtils.class
)
public class URLUtilsImpl implements URLUtils {

    @Override
    public String getShoppingCartURL(ThemeDisplay themeDisplay) {
        String url = StringPool.BLANK;
        long groupId = themeDisplay.getScopeGroupId();
        if (_configurationProvider != null) {
            try {
                DSDSiteConfiguration urlsConfiguration = _configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, groupId);

                Group group = GroupLocalServiceUtil.getGroup(groupId);
                if (group != null) {
                    Layout registrationPage = LayoutLocalServiceUtil
                            .fetchLayoutByFriendlyURL(groupId, false, urlsConfiguration.registrationURL());
                    if (registrationPage != null) {
                        url = group.getDisplayURL(themeDisplay, false) + registrationPage.getFriendlyURL(themeDisplay.getLocale());
                    }
                }
            } catch (Exception e) {
                LOG.debug("Error creating portlet url", e);
            }
        }
        return url;
    }

    @Override
    public String getDownloadCartURL(ThemeDisplay themeDisplay) {
        String url = "";
        long groupId = themeDisplay.getScopeGroupId();
        if (_configurationProvider != null) {
            try {
                DownloadSiteConfiguration urlsConfiguration = _configurationProvider
                        .getGroupConfiguration(DownloadSiteConfiguration.class, groupId);

                Group group = GroupLocalServiceUtil.getGroup(groupId);

                if (group != null) {
                    Layout downloadPage = LayoutLocalServiceUtil
                            .fetchLayoutByFriendlyURL(groupId, false, urlsConfiguration.downloadURL());
                    if (downloadPage != null) {
                        url = group.getDisplayURL(themeDisplay, false) + downloadPage.getFriendlyURL(themeDisplay.getLocale());
                    }
                }
            } catch (Exception e) {
                LOG.debug("Error creating portlet url", e);
            }
        }
        return url;
    }

    @Override
    public String setUrlParameter(String url, String namespace, String key, String value) {
        final String[] urlParts = url.split("\\?");
        StringBuilder newUrl = new StringBuilder(urlParts[0]);
        newUrl.append('?');
        final String[] queryParts;
        if (urlParts.length == 1) {
            queryParts = new String[0];
        } else {
            queryParts = urlParts[1].split("&");
        }

        String namespaceKey = namespace + key;
        for (String queryPart : queryParts) {
            if (queryPart.startsWith(namespaceKey)) continue;
            newUrl.append(queryPart);
            newUrl.append('&');
        }
        newUrl.append(namespaceKey).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));

        return newUrl.toString();
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(URLUtilsImpl.class);
}
