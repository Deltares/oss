package com.worth.deltares.mail;

import java.io.IOException;

import javax.mail.internet.InternetAddress;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

public class MailUtil {
	
	private static final Log _log = LogFactoryUtil.getLog(MailUtil.class);

	public static void notifyAdministrator(User user, String organizationName, boolean isOrganizationAdmin, String groups, 
			String userProfileUrl) throws PortalException{
		String subject = "New OSS User Registration (" + user.getFullName() + ")";
		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(MailUtil.class.getClassLoader(), "portlet");
		String adminEmailAddress = configuration.get("deltares.oss.admin.email");
		String body = "";
		try {
			body = StringUtil.read(
					MailUtil.class.getClassLoader(),
					"templates/mail" +
						"/admin_notification.tmpl");
			body = StringUtil.replace(body, new String[]{
					"[$USER_FULL_NAME$]","[$USER_EMAIL$]","[$ORGANIZATION_NAME$]","[$COMMUNITIES$]","[$USER_PROFILE_URL$]"
			}, new String[]{
					user.getFullName(), user.getEmailAddress(), organizationName, groups, userProfileUrl
			});
		} catch (IOException e) {
			_log.info("There was an error processing the template");
			e.printStackTrace();
		}
		
		InternetAddress internetAddress = new InternetAddress();
		internetAddress.setAddress(adminEmailAddress);
		
		sendMail(subject, body, internetAddress, internetAddress);
		
	}
	
	protected static void sendMail(String subject, String body, InternetAddress from, InternetAddress to){
		try {
			_log.debug("Creating mail message...");
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			mailMessage.setSubject(subject);
			mailMessage.setBody(body);
			mailMessage.setFrom(from);
			mailMessage.setTo(to);


		    _log.debug("Sending email...");
		    MailServiceUtil.sendEmail(mailMessage);
	    } catch (Exception e) {
	      _log.error("Error sending email. See debug logs for details.");
	      _log.debug(e);

	    }
	}
}
