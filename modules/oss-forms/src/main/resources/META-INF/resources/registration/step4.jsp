<%
    boolean subscribed = (boolean) request.getAttribute("subscribed");
%>
<c:if test="${not empty attributes}">
    <c:set var="remarks" value="<%= attributes.get("remarks") %>"/>
</c:if>
<div class="row">
    <span>
        <liferay-ui:message key="dsd.registration.step4.relevant.information"/>
    </span>
    <div class="float-left w-100">
        <aui:input
                name="subscribe_newsletter"
                label="dsd.registration.step4.newsletter.register"
                type="checkbox"
                checked="${subscribed}"
        />
    </div>
</div>
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
