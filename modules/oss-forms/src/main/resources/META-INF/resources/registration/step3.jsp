<%@ page import="nl.deltares.portal.constants.BillingConstants" %>
<h3><strong><liferay-ui:message key="download.email.billing.payment.contact"/></strong></h3>
<aui:row>
    <aui:col width="100">
        <div class="row">
            <div class="col">
                <aui:input
                        label="registrationform.email"
                        name='<%=BillingConstants.EMAIL%>'
                        value="<%= user.getEmailAddress() %>" max="75">
                    <aui:validator name="email" />
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        label="registrationform.firstname"
                        name='<%=BillingConstants.FIRST_NAME%>'
                        value="<%= user.getFirstName() %>" max="75">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'),3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        label="registrationform.lastname"
                        name='<%=BillingConstants.LAST_NAME%>'
                        value="<%= user.getLastName() %>" max="75">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'),3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
    </aui:col>
</aui:row>
<h3><strong><liferay-ui:message key="download.email.billing.payment.details"/></strong></h3>
<aui:row>
    <aui:col width="100">
        <div class="row">
            <div class="col">
                <aui:select
                        name="<%=BillingConstants.PAYMENT_METHOD%>"
                        type="select"
                        label="dsd.registration.step3.billing.method"
                        value="payLink">
                    <aui:option value="payLink" label="regostrationform.paymethod.link"/>
                    <aui:option value="bankTransfer" label="regostrationform.paymethod.bank"/>
                </aui:select>
            </div>
            <div class="col">
                <aui:input
                        name='<%=BillingConstants.PAYMENT_REFERENCE%>'
                        label="dsd.registration.step3.billing.reference"
                        helpMessage="dsd.registration.step3.billing.reference.info"
                        value="" max="75">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name='<%=BillingConstants.ORG_VAT%>'
                        label="dsd.registration.step3.billing.vat"
                        helpMessage="dsd.registration.step3.billing.vat.info"
                        value="" max="75">
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name='<%= BillingConstants.ORG_EXTERNAL_REFERENCE_CODE %>'
                        label="dsd.registration.step3.billing.companyid"
                        helpMessage="dsd.registration.step3.billing.companyid.info"
                        value="" max="75">
                </aui:input>
            </div>
        </div>
    </aui:col>
</aui:row>
<h3><strong><liferay-ui:message key="registrationform.select.billing"/></strong></h3>
<aui:row>
    <aui:col width="100">
        <div class="row">
            <div class="col">
                <aui:select
                        name="select_address"
                        type="select"
                        label=""
                        value="0">
                    <aui:option value="0" label ="registrationform.select.custom.address" />
                </aui:select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= BillingConstants.ORG_NAME %>"
                        label="registrationform.orgname"
                        value="" max="75">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name='<%= BillingConstants.ORG_STREET %>'
                        label="registrationform.orgaddress"
                        value="" max="255">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name='<%= BillingConstants.ORG_POSTAL %>'
                        label="registrationform.orgpostcode"
                        value="" max="10">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name='<%= BillingConstants.ORG_CITY %>'
                        label="registrationform.orgcity"
                        value="" max="75">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:select
                        name="<%=BillingConstants.ORG_COUNTRY_CODE%>"
                        type="select"
                        label="registrationform.orgcountry"
                        value="" >
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                    <aui:option value="" label ="registrationform.select.country" />
                    <% List<Country> countries = CountryServiceUtil.getCompanyCountries(themeDisplay.getCompanyId(), true); %>
                    <%    for (Country country : countries) { %>
                    <aui:option value="<%=country.getA2()%>" label ="<%= country.getName(locale) %>" />
                    <% } %>
                </aui:select>
            </div>
            <div class="col">
                <aui:input
                        name="<%=BillingConstants.ORG_PHONE %>"
                        label="registrationform.phone"
                        value="" max="15">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 3);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>

    </aui:col>
</aui:row>
<aui:script use="event, node, aui-base">

    let addressSelection = document.getElementById("<portlet:namespace />select_address");
    addressSelection.onchange = function (event){
        DsdRegistrationFormsUtil.addressSelectionChanged("<portlet:namespace />", event.target)
    }

</aui:script>