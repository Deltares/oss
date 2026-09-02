<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.dao.search.RowChecker" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="java.util.Map" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
    final String filterEmailValue = (String) request.getAttribute("filterEmailValue");
    final String filterEventValue = (String) request.getAttribute("filterEventValue");
    final String filterRegistrationValue = (String) request.getAttribute("filterRegistrationValue");
    final Map<Long, String> eventTitles = (Map) request.getAttribute("eventTitles");
    final Map<Long, String> registrationTitles = (Map) request.getAttribute("registrationTitles");

%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.registration.title" collapsible="true">

    <liferay-portlet:renderURL varImpl="iteratorURL">
        <portlet:param name="filterEmailValue" value="<%=filterEmailValue%>"/>
        <portlet:param name="filterEventValue" value="<%=filterEventValue%>"/>
        <portlet:param name="filterRegistrationValue" value="<%=filterRegistrationValue%>"/>
    </liferay-portlet:renderURL>

    <portlet:actionURL name="filterEmail" var="filterEmailURL"/>
    <portlet:actionURL name="filterSelections" var="filterSelectionURL"/>

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

    <aui:form action="<%=filterEmailURL%>" name="filterEmailForm">
        <!-- Hidden field to indicate which action was triggered. The id is namespaced for uniqueness on the page. -->
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.filter.email.label"/></div>
                </aui:col>
                <aui:col width="70">
                    <aui:input name="filterEmailValue" label="" value="<%=filterEmailValue%>"/>
                </aui:col>
                <aui:col width="10">
                    <aui:button-row>
                        <aui:button type="submit" value="table.filter.button" cssClass="float-right" />
                    </aui:button-row>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <aui:form action="<%=filterSelectionURL%>" name="filterSelectionForm">
        <!-- Hidden field to indicate which action was triggered. The id is namespaced for uniqueness on the page. -->
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.filter.selection.label"/></div>
                </aui:col>
                <aui:col width="40">
                    <aui:select name="filterEventValue" label="Event" value="<%=filterEventValue%>" onChange="submit()">
                        <aui:option value="0">Select...</aui:option>
                        <%
                            for (Map.Entry<Long, String> eventInfo : eventTitles.entrySet()) {
                        %>
                        <aui:option value="<%=eventInfo.getKey()%>" label="<%=eventInfo.getValue()%>"/>
                        <%
                            }
                        %>
                    </aui:select>
                </aui:col>
                <aui:col width="40">
                    <aui:select name="filterRegistrationValue" label="Registration" value="<%=filterRegistrationValue%>" onChange="submit()" >
                        <aui:option value="0">Select...</aui:option>
                        <%
                            for (Map.Entry<Long, String> registrationInfo : registrationTitles.entrySet()) {
                        %>
                        <aui:option value="<%=registrationInfo.getKey()%>" label="<%=registrationInfo.getValue()%>"/>
                        <%
                            }
                        %>
                    </aui:select>
                </aui:col>
            </aui:row>

        </aui:fieldset>
    </aui:form>
    <aui:button-row>
        <%--    Don't pass filter values so filter fields will be emptied.--%>
        <portlet:renderURL var="clearFilterURL">
            <portlet:param name="mvcPath" value="/registrationTable.jsp"/>
        </portlet:renderURL>
        <aui:button type="submit" value="table.filter.clear" href="<%=clearFilterURL%>"/>
    </aui:button-row>
    <hr>
    <aui:form>
        <jsp:useBean id="records" type="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25"
                                     emptyResultsMessage='<%=LanguageUtil.get(locale, "no-registration-records")%>'
                                     total="<%=count%>" rowChecker="<%= new RowChecker(renderResponse) %>">
            <liferay-ui:search-container-results results="<%= records %>"/>

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayRegistration"
                    modelVar="entry"
                    keyProperty="recordId"
            >
                <liferay-ui:search-container-column-text property="eventName" name="Event" orderable="true"
                                                         orderableProperty="eventName"/>
                <liferay-ui:search-container-column-text property="registrationName" name="Registration"
                                                         orderable="true" orderableProperty="registrationName"/>
                <liferay-ui:search-container-column-text property="email" name="E-Mail" orderable="true"
                                                         orderableProperty="email"/>
                <liferay-ui:search-container-column-text property="startTime" name="Start (GMT)" orderable="true"
                                                         orderableProperty="startTime"/>
                <liferay-ui:search-container-column-text property="endTime" name="End (GMT)" orderable="true"
                                                         orderableProperty="endTime"/>
                <liferay-ui:search-container-column-text name="Actions">
                    <aui:button-row>
                        <portlet:renderURL var="editRegistrationURL">
                            <portlet:param name="mvcPath" value="/editRegistration.jsp"/>
                            <portlet:param name="recordId" value="${entry.getRecordId()}"/>
                            <portlet:param name="editEmailValue" value="${entry.getEmail()}"/>
                            <portlet:param name="filterEmailValue" value="<%=filterEmailValue%>"/>
                            <portlet:param name="filterEventValue" value="<%=filterEventValue%>"/>
                            <portlet:param name="filterRegistrationValue" value="<%=filterRegistrationValue%>"/>
                        </portlet:renderURL>
                        <portlet:actionURL var="deleteRegistrationURL" name="delete">
                            <portlet:param name="recordId" value="${entry.getRecordId()}"/>
                            <portlet:param name="filterEmailValue" value="<%=filterEmailValue%>"/>
                            <portlet:param name="filterEventValue" value="<%=filterEventValue%>"/>
                            <portlet:param name="filterRegistrationValue" value="<%=filterRegistrationValue%>"/>
                        </portlet:actionURL>

                        <aui:button name="editButton" type="submit" value="Edit" href="<%=editRegistrationURL%>"/>
<%--                        <aui:button name="deleteButton" type="submit" cssClass="deleteButton" value="Delete"--%>
<%--                                    href="<%=deleteRegistrationURL%>"/>--%>
                    </aui:button-row>
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
        <aui:button-row>
            <aui:button name="exportResultsButton" type="submit" value="Export"/>
            <aui:button name="deleteSelectedButton" type="submit" value="Delete selected"/>
        </aui:button-row>
    </aui:form>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
        </aui:col>
    </aui:row>
</aui:fieldset>
<aui:script use="event, io, aui-io-request, node, aui-base, aui-progressbar">


    let exportResultsButton = document.getElementById('<portlet:namespace/>exportResultsButton');
    exportResultsButton.onclick = function(event){
        event.preventDefault();
        TableFormsUtil.exportResults("<portlet:resourceURL/>", "<portlet:namespace/>", "export-registrations.csv")
    };

    let deleteSelectedButton = document.getElementById('<portlet:namespace/>deleteSelectedButton');
    deleteSelectedButton.onclick = function(event){
        event.preventDefault();
        TableFormsUtil.deleteSelected("<portlet:resourceURL/>", "<liferay-portlet:renderURL/>", "<portlet:namespace/>", "delete-selected-registrations.csv")
    };

</aui:script>


