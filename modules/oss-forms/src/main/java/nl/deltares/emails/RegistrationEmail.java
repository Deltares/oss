package nl.deltares.emails;

import com.liferay.portal.kernel.model.User;
import nl.deltares.emails.serializer.EmailSerializer;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.model.impl.Registration;

import java.util.*;

import static nl.deltares.emails.EmailUtils.sendEmail;

public class RegistrationEmail {

    private String sendFromEmail = "mydeltares@deltares.nl";
    private final List<String> sendCCEmail = new ArrayList<>();
    private final List<String> sendBCCEmail = new ArrayList<>();
    private String replyToEmail = null;
    private final ResourceBundle bundle;
    private String subject;
    private String emailBannerUrl = null;
    private String emailFooterUrl;
    private long emailBannerFileEntryId;
    private long emailFooterFileEntryId;
    private User _user;
    private List<Registration> _registrations;
    private List<RegistrationInfo> _registrationInfos;

    public RegistrationEmail(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void setReplyToEmail(String replyToEmail) {
        this.replyToEmail = replyToEmail;
    }

    public void setSendFromEmail(String sendFromEmail) {
        this.sendFromEmail = sendFromEmail;
    }

    public void addCCEmail(String ccEmail) {
        if (!sendCCEmail.contains(ccEmail)) sendCCEmail.add(ccEmail);
    }

    public void addBCCEmail(String bccEmail) {
        if (!sendBCCEmail.contains(bccEmail)) sendBCCEmail.add(bccEmail);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void sendUnregisterEmail(EmailSerializer<RegistrationEmail> serializer, User user, List<Registration> registrations) throws Exception {
        _user = user;
        _registrations = registrations;
        _registrationInfos = Collections.emptyList();
        StringBuilder bodyBuilder = new StringBuilder();
        serializer.serialize(this, bodyBuilder);
        sendEmail(bodyBuilder.toString(), subject, _user.getEmailAddress(), sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail,
                loadImageMap(), Collections.emptyMap());
    }

    public void sendRegisterEmail(EmailSerializer<RegistrationEmail> serializer, User user, List<Registration> registrations,
                                  List<RegistrationInfo> registrationInfos) throws Exception {

        _user = user;
        _registrations = registrations;
        _registrationInfos = registrationInfos;
        StringBuilder bodyBuilder = new StringBuilder();
        serializer.serialize(this, bodyBuilder);

        sendEmail(bodyBuilder.toString(), subject, _user.getEmailAddress(), sendCCEmail, sendBCCEmail, sendFromEmail, replyToEmail,
                loadImageMap(), Collections.emptyMap());
    }

    private HashMap<String, Object> loadImageMap() {

        HashMap<String, Object> imageMap = new HashMap<>();
        if (emailBannerFileEntryId > 0) imageMap.put("banner", emailBannerFileEntryId);
        if (emailFooterFileEntryId > 0) imageMap.put("footer", emailFooterFileEntryId);
        return imageMap;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setEmailBanner(String emailBannerURL, long emailBannerFileEntryId) {
        this.emailBannerUrl = emailBannerURL;
        this.emailBannerFileEntryId = emailBannerFileEntryId;
    }

    public void setEmailFooter(String emailFooterURL, long emailFooterFileEntryId) {
        this.emailFooterUrl = emailFooterURL;
        this.emailFooterFileEntryId = emailFooterFileEntryId;
    }

    public String getEmailBannerUrl() {
        return emailBannerUrl;
    }

    public String getEmailFooterUrl() {
        return emailFooterUrl;
    }


    public User getUser() {
        return _user;
    }

    public List<Registration> getRegistrations() {
        return _registrations;
    }

    public String getRemarks(String articleId) {
        Optional<RegistrationInfo> first = _registrationInfos.stream().filter(info -> info.getArticleId().equals(articleId)).findFirst();
        return first.map(RegistrationInfo::getRemarks).orElse(null);
    }
}
