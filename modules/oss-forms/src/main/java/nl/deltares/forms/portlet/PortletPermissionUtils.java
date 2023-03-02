package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleServiceUtil;

public class PortletPermissionUtils {

    private static final Log LOG = LogFactoryUtil.getLog(PortletPermissionUtils.class);

    static boolean isUserSiteOrOtherAdministrator(long userId, long siteGroupId){
        return isUserAdministrator(userId) || isUserSiteAdministrator(userId, siteGroupId);
    }

    static boolean isUserAdministrator(long userId) {
        try {
            for (Role userGroupRole : RoleServiceUtil.getUserRoles(userId)) {
                if (userGroupRole.getName().equals("Administrator")) return true;
            }
        } catch (PortalException e) {
            LOG.info("Error getting user roles: " + e.getMessage());
        }
        return false;
    }

    static boolean isUserSiteAdministrator(long userId, long siteGroupId){
        try {
            for (Role userGroupRole : RoleServiceUtil.getUserGroupRoles(userId, siteGroupId)) {
                if (userGroupRole.getName().equals("Site Administrator")) return true;
            }
        } catch (PortalException e) {
            LOG.info("Error getting user group roles: " + e.getMessage());
        }
        return false;
    }
}
