<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<%@ page import="nl.deltares.portal.constants.OrganizationConstants" %>
<aui:row>
    <aui:col width="100">
        <c:if test="${not empty attributes}">
            <c:set var="org_name" value="<%= attributes.get(OrganizationConstants.ORG_NAME) %>"/>
            <c:set var="org_address" value="<%= attributes.get(OrganizationConstants.ORG_STREET) %>"/>
            <c:set var="org_postal" value="<%= attributes.get(OrganizationConstants.ORG_POSTAL) %>"/>
            <c:set var="org_city" value="<%= attributes.get(OrganizationConstants.ORG_CITY) %>"/>
            <c:set var="org_country" value="<%= attributes.get(OrganizationConstants.ORG_COUNTRY_CODE) %>"/>
            <c:set var="org_phone" value="<%= attributes.get(OrganizationConstants.ORG_PHONE) %>"/>
            <c:set var="org_website" value="<%= attributes.get(OrganizationConstants.ORG_WEBSITE) %>"/>
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
                        name="<%= OrganizationConstants.FIRST_NAME %>"
                        label="registrationform.firstname"
                        value="<%= user.getFirstName() %>"
                        original_value="<%= user.getFirstName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.LAST_NAME %>"
                        label="registrationform.lastname"
                        value="<%= user.getLastName() %>"
                        original_value="<%= user.getLastName() %>"
                        disabled="true">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.EMAIL %>"
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
                    <aui:validator name="maxLength">254</aui:validator>
                </aui:input>
            </div>
        </div>

        <span><liferay-ui:message key="registrationform.organizationInfo"/></span>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_NAME %>"
                        label="registrationform.orgname"
                        value="${org_name}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_WEBSITE %>"
                        label="registrationform.orgwebsite"
                        value="${org_website}">
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>

        <aui:input
                name="<%= OrganizationConstants.ORG_STREET %>"
                label="registrationform.orgaddress"
                value="${org_address}">
            <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }

            </aui:validator>
            <aui:validator name="maxLength">255</aui:validator>
        </aui:input>

        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_POSTAL %>"
                        label="registrationform.orgpostcode"
                        value="${org_postal}">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">10</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_CITY %>"
                        label="registrationform.orgcity"
                        value="${org_city}">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:select
                        name="<%=OrganizationConstants.ORG_COUNTRY_CODE%>"
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
                    <% List<Country> countries = CountryServiceUtil.getCompanyCountries(themeDisplay.getCompanyId(), true); %>
                    <% for (Country country : countries) { %>
                    <aui:option value="<%=country.getA2()%>" label="<%= country.getName(locale) %>"/>
                    <% } %>
                </aui:select>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_PHONE %>"
                        label="registrationform.phone"
                        value="${org_phone}">
                    <aui:validator name="required">
                        function () {
                        return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 2);
                        }
                    </aui:validator>
                    <aui:validator name="maxLength">15</aui:validator>
                </aui:input>
            </div>
        </div>


    </aui:col>
</aui:row>