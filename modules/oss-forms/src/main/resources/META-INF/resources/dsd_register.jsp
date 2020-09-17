<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<%
    Map attributes = (Map) renderRequest.getAttribute("attributes");

    String registrationId = ParamUtil.getString(renderRequest, "articleId");
    String action = ParamUtil.getString(renderRequest, "action");
    DsdParserUtils dsdParserUtils = (DsdParserUtils) request.getAttribute("dsdParserUtils");
    Registration mainRegistration = null;
    Event event = null;
    if (registrationId != null) {
        mainRegistration = dsdParserUtils.getRegistration(themeDisplay.getScopeGroupId(), registrationId);
        event = dsdParserUtils.getEvent(mainRegistration.getGroupId(), String.valueOf(mainRegistration.getEventId()));
    }
%>

<portlet:actionURL name="/submit/register/form" var="submitRegisterForm"/>

<liferay-ui:success key="unregister-success" message="">
    <liferay-ui:message key="unregister-success" arguments="name of registration" />
</liferay-ui:success>

<liferay-ui:success key="registration-success" message="">
    <liferay-ui:message key="registration-success" arguments='<%= SessionMessages.get(liferayPortletRequest, "registration-success") %>' />
</liferay-ui:success>

<liferay-ui:error key="registration-failed">
    <liferay-ui:message key="registration-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "registration-failed") %>' />
</liferay-ui:error>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>' />
</liferay-ui:error>

<liferay-ui:error key="send-email-failed">
    <liferay-ui:message key="send-email-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "send-email-failed") %>' />
</liferay-ui:error>



<div class="bs-stepper">
<%--    <h2><liferay-ui:message key="dsd.registration.title"/></h2>--%>

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <a class="next-step enabled btn btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn btn-primary d-none">
            <liferay-ui:message key="register"/>
        </a>
    </div>

    <div class="flex-row justify-content-between bs-stepper-indicators py-3">
        <ul class="navbar navbar-nav">
            <li class="nav-item active icon-circle-blank">
                <a class="active" href="#stepper-step-1" title="Step 1">
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank" >
                <a href="#stepper-step-2" title="Step 2">
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank disabled" id="nav-stepper-step-3">
                <a href="#stepper-step-3" title="Step 3">
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
<%--            <li class="nav-item icon-circle-blank">--%>
<%--                <a href="#stepper-step-4" title="Step 4">--%>
<%--                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>--%>
<%--                </a>--%>
<%--            </li>--%>
            <li class="nav-item icon-circle-blank">
                <a href="#stepper-step-5" title="Step 5">
                    <span><liferay-ui:message key="dsd.registration.steps.step5"/></span>
                </a>
            </li>
        </ul>
    </div>

    <div class="registration-container">

        <aui:form action="<%= submitRegisterForm %>" name="fm">

            <aui:input
                    name="redirect"
                    type="hidden"
                    value="${registrationDisplayContext.getPortletRequest(renderRequest, action)}" />

            <aui:input name="articleId"
                       type="hidden"
                       value="<%= registrationId %>" />

            <aui:input name="action"
                       type="hidden"
                       value="<%= action %>" />

            <div class="tab-content">
                <div class="tab-pane active" role="tabpanel" id="stepper-step-1">
                    <%@ include file="registration/step1.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-2">
                    <%@ include file="registration/step2.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-3">
                    <%@ include file="registration/step3.jsp" %>
                </div>
<%--                <div class="tab-pane" role="tabpanel" id="stepper-step-4">--%>
<%--                    <%@ include file="registration/step4.jsp" %>--%>
<%--                </div>--%>
                <div class="tab-pane" role="tabpanel" id="stepper-step-5">
                    <%@ include file="registration/step5.jsp" %>
                </div>
            </div>
        </aui:form>

    </div>

    <div class="registration-controls d-flex justify-content-between">
        <a class="prev-step disabled btn btn-primary">
            <liferay-ui:message key="prev.step"/>
        </a>
        <a class="next-step enabled btn btn-primary">
            <liferay-ui:message key="next.step"/>
        </a>
        <a class="submit btn btn-primary d-none">
            <liferay-ui:message key="register"/>
        </a>
    </div>
