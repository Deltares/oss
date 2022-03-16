<%@ page import="nl.deltares.dsd.model.BillingInfo" %>
<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox" />

<span><liferay-ui:message key="dsd.registration.step3.billing.title"/></span>
<div class="row">

    <aui:col width="50">
        <c:if test="${not empty attributes}">
            <c:set var="billingEmail" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_email.name()) %>"/>
            <c:set var="billingName" value="<%= attributes.get(BillingInfo.ATTRIBUTES.billing_name.name()) %>"/>
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
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
            <aui:validator name="email"/>
        </aui:input>
        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_name.name() %>"
                label="dsd.registration.step3.billing.name"
                value="${billingName}" billing_value="${billingName}">
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_address.name() %>"
                label="dsd.registration.step3.billing.address"
                value="${billingAddress}" billing_value="${billingAddress}">
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= BillingInfo.ATTRIBUTES.billing_postal.name() %>"
                        label="dsd.registration.step3.billing.postal"
                        value="${billingPostal}" billing_value="${billingPostal}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(getFormName(), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= BillingInfo.ATTRIBUTES.billing_city.name() %>"
                        label="dsd.registration.step3.billing.city"
                        value="${billingCity}" billing_value="${billingCity}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(getFormName(), 3);
                        }
                    </aui:validator>
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
                        return checkStep(getFormName(), 3);
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
                label="dsd.registration.step3.billing.method"
                value="${billingMethod}">
            <aui:option value="payLink" label="regostrationform.paymethod.link"/>
            <aui:option value="bankTransfer" label="regostrationform.paymethod.bank"/>
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:select>

        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_reference.name() %>"
                label="dsd.registration.step3.billing.reference"
                value="${billingReference}" billing_value="${billingReference}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
        <aui:input
                name="<%= BillingInfo.ATTRIBUTES.billing_vat.name() %>"
                label="dsd.registration.step3.billing.vat"
                value="${billingVat}" billing_value="${billingVat}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
    </div>
</div>