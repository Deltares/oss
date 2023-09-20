<%@ page import="java.util.Map" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>
<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	Map<String, String> attributes = (Map) renderRequest.getAttribute("attributes");

	String portraitURL = "";
	try {
		portraitURL = user.getPortraitURL(themeDisplay);
	} catch (PortalException e) {
//
	}
%>
<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/userprofile.jsp" />
</portlet:renderURL>

<portlet:actionURL name="saveUserProfile" var="saveUserProfileForm"/>

<portlet:actionURL name="saveUserAvatar" var="saveUserAvatarForm"/>

<portlet:actionURL name="deleteUserAvatar" var="deleteUserAvatarForm"/>

<liferay-ui:success key="update-profile-success" message="">
	<liferay-ui:message key="update.profile.success" arguments="name of registration"/>
</liferay-ui:success>

<liferay-ui:error key="update-profile-failed">
	<liferay-ui:message key="update.profile.failed"
						arguments='<%= SessionErrors.get(liferayPortletRequest, "update-profile-failed") %>'/>
</liferay-ui:error>

<c:if test="${not empty attributes}">
	<c:set var="academicTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.academicTitle.name()) %>"/>
	<c:set var="initials" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.initials.name()) %>"/>
	<c:set var="jobTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.jobTitle.name()) %>"/>
	<c:set var="org_name" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>"/>
	<c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
	<c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
	<c:set var="org_website" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_website.name()) %>"/>
	<c:set var="org_phone" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_phone.name()) %>"/>
	<c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
	<c:set var="country" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()) %>"/>
</c:if>

<aui:row>
	<aui:col width="50">

		<span><liferay-ui:message key="userprofileform.userInfo"/></span>

		<aui:form name="updateUserProfile"  action="<%=saveUserProfileForm%>">
			<aui:fieldset >
				<aui:row>
					<aui:col width="100">
						<aui:input
								name="username"
								label="userprofileform.username"
								value="<%=user.getScreenName()%>"
								disabled="true"
								/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="100">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.email.name() %>"
								label="userprofileform.email"
								value="<%= user.getEmailAddress() %>"
								disabled="true"
						/>

					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"
								label="userprofileform.firstname"
								value="<%= user.getFirstName() %>">
							<aui:validator name="required" />
						</aui:input>
					</aui:col>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.last_name.name() %>"
								label="userprofileform.lastname"
								value="<%= user.getLastName() %>" >
							<aui:validator name="required" />
						</aui:input>

					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.academicTitle.name() %>"
								label="userprofileform.academic.titles"
								value="${academicTitle}"
								cssClass="update-profile"/>
					</aui:col>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.initials.name() %>"
								label="userprofileform.initials"
								value="${initials}"
								cssClass="update-profile"/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="100">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.jobTitle.name() %>"
								label="userprofileform.job.titles"
								value="${jobTitle}" />
					</aui:col>
				</aui:row>
				<aui:button-row>
					<aui:button type="submit" name="updateProfile" value="userprofileform.saveProfile" />
					<aui:button type="submit" name="cancelProfile" value="userprofileform.cancel" href="<%= viewURL %>" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>
	</aui:col>


	<aui:col width="50">
		<span><liferay-ui:message key="userprofileform.userAvatar"/></span>
		<aui:row>
			<aui:col width="100"><img src="<%=portraitURL%>" ></aui:col>
		</aui:row>
		<aui:form action="<%= saveUserAvatarForm %>" enctype="multipart/form-data" >
			<aui:fieldset >
				<aui:row >
					<aui:col width="100">
						<aui:input label="" name="image" type="file" cssClass="button btn-lg btn-primary" />
					</aui:col>
				</aui:row>
				<aui:button-row>
					<aui:button type="submit" name="updateAvatar" value="userprofileform.saveAvatar" />
					<aui:button type="submit" name="deleteAvatar" value="userprofileform.deleteAvatar"
								href="<%=deleteUserAvatarForm%>"/>
					<aui:button type="submit" name="cancelAvatar" value="userprofileform.cancel" href="<%= viewURL %>" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

	</aui:col>
</aui:row>
<aui:row>
	<aui:col width="100">

		<span><liferay-ui:message key="userprofileform.organizationInfo"/></span>

		<aui:form name="updateOrganizationInfo"  action="<%=saveUserProfileForm%>">
			<aui:fieldset >
				<aui:row>
					<aui:col width="100">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"
								label="userprofileform.orgname"
								value="${org_name}"
								cssClass="update-profile"/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="100">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"
								label="userprofileform.orgaddress"
								value="${org_address}"
								cssClass="update-profile"/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"
								label="userprofileform.orgpostcode"
								value="${org_postal}"
								cssClass="update-profile"/>
					</aui:col>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"
								label="userprofileform.orgcity"
								value="${org_city}"
								cssClass="update-profile"/>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="100">
						<aui:select
								name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"
								type="select"
								label="userprofileform.orgcountry"
								value="${country}" >
							<aui:option value="" label ="userprofileform.select.country" />
							<% List<Country> countries = CountryServiceUtil.getCountries(true); %>
							<%    for (Country country : countries) { %>
							<aui:option value="<%=country.getName()%>" label ="<%= country.getName(locale) %>" />
							<% } %>
						</aui:select>
					</aui:col>
				</aui:row>
				<aui:row>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_website.name() %>"
								label="userprofileform.orgwebsite"
								value="${org_website}" />
					</aui:col>
					<aui:col width="50">
						<aui:input
								name="<%= KeycloakUtils.ATTRIBUTES.org_phone.name() %>"
								label="userprofileform.phone"
								value="${org_phone}" />
					</aui:col>
				</aui:row>

				<aui:button-row>
					<aui:button type="submit" name="updateProfile" value="userprofileform.saveProfile" />
					<aui:button type="submit" name="cancelProfile" value="userprofileform.cancel" href="<%= viewURL %>" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

	</aui:col>
</aui:row>