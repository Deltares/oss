<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<portlet:actionURL name="/submit/register/form" var="submitRegisterForm"/>

<portlet:renderURL var="addRegistrationURL">
	<portlet:param name="mvcPath" value="/add_dsd_registration.jsp"></portlet:param>
</portlet:renderURL>

<portlet:renderURL var="delRegistrationURL">
	<portlet:param name="mvcPath" value="/del_dsd_registration.jsp"></portlet:param>
</portlet:renderURL>

<liferay-ui:success key="registration-success" message="">
    <liferay-ui:message key="registration-success" arguments="<%= new String[]{user.getEmailAddress(), "todo"} %>" />
</liferay-ui:success>

<liferay-ui:error key="update-attributes-failed">
    <liferay-ui:message key="update-attributes-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "update-attributes-failed") %>' />
</liferay-ui:error>

<div class="bs-stepper">
    <h2><liferay-ui:message key="dsd.registration.title"/></h2>
    <div class="flex-row justify-content-between bs-stepper-indicators">
        <ul class="navbar navbar-nav">
            <li class="nav-item active icon-circle">
                <a class="active" href="#stepper-step-1" title="Step 1">
                    <span><liferay-ui:message key="dsd.registration.steps.step1"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank">
                <a href="#stepper-step-2" title="Step 2">
                    <span><liferay-ui:message key="dsd.registration.steps.step2"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank">
                <a href="#stepper-step-3" title="Step 3">
                    <span><liferay-ui:message key="dsd.registration.steps.step3"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank">
                <a href="#stepper-step-4" title="Step 4">
                    <span><liferay-ui:message key="dsd.registration.steps.step4"/></span>
                </a>
            </li>
            <li class="nav-item icon-circle-blank">
                <a href="#stepper-step-5" title="Step 5">
                    <span><liferay-ui:message key="dsd.registration.steps.step5"/></span>
                </a>
            </li>
        </ul>
    </div>

    <div class="registration-container">

        <aui:form action="<%= submitRegisterForm %>" name="fm">
            <div class="tab-content">
                <div class="tab-pane active" role="tabpanel" id="stepper-step-1">
                    <%@ include file="registration/step1.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-2">
                    <%@ include file="registration/step2.jsp" %>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-3">
                    <p>step 3</p>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-4">
                    <p>step 4</p>
                </div>
                <div class="tab-pane" role="tabpanel" id="stepper-step-5">
                    <p>step 5</p>
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

    updateBadge = function() {
        let showTitle = $('input[name="<portlet:namespace />show_badge_title"]:checked').val();
        let nameSetting = $('input[name="<portlet:namespace />name_setting"]:checked').val();
        let titles = $('input[name="<portlet:namespace />titles"]').val();
        let firstName = $('input[name="<portlet:namespace />firstName"]').val();
        let initials = $('input[name="<portlet:namespace />initials"]').val();
        let lastName = $('input[name="<portlet:namespace />lastName"]').val();
        let title = '';

        console.log('showTitle: ' + showTitle);
        console.log('nameSetting: ' + nameSetting);

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
    }

    $(document).ready(function() {
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
        $('.bs-stepper').formStepper(form);

        $('.update-badge').change(updateBadge);
        updateBadge();
    });
</aui:script>