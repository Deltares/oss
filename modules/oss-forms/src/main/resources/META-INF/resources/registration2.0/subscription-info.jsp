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

<h3><strong><liferay-ui:message key="registrationform.subscription.information"/></strong></h3>
<strong><liferay-ui:message key="registrationform.subscription.information.sub"/></strong>
<br/>
<%
    SubscriptionsDisplayContext displayContext = (SubscriptionsDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
    List<SubscriptionSelection> subscriptionSelections = displayContext.getSubscriptions();

    for (SubscriptionSelection subscriptionSelection : subscriptionSelections) {

        final Boolean selected = subscriptionSelection.isSelected();
        final String name = subscriptionSelection.getName();
        final String id = "subscription-" + subscriptionSelection.getId();
%>
<div class="form-group-autofit">
    <aui:input
            name ="<%= id %>"
            label="<%= name %>"
            type="checkbox"
            checked="<%=selected%>" />
</div>
<%
    }
%>
