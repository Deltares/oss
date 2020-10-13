package nl.worth.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;

public interface PermissionUtils {

  boolean userIsSiteMember(User user, Group group);

  boolean isResourcePublic(long companyId, String resourceName, String primaryKey)
      throws PortalException;

}