<h3>
    <liferay-ui:message key="dsd.registration.step4.relevant.information"/>
</h3>
<aui:input
        name="course_conditions"
        label="dsd.registration.step4.newsletter.register"
        type="checkbox"/>
<h3>
    <liferay-ui:message key="dsd.registration.step4.networking"/>
</h3>

<p><liferay-ui:message key="dsd.registration.step4.networking.description"/></p>

<span><liferay-ui:message key="registrationform.orgname"/></span>

<div class="d-flex justify-content-start">
    <div class="pr-3">
        <aui:input
                name="share_name"
                label="yes"
                type="radio"
                value="yes" />
    </div>
    <div class="pr-3">
        <aui:input
                name="share_name"
                label="no"
                type="radio"
                value="no" />
    </div>
</div>

<span><liferay-ui:message key="registrationform.email"/></span>

<div class="d-flex justify-content-start">
    <div class="pr-3">
        <aui:input
                name="share_email"
                label="yes"
                type="radio"
                value="yes" />
    </div>
    <div class="pr-3">
        <aui:input
                name="share_email"
                label="no"
                type="radio"
                value="no" />
    </div>
</div>
