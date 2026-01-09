package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import nl.deltares.emails.RegistrationEmail;

public class RegisterEmailSerializer extends AbsRegisterEmailSerializer {

    @Override
    String getEventTitle() {
        return "dsd.email.register.event";
    }

    @Override
    void appendNotice(StringBuilder writer, RegistrationEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.notice", content.getSiteUrl()));
        writer.append("</p>");
    }
}
