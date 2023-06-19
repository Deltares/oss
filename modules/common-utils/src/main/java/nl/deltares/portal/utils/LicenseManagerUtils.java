package nl.deltares.portal.utils;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.model.User;
import nl.deltares.portal.model.impl.LicenseFile;

import java.io.IOException;
import java.util.Map;

public interface LicenseManagerUtils {

    boolean isActive();

    Map<String, String> encryptLicense(LicenseFile licenseFile, User user) throws IOException, JSONException;

    Map<String, String> encryptLicense(String licenseType, User user) throws IOException, JSONException;

}
