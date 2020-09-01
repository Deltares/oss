package nl.deltares.emails;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.dsd.model.BillingInfo;
import nl.deltares.dsd.model.RegistrationRequest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

import static nl.deltares.emails.EmailUtils.*;

public class DsdEmail {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");
    private String sendFromEmail = "mydeltares@deltares.nl";
//    private String replyToEmail = "dsd@deltares.nl";
    private String replyToEmail = "erik.derooij@deltares.nl"; //TODO: change to dsd
    private URL banner = null;
    private URL footer = null;
    private ResourceBundle bundle;
    private String languageId = "en";


    public void setResourceBundle(ResourceBundle bundel) {
        this.bundle = bundel;
    }

    public void setBanner(URL bannerPath){
        this.banner = bannerPath;
    }

    public void setFooter(URL footerPath) {
        this.footer = footerPath;
    }

    public void setSendFromEmail(String sendFromEmail) {
        this.sendFromEmail = sendFromEmail;
    }

    public void sendUnregisterEmail(User user, RegistrationRequest registrationRequest) throws Exception {
        String body = getTemplate("unregister_" + languageId + ".tmpl");
        body = StringUtil.replace(body,
                new String[]{"[$FIRSTNAME$]", "[$LASTNAME$]", "[$EVENT$]", "[$RESERVATION$]"},
                new String[]{user.getFirstName(), user.getLastName(), registrationRequest.getEventName(), registrationRequest.getRegistration().getTitle()});

        String subject = LanguageUtil.format(bundle, "dsd.unregister.subject", "event");

        BillingInfo billingInfo = registrationRequest.getBillingInfo();
        String ccEmail;
        String toEmail;
        if (billingInfo != null && billingInfo.getEmail() != null){
            toEmail = billingInfo.getEmail();
            ccEmail = user.getEmailAddress();
        } else {
            toEmail = user.getEmailAddress();
            ccEmail = null;
        }

        sendEmail(body, subject, toEmail, ccEmail, sendFromEmail, replyToEmail, loadImageMap());
    }

    public void sendRegisterEmail(User user, RegistrationRequest registrationRequest) throws Exception{
        String body = getTemplate("register_" +  languageId + ".tmpl");

        body = StringUtil.replace(body,
                new String[]{ "[$FIRSTNAME$]", "[$LASTNAME$]", "[$EVENT$]", "[$RESERVATION$]", "[$TYPE$]", "[$ROOM$}", "[$DATE$]", "[$TIME$]", "[$SITEURL$]"},
                new String[]{user.getFirstName(), user.getLastName(), registrationRequest.getEventName(), registrationRequest.getRegistration().getTitle(),
                        getType(registrationRequest.getRegistration().getType()), registrationRequest.getLocation(),
                        getDate(registrationRequest), getTime(registrationRequest), registrationRequest.getArticleUrl()});

        String subject = LanguageUtil.format(bundle, "dsd.register.subject", "event");

        BillingInfo billingInfo = registrationRequest.getBillingInfo();
        String ccEmail;
        String toEmail;
        if (billingInfo != null && billingInfo.getEmail() != null){
            toEmail = billingInfo.getEmail();
            ccEmail = user.getEmailAddress();
        } else {
            toEmail = user.getEmailAddress();
            ccEmail = null;
        }
        sendEmail(body, subject, toEmail, ccEmail, sendFromEmail, replyToEmail, loadImageMap());
    }

    private String getTime(RegistrationRequest registrationRequest) {
        String startTime = timeFormat.format(registrationRequest.getRegistration().getStartTime());
        String endTime = timeFormat.format(registrationRequest.getRegistration().getEndTime());
        return startTime + " - " + endTime;
    }

    private String getDate(RegistrationRequest registrationRequest) {
        String startDay = dateFormat.format(registrationRequest.getRegistration().getStartTime());
        String endDay = dateFormat.format(registrationRequest.getRegistration().getEndTime());
        return startDay + " - " + endDay;
    }

    private String getType(String type) {
        return LanguageUtil.get(bundle, "dsd.type." + type);
    }

    private HashMap<String, URL> loadImageMap() {
        HashMap<String, URL> imageMap = new HashMap<>();
        imageMap.put("banner", banner == null ? getImage("dsdint_banner.jpg") : banner);
        imageMap.put("footer", footer == null ? getImage("dsdint_footer.png") : footer);
        return imageMap;
    }

    public void setLanguage(String languageId) {
        this.languageId = languageId;
    }
}
