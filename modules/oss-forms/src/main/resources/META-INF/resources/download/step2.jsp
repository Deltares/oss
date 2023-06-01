<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<aui:row>
    <aui:col width="100">
        <c:if test="${not empty attributes}">
            <c:set var="org_name" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_name.name()) %>"/>
            <c:set var="org_address" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_address.name()) %>"/>
            <c:set var="org_postal" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_postal.name()) %>"/>
            <c:set var="org_city" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_city.name()) %>"/>
            <c:set var="org_country" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_country.name()) %>"/>
            <c:set var="org_phone" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_phone.name()) %>"/>
            <c:set var="org_website" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.org_website.name()) %>"/>
            <c:set var="phone" value="<%= attributes.get(KeycloakUtils.ATTRIBUTES.phone.name()) %>"/>
        </c:if>
        <span><liferay-ui:message key="registrationform.userInfo"/></span>
        <div class="row">
            <div class="col">
                <aui:input
                        name="registration_other"
                        label="registrationform.register.other"
                        type="checkbox"
                        checked="false"/>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.first_name.name() %>"
                        label="registrationform.firstname"
                        value="<%= user.getFirstName() %>"
                        original_value="<%= user.getFirstName() %>"
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
                        original_value="<%= user.getLastName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                </aui:input>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.email.name() %>"
                        label="registrationform.email"
                        value="<%= user.getEmailAddress() %>"
                        original_value="<%= user.getEmailAddress() %>"
                        disabled="true">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="email"/>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.phone.name() %>"
                        label="registrationform.phone"
                        value="${phone}"/>
            </div>
        </div>

        <span><liferay-ui:message key="registrationform.organizationInfo"/></span>

        <div class="row">
            <div class="col">
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
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_website.name() %>"
                        label="registrationform.orgwebsite"
                        value="${org_website}"/>
            </div>
        </div>

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
        <div class="row">
            <div class="col">
                <aui:select
                        name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"
                        type="select"
                        label="registrationform.orgcountry"
                        value="${org_country}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="minLength">
                        2
                    </aui:validator>
                    <aui:option value="" label="registrationform.select.country"/>
                    <% List<Country> countries = CountryServiceUtil.getCompanyCountries(themeDisplay.getCompanyId()); %>
                    <% for (Country country : countries) { %>
                    <aui:option value="<%=country.getName()%>" label="<%= country.getName(locale) %>"/>
                    <% } %>
                </aui:select>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_phone.name() %>"
                        label="registrationform.phone"
                        value="${org_phone}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                </aui:input>
            </div>
        </div>


    </aui:col>
</aui:row>