package nl.deltares.portal.utils;

import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.LicenseFile;

public interface LicenseManagerUtils {

    boolean isActive();

    boolean encryptLicense(LicenseFile licenseFile, User user);

}
