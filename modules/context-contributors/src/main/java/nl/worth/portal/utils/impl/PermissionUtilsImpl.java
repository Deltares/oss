package nl.worth.portal.utils.impl;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import nl.worth.portal.utils.PermissionUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    service = PermissionUtils.class
)
public class PermissionUtilsImpl implements PermissionUtils {

  @Reference
  private UserLocalService userLocalService;

  @Reference
  private RoleLocalService roleLocalService;

  @Reference
  private ResourceActionLocalService resourceActionLocalService;

  @Reference
  private ResourcePermissionLocalService resourcePermissionLocalService;

  @Override
  public boolean userIsSiteMember(User user, Group group) {
    return userLocalService.hasGroupUser(group.getGroupId(), user.getUserId());
  }

  @Override
  public boolean isResourcePublic(long companyId, String resourceName, String primaryKey)
      throws PortalException {
    ResourceAction viewAction = resourceActionLocalService
        .getResourceAction(resourceName, ActionKeys.VIEW);
    Role guestRole = roleLocalService.getRole(companyId, RoleConstants.GUEST);
    ResourcePermission resourcePermission = resourcePermissionLocalService
        .fetchResourcePermission(companyId, resourceName,
            ResourceConstants.SCOPE_INDIVIDUAL, primaryKey,
            guestRole.getRoleId());
    return resourcePermission != null && resourcePermission.hasAction(viewAction);
  }
}
