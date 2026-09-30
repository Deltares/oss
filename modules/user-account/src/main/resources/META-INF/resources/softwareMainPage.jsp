<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    Map<Long, String> customerInfo = (Map<Long, String>) renderRequest.getAttribute("customerInfo");
    final String filterSelection = (String) request.getAttribute("filterSelection");
    final Long customerSelection = (Long) request.getAttribute("customerSelection");
    final String maconomyId = (String) request.getAttribute("maconomyId");
    List<?> records = (List<?>) renderRequest.getAttribute("records");
    String tabSelection = (String) request.getAttribute("tabSelection");
    if (tabSelection == null){
        tabSelection = "softwareSuites";
    }

    String cssSoftwareSuites = tabSelection.equals("softwareSuites") ? "active selected" : "";
    String cssSoftwareGroups = tabSelection.equals("softwareGroups") ? "active selected" : "";
%>

<portlet:actionURL name="filter" var="filterCustomerLicensesURL">
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
    <portlet:param name="tabSelection" value="<%=tabSelection%>"/>
</portlet:actionURL>
<portlet:actionURL name="customerSelect" var="selectCustomerURL">
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="tabSelection" value="<%=tabSelection%>"/>
</portlet:actionURL>
<portlet:actionURL name="sendLicenseFiles" var="sendLicenseFilesURL">
    <portlet:param name="customerId" value="<%=String.valueOf(customerSelection)%>"/>
    <portlet:param name="customerName" value="<%=customerInfo.get(customerSelection)%>"/>
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
    <portlet:param name="tabSelection" value="<%=tabSelection%>"/>
</portlet:actionURL>
<liferay-ui:success key="send-licenses-success" embed="true" targetNode="">
    <liferay-ui:message key="send.licenses.success"/>
</liferay-ui:success>
<portlet:renderURL var="softwareSuitesURL">
    <portlet:param name="tabSelection" value="softwareSuites"/>
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
</portlet:renderURL>
<portlet:renderURL var="softwareGroupsURL">
    <portlet:param name="tabSelection" value="softwareGroups"/>
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
</portlet:renderURL>


<liferay-ui:error key="send-licenses-failed">
    <liferay-ui:message key="send.licenses.failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "send-licenses-failed") %>'/>
</liferay-ui:error>

<%
    if (!themeDisplay.isSignedIn()) {
%>
<div class="alert alert-info" ><liferay-ui:message key="not-logged-in"/></div>
<%
    } else {
%>
<aui:fieldset>
    <aui:row>
        <aui:col width="25">
            <aui:form action="<%=selectCustomerURL%>" name="customerSelectionForm">
                <div class="d-flex justify-content-start">
                    <aui:select name="customerSelection" label="customer.select.label" value="<%=customerSelection%>"
                                onChange="submit()">

                        <%
                            for (Long customerId : customerInfo.keySet()) {
                                String customerName = customerInfo.get(customerId);
                        %>
                        <aui:option value="<%=customerId%>" label="<%=customerName%>"/>
                        <%
                            }
                        %>
                    </aui:select>
                </div>
            </aui:form>
        </aui:col>
        <aui:col width="25">
            <aui:form action="<%=filterCustomerLicensesURL%>" name="filterForm">
                <div class="d-flex justify-content-start">
                    <aui:select name="filterSelection" label="softwaresuites.filter.label" value="<%=filterSelection%>"
                                onChange="submit()">
                        <aui:option value="Active" label="Running" selected="true"/>
                        <aui:option value="Expired" label="Expired"/>
                        <aui:option value="Terminated" label="Terminated"/>
                    </aui:select>
                </div>
            </aui:form>
        </aui:col>
        <aui:col cssClass="bottom-align" width="50">
            <aui:button-row>
                <aui:button name="sendButton" href="<%=sendLicenseFilesURL%>" cssClass="sendButton"
                            value="Send license files"/>
            </aui:button-row>
        </aui:col>
    </aui:row>
</aui:fieldset>

<div class="portlet-navigation">
    <ul class="nav nav-pills navbar-site">
        <li class="lfr-nav-item nav-item <%=cssSoftwareSuites%>">
            <a href="<%= softwareSuitesURL%> " class="nav-link text-truncate" >Software Suites</a>
        </li>
        <li class="lfr-nav-item nav-item <%=cssSoftwareGroups%>">
            <a href="<%= softwareGroupsURL%> " class="nav-link text-truncate" >Software Groups</a>
        </li>
    </ul>
</div>
<% if (records.isEmpty()) { %>
<div class="alert alert-info" >
    <liferay-ui:message key="no-clm-records"
        arguments='<%=new String[]{themeDisplay.getUser().getEmailAddress()} %>'/>
</div>
<%
    } else if (tabSelection.equals("softwareSuites")) {
%>
        <%@ include file="softwareSuites.jsp" %>
<%
    } else {
%>
        <%@ include file="softwareGroups.jsp" %>
<%
    }
}
%>
