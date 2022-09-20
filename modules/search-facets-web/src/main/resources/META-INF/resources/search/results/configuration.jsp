<%@ page import="nl.deltares.search.results.SearchResultsPortletConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%

    SearchResultsPortletConfiguration configuration =
            (SearchResultsPortletConfiguration) renderRequest.getAttribute(SearchResultsPortletConfiguration.class.getName());

    String displayTemplate = "";
    String displayType = "";
    if (Validator.isNotNull(configuration)) {
        displayTemplate = portletPreferences.getValue("displayTemplate", configuration.displayTemplate());
        displayType = portletPreferences.getValue("displayType", configuration.displayType());
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
                name="displayTemplate"
                label="Display Template"
                value="<%= displayTemplate %>">
        </aui:input>

        <aui:select
                name="displayType"
                label="Display Type"
                type="select"
                cssClass='select'
                value="<%= displayType %>">
            <aui:option value="dsd" >Software Days</aui:option>
            <aui:option value="download" >Download</aui:option>
        </aui:select>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>
