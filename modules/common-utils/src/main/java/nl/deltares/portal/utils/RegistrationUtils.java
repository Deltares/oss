package nl.deltares.portal.utils;

import com.liferay.portal.kernel.exception.PortalException;

public interface RegistrationUtils {

    void validateRegistration(long groupId, long articleId, long userId) throws PortalException;

}
