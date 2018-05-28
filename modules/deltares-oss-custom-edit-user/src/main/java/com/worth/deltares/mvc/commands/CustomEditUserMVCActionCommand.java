package com.worth.deltares.mvc.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.osgi.service.component.annotations.Component;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.model.AnnouncementsEntryConstants;
import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalServiceUtil;
import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.PhoneNumberExtensionException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserFieldException;
import com.liferay.portal.kernel.exception.UserIdException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserReminderQueryException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.exception.UserSmsException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.ldap.constants.LegacyLDAPPropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;


@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_users_admin_web_portlet_MyOrganizationsPortlet",
		"javax.portlet.name=com_liferay_users_admin_web_portlet_UsersAdminPortlet",
		"mvc.command.name=/users_admin/edit_user",
		"service.ranking:Integer=100"
	},
	service = MVCActionCommand.class
)
public class CustomEditUserMVCActionCommand extends BaseMVCActionCommand{
	
	private static Log _log = LogFactoryUtil.getLog(CustomEditUserMVCActionCommand.class);

	protected User addUser(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = ParamUtil.getBoolean(
			actionRequest, "autoPassword", true);
		String password1 = actionRequest.getParameter("password1");
		String password2 = actionRequest.getParameter("password2");

		String reminderQueryQuestion = ParamUtil.getString(
			actionRequest, "reminderQueryQuestion");

		if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
			reminderQueryQuestion = ParamUtil.getString(
				actionRequest, "reminderQueryCustomQuestion");
		}

