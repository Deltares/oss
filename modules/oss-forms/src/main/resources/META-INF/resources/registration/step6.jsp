<div id="<portlet:namespace/>course-conditions-div">
    <span>
        <liferay-ui:message key="dsd.registration.step6.conditions.title"/>
    </span>
    <aui:input
            name="course_conditions"
            label="dsd.registration.step6.conditions.description"
            type="checkbox">
                <aui:validator name="required">
                    function () {
                    return checkStep(CommonFormsUtil.getFormName("<portlet:namespace/>"), 6);
                    }
                </aui:validator>
    </aui:input>
    <p>
        <aui:a href="<%=conditionsURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step6.conditions.link"/></aui:a>
    </p>
</div>
<span>
    <liferay-ui:message key="dsd.registration.step6.privacy.title"/>
</span>
<p>
    <liferay-ui:message key="dsd.registration.step6.privacy.body"/>
    <br><aui:a href="<%=privacyURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step6.privacy.link"/></aui:a>
    <br><aui:a href="<%=contactURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step6.contact.link"/></aui:a>
</p>