<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final String filterEmail = (String) request.getAttribute("filterEmail");

%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.registration.title">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/registrationTable.jsp" />
    </portlet:renderURL>

    <portlet:actionURL name="filter" var="filterTableURL" />

    <liferay-ui:success key="action-success" message="">
        <liferay-ui:message key="action-success"
                            arguments='<%= SessionMessages.get(liferayPortletRequest, "action-success") %>'/>
    </liferay-ui:success>

    <liferay-ui:error key="action-failed">
        <liferay-ui:message key="action-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "action-failed") %>'/>
    </liferay-ui:error>
    <liferay-ui:error key="filter-failed">
        <liferay-ui:message key="filter-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "filter-failed") %>'/>
    </liferay-ui:error>

    <aui:form action="<%=filterTableURL%>" name="<portlet:namespace />filterForm" >
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.filter.email.label"/></div>
                </aui:col>
                <aui:col width="20">
                    <aui:input name="filterEmail" label="" />
                </aui:col>
                <aui:col width="50"/>
                <aui:col width="20">
                    <aui:button type="submit" value="table.filter.button" />
                    <aui:button type="cancel" onClick="<%= viewURL %>" value="table.filter.clear"/>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form >
        <jsp:useBean id="records" class="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-registration-records")%>'
                                     total="<%=records.size()%>">
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayRegistration"
                    modelVar="entry"
            >
                <liferay-ui:search-container-column-text property="email" name="E-Mail"/>
                <liferay-ui:search-container-column-text property="eventName" name="Event"/>
                <liferay-ui:search-container-column-text property="registrationName" name="Registration"/>
                <liferay-ui:search-container-column-text property="startTime" name="Start (GMT)"/>
                <liferay-ui:search-container-column-text property="endTime" name="End (GMT)"/>

                <%--                <liferay-ui:search-container-column-text property="preferences" name="Prefferences"/>--%>
                <liferay-ui:search-container-column-text name="Actions">
                    <aui:button-row>
                        <portlet:renderURL var="editRegistrationURL">
                            <portlet:param name="mvcPath" value="/editRegistration.jsp" />
                            <portlet:param name="registrationId" value="${entry.getId()}"/>
                            <portlet:param name="filterEmail" value="${entry.getEmail()}"/>
                        </portlet:renderURL>
                        <portlet:actionURL var="deleteRegistrationURL" name="delete">
                            <portlet:param name="registrationId" value="${entry.getId()}"/>
                            <portlet:param name="filterEmail" value="${entry.getEmail()}"/>
                        </portlet:actionURL>
                        <aui:button name="editButton" type="submit" value="Edit" href="<%=editRegistrationURL%>"/>
                        <aui:button name="deleteButton" type="submit" value="Delete" href="<%=deleteRegistrationURL%>" onClick="userConfirm()" />
                   </aui:button-row>
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
    </aui:form>
</aui:fieldset>
<aui:script use="event, node, aui-base">

    userConfirm = function (){
        if (confirm("You are about to delete this registration.\nDo you want to continue?") === false) {
            event.preventDefault();
        }
    }

</aui:script>


