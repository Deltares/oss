<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>
<%@ page import="com.liferay.portal.configuration.module.configuration.ConfigurationProvider" %>

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
    <div class="lfr-form-content">
        <div class="sheet sheet-lg">
            <div aria-multiselectable="true" class>
        <aui:fieldset id="site_config" collapsible="true" label="Site config">
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
                    label="dsd.email.cancellationReplyTo"
                    name="cancellationReplyToEmail"
                    value="<%= configuration.cancellationReplyToEmail() %>"/>

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
        <aui:fieldset id="external_links" collapsible="true" label="External links">
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

        </aui:fieldset>
            </div>
        </div>
    </div>
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>