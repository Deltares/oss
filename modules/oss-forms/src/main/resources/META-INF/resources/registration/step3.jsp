<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox" />

<span><liferay-ui:message key="dsd.registration.step3.billing.title"/></span>
<div class="row">

    <aui:col width="50">
        <c:if test="${not empty attributes}">
            <c:set var="billingEmail" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_email.name()) %>"/>
            <c:set var="billingName" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_name.name()) %>"/>
            <c:set var="billingAddress" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_address.name()) %>"/>
            <c:set var="billingPostal" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_postal.name()) %>"/>
            <c:set var="billingCity" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_city.name()) %>"/>
            <c:set var="billingCountry" value="<%= attributes.get(KeycloakUtils.BILLING_ATTRIBUTES.billing_country.name()) %>"/>
            <c:set var="billingMethod" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_preferred_payment.name()) %>"/>
            <c:set var="billingReference" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.pay_reference.name()) %>"/>
            <c:set var="billingVat" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_vat.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.BILLING_ATTRIBUTES.billing_email.name()%>"
                label="dsd.registration.step3.billing.email"
                value="${billingEmail}" >
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
        <aui:input
                name="<%= KeycloakUtils.BILLING_ATTRIBUTES.billing_name.name() %>"
                label="dsd.registration.step3.billing.name"
                value="${billingName}">
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="<%= KeycloakUtils.BILLING_ATTRIBUTES.billing_address.name() %>"
                label="dsd.registration.step3.billing.address"
                value="${billingAddress}">
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.BILLING_ATTRIBUTES.billing_postal.name() %>"
                        label="dsd.registration.step3.billing.postal"
                        value="${billingPostal}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(getFormName(), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.BILLING_ATTRIBUTES.billing_city.name() %>"
                        label="dsd.registration.step3.billing.city"
                        value="${billingCity}">
                    <aui:validator name="required">
                        function () {
                            return checkStep(getFormName(), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <aui:select
                name="<%=KeycloakUtils.BILLING_ATTRIBUTES.billing_country.name()%>"
                type="select"
                label="dsd.registration.step3.billing.country"
                value="${billingCountry}" >
            <% List<Country> countries = CountryServiceUtil.getCountries(true); %>
            <%    for (Country country : countries) { %>
            <aui:option value="<%=country.getName()%>" label ="<%= country.getName(locale) %>" />
            <% } %>
            <aui:validator name="required">
                function () {
                    return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:select>

    </aui:col>

    <div class="col">

        <aui:select
                name="<%= KeycloakUtils.ATTRIBUTES.org_preferred_payment.name() %>"
                type="select"
                label="dsd.registration.step3.billing.method"
                value="${billingMethod}">
            <aui:option value="bankTransfer" label="regostrationform.paymethod.bank"/>
            <aui:option value="payLink" label="regostrationform.paymethod.link"/>
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:select>

        <c:if test="${not empty attributes}">
            <c:set var="payment_reference" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.pay_reference.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.pay_reference.name() %>"
                label="dsd.registration.step3.billing.reference"
                value="${billingReference}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_vat.name() %>"
                label="dsd.registration.step3.billing.vat"
                value="${billingVat}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
    </div>
</div>