</div>

<aui:script use="liferay-form">
    const FIRST_STEP_ERROR_MESSAGE = '<liferay-ui:message key="dsd.registration.step1.error"/>';

    validateFirstStep = function() {
        let isFirstStepValid = $('.parent-registration').is(':checked');
        if (!isFirstStepValid) {
            alert(FIRST_STEP_ERROR_MESSAGE);
        }
        return isFirstStepValid;
    }

    getFormName = function() {
        return "<portlet:namespace/>fm";
    };

    checkPrice = function() {
        let parent = $('.parent-registration');

        if (parseFloat(parent[0].getAttribute('data-price')) > 0){
            $('#nav-stepper-step-3').removeClass('disabled'); //remove
            return;
        }

        let children = $('.child-registration');

        $('#nav-stepper-step-3').addClass('disabled'); //add;

        $.each( children, function( i, child ) {
            if (child.checked && parseFloat(child.getAttribute('data-price')) > 0){
                $('#nav-stepper-step-3').removeClass('disabled'); //remove
            }
        });
    };

    updateBadge = function() {
        let showTitle = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.badge_title_setting.name()%>"]:checked').val();
        let nameSetting = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.badge_name_setting.name() %>"]:checked').val();
        let titles = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.academicTitle.name() %>"]').val();
        let firstName = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"]').val();
        let initials = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.initials.name() %>"]').val();
        let lastName = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.last_name.name() %>"]').val();
        let jobTitle = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.jobTitle.name() %>"]').val();
        let title = '';

        if (showTitle === 'yes') {
            title += titles + ' ';
        }

        if (nameSetting === 'name') {
            title += firstName;
        } else if (nameSetting === 'initials') {
            title += initials;
        } else if (nameSetting === 'both') {
            title += initials + ' (' + firstName + ')';
        }

        title += ' ' + lastName;

        $('#badge-title').text(title);
        $('#job-title').text(jobTitle);
    }

    updatePaymentAddress = function() {
        let checked = this.checked;
        let name = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"]').val();
        let address = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"]').val();
        let postCode = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"]').val();
        let city = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"]').val();
        let country = $('select[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_country.name() %>"]').val();
        let email = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.email.name() %>"]').val();

        if (!checked) {
            address = '';
            postCode = '';
            city = '';
            country = '';
            email = '';
        }

        let paymentNameInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_name.name() %>"]');
        let paymentAddressInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_address.name() %>"]');
        let paymentPostCodeInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_postal.name() %>"]');
        let paymentCityInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_city.name() %>"]');
        let paymentCountryInput = $('select[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_country.name() %>"]');
        let paymentEmailInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_email.name() %>"]');

        paymentNameInput.val(name);
        paymentAddressInput.val(address);
        paymentPostCodeInput.val(postCode);
        paymentCityInput.val(city);
        paymentCountryInput.val(country);
        paymentEmailInput.val(email);

        if (checked) {
            paymentNameInput.prop('disabled', true);
            paymentAddressInput.prop('disabled', true);
            paymentPostCodeInput.prop('disabled', true);
            paymentCityInput.prop('disabled', true);
            paymentCountryInput.prop('disabled', true);
            paymentEmailInput.prop('disabled', true);
        } else {
            paymentNameInput.prop('disabled', false);
            paymentAddressInput.prop('disabled', false);
            paymentPostCodeInput.prop('disabled', false);
            paymentCityInput.prop('disabled', false);
            paymentCountryInput.prop('disabled', false);
            paymentEmailInput.prop('disabled', false);
        }
    }

    $(document).ready(function() {
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
        $('.bs-stepper').formStepper(form);

        $('.update-badge').change(updateBadge);
        $('.child-registration').change(checkPrice);
        $('input[name="<portlet:namespace />use_organization_address"]').change(updatePaymentAddress);
        updateBadge();
        checkPrice();
    });
</aui:script>