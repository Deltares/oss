package nl.deltares.portal.events;

import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.DownloadUtils;
import nl.deltares.portal.utils.GeoIpUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.SanctionCheckUtils;
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
		property = { "key=login.events.post" },
		service = LifecycleAction.class
)

public class PostLoginAction implements LifecycleAction {

	private static final Log LOG = LogFactoryUtil.getLog(
			PostLoginAction.class);

	@Reference
	private KeycloakUtils keycloakUtils;

	@Reference
	private UserLocalService userLocalService;

	@Reference
	private DownloadUtils downloadUtils;

	@Reference
	protected SanctionCheckUtils sanctionCheckUtils;

	private GeoIpUtils geoIpUtils;
	@Reference(
			unbind = "-",
			cardinality = ReferenceCardinality.AT_LEAST_ONE
	)
	protected void setGeoIpUtils(GeoIpUtils geoIpUtils) {

		//todo: add check for preferred instance
		if (geoIpUtils.isActive()){
			this.geoIpUtils = geoIpUtils;
		}
	}

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)  {

		HttpServletRequest request = lifecycleEvent.getRequest();
		if (request == null) return;

		Principal userPrincipal = request.getUserPrincipal();
		if (userPrincipal == null) return;

		User user = userLocalService.fetchUserById(Long.parseLong(userPrincipal.getName()));
		if (user == null){
			LOG.warn("Could not fine user for principal " + userPrincipal.getName());
			return;
		}

		if (keycloakUtils.isActive()){

			try {

				byte[] bytes = keycloakUtils.getUserAvatar(user.getEmailAddress());
				if (bytes != null && bytes.length > 0) {
					LOG.info("Updating avatar for user " + user.getFullName());
					userLocalService.updatePortrait(user.getUserId(), bytes);
				} else {
					LOG.info("Deleting avatar for user " + user.getFullName());
					userLocalService.deletePortrait(user.getUserId());
				}
			} catch (Exception e) {
				LOG.warn(String.format("Error updating portrait %d for user %s", user.getPortraitId(), user.getScreenName()), e);
			}

			try {
				//this value should contain the origin of the login request
				String siteId = getSiteId(request.getParameter("redirect"));
				if (siteId != null) {
					LOG.info(String.format("Register user '%s' login to site '%s'", user.getFullName(), siteId));
					keycloakUtils.registerUserLogin(user.getEmailAddress(), siteId);
				}
			} catch (Exception e) {
				LOG.warn(String.format("Error registering user %s to site: %s", user.getEmailAddress(), e.getMessage()), e);
			}

		}

		if (downloadUtils.isActive()){
			final LayoutSet layoutSet = (LayoutSet) request.getAttribute(WebKeys.VIRTUAL_HOST_LAYOUT_SET);
			//Check if users has any pending shares that need to be updated
			downloadUtils.updatePendingShares(user, layoutSet.getGroupId());
			downloadUtils.updateProcessingShares(user, layoutSet.getGroupId());
		}

		if (geoIpUtils != null){
			final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(request.getRemoteAddr());
			final String countryName = geoIpUtils.getCountryName(clientIpInfo);
			if (sanctionCheckUtils.isSanctioned(geoIpUtils.getCountryIso2Code(clientIpInfo))){
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
			if (path != null && path.contains("web/")){
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