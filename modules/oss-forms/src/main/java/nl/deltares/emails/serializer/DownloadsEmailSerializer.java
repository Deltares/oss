package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
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
        for (Download download : downloads) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            appendDownload(writer, content, download);
            writer.append("<tr><td><hr></td><td><hr></td></tr>");

        }
        writer.append("</table>");

        appendNotice(writer, content);

        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");
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
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.filesize", null)).append("</td>");
        writer.append("<td>");
        writer.append(download.isBillingRequired() ? "Yes" : "No");
        writer.append("</td>");
        writer.append("</tr>");

    }
}
