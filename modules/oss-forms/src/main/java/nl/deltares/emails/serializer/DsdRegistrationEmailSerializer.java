package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.dsd.model.RegistrationRequest;
import nl.deltares.emails.DsdEmail;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public abstract class DsdRegistrationEmailSerializer implements EmailSerializer<DsdEmail>{

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    @SuppressWarnings("RedundantThrows")
    @Override
    public void serialize(DsdEmail content, StringBuilder writer) throws Exception {

        RegistrationRequest request = content.getRegistrationRequest();
        Event event = request.getEvent();

        if (event.getEmailBannerURL()  != null){
            writer.append("<img src=\"cid:banner\" />");
        }
        User user = content.getUser();
        writer.append("<h2>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.header", new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</h2>");

        appendRegistrationAction(writer, content);

        writer.append("<table style=\"width: 900px;\">");
        List<Registration> registrations = request.getRegistrations();
        for (Registration registration : registrations) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");

            appendRegistration(writer, content, registration);

            writer.append("<tr><td><hr></td><td><hr></td></tr>");

            List<Registration> childRegistrations = request.getChildRegistrations(registration);
            for (Registration childRegistration : childRegistrations) {
                appendRegistration(writer, content, childRegistration);

                writer.append("<tr><td><hr></td><td><hr></td></tr>");
            }
        }
        writer.append("</table>");

        appendNotice(writer, content);

        if (request.isBusInfo()) appendBusInfo(writer, content);

        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");

        if (event.getEmailFooterURL() != null){
            writer.append("<img src=\"cid:footer\" />");
        }
        writer.append("<br>");
    }

    protected abstract void appendNotice(StringBuilder writer, DsdEmail content);

    protected abstract void appendRegistrationAction(StringBuilder writer, DsdEmail content);

    protected abstract void appendBusInfo(StringBuilder writer, DsdEmail content);

    private void appendRegistration(StringBuilder writer, DsdEmail content, Registration registration) {
        writer.append("<tr>");
        writer.append("<td class=\"type\">" + LanguageUtil.format(content.getBundle(), "dsd.email.registration.name", null) + "</td>");
        writer.append("<td>");
        writer.append(registration.getTitle());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">" + LanguageUtil.format(content.getBundle(), "dsd.email.registration.type", null) +"</td>");
        writer.append("<td>");
        writer.append(content.getRegistrationRequest().translateRegistrationType(registration.getType()));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">"+ LanguageUtil.format(content.getBundle(), "dsd.email.registration.room", null) +"</td>");
        writer.append("<td>");
        writer.append(content.getRegistrationRequest().getLocation(registration));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">"+LanguageUtil.format(content.getBundle(), "dsd.email.registration.date", null)+"</td>");
        writer.append("<td>");
        writer.append(getDate(registration));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">"+LanguageUtil.format(content.getBundle(), "dsd.email.registration.time", null)+"</td>");
        writer.append("<td>");
        writer.append(getTime(registration));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">"+LanguageUtil.format(content.getBundle(), "dsd.email.registration.price", null)+"</td>");
        writer.append("<td>");
        writer.append(getPrice(content.getBundle(), registration));
        writer.append("</td>");
        writer.append("</tr>");

    }

    private String getPrice(ResourceBundle bundle, Registration registration) {

        if (registration.getPrice() > 0){
            return registration.getCurrency() + ' ' + registration.getPrice();
        }
        return LanguageUtil.format(bundle, "dsd.theme.session.free", null);
    }

    private String getTime(Registration registration) {
        String startTime = timeFormat.format(registration.getStartTime());
        String endTime = timeFormat.format(registration.getEndTime());
        return startTime + " - " + endTime;
    }

    private String getDate(Registration registration) {
        String startDay = dateFormat.format(registration.getStartTime());
        if (registration.getEndTime().after(registration.getStartTime())){
            String endDay = dateFormat.format(registration.getEndTime());
            return startDay + " - " + endDay;
        }
        return startDay;

    }
}
