<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.registration.RegistrationFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    RegistrationFacetConfiguration reg_configuration =
            (RegistrationFacetConfiguration)
                    renderRequest.getAttribute(RegistrationFacetConfiguration.class.getName());

    String structureList = "";
    if (Validator.isNotNull(reg_configuration)){
        structureList = portletPreferences.getValue("structureList", reg_configuration.structureList());
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
                label="Structure names (lower case & space separated)"
                name="structureList"
                value='<%= structureList %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
