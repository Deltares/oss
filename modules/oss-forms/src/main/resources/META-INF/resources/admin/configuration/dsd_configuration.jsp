<%@ include file="dsd_init.jsp" %>

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
                label="dsd.email.enabled"
                name="enableEmails"
                type="checkbox"
                value="<%= configuration.enableEmails() %>"/>

        <aui:input
                label="dsd.site"
                name="dsdSite"
                type="checkbox"
                value="<%= configuration.dsdSite() %>"/>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>