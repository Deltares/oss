<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    CheckboxFacetConfiguration configuration =
            (CheckboxFacetConfiguration)
                    renderRequest.getAttribute(CheckboxFacetConfiguration.class.getName());

    String visible = null;
    String defaultValue = null;
    String title = "";
    String structureName = "";
    String fieldName = "";
    if (Validator.isNotNull(configuration)){
        visible = portletPreferences.getValue("visible", configuration.visible());
        defaultValue = portletPreferences.getValue("defaultValue", configuration.defaultValue());
        title = portletPreferences.getValue("title", configuration.title());
        structureName = portletPreferences.getValue("structureName", configuration.structureName());
        fieldName = portletPreferences.getValue("fieldName", configuration.fieldName());
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

    <aui:fieldset>

        <aui:input
                name="visible"
                label="Show portlet"
                type="checkbox"
                value='<%= visible %>'
        >
        </aui:input>

        <aui:input
                name="defaultValue"
                label="Default value"
                type="checkbox"
                value='<%= defaultValue %>'
        >
        </aui:input>

        <aui:input
                label="Title of facet"
                name="title"
                value='<%= title %>'
        >
        </aui:input>
        <aui:input
                label="Name of structure containing checkbox"
                name="structureName"
                value='<%= structureName %>'
        >
        </aui:input>
        <aui:input
                label="Name of field containing checkbox"
                name="fieldName"
                value='<%= fieldName %>'
        >
        </aui:input>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
