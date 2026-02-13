<%@ page import="nl.deltares.model.RegistrationsInfo" %>
<%@ include file="init.jsp" %>
<%
    SubmitOrderDisplayContext displayContext = (SubmitOrderDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);

    RegistrationsInfo registrationsInfo = displayContext.getRegistrationsInfo();
%>
<clay:row>

    <div class="prose prose--app">
        <h3><liferay-ui:message key="registrationform.order.summary"/></h3>
    </div>

    <clay:col cssClass="commerce-checkout-summary" size="8">
        <div class="commerce-checkout-summary-body" id="<portlet:namespace />entriesContainer">
            <liferay-ui:search-container
                    cssClass="list-group-flush"
                    id="registrationItems"
            >
                <liferay-ui:search-container-results
                        results="<%= registrationsInfo.getRegistrations() %>"
                />
            </liferay-ui:search-container>
        </div>
    </clay:col>
</clay:row>
<%
    if (displayContext.showTerms()) {
%>
<clay:row>

    <div class="prose prose--app">
        <h3><liferay-ui:message key="registrationform.terms.header"/></h3>
    </div>
    <div class="form-group-autofit">
        <aui:input
                name ="course_conditions"
                label="dsd.registration.step6.conditions.description"
                type="checkbox"
                checked="false">
            <aui:validator name="required" />
        </aui:input>
        &nbsp;&nbsp;
        <aui:a href="<%=displayContext.getTermsURL()%>"
               target="_blank"
               label="registrationform.terms.view"/>
    </div>

</clay:row>
<%
    }
%>
<clay:row>
    <div class="prose prose--app">
        <h3><liferay-ui:message key="registrationform.privacy.header"/></h3>
    </div>
    <p><liferay-ui:message key="registrationform.privacy.text"/></p>

    <aui:a href="<%=displayContext.getPrivacyURL()%>" target="_blank" label="dsd.registration.step6.privacy.link" />
    &nbsp;&nbsp;
    <aui:a href="<%=displayContext.getContactURL()%>" target="_blank" label="dsd.registration.step6.contact.link" />

</clay:row>

