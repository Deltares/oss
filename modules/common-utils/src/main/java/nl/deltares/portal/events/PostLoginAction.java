package nl.deltares.portal.events;

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

		try {
			if (keycloakUtils.isActive()){
				LOG.info("Updating avatar for user " + user.getFullName());
				byte[] bytes = keycloakUtils.getUserAvatar(user.getEmailAddress());
				if (bytes != null && bytes.length > 0) {
					userLocalService.updatePortrait(user.getUserId(), bytes);
				} else {
					userLocalService.deletePortrait(user.getUserId());
				}
			}
		} catch (Exception e) {
			LOG.warn(String.format("Error updating portrait %d for user %s", user.getPortraitId(), user.getScreenName()), e);
		}

	}

}