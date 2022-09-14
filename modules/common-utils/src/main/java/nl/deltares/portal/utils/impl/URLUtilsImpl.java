package nl.deltares.portal.utils.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.configuration.DownloadSiteConfiguration;
import nl.deltares.portal.utils.URLUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

                if (themeDisplay != null && group != null) {
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


    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(URLUtilsImpl.class);
}
