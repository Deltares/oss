<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="com.liferay.portal.kernel.dao.search.RowChecker" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
    final String filterValue = (String) request.getAttribute("filterValue");
    final String filterSelection = (String) request.getAttribute("filterSelection");

%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.registration.title">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/registrationTable.jsp" />
    </portlet:renderURL>

    <liferay-portlet:renderURL varImpl="iteratorURL">
        <portlet:param name="filterValue" value="<%=filterValue%>" />
        <portlet:param name="filterSelection" value="<%= filterSelection %>" />
    </liferay-portlet:renderURL>

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
                    <div class="control-label"><liferay-ui:message key="table.filter.label"/></div>
                </aui:col>
                <aui:col width="20">
                    <aui:input name="filterValue" label="" value="<%=filterValue%>"/>
                </aui:col>
                <aui:col width="50">
                    <div class="d-flex justify-content-start">
                        <div class="pr-3">
                            <aui:input
                                    name="filterSelection"
                                    label="E-mail"
                                    type="radio"
                                    value="email"
                                    checked='<%="email".equals(filterSelection)%>'/>
                        </div>
                        <div class="pr-3">
                            <aui:input
                                    name="filterSelection"
                                    label="Resource ID"
                                    type="radio"
                                    value="resourceid"
                                    checked='<%="resourceid".equals(filterSelection)%>'/>
                        </div>
                        <div class="pr-3">
                            <aui:input
                                    name="filterSelection"
                                    label="Event Resource ID"
                                    type="radio"
                                    value="eventid"
                                    checked='<%="eventid".equals(filterSelection)%>'/>
                        </div>
                    </div>
                </aui:col>
                <aui:col width="20">
                    <aui:button type="submit" value="table.filter.button" />
                    <aui:button type="submit" onClick="<%= viewURL %>" value="table.filter.clear"/>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form >
        <jsp:useBean id="records" class="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-registration-records")%>'
                                     total="<%=count%>" rowChecker="<%= new RowChecker(renderResponse) %>" >
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayRegistration"
                    modelVar="entry"
            >
                <liferay-ui:search-container-column-text property="eventName" name="Event"/>
                <liferay-ui:search-container-column-text property="registrationName" name="Registration"/>
                <liferay-ui:search-container-column-text property="email" name="E-Mail"/>
                <liferay-ui:search-container-column-text property="startTime" name="Start (GMT)"/>
                <liferay-ui:search-container-column-text property="endTime" name="End (GMT)"/>
                <liferay-ui:search-container-column-text property="eventResourceId" name="Event Resource ID"/>
                <liferay-ui:search-container-column-text property="resourceId" name="Resource ID"/>
                <%--                <liferay-ui:search-container-column-text property="preferences" name="Prefferences"/>--%>
                <liferay-ui:search-container-column-text name="Actions">
                    <aui:button-row>
                        <portlet:renderURL var="editRegistrationURL">
                            <portlet:param name="mvcPath" value="/editRegistration.jsp" />
                            <portlet:param name="recordId" value="${entry.getRecordId()}"/>
                            <portlet:param name="filterEmail" value="${entry.getEmail()}"/>
                        </portlet:renderURL>
                        <portlet:actionURL var="deleteRegistrationURL" name="delete">
                            <portlet:param name="recordId" value="${entry.getRecordId()}"/>
                            <portlet:param name="filterEmail" value="${entry.getEmail()}"/>
                        </portlet:actionURL>
                        <aui:button name="editButton" type="submit" value="Edit" href="<%=editRegistrationURL%>"/>
                        <aui:button name="deleteButton" type="submit" cssClass="deleteButton" value="Delete" href="<%=deleteRegistrationURL%>"  />
                   </aui:button-row>
                </liferay-ui:search-container-column-text>
            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
    </aui:form>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
        </aui:col>
    </aui:row>
</aui:fieldset>
<aui:script use="event, node, aui-base, aui-progressbar">

    let deleteButtons = $(document.getElementsByClassName("deleteButton"));
    [...deleteButtons].forEach(function (button) {
        button.onclick = function (event){
            if (confirm("You are about to delete this registration.\nDo you want to continue?") === false) {
                event.preventDefault();
            }
        };
    });

</aui:script>


