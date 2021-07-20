<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.date.DateRangeFacetConfiguration" %>

<%@ include file="/META-INF/resources/init.jsp" %>

<%
    DateRangeFacetConfiguration configuration =
            (DateRangeFacetConfiguration)
                    renderRequest.getAttribute(DateRangeFacetConfiguration.class.getName());
    String startDateConfig = null;
    String endDateConfig = null;
    String setStartNowConfig = null;

    if (Validator.isNotNull(configuration)) {
        startDateConfig = portletPreferences.getValue("startDate", configuration.startDate());
        endDateConfig = portletPreferences.getValue("endDate", configuration.endDate());
        setStartNowConfig = portletPreferences.getValue("setStartNow", configuration.setStartNow());
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
                name="startDate"
                label="Start search period (dd-mm-yyyy)"
                value="<%= startDateConfig %>">
        </aui:input>
        <aui:input
                name="endDate"
                label="End search period (dd-mm-yyyy)"
                value="<%= endDateConfig %>">
        </aui:input>
        <aui:input
                name="setStartNow"
                label="facet.start-date-now.label"
                type="checkbox"
                value="<%= setStartNowConfig %>">
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
