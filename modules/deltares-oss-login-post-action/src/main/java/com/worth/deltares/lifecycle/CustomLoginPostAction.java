package com.worth.deltares.lifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.ldap.constants.LegacyLDAPPropsKeys;

/**
 * @author Cesar Isaac Hernandez Lavarreda @ Worth Systems
 */

@Component(
 immediate = true, 
 property = {
	 "key=login.events.post",
	 "service.ranking:Integer=100"
 },
 service = LifecycleAction.class
)
public class CustomLoginPostAction implements LifecycleAction {

	private static Log _log = LogFactoryUtil.getLog(CustomLoginPostAction.class);


	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		
		_log.info("CustomLoginPostAction.processLifecycleEvent");
				
        HttpServletRequest request = lifecycleEvent.getRequest();
        			
		try {
		
			if (_log.isDebugEnabled()) {
				_log.debug("Running " + request.getRemoteUser());
			}
			
			long companyId = PortalUtil.getCompanyId(request);
			
			User user = UserLocalServiceUtil.getUserById(
				PortalUtil.getUserId(request));

			boolean skipLDAPUpdate = false;
			
			if( (PrefsPropsUtil.getString("deltares.oss.admins",StringPool.BLANK).indexOf(
				Long.toString(user.getUserId())) >= 0 )){
				
				skipLDAPUpdate = true;
				
				_log.debug("user: " + 
					user.getUserId() + 
					" is reserved, not updating from ldap");
			
			}
			
			if(PrefsPropsUtil.getBoolean(LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED,false)) {
			
				if( (PrefsPropsUtil.getString("deltares.oss.ldaps", StringPool.BLANK).indexOf(
					Long.toString(PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(),user.getEmailAddress()))) < 0 )){
					
					skipLDAPUpdate = true;
					
					_log.debug("LDAP Server: " + 
						PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress()) + 
						" is reserved, not updating from ldap");
				}
			
			} else {
				skipLDAPUpdate = true;
			}
			
			if(!skipLDAPUpdate) {
				
				long userId = user.getUserId();
				
				try {
		
					long ldapServerId = PortalLDAPUtil.getLdapServerId(companyId, user.getScreenName(), user.getEmailAddress());
					
					_log.debug("ldapServerId: "+ ldapServerId);
					
					byte[] cookie = new byte[0];
					
					RoleLocalServiceUtil.unsetUserRoles(user.getUserId(), user.getRoleIds());
					UserGroupRoleLocalServiceUtil.deleteUserGroupRolesByUserId(user.getUserId());
					
					_log.debug(user.getUserId() + " removing all roles");
					
					LdapContext ldapContext = PortalLDAPUtil.getContext(
						ldapServerId, companyId);
					
					List<SearchResult> searchResults = new ArrayList<SearchResult>();
					
					String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);
					
					String baseDN = PrefsPropsUtil.getString(companyId, LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix, StringPool.BLANK);
					
					PortalLDAPUtil.searchLDAP(companyId, ldapContext, cookie, 100, baseDN,
						"(&(memberOf=*)(objectClass=inetOrgPerson)(cn="+ user.getScreenName() +"))",
						new String[] {"memberOf"}, searchResults);				
					
					for (SearchResult searchResult : searchResults) {
						
						Attributes attributes = ldapContext.getAttributes(((Context) searchResult).getNameInNamespace(), new String[] {"memberOf"});
						NamingEnumeration<? extends Attribute> atts = attributes.getAll();
						
						while(atts.hasMore()) {
						
							NamingEnumeration<?> att = atts.nextElement().getAll();
							
							while(att.hasMore()) {
							
								LdapName dn = new LdapName((String) att.next());
								
								if(dn.size()==6) {
								
									String role = StringUtil.upperCaseFirstLetter(dn.get(dn.size()-1).substring(3));
									
									role = role.substring(0,role.length()-1);
									
									String group = StringUtil.upperCaseFirstLetter(dn.get(dn.size()-2).substring(3));
									
									String type = dn.get(dn.size()-3).substring(3);
									
									if(type.compareTo("groups") == 0 && group.compareTo("Liferay") == 0) {
										
										_log.debug("Setting Regular role: "+ role +" for "+ request.getRemoteUser());
										
										long roleId = 0;
										
										try {
											
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										} catch (NoSuchRoleException e) {
											
											_log.debug("Creating Regular role: "+ role +" for "+ request.getRemoteUser());
											
											RoleLocalServiceUtil.addRole(userId, null, companyId, role, null, null, 1, type, null);
											
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										} 
										
										RoleLocalServiceUtil.addUserRoles(userId, new long[] {roleId});
										
									} else if (type.compareTo("organizations") == 0) {
										
										role = "Organization " + role;
										
										_log.debug("Setting role: "+ role +" for "+ request.getRemoteUser() + " on "+ group);
										
										long roleId = 0;
										
										long groupId = 0;
										
										try {
										
											groupId  = OrganizationLocalServiceUtil.getOrganization(companyId, group).getGroup().getGroupId();
										
										} catch (NoSuchOrganizationException e) {
										
											_log.debug("Organization ("+ group +") does not exist in liferay, creating it");
											
											OrganizationServiceUtil.addOrganization(OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, Group.class.getName(),
													OrganizationConstants.TYPE_ORGANIZATION, 0, 0, ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, 
													StringPool.BLANK, false, new ServiceContext());
		
										}
										
										try {
										
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										} catch (NoSuchRoleException e)  {
											
											_log.debug("Creating role: "+ role +" for "+ request.getRemoteUser());
										
											RoleLocalServiceUtil.addRole(userId, null, companyId, role, null, null, 3, null, null);
											
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										} 
										
										UserGroupRoleLocalServiceUtil.addUserGroupRoles(userId, groupId, new long[] {roleId});
										
									} else if (type.compareTo("communities") == 0) {
										
										role = "Community " + role;
										
										_log.debug("Setting role: "+ role +" for "+ request.getRemoteUser() + " on "+ group);
										
										long roleId = 0;
										
										long groupId = 0;
										Map<Locale, String> nameMap = new HashMap<>();

										nameMap.put(LocaleUtil.getDefault(), group);

										Map<Locale, String> descriptionMap = new HashMap<>();
										
										try {
											
											groupId = GroupLocalServiceUtil.getGroup(companyId, group).getGroupId();
										
										} catch (NoSuchGroupException e) {
										
											_log.debug("Community ("+ group +") does not exist in liferay, creating it");
											
											GroupLocalServiceUtil.addGroup(userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, Group.class.getName(), CounterLocalServiceUtil.increment(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
													nameMap, descriptionMap, GroupConstants.TYPE_SITE_PRIVATE, false, GroupConstants.TYPE_SITE_RESTRICTED, FriendlyURLNormalizerUtil.normalize("/" + group), 
													false, true, new ServiceContext());
					
										}
										
										try {
											
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										} catch (NoSuchRoleException e)  { 
											
											_log.debug("Creating role: "+ role +" for "+ request.getRemoteUser());
										
											RoleLocalServiceUtil.addRole(userId, null, companyId, role, null, null, 2, null, null);
											
											roleId = RoleLocalServiceUtil.getRole(companyId, role).getRoleId();
										
										}
										
										UserGroupRoleLocalServiceUtil.addUserGroupRoles(userId, groupId, new long[] { roleId });
									}
								}
							}
						}
					}
				} catch(Exception e) {
					_log.debug("LDAP disabled, nothing to do");
				}				
			}
		
		} catch (Exception e) {
			throw new ActionException(e);
		}	
	}
}