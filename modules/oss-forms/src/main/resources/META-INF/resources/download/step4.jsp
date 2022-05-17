<div class="row">
    <span>
        <liferay-ui:message key="dsd.registration.step4.relevant.information"/>
        <p>
            <liferay-ui:message key="dsd.registration.step4.newsletter.register"/>:
        </p>
    </span>
</div>
<%
    for (Subscription subscription : subscriptionSelection.keySet()) {
        final Boolean selected = subscriptionSelection.get(subscription);
        final String name = subscription.getName();
        final String id = "subscription-" + subscription.getId();
%>
    <div class="row">
        <div class="float-left w-100">
            <aui:input
                    name="<%=id%>"
                    label="<%=name%>"
                    type="checkbox"
                    checked="<%=selected%>"
            />
        </div>
    </div>
<%
    }
%>