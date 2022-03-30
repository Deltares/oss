<div id="<portlet:namespace />course-conditions-div">
    <span>
        <liferay-ui:message key="dsd.registration.step5.conditions.title"/>
    </span>
    <aui:input
            name="course_conditions"
            label="dsd.registration.step5.conditions.description"
            type="checkbox">
                <aui:validator name="required">
                    function () {
                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 5);
                    }
                </aui:validator>
    </aui:input>
    <p>
        <aui:a href="<%=conditionsURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step5.conditions.link"/></aui:a>
    </p>
</div>
<span>
    <liferay-ui:message key="dsd.registration.step5.privacy.title"/>
</span>
<p>
    <liferay-ui:message key="dsd.registration.step5.privacy.body"/>
    <br><aui:a href="<%=privacyURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step5.privacy.link"/></aui:a>
    <br><aui:a href="<%=contactURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step5.contact.link"/></aui:a>
</p>