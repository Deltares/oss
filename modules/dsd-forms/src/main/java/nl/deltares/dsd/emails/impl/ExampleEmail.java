package nl.deltares.dsd.emails.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.dsd.emails.EmailUtils;

import javax.mail.internet.InternetAddress;

public class ExampleEmail {

    public void sendEmail() throws Exception{

        String body = EmailUtils.readTemplate("/content/example.tmpl");

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;


        body = StringUtil.replace(body, new String[] { "[$NAME$]","[$RESULT$]","[$PERCENTAGE$]","[$EXAM$]" }, new String[] { "Ravi", "CONGRATULATION" ,"80%" , "CCLP"});
        fromAddress = new InternetAddress("dsd@deltares.nl");
        toAddress = new InternetAddress("erik.derooij@deltares.nl");
        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(toAddress);
        mailMessage.setFrom(fromAddress);
        mailMessage.setSubject("Send mail by Using Tempelate");
        mailMessage.setBody(body);
        mailMessage.setHTMLFormat(true);
        MailServiceUtil.sendEmail(mailMessage);

    }

//    public void setEmailWithImgExample(){
//
//        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
//        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
//        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
//        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
//        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
//        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
//
//        try {
//
//            Transport transport = MailServiceUtil.getSession().getTransport();
//
//            Message message = new MimeMessage(MailServiceUtil.getSession());
//            message.setFrom(new InternetAddress("no-reply@test.com"));
//            message.setSubject("send email with Java");
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress("joe@test.com"));
//
//            MimeMultipart multipart = new MimeMultipart("related");
//            BodyPart messageBodyPart = new MimeBodyPart();
//
//            String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
//            messageBodyPart.setContent(htmlText, "text/html");
//            multipart.addBodyPart(messageBodyPart);
//
//
//            messageBodyPart = new MimeBodyPart();
//            DataSource fds = new FileDataSource("D:\\temp\\image\\newJob.jpg");
//            messageBodyPart.setDataHandler(new DataHandler(fds));
//            messageBodyPart.setHeader("Content-ID","<image>");
//            multipart.addBodyPart(messageBodyPart);
//
//            // put everything together
//            message.setContent(multipart);
//            transport.connect();
//            transport.sendMessage(message,
//                    message.getRecipients(Message.RecipientType.TO));
//            transport.close();
//
//            System.out.println("Send mail using Java ");
//        } catch (Exception e) {
//            //todo
//        }
//    }
}
