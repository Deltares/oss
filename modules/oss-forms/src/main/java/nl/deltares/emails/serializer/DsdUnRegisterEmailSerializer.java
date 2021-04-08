package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import nl.deltares.emails.DsdEmail;

public class DsdUnRegisterEmailSerializer extends DsdRegistrationEmailSerializer{

    public void appendNotice(StringBuilder writer, DsdEmail content) {
    }

    public void appendRegistrationAction(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.unregister.event", content.getRegistrationRequest().getEvent().getTitle()));
        writer.append("</p>");
    }

    @Override
    protected void appendBusInfo(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.unregister.busnotice", new Object[0]));
        writer.append("</p>");
    }
}
