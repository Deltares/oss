<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.portal.configuration.DownloadSiteConfiguration" %>
<%@ page import="com.liferay.portal.configuration.module.configuration.ConfigurationProvider" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());
    DownloadSiteConfiguration configuration = configurationProvider.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());

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

        <aui:input
                label="privacy-url"
                name="privacyURL"
                value="<%= configuration.privacyURL() %>"/>
        <aui:input
                label="contact-url"
                name="contactURL"
                value="<%= configuration.contactURL() %>"/>
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
                label="download.email.enabled"
                name="enableEmails"
                type="checkbox"
                value="<%= configuration.enableEmails() %>"/>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>