package nl.deltares.emails.serializer;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import nl.deltares.model.DownloadRequest;
import nl.deltares.emails.DownloadEmail;
import nl.deltares.portal.constants.BillingConstants;
import nl.deltares.portal.constants.LicenseConstants;
import nl.deltares.portal.constants.OrganizationConstants;
import nl.deltares.portal.model.impl.Download;

import java.util.List;

public class DownloadsEmailSerializer implements EmailSerializer<DownloadEmail> {

    @SuppressWarnings("RedundantThrows")
    @Override
    public void serialize(DownloadEmail content, StringBuilder writer) throws Exception {

        DownloadRequest request = content.getDownloadRequest();

        User user = content.getUser();
        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.header", new Object[]{user.getFirstName(), user.getLastName()}));
        writer.append("</p>");

        appendDownloadAction(writer, content);

        writer.append("<table style=\"width: 900px;\">");
        List<Download> downloads = request.getDownloads();
        boolean paymentRequired = false;
        boolean licenseRequired = false;
        for (Download download : downloads) {

            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            appendDownload(writer, content, download);
            writer.append("<tr><td><hr></td><td><hr></td></tr>");
            if (download.isBillingRequired()) {
                paymentRequired = true;
            }
            if (download.isLockTypeRequired() || download.isLicenseTypeRequired()){
                licenseRequired = true;
            }
        }

        writer.append("</table>");
        appendNotice(writer, content);

        appendUserInfo(writer, content);

        if (paymentRequired) {
            appendBillingInfo(writer, content);
        }

        if (licenseRequired){
            appendLicenseInfo(writer, content);
        }
        writer.append("</br>");
        writer.append("</br>");
        writer.append("</br>");
    }

    private void appendLicenseInfo(StringBuilder writer, DownloadEmail content) {

        final DownloadRequest downloadRequest = content.getDownloadRequest();

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.license.info", null));
        writer.append("</p>");

        writer.append("<table style=\"width: 900px;\">");
        writer.append("<tr><td><hr></td><td><hr></td></tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.license.lockType", null)).append("</td>");
        writer.append("<td>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.license.lockType." + downloadRequest.getRequestParameter(LicenseConstants.LOCK_TYPE), null));
        writer.append("</td>");
        writer.append("</tr>");

        final String dongleNumber = downloadRequest.getRequestParameter(LicenseConstants.DONGLE_NUMBER);
        if (dongleNumber != null && !dongleNumber.isEmpty()){
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.license.lockType.dongle", null)).append("</td>");
            writer.append("<td>");
            writer.append(dongleNumber);
            writer.append("</td>");
            writer.append("</td>");
        }

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.license.licenseType", null)).append("</td>");
        writer.append("<td>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.license.licenseType." + downloadRequest.getRequestParameter(LicenseConstants.LICENSE_TYPE), null));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr><td><hr></td><td><hr></td></tr>");
        writer.append("</table>");
    }

    private void appendUserInfo(StringBuilder writer, DownloadEmail content) {
        final User user = content.getUser();

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.user.info", null));
        writer.append("</p>");

        writer.append("<table style=\"width: 900px;\">");
        writer.append("<tr><td><hr></td><td><hr></td></tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.email", null)).append("</td>");
        writer.append("<td>");
        writer.append(user.getEmailAddress());
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.name", null)).append("</td>");
        writer.append("<td>");
        writer.append(user.getFirstName());
        writer.append(' ');
        writer.append(user.getLastName());
        writer.append("</td>");
        writer.append("</tr>");

        final DownloadRequest downloadRequest = content.getDownloadRequest();

        if (downloadRequest.isUserInfoRequired()) {
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.org_name", null)).append("</td>");
            writer.append("<td>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_NAME, ""));
            writer.append("</td>");
            writer.append("</tr>");

            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.org_address", null)).append("</td>");
            writer.append("<td>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_STREET, ""));
            writer.append("</br>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_POSTAL, ""));
            writer.append(", ");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_CITY, ""));
            writer.append("</br>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_EXTERNAL_REFERENCE_CODE, ""));
            writer.append("</td>");
            writer.append("</tr>");

            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.org_phone", null)).append("</td>");
            writer.append("<td>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_PHONE, ""));
            writer.append("</td>");
            writer.append("</tr>");

            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.user.org_website", null)).append("</td>");
            writer.append("<td>");
            writer.append(downloadRequest.getRequestParameterOrDefault(OrganizationConstants.ORG_WEBSITE, ""));
            writer.append("</td>");
            writer.append("</tr>");
        }
        writer.append("<tr><td><hr></td><td><hr></td></tr>");
        writer.append("</table>");
    }

    public void appendBillingInfo(StringBuilder writer, DownloadEmail content){
        final DownloadRequest downloadRequest = content.getDownloadRequest();

        writer.append("<p>");
        writer.append(LanguageUtil.format(content.getBundle(), "download.email.billing.info", null));
        writer.append("</p>");

        writer.append("<table style=\"width: 900px;\">");
        writer.append("<tr><td><hr></td><td><hr></td></tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.email", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.EMAIL, ""));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.name", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_NAME, ""));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.address", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_STREET, ""));
        writer.append("</br>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_POSTAL, ""));
        writer.append(", ");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_CITY, ""));
        writer.append("</br>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_EXTERNAL_REFERENCE_CODE, ""));
        writer.append("</td>");
        writer.append("</tr>");
        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.phone", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_PHONE,""));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.method", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.PAYMENT_METHOD, ""));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.reference", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.PAYMENT_REFERENCE, ""));
        writer.append("</td>");
        writer.append("</tr>");

        writer.append("<tr>");
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.billing.vat", null)).append("</td>");
        writer.append("<td>");
        writer.append(downloadRequest.getRequestParameterOrDefault(BillingConstants.ORG_VAT, ""));
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
        writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.link", null)).append("</td>");
        writer.append("<td>");
        final String shareLink = content.getDownloadRequest().getShareLink(download.getArticleId());
        if (shareLink == null){
            writer.append(LanguageUtil.format(content.getBundle(), "download.email.linkSentSeparately", null));
        } else {
            writer.append("<a href=\"").append(shareLink).append("\">open link</a>");
        }
        writer.append("</td>");
        writer.append("</tr>");

        final String sharePassword = content.getDownloadRequest().getSharePassword(download.getArticleId());
        if (sharePassword != null){
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.linkPassword", null)).append("</td>");
            writer.append("<td>");
            writer.append(sharePassword);
            writer.append("</td>");
            writer.append("</tr>");
        }

        final String licenseDownloadLink = content.getDownloadRequest().getLicenseDownloadLink(download.getArticleId());
        if (licenseDownloadLink != null){
            writer.append("<tr>");
            writer.append("<td class=\"type\">").append(LanguageUtil.format(content.getBundle(), "download.email.licenseDownload", null)).append("</td>");
            writer.append("<td>");
            writer.append("<a href=\"").append(licenseDownloadLink).append("\">").append("download link").append("</a>");
            writer.append("</td>");
            writer.append("</tr>");
        }
    }
}
