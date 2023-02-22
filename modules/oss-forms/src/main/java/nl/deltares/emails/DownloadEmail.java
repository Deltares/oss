package nl.deltares.emails;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.DownloadRequest;
import nl.deltares.emails.serializer.DownloadsEmailSerializer;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

import java.util.ResourceBundle;

import static nl.deltares.emails.EmailUtils.sendEmail;

public class DownloadEmail {

    private String sendFromEmail = "mydeltares@deltares.nl";
    private String sendToEmail = null;
    private String sendCCEmail = null;
    private String sendBCCEmail = null;
    private String replyToEmail = null;
    private final User user;
    private final ResourceBundle bundle;
    private final DownloadRequest request;

    public DownloadEmail(User user, ResourceBundle bundle, DownloadRequest request) {
        this.user = user;
        this.bundle = bundle;
        this.request = request;
    }

    public void setReplyToEmail(String replyToEmail) {
        if (replyToEmail == null || replyToEmail.isEmpty()) return;
        this.replyToEmail = replyToEmail;
    }

    public void setSendFromEmail(String sendFromEmail) {
        if (sendFromEmail == null || sendFromEmail.isEmpty()) return;
        this.sendFromEmail = sendFromEmail;
    }

    private void loadEmailAddresses() {
        BillingInfo billingInfo = request.getBillingInfo();
        if (billingInfo != null && billingInfo.getEmail() != null) {
            sendToEmail = billingInfo.getEmail();
            if (sendToEmail.equals(user.getEmailAddress())) {
                sendCCEmail = null;
            } else {
                sendCCEmail = user.getEmailAddress();
            }
        } else {
            sendToEmail = user.getEmailAddress();
            sendCCEmail = null;
        }
    }

    public void sendDownloadsEmail() throws Exception {
        StringBuilder bodyBuilder = new StringBuilder();
        DownloadsEmailSerializer serializer = new DownloadsEmailSerializer();
        serializer.serialize(this, bodyBuilder);

        String subject = LanguageUtil.format(bundle, "download.email.subject", null);

        loadEmailAddresses();

        sendEmail(bodyBuilder.toString(), subject, sendToEmail, sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail, loadImageMap(), Collections.emptyMap());
    }

    private HashMap<String, URL> loadImageMap() throws MalformedURLException {

        URL bannerURL = request.getBannerURL();
        HashMap<String, URL> imageMap = new HashMap<>();
        if (bannerURL != null) imageMap.put("banner", bannerURL);
        return imageMap;
    }

    public User getUser() {
        return user;
    }

    public DownloadRequest getDownloadRequest() {
        return request;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBCCToEmail(String bccToEmail) {
        if (bccToEmail == null || bccToEmail.isEmpty()) return;
        sendBCCEmail = bccToEmail;
    }
}
