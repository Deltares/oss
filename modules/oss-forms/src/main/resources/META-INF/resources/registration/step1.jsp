<%@ page import="java.util.Collections" %>
<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
<%@ page import="nl.deltares.portal.constants.OrganizationConstants" %>
<h3><strong><liferay-ui:message key="registrationform.select.org"/></strong></h3>
<aui:row>
    <aui:col width="100">

        <aui:input name="org_domains" type="hidden" />
        <div class="row">
            <div class="col">
                <aui:select
                        name="select_organization"
                        type="select"
                        label=""
                        value="-1">
                    <aui:option value='<%=attributes.getOrDefault(OrganizationConstants.ORG_EXTERNAL_REFERENCE_CODE, "0")%>' label ="registrationform.select.custom.org" />
                    <%
                        List<Map<String, String>> accounts;
                        try {
                            accounts = JsonContentUtils.parseJsonArrayToMap(accountsJson);
                        } catch (Exception e) {
                            SessionErrors.add(liferayPortletRequest, "registration-failed", e.getMessage());
                            accounts = Collections.emptyList();
                        }
                        for (Map<String, String> account : accounts) {
                     %>
                            <aui:option value='<%=account.get("accountEntryId")%>' label ="<%=account.get(OrganizationConstants.ORG_NAME)%>" />
                    <%  } %>
                </aui:select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_NAME %>"
                        label="registrationform.orgname"
                        value="" max="75">
                    <aui:validator name="required">
                            function () {
                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                            }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_STREET %>"
                        label="registrationform.orgaddress"
                        value="" max="255">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_POSTAL %>"
                        label="registrationform.orgpostcode"
                        value="">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">10</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_CITY %>"
                        label="registrationform.orgcity"
                        value="" max="75">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                }
                    </aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:select
                        name="<%=OrganizationConstants.ORG_COUNTRY_CODE%>"
                        type="select"
                        label="registrationform.orgcountry"
                        value="" >
                    <aui:validator name="required">
                        function () {
                            return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                        }
                    </aui:validator>
                    <aui:option value="" label ="registrationform.select.country" />
                    <% List<Country> countries = CountryServiceUtil.getCompanyCountries(themeDisplay.getCompanyId(), true);
                        for (Country country : countries) { %>
                            <aui:option value="<%=country.getA2()%>" label ="<%= country.getName(locale) %>" />
                    <% } %>
                </aui:select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_PHONE %>"
                        label="registrationform.phone"
                        value="" max="15">
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= OrganizationConstants.ORG_WEBSITE %>"
                        label="registrationform.orgwebsite"
                        value="">
                    <aui:validator name="url" />
                </aui:input>
            </div>
        </div>
    </aui:col>
</aui:row>
<aui:script use="event, node, aui-base">

    //store the account information
    let orgSelection = document.getElementById("<portlet:namespace />select_organization");
    orgSelection.onchange = function (event){
        DsdRegistrationFormsUtil.accountSelectionChanged("<portlet:namespace />", event.target)
        let form = Liferay.Form.get("<portlet:namespace />fm").formValidator;
            form.validate()
    }

</aui:script>