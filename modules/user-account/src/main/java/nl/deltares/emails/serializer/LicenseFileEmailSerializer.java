package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.common.emails.serializer.EmailSerializer;
import nl.deltares.emails.LicenseFilesEmail;

public class LicenseFileEmailSerializer implements EmailSerializer<LicenseFilesEmail> {

    private final String customerName;
    public LicenseFileEmailSerializer(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void serialize(LicenseFilesEmail content, StringBuilder writer) {

        User user = content.getUser();
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "downloadlicenses.email.header", new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</p>");

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "downloadlicenses.email.request", customerName));
        writer.append("</p>");

        writer.append("</br>");

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "downloadlicenses.email.info", customerName));
        writer.append("</p>");
        writer.append("</br>");
        writer.append("</br>");

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "downloadlicenses.email.signature", customerName));
        writer.append("</p>");
    }

}
