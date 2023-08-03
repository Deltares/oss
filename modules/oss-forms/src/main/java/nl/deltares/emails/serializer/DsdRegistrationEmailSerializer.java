package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.model.RegistrationRequest;
import nl.deltares.emails.DsdEmail;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.Period;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

public abstract class DsdRegistrationEmailSerializer implements EmailSerializer<DsdEmail>{

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    @SuppressWarnings("RedundantThrows")
    @Override
    public void serialize(DsdEmail content, StringBuilder writer) throws Exception {

        RegistrationRequest request = content.getRegistrationRequest();
        Event event = request.getEvent();

        if (event.getEmailBannerURL() != null && !event.getEmailBannerURL().isBlank()){
            writer.append("<img src=\"cid:banner\" />");
        }
        User user = content.getUser();
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.header", new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</p>");
        appendRegistrationAction(writer, content);

        writer.append("<table style=\"width: 900px;\">");
        List<Registration> registrations = request.getRegistrations();
        for (Registration registration : registrations) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            setTimeZone(registration.getTimeZoneId());
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

        appendRemarks(writer, content);

        if (request.isBusInfo()) appendBusInfo(writer, content);

        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");

        if (event.getEmailFooterURL() != null && !event.getEmailFooterURL().isBlank()){
            writer.append("<img src=\"cid:footer\" />");
        }
        writer.append("<br>");
    }

    private void setTimeZone(String timeZoneId) {
        final TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        timeFormat.setTimeZone(timeZone);
        dateFormat.setTimeZone(timeZone);
    }

    protected abstract void appendRemarks(StringBuilder writer, DsdEmail content);

    protected abstract void appendNotice(StringBuilder writer, DsdEmail content);

    protected abstract void appendRegistrationAction(StringBuilder writer, DsdEmail content);

    protected abstract void appendBusInfo(StringBuilder writer, DsdEmail content);

    private void appendRegistration(StringBuilder writer, DsdEmail content, Registration registration) {
        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.name", null)).append("</td>");
        writer.append("<td>");
        writer.append(registration.getTitle());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.type", null)).append("</td>");
        writer.append("<td>");
        writer.append(content.getRegistrationRequest().translateRegistrationType(registration.getType()));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.room", null)).append("</td>");
        writer.append("<td>");
        writer.append(content.getRegistrationRequest().getLocation(registration));
        writer.append("</td>");
        writer.append("</tr>");

        if (registration.isMultiDayEvent() && !registration.isDaily()){
            final List<Period> startAndEndTimesPerDay = registration.getStartAndEndTimesPerDay();
            for (Period period : startAndEndTimesPerDay) {
                writer.append("<tr>");
                writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.date", null)).append("</td>");
                writer.append("<td>");
                writer.append(getDateString(period.getStartDate(), period.getEndDate()));
                writer.append("</td>");
                writer.append("</tr>");

                writer.append("<tr>");
                writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.time", null)).append("</td>");
                writer.append("<td>");
                writer.append(getTimeString(period.getStartDate(), period.getEndDate(), registration.getTimeZoneId()));
                writer.append("</td>");
                writer.append("</tr>");
            }

        } else {
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.date", null)).append("</td>");
            writer.append("<td>");
            writer.append(getDateString(registration.getStartTime(), registration.getEndTime()));
            writer.append("</td>");
            writer.append("</tr>");

            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.time", null)).append("</td>");
            writer.append("<td>");
            writer.append(getTimeString(registration.getStartTime(), registration.getEndTime(), registration.getTimeZoneId()));
            writer.append("</td>");
            writer.append("</tr>");
        }


        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.price", null)).append("</td>");
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

    private String getTimeString(Date startDate, Date endDate, String timeZoneId) {
        String startTime = timeFormat.format(startDate);
        String endTime = timeFormat.format(endDate);
        return startTime + " - " + endTime + "(" + timeZoneId + ")";
    }

    private String getDateString(Date startDate, Date endDate) {
        String startDay = dateFormat.format(startDate);
        if (endDate.after(startDate)){
            String endDay = dateFormat.format(endDate);
            return startDay + " - " + endDay;
        }
        return startDay;

    }
}
