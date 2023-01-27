package nl.deltares.portal.utils.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import nl.deltares.portal.model.impl.LicenseFile;
import nl.deltares.portal.utils.LicenseManagerUtils;
import org.osgi.service.component.annotations.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = LicenseManagerUtils.class
)
/*
 * The idea of this class was to allow retrieval of user location info from request. However it is not
 * that easy to get IP address from the request and therefore this class is not being used.
 */
public class LicenseManagerUtilsImpl implements LicenseManagerUtils {

    private static final Log LOG = LogFactoryUtil.getLog(LicenseManagerUtilsImpl.class);

    public final static String LMCRYPT = "deltares.licenses.lmcrypt";
    private final String lmcrypt_path;
    private final Path licensesDir = new File(System.getProperty("java.io.tmpdir"), "licenses").toPath();


    public LicenseManagerUtilsImpl() {
        String file_path = PropsUtil.get(LMCRYPT);

        File file = new File(file_path);
        if (file.exists()) {
            lmcrypt_path = file_path;
        } else {
            LOG.warn("LMCrypt file does not exist: " + file.getAbsolutePath());
            lmcrypt_path = null;
        }
    }

    @Override
    public boolean isActive() {
        return lmcrypt_path != null;
    }

    @Override
    public boolean encryptLicense(LicenseFile licenseFile, User user){

        if (!isActive()){
            LOG.warn("Unable to generate license files as the LicenseManager is not active!");
            return false;
        }
        if (user == null || user.isDefaultUser()) return false;

        String licenseFileTemplateContent = replaceTags(licenseFile, user);


        try {
            if (!licensesDir.toFile().exists()){
                Files.createDirectories(licensesDir);
            }
            final Path tempFile = Files.createTempFile(licensesDir, null, licenseFile.getName());
            FileUtil.write(tempFile.toFile(), licenseFileTemplateContent);

            final Process process = Runtime.getRuntime().exec(String.format("%s %s", lmcrypt_path, tempFile.toAbsolutePath()));

            if (process.waitFor(5, TimeUnit.SECONDS)) {
                //successfully created a license file
                licenseFile.setGeneratedFile(tempFile.toFile());
                return true;
            }
        } catch (IOException | InterruptedException e) {
            LOG.warn(String.format("Failed to generate license file %s for user %s: %s", licenseFile.getName(), user.getEmailAddress(), e.getMessage()));
        }
        return false;

    }

    private String replaceTags(LicenseFile licenseFile, User user) {
        String licenseFileTemplateContent = licenseFile.getTemplateContent();
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("FIRSTNAME", user.getFirstName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("LASTNAME", user.getLastName());
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("EMAIL", user.getEmailAddress());

        final SimpleDateFormat dateFormat = new SimpleDateFormat(licenseFile.getDateFormat());
        final String expirationDate = dateFormat.format(new Date(System.currentTimeMillis() + licenseFile.getExpirationPeriodInMillis()));
        licenseFileTemplateContent = licenseFileTemplateContent.replaceAll("EXPIRATIONDATE", expirationDate);

        return licenseFileTemplateContent;
    }
}
