<%@ include file="init.jsp"%>

<portlet:actionURL name="<%= OssConstants.SUBMIT_REGISTER_FORM_URL %>" var="submitRegisterForm"/>
<portlet:actionURL name="/submit/register/save_step" var="saveStepURL" />

<div class="row">
   <div class="commerce-checkout container-fluid container-fluid-max-xl">
        <c:choose>
            <c:when test="<%= ids == null || ids.isEmpty()%>">
                <div class="alert alert-info mx-auto">
                    <liferay-ui:message key="the-cart-is-empty" />
                    <liferay-ui:message key="please-add-products-to-proceed-with-the-checkout" />
                </div>
            </c:when>
            <c:otherwise>
                <ul class="commerce-multi-step-nav multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm">

                <%
                    int step = 1;
                    boolean complete = true;
                    List<DeltaresCheckoutStep> checkoutSteps = checkoutDisplayContext.getCheckoutSteps();
                    String currentCheckoutStepName = checkoutDisplayContext.getCurrentCheckoutStepName();
                    Iterator<DeltaresCheckoutStep> iterator = checkoutSteps.iterator();
                    while (iterator.hasNext()) {
                        DeltaresCheckoutStep checkoutStep = iterator.next();
                        String name = checkoutStep.getName();
                        if(!checkoutStep.isVisible(request, response)) continue;

                        String cssClass = "multi-step-item";

                        if (iterator.hasNext()) {
                            cssClass += " multi-step-item-expand";
                        }

                        if (currentCheckoutStepName.equals(name)) {
                            cssClass += " active";
                            complete = false;
                        }

                        if (complete) {
                            cssClass += " complete";
                        }
                %>

                    <li class="<%= cssClass %>">
                        <div class="multi-step-divider"></div>
                        <div class="multi-step-indicator">
                            <div class="multi-step-indicator-label" id="<portlet:namespace/>nav-stepper-step-1">
                                <span><liferay-ui:message key="<%= checkoutStep.getName() %>"/></span>
                            </div>
                            <span class="multi-step-icon" data-multi-step-icon="<%= step %>"></span>
                        </div>
                    </li>

                    <%
                            step++;
                        }
                    %>
                </ul>

                <span id="<portlet:namespace/>group-message-block"></span>

                <aui:form action="<%= saveStepURL %>" data-senna-off="<%= checkoutDisplayContext.isSennaDisabled() %>" name="fm" >
                    <aui:input name="checkoutStepName" type="hidden" value="<%= currentCheckoutStepName %>" />
                    <aui:input name="ids" type="hidden" value="<%= ids %>" />
                    <aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
                    <%
                        String error = null;
                        try {
                            checkoutDisplayContext.renderCurrentCheckoutStep(pageContext);
                        } catch (Exception e) {
                            error = e.getMessage();
                        }
                    %>
                    <c:if test="<%=error != null%>">
                        <liferay-ui:error message="<%= error %>" />
                    </c:if>

                    <c:if test="<%= checkoutDisplayContext.showControls() %>">
                        <hr style="margin-bottom: 1rem; margin-top: 1rem"/>
                    <aui:button-row>
                        <c:if test="<%= Validator.isNotNull(checkoutDisplayContext.getPreviousCheckoutStepName()) %>">
                            <aui:button cssClass="pull-left btn-primary" href="<%= previousStepURL %>" value="previous" />
                        </c:if>

                        <aui:button cssClass="pull-right btn-primary" name="continue" type="submit" value="continue" />
                    </aui:button-row>
                    </c:if>
                </aui:form>

            </c:otherwise>
        </c:choose>
    </div>
</div>
<aui:script>

    <c:if test='<%= SessionErrors.contains(request, RegistrationFormException.class) %>'>
        <%
            List<RegistrationFormException> errors = (List<RegistrationFormException>) SessionErrors.get(request, RegistrationFormException.class);
            for (RegistrationFormException error : errors) {
                String message = error.getMessage();
                if (message == null) continue;
                String replace = message.replace("\"", "'");
        %>
            CommonFormsUtil.writeError("<portlet:namespace />", "<%= replace %>");
        <%
            }
        %>
    </c:if>

</aui:script>