<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
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
<%@ page import="java.util.Iterator" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="nl.deltares.portal.constants.OssConfigurationConstants" %>
<%@ page import="nl.deltares.portal.constants.OssConstants" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    String remarks = "";
    Map<String, String> attributes = (Map) renderRequest.getAttribute("attributes");
    String conditionsURL = (String) renderRequest.getAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_CONDITIONS_URL);
    String privacyURL = (String) renderRequest.getAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_PRIVACY_URL);
    String contactURL = (String) renderRequest.getAttribute(OssConfigurationConstants.DSD_SITE_CONFIG_CONTACT_URL);
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

<span id="<portlet:namespace/>group-message-block"></span>

<aui:script>
<%
    Iterator<String> iterator = SessionErrors.iterator(liferayPortletRequest);
    for (Iterator<String> it = iterator; it.hasNext(); ) {
        String messageKey = it.next();
        String arguments = (String) SessionErrors.get(liferayPortletRequest, messageKey);
        if (arguments == null) arguments = "";
%>
    CommonFormsUtil.writeError("<portlet:namespace />", "<liferay-ui:message key="<%=messageKey%>" arguments="<%=arguments%>" />");
<%
    }
%>
</aui:script>
<portlet:actionURL name="<%= OssConstants.SUBMIT_REGISTER_FORM_URL %>" var="submitRegisterForm"/>

<liferay-ui:success key="unregister-success" message="">
    <liferay-ui:message key="unregister-success" arguments="name of registration"/>
</liferay-ui:success>

<liferay-ui:success key="registration-success" message="">
    <liferay-ui:message key="registration-success"
                        arguments='<%= SessionMessages.get(liferayPortletRequest, "registration-success") %>'/>
</liferay-ui:success>

<%--<liferay-ui:error key="registration-failed">--%>
<%--    <liferay-ui:message key="registration-failed"--%>
<%--                        arguments='<%= SessionErrors.get(liferayPortletRequest, "registration-failed") %>'/>--%>
<%--</liferay-ui:error>--%>

<%--<liferay-ui:error key="update-attributes-failed">--%>
<%--    <liferay-ui:message key="update-attributes-failed"--%>
<%--                        arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>'/>--%>
<%--</liferay-ui:error>--%>

<%--<liferay-ui:error key="send-email-failed">--%>
<%--    <liferay-ui:message key="send-email-failed"--%>
<%--                        arguments='<%= SessionErrors.get(liferayPortletRequest, "send-email-failed") %>'/>--%>
<%--</liferay-ui:error>--%>


<div class="bs-stepper">
    <%--    <h2><liferay-ui:message key="dsd.registration.title"/></h2>--%>

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

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav form-step">
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

    const validateFirstStep = function() {

        if (CommonFormsUtil.getCurrentStep("<portlet:namespace />fm") > 1) return true;

        let FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="dsd.registration.step1.error"/>';
        let FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING = '<liferay-ui:message key="dsd.registration.step1.error.missing.parent"/>';
        let errMessage = DsdRegistrationFormsUtil.validateFirstStep(FIRST_STEP_ERROR_MESSAGE, FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING);
        if (errMessage) {
            alert(errMessage);
        }
        // registerOther();
        return errMessage == null;
    }

    const preSubmitAction = function (){

    }

    let namespace = "<portlet:namespace />";
    let form = Liferay.Form.get(namespace + "fm");
    form.validateFirstStep = validateFirstStep;
    form.preSubmitAction = preSubmitAction;
    let stepper = document.querySelector('.bs-stepper')
    let formStepper = FormStepper.init(stepper, form)

    DsdRegistrationFormsUtil.updateBadge(namespace);
    DsdRegistrationFormsUtil.checkSelection(namespace);
    let badgeListeners = document.getElementsByClassName("update-badge");
    Array.from(badgeListeners).forEach(function (item) {
        item.addEventListener('change', function (){
            DsdRegistrationFormsUtil.updateBadge(namespace);
        });
    });
    let parents = document.getElementsByClassName("parent-registration");
    Array.from(parents).forEach(function (registration) {
        registration.addEventListener('change', function (){
            DsdRegistrationFormsUtil.checkSelection(namespace);
        });
    });
    let children = document.getElementsByClassName("child-registration");
    Array.from(children).forEach(function (registration) {
        registration.addEventListener('change', function (){
            DsdRegistrationFormsUtil.checkSelection(namespace);
        });
    });
    document.getElementById(namespace + "registration_other").addEventListener('change', function() {
        CommonFormsUtil.registerOther(namespace);
    });
    document.getElementById(namespace + "use_organization_address").addEventListener('change', function() {
        CommonFormsUtil.updatePaymentAddress(namespace, this.checked);
    });

</aui:script>