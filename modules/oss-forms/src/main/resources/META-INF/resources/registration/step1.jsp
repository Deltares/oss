<aui:row>
    <aui:col width="100">

        <aui:input name="org_domains" type="hidden" />
        <div class="row">
            <div class="col">
                <aui:select
                        name="select_organization"
                        type="select"
                        label=""
                        value="">
                    <aui:option value="-1" label ="registrationform.select.org" />
                </aui:select>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_name.name() %>"
                        label="registrationform.orgname"
                        value="">
                    <aui:validator name="required">
                            function () {
                                return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                            }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_vat.name() %>"
                        label="registrationform.orgtaxid"
                        value="">
                    <aui:validator name="maxLength">75</aui:validator>
                </aui:input>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <aui:input
                        name="<%= KeycloakUtils.ATTRIBUTES.org_address.name() %>"
                        label="registrationform.orgaddress"
                        value="">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">255</aui:validator>
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
                        value="">
                    <aui:validator name="required">
                                function () {
                                    return checkStep(CommonFormsUtil.getFormName('<portlet:namespace />'), 1);
                                }
                    </aui:validator>
                    <aui:validator name="maxLength">75</aui:validator>
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
                    <aui:validator name="minLength">
                        2
                    </aui:validator>
                    <aui:option value="" label ="registrationform.select.country" />
                    <% List<Country> countries = CountryServiceUtil.getCompanyCountries(themeDisplay.getCompanyId(), true); %>
                    <%    for (Country country : countries) { %>
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
                        value="">
                    <aui:validator name="maxLength">15</aui:validator>
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
    let accounts=<%=accountsJson%>;
    let orgReference = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_reference.name(), "")%>";
    let selectedIndex = 0;
    let orgSelection = document.getElementById("<portlet:namespace />select_organization");
    for (var i = 0; i < accounts.length; i++) {
        var option = document.createElement("option")
        option.label = accounts[i]["<%=KeycloakUtils.ATTRIBUTES.org_name.name()%>"];
        option.value = i
        orgSelection.add(option)

        if (orgReference === accounts[i]["<%=KeycloakUtils.ATTRIBUTES.org_reference.name()%>"]){
            selectedIndex = i;
        }
    }

    const accountSelectionChanged = function (orgSelection){

        let name = "";
        let vat = "";
        let website = "";
        let address = "";
        let postal = "";
        let city = "";
        let country = "";
        let phone = "";
        let domain = "";
        let accountIndex = orgSelection.value;
        if (accountIndex >= 0) {
            let account = accounts[accountIndex];
            name = account["<%=KeycloakUtils.ATTRIBUTES.org_name.name()%>"]? account["<%=KeycloakUtils.ATTRIBUTES.org_name.name()%>"]:"";
            vat = account["<%=KeycloakUtils.ATTRIBUTES.org_vat.name()%>"] ? account["<%=KeycloakUtils.ATTRIBUTES.org_vat.name()%>"]:"";
            website = account["<%=KeycloakUtils.ATTRIBUTES.org_website.name()%>"]? account["<%=KeycloakUtils.ATTRIBUTES.org_website.name()%>"]: "";
            address = account["<%=KeycloakUtils.ATTRIBUTES.org_address.name()%>"]? account["<%=KeycloakUtils.ATTRIBUTES.org_address.name()%>"]: "";
            postal = account["<%=KeycloakUtils.ATTRIBUTES.org_postal.name()%>"]? account["<%=KeycloakUtils.ATTRIBUTES.org_postal.name()%>"]:"";
            city = account["<%=KeycloakUtils.ATTRIBUTES.org_city.name()%>"]?account["<%=KeycloakUtils.ATTRIBUTES.org_city.name()%>"]:"";
            country = account["<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"]?account["<%=KeycloakUtils.ATTRIBUTES.org_country.name()%>"]:"";
            phone = account["<%=KeycloakUtils.ATTRIBUTES.org_phone.name()%>"]?account["<%=KeycloakUtils.ATTRIBUTES.org_phone.name()%>"]:"";
            domain = account["domains"];
        }
        document.getElementById("<portlet:namespace />org_domains").value = domain;
        document.getElementById("<portlet:namespace />org_name").value = name;
        document.getElementById("<portlet:namespace />org_vat").value = vat;
        document.getElementById("<portlet:namespace />org_website").value = website;
        document.getElementById("<portlet:namespace />org_address").value = address;
        document.getElementById("<portlet:namespace />org_postal").value = postal;
        document.getElementById("<portlet:namespace />org_city").value = city;
        document.getElementById("<portlet:namespace />org_country").value = country;
        document.getElementById("<portlet:namespace />org_phone").value = phone;

        document.getElementById("<portlet:namespace />org_name").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_vat").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_website").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_address").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_postal").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_city").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_country").disabled = accountIndex >= 0;
        document.getElementById("<portlet:namespace />org_phone").disabled = accountIndex >= 0;
    }

    if (accounts.length > 0 ){

        if (accounts.length === 1){
            orgSelection.selectedIndex = 1
        } else {
            orgSelection.selectedIndex = selectedIndex;
        }
        accountSelectionChanged(orgSelection.options[orgSelection.selectedIndex]);

        orgSelection.onclick = function (event){
            accountSelectionChanged(event.target)
        }
    } else if (<%=!attributes.isEmpty()%>) {
        document.getElementById("<portlet:namespace />org_name").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_name.name(), "")%>"
        document.getElementById("<portlet:namespace />org_vat").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_vat.name(), "")%>"
        document.getElementById("<portlet:namespace />org_website").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_website.name(), "")%>"
        document.getElementById("<portlet:namespace />org_address").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_address.name(), "")%>"
        document.getElementById("<portlet:namespace />org_postal").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_postal.name(), "")%>"
        document.getElementById("<portlet:namespace />org_city").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_city.name(), "")%>"
        document.getElementById("<portlet:namespace />org_country").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_country.name(), "")%>"
        document.getElementById("<portlet:namespace />org_phone").value = "<%=attributes.getOrDefault(KeycloakUtils.ATTRIBUTES.org_phone.name(), "")%>"
    }
</aui:script>