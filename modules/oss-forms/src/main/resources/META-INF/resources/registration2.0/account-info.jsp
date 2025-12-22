<%@ include file="init.jsp" %>
<div class="prose prose--app">
<h3><liferay-ui:message key="registrationform.select.org"/></h3>
</div>
<%
    Long selection = (Long) request.getAttribute("selectedAccountEntryId");
    long selectedAccountEntryId = selection == null ? 0 : selection;
    AccountSelectionCheckoutStepDisplayContext displayContext = (AccountSelectionCheckoutStepDisplayContext)request
            .getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    String paramName = displayContext.getParamName();
    List<AccountEntry> accountEntries = displayContext.getAccountEntries();

    AccountEntry selectedAccountEntry;
    if (selectedAccountEntryId == 0 && !accountEntries.isEmpty()) {
        selectedAccountEntry = accountEntries.get(0);
        selectedAccountEntryId = selectedAccountEntry.getAccountEntryId();
    } else {
        selectedAccountEntry = displayContext.getAccountEntry(selectedAccountEntryId);
    }
    Address selectedAddress = null;
    if (selectedAccountEntry != null){
      selectedAddress = displayContext.getAccountAddress(selectedAccountEntry);
    }
    boolean canCreateNewAccount = displayContext.canCreateNewAccount();

%>

<aui:input disabled="<%=accountEntries.isEmpty() %>" name="<%= paramName %>" type="hidden" value="<%= selectedAccountEntryId %>" />

<div class="form-group-autofit">
    <aui:select
            name="accountSelection"
            type="select"
            label=""
            wrapperCssClass="commerce-form-group-item-row form-group-item">

        <aui:option value='0'
                    data-canEdit="<%=canCreateNewAccount%>"
                    label ='<%=canCreateNewAccount? "registrationform.select.custom.org" : "registrationform.select.custom.org1"%>' />
        <%
            for (AccountEntry accountEntry : accountEntries) {
                Address address = displayContext.getAccountAddress(accountEntry);
                String name = address == null ? "" : address.getName();
                String city = address == null ? "" : address.getCity();
                Long countryId = address == null ? 0 : address.getCountryId();
                String phoneNumber = address == null ? "" : address.getPhoneNumber();
                Long regionId = address == null ? 0 : address.getRegionId();
                String street = address == null ? "" : address.getStreet1();
                String zip = address == null ? "" : address.getZip();
                if (phoneNumber == null) {
                    phoneNumber = "";
                }
                boolean canEdit = accountEntry.isPersonalAccount();
        %>
        <aui:option data-city="<%= HtmlUtil.escapeAttribute(city) %>"
                    data-org-name="<%= HtmlUtil.escapeAttribute(accountEntry.getName()) %>"
                    data-address-name="<%= HtmlUtil.escapeAttribute(name) %>"
                    data-country="<%= countryId %>"
                    data-phone-number='<%= HtmlUtil.escapeAttribute(phoneNumber) %>'
                    data-region="<%= regionId %>"
                    data-street-1="<%= HtmlUtil.escapeAttribute(street) %>"
                    data-zip="<%= HtmlUtil.escapeAttribute(zip) %>"
                    data-website="<%= HtmlUtil.escapeAttribute(displayContext.getAccountWebsite(accountEntry)) %>"
                    data-vat="<%=HtmlUtil.escapeAttribute(accountEntry.getTaxIdNumber())%>"
                    data-canEdit="<%=canEdit%>"
                    label="<%= HtmlUtil.escape(accountEntry.getName()) %>"
                    selected="<%= accountEntry.getAccountEntryId() == selectedAccountEntryId %>"
                    value="<%= accountEntry.getAccountEntryId() %>" />
        <%  }
        %>
    </aui:select>
</div>
<div class="account-fields">
    <div class="form-group-autofit">
        <aui:input
            name="<%= OrganizationConstants.ORG_NAME %>"
            label="registrationform.orgname" wrapperCssClass="form-group-item">
        </aui:input>

        <aui:input
                name="<%= OrganizationConstants.ORG_ADDRESS_NAME %>"
                label="registrationform.orgaddress.name" wrapperCssClass="form-group-item" type="hidden">
        </aui:input>

    </div>
</div>
<div class="account-fields" >
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_STREET %>"
                label="registrationform.orgaddress" wrapperCssClass="form-group-item">
            <aui:validator name="required" />
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_POSTAL %>"
                label="registrationform.orgpostcode" wrapperCssClass="form-group-item">
            <aui:validator name="required" />
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_CITY %>"
                label="registrationform.orgcity" wrapperCssClass="form-group-item">
            <aui:validator name="required" />
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
            <aui:validator name="required" />
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_WEBSITE %>"
                label="registrationform.orgwebsite" wrapperCssClass="form-group-item">
            <aui:validator name="url" />
        </aui:input>

    </div>

    <div class="form-group-autofit">
        <aui:input
                name='<%=OrganizationConstants.ORG_VAT%>'
                label="registrationform.billing.vat"
                helpMessage="registrationform.billing.vat.info"
                wrapperCssClass="form-group-item"
                />
    </div>
</div>


<aui:script use="event, node, aui-base">

    //store the account information
    let accountSelection = document.getElementById("<portlet:namespace />accountSelection");
    accountSelection.onchange = function (event){
        RegistrationFormsUtil.accountSelectionChanged("<portlet:namespace />", event.target, "<%=paramName%>" )
    }

    RegistrationFormsUtil.loadCountrySelection("<portlet:namespace />",
    "<%=selectedAddress == null? 0 : selectedAddress.getCountryId()%>",
    "<%=selectedAddress == null? 0 : selectedAddress.getRegionId()%>",
    <%=displayContext.getCompanyId()%>
    );

    RegistrationFormsUtil.accountSelectionChanged("<portlet:namespace />", accountSelection, "<%=paramName%>")
</aui:script>