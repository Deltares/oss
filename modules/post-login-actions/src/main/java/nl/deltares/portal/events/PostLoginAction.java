package nl.deltares.portal.events;

import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.PostLoginUpdateDownloadStatus;
import nl.deltares.tasks.impl.PostLoginUpdateUserInfo;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Map;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {"key=login.events.post"},
        service = LifecycleAction.class
)

public class PostLoginAction implements LifecycleAction {

    private static final Log LOG = LogFactoryUtil.getLog(
            PostLoginAction.class);

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private DownloadUtils downloadUtils;

    @Reference
    private UserLocalService userLocalService;

    @Reference
    protected SanctionCheckUtils sanctionCheckUtils;

    private GeoIpUtils geoIpUtils;

    @Reference(
            unbind = "-",
            cardinality = ReferenceCardinality.AT_LEAST_ONE
    )
    protected void setGeoIpUtils(GeoIpUtils geoIpUtils) {

        //todo: add check for preferred instance
        if (geoIpUtils.isActive()) {
            this.geoIpUtils = geoIpUtils;
        }
    }

    @Override
    public void processLifecycleEvent(LifecycleEvent lifecycleEvent) {

        HttpServletRequest request = lifecycleEvent.getRequest();
        if (request == null) return;

        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) return;

        User user = userLocalService.fetchUserById(Long.parseLong(userPrincipal.getName()));
        if (user == null) {
            LOG.warn("Could not fine user for principal " + userPrincipal.getName());
            return;
        }

        if (keycloakUtils.isActive()) {
            //this value should contain the origin of the login request
            final String id = String.format("PostLoginUpdateUserInfo_%d_%d", user.getCompanyId(), user.getUserId());
            String siteId = getSiteId(request.getParameter("redirect"));
            LOG.info(String.format("Starting post login activity '%s'", id));
            try {
                final PostLoginUpdateUserInfo postLoginRequest = new PostLoginUpdateUserInfo(id, user, siteId, keycloakUtils);
                DataRequestManager.getInstance().addToQueue(postLoginRequest);
            } catch (Exception e) {
                LOG.warn(String.format("Error executing PostLoginUpdateUserInfo request %s", id), e);
            }

        }

        if (geoIpUtils != null) {
            final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(request.getRemoteAddr());
            final String countryName = geoIpUtils.getCountryName(clientIpInfo);
            if (sanctionCheckUtils.isSanctioned(geoIpUtils.getCountryIso2Code(clientIpInfo))) {
                request.getSession().setAttribute("LIFERAY_SHARED_isSanctioned", true);
                request.getSession().setAttribute("LIFERAY_SHARED_sanctionCountry", countryName);
                LOG.info(String.format("User '%s' logged in from sanctioned country '%s'", user.getFullName(), countryName));
            } else {
                LOG.info(String.format("User '%s' logged in from not-sanctioned country '%s'", user.getFullName(), countryName));
            }
        }
    }

    private String getSiteId(String redirectUrl) {
        try {
            URL url = new URL(redirectUrl);
            String host = url.getHost();
            String path = url.getPath();
            if (path != null && path.contains("web/")) {
                String[] pathElements = path.split("/");
                StringBuilder sitePath = new StringBuilder(host);
                boolean stopAtNext = false;
                for (String pathElement : pathElements) {
                    if (pathElement.equals("web")) {
                        sitePath.append('/');
                        sitePath.append(pathElement);
                        stopAtNext = true;
                    } else if (stopAtNext) {
                        sitePath.append('/');
                        sitePath.append(pathElement);
                        break;
                    }
                }
                return sitePath.toString();
            } else {
                return host;
            }
        } catch (MalformedURLException e) {
            return null;
        }

    }

}