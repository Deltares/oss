<span>
    <strong><liferay-ui:message key="dsd.registration.step6.privacy.title"/></strong>
</span>
<p>
    <liferay-ui:message key="download.step6.privacy.body"/>
</p>
<p>
    <liferay-ui:message key="download.step6.privacy.link.body"/> <br>
    <aui:a href="<%=privacyURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step6.privacy.link"/></aui:a>
</p>
<p>
    <liferay-ui:message key="download.step6.contact.link.body"/> <br>
    <aui:a href="<%=contactURL%>" target="_blank"><liferay-ui:message key="dsd.registration.step6.contact.link"/></aui:a>
</p>
<%
    if (hasMultipleDownloadUrls) {
%>
<strong> <liferay-ui:message key="download.step6.country.title"/> </strong>
<%
        final List<String> downloadServerCountryCodes = downloadUtils.getDownloadServerCountryCodes();
        final String defaultCountryCode = downloadUtils.getDefaultCountryCode();
        for (String countryCode : downloadServerCountryCodes) {
            String countryName = downloadUtils.getDownloadServerCountryName(countryCode);

%>

<aui:row>
    <aui:col width="20">
        <aui:input
                name="download.countryCode"
                label="download.step6.country.name"
                type="radio"
                checked="<%=countryCode.equals(defaultCountryCode)%>"
                value="<%=countryCode%>"
        >
        </aui:input>
    </aui:col>
    <aui:col width="80">
        <%=countryName.toUpperCase()%>
    </aui:col>
</aui:row>

<%
        }
    }
%>
