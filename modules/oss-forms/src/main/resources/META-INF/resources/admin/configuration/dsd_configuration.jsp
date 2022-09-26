<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider"  %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    DSDSiteConfiguration configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

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
                label="event-id"
                name="eventId"
                value="<%= configuration.eventId() %>"/>

        <aui:input
                label="mailing-ids"
                name="mailingIds"
                value="<%= configuration.mailingIds() %>"/>

        <aui:input
                label="registration-url"
                name="registrationURL"
                value="<%= configuration.registrationURL() %>"/>

        <aui:input
                label="bustransfer-url"
                name="busTransferURL"
                value="<%= configuration.busTransferURL() %>"/>

        <aui:input
                label="travelstay-url"
                name="travelStayURL"
                value="<%= configuration.travelStayURL() %>"/>

        <aui:input
                label="conditions-url"
                name="conditionsURL"
                value="<%= configuration.conditionsURL() %>"/>

        <aui:input
                label="privacy-url"
                name="privacyURL"
                value="<%= configuration.privacyURL() %>"/>

        <aui:input
                label="contact-url"
                name="contactURL"
                value="<%= configuration.contactURL() %>"/>

        <aui:input
                label="dsd.email.sendFrom"
                name="sendFromEmail"
                value="<%= configuration.sendFromEmail() %>"/>

        <aui:input
                label="dsd.email.replyTo"
                name="replyToEmail"
                value="<%= configuration.replyToEmail() %>"/>

        <aui:input
                label="dsd.email.bccTo"
                name="bccToEmail"
                value="<%= configuration.bccToEmail() %>"/>

        <aui:input
                label="dsd.email.enableBusInfo"
                name="enableBusInfo"
                type="checkbox"
                value="<%= configuration.enableBusInfo() %>"/>

        <aui:input
                label="dsd.email.enabled"
                name="enableEmails"
                type="checkbox"
                value="<%= configuration.enableEmails() %>"/>

        <aui:input
                label="dsd.site"
                name="dsdSite"
                type="checkbox"
                value="<%= configuration.dsdSite() %>"/>

        <aui:input
                label="dsd.structure.keys"
                name="dsdRegistrationStructures"
                value="<%= configuration.dsdRegistrationStructures() %>"/>

        <aui:input
                label="dsd.date.field"
                name="dsdRegistrationDateField"
                value="<%= configuration.dsdRegistrationDateField() %>"/>

        <aui:input
                label="dsd.type.field"
                name="dsdRegistrationTypeField"
                value="<%= configuration.dsdRegistrationTypeField() %>"/>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>