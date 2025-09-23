<%@ include file="init.jsp" %>
<%
    SubmitOrderDisplayContext displayContext = (SubmitOrderDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);

    if (displayContext.showTerms()) {
%>
<h3><strong><liferay-ui:message key="registrationform.terms.header"/></strong></h3>

<div class="form-group-autofit">
    <aui:input
            name ="course_conditions"
            label="dsd.registration.step6.conditions.description"
            wrapperCssClass="form-group-item"
            type="checkbox"
            checked="false">
        <aui:validator name="required" />
    </aui:input>
</div>
<div class="form-group-autofit">
    <aui:a href="<%=displayContext.getTermsURL()%>"
           wrapperCssClass="form-group-item"
           label="registrationform.terms.view"/>
</div>
<%
    }
%>
<h3><strong><liferay-ui:message key="registrationform.privacy.header"/></strong></h3>

<div class="form-group-autofit">
    <p><liferay-ui:message key="registrationform.privacy.text"/></p>
</div>

<div class="form-group-autofit">
    <aui:a href="<%=displayContext.getPrivacyURL()%>" wrapperCssClass="form-group-item" target="_blank" label="dsd.registration.step6.privacy.link" />
</div>
<div class="form-group-autofit">
    <aui:a href="<%=displayContext.getContactURL()%>" wrapperCssClass="form-group-item" target="_blank" label="dsd.registration.step6.contact.link" />
</div>