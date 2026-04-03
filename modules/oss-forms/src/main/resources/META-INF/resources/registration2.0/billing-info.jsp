<%@ page import="java.util.Optional" %>
<%@ include file="init.jsp" %>
<div class="prose prose--app">
<h3><liferay-ui:message key="registrationform.billing.payment.details"/></h3>
</div>
<%
    BillingDetailsCheckoutStepDisplayContext displayContext = (BillingDetailsCheckoutStepDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    String paramName = displayContext.getParamName();

    BillingInfo billingInfo = displayContext.getBillingInfo();
    List<Address> availableAddresses = displayContext.getBillingAddresses();

    Optional<Address> optionalAddress = availableAddresses.stream().filter(address ->
            address.getAddressId() == billingInfo.getBillingAddressId()).findFirst();
    Address selectedAddress = optionalAddress.orElse(availableAddresses.stream().findFirst().orElse(null));
    long selectedAddressId = selectedAddress == null ? 0 : selectedAddress.getAddressId();

    boolean canEditAccount = displayContext.canEditAccount();
    boolean canAddAddress = displayContext.canAddAddress();
%>
<aui:input disabled="<%=availableAddresses.isEmpty() %>" name="<%= paramName %>" type="hidden" value="<%= selectedAddressId %>" />

<div class="form-group-autofit">
    <aui:input
            label="registrationform.email"
            name='<%=BillingConstants.EMAIL%>'
            value="<%= billingInfo.getEmail() %>" wrapperCssClass="form-group-item" >
        <aui:validator name="email" />
        <aui:validator name="required" />
    </aui:input>
</div>

<div class="form-group-autofit">
    <aui:input
            label="registrationform.firstname"
            name='<%=BillingConstants.FIRST_NAME%>'
            value="<%= billingInfo.getFirstName() %>" wrapperCssClass="form-group-item">
        <aui:validator name="required" />
    </aui:input>

    <aui:input
            label="registrationform.lastname"
            name='<%=BillingConstants.LAST_NAME%>'
            value="<%= billingInfo.getLastName() %>" wrapperCssClass="form-group-item">
        <aui:validator name="required" />
    </aui:input>
</div>

<div class="form-group-autofit">
    <aui:select
            name="<%=BillingConstants.PAYMENT_METHOD%>"
            type="select"
            label="registrationform.billing.payment.method"
            value='<%= billingInfo.getPreference() %>' wrapperCssClass="commerce-form-group-item-row form-group-item">
        <aui:option value="payLink" label="registrationform.billing.paymethod.link"/>
        <aui:option value="bankTransfer" label="registrationform.billing.paymethod.bank"/>
    </aui:select>

    <aui:input
            name='<%=BillingConstants.PAYMENT_REFERENCE%>'
            label="registrationform.billing.reference"
            helpMessage="registrationform.billing.reference.info"
            wrapperCssClass="form-group-item"
            value='<%= billingInfo.getReference() %>' >
        <aui:validator name="required" />
    </aui:input>

</div>

<div class="form-group-autofit">
    <aui:input
            name='<%=OrganizationConstants.ORG_VAT%>'
            label="registrationform.billing.vat"
            helpMessage="registrationform.billing.vat.info"
            wrapperCssClass="form-group-item"
            disabled="<%=!canEditAccount%>"
            value='<%= billingInfo.getVat()%>'
    />
</div>


<div class="form-group-autofit">
    <aui:input
            name="<%=BillingConstants.REMARKS%>"
            label="registrationform.billing.remarks"
            wrapperCssClass="form-group-item"
            value=""
            type="textarea"
    />
</div>

<h3><strong><liferay-ui:message key="registrationform.billing.address.select"/></strong></h3>

<div class="form-group-autofit">
    <aui:select
            name="addressSelection"
            type="select"
            label=""
            wrapperCssClass="commerce-form-group-item-row form-group-item">

        <aui:option value='0'
                    data-canEdit="<%=canAddAddress%>"
                    label ='<%=canAddAddress? "registrationform.billing.address.new" : "registrationform.billing.address.select"%>' />
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
                    data-canEdit="<%=displayContext.canEditAddress(address.getAddressId())%>"
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
            <aui:validator name="required" />
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_POSTAL %>"
                label="registrationform.orgpostcode"
                wrapperCssClass="form-group-item">
            <aui:validator name="required" />
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_CITY %>"
                label="registrationform.orgcity"
                wrapperCssClass="form-group-item">
            <aui:validator name="required" />
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">
        <aui:select label="registrationform.orgcountry"
                    name="<%=OrganizationConstants.ORG_COUNTRY_ID%>" placeholder="country" title="country" wrapperCssClass="form-group-item">
            <aui:validator errorMessage='<%= LanguageUtil.get(request, "please-enter-a-valid-country") %>' name="min">1</aui:validator>
            <aui:validator name="required" />
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
            <aui:validator name="required" />
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