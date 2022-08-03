<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="nl.deltares.portal.configuration.DownloadSiteConfiguration" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.model.impl.Subscription" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="nl.deltares.portal.utils.EmailSubscriptionUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nl.deltares.portal.model.impl.Terms" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationException" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    DownloadSiteConfiguration configuration = null;
    try {
        configuration = configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
    } catch (ConfigurationException e) {
        System.err.println(e.getMessage());
    }

    String homeUrl = themeDisplay.getCDNBaseURL();
    String webUrl = themeDisplay.getPathFriendlyURLPublic();
    String groupUrl = themeDisplay.getSiteGroup().getFriendlyURL();
    String privacyURL = homeUrl + webUrl + groupUrl + configuration.privacyURL();
    String contactURL = homeUrl + webUrl + groupUrl + configuration.contactURL();


    Map attributes = (Map) renderRequest.getAttribute("attributes");
    String action = ParamUtil.getString(renderRequest, "action");
    DsdParserUtils dsdParserUtils = (DsdParserUtils) request.getAttribute("dsdParserUtils");
    EmailSubscriptionUtils subscriptionUtils = (EmailSubscriptionUtils) request.getAttribute("subscriptionUtils");
    final Map<Subscription, Boolean> subscriptionSelection = new HashMap<>();
    final List<Terms> terms = new ArrayList<>();

    boolean showLockTypes = false;
    boolean showLicenseTypes = false;

%>

<portlet:actionURL name="/submit/download/form" var="submitDownloadForm"/>

<liferay-ui:success key="sendlink-success" message="">
    <liferay-ui:message key="sendlink-success" arguments="name of download" />
</liferay-ui:success>

<liferay-ui:error key="sendlink-failed">
    <liferay-ui:message key="sendlink-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "sendlink-failed") %>' />
</liferay-ui:error>

<liferay-ui:error key="registerlink-failed">
    <liferay-ui:message key="registerlink-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "registerlink-failed") %>' />
</liferay-ui:error>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>' />
</liferay-ui:error>

<liferay-ui:error key="send-email-failed">
    <liferay-ui:message key="send-email-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "send-email-failed") %>' />
</liferay-ui:error>

<div class="bs-stepper">

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn-lg btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
<%--        <a class="clear-cart enabled btn-lg btn-primary">--%>
<%--            <liferay-ui:message key="clear.cart"/>--%>
<%--        </a>--%>
        <a class="next-step enabled btn-lg btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn-lg btn-primary d-none">
            <liferay-ui:message key="last.step"/>
        </a>
    </div>

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav">
            <li class="nav-item active icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-1">
                <a class="active" href="#stepper-step-1" title="Step 1" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled"  id="<portlet:namespace/>nav-stepper-step-2">
                <a href="#stepper-step-2" title="Step 2" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-3">
                <a href="#stepper-step-3" title="Step 3" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-3b">
                <a href="#stepper-step-3b" title="Step 3b" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step3b"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-4">
                <a href="#stepper-step-4" title="Step 4" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-5">
                <a href="#stepper-step-5" title="Step 5" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step5"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-6">
                <a href="#stepper-step-6" title="Step 6" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step6"/></span>
                </a>
            </li>
        </ul>
    </div>

    <div class="registration-container">

        <aui:form action="<%= submitDownloadForm %>" name="fm">

            <c:choose>
                <c:when test='<%= "download".equals(action) %>'>
                    <aui:input name="ids"
                               type="hidden"
                               value='<%= ParamUtil.getString(renderRequest, "ids") %>' />
                </c:when>
                <c:otherwise>
                    <aui:input name="articleId"
                               type="hidden"
                               value='<%= ParamUtil.getString(renderRequest, "articleId") %>' />
                </c:otherwise>
            </c:choose>

            <aui:input name="action"
                       type="hidden"
                       value="<%= action %>" />

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
                <div class="tab-pane" role="tabpanel" id="stepper-step-3b">
                    <%@ include file="step3b.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-4">
                    <%@ include file="step4.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-5">
                    <%@ include file="step5.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-6">
                    <%@ include file="step6.jsp" %>
                </div>
            </div>
        </aui:form>

    </div>

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn-lg btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <a class="next-step enabled btn-lg btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn-lg btn-primary d-none">
            <liferay-ui:message key="last.step"/>
        </a>
    </div>
</div>

<aui:script use="liferay-form">

    validateFirstStep = function (){
        let FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="download.step1.error"/>'
        let isValid = DownloadFormsUtil.validateFirstStep(FIRST_STEP_ERROR_MESSAGE);
        if (! isValid ) {
            alert(FIRST_STEP_ERROR_MESSAGE);
        }
        return isValid;
    }

    checkSelection = function (){
        DownloadFormsUtil.checkSelection("<portlet:namespace />");
    }
    preSubmitAction = function (){
        shoppingCart.clearDownloadsCart();
    }

    updateLockSelection = function (active) {
        $(document.getElementById("<portlet:namespace />lock-new"))[0].checked = (active === "lock-new");
        $(document.getElementById("<portlet:namespace />lock-existing"))[0].checked = (active === "lock-existing");
        $(document.getElementById("<portlet:namespace />lock-mac"))[0].checked = (active === "lock-mac");
    }

    updateLicenseSelection = function (active) {
        $(document.getElementById("<portlet:namespace />license-network"))[0].checked = (active === "license-network");
        $(document.getElementById("<portlet:namespace />license-standalone"))[0].checked = (active === "license-standalone");
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
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
        $('.bs-stepper').formStepper(form);
        checkSelection();
        $(document.getElementById("<portlet:namespace />use_organization_address")).change(function() {
            CommonFormsUtil.updatePaymentAddress('<portlet:namespace />', this.checked);
        });
        <c:if test='<%= !SessionErrors.isEmpty(liferayPortletRequest) %>'>shoppingCart.clearDownloadsCart()</c:if>
    });
</aui:script>