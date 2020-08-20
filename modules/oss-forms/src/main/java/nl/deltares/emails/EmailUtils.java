package nl.deltares.emails;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.activation.*;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class EmailUtils {

    static void sendEmail(String body, String subject, String sendToEmail, String sendFromEmail, Map<String, URL> data) throws Exception {

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");

        Transport transport = MailServiceUtil.getSession().getTransport();
        try {
            Message message = new MimeMessage(MailServiceUtil.getSession());
            message.setFrom(new InternetAddress(sendFromEmail));
            message.setSubject(subject);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html");
            multipart.addBodyPart(messageBodyPart);

            for (String cid : data.keySet()) {

                messageBodyPart = new MimeBodyPart();
                DataSource fds = new URLDataSource(data.get(cid));
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", '<' + cid + '>');
                multipart.addBodyPart(messageBodyPart);

            }

            // put everything together
            message.setContent(multipart);
            transport.connect(
                    PropsUtil.get("mail.session.mail.smtp.host"),
                    Integer.parseInt(PropsUtil.get("mail.session.mail.smtp.port")),
                    PropsUtil.get("mail.session.mail.smtp.user"),
                    PropsUtil.get("mail.session.mail.smtp.password"));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        } finally {
            transport.close();
        }


    }

    static void sendEmail(String body, String subject, String sendToEmail, String sendFromEmail, boolean html) throws Exception {

        InternetAddress fromAddress = new InternetAddress(sendFromEmail);
        InternetAddress toAddress = new InternetAddress(sendToEmail);
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(toAddress);
        mailMessage.setFrom(fromAddress);
        mailMessage.setSubject(subject);
        mailMessage.setBody(body);
        mailMessage.setHTMLFormat(html);
        MailServiceUtil.sendEmail(mailMessage);

    }

    static URL getImage(String imageFile){
        return EmailUtils.class.getClassLoader().getResource("/content/images/" + imageFile);
    }

    static String getTemplate(String templateFile) throws IOException {
        String templatePath = "/content/" + templateFile;

        String body = ContentUtil.get(EmailUtils.class.getClassLoader(), templatePath);
        if (body == null){
            throw new IOException("Could not find template: " + templatePath);
        }
        return body;
    }
}
