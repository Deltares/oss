<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.program.UserProgramFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    UserProgramFacetConfiguration configuration =
            (UserProgramFacetConfiguration)
                    renderRequest.getAttribute(UserProgramFacetConfiguration.class.getName());

    String visible = null;
    String showRegistrationsMadeForOthers = null;
    String excludedSiteGroupIds = "";
    String includedSiteGroupIds = "";

    if (Validator.isNotNull(configuration)){
        visible = portletPreferences.getValue("visible", configuration.visible());
        showRegistrationsMadeForOthers = portletPreferences.getValue("showRegistrationsMadeForOthers", configuration.showRegistrationsMadeForOthers());
        excludedSiteGroupIds = portletPreferences.getValue("excludedSiteGroupIds", configuration.excludedSiteGroupIds());
        includedSiteGroupIds = portletPreferences.getValue("includedSiteGroupIds", configuration.includedSiteGroupIds());
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
                name="showRegistrationsMadeForOthers"
                label="Show My Programs list made for other users"
                type="checkbox"
                value='<%= showRegistrationsMadeForOthers %>'
        >
        </aui:input>

        <aui:input
                label="Excluded site group IDs"
                name="excludedSiteGroupIds"
                helpMessage="Site group IDs of sites from which to exclude articles. Space separated"
                value='<%= excludedSiteGroupIds %>'
        >
        </aui:input>


        <aui:input
                label="Included site group IDs"
                name="includedSiteGroupIds"
                helpMessage="Site group IDs of sites from which to include articles. Space separated"
                value='<%= includedSiteGroupIds %>'
        >
        </aui:input>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
