<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="nl.deltares.portal.configuration.DownloadSiteConfiguration" %>
<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());
    DownloadSiteConfiguration configuration = configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

    Map<String, String> templateMap = (Map<String,String>) renderRequest.getAttribute("templateMap");
    if (templateMap == null) templateMap = new HashMap<>();

    final List<String> languageIds = (List<String>) renderRequest.getAttribute("languageIds");
%>
<liferay-portlet:actionURL
        portletConfiguration="<%= true %>"
        var="configurationActionURL"
/>

<liferay-portlet:renderURL
        portletConfiguration="<%= true %>"
        var="configurationRenderURL"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
    <aui:input
            name="<%= Constants.CMD %>"
            type="hidden"
            value="<%= Constants.UPDATE %>"
    />

    <aui:input
            name="redirect"
            type="hidden"
            value="<%= configurationRenderURL %>"
    />

    <aui:fieldset>

        <aui:input
                label="download-url"
                name="downloadURL"
                value="<%= configuration.downloadURL() %>"/>
        <%
            Map<String, String> privacyURL = (Map<String,String>) renderRequest.getAttribute("privacyURL");
            for (String languageId : languageIds) {
                String name = "privacyURL-" + languageId;
        %>
        <aui:input
                label="privacy-url"
                prefix="<%=languageId%>"
                name="<%=name%>"
                value="<%= privacyURL.get(languageId) %>"/>
        <%
            }
        %>
        <%
            Map<String, String> contactURL = (Map<String,String>) renderRequest.getAttribute("contactURL");
            for (String languageId : languageIds) {
                String name = "contactURL-" + languageId;
        %>
        <aui:input
                label="contact-url"
                prefix="<%=languageId%>"
                name="<%=name%>"
                value="<%= contactURL.get(languageId) %>"/>
        <%
            }
        %>

        <aui:input
                label="download.configuration.sendFrom"
                name="sendFromEmail"
                value="<%= configuration.sendFromEmail() %>"/>

        <aui:input
                label="download.configuration.replyTo"
                name="replyToEmail"
                value="<%= configuration.replyToEmail() %>"/>

        <aui:input
                label="download.configuration.bccTo"
                name="bccToEmail"
                value="<%= configuration.bccToEmail() %>"/>

        <aui:input
                label="download.configuration.bannerURL"
                name="bannerURL"
                value="<%= configuration.bannerURL() %>"/>

        <aui:input
                label="download.email.enabled"
                name="enableEmails"
                type="checkbox"
                value="<%= configuration.enableEmails() %>"/>

        <table id="searchResultsMap" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Search Result Portlet ID</th>
                <th>Result Type</th>
            </tr>
            </thead>
            <tbody>
            <% int row = 0; %>
            <%    for (String portletId : templateMap.keySet()) { %>
            <tr>
                <td><aui:input type="text" name='<%="portletId-" + (row)%>' value="<%=portletId%>" label=""/></td>
                <td><aui:input type="text" name='<%="templateId-" + (row)%>' value="<%=templateMap.get(portletId)%>" label=""/></td>
            </tr>
            <%
                    row++;
                } %>
            <tr>
                <td><aui:input type="text" name='<%="portletId-" + (row)%>' value="enter id of search results portlet" label=""/></td>
                <td><aui:input type="text" name='<%="templateId-" + (row)%>' value="enter template key" label=""/></td>
            </tr>
            <tr>
                <td><button class="btn btn-lg btn-primary" type="submit" >Add Row</button></td>
            </tr>
            </tbody>
        </table>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>