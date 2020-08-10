<aui:row>
    <aui:col width="50">
        <span><liferay-ui:message key="registrationform.userInfo"/></span>

        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="salutation"
                        label="registrationform.salutation.mr"
                        type="radio"
                        value="" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="salutation"
                        label="registrationform.salutation.mrs"
                        type="radio"
                        value="" />
            </div>
            <div class="p-0">
                <aui:input
                        name="salutation"
                        label="registrationform.salutation.neutral"
                        type="radio"
                        value="" />
            </div>
        </div>

        <aui:input
                name="titles"
                label="registrationform.academic.titles"
                cssClass="update-badge"/>

        <aui:input
                name="initials"
                label="registrationform.initials"
                cssClass="update-badge"/>

        <aui:input
                name="firstName"
                label="registrationform.firstname"
                value="<%= user.getFirstName() %>"
                disabled="true">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="lastName"
                label="registrationform.lastname"
                value="<%= user.getLastName() %>"
                disabled="true">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="email"
                label="registrationform.email"
                value="<%= user.getEmailAddress() %>"
                disabled="true">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="username"
                label="registrationform.username"
                value="<%= user.getScreenName() %>"
                disabled="true">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <aui:input
                name="titles"
                label="registrationform.job.titles" />


        <span><liferay-ui:message key="registrationform.organizationInfo"/></span>

        <c:if test="${not empty attributes}">
            <c:set var="org_name" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>"/>
        </c:if>
        <aui:input
                name="<%=KeycloakUtils.ATTRIBUTES.org_name.name()%>"
                label="registrationform.orgname"
                value="${org_name}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <c:if test="${not empty attributes}">
            <c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
        </c:if>
        <aui:input
                name="<%=KeycloakUtils.ATTRIBUTES.org_address.name()%>"
                label="registrationform.orgaddress"
                value="${org_address}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <c:if test="${not empty attributes}">
            <c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
        </c:if>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_postal.name()%>"
                   label="registrationform.orgpostcode"
                   value="${org_postal}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

        <c:if test="${not empty attributes}">
            <c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
        </c:if>
        <aui:input name="<%=KeycloakUtils.ATTRIBUTES.org_city.name()%>"
                   label="registrationform.orgcity"
                   value="${org_city}">
            <aui:validator name="required">
                function () {
                return checkStep(getFormName(), 2);
                }
            </aui:validator>
        </aui:input>

    </aui:col>
    <aui:col width="50">

        <p><liferay-ui:message key="dsd.registration.step2.badge.title"/></p>

        <div class="card mb-3">
            <div class="card-header">$ {title} <span class="d-block">$ {year}</span></div>
            <div class="card-body">
                <h1 class="card-title" id="badge-title"></h1>
                <span class="card-text"></span>
            </div>
        </div>


        <span><liferay-ui:message key="dsd.registration.step2.show.title"/></span>
        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="show_badge_title"
                        label="yes"
                        cssClass="update-badge"
                        type="radio"
                        value="yes" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="show_badge_title"
                        label="no"
                        cssClass="update-badge"
                        type="radio"
                        value="no" />
            </div>
        </div>

        <span><liferay-ui:message key="dsd.registration.step2.badge.name"/></span>
        <div class="d-flex justify-content-start">
            <div class="pr-3">
                <aui:input
                        name="name_setting"
                        label="dsd.registration.step2.badge.name.1"
                        cssClass="update-badge checked"
                        type="radio"
                        value="name" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="name_setting"
                        label="dsd.registration.step2.badge.name.2"
                        cssClass="update-badge"
                        type="radio"
                        value="initials" />
            </div>
            <div class="pr-3">
                <aui:input
                        name="name_setting"
                        label="dsd.registration.step2.badge.name.3"
                        cssClass="update-badge"
                        type="radio"
                        value="both" />
            </div>
        </div>

    </aui:col>
</aui:row>
