<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<aui:row>
    <aui:col width="100">
        <c:if test="${not empty attributes}">
            <c:set var="academicTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.academicTitle.name()) %>"/>
            <c:set var="initials" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.initials.name()) %>"/>
            <c:set var="jobTitle" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.jobTitle.name()) %>"/>
            <c:set var="org_name" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>"/>
            <c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
            <c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
            <c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
            <c:set var="country" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()) %>"/>
        </c:if>
        <span><liferay-ui:message key="registrationform.userInfo"/></span>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.academicTitle.name() %>"
                        label="registrationform.academic.titles"
                        value="${academicTitle}"/>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.initials.name() %>"
                        label="registrationform.initials"
                        value="${initials}"/>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"
                        label="registrationform.firstname"
                        value="<%= user.getFirstName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.last_name.name() %>"
                        label="registrationform.lastname"
                        value="<%= user.getLastName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
        </div>

        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.email.name() %>"
                label="registrationform.email"
                value="<%= user.getEmailAddress() %>"
                disabled="true">
            <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
            </aui:validator>
            <aui:validator name="email"/>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="username"
                        label="registrationform.username"
                        value="<%= user.getScreenName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.jobTitle.name() %>"
                        label="registrationform.job.titles"
                        value="${jobTitle}"/>
            </div>
        </div>

        <span><liferay-ui:message key="registrationform.organizationInfo"/></span>

        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"
                label="registrationform.orgname"
                value="${org_name}">
            <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
            </aui:validator>
        </aui:input>
        <aui:input
                name="<%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"
                label="registrationform.orgaddress"
                value="${org_address}">
            <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }

            </aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"
                        label="registrationform.orgpostcode"
                        value="${org_postal}">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"
                        label="registrationform.orgcity"
                        value="${org_city}">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <aui:select
                name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"
                type="select"
                label="registrationform.orgcountry"
                value="${country}">
            <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
            </aui:validator>
            <aui:validator name="minLength">
                2
            </aui:validator>
            <aui:option value="" label="registrationform.select.country"/>
            <% List<Country> countries = CountryServiceUtil.getCountries(true); %>
            <% for (Country country : countries) { %>
            <aui:option value="<%=country.getName()%>" label="<%= country.getName(locale) %>"/>
            <% } %>
        </aui:select>

    </aui:col>
</aui:row>
