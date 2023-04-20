<div class="row">
    <span>
        <liferay-ui:message key="dsd.registration.step4.relevant.information"/>
        <p>
            <liferay-ui:message key="dsd.registration.step4.newsletter.register"/>:
        </p>
    </span>
</div>
<%
    for (SubscriptionSelection subscriptionSelection : subscriptionSelections) {
        final String name = subscriptionSelection.getName();
        final String id = "subscription-" + subscriptionSelection.getId();
%>
    <div class="row">
        <div class="float-left w-100">
            <aui:input
                    name="<%=id%>"
                    label="<%=name%>"
                    type="checkbox"
                    checked="<%=subscriptionSelection.isSelected()%>"
            />
        </div>
    </div>
<%
    }
%>