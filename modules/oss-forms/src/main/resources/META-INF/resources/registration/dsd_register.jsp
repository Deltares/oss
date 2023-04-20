<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>


<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="com.liferay.portal.kernel.util.DateUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="nl.deltares.portal.model.impl.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.model.subscriptions.SubscriptionSelection" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="nl.deltares.portal.utils.*" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    String remarks = "";
    Map<String, String> attributes = (Map) renderRequest.getAttribute("attributes");
    String conditionsURL = (String) renderRequest.getAttribute("conditionsURL");
    String privacyURL = (String) renderRequest.getAttribute("privacyURL");
    String contactURL = (String) renderRequest.getAttribute("contactURL");

    String action = ParamUtil.getString(renderRequest, "action");
    DsdParserUtils dsdParserUtils = (DsdParserUtils) request.getAttribute("dsdParserUtils");
    final List<SubscriptionSelection>  subscriptionSelections = (List) request.getAttribute("subscriptionSelection");
    Event event = null;
    try {
        event = dsdParserUtils.getEvent(themeDisplay.getScopeGroupId(), String.valueOf(request.getAttribute("eventId")), themeDisplay.getLocale());
    } catch (PortalException e) {
        SessionErrors.add(liferayPortletRequest, "registration-failed", e.getMessage());
    }
%>

<portlet:actionURL name="/submit/register/form" var="submitRegisterForm"/>

<liferay-ui:success key="unregister-success" message="">
    <liferay-ui:message key="unregister-success" arguments="name of registration"/>
</liferay-ui:success>

<liferay-ui:success key="registration-success" message="">
    <liferay-ui:message key="registration-success"
                        arguments='<%= SessionMessages.get(liferayPortletRequest, "registration-success") %>'/>
</liferay-ui:success>

<liferay-ui:error key="registration-failed">
    <liferay-ui:message key="registration-failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "registration-failed") %>'/>
</liferay-ui:error>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>'/>
</liferay-ui:error>

<liferay-ui:error key="send-email-failed">
    <liferay-ui:message key="send-email-failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "send-email-failed") %>'/>
</liferay-ui:error>


<div class="bs-stepper">
    <%--    <h2><liferay-ui:message key="dsd.registration.title"/></h2>--%>

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <%--        <a class="clear-cart enabled btn-lg btn-primary">--%>
        <%--            <liferay-ui:message key="clear.cart"/>--%>
        <%--        </a>--%>
        <a class="next-step enabled btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn-primary d-none">
            <liferay-ui:message key="register"/>
        </a>
    </div>

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav">
            <li class="nav-item active icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-1">
                <a class="active" href="#stepper-step-1" title="Step 1" >
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-2">
                <a href="#stepper-step-2" title="Step 2" >
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-3">
                <a href="#stepper-step-3" title="Step 3" >
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-4">
                <a href="#stepper-step-4" title="Step 4" >
                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-6">
                <a href="#stepper-step-6" title="Step 6" >
                    <span><liferay-ui:message key="dsd.registration.steps.step6"/></span>
                </a>
            </li>
        </ul>
    </div>

    <div class="registration-container">

        <aui:form action="<%= submitRegisterForm %>" name="fm">

            <c:choose>
                <c:when test='<%= "register".equals(action) %>'>
                    <aui:input name="ids"
                               type="hidden"
                               value='<%= ParamUtil.getString(renderRequest, "ids") %>'/>
                </c:when>
                <c:otherwise>
                    <aui:input name="articleId"
                               type="hidden"
                               value='<%= ParamUtil.getString(renderRequest, "articleId") %>'/>
                </c:otherwise>
            </c:choose>

            <aui:input name="action"
                       type="hidden"
                       value="<%= action %>"/>

            <div class="tab-content">
                <div class="tab-pane active" role="tabpanel" id="stepper-step-1">
                    <%@ include file="step1.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-2">
                    <%@ include file="step2.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-3">
                    <%@ include file="step3.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-4">
                    <%@ include file="step4.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-6">
                    <%@ include file="step6.jsp" %>
                </div>
            </div>
        </aui:form>

    </div>

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <a class="next-step enabled btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn-primary d-none">
            <liferay-ui:message key="register"/>
        </a>
    </div>
</div>

<aui:script use="liferay-form">

    validateFirstStep = function() {

        if (getCurrentStep("<portlet:namespace />fm") > 1) return true;

        let FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="dsd.registration.step1.error"/>';
        let FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING = '<liferay-ui:message key="dsd.registration.step1.error.missing.parent"/>';
        let errMessage = DsdRegistrationFormsUtil.validateFirstStep(FIRST_STEP_ERROR_MESSAGE, FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING);
        if (errMessage) {
            alert(errMessage);
        }
        registerOther();

        return errMessage == null;
    }

    preSubmitAction = function (){
        shoppingCart.clearCart();
    }

    checkSelection = function (){
        DsdRegistrationFormsUtil.checkSelection("<portlet:namespace />");
    }

    updateBadge = function (){
        DsdRegistrationFormsUtil.updateBadge('<portlet:namespace />');
    }

    registerOther = function (){
        let registerOther = $(document.getElementById("<portlet:namespace />registration_other"))[0].checked;
        let firstName = $(document.getElementById("<portlet:namespace />first_name"))[0];
        let lastName = $(document.getElementById("<portlet:namespace />last_name"))[0];
        let email = $(document.getElementById("<portlet:namespace />email"))[0];
        firstName.disabled = !registerOther;
        lastName.disabled = !registerOther;
        email.disabled = !registerOther;
        if (registerOther){
            firstName.classList.remove("disabled");
            lastName.classList.remove("disabled");
            email.classList.remove("disabled");
        } else {
            firstName.classList.add("disabled");
            lastName.classList.add("disabled");
            email.classList.add("disabled");

            firstName.value = firstName.getAttribute('original_value');
            lastName.value = lastName.getAttribute('original_value');
            email.value = email.getAttribute('original_value');
        }
    }

    $(document).ready(function() {
        let form = Liferay.Form.get("<portlet:namespace/>fm").formValidator;
        $('.bs-stepper').formStepper(form);
        updateBadge();
        checkSelection();
        $(document.getElementById("<portlet:namespace />use_organization_address")).change(function() {
            CommonFormsUtil.updatePaymentAddress('<portlet:namespace />', this.checked);
        });

        <c:if test='<%= !SessionErrors.isEmpty(liferayPortletRequest) %>'>shoppingCart.clearCart()</c:if>
    });
</aui:script>