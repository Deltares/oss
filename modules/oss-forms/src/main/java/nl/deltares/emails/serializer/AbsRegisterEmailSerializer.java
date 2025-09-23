package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.emails.RegistrationEmail;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.Period;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

public abstract class AbsRegisterEmailSerializer implements EmailSerializer<RegistrationEmail> {

    private SimpleDateFormat dateFormat;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    @SuppressWarnings("RedundantThrows")
    @Override
    public void serialize(RegistrationEmail content, StringBuilder writer) throws Exception {

        dateFormat = new SimpleDateFormat("dd MMMM yyyy", content.getBundle().getLocale());

        String emailBannerUrl = content.getEmailBannerUrl();
        if (emailBannerUrl != null && !emailBannerUrl.isEmpty()) {
            writer.append("<img src=\"cid:banner\" />");
        }

        User user = content.getUser();

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.header",
                new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</p>");
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), getEventTitle(), null));
        writer.append("</p>");

        writer.append("<table style=\"width: 900px;\">");
        List<Registration> registrations = content.getRegistrations();
        for (Registration registration : registrations) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            setTimeZone(registration.getTimeZoneId());
            appendRegistration(writer, content, registration, content.getRemarks(registration.getArticleId()));
            writer.append("<tr><td><hr></td><td><hr></td></tr>");

        }
        writer.append("</table>");


        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");

        String emailFooterUrl = content.getEmailFooterUrl();
        if (emailFooterUrl != null && !emailFooterUrl.isEmpty()) {
            writer.append("<img src=\"cid:footer\" />");
        }
        writer.append("<br>");
    }

    abstract String getEventTitle();

    private void setTimeZone(String timeZoneId) {
        final TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        timeFormat.setTimeZone(timeZone);
        dateFormat.setTimeZone(timeZone);
    }

    private void appendRegistration(StringBuilder writer, RegistrationEmail content, Registration registration, String remarks) {
        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.name", null)).append("</td>");
        writer.append("<td>");
        writer.append(registration.getTitle());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.registration.room", null)).append("</td>");
        writer.append("<td>");
        writer.append(getLocation(registration));
        writer.append("</td>");
        writer.append("</tr>");

        if (registration.isMultiDayEvent() && !registration.isDaily()) {
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

        if (remarks != null) {
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "dsd.email.register.remarks", null)).append("</td>");
            writer.append("<td>");
            writer.append(remarks);
            writer.append("</td>");
            writer.append("</tr>");
        }

    }

    private String getLocation(Registration registration) {
        if (registration instanceof SessionRegistration) {
            Room room = ((SessionRegistration) registration).getRoom();
            return room.getTitle();
        } else if (registration instanceof DinnerRegistration) {
            Location location = ((DinnerRegistration) registration).getRestaurant();
            return location.getTitle();
        }
        return null;
    }

    private String getPrice(ResourceBundle bundle, Registration registration) {

        if (registration.getPrice() > 0) {
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
        if (endDate.after(startDate)) {
            String endDay = dateFormat.format(endDate);
            return startDay + " - " + endDay;
        }
        return startDay;

    }
}
