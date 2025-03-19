<%@ page import="nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext" %>
<%@ page import="com.liferay.account.model.AccountEntry" %>
<%@ page import="com.liferay.portal.kernel.model.Address" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="nl.deltares.forms.constants.OrganizationConstants" %>
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

<h3><strong><liferay-ui:message key="registrationform.select.org"/></strong></h3>
<%
    Long selection = (Long) request.getAttribute("selectedAccountEntryId");
    long selectedAccountEntryId = selection != null ? selection : 0;
    AccountSelectionCheckoutStepDisplayContext displayContext = (AccountSelectionCheckoutStepDisplayContext)request
            .getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    String paramName = displayContext.getParamName();
    List<AccountEntry> accountEntries = displayContext.getAccountEntries();

    AccountEntry selectedAccountEntry = displayContext.getAccountEntry(selectedAccountEntryId);
    Address selectedAddress = null;
    if (selectedAccountEntry != null) {
        selectedAddress = selectedAccountEntry.getDefaultBillingAddress();
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
                Address address = accountEntry.getDefaultBillingAddress();
                boolean canEdit = displayContext.canEditAccount(user, accountEntry);
        %>
        <aui:option data-city="<%= HtmlUtil.escapeAttribute(address.getCity()) %>"
                    data-country="<%= HtmlUtil.escapeAttribute(String.valueOf(address.getCountryId())) %>"
                    data-name="<%= HtmlUtil.escapeAttribute(accountEntry.getName()) %>"
                    data-phone-number='<%= HtmlUtil.escapeAttribute(address.getPhoneNumber() == null ? "": address.getPhoneNumber()) %>'
                    data-region="<%= HtmlUtil.escapeAttribute(String.valueOf(address.getRegionId())) %>"
                    data-street-1="<%= HtmlUtil.escapeAttribute(address.getStreet1()) %>"
                    data-zip="<%= HtmlUtil.escapeAttribute(address.getZip()) %>"
                    data-website="<%= HtmlUtil.escapeAttribute(displayContext.getAccountWebsite(accountEntry.getAccountEntryId())) %>"
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

    </div>
</div>
<div class="account-fields" >
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_STREET %>"
                label="registrationform.orgaddress" wrapperCssClass="form-group-item">
        </aui:input>
    </div>
</div>
<div class="account-fields">
    <div class="form-group-autofit">

        <aui:input
                name="<%= OrganizationConstants.ORG_POSTAL %>"
                label="registrationform.orgpostcode" wrapperCssClass="form-group-item">
        </aui:input>
        <aui:input
                name="<%= OrganizationConstants.ORG_CITY %>"
                label="registrationform.orgcity" wrapperCssClass="form-group-item">
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
        <aui:input
                name="<%= OrganizationConstants.ORG_WEBSITE %>"
                label="registrationform.orgwebsite" wrapperCssClass="form-group-item">
            <aui:validator name="url" />
        </aui:input>

    </div>
</div>


<aui:script use="event, node, aui-base">

    //store the account information
    let accountSelection = document.getElementById("<portlet:namespace />accountSelection");
    accountSelection.onchange = function (event){
        RegistrationFormsUtil.accountSelectionChanged("<portlet:namespace />", event.target, "<%=paramName%>" )
    }

    RegistrationFormsUtil.loadCountrySelection("<portlet:namespace />",
    "<%=selectedAddress == null? 0 : selectedAddress.getCountry().getCountryId()%>",
    "<%=selectedAddress == null? 0 : selectedAddress.getRegionId()%>",
    <%=displayContext.getCompanyId()%>
    );

    RegistrationFormsUtil.accountSelectionChanged("<portlet:namespace />", accountSelection, "<%=paramName%>")
</aui:script>