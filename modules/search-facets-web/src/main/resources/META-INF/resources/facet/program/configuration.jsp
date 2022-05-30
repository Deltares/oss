<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.program.UserProgramFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    UserProgramFacetConfiguration configuration =
            (UserProgramFacetConfiguration)
                    renderRequest.getAttribute(UserProgramFacetConfiguration.class.getName());

    String showRegistrationsMadeForOthers = null;
    String linkToRegistrationsPageForOthers = null;
    if (Validator.isNotNull(configuration)){
        showRegistrationsMadeForOthers = portletPreferences.getValue("showRegistrationsMadeForOthers", configuration.showRegistrationsMadeForOthers());
        linkToRegistrationsPageForOthers = portletPreferences.getValue("linkToRegistrationsPageForOthers", configuration.linkToRegistrationsPageForOthers());
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
                name="showRegistrationsMadeForOthers"
                label="Show My Programs list made for other users"
                type="checkbox"
                value='<%= showRegistrationsMadeForOthers %>'
        >
        </aui:input>

        <aui:input
                name="linkToRegistrationsPageForOthers"
                label="Link to MY PROGRAM page with registrations made for others"
                value='<%= linkToRegistrationsPageForOthers %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
