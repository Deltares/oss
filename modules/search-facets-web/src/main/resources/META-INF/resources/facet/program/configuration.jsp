<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.program.UserProgramFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    UserProgramFacetConfiguration configuration =
            (UserProgramFacetConfiguration)
                    renderRequest.getAttribute(UserProgramFacetConfiguration.class.getName());

    String showRegistrationsForOthers = null;
    if (Validator.isNotNull(configuration)){
        showRegistrationsForOthers = portletPreferences.getValue("showRegistrationsForOthers", configuration.showRegistrationsForOthers());
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
                name="showRegistrationsForOthers"
                label="Show registrations made for other users"
                type="checkbox"
                value='<%= showRegistrationsForOthers %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
