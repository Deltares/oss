package nl.deltares.emails;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
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
        String startTls = PropsUtil.get("mail.session.mail.smtp.starttls.enable");
        session.getProperties().setProperty("mail.smtp.starttls.enable", startTls == null ? "true": startTls);
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
                    PropsUtil.get("mail.session.mail.smtp.host"),
                    Integer.parseInt(PropsUtil.get("mail.session.mail.smtp.port")),
                    PropsUtil.get("mail.session.mail.smtp.user"),
                    PropsUtil.get("mail.session.mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e){
            LOG.warn(String.format("Failed to send email to %s: %s", sendToEmail, e.getMessage()), e);
        }finally {
            transport.close();
        }


    }

    private static Address[] toInternetAddresses(String emailList) throws AddressException {
        String[] emails = emailList.split(";");
        InternetAddress[] addresses = new InternetAddress[emails.length];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = new InternetAddress(emails[i]);
        }
        return addresses;
    }
}
