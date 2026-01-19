package nl.deltares.emails;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.emails.serializer.LicenseFileEmailSerializer;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

import static nl.deltares.common.emails.EmailUtils.sendEmail;

public class LicenseFilesEmail {

    private final String customerName;
    private final User user;
    private final ResourceBundle bundle;
    private String sendFromEmail;
    private String replyToEmail;
    private String sendBCCEmail;
    private final String sendCCEmail;
    private final String sendToEmail;

    public LicenseFilesEmail(String customerName, User user, ResourceBundle bundle) {
        this.user = user;
        this.customerName = customerName;
        this.bundle = bundle;

        this.sendToEmail = user.getEmailAddress();
        this.sendFromEmail = "software@deltares.nl";
        this.sendCCEmail = null;
    }

    public void setBCCToEmail(String bccToEmail) {
        if (bccToEmail == null || bccToEmail.isEmpty()) return;
        sendBCCEmail = bccToEmail;
    }

    public void setReplyToEmail(String replyToEmail) {
        if (replyToEmail == null || replyToEmail.isEmpty()) return;
        this.replyToEmail = replyToEmail;
    }

    public void setSendFromEmail(String sendFromEmail) {
        if (sendFromEmail == null || sendFromEmail.isEmpty()) return;
        this.sendFromEmail = sendFromEmail;
    }

    public void sendLicenseFilesEmail(File licenseFile) throws Exception {
        StringBuilder bodyBuilder = new StringBuilder();
        LicenseFileEmailSerializer serializer = new LicenseFileEmailSerializer(customerName);
        serializer.serialize(this, bodyBuilder);

        String subject = LanguageUtil.format(bundle, "downloadlicenses.email.subject", customerName);

        Map<String, File> attachments = Collections.singletonMap("licenseFiles.zip", licenseFile);
        sendEmail(bodyBuilder.toString(), subject, sendToEmail, sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail,
                Collections.emptyMap(), attachments);
    }

    public User getUser() {
        return user;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

}
