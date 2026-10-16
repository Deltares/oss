<%@ include file="init.jsp" %>
<div class="prose prose--app">
<h3><liferay-ui:message key="registrationform.subscription.information"/></h3>
<h4><liferay-ui:message key="registrationform.subscription.information.sub"/></h4>
</div>
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
