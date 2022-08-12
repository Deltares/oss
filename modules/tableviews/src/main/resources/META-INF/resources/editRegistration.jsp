<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.tableview.model.DisplayRegistration" %>
<%@ page import="java.util.List" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%

    final DisplayRegistration displayRegistration = (DisplayRegistration) request.getAttribute("record");
    String registrationId = "";
    String registrationName = "";
    String eventName = "";
    String email = "";
    String preferences = "";
    if (displayRegistration != null) {
        registrationId = String.valueOf(displayRegistration.getId());
        registrationName = displayRegistration.getRegistrationName();
        eventName = displayRegistration.getEventName();
        email = displayRegistration.getEmail();
        preferences = displayRegistration.getPreferences();
    }

%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.registration.edit.title">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/registrationTable.jsp"/>
        <portlet:param name="filterEmail" value="<%=email%>"/>
    </portlet:renderURL>

    <portlet:actionURL name="save" var="saveRegistrationURL">
        <portlet:param name="redirectPage" value="/registrationSuccess.jsp"/>
        <portlet:param name="filterEmail" value="<%=email%>"/>
        <portlet:param name="registrationId" value="<%=registrationId%>"/>
    </portlet:actionURL>

    <liferay-ui:error key="action-failed">
        <liferay-ui:message key="action-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "action-failed") %>'/>
    </liferay-ui:error>

    <aui:form action="<%=saveRegistrationURL%>" name="<portlet:namespace />filterForm">
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.edit.email"/></div>
                </aui:col>
                <aui:col width="80">
                    <aui:input name="email" label="" disabled="true" value="<%=email%>"/>
                </aui:col>
            </aui:row>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.edit.event"/></div>
                </aui:col>
                <aui:col width="80">
                    <aui:input name="event" label="" disabled="true" value="<%=eventName%>"/>
                </aui:col>
            </aui:row>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.edit.registration"/></div>
                </aui:col>
                <aui:col width="80">
                    <aui:input name="registration" label="" disabled="true"
                               value="<%=registrationName%>"/>
                </aui:col>
            </aui:row>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.edit.preferences"/></div>
                </aui:col>
                <aui:col width="80" span="4">
                    <aui:input type="textarea" name="preferences" label=""
                               value="<%=preferences%>" />
                </aui:col>
            </aui:row>
            <aui:row>
                <aui:col width="20">
                    <aui:button type="submit" value="table.edit.save"/>
                    <aui:button type="cancel" onClick="<%= viewURL %>" value="table.edit.cancel" />
                </aui:col>
            </aui:row>

        </aui:fieldset>

    </aui:form>

</aui:fieldset>

