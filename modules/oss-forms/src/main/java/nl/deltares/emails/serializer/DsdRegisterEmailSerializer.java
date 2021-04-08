package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import nl.deltares.emails.DsdEmail;

public class DsdRegisterEmailSerializer extends DsdRegistrationEmailSerializer{

    public void appendNotice(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.notice",content.getRegistrationRequest().getSiteURL()));
        writer.append("</p>");
    }

    public void appendRegistrationAction(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.event", content.getRegistrationRequest().getEvent().getTitle()));
        writer.append("</p>");
    }

    public void appendBusInfo(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.busnotice", new Object[0]));
        writer.append("</p>");
    }
}
