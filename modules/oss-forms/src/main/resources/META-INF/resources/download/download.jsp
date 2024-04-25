<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.model.subscriptions.SubscriptionSelection" %>
<%@ page import="nl.deltares.portal.model.impl.Terms" %>
<%@ page import="nl.deltares.portal.model.impl.Download" %>
<%@ page import="java.util.List" %>
<%@ page import="nl.deltares.portal.utils.DownloadUtils" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    String homeUrl = themeDisplay.getCDNBaseURL();
    String webUrl = themeDisplay.getPathFriendlyURLPublic();
    String groupUrl = themeDisplay.getSiteGroup().getFriendlyURL();


    Map attributes = (Map) renderRequest.getAttribute("attributes");
    String privacyURL = (String) renderRequest.getAttribute("privacyURL");
    String contactURL = (String) renderRequest.getAttribute("contactURL");
    String action = ParamUtil.getString(renderRequest, "action");
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
    List<Download> downloads = (List<Download>) request.getAttribute("downloads");
    List<SubscriptionSelection> subscriptionSelections = (List<SubscriptionSelection>) request.getAttribute("subscriptionSelections");
    final List<Terms> terms = (List) renderRequest.getAttribute("terms");

    boolean showLockTypes = (boolean) renderRequest.getAttribute("showLockTypes");
    boolean showLicenseTypes = (boolean) renderRequest.getAttribute("showLicenseTypes");
    boolean hasMultipleDownloadUrls = (boolean) renderRequest.getAttribute("hasMultipleDownloadUrls");
    DownloadUtils downloadUtils = null;
    if (hasMultipleDownloadUrls) {
         downloadUtils = (DownloadUtils) renderRequest.getAttribute("downloadUtils");
    }
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
            <liferay-ui:message key="last.step"/>
        </a>
    </div>

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav form-step">
            <li class="nav-item active icon-circle-blank" id="<portlet:namespace/>nav-stepper-step-1">
                <a class="active" href="#stepper-step-1" title="Step 1" >
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled"  id="<portlet:namespace/>nav-stepper-step-2">
                <a href="#stepper-step-2" title="Step 2" >
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-3">
                <a href="#stepper-step-3" title="Step 3" >
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-3b">
                <a href="#stepper-step-3b" title="Step 3b" >
                    <span><liferay-ui:message key="dsd.registration.steps.step3b"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-4">
                <a href="#stepper-step-4" title="Step 4" >
                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="<portlet:namespace/>nav-stepper-step-5">
                <a href="#stepper-step-5" title="Step 5" >
                    <span><liferay-ui:message key="dsd.registration.steps.step5"/></span>
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
        <a class="prev-step disabled btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <a class="next-step enabled btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn-primary d-none">
            <liferay-ui:message key="last.step"/>
        </a>
    </div>
</div>

<aui:script use="liferay-form">

    const validateFirstStep = function (){
        let FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="download.step1.error"/>'
        let isValid = DownloadFormsUtil.validateFirstStep(FIRST_STEP_ERROR_MESSAGE);
        if (! isValid ) {
            alert(FIRST_STEP_ERROR_MESSAGE);
        }
        return isValid;
    }

    const preSubmitAction = function (){
        shoppingCart.clearDownloadsCart();
    }

    $(document).ready(function() {
        let namespace = "<portlet:namespace />";
        let form = Liferay.Form.get(namespace + "fm").formValidator;
        form.validateFirstStep = validateFirstStep;
        form.preSubmitAction = preSubmitAction;
        $('.bs-stepper').formStepper(form);
        DownloadFormsUtil.checkSelection(namespace);
        let downloads = $(document.getElementsByClassName("download"));
        [...downloads].forEach(function (download) {
            download.onchange = function (){ DownloadFormsUtil.checkSelection(namespace)};
        });
        $(document.getElementById(namespace + "registration_other")).change(function() {
            CommonFormsUtil.registerOther(namespace);
        });
        $(document.getElementById(namespace + "use_organization_address")).change(function() {
            CommonFormsUtil.updatePaymentAddress(namespace, this.checked);
        });
        <c:if test='<%= !SessionErrors.isEmpty(liferayPortletRequest) %>'>shoppingCart.clearDownloadsCart()</c:if>
    });
</aui:script>