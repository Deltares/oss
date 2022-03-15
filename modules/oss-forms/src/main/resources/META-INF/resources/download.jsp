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

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    DownloadSiteConfiguration configuration = configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

    String conditionsURL = configuration.conditionsURL();

    String homeUrl = themeDisplay.getCDNBaseURL();
    String webUrl = themeDisplay.getPathFriendlyURLPublic();
    String groupUrl = themeDisplay.getSiteGroup().getFriendlyURL();
    String privacyURL = homeUrl + webUrl + groupUrl + configuration.privacyURL();
    String contactURL = homeUrl + webUrl + groupUrl + configuration.contactURL();


    Map attributes = (Map) renderRequest.getAttribute("attributes");
    String action = ParamUtil.getString(renderRequest, "action");
    DsdParserUtils dsdParserUtils = (DsdParserUtils) request.getAttribute("dsdParserUtils");

%>

<portlet:actionURL name="/submit/download/form" var="submitDownloadForm"/>

<liferay-ui:success key="sendlink-success" message="">
    <liferay-ui:message key="sendlink-success" arguments="name of download" />
</liferay-ui:success>

<liferay-ui:error key="sendlink-failed">
    <liferay-ui:message key="sendlink-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "sendlink-failed") %>' />
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
            <liferay-ui:message key="register"/>
        </a>
    </div>

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav">
            <li class="nav-item active icon-circle-blank">
                <a class="active" href="#stepper-step-1" title="Step 1" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled"  id="nav-stepper-step-2">
                <a href="#stepper-step-2" title="Step 2" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="nav-stepper-step-3">
                <a href="#stepper-step-3" title="Step 3" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="nav-stepper-step-4">
                <a href="#stepper-step-4" title="Step 4" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" id="nav-stepper-step-5">
                <a href="#stepper-step-5" title="Step 5" style="font-family:Open Sans,serif">
                    <span><liferay-ui:message key="dsd.registration.steps.step5"/></span>
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
                    <%@ include file="download/step1.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-2">
                    <%@ include file="download/step2.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-3">
                    <%@ include file="download/step3.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-4">
                    <%@ include file="download/step4.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-5">
                    <%@ include file="download/step5.jsp" %>
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
            <liferay-ui:message key="register"/>
        </a>
    </div>
</div>

<aui:script use="liferay-form">
    const FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="download.step1.error"/>';
    validateFirstStep = function() {
        let isFirstStepValid = $('.download').is(':checked');
        if (!isFirstStepValid) {
            alert(FIRST_STEP_ERROR_MESSAGE);
            return isFirstStepValid;
        }
        return isFirstStepValid;
    }

    getFormName = function() {
        return "<portlet:namespace/>fm";
    };

    checkSelection = function() {
        let downloads = $('.download');

        let userInfoEnabled = false;
        let subscriptionEnabled = false;
        let billingInfoEnabled = false;
        let termsEnabled = false;
        $.each( downloads, function( i, download ) {
            if (download.checked){

                if ( download.getAttribute('userinfo') === "true" ) {
                    userInfoEnabled = true;
                }

                if ( download.getAttribute('subscription') === "true" ) {
                    subscriptionEnabled = true;
                }
                if ( download.getAttribute('billinginfo') === "true" ) {
                    billingInfoEnabled = true;
                }

                if ( download.getAttribute('terms') === "true" ) {
                    termsEnabled = true;
                }
            }

        });
        if (userInfoEnabled){
            $('#nav-stepper-step-2').removeClass('disabled'); //remove
        } else {
            $('#nav-stepper-step-2').addClass('disabled'); //add;
        }

        if (billingInfoEnabled){
            $('#nav-stepper-step-3').removeClass('disabled'); //remove
        } else {
            $('#nav-stepper-step-3').addClass('disabled'); //add;
        }

        if (subscriptionEnabled){
            $('#nav-stepper-step-4').removeClass('disabled'); //remove
        } else {
            $('#nav-stepper-step-4').addClass('disabled'); //add;
        }

        if (termsEnabled){
            $('#course-conditions-div')[0].hidden = false;
            $('input[name="<portlet:namespace />course_conditions"]')[0].disabled = false;
        } else {
            $('#course-conditions-div')[0].hidden = true;
            $('input[name="<portlet:namespace />course_conditions"]')[0].disabled = true;
        }
    };

    updatePaymentAddress = function() {
        let checked = this.checked;

        let paymentNameInput = $('input[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_name.name() %>"]');
        let paymentAddressInput = $('input[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_address.name() %>"]');
        let paymentPostCodeInput = $('input[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_postal.name() %>"]');
        let paymentCityInput = $('input[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_city.name() %>"]');
        let paymentCountryInput = $('select[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_country.name() %>"]');
        let paymentEmailInput = $('input[name="<portlet:namespace /><%= BillingInfo.ATTRIBUTES.billing_email.name() %>"]');

        if (checked) {

            //cache billing info
            paymentNameInput.prop('billing_value', paymentNameInput.val());
            paymentAddressInput.prop('billing_value', paymentAddressInput.val());
            paymentPostCodeInput.prop('billing_value', paymentPostCodeInput.val());
            paymentCityInput.prop('billing_value', paymentCityInput.val());
            paymentCountryInput.prop('billing_value',  paymentCountryInput.val());
            paymentEmailInput.prop('billing_value',  paymentEmailInput.val());

            //replace billing info with user attributes info
            let name = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"]').val();
            let address = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"]').val();
            let postCode = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"]').val();
            let city = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"]').val();
            let country = $('select[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_country.name() %>"]').val();
            let email = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.email.name() %>"]').val();

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);

            paymentNameInput.prop('disabled', true);
            paymentAddressInput.prop('disabled', true);
            paymentPostCodeInput.prop('disabled', true);
            paymentCityInput.prop('disabled', true);
            paymentCountryInput.prop('disabled', true);
            paymentEmailInput.prop('disabled', true);

        } else {
            //restore billing info
            let name = paymentNameInput.prop('billing_value');
            let address = paymentAddressInput.prop('billing_value');
            let postCode = paymentPostCodeInput.prop('billing_value');
            let city = paymentCityInput.prop('billing_value');
            let country = paymentCountryInput.prop('billing_value');
            let email = paymentEmailInput.prop('billing_value');

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);

            paymentNameInput.prop('disabled', false);
            paymentAddressInput.prop('disabled', false);
            paymentPostCodeInput.prop('disabled', false);
            paymentCityInput.prop('disabled', false);
            paymentCountryInput.prop('disabled', false);
            paymentEmailInput.prop('disabled', false);

        }
    }

    preSubmitAction = function() {
        shoppingCart.clearCart();
    }

    $(document).ready(function() {
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
        $('.bs-stepper').formStepper(form);

        $('.download').change(checkSelection);
        // $('.clear-cart').on('click', function(){
        //     shoppingCart.clearCart()
        // });
        $('input[name="<portlet:namespace />use_organization_address"]').change(updatePaymentAddress);
        checkSelection();

        <c:if test='<%= !SessionErrors.isEmpty(liferayPortletRequest) %>'>shoppingCart.clearDownloadsCart()</c:if>
    });
</aui:script>