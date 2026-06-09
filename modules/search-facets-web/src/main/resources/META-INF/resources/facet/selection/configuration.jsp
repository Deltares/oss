<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.selection.SelectionFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    SelectionFacetConfiguration sel_configuration =
            (SelectionFacetConfiguration)
                    renderRequest.getAttribute(SelectionFacetConfiguration.class.getName());

    String structureName = "";
    String fieldName = "";
    String picklistExternalIdentifier = "";
    String title = "";
    if (Validator.isNotNull(sel_configuration)) {
        title = portletPreferences.getValue("title", sel_configuration.title());
        structureName = portletPreferences.getValue("structureName", sel_configuration.structureName());
        picklistExternalIdentifier = portletPreferences.getValue("picklistExternalIdentifier", sel_configuration.picklistExternalIdentifier());
        fieldName = portletPreferences.getValue("fieldName", sel_configuration.fieldName());
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
                label='<%="Title of facet"%>'
                name='<%="title"%>'
                value='<%= title %>'
        >
        </aui:input>
        <aui:input
                label="Name of structure to use as terms in facet search"
                name="structureName"
                value='<%= structureName %>'
        >
        </aui:input>
        <aui:input
                label="Name of field to use as terms in facet search"
                name="fieldName"
                value='<%= fieldName %>'
        >
        </aui:input>

        <aui:input
                label="Name of picklist containing selection list"
                name="picklistExternalIdentifier"
                value='<%= picklistExternalIdentifier %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>
