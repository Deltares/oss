package nl.deltares.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.ArrayUtil;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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
				LayoutSet layoutSet = (LayoutSet) request.getAttribute("VIRTUAL_HOST_LAYOUT_SET");
				String hostname = layoutSet.getCompanyFallbackVirtualHostname();
				LastPath last_path = (LastPath) request.getSession().getAttribute("LAST_PATH");
				String siteId = getSiteId(last_path.getPath(), hostname);
				if (siteId != null) {
					LOG.info(String.format("Register user '%s' login to site '%s'", user.getFullName(), siteId));
					keycloakUtils.registerUserLogin(user.getEmailAddress(), siteId);
				}
			} catch (Exception e) {
				LOG.warn(String.format("Error registering user %s to site: %s", user.getEmailAddress(), e.getMessage()), e);
			}

		}

	}

	private String getSiteId(String path, String hostname) {
		if (path == null) return hostname;
		String[] pathItems = path.split("/");
		String[] pathItemsWithValues = ArrayUtil.remove(pathItems, "");
		if (pathItemsWithValues.length == 0) return hostname;
		if (hostname != null && hostname.length() > 0) return hostname.concat(".").concat(pathItemsWithValues[0]);
		return pathItemsWithValues[0];
	}

}