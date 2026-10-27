<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ include file="init.jsp" %>

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
            label="Filterable columns"
            name="filterableColumns"
            type="text"
            value="<%= filterableColumns %>"
            helpMessage="Comma-separated list of columns that should be filterable in the table."
        />

        <aui:input
            checked="<%= strictValidation %>"
            label="Strict validation"
            name="strictValidation"
            type="checkbox"
            value="true"
        />
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" value="Save" />
    </aui:button-row>
</aui:form>
