<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>
<%@ page import="nl.deltares.forms.internal.FilterAccountSelectionCheckoutStepDisplayContext" %>
<%@ page import="nl.deltares.forms.util.SelectAdditionalAccountsCheckoutStep" %>
<%@ include file="init.jsp" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<div class="prose prose--app">
    <h3><liferay-ui:message key="registrationform.select.customer.org"/></h3>
    <h4><liferay-ui:message key="registrationform.select.customer.option"/></h4>
</div>
<%
    FilterAccountSelectionCheckoutStepDisplayContext displayContext = (FilterAccountSelectionCheckoutStepDisplayContext) request
            .getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);

    List<AccountEntry> accountEntryList = displayContext.getAccountEntries();
    String filterValue = displayContext.getFilterValue();
    int totalCount = displayContext.getTotalCount();
%>
<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="ids" value="<%=ids%>"/>
    <portlet:param name="filterValue" value="<%=filterValue%>"/>
    <portlet:param name="checkoutStepName" value="<%=SelectAdditionalAccountsCheckoutStep.NAME%>"/>
</liferay-portlet:renderURL>

<aui:input name="stayonpage" type="hidden" value="true"/>
<aui:input name="selectedAccountEntryId" type="hidden" value=""/>

<aui:fieldset>
    <aui:row>
        <aui:col width="75">
            <aui:input name="filterValue" placeholder="registrationform.searchorg" label="" value="<%=filterValue%>"/>
        </aui:col>
        <aui:col width="25">
            <aui:button-row>
                <aui:button name="submitFilter" type="submit" value="registrationform.search"
                            cssClass="btn btn-primary"  />
                <aui:button name="clearFilter" type="submit" value="registrationform.clear" cssClass="btn btn-primary"/>
            </aui:button-row>
        </aui:col>

    </aui:row>
</aui:fieldset>

<hr/>

<liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25"
                             emptyResultsMessage='<%=LanguageUtil.get(locale, "no-registration-records")%>'
                             total="<%=totalCount%>">
    <liferay-ui:search-container-results results="<%= accountEntryList %>" />

    <liferay-ui:search-container-row
            className="com.liferay.account.model.AccountEntry"
            modelVar="entry"
    >
        <liferay-ui:search-container-row-parameter name="entry" value="<%= entry %>"/>
        <liferay-ui:search-container-row-parameter name="accountEntryId" value="${entry.accountEntryId}"/>
        <liferay-ui:search-container-column-text property="name" name="Name"/>
        <liferay-ui:search-container-column-text property="accountEntryId" name="ID"/>
        <liferay-ui:search-container-column-text property="type" name="Type"/>
        <liferay-ui:search-container-column-text name="Action">
            <aui:button name="selectOrganization" type="submit" data-accountEntryId="${entry.accountEntryId}" value="registrationform.select"
                        cssClass="btn btn-secondary select-org-button"/>
        </liferay-ui:search-container-column-text>

    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator/>
</liferay-ui:search-container>

<hr style="margin-bottom: 1rem; margin-top: 1rem"/>
<aui:button-row>
    <aui:button cssClass="pull-right btn-primary nav-button" name="previous" type="submit" value="previous"/>
    <aui:button cssClass="pull-right btn-primary nav-button" name="continue" type="submit" value="continue"/>
</aui:button-row>


<aui:script use="event, node, aui-base, aui-io-request">


    let clearFilterButton = document.getElementById('<portlet:namespace/>clearFilter');
    clearFilterButton.addEventListener('click', function (event){
        let filterValue = document.getElementById("<portlet:namespace/>filterValue");
        filterValue.value = "";
    });

    let selectOrgButtons = document.getElementsByClassName('select-org-button');
    Array.from(selectOrgButtons).forEach(function (button) {
       button.addEventListener('click', function (event) {
            let accountEntryId = this.dataset.accountentryid ;
            let selectedAccountEntryIdInput = document.getElementById('<portlet:namespace/>selectedAccountEntryId');
            selectedAccountEntryIdInput.value = accountEntryId;
            let stayOnPageInput = document.getElementById('<portlet:namespace/>stayonpage');
            stayOnPageInput.value = "false";
        });
    });
    let navButtons = document.getElementsByClassName('nav-button');
    Array.from(navButtons).forEach(function (button) {
        button.addEventListener('click', function (event){
            let stayOnPageInput = document.getElementById('<portlet:namespace/>stayonpage');
            stayOnPageInput.value = "false";
            let selectedAccountEntryIdInput = document.getElementById('<portlet:namespace/>selectedAccountEntryId');
            selectedAccountEntryIdInput.value = "";
        });
    });

</aui:script>