<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ include file="init.jsp" %>

<%
    CsvViewerConfiguration configuration = (CsvViewerConfiguration) renderRequest.getAttribute(CsvViewerConfiguration.class.getName());
    String csvFileUrl = "";
    String password = "";
    String username = "";
    String displayColumns = "";
    if (Validator.isNotNull(configuration)) {
        csvFileUrl = portletPreferences.getValue("csvFileUrl", configuration.csvFileUrl());
        username = portletPreferences.getValue("username", configuration.username());
        password = portletPreferences.getValue("password", configuration.password());
        displayColumns = portletPreferences.getValue("displayColumns", configuration.displayColumns());
    }
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

    <aui:fieldset collapsible="true" label="CSV Viewer configuration">
        <aui:input
            label="CSV file URL"
            name="csvFileUrl"
            type="text"
            value="<%= csvFileUrl %>"
        />

        <aui:input
            label="Username"
            name="username"
            type="text"
            value="<%= username %>"
        />
        <aui:input
                label="Password"
                name="password"
                type="password"
                value="<%= password %>"
        />

        <aui:input
            label="Display columns"
            name="displayColumns"
            type="text"
            value="<%= displayColumns %>"
            helpMessage="Comma-separated list of columns that should be displayed in the table."
        />

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" value="Save" />
    </aui:button-row>
</aui:form>
