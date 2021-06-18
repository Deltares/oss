package nl.deltares.portal.events;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {

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

			try {
				final Map<String, String> userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
				final ExpandoBridge expandoBridge = user.getExpandoBridge();
				for (KeycloakUtils.ATTRIBUTES attribute : KeycloakUtils.ATTRIBUTES.values()) {
					final String value = userAttributes.get(attribute.name());
					if (expandoBridge.hasAttribute(attribute.name())) {
						expandoBridge.setAttribute(attribute.name(), value, false);
					} else {
						if (value == null) continue;
						expandoBridge.addAttribute(attribute.name(), false);
						expandoBridge.setAttribute(attribute.name(), value, false);
					}
				}
			} catch (Exception e){
				LOG.warn(String.format("Error updating address for user %s: %s", user.getEmailAddress(), e.getMessage()), e);
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