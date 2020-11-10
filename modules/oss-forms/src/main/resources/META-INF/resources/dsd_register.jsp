<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<%
    Map attributes = (Map) renderRequest.getAttribute("attributes");
    String action = ParamUtil.getString(renderRequest, "action");
    DsdParserUtils dsdParserUtils = (DsdParserUtils) request.getAttribute("dsdParserUtils");
    Event event = dsdParserUtils.getEvent(themeDisplay.getScopeGroupId(), String.valueOf(configuration.eventId()));
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
        <a class="clear-cart enabled btn btn-primary">
            <liferay-ui:message key="clear.cart"/>
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

            <c:choose>
                <c:when test='<%= "register".equals(action) %>'>
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
    const FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING = '<liferay-ui:message key="dsd.registration.step1.error.missing.parent"/>';
    validateFirstStep = function() {
        let isFirstStepValid = $('.parent-registration').is(':checked');
        if (!isFirstStepValid) {
            alert(FIRST_STEP_ERROR_MESSAGE);
            return isFirstStepValid;
        }
        let registrations = $('.registration-item');
        $.each(registrations, function(i, registration) {
             let parentChecked = registration.getElementsByClassName("parent-registration")[0].checked;
             let childChecked = false;
             let children = Array.from(registration.getElementsByClassName('child-registration'));
             children.forEach(function(child) {
                if (child.checked){
                    childChecked = true;
                }
            })
            if (!parentChecked && childChecked){
                isFirstStepValid = false;
            }
        });
        if (!isFirstStepValid){
            alert(FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING)
        }
        return isFirstStepValid;
    }

    getFormName = function() {
        return "<portlet:namespace/>fm";
    };

    checkPrice = function() {
        let parents = $('.parent-registration');

        let priceEnabled = false;
        $.each( parents, function( i, parent ) {
            if (parent.checked && parseFloat(parent.getAttribute('data-price')) > 0){
                priceEnabled = true;
            }
        });

        let children = $('.child-registration');

        $.each( children, function( i, child ) {
            if (child.checked && parseFloat(child.getAttribute('data-price')) > 0){
                priceEnabled = true;
            }
        });
        if (priceEnabled){
            $('#nav-stepper-step-3').removeClass('disabled'); //remove
        } else {
            $('#nav-stepper-step-3').addClass('disabled'); //add;
        }
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

        let paymentNameInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_name.name() %>"]');
        let paymentAddressInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_address.name() %>"]');
        let paymentPostCodeInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_postal.name() %>"]');
        let paymentCityInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_city.name() %>"]');
        let paymentCountryInput = $('select[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_country.name() %>"]');
        let paymentEmailInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_email.name() %>"]');
        let paymentVatInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_vat.name() %>"]');
        let paymentRefInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.BILLING_ATTRIBUTES.billing_reference.name() %>"]');
        let userVatInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_vat.name() %>"]');
        let userRefInput = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.pay_reference.name() %>"]');

        if (checked) {

            //cache billing info
            paymentNameInput.prop('billing_value', paymentNameInput.val());
            paymentAddressInput.prop('billing_value', paymentAddressInput.val());
            paymentPostCodeInput.prop('billing_value', paymentPostCodeInput.val());
            paymentCityInput.prop('billing_value', paymentCityInput.val());
            paymentCountryInput.prop('billing_value',  paymentCountryInput.val());
            paymentEmailInput.prop('billing_value',  paymentEmailInput.val());
            paymentVatInput.prop('billing_value',  paymentVatInput.val());
            paymentRefInput.prop('billing_value',  paymentRefInput.val());

            //replace billing info with user attributes info
            let name = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"]').val();
            let address = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"]').val();
            let postCode = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"]').val();
            let city = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"]').val();
            let country = $('select[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_country.name() %>"]').val();
            let email = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.email.name() %>"]').val();
            let vat = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.org_vat.name() %>"]').val();
            let ref = $('input[name="<portlet:namespace /><%= KeycloakUtils.ATTRIBUTES.pay_reference.name() %>"]').val();

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);
            paymentVatInput.val(vat);
            paymentRefInput.val(ref);

            paymentNameInput.prop('disabled', true);
            paymentAddressInput.prop('disabled', true);
            paymentPostCodeInput.prop('disabled', true);
            paymentCityInput.prop('disabled', true);
            paymentCountryInput.prop('disabled', true);
            paymentEmailInput.prop('disabled', true);
            paymentVatInput.prop('disabled', false);  //must be editable
            paymentRefInput.prop('disabled', false);  //must be editable
        } else {
            //cache payment ref and vat
            userVatInput.val(paymentVatInput.val());
            userRefInput.val(paymentRefInput.val());

            //restore billing info
            let name = paymentNameInput.prop('billing_value');
            let address = paymentAddressInput.prop('billing_value');
            let postCode = paymentPostCodeInput.prop('billing_value');
            let city = paymentCityInput.prop('billing_value');
            let country = paymentCountryInput.prop('billing_value');
            let email = paymentEmailInput.prop('billing_value');
            let vat = paymentVatInput.prop('billing_value');
            let ref = paymentRefInput.prop('billing_value');

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);
            paymentVatInput.val(vat);
            paymentRefInput.val(ref);

            paymentNameInput.prop('disabled', false);
            paymentAddressInput.prop('disabled', false);
            paymentPostCodeInput.prop('disabled', false);
            paymentCityInput.prop('disabled', false);
            paymentCountryInput.prop('disabled', false);
            paymentEmailInput.prop('disabled', false);
            paymentVatInput.prop('disabled', false);
            paymentRefInput.prop('disabled', false);
        }
    }

    preSubmitAction = function() {
        shoppingCart.clearCart();
    }

    $(document).ready(function() {
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
        $('.bs-stepper').formStepper(form);

        $('.update-badge').change(updateBadge);
        $('.child-registration').change(checkPrice);
        $('.parent-registration').change(checkPrice);
        $('.clear-cart').on('click', function(){
            shoppingCart.clearCart()
        });
        $('input[name="<portlet:namespace />use_organization_address"]').change(updatePaymentAddress);
        updateBadge();
        checkPrice();

        <c:if test='<%= !SessionErrors.isEmpty(liferayPortletRequest) %>'>shoppingCart.clearCart()</c:if>
    });
</aui:script>