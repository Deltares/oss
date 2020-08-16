<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox"/>

<div class="row">
    <div class="col">
        <c:if test="${not empty attributes}">
            <c:set var="billing_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.billing_address.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.billing_address.name() %>"
                label="dsd.registration.step3.billing.address"
                value="${billing_address}">
        </aui:input>

        <div class="row">
            <div class="col">
                <c:if test="${not empty attributes}">
                    <c:set var="billing_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.billing_postal.name()) %>"/>
                </c:if>
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.billing_postal.name() %>"
                        label="registrationform.orgpostcode"
                        value="${billing_postal}">
                </aui:input>
            </div>
            <div class="col">
                <c:if test="${not empty attributes}">
                    <c:set var="billing_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.billing_city.name()) %>"/>
                </c:if>
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.billing_city.name() %>"
                        label="registrationform.orgcity"
                        value="${billing_city}">
                </aui:input>
            </div>
        </div>

        <c:if test="${not empty attributes}">
            <c:set var="billing_country" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.billing_country.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.billing_country.name() %>"
                label="dsd.registration.step3.billing.address"
                value="${billing_country}">
        </aui:input>

        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.terms_agreements.name() %>"
                label="dsd.registration.step3.terms.agreements"
                type="checkbox">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="remarks"
                label="dsd.registration.step3.remarks"
                type="textarea">
        </aui:input>
    </div>
    <div class="col">
        <aui:input
                name="payment_email"
                label="dsd.registration.step3.payment.email">
        </aui:input>

        <aui:select
                name="<%= KeycloakUtils.ATTRIBUTES.org_preferred_payment.name() %>"
                type="select"
                label="dsd.registration.step3.payment.method"
                showEmptyOption="true">
            <aui:option value="bankTransfer" label="regostrationform.paymethod.bank"/>
            <aui:option value="payLink" label="regostrationform.paymethod.link"/>
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:select>

        <c:if test="${not empty attributes}">
            <c:set var="payment_reference" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.pay_reference.name() %>"
                label="dsd.registration.step3.payment.reference"
                value="${org_city}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>

        <c:if test="${not empty attributes}">
            <c:set var="vat_code" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_vat.name()) %>"/>
        </c:if>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_vat.name() %>"
                label="dsd.registration.step3.vat.code"
                value="${vat_code}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 3);
                }
            </aui:validator>
        </aui:input>
    </div>
</div>
