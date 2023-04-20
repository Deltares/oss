<%--@elvariable id="attributes" type="java.util.Map"--%>
<c:if test="${not empty attributes}">
    <c:set var="remarks" value='<%= attributes.get("remarks") %>'/>
</c:if>
<div class="row">
    <span>
        <p>
            <liferay-ui:message key="dsd.registration.step4.newsletter.register"/>:
        </p>
    </span>
</div>
<%
    for (SubscriptionSelection subscriptionSelection : subscriptionSelections) {
        final Boolean selected = subscriptionSelection.isSelected();
        final String name = subscriptionSelection.getName();
        final String id = "subscription-" + subscriptionSelection.getId();
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
<div class="row">
    <span>
        <liferay-ui:message key="dsd.registration.step4.remarks"/>
    </span>
    <div class="float-left w-100">
            <aui:input
                    name="remarks_registration"
                    label=""
                    type="textarea"
                    maxLength="255"
                    resizable="false"
                    value="${remarks}" />
    </div>
</div>
