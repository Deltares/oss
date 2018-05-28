package com.worth.deltares.mvc.commands;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

import com.liferay.login.web.internal.portlet.util.LoginUtil;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.DuplicateOpenIdException;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.OrganizationParentException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.RequiredFieldException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.exception.TermsOfUseException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserIdException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.exception.UserSmsException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.worth.deltares.mail.MailUtil;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
	property = {
		"javax.portlet.name=com_liferay_login_web_portlet_FastLoginPortlet",
		"javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet",
		"mvc.command.name=/login/create_account",
		"service.ranking:Integer=100"
	},
	service = MVCActionCommand.class
)
public class CustomCreateAccountMVCActionCommand extends BaseMVCActionCommand {

	private static final Log _log = LogFactoryUtil.getLog(
			CustomCreateAccountMVCActionCommand.class);
	
	@Override
	public void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		try {
			if (cmd.equals(Constants.ADD)) {
				if (PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT) {
					CaptchaUtil.check(actionRequest);
				}
				addUser(actionRequest, actionResponse);
			}
		}catch (Exception e) {
			if (e instanceof
					UserEmailAddressException.MustNotBeDuplicate ||
				e instanceof UserScreenNameException.MustNotBeDuplicate) {

				String emailAddress = ParamUtil.getString(
					actionRequest, "emailAddress");

				User user = UserLocalServiceUtil.fetchUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if ((user == null) ||
					(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					actionResponse.setRenderParameter(
						"mvcPath", "/update_account.jsp");
				}
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof CaptchaConfigurationException ||
					 e instanceof CaptchaTextException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactBirthdayException ||
					 e instanceof ContactNameException ||
					 e instanceof DuplicateOpenIdException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchOrganizationException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof OrganizationParentException ||
					 e instanceof PhoneNumberException ||
					 e instanceof RequiredFieldException ||
					 e instanceof RequiredUserException ||
					 e instanceof TermsOfUseException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserIdException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserScreenNameException ||
					 e instanceof UserSmsException ||
					 e instanceof WebsiteURLException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				_log.error(e.getLocalizedMessage());
				throw new PortletException(e);
			}
		}
	}
	
	protected void addUser(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		_log.info("CustomCreateAccountMVCActionCommand.addUser");
		HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();
		
		List<Long> groupIdList = new ArrayList<Long>();
		
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()) {
			
			String parameterName = parameterNames.nextElement();
			if(parameterName.startsWith("communityId_")) {
				if(ParamUtil.getLong(request, parameterName) > 0) {
					groupIdList.add(Long.parseLong(parameterName.replace("Checkbox", "").split("_")[1]));
				}
			}
		}
		
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true; /* isAutoScreenName(); */
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(actionRequest, "emailAddress");
		long facebookId = ParamUtil.getLong(actionRequest, "facebookId");
		String openId = ParamUtil.getString(actionRequest, "openId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		int prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		int suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.get(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		
		
		long[] groupIds = ArrayUtil.toLongArray(groupIdList);
		long[] roleIds = null;
		long[] userGroupIds = null;
		long[] organizationIds = null;

		boolean sendEmail = true;
		
        /* Deze check wordt niet uitgevoerd om de een of andere reden... */
        if (Validator.isNotNull(emailAddress) && StringUtil.endsWith(emailAddress, "@deltares.nl")) {
            throw new EmailAddressException("Deltares gebruikers hoeven zich niet aan te melden, maar kunnen meteen inloggen met hun gebruikersnaam en wachtwoord.");
        }
        
        if(groupIds.length<1) {
        	throw new RequiredFieldException("", "");
        }

		String organization = ParamUtil.getString(actionRequest,"organization");
		boolean isOrganizationAdmin = ParamUtil.getBoolean(actionRequest, "orgAdmin");
	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), actionRequest);

		if (GetterUtil.getBoolean(PropsUtil.get(PropsKeys.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD))) {
			autoPassword = false;

			password1 = ParamUtil.getString(actionRequest, "password1");
			password2 = ParamUtil.getString(actionRequest, "password2");
		}

		boolean openIdPending = false;

		Boolean openIdLoginPending = (Boolean)session.getAttribute(
			WebKeys.OPEN_ID_LOGIN_PENDING);

		if ((openIdLoginPending != null) && openIdLoginPending  && Validator.isNotNull(openId)) {
			openIdPending = true;
		}


		User user = UserLocalServiceUtil.addUser(0l,
			company.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);
		
		List<Group> groups = GroupLocalServiceUtil.getGroups(groupIds);
		
		String groupsString = "";
		for(Group group : groups) {
			if(!groupsString.contains(group.getName() + ", ")) {
				groupsString = groupsString + group.getName(themeDisplay.getLocale()) + ", ";
			}
		}
				
		/*
		 * Notify administrator - START
		 */
		//Email administrator(s)
		PortletRequest portletRequest = (PortletRequest)request.getAttribute("javax.portlet.request");
		
		PortletURL portletURL = PortletURLFactoryUtil.create(portletRequest, "com_liferay_users_admin_web_portlet_UsersAdminPortlet",
				PortalUtil.getControlPanelPlid(portletRequest), PortletRequest.RENDER_PHASE);
		portletURL.setParameter(
				"mvcRenderCommandName", "/users_admin/edit_user");
		portletURL.setParameter("p_u_i_d", String.valueOf(user.getUserId()));
		
		MailUtil.notifyAdministrator(user, organization, isOrganizationAdmin, groupsString, portletURL.toString());
		
		/*
		 * Notify administrator - END
		 */
		
			
		if (openIdPending) {
			session.setAttribute(WebKeys.OPEN_ID_LOGIN, user.getUserId());

			session.removeAttribute(WebKeys.OPEN_ID_LOGIN_PENDING);
		}
		else {

			SessionMessages.add(request, "user_added", user.getEmailAddress());
		}

		sendRedirect(actionRequest, actionResponse, themeDisplay, user, user.getPasswordUnencrypted());
	}
	
	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ThemeDisplay themeDisplay, User user, String password)
		throws Exception {

		String login = null;

		Company company = themeDisplay.getCompany();

		String authType = company.getAuthType();

		if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(user.getUserId());
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = user.getScreenName();
		}
		else {
			login = user.getEmailAddress();
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			AuthenticatedSessionManagerUtil.login(
				request, response, login, password, false, null);
		}
		else {
			PortletURL loginURL = LoginUtil.getLoginURL(
				request, themeDisplay.getPlid());

			loginURL.setParameter("login", login);

			redirect = loginURL.toString();
		}

		actionResponse.sendRedirect(redirect);
	}
}
