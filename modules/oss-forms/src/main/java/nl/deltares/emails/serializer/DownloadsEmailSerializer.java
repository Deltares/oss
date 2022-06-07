package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.model.BillingInfo;
import nl.deltares.model.DownloadRequest;
import nl.deltares.emails.DownloadEmail;
import nl.deltares.portal.model.impl.Download;

import java.util.List;

public class DownloadsEmailSerializer implements EmailSerializer<DownloadEmail> {

    @SuppressWarnings("RedundantThrows")
    @Override
    public void serialize(DownloadEmail content, StringBuilder writer) throws Exception {

        DownloadRequest request = content.getDownloadRequest();

        writer.append("<img src=\"cid:banner\" />");
        User user = content.getUser();
        writer.append("<h2>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.header", new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</h2>");

        appendDownloadAction(writer, content);

        writer.append("<table style=\"width: 900px;\">");
        List<Download> downloads = request.getDownloads();
        boolean paymentRequired = false;
        for (Download download : downloads) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            appendDownload(writer, content, download);
            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            if (download.isBillingRequired()) {
                paymentRequired = true;
            }
        }
        writer.append("</table>");

        if (paymentRequired) {
            appendBillingInfo(writer, content);
        }

        appendNotice(writer, content);

        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");
    }

    public void appendBillingInfo(StringBuilder writer, DownloadEmail content){
        final DownloadRequest downloadRequest = content.getDownloadRequest();
        final BillingInfo billingInfo = downloadRequest.getBillingInfo();

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.billing.info", billingInfo.getEmail()));
        writer.append("</p>");

        writer.append("<table style=\"width: 900px;\">");
        writer.append("<tr><td><hr></td><td><hr></td></tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.email", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getEmail());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.name", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getName());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.address", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getAddress());
        writer.append("</br>");
        writer.append(billingInfo.getPostal());
        writer.append(", ");
        writer.append(billingInfo.getCity());
        writer.append("</br>");
        writer.append(billingInfo.getCountry());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.method", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getPreference());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.reference", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getReference());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.vat", null)).append("</td>");
        writer.append("<td>");
        writer.append(billingInfo.getVat());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr><td><hr></td><td><hr></td></tr>");
        writer.append("</table>");
    }

    public void appendNotice(StringBuilder writer, DownloadEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.notice", content.getUser().getEmailAddress()));
        writer.append("</p>");
    }

    private void appendDownloadAction(StringBuilder writer, DownloadEmail content) {
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.request", null));
        writer.append("</p>");
    }

    private void appendDownload(StringBuilder writer, DownloadEmail content, Download download) {
        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.filename", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.getFileName());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.filetype", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.getFileTypeName());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.filesize", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.getFileSize());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.paymentRequired", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.isBillingRequired() ? "Yes" : "No");
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.linkSentSeparately", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.isAutomaticLinkCreation() ? "No" : "Yes");
        writer.append("</td>");
        writer.append("</tr>");
    }
}
