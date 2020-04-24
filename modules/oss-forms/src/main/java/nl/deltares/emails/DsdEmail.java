package nl.deltares.emails;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.dsd.model.Reservation;
import nl.deltares.dsd.model.Session;
import nl.deltares.dsd.model.Type;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import static nl.deltares.emails.EmailUtils.*;

public class DsdEmail {

    public enum DSD_SITE {dsdint, dsdnl}

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");
    private final DSD_SITE site;
    private String siteUrl;
    private String sendFromEmail = "dsd@deltares.nl";
    private URL banner = null;
    private URL footer = null;
    private ResourceBundle bundle;


    public DsdEmail(DSD_SITE site) {
        this.site = site;
    }

    public void setSiteUrl(String siteUrl){
        this.siteUrl = siteUrl;
    }

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

    public void sendUnregisterEmail(User user, Reservation reservation) throws Exception {
        String body = getTemplate("unregister_" + site.name() + ".tmpl");
        body = StringUtil.replace(body,
                new String[]{"[$FIRSTNAME$]", "[$EVENT$]", "[$RESERVATION$]"},
                new String[]{user.getFirstName(), reservation.getEventName(), reservation.getName()});

        String subject = LanguageUtil.format(bundle, "dsd.unregister.subject", "event");

        sendEmail(body, subject, user.getEmailAddress(), sendFromEmail,  loadImageMap());
    }

    public void sendRegisterEmail(User user, Reservation reservation) throws Exception{
        String body = getTemplate("register_" + site.name() + ".tmpl");

        body = StringUtil.replace(body,
                new String[]{ "[$FIRSTNAME$]", "[$EVENT$]", "[$RESERVATION$]", "[$TYPE$]", "[$ROOM$}", "[$DATE$]", "[$TIME$]", "[$SITEURL$]"},
                new String[]{user.getFirstName(), reservation.getEventName(), reservation.getName(),
                        getType(reservation.getType()), getRoom(reservation), getDate(reservation), getTime(reservation), siteUrl});

        String subject = LanguageUtil.format(bundle, "dsd.register.subject", "event");
        sendEmail(body, subject, user.getEmailAddress(), sendFromEmail, loadImageMap());
//        sendEmail(body, subject, "erik.derooij@deltares.nl", sendFromEmail, true);
    }

    private String getTime(Reservation reservation) {
        String startTime = timeFormat.format(new Date(reservation.getStartTime()));
        String endTime = timeFormat.format(new Date(reservation.getEndTime()));
        return startTime + " - " + endTime;
    }

    private String getDate(Reservation reservation) {
        String startDay = dateFormat.format(new Date(reservation.getStartTime()));
        String endDay = dateFormat.format(new Date(reservation.getEndTime()));
        return startDay + " - " + endDay;
    }

    private String getRoom(Reservation reservation) {
        if (reservation instanceof Session){
            return ((Session)reservation).getRoom().getName();
        } else {
            throw new UnsupportedOperationException("Reservation type does not support rooms: " + reservation.getType());
        }
    }

    private String getType(Type type) {
        return LanguageUtil.get(bundle, "dsd.type." + type.name());
    }

    private HashMap<String, URL> loadImageMap() {
        HashMap<String, URL> imageMap = new HashMap<>();
        imageMap.put("banner", banner == null ? getImage(site.name() + "_banner.jpg") : banner);
        imageMap.put("footer", footer == null ? getImage(site.name() + "_footer.png") : footer);
        return imageMap;
    }

}
