package nl.deltares.emails;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EmailUtils {

    private static final Log LOG = LogFactoryUtil.getLog(EmailUtils.class);

    public static String sendTestEmail(User user, String sendToEmail, String source) throws Exception {

        String body = String.format("This is a test email sent by %s at time %s", user.getEmailAddress(), new Date());
        String subject = "Test email sent from site " + source;
        sendEmail(body, subject, sendToEmail, null, null, user.getEmailAddress(), null,
                Collections.singletonMap("debug", true), Collections.emptyMap());

        return getConnectionString(sendToEmail);
    }

    private static String getConnectionString(String sendToEmail) {
        StringBuilder response = new StringBuilder();
        response.append("Connection Settings:\n");
        response.append("Sending to: ").append(sendToEmail).append("\n");
        response.append("SMTP Host: ").append(getSmtpHost()).append("\n");
        response.append("SMTP Port: ").append(getSmtpPort()).append("\n");
        response.append("SMTP User: ").append(getSmtpUser()).append("\n");
        String smtpPassword = getSmtpPassword();
        if (smtpPassword !=null && smtpPassword.length() > 4) {
            response.append("SMTP Password ending with: ...").append(smtpPassword.substring(smtpPassword.length() - 4)).append("\n");
        } else {
            response.append("SMTP Password: ****\n");
        }
        return response.toString();
    }

    static void sendEmail(String body, String subject, String sendToEmail, String sendCcEmail, String sendBccEmail,
                          String sendFromEmail, String replyToEmail,  Map<String, Object> data, Map<String, File> attachments) throws Exception {

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");

        Properties props = new Properties();
        props.put("mail.smtp.host", getSmtpHost());
        props.put("mail.smtp.port", getSmtpPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.sasl.enable", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getSmtpUser(), getSmtpPassword());
            }
        });
        if (data.containsKey("debug")){
            session.setDebug(true);
        }
//https://medium.com/@python-javascript-php-html-css/solving-javax-mail-authenticationfailedexception-in-java-email-applications-1bfb7993889c
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendFromEmail)); // always send from mydeltares@deltares.nl. only email with sufficient privileges.
            InternetAddress replyTo = new InternetAddress(replyToEmail == null ? sendFromEmail : replyToEmail);
            message.setReplyTo(new InternetAddress[] {replyTo});
            message.setSubject(subject);
            message.setRecipients(Message.RecipientType.TO, toInternetAddresses(sendToEmail));
            if (sendCcEmail != null) message.setRecipients(Message.RecipientType.CC, toInternetAddresses(sendCcEmail));
            if (sendBccEmail != null) message.setRecipients(Message.RecipientType.BCC, toInternetAddresses(sendBccEmail)); // reply to academy email

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setHeader("Content-Type", "text/html; charset=UTF-8");
            messageBodyPart.setContent(body, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            for (String cid : data.keySet()) {
                if (cid.equals("debug")) continue;
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(getDataSource(data, cid)));
                messageBodyPart.setHeader("Content-ID", '<' + cid + '>');
                multipart.addBodyPart(messageBodyPart);
            }

            for (String attachmentId : attachments.keySet()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                final File file = attachments.get(attachmentId);
                attachmentPart.attachFile(file);
                attachmentPart.setFileName(attachmentId);
                multipart.addBodyPart(attachmentPart);
            }
            message.setContent(multipart);
            Transport.send(message, message.getAllRecipients());
        } catch (Exception e){
            String connectionString = getConnectionString(sendToEmail);
            String msg = String.format("Failed to send email to %s: %s \n %s", sendToEmail, e.getMessage(), connectionString);
            LOG.warn(msg, e);
            throw new PortalException(msg);
        }

    }

    private static DataSource getDataSource(Map<String, Object> data, String cid) throws PortalException, IOException {
        final Object dataValue = data.get(cid);

        if (dataValue instanceof Long) {
            final DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry((Long) dataValue);
            return new ByteArrayDataSource(dlFileEntry.getContentStream(), dlFileEntry.getMimeType());
        } else if (dataValue instanceof URL){
            return new URLDataSource((URL) dataValue);
        } else {
            throw new UnsupportedDataTypeException(String.format("Unsupported data type for cid %s: %s", cid, dataValue.getClass().getName()));
        }
    }

    private static String getSmtpProtocol() {
        return MailServiceUtil.getSession().getProperties().getProperty("mail.transport.protocol", "smtp");
    }
    private static String getSmtpPassword() {
        final String key = String.format("mail.%s.password", getSmtpProtocol());
        return MailServiceUtil.getSession().getProperties().getProperty(key, "");
    }

    private static String getSmtpUser() {
        final String key = String.format("mail.%s.user", getSmtpProtocol());
        return MailServiceUtil.getSession().getProperties().getProperty(key, "");
    }

    private static int getSmtpPort() {
        final String key = String.format("mail.%s.port", getSmtpProtocol());
        return Integer.parseInt(
                MailServiceUtil.getSession().getProperties().getProperty(key, "587")
        );
    }

    private static String getSmtpHost() {
        final String key = String.format("mail.%s.host", getSmtpProtocol());
        return MailServiceUtil.getSession().getProperties().getProperty(key, "");
    }

    private static Address[] toInternetAddresses(String emailList) {
        String[] emails = emailList.split(";");
        List<InternetAddress> addresses = new ArrayList<>();
        for (String email : emails) {
            if (email.isEmpty()) continue;
            try {
                final InternetAddress internetAddress = new InternetAddress(email);
                addresses.add(internetAddress);
            } catch (AddressException e) {
                LOG.warn(String.format("Failed to parse email address %s: %s", email, e.getMessage()));
            }
        }
        return addresses.toArray(new Address[0]);
    }
}
