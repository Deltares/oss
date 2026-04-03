<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.useraccount.model.SoftwareSuite" %>
<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    Map<Long, String> customerInfo = (Map<Long, String>) renderRequest.getAttribute("customerInfo");
    List<SoftwareSuite> suiteListSelection = (List<SoftwareSuite>) renderRequest.getAttribute("records");
    final String filterSelection = (String) request.getAttribute("filterSelection");
    final Long customerSelection = (Long) request.getAttribute("customerSelection");
%>

<portlet:actionURL name="filter" var="filterCustomerLicensesURL">
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
</portlet:actionURL>
<portlet:actionURL name="customerSelect" var="selectCustomerURL">
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
</portlet:actionURL>
<portlet:actionURL name="sendLicenseFiles" var="sendLicenseFilesURL">
    <portlet:param name="customerId" value="<%=String.valueOf(customerSelection)%>"/>
    <portlet:param name="customerName" value="<%=customerInfo.get(customerSelection)%>"/>
    <portlet:param name="filterSelection" value="<%=filterSelection%>"/>
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
</portlet:actionURL>

<liferay-ui:success key="send-licenses-success" embed="true" targetNode="">
    <liferay-ui:message key="send.licenses.success"/>
</liferay-ui:success>

<liferay-ui:error key="send-licenses-failed">
    <liferay-ui:message key="send.licenses.failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "send-licenses-failed") %>'/>
</liferay-ui:error>

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

<%
    if (suiteListSelection.isEmpty()) {
%>
<div><strong><liferay-ui:message key="no-license-records"
                                 arguments='<%=new String[]{filterSelection, themeDisplay.getUser().getEmailAddress()} %>'/></strong>
</div>
<%
    }

    for (SoftwareSuite softwareSuite : suiteListSelection) {
%>

<aui:fieldset>
    <a href="#softwareSuite-<%=softwareSuite.getSuiteCode()%>" aria-controls="site_configContent" aria-expanded="false"
       class="collapse-icon collapse-icon-middle sheet-subtitle collapsed" data-toggle="liferay-collapse" role="button">
            <span class="c-inner" tabindex="-1">
                <span class="collapse-icon-closed">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-plus">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#plus"></use>
                    </svg>
                </span>
                <span class="collapse-icon-open">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-hr">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#hr"></use>
                    </svg>
                </span>
                &nbsp;
                <span class="h1"><%=softwareSuite.getSuiteName()%></span>
            </span>
    </a>

    <div class="panel-collapse collapse" id="softwareSuite-<%=softwareSuite.getSuiteCode()%>">
        <%@ include file="softwareSuiteSubscriptions.jsp" %>
    </div>

</aui:fieldset>

<% } %>