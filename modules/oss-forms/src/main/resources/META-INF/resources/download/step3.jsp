<%@ page import="nl.deltares.portal.constants.BillingConstants" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox" />

<span><liferay-ui:message key="dsd.registration.step3.billing.title"/></span>
<div class="row">

    <aui:col width="50">
        <c:if test="${not empty attributes}">
            <c:set var="billingFirstName" value="<%= attributes.get(OrganizationConstants.FIRST_NAME) %>"/>
            <c:set var="billingLastName" value="<%= attributes.get(OrganizationConstants.LAST_NAME) %>"/>
            <c:set var="billingCompany" value="<%= attributes.get(OrganizationConstants.ORG_NAME) %>"/>
            <c:set var="billingEmail" value="<%= attributes.get(OrganizationConstants.EMAIL) %>"/>
            <c:set var="billingAddress" value="<%= attributes.get(OrganizationConstants.ORG_STREET) %>"/>
            <c:set var="billingPostal" value="<%= attributes.get(OrganizationConstants.ORG_POSTAL) %>"/>
            <c:set var="billingCity" value="<%= attributes.get(OrganizationConstants.ORG_CITY) %>"/>
            <c:set var="billingCountry" value="<%= attributes.get(OrganizationConstants.ORG_COUNTRY_CODE) %>"/>
            <c:set var="billingVat" value="<%= attributes.get(OrganizationConstants.ORG_VAT) %>"/>
        </c:if>
        <aui:input
                name="<%= BillingConstants.EMAIL %>"
                label="dsd.registration.step3.billing.email"
                value="${billingEmail}" billing_value="${billingEmail}">
            <aui:validator name="required">
                function () {
                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                }
            </aui:validator>
            <aui:validator name="email"/>
        </aui:input>
        <aui:input
                name="<%= BillingConstants.ORG_NAME %>"
                label="dsd.registration.step3.billing.company"
                value="${billingCompany}" billing_value="${billingCompany}">
            <aui:validator name="required">
                function () {
                return checkStep(CommonFormsUtil.getFormName("<portlet:namespace/>"), 3);
                }
            </aui:validator>
            <aui:validator name="maxLength">75</aui:validator>
        </aui:input>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= BillingConstants.FIRST_NAME %>"
                        label="registrationform.firstname"
                        value="<%= user.getFirstName() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= BillingConstants.LAST_NAME %>"
                        label="registrationform.lastname"
                        value="<%= user.getLastName() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>

        <aui:input
                name="<%= BillingConstants.ORG_STREET %>"
                label="dsd.registration.step3.billing.address"
                value="${billingAddress}" billing_value="${billingAddress}">
            <aui:validator name="required">
                function () {
                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                }
            </aui:validator>
            <aui:validator name="maxLength">255</aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= BillingConstants.ORG_POSTAL %>"
                        label="dsd.registration.step3.billing.postal"
                        value="${billingPostal}" billing_value="${billingPostal}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">10</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= BillingConstants.ORG_CITY %>"
                        label="dsd.registration.step3.billing.city"
                        value="${billingCity}" billing_value="${billingCity}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>
        <aui:select
                name="<%=BillingConstants.ORG_COUNTRY_CODE%>"
                type="select"
                label="dsd.registration.step3.billing.country"
                value="${billingCountry}" billing_value="${billingCountry}">
                <aui:option value="" label ="registrationform.select.country" />
            <% List<Country> countries = CountryServiceUtil.getCompanyCountries(PortalUtil.getDefaultCompanyId()); %>
            <% for (Country country : countries) { %>
            <aui:option value="<%=country.getA2()%>" label="<%= country.getName(locale) %>"/>
            <% } %>
            <aui:validator name="required">
                    function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                    }
            </aui:validator>
            <aui:validator name="minLength">
                2
            </aui:validator>
        </aui:select>

    </aui:col>

    <div class="col">

        <aui:select
                name="billing_preference"
                type="select"
                label="download.email.billing.method"
                value="${billingMethod}">
            <aui:option value="payLink" label="downloadform.paymethod.link"/>
            <aui:option value="bankTransfer" label="downloadform.paymethod.bank"/>
            <aui:validator name="required">
                function () {
                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                }
            </aui:validator>
        </aui:select>

        <aui:input
                name="<%= BillingConstants.PAYMENT_REFERENCE %>"
                label="dsd.registration.step3.billing.reference"
                value="" billing_value="">
            <aui:validator name="maxLength">75</aui:validator>
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_VAT %>"
                label="dsd.registration.step3.billing.vat"
                value="${billingVat}" billing_value="${billingVat}" helpMessage="dsd.registration.step3.billing.vat.info">
            <aui:validator name="maxLength">25</aui:validator>
        </aui:input>
    </div>
</div>