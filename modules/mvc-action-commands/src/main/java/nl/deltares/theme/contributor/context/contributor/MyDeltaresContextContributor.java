package nl.deltares.theme.contributor.context.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.common.MyDeltaresUtil;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {"type=" + com.liferay.portal.kernel.template.TemplateContextContributor.TYPE_THEME},
	service = com.liferay.portal.kernel.template.TemplateContextContributor.class
)
public class MyDeltaresContextContributor
	implements com.liferay.portal.kernel.template.TemplateContextContributor {

	private static final Log LOG = LogFactoryUtil.getLog(MyDeltaresContextContributor.class);

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		boolean isAdmin = false;
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			Group group = themeDisplay.getScopeGroup();
			PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();

			if (themeDisplay.isSignedIn() && GroupPermissionUtil.contains(permissionChecker, group,
					ActionKeys.VIEW_SITE_ADMINISTRATION)) {
				isAdmin = true;
			}

		} catch (PortalException e) {
			LOG.warn(e);
		}
		contextObjects.put("is_site_admin", isAdmin);

		contextObjects.put("user_signout_url", themeDisplay.getURLSignOut());
		try {
			contextObjects.put("user_mailing_url", MyDeltaresUtil.getMailingPath() );
			contextObjects.put("user_account_url", MyDeltaresUtil.getAccountPath());
			contextObjects.put("user_avatar_url", MyDeltaresUtil.getAvatarPath());
			contextObjects.put("has_user_avatar_url", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			String portraitURL = themeDisplay.getUser().getPortraitURL(themeDisplay);
//			contextObjects.put("has_user_avatar_url", portraitURL != null);
//			contextObjects.put("user_avatar_url", portraitURL);
//		} catch (PortalException e) {
//			LOG.warn(e);
//		}
	}

}