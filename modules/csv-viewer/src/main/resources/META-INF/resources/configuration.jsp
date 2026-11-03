<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.json.JSONException" %>
<%@ page import="java.util.*" %>
<%@ include file="init.jsp" %>

<%
    CsvViewerConfiguration configuration = (CsvViewerConfiguration) renderRequest.getAttribute(CsvViewerConfiguration.class.getName());

    //General
    String csvFileUrl = "";
    String password = "";
    String username = "";
    String displayColumns = "";
    String colorMap ="{}";

    //Timeline
    String resourceColumn = "";
    String eventTitleColumn = "";
    String eventStartColumn = "";
    String eventEndColumn = "";
    String eventColorColumn = "";

    if (Validator.isNotNull(configuration)) {
        csvFileUrl = portletPreferences.getValue("csvFileUrl", configuration.csvFileUrl());
        username = portletPreferences.getValue("username", configuration.username());
        password = portletPreferences.getValue("password", configuration.password());
        displayColumns = portletPreferences.getValue("displayColumns", configuration.displayColumns());
        resourceColumn = portletPreferences.getValue("resourceColumn", configuration.resourceColumn());
        eventTitleColumn = portletPreferences.getValue("eventTitleColumn", configuration.eventTitleColumn());
        eventStartColumn = portletPreferences.getValue("eventStartColumn", configuration.eventStartColumn());
        eventEndColumn = portletPreferences.getValue("eventEndColumn", configuration.eventEndColumn());
        eventColorColumn = portletPreferences.getValue("eventColorColumn", configuration.eventColorColumn());
        colorMap = portletPreferences.getValue("colorMap", configuration.colorMap());
    }

    List<String> colorValueKeys = CsvDataService.getColumnValueKeys(eventColorColumn, csvFileUrl, username, password);
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

    <aui:fieldset collapsible="true" label="General configuration" >
        <aui:input
            label="CSV file URL"
            name="csvFileUrl"
            type="text"
            value="<%= csvFileUrl %>"
        />

        <aui:input
            label="Username"
            name="username"
            type="text"
            value="<%= username %>"
        />
        <aui:input
                label="Password"
                name="password"
                type="password"
                value="<%= password %>"
        />

        <aui:input
            label="Display columns"
            name="displayColumns"
            type="text"
            value="<%= displayColumns %>"
            helpMessage="Comma-separated list of columns that should be displayed in the table."
        />

    </aui:fieldset>

    <aui:fieldset collapsible="true" label="Color Map">
        <%
            Map<String, String> map;
            try {
                map = JsonContentUtils.parseJsonToMap(colorMap);
            } catch (JSONException e) {
                map = new HashMap<>(0);
            }

            for (String key : colorValueKeys) {

        %>
        <aui:input
                label='Key'
                name='<%="col_key_" + ( key )%>'
                type="text"
                value='<%= key %>'
        />

        <aui:input
                label='Color'
                name='<%="col_value_" + ( key )%>'
                type="color"
                value='<%= map.get(key) %>'
        />

        <% } %>

    </aui:fieldset>

    <aui:fieldset collapsible="true" label="Time-line configuration">
        <aui:input
                label="Resource column"
                name="resourceColumn"
                type="text"
                value="<%= resourceColumn %>"
        />

        <aui:input
                label="Event Title column"
                name="eventTitleColumn"
                type="text"
                value="<%= eventTitleColumn %>"
        />
        <aui:input
                label="Event Start column"
                name="eventStartColumn"
                type="text"
                value="<%= eventStartColumn %>"
        />

        <aui:input
                label="Event End column"
                name="eventEndColumn"
                type="text"
                value="<%= eventEndColumn %>"
        />

        <aui:input
                label="Event Color column"
                name="eventColorColumn"
                type="text"
                value="<%= eventColorColumn %>"
        />


    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" value="Save" />
    </aui:button-row>
</aui:form>
