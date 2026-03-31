<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>
<%@ page import="com.liferay.account.model.AccountEntry" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="nl.deltares.forms.internal.FilterAccountSelectionCheckoutStepDisplayContext" %>
<%@ page import="nl.deltares.forms.constants.CheckoutWebKeys" %>
<%@ page import="nl.deltares.forms.util.FilterAccountSelectionCheckoutStep" %>
<%@ page import="com.liferay.portal.kernel.servlet.ServletContextUtil" %>
<%@ include file="init.jsp" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<div class="prose prose--app">
    <h3><liferay-ui:message key="registrationform.select.customer.org"/></h3>
</div>
<%
    FilterAccountSelectionCheckoutStepDisplayContext displayContext = (FilterAccountSelectionCheckoutStepDisplayContext)request
            .getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);

    String filterValue = displayContext.getFilterValue();
    List<AccountEntry> accountEntryList = displayContext.getAccountEntries();
    int totalCount = displayContext.getTotalCount();
%>
<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="ids" value="<%=ids%>"/>
    <portlet:param name="filterValue" value="<%=filterValue%>"/>
    <portlet:param name="checkoutStepName" value="<%=FilterAccountSelectionCheckoutStep.NAME%>"/>
</liferay-portlet:renderURL>

<aui:form  name="filterForm" >
    <aui:fieldset>
        <aui:row>
            <aui:input name="stayonpage" type="hidden" value="true"/>
            <aui:col width="90">
                <aui:input name="filterValue" placeholder="registrationform.searchorg" label="" value="<%=filterValue%>"/>
            </aui:col>
            <aui:col width="10">
                <aui:button type="submit" value="registrationform.search"  cssClass="btn btn-primary" />
            </aui:col>
        </aui:row>
    </aui:fieldset>
</aui:form>

<hr/>
<aui:form>

    <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25"
                                 emptyResultsMessage='<%=LanguageUtil.get(locale, "no-registration-records")%>'
                                 total="<%=totalCount%>" >
        <liferay-ui:search-container-results results="<%= accountEntryList %>" />
        <liferay-ui:search-container-row
                className="com.liferay.account.model.AccountEntry"
                modelVar="entry"
        >

            <liferay-ui:search-container-row-parameter name="accountEntryId" value="${entry.accountEntryId}" />
            <liferay-ui:search-container-column-text property="name" name="Name"/>
            <liferay-ui:search-container-column-text property="accountEntryId" name="ID" />
            <liferay-ui:search-container-column-text property="type" name="Type" />
            <liferay-ui:search-container-column-text name="Action" >
                <aui:button-row>
                    <portlet:actionURL name="/submit/register/select_org_step" var="saveStepURL" >
                        <portlet:param name="accountEntryId" type="hidden" value="${entry.accountEntryId}"/>
                        <portlet:param name="checkoutStepName" type="hidden" value="<%= FilterAccountSelectionCheckoutStep.NAME %>"/>
                        <portlet:param name="ids" type="hidden" value="<%= ids %>"/>
                        <portlet:param name="redirect" type="hidden" value="<%= currentURL %>"/>
                    </portlet:actionURL>
                    <aui:button name="selectOrganization" type="submit" value="registrationform.select"
                                cssClass="btn btn-secondary"/>
                </aui:button-row>
            </liferay-ui:search-container-column-text>

        </liferay-ui:search-container-row>
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
</aui:form>
