<h3>
    <liferay-ui:message key="dsd.registration.step5.selected.events"/>

</h3>
<h3>
    <liferay-ui:message key="dsd.registration.step5.conditions.title"/>
</h3>
<aui:input
        name="course_conditions"
        label="dsd.registration.step5.conditions.description"
        type="checkbox">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 5);
                }
            </aui:validator>
</aui:input>
<h3>
    <liferay-ui:message key="dsd.registration.step5.privacy.title"/>
</h3>
<p><liferay-ui:message key="dsd.registration.step5.privacy.body"/></p>