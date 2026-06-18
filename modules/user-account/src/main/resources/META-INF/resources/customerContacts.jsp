<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    Map<Long, String> customerInfo = (Map<Long, String>) renderRequest.getAttribute("customerInfo");
    final String filterSelection = (String) request.getAttribute("filterSelection");
    final Long customerSelection = (Long) request.getAttribute("customerSelection");

    final String customerContactsError = (String) request.getAttribute("customer-contacts-error");

//    final String selectedCustomerName = customerInfo.getOrDefault(customerSelection, "");

%>

<portlet:actionURL name="filter" var="filterCustomerContactsURL">
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>"/>
</portlet:actionURL>
<portlet:actionURL name="customerSelect" var="selectCustomerURL" >
    <portlet:param name="filterSelection" value="<%=filterSelection%>" />
</portlet:actionURL>

<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="customerSelection" value="<%=String.valueOf(customerSelection)%>" />
    <portlet:param name="filterSelection" value="<%= filterSelection %>" />
</liferay-portlet:renderURL>

<aui:fieldset>
    <aui:row>
        <aui:col width="25">
            <aui:form action="<%=selectCustomerURL%>" name="customerSelectionForm" >
                <div class="d-flex justify-content-start">
                    <aui:select name="customerSelection" label="customer.select.label" value="<%=customerSelection%>" onChange="submit()">

                        <%
                            for (Long customerId : customerInfo.keySet()) {
                                String customerName = customerInfo.get(customerId);
                        %>
                                <aui:option value="<%=customerId%>" label="<%=customerName%>" />
                        <%
                            }
                        %>
                    </aui:select>
                </div>
            </aui:form>
        </aui:col>
        <aui:col width="25">
            <aui:form action="<%=filterCustomerContactsURL%>" name="filterForm" >
                <div class="d-flex justify-content-start">
                    <aui:select name="filterSelection" label="customercontacts.filter.label"  value="<%=filterSelection%>" onChange="submit()">
                        <aui:option value="all" label="All" selected="true"/>
                        <aui:option value="license-manager" label="license-manager.label"/>
                        <aui:option value="beta-tester" label="beta-tester.label"/>
                    </aui:select>
                </div>
            </aui:form>
        </aui:col>
        <aui:col width="50">
        </aui:col>
    </aui:row>
</aui:fieldset>

<aui:form name="customerContacts" >
    <jsp:useBean id="customerContactList" type="java.util.List" scope="request"/>
    <jsp:useBean id="totalCustomerContactCount" type="java.lang.Integer" scope="request"/>
    <%
        String emptyResultsMessage = "";
        if (customerContactsError != null){
            emptyResultsMessage = customerContactsError;
        }else if (!themeDisplay.isSignedIn()) {
            emptyResultsMessage = LanguageUtil.format(request, "not-logged-in", new Object[0]);
        } else if (totalCustomerContactCount == 0) {
            emptyResultsMessage = LanguageUtil.format(request, "no-clm-records", new Object[]{themeDisplay.getUser().getEmailAddress()});
        }
    %>
    <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25"
                                 emptyResultsMessage='<%=emptyResultsMessage%>'
                                 total="<%=totalCustomerContactCount%>">
        <liferay-ui:search-container-results results="<%= customerContactList %>"/>

        <liferay-ui:search-container-row
                className="nl.deltares.useraccount.model.CustomerContact"
                modelVar="entry"
                keyProperty="contactId"
        >
            <liferay-ui:search-container-column-text cssClass="col-1" property="contactSalutation" name="customercontactform.salutation">
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text cssClass="col-2" property="contactName" name="customercontactform.name" orderable="true" orderableProperty="contactName"/>
            <liferay-ui:search-container-column-text cssClass="col-2" property="contactEmail" name="customercontactform.email" orderable="true" orderableProperty="contactEmail"/>
        </liferay-ui:search-container-row>
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
</aui:form>
