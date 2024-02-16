<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.event.EventFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    EventFacetConfiguration reg_configuration =
            (EventFacetConfiguration)
                    renderRequest.getAttribute(EventFacetConfiguration.class.getName());

    String eventsList = "";
    if (Validator.isNotNull(reg_configuration)){
        eventsList = portletPreferences.getValue("eventsList", reg_configuration.eventsList());
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
                label="Overruling event IDs (space separated)"
                name="eventsList"
                value='<%= eventsList %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
