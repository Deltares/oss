<%@ include file="init.jsp" %>

<%
    if (SessionErrors.contains(request, RegistrationFormException.class)) {
        List<RegistrationFormException> errors = (List<RegistrationFormException>) SessionErrors.get(request, RegistrationFormException.class);

        for (RegistrationFormException error : errors) {
%>
<liferay-ui:error exception="<%=RegistrationFormException.class%>">
    <%= error.getMessage() %>
</liferay-ui:error>
<%
        }
    }
%>

<h3><strong><liferay-ui:message key="registrationform.billing.select.address"/></strong></h3>
<%
    BillingInfo billingInfo = (BillingInfo) request.getAttribute("billingInfo");
    BillingDetailsCheckoutStepDisplayContext displayContext = (BillingDetailsCheckoutStepDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    List<Address> availableAddresses = displayContext.getBillingAddresses();
    String paramName = displayContext.getParamName();

    Long selectedAddressId = billingInfo == null ? 0 : billingInfo.getBillingAddressId();
    Address selectedAddress = null;
    if (selectedAddressId == 0 && !availableAddresses.isEmpty()) {
        selectedAddress = availableAddresses.get(0);
        selectedAddressId = selectedAddress.getAddressId();
    }
    boolean canEditAddress = displayContext.canEditAddress();
%>
<aui:input disabled="<%=availableAddresses.isEmpty() %>" name="<%= paramName %>" type="hidden" value="<%= selectedAddressId %>" />

<div class="form-group-autofit">
    <aui:input
            label="registrationform.email"
            name='<%=BillingConstants.EMAIL%>'
            value="<%= billingInfo == null ? user.getEmailAddress() : billingInfo.getEmail() %>" wrapperCssClass="form-group-item" >
        <aui:validator name="email" />
        <aui:validator name="required" />
    </aui:input>
</div>

<div class="form-group-autofit">
    <aui:input
            label="registrationform.firstname"
            name='<%=BillingConstants.FIRST_NAME%>'
            value="<%= billingInfo == null ? user.getFirstName() : billingInfo.getFirstName() %>" wrapperCssClass="form-group-item">
        <aui:validator name="required" />
    </aui:input>

    <aui:input
            label="registrationform.lastname"
            name='<%=BillingConstants.LAST_NAME%>'
            value="<%= billingInfo == null ? user.getLastName() : billingInfo.getLastName() %>" wrapperCssClass="form-group-item">
        <aui:validator name="required" />
    </aui:input>
</div>

<div class="form-group-autofit">
    <aui:select
            name="<%=BillingConstants.PAYMENT_METHOD%>"
            type="select"
            label="registrationform.billing.payment.method"
            value='<%= billingInfo == null ? "paylink" : billingInfo.getPreference() %>' wrapperCssClass="commerce-form-group-item-row form-group-item">
        <aui:option value="payLink" label="registrationform.billing.paymethod.link"/>
        <aui:option value="bankTransfer" label="registrationform.billing.paymethod.bank"/>
    </aui:select>

    <aui:input
            name='<%=BillingConstants.PAYMENT_REFERENCE%>'
            label="registrationform.billing.reference"
            helpMessage="registrationform.billing.reference.info"
            wrapperCssClass="form-group-item"
            value='<%= billingInfo == null ? "" : billingInfo.getReference()%>' >
        <aui:validator name="required" />
    </aui:input>

</div>

<div class="form-group-autofit">
    <aui:input
            name='<%=OrganizationConstants.ORG_VAT%>'
            label="registrationform.billing.vat"
            helpMessage="registrationform.billing.vat.info"
            wrapperCssClass="form-group-item"
            disabled="<%=!canEditAddress%>"
            value='<%= billingInfo == null ? "" : billingInfo.getVat()%>'
    />
    <aui:input
            name='<%= OrganizationConstants.ORG_REGISTRATION_ID %>'
            label="registrationform.billing.companyid"
            helpMessage="registrationform.billing.companyid.info"
            wrapperCssClass="form-group-item"
            disabled="<%=!canEditAddress%>"
            value='<%= billingInfo == null ? "" : billingInfo.getCompanyIdentifier()%>'
    />
</div>

<h3><strong><liferay-ui:message key="registrationform.billing.payment.details"/></strong></h3>

<div class="form-group-autofit">
    <aui:select
            name="addressSelection"
            type="select"
            label=""
            wrapperCssClass="commerce-form-group-item-row form-group-item">

        <aui:option value='0'
                    data-canEdit="<%=canEditAddress%>"
                    label ='<%=canEditAddress? "registrationform.billing.address.new" : "registrationform.billing.address.select"%>' />
        <%
            for (Address address : availableAddresses) {
        %>
        <aui:option data-city="<%= HtmlUtil.escapeAttribute(address.getCity()) %>"
                    data-country="<%= address.getCountryId() %>"
                    data-org-name=""
                    data-address-name="<%= HtmlUtil.escapeAttribute(address.getName()) %>"
                    data-phone-number='<%= HtmlUtil.escapeAttribute(address.getPhoneNumber()) %>'
                    data-region="<%= address.getRegionId() %>"
                    data-street-1="<%= HtmlUtil.escapeAttribute(address.getStreet1()) %>"
                    data-zip="<%= HtmlUtil.escapeAttribute(address.getZip()) %>"
                    data-canEdit="<%=canEditAddress%>"
                    label="<%= HtmlUtil.escape(address.getName()) %>"
                    selected="<%= address.getAddressId() == selectedAddressId %>"
                    value="<%= address.getAddressId() %>" />
        <%  }
        %>
    </aui:select>
</div>
<div class="account-fields">
    <div class="form-group-autofit">
        <aui:input
                name="<%= OrganizationConstants.ORG_NAME %>"
                label="registrationform.orgaddress.name"
                wrapperCssClass="form-group-item" type="hidden">
        </aui:input>

        <aui:input
                name="<%= OrganizationConstants.ORG_ADDRESS_NAME %>"
                label="registrationform.orgaddress.name" wrapperCssClass="form-group-item" >
        </aui:input>
    </div>
</div>
<div class="account-fields" >
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_STREET %>"
                label="registrationform.orgaddress"
                wrapperCssClass="form-group-item">
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_POSTAL %>"
                label="registrationform.orgpostcode"
                wrapperCssClass="form-group-item">
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_CITY %>"
                label="registrationform.orgcity"
                wrapperCssClass="form-group-item">
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">
        <aui:select label="registrationform.orgcountry"
                    name="<%=OrganizationConstants.ORG_COUNTRY_ID%>" placeholder="country" title="country" wrapperCssClass="form-group-item">
            <aui:validator errorMessage='<%= LanguageUtil.get(request, "please-enter-a-valid-country") %>' name="min">1</aui:validator>
        </aui:select>
        <aui:select label="registrationform.orgregion"
                    name="<%=OrganizationConstants.ORG_REGION%>" placeholder="region" title="region" wrapperCssClass="form-group-item" />
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">
        <aui:input
                name="<%= OrganizationConstants.ORG_PHONE %>"
                label="registrationform.phone" wrapperCssClass="form-group-item">
        </aui:input>

    </div>
</div>

<aui:script use="event, node, aui-base">

    //store the account information
    let addressSelection = document.getElementById("<portlet:namespace />addressSelection");
    addressSelection.onchange = function (event){
        RegistrationFormsUtil.addressSelectionChanged("<portlet:namespace />", event.target, "<%=paramName%>" )
    }

    RegistrationFormsUtil.loadCountrySelection("<portlet:namespace />",
        "<%=selectedAddress == null? 0 : selectedAddress.getCountryId()%>",
        "<%=selectedAddress == null? 0 : selectedAddress.getRegionId()%>",
        <%=displayContext.getCompanyId()%>
    );

    RegistrationFormsUtil.addressSelectionChanged("<portlet:namespace />", addressSelection, "<%=paramName%>")
</aui:script>