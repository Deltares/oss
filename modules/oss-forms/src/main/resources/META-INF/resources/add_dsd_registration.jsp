<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>
<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="java.util.List" %>
<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/dsd_registration.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="addRegistration" var="addRegistrationURL"></portlet:actionURL>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>' />
</liferay-ui:error>
<liferay-ui:error key="validation-failed">
    <liferay-ui:message key="validation-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "validation-failed") %>' />
</liferay-ui:error>

<aui:form action="<%= addRegistrationURL %>" name="<portlet:namespace />fm">

    <aui:fieldset>
        <h1><liferay-ui:message key="registrationform.userInfo" /></h1>
        <aui:input name="email" label="registrationform.email" required="true" value="<%= user.getEmailAddress() %>" disabled="true" ></aui:input>
        <aui:input name="username" label="registrationform.username" required="true" value="<%= user.getScreenName() %>" disabled="true"></aui:input>
        <aui:input name="firstname" label="registrationform.firstname" required="true" value="<%= user.getFirstName() %>" disabled="true"></aui:input>
        <aui:input name="lastname" label="registrationform.lastname" required="true" value="<%= user.getLastName() %>" disabled="true"></aui:input>
        <hr>
        <h1><liferay-ui:message key="registrationform.organizationInfo" /></h1>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_name.name()%>" label="registrationform.orgname" required="true" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>" ></aui:input>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_address.name()%>" label="registrationform.orgaddress" required="true" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>" ></aui:input>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_postal.name()%>" label="registrationform.orgpostcode" required="true" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"></aui:input>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_city.name()%>" label="registrationform.orgcity" required="true" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"></aui:input>

        <aui:select name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>" type="select" label="registrationform.orgcountry"  value="<%=  attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()) %>" >
            <% List<Country> countries = CountryServiceUtil.getCountries(true); %>
            <%    for (Country country : countries) { %>
                    <aui:option value="<%=country.getName()%>" label ="<%= country.getName(locale) %>" />
            <% } %>
        </aui:select>

        <% if (requiresPayment) { %>
        <% String prefPayment = "payLink"; %>
        <hr>
        <h1><liferay-ui:message key="registrationform.paymentInfo" /></h1>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.pay_reference.name()%>" label="registrationform.payreference" required="true" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.pay_reference.name()) %>" ></aui:input>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_vat.name()%>" label="registrationform.payvat" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_vat.name()) %>" ></aui:input>
        <aui:select name="<%=KeycloakUtils.ATTRIBUTES.org_preferred_payment.name()%>" type="select" label="registrationform.paymethod"  value="<%=  attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_preferred_payment.name(), prefPayment) %>" >
            <aui:option value="bankTransfer" label ="regostrationform.paymethod.bank" />
            <aui:option value="payLink" label ="regostrationform.paymethod.link" />
        </aui:select>
        <% } %>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
        <aui:button type="cancel" onClick="<%= viewURL.toString() %>"></aui:button>
    </aui:button-row>
</aui:form>