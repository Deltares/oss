package nl.deltares.emails;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.emails.serializer.DsdRegisterEmailSerializer;
import nl.deltares.emails.serializer.DsdRegistrationEmailSerializer;
import nl.deltares.emails.serializer.DsdUnRegisterEmailSerializer;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.RegistrationRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import static nl.deltares.emails.EmailUtils.sendEmail;

public class DsdEmail {

    private String sendFromEmail = "mydeltares@deltares.nl";
    private String sendToEmail = null;
    private String sendCCEmail = null;
    private String sendBCCEmail = null;
    private String replyToEmail = null;
    private final User user;
    private final ResourceBundle bundle;
    private final RegistrationRequest request;

    public DsdEmail(User user, ResourceBundle bundle, RegistrationRequest request) {
        this.user = user;
        this.bundle = bundle;
        this.request = request;
    }

    public void setReplyToEmail(String replyToEmail) {
        this.replyToEmail = replyToEmail;
    }

    public void setSendFromEmail(String sendFromEmail) {
        this.sendFromEmail = sendFromEmail;
    }

    public void sendUnregisterEmail() throws Exception {

        StringBuilder bodyBuilder = new StringBuilder();
        DsdUnRegisterEmailSerializer serializer = new DsdUnRegisterEmailSerializer();
        serializer.serialize(this, bodyBuilder);

        String subject = LanguageUtil.format(bundle, "dsd.unregister.subject", request.getEvent().getTitle());

        loadEmailAddresses();

        sendEmail(bodyBuilder.toString(), subject, sendToEmail, sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail, loadImageMap(), Collections.emptyMap());
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

    public void sendRegisterEmail() throws Exception {
        StringBuilder bodyBuilder = new StringBuilder();
        DsdRegistrationEmailSerializer serializer = new DsdRegisterEmailSerializer();
        serializer.serialize(this, bodyBuilder);

        String subject = LanguageUtil.format(bundle, "dsd.register.subject", request.getEvent().getTitle());

        loadEmailAddresses();

        sendEmail(bodyBuilder.toString(), subject, sendToEmail, sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail, loadImageMap(), Collections.emptyMap());
    }

    private HashMap<String, Object> loadImageMap()  {

        long bannerFileEntryId = request.getEvent().getEmailBannerFileEntryId();
        long footerFileEntryId = request.getEvent().getEmailFooterFileEntryId();
        HashMap<String, Object> imageMap = new HashMap<>();
        if (bannerFileEntryId > 0 ) imageMap.put("banner", bannerFileEntryId);
        if (footerFileEntryId > 0 ) imageMap.put("footer", footerFileEntryId);
        return imageMap;
    }

    public User getUser() {
        return user;
    }

    public RegistrationRequest getRegistrationRequest() {
        return request;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBCCToEmail(String bccToEmail) {
        sendBCCEmail = bccToEmail;
    }
}
