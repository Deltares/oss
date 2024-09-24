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
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.event", null));
        writer.append("</p>");
    }

    public void appendBusInfo(StringBuilder writer, DsdEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.busnotice", content.getRegistrationRequest().getBusTransferUrl() ));
        writer.append("</p>");
    }

    @Override
    protected void appendRemarks(StringBuilder writer, DsdEmail content) {
        String remarks = content.getRegistrationRequest().getRequestParameter("remarks");
        if (remarks == null) return;
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "dsd.email.register.remarks", null ));
        writer.append("<br />");
        writer.append(remarks);
        writer.append("</p>");
    }

}
