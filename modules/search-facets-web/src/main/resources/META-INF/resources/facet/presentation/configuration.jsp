<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.presentation.PresentationFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    PresentationFacetConfiguration configuration =
            (PresentationFacetConfiguration)
                    renderRequest.getAttribute(PresentationFacetConfiguration.class.getName());

    String visible = null;
    String defaultValue = null;
    if (Validator.isNotNull(configuration)){
        visible = portletPreferences.getValue("visible", configuration.visible());
        defaultValue = portletPreferences.getValue("defaultValue", configuration.defaultValue());
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
                label="Show presentation portlet"
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
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
