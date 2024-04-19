package nl.deltares.portal.utils.impl;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import nl.deltares.portal.configuration.WebinarSiteConfiguration;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.HttpClientUtils;
import nl.deltares.portal.utils.WebinarUtils;
import nl.deltares.portal.utils.WebinarUtilsFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        service = WebinarUtilsFactory.class
)
public class WebinarUtilsFactoryImpl implements WebinarUtilsFactory {

    public WebinarUtils newInstance(Registration registration) throws Exception{
        if (!isWebinarSupported(registration)){
            throw new UnsupportedOperationException("unsupported registration type " + registration.getClass().getSimpleName());
        }
        String webinarProvider = ((SessionRegistration) registration).getWebinarProvider();
        return newInstance(registration.getGroupId(), webinarProvider.toLowerCase());
    }

    public WebinarUtils newInstance(long groupId, String  webinarProvider) throws Exception{

        Class<? extends HttpClientUtils> webinarClass = getWebinarClass(webinarProvider);
        WebinarUtils webinarUtils;
        switch (webinarProvider){
            case "goto":
                webinarUtils = new GotoUtils(getSiteConfiguration(groupId));
                break;
            case "anewspring" :
                webinarUtils = new ANewSpringUtils(getSiteConfiguration(groupId));
                break;
            case "msteams" :
                webinarUtils = new MSTeamsUtils();
                break;
            default:
                throw new UnsupportedOperationException("unsupported provider " + webinarClass.getSimpleName());
        }
        return webinarUtils;
    }

    private Class<? extends HttpClientUtils> getWebinarClass(String webinarProvider) {
        switch (webinarProvider){
            case "goto":
                return GotoUtils.class;
            case "anewspring":
                return ANewSpringUtils.class;
            case "msteams":
                return MSTeamsUtils.class;
            default:
                throw new UnsupportedOperationException("webinar class unsupported: " + webinarProvider);
        }
    }

    public boolean isWebinarSupported(Registration registration){

        if (registration instanceof SessionRegistration){
            String webinarProvider = ((SessionRegistration) registration).getWebinarProvider();
            try {
                //noinspection ResultOfMethodCallIgnored
                getWebinarClass(webinarProvider.toLowerCase());
                return true;
            } catch (Exception e) {
                //
            }
        }
        return false;
    }

    private WebinarSiteConfiguration getSiteConfiguration(long siteId) {
        WebinarSiteConfiguration groupConfiguration = null;
        try {
            groupConfiguration = _configurationProvider.getGroupConfiguration(WebinarSiteConfiguration.class, siteId);
            if (groupConfiguration == null){
                return _configurationProvider.getSystemConfiguration(WebinarSiteConfiguration.class);
            }
        } catch (ConfigurationException e) {
            LOG.error("Error retrieving configuration: " + e.getMessage());
        }
        return groupConfiguration;
    }

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(WebinarUtilsFactoryImpl.class);
}
