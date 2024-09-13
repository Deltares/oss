<%@ page import="java.util.Collections" %>
<%@ page import="com.liferay.portal.kernel.model.Country" %>
<%@ page import="com.liferay.portal.kernel.service.CountryServiceUtil" %>
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
                    <aui:option value="-1" label ="registrationform.select.custom.org" />
                    <%
                        List<Map<String, String>> accounts;
                        try {
                            accounts = JsonContentUtils.parseJsonArrayToMap(accountsJson);
                        } catch (Exception e) {
                            SessionErrors.add(liferayPortletRequest, "registration-failed", e.getMessage());
                            accounts = Collections.emptyList();
                        }
                        for (int i = 0; i < accounts.size(); i++) {
                     %>
                            <aui:option value="<%=i%>" label ="<%=accounts.get(i).get(KeycloakUtils.ATTRIBUTES.org_name.name())%>" />
                    <%  } %>
                </aui:select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.org_postal.name() %>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.org_city.name() %>"
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
                        name="<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"
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
                        name="<%= KeycloakUtils.ATTRIBUTES.org_phone.name() %>"
                        label="registrationform.phone"
                        value="" max="15">
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_website.name() %>"
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