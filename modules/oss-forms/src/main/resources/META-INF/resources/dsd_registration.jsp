<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<portlet:renderURL var="addRegistrationURL">
	<portlet:param name="mvcPath" value="/add_dsd_registration.jsp"></portlet:param>
</portlet:renderURL>

<portlet:renderURL var="delRegistrationURL">
	<portlet:param name="mvcPath" value="/del_dsd_registration.jsp"></portlet:param>
</portlet:renderURL>

<liferay-ui:success key="registration-success" message="">
    <liferay-ui:message key="registration-success" arguments="<%= new String[]{user.getEmailAddress(), "todo"} %>" />
</liferay-ui:success>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>' />
</liferay-ui:error>

<aui:button-row>
    <%	if (isRegistered) { %>
    <aui:button onClick="<%= delRegistrationURL.toString() %>" value="registrationform.unregister" disabled="<%= user.isDefaultUser() %>"></aui:button>
    <%	} else { %>
    <aui:button onClick="<%= addRegistrationURL.toString() %>" value="registrationform.register" disabled="<%= user.isDefaultUser() %>"></aui:button>
    <% 	} %>
</aui:button-row>
