package nl.deltares.emails;

import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.liferay.mail.kernel.service.MailServiceUtil.getSession;

public class EmailUtils {

    private static final Log LOG = LogFactoryUtil.getLog(EmailUtils.class);

    static void sendEmail(String body, String subject, String sendToEmail, String sendCcEmail, String sendBccEmail,
                          String sendFromEmail, String replyToEmail,  Map<String, URL> data, Map<String, File> attachments) throws Exception {

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");

        final Session session = getSession();
        Transport transport = session.getTransport();
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

                messageBodyPart = new MimeBodyPart();
                DataSource fds = new URLDataSource(data.get(cid));
                messageBodyPart.setDataHandler(new DataHandler(fds));
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

            // put everything together
            message.setContent(multipart);
            transport.connect(
                    getSmtpHost(),
                    getSmtpPort(),
                    getSmtpUser(),
                    getSmtpPassword());
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e){
            LOG.warn(String.format("Failed to send email to %s: %s", sendToEmail, e.getMessage()), e);
        }finally {
            transport.close();
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
