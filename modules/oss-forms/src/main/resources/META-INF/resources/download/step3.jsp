<%@ page import="nl.deltares.model.BillingInfo" %>
<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox" />

<span><liferay-ui:message key="dsd.registration.step3.billing.title"/></span>
<div class="row">

    <aui:col width="50">
        <c:if test="${not empty attributes}">
            <c:set var="billingFirstName" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_firstname.name()) %>"/>
            <c:set var="billingLastName" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_lastname.name()) %>"/>
            <c:set var="billingCompany" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_company.name()) %>"/>
            <c:set var="billingEmail" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_email.name()) %>"/>
            <c:set var="billingAddress" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_address.name()) %>"/>
            <c:set var="billingPostal" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_postal.name()) %>"/>
            <c:set var="billingCity" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_city.name()) %>"/>
            <c:set var="billingCountry" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_country.name()) %>"/>
            <c:set var="billingReference" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_reference.name()) %>"/>
            <c:set var="billingVat" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_vat.name()) %>"/>
            <c:set var="billingMethod" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_preference.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_email.name()%>"
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
                name="<%= BillingInfo.ATTRIBUTES.billing_company.name() %>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.last_name.name() %>"
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
                name="<%= BillingInfo.ATTRIBUTES.billing_address.name() %>"
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
                        name="<%= BillingInfo.ATTRIBUTES.billing_postal.name() %>"
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
                        name="<%= BillingInfo.ATTRIBUTES.billing_city.name() %>"
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
                name="<%=BillingInfo.ATTRIBUTES.billing_country.name()%>"
                type="select"
                label="dsd.registration.step3.billing.country"
                value="${billingCountry}" billing_value="${billingCountry}">
                <aui:option value="" label ="registrationform.select.country" />
            <% List<Country> countries = CountryServiceUtil.getCountries(true); %>
            <% for (Country country : countries) { %>
            <aui:option value="<%=country.getName()%>" label="<%= country.getName(locale) %>"/>
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
                name="<%= BillingInfo.ATTRIBUTES.billing_preference.name() %>"
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
                name="<%= BillingInfo.ATTRIBUTES.billing_reference.name() %>"
                label="dsd.registration.step3.billing.reference"
                value="${billingReference}" billing_value="${billingReference}">
            <aui:validator name="maxLength">75</aui:validator>
        </aui:input>
        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_vat.name() %>"
                label="dsd.registration.step3.billing.vat"
                value="${billingVat}" billing_value="${billingVat}" helpMessage="dsd.registration.step3.billing.vat.info">
            <aui:validator name="maxLength">25</aui:validator>
        </aui:input>
    </div>
</div>