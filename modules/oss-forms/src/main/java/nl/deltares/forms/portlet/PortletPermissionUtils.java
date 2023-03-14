package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleServiceUtil;

public class PortletPermissionUtils {

    private static final Log LOG = LogFactoryUtil.getLog(PortletPermissionUtils.class);

    static boolean isUserSiteAdministrator(long userId, long siteGroupId){
        return isUserInSiteRole(userId, siteGroupId, "Site Administrator");
    }

    static boolean isUserInSiteRole(long userId, long siteGroupId, String role) {

        try {
            for (Role userGroupRole : RoleServiceUtil.getUserGroupRoles(userId, siteGroupId)) {
                if (userGroupRole.getName().equals(role)) return true;
            }
        } catch (PortalException e) {
            LOG.info("Error getting user's site roles: " + e.getMessage());
        }

        try {
            for (Role userGroupRole : RoleServiceUtil.getUserGroupGroupRoles(userId, siteGroupId)) {
                if (userGroupRole.getName().equals(role)) return true;
            }
        } catch (PortalException e) {
            LOG.info("Error getting user's group site roles: " + e.getMessage());
        }
        return false;
    }
}
