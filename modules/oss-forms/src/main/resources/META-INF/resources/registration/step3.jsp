<aui:input
        name="use_organization_address"
        label="dsd.registration.step3.use.organization.address"
        type="checkbox"/>

<div class="row">
    <div class="col">
        <c:if test="${not empty attributes}">
            <c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
        </c:if>
        <aui:input
                name="payment_address"
                label="dsd.registration.step3.billing.address"
                value="${org_address}">
        </aui:input>

        <div class="row">
            <div class="col">
                <c:if test="${not empty attributes}">
                    <c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
                </c:if>
                <aui:input
                        name="payment_address_postcode"
                        label="registrationform.orgpostcode"
                        value="${org_postal}">
                </aui:input>
            </div>
            <div class="col">
                <c:if test="${not empty attributes}">
                    <c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
                </c:if>
                <aui:input
                        name="payment_address_city"
                        label="registrationform.orgcity"
                        value="${org_city}">
                </aui:input>
            </div>
        </div>

        <aui:input
                name="terms_and_agreements"
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
