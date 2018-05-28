package com.worth.deltares.actions;

import java.util.Enumeration;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.ldap.constants.LegacyLDAPPropsKeys;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
	immediate=true,
	property={
		"path=/portal/update_terms_of_use"
	},
	service = StrutsAction.class
)
public class CustomUpdateTermsOfUseAction extends BaseStrutsAction {
	
	Log _log = LogFactoryUtil.getLog(CustomUpdateTermsOfUseAction.class);

	@Override
	public String execute(StrutsAction originalStrutsAction, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		_log.info("CustomUpdateTermsOfUseActtion.execute.");
		
		long userId = PortalUtil.getUserId(request);

		User user = PortalUtil.getUser(request);
		
		
		
		if(Validator.isNotNull(request.getParameter("DeveloperEULAAgreedOn"))) {
			user.getExpandoBridge().setAttribute(request.getParameter("DeveloperEULAAgreedOn") + 
					"DeveloperEULA", "true");
			
			//update ldap roles
			long companyId = PortalUtil.getCompanyId(request);
			
			long ldapServerId = PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress());
			
			Group community = GroupLocalServiceUtil.getGroup(Long.parseLong(request.getParameter("DeveloperEULAAgreedOn")));
			
			Role role = RoleLocalServiceUtil.getRole(companyId, "Community Developer");
			
			//set Liferay role to be sure
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(userId, community.getGroupId(), new long[] {role.getRoleId()});
			
			_log.debug("ldapServerId: "+ ldapServerId);
			
			String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);
			
			String baseDN = PrefsPropsUtil.getString(companyId, 
					LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix);
			
			
			
			String updateDN = "cn=" + user.getScreenName().toLowerCase() + ",ou=people," + baseDN;
			
			LdapContext ldapContext = PortalLDAPUtil.getContext(
					ldapServerId, companyId);
	
			BasicAttributes memberOf = new BasicAttributes(
					"memberOf",
					"cn=" + "developer" + "s," +
					"ou=" + community.getName().toLowerCase() + "," + 
					"ou=community," + 
					baseDN);
			
			BasicAttributes roleAttrs = new BasicAttributes(true);
			
			BasicAttribute objectClass = new BasicAttribute("objectClass");
			
			objectClass = new BasicAttribute("objectClass");
			objectClass.add("groupOfNames");
			objectClass.add("top");
		
			roleAttrs.put(objectClass);
			roleAttrs.put(new BasicAttribute("cn","developer"+"s"));
			roleAttrs.put(new BasicAttribute("member","cn=ossadmin,ou=people,"+baseDN));
		
			try {
		
				ldapContext.rebind(
						"cn=" + "developer"+"s," +
						"ou=" + community.getName() + "," + 
						"ou=communities," + 
						baseDN,null,roleAttrs);
		
			} catch (Exception e) {
			
				_log.debug("Exception: " + 
						"cn=" + "developer"+"s," +
						"ou=" + community.getName() +
						",ou=communities," + 
						baseDN);

			}
		
			ldapContext.modifyAttributes(updateDN, DirContext.ADD_ATTRIBUTE, memberOf);
			
			_log.debug("groupId: " + request.getParameter("DeveloperEULAAgreedOn"));
		}
		
		Enumeration<String> enumeration = user.getExpandoBridge().getAttributeNames();
		
		boolean allEulasAccepted = true;
		
		while(enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();

			if(key.endsWith("EULA")) {
				try {
					if(user.getExpandoBridge().getAttribute(key).equals("false")){
						allEulasAccepted = false;
						_log.debug("there are more EULAs where that came from");
					}
				} catch (Exception e) {
				}
			}
		}
		
		if(allEulasAccepted) {
			UserServiceUtil.updateAgreedToTermsOfUse(userId, true);
		}

		RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/common/referer_js.jsp");

		requestDispatcher.forward(request, response);
		return originalStrutsAction.execute(originalStrutsAction, request, response);
	}
	@Reference(target = "(osgi.web.symbolicname=com.liferay.portal.settings.web)")
	private volatile ServletContext _servletContext;
	
}