		String reminderQueryAnswer = ParamUtil.getString(
			actionRequest, "reminderQueryAnswer");
		boolean autoScreenName = ParamUtil.getBoolean(
			actionRequest, "autoScreenName");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = 0;
		String openId = ParamUtil.getString(actionRequest, "openId");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		String greeting = ParamUtil.getString(actionRequest, "greeting");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		long suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String comments = ParamUtil.getString(actionRequest, "comments");
		String smsSn = ParamUtil.getString(actionRequest, "smsSn");
		String facebookSn = ParamUtil.getString(actionRequest, "facebookSn");
		String jabberSn = ParamUtil.getString(actionRequest, "jabberSn");
		String skypeSn = ParamUtil.getString(actionRequest, "skypeSn");
		String twitterSn = ParamUtil.getString(actionRequest, "twitterSn");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] groupIds = UsersAdminUtil.getGroupIds(actionRequest);
		long[] organizationIds = UsersAdminUtil.getOrganizationIds(
			actionRequest);
		long[] roleIds = UsersAdminUtil.getRoleIds(actionRequest);
		List<UserGroupRole> userGroupRoles = UsersAdminUtil.getUserGroupRoles(
			actionRequest);
		long[] userGroupIds = UsersAdminUtil.getUserGroupIds(actionRequest);
		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);
		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);
		List<AnnouncementsDelivery> announcementsDeliveries =
			getAnnouncementsDeliveries(actionRequest);
		boolean sendEmail = true;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		User user = UserServiceUtil.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			LocaleUtil.fromLanguageId(languageId), firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, addresses, emailAddresses, phones, websites,
			announcementsDeliveries, sendEmail, serviceContext);

		if (!userGroupRoles.isEmpty()) {
			for (UserGroupRole userGroupRole : userGroupRoles) {
				userGroupRole.setUserId(user.getUserId());
			}

			user = UserServiceUtil.updateUser(
				user.getUserId(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false, reminderQueryQuestion,
				reminderQueryAnswer, user.getScreenName(),
				user.getEmailAddress(), facebookId, openId, true, null,
				languageId, timeZoneId, greeting, comments, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
				twitterSn, jobTitle, groupIds, organizationIds, roleIds,
				userGroupRoles, userGroupIds, addresses, emailAddresses, phones,
				websites, announcementsDeliveries, serviceContext);
		}

		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled");
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled");

		SitesUtil.updateLayoutSetPrototypesLinks(
			user.getGroup(), publicLayoutSetPrototypeId,
			privateLayoutSetPrototypeId, publicLayoutSetPrototypeLinkEnabled,
			privateLayoutSetPrototypeLinkEnabled);

		return user;
	}
	
	protected List<AnnouncementsDelivery> getAnnouncementsDeliveries(
		ActionRequest actionRequest) {

		List<AnnouncementsDelivery> announcementsDeliveries = new ArrayList<>();

		for (String type : AnnouncementsEntryConstants.TYPES) {
			boolean email = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Email");
			boolean sms = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Sms");
			boolean website = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Website");

			AnnouncementsDelivery announcementsDelivery =
				new AnnouncementsDeliveryImpl();

			announcementsDelivery.setType(type);
			announcementsDelivery.setEmail(email);
			announcementsDelivery.setSms(sms);
			announcementsDelivery.setWebsite(website);

			announcementsDeliveries.add(announcementsDelivery);
		}

		return announcementsDeliveries;
	}
	
	protected List<AnnouncementsDelivery> getAnnouncementsDeliveries(
			ActionRequest actionRequest, User user)
		throws Exception {

		if (actionRequest.getParameter(
				"announcementsType" + AnnouncementsEntryConstants.TYPES[0] +
					"Email") == null) {

			return AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(
				user.getUserId());
		}

		return getAnnouncementsDeliveries(actionRequest);
	}
	
	protected long getListTypeId(
			PortletRequest portletRequest, String parameterName, String type)
		throws Exception {

		String parameterValue = ParamUtil.getString(
			portletRequest, parameterName);

		ListType listType = ListTypeLocalServiceUtil.addListType(
			parameterValue, type);

		return listType.getListTypeId();
	}
	
	protected User updateLockout(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		UserServiceUtil.updateLockoutById(user.getUserId(), false);

		return user;
	}
	
	protected void deleteUsers(ActionRequest actionRequest) throws Exception {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long[] deleteUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteUserIds"), 0L);

		for (long deleteUserId : deleteUserIds) {
			if (cmd.equals(Constants.DEACTIVATE) ||
				cmd.equals(Constants.RESTORE)) {

				int status = WorkflowConstants.STATUS_APPROVED;

				if (cmd.equals(Constants.DEACTIVATE)) {
					status = WorkflowConstants.STATUS_INACTIVE;
				}

				UserServiceUtil.updateStatus(
					deleteUserId, status, new ServiceContext());
			}
			else {
				UserServiceUtil.deleteUser(deleteUserId);
			}
		}
	}
	@Override
	public void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		_log.info("CustomEditUserMVCActionCommand.doProcessAction");
		if (_log.isDebugEnabled()) {
			_log.debug("Running processAction " + actionRequest.getRemoteUser());
		}	
		
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		
		try {
			User user = null;
			String oldScreenName = StringPool.BLANK;
			String oldLanguageId = StringPool.BLANK;
			
			if (cmd.equals(Constants.ADD)) {
				user = addUser(actionRequest);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.RESTORE)) {

				deleteUsers(actionRequest);
			}
			else if (cmd.equals("deleteRole")) {
				deleteRole(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateUser(actionRequest);

				user = (User)returnValue[0];
				oldScreenName = ((String)returnValue[1]);
				oldLanguageId = ((String)returnValue[2]);
			}
			else if (cmd.equals("unlock")) {
				user = updateLockout(actionRequest);
			}
			
			String redirect = ParamUtil.getString(actionRequest, "redirect");
			
			if (Validator.isNotNull(user)) {
				ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
				if (Validator.isNotNull(oldScreenName)) {

					// This will fix the redirect if the user is on his personal
					// my account page and changes his screen name. A redirect
					// that references the old screen name no longer points to a
					// valid screen name and therefore needs to be updated.

					Group group = user.getGroup();

					if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
						Layout layout = themeDisplay.getLayout();

						String friendlyURLPath = group.getPathFriendlyURL(layout.isPrivateLayout(), themeDisplay);

						String oldPath = friendlyURLPath + StringPool.SLASH + oldScreenName;
						String newPath = friendlyURLPath + StringPool.SLASH + user.getScreenName();

						redirect = StringUtil.replace(redirect, oldPath, newPath);

						redirect = StringUtil.replace(redirect, HttpUtil.encodeURL(oldPath),HttpUtil.encodeURL(newPath));
					}
					
					if (Validator.isNotNull(oldLanguageId) &&themeDisplay.isI18n()) {

						String i18nLanguageId = user.getLanguageId();
						int pos = i18nLanguageId.indexOf(CharPool.UNDERLINE);

						if (pos != -1){
							i18nLanguageId = i18nLanguageId.substring(0, pos);
						}

						String i18nPath = StringPool.SLASH + i18nLanguageId;

						redirect = StringUtil.replace(redirect, themeDisplay.getI18nPath(), i18nPath);
					}
					redirect = HttpUtil.setParameter(redirect, actionResponse.getNamespace() + "p_u_i_d", user.getUserId());
				}
			}
			sendRedirect(actionRequest, actionResponse, redirect);
		} catch (Exception e) {
			String mvcPath = "/edit_user.jsp";

			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
			}
			
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactBirthdayException ||
					 e instanceof ContactNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof MembershipPolicyException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof PhoneNumberExtensionException ||
					 e instanceof RequiredUserException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserFieldException ||
					 e instanceof UserIdException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserReminderQueryException ||
					 e instanceof UserScreenNameException ||
					 e instanceof UserSmsException ||
					 e instanceof WebsiteURLException) {

				if (e instanceof NoSuchListTypeException) {
					NoSuchListTypeException nslte = (NoSuchListTypeException)e;

					Class<?> clazz = e.getClass();

					SessionErrors.add(
						actionRequest, clazz.getName() + nslte.getType());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}
								
				String password1 = actionRequest.getParameter("password1");
				String password2 = actionRequest.getParameter("password2");

				boolean submittedPassword = false;

				if (!Validator.isBlank(password1) || !Validator.isBlank(password2)) {

					submittedPassword = true;
				}
				
				if (e instanceof CompanyMaxUsersException ||
					e instanceof RequiredUserException || submittedPassword) {

					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (submittedPassword) {
						User user = PortalUtil.getSelectedUser(actionRequest);

						redirect = HttpUtil.setParameter(
							redirect, actionResponse.getNamespace() + "p_u_i_d",
							user.getUserId());
					}

					if (Validator.isNotNull(redirect)) {
						sendRedirect(actionRequest, actionResponse, redirect);

						return;
					}
				}
				else {
					throw e;
				}

				actionResponse.setRenderParameter("mvcPath", mvcPath);
			}
		}
	}
	
	protected void deleteRole(ActionRequest actionRequest) throws Exception {
		
		_log.debug("CustomEditUserMVCActionCommand.deleteRole()");
		User user = PortalUtil.getSelectedUser(actionRequest);
		
		long roleId = ParamUtil.getLong(actionRequest, "roleId");
		
		Role role = RoleServiceUtil.getRole(roleId);

		long companyId = PortalUtil.getCompanyId(actionRequest);
		
		boolean isReservedUser = false;
		
		if( (PrefsPropsUtil.getString("deltares.oss.admins", StringPool.BLANK).indexOf(Long.toString(user.getUserId())) >= 0 )){
			isReservedUser = true;
			_log.debug(user.getUserId() + " is reserved, not updating roles to ldap");
		}
		
		if(PrefsPropsUtil.getBoolean(LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED,false) 
			&& !isReservedUser 
			&& StringUtil.endsWith(user.getScreenName(), ".x")) {
			try {
				long ldapServerId = PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress());
			
				LdapContext ldapContext = PortalLDAPUtil.getContext(
						ldapServerId, companyId);
				
				String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);
				
				String baseDN = PrefsPropsUtil.getString(companyId, LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix);
				
				if(role.getType()==1) {
					_log.debug("trying to remove regular role: "+ role.getTitle() + " for user: " + user.getScreenName());
					ldapContext.modifyAttributes("cn="+role.getTitle().toLowerCase()+",ou=liferay,ou=groups,"+baseDN, new ModificationItem[] {
							new ModificationItem(
									DirContext.REMOVE_ATTRIBUTE,
									new BasicAttribute("memberOf", "cn="+user.getScreenName()+",ou=people,"+baseDN))
							});
				} else {
					_log.debug("trying to remove "+role.getTypeLabel()+" role: "+ role.getTitle() + " for user: " + user.getScreenName());
				}
				
			} catch(Exception e){
				_log.error("Exception @ CustomEditUserMVCActionCommand.deleteRole");
				_log.error(e.getLocalizedMessage());
			}
		}
		
		UserServiceUtil.deleteRoleUser(roleId, user.getUserId());
	}
	
	protected Object[] updateUser(ActionRequest actionRequest)
			throws Exception {

			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			User user = PortalUtil.getSelectedUser(actionRequest);

			Contact contact = user.getContact();

			String oldPassword = AdminUtil.getUpdateUserPassword(
				actionRequest, user.getUserId());
			String newPassword1 = ParamUtil.getString(actionRequest, "password1");
			String newPassword2 = ParamUtil.getString(actionRequest, "password2");
			boolean passwordReset = ParamUtil.getBoolean(
				actionRequest, "passwordReset");

			String reminderQueryQuestion = BeanParamUtil.getString(
				user, actionRequest, "reminderQueryQuestion");

			if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
				reminderQueryQuestion = BeanParamUtil.getString(
					user, actionRequest, "reminderQueryCustomQuestion");
			}

			String reminderQueryAnswer = BeanParamUtil.getString(
				user, actionRequest, "reminderQueryAnswer");
			String oldScreenName = user.getScreenName();
			String screenName = BeanParamUtil.getString(
				user, actionRequest, "screenName");
			String emailAddress = BeanParamUtil.getString(
				user, actionRequest, "emailAddress");
			long facebookId = user.getFacebookId();
			String openId = BeanParamUtil.getString(user, actionRequest, "openId");
			String oldLanguageId = user.getLanguageId();
			String languageId = BeanParamUtil.getString(
				user, actionRequest, "languageId");
			String timeZoneId = BeanParamUtil.getString(
				user, actionRequest, "timeZoneId");
			String greeting = BeanParamUtil.getString(
				user, actionRequest, "greeting");
			String firstName = BeanParamUtil.getString(
				user, actionRequest, "firstName");
			String middleName = BeanParamUtil.getString(
				user, actionRequest, "middleName");
			String lastName = BeanParamUtil.getString(
				user, actionRequest, "lastName");
			int prefixId = BeanParamUtil.getInteger(
				contact, actionRequest, "prefixId");
			int suffixId = BeanParamUtil.getInteger(
				contact, actionRequest, "suffixId");
			boolean male = BeanParamUtil.getBoolean(
				user, actionRequest, "male", true);
		
			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();
		
			birthdayCal.setTime(contact.getBirthday());
		
			int birthdayMonth = ParamUtil.getInteger(
				actionRequest, "birthdayMonth", birthdayCal.get(Calendar.MONTH));
			int birthdayDay = ParamUtil.getInteger(
				actionRequest, "birthdayDay", birthdayCal.get(Calendar.DATE));
			int birthdayYear = ParamUtil.getInteger(
				actionRequest, "birthdayYear", birthdayCal.get(Calendar.YEAR));
			String comments = BeanParamUtil.getString(
				user, actionRequest, "comments");
			String smsSn = BeanParamUtil.getString(contact, actionRequest, "smsSn");
			String facebookSn = BeanParamUtil.getString(
				contact, actionRequest, "facebookSn");
			String jabberSn = BeanParamUtil.getString(
				contact, actionRequest, "jabberSn");
			String skypeSn = BeanParamUtil.getString(
				contact, actionRequest, "skypeSn");
			String twitterSn = BeanParamUtil.getString(
				contact, actionRequest, "twitterSn");
			String jobTitle = BeanParamUtil.getString(
				user, actionRequest, "jobTitle");
			long[] groupIds = UsersAdminUtil.getGroupIds(actionRequest);
			long[] organizationIds = UsersAdminUtil.getOrganizationIds(
					actionRequest);
			long[] roleIds = UsersAdminUtil.getRoleIds(actionRequest);
			List<UserGroupRole> userGroupRoles =
				UsersAdminUtil.getUserGroupRoles(actionRequest);
			long[] userGroupIds = UsersAdminUtil.getUserGroupIds(actionRequest);
			List<Address> addresses = UsersAdminUtil.getAddresses(
				actionRequest);
			List<EmailAddress> emailAddresses =
					UsersAdminUtil.getEmailAddresses(actionRequest);
			List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
			List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);
			List<AnnouncementsDelivery> announcementsDeliveries =
				getAnnouncementsDeliveries(actionRequest);
		
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				User.class.getName(), actionRequest);

			long companyId = PortalUtil.getCompanyId(actionRequest);
			
			boolean skipLDAPUpdate = false;
			
			if( (PrefsPropsUtil.getString("deltares.oss.admins", StringPool.BLANK).indexOf(
				Long.toString(user.getUserId())) >= 0 )){
				
				// Check if the user is reserved from updating to ldap (a local administrator).
				
				skipLDAPUpdate = true;
				
				_log.debug("User: " + 
					user.getUserId() + 
					" is reserved, not updating from ldap");
			
			}
			
			if(GetterUtil.getBoolean(LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED)) {
				
				// Check if the user is from an LDAP we can write to, if not we don't even try.
				
				if( (PrefsPropsUtil.getString("deltares.oss.ldaps", StringPool.BLANK).indexOf(
					Long.toString(PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress()))) < 0 )){
					
					skipLDAPUpdate = true;
					
					_log.debug("LDAP Server: " + 
						PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress()) + 
						" is reserved, not updating from ldap");
				}
			
			} else {
				
				// LDAP auth is disabled, so we definatly wont update to LDAP
				
				skipLDAPUpdate = true;
			
			}
			
			if(!skipLDAPUpdate) {
				
				long ldapServerId = PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress());
				
				_log.debug("ldapServerId: "+ ldapServerId);
				
				String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);
				
				String baseDN = PrefsPropsUtil.getString(companyId, LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix);
				
				List<SearchResult> searchResults = new ArrayList<SearchResult>();

				LdapContext ldapContext = PortalLDAPUtil.getContext(
					ldapServerId, companyId);

				byte[] cookie = new byte[0];
				
				PortalLDAPUtil.searchLDAP(companyId, ldapContext, cookie, 100, baseDN,
					"(&(memberOf=*)(cn=" + user.getScreenName() + 
					")(objectClass=inetOrgPerson))",
					new String[] {"memberOf"}, searchResults);
				
				for (SearchResult searchResult : searchResults) {
					
					_log.debug(user.getScreenName() + ": found in " + searchResult.getNameInNamespace());
				
					Attributes memberOfs = new BasicAttributes();
					Attribute memberOf = new BasicAttribute("memberOf");
					memberOfs.put(memberOf);
					
					try {
						ldapContext.modifyAttributes(searchResult.getNameInNamespace(), 
							DirContext.REMOVE_ATTRIBUTE, memberOfs);
					} catch (javax.naming.directory.NoSuchAttributeException e) {
						_log.error(e);
					} catch (InvalidAttributeValueException e) {
						_log.error(e);
					}

				}
				
				String updateDN = "cn=" + user.getScreenName().toLowerCase() + ",ou=people," + baseDN;
				
				for(long roleId : roleIds) {
					
					Role role = RoleLocalServiceUtil.getRole(roleId);
					Attributes memberOf = new BasicAttributes(
						"memberOf",
						"cn=" + role.getName().toLowerCase() + 
						"s,ou=liferay,ou=groups," + 
						baseDN);
					
					Attributes roleAttrs = new BasicAttributes(true);
					
					Attribute objectClass = new BasicAttribute("objectClass");
					objectClass.add("groupOfNames");
					objectClass.add("top");
					
					roleAttrs.put(objectClass);
					roleAttrs.put(new BasicAttribute("cn",role.getName().toLowerCase()+"s"));
					roleAttrs.put(new BasicAttribute("member","cn=ossadmin,ou=people," + baseDN));
					
					try {
					
						ldapContext.rebind(
							"cn=" + role.getName().toLowerCase() + 
							"s,ou=liferay,ou=groups," + 
							baseDN,null,roleAttrs);
					
					} catch (Exception e) {
						
						_log.debug("Exception: " + 
							"cn=" + role.getName().toLowerCase() + 
							"s,ou=liferay,ou=groups," + 
							baseDN);

					}
					
					ldapContext.modifyAttributes(updateDN, DirContext.ADD_ATTRIBUTE, memberOf);
					
					_log.debug(user.getScreenName() + ": " + 
							"cn=" + role.getName().toLowerCase() + 
							"s,ou=liferay,ou=groups," + 
							baseDN);
				
				} 
				
				for(UserGroupRole userGroupRole : userGroupRoles){
					
					String roleName = userGroupRole.getRole().getName().toLowerCase();
					
					if(userGroupRole.getRole().getType()==2) {
						
						if(roleName.startsWith("community ")) {
							roleName = roleName.substring(10);
						}
					
						String communityName = userGroupRole.getGroup().getName().toLowerCase(); 
						
						long groupId = userGroupRole.getGroup().getGroupId();
						
						Attributes memberOf = new BasicAttributes(
								"memberOf",
								"cn=" + roleName + "s," +
								"ou=" + communityName + "," + 
								"ou=community," + 
								baseDN);
						
						Attributes communityAttrs = new BasicAttributes(true);
					
						Attribute objectClass = new BasicAttribute("objectClass");
						objectClass.add("organizationalUnit");
						objectClass.add("top");
					
						Attribute ouSet = new BasicAttribute("ou");
						ouSet.add(communityName);
				    
						communityAttrs.put(objectClass);
						communityAttrs.put(ouSet);
					
						try {
						
							ldapContext.rebind("ou=" + communityName +
									",ou=communities," + 
									baseDN,null,communityAttrs);
					
						} catch (Exception e) {
						
							_log.debug("Exception: " + 
									"ou=" + communityName +
									",ou=communities," + 
									baseDN);
					
						}
						
						//Check if this community has a different EULA for the developer role
						String[] communitiesArr = PrefsPropsUtil.getStringArray("deltares.oss.communities.eula", StringPool.COMMA);
						if(ArrayUtil.contains(communitiesArr, Long.toString(groupId), true) && 
								roleName.equals("developer") &&
								user.getExpandoBridge().getAttribute(communityName + "DeveloperEULA")!="true") {
							
							_log.debug(communityName + " Has a different EULA for developer, " +
									"forcing new acceptance from " + user.getScreenName());
							
							UserLocalServiceUtil.updateAgreedToTermsOfUse(user.getUserId(), false);
							
							try {
								user.getExpandoBridge().addAttribute(groupId + "DeveloperEULA");
							} catch (DuplicateColumnNameException dcne) {
								
							}
							
							user.getExpandoBridge().setAttribute(groupId + "DeveloperEULA", "false");
							
							UnicodeProperties properties = user.getExpandoBridge()
									.getAttributeProperties(groupId + "DeveloperEULA");
							
							properties.setProperty(
									ExpandoColumnConstants.PROPERTY_HIDDEN,
									Boolean.TRUE.toString());
							
							user.getExpandoBridge().setAttributeProperties(groupId + "DeveloperEULA", properties);
									
							//Make sure the role doesn't get added to LDAP before the user has accepted the EULA
							
						} else {
						
							Attributes roleAttrs = new BasicAttributes(true);
						
							objectClass = new BasicAttribute("objectClass");
							objectClass.add("groupOfNames");
							objectClass.add("top");
						
							roleAttrs.put(objectClass);
							roleAttrs.put(new BasicAttribute("cn",roleName+"s"));
							roleAttrs.put(new BasicAttribute("member","cn=ossadmin,ou=people,"+baseDN));
						
							try {
						
								ldapContext.rebind(
										"cn=" + roleName + "s," +
										"ou=" + communityName + "," + 
										"ou=communities," + 
										baseDN,null,roleAttrs);
						
							} catch (Exception e) {
							
								_log.debug("Exception: " + 
										"cn=" + roleName + "s," +
										"ou=" + communityName +
										",ou=communities," + 
										baseDN);

							}
						
							ldapContext.modifyAttributes(updateDN, DirContext.ADD_ATTRIBUTE, memberOf);
						
							_log.debug(user.getScreenName() + ": " + memberOf.toString());
						}
						
					} else {
						
						String organizationName = 
							OrganizationLocalServiceUtil.getOrganization(userGroupRole.getGroup()
								.getOrganizationId()).getName().toLowerCase(); 
						
						if(roleName.startsWith("organization ")) {
							roleName = roleName.substring(13);
						}
						
						Attributes memberOf = new BasicAttributes(
							"memberOf",
							"cn=" + roleName + "s," +
							"ou=" + organizationName + "," + 
							"ou=organization," + 
							baseDN);
						
						Attributes organizationAttrs = new BasicAttributes(true);
						
						Attribute objectClass = new BasicAttribute("objectClass");
						objectClass.add("organizationalUnit");
						objectClass.add("top");
						
						Attribute ouSet = new BasicAttribute("ou");
					    ouSet.add(organizationName);
					    
					    organizationAttrs.put(objectClass);
					    organizationAttrs.put(ouSet);
					    
					    try {
							
							ldapContext.rebind("ou=" + organizationName +
								",ou=organizations," + 
								baseDN,null,organizationAttrs);
						
						} catch (Exception e) {
							
							_log.debug("Exception: " + 
								"ou=" + organizationName +
								",ou=organizations," + 
								baseDN);
						
						}
						
						Attributes roleAttrs = new BasicAttributes(true);
						
						objectClass = new BasicAttribute("objectClass");
						objectClass.add("groupOfNames");
						objectClass.add("top");
						
						roleAttrs.put( objectClass );
						roleAttrs.put( new BasicAttribute( "cn",roleName+"s" ) );
						roleAttrs.put( new BasicAttribute( "member","cn=ossadmin,ou=people," + baseDN ) );
						
						try {
						
							ldapContext.rebind(
								"cn=" + roleName + "s," +
								"ou=" + organizationName + "," + 
								"ou=organizations," + 
								baseDN,null,roleAttrs);
						
						} catch (Exception e) {
							
							_log.debug("Exception: " + 
								"cn=" + roleName + "s," +
								"ou=" + organizationName +
								",ou=organizations," + 
								baseDN);

						}
						
						ldapContext.modifyAttributes(updateDN, DirContext.ADD_ATTRIBUTE, memberOf);
						
						_log.debug(user.getScreenName() + ": " + memberOf.toString());
					
					}
					
				}
				
			}
			
			user = UserServiceUtil.updateUser(
					user.getUserId(), oldPassword, newPassword1, newPassword2, 
					passwordReset, reminderQueryQuestion, reminderQueryAnswer, 
					screenName, emailAddress, facebookId, openId, false, null, languageId, 
					timeZoneId, greeting, comments, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, 
					smsSn, facebookSn, jabberSn, skypeSn, twitterSn, jobTitle, groupIds, organizationIds,
					roleIds, userGroupRoles, userGroupIds, addresses, emailAddresses, 
					phones, websites, announcementsDeliveries, serviceContext);
		
			if (oldScreenName.equals(user.getScreenName())) {
				oldScreenName = StringPool.BLANK;
			}
		
			boolean deletePortrait = ParamUtil.getBoolean(
				actionRequest, "deletePortrait");
		
			if (deletePortrait) {
				UserServiceUtil.deletePortrait(user.getUserId());
			}
		
			if (user.getUserId() == themeDisplay.getUserId()) {
		
				// Reset the locale
		
				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					actionRequest);
				HttpSession session = request.getSession();
		
				session.removeAttribute(Globals.LOCALE_KEY);
		
				// Clear cached portlet responses
		
				PortletSession portletSession = actionRequest.getPortletSession();
		
				InvokerPortletImpl.clearResponses(portletSession);
		
				// Password
		
				if (PropsValues.SESSION_STORE_PASSWORD &&
					Validator.isNotNull(newPassword1)) {
		
					portletSession.setAttribute(
						WebKeys.USER_PASSWORD, newPassword1,
						PortletSession.APPLICATION_SCOPE);
				}
			}
			String portletId = serviceContext.getPortletId();
			String myAccountPortletId = PortletProviderUtil.getPortletId(
				PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
				PortletProvider.Action.VIEW);
			
			Group group = user.getGroup();
			if (!portletId.equals(myAccountPortletId) &&
				GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), group.getGroupId(),
					ActionKeys.UPDATE)) {

				long publicLayoutSetPrototypeId = ParamUtil.getLong(
					actionRequest, "publicLayoutSetPrototypeId");
				long privateLayoutSetPrototypeId = ParamUtil.getLong(
					actionRequest, "privateLayoutSetPrototypeId");
				boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					actionRequest, "publicLayoutSetPrototypeLinkEnabled");
				boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					actionRequest, "privateLayoutSetPrototypeLinkEnabled");

				LayoutSet publicLayoutSet = group.getPublicLayoutSet();
				LayoutSet privateLayoutSet = group.getPrivateLayoutSet();

				if ((publicLayoutSetPrototypeId > 0) ||
					(privateLayoutSetPrototypeId > 0) ||
					(publicLayoutSetPrototypeLinkEnabled !=
						publicLayoutSet.isLayoutSetPrototypeLinkEnabled()) ||
					(privateLayoutSetPrototypeLinkEnabled !=
						privateLayoutSet.isLayoutSetPrototypeLinkEnabled())) {

					SitesUtil.updateLayoutSetPrototypesLinks(
						group, publicLayoutSetPrototypeId,
						privateLayoutSetPrototypeId,
						publicLayoutSetPrototypeLinkEnabled,
						privateLayoutSetPrototypeLinkEnabled);
				}
			}
		
			return new Object[] {user, oldScreenName, oldLanguageId};
		}
}
