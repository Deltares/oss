<%@ include file="init.jsp" %>
<%
    JSONObject jsonCsv = (JSONObject) renderRequest.getAttribute("jsonCsv");
    int totalCount = jsonCsv != null ? jsonCsv.getInt("totalRows") : 0;

    List<JSONObject> rows = jsonCsv != null ? (List<JSONObject>) jsonCsv.get("rows") : Collections.emptyList();

    String tabSelection = (String) request.getAttribute("tabSelection");
    if (tabSelection == null || tabSelection.isEmpty()){
        tabSelection = "viewTable";
    }

    String cssViewTable = tabSelection.equals("viewTable") ? "active selected" : "";
    String cssViewTimeLine = tabSelection.equals("viewTimeLine") ? "active selected" : "";

    CsvViewerConfiguration configuration = (CsvViewerConfiguration) renderRequest.getAttribute(CsvViewerConfiguration.class.getName());
    //General
    String colorMap ="{}";

    //Timeline
    String eventColorColumn = "";

    if (Validator.isNotNull(configuration)) {
        eventColorColumn = portletPreferences.getValue("eventColorColumn", configuration.eventColorColumn());
        colorMap = portletPreferences.getValue("colorMap", configuration.colorMap());
    }
    Map<String, String> map;
    try {
        map = JsonContentUtils.parseJsonToMap(colorMap);
    } catch (JSONException e) {
        map = new HashMap<>(0);
    }
%>
<portlet:renderURL var="viewTableURL">
    <portlet:param name="tabSelection" value="viewTable"/>
</portlet:renderURL>
<portlet:renderURL var="viewTimeLineURL">
    <portlet:param name="tabSelection" value="viewTimeLine"/>
</portlet:renderURL>

<%
    if (!themeDisplay.isSignedIn()) {
%>
<div class="alert alert-info" ><liferay-ui:message key="not-logged-in"/></div>
<%
} else {
%>
<div class="portlet-navigation">
    <ul class="nav nav-pills navbar-site">
        <li class="lfr-nav-item nav-item <%=cssViewTable%>">
            <a href="<%= viewTableURL %> " class="nav-link text-truncate" >View Table</a>
        </li>
        <li class="lfr-nav-item nav-item <%=cssViewTimeLine%>">
            <a href="<%= viewTimeLineURL %> " class="nav-link text-truncate" >View Time Line</a>
        </li>
    </ul>
</div>
<% if (totalCount == 0) { %>
<div class="alert alert-info" >
    <liferay-ui:message key="no-csv-records" />
</div>
<%
} else if (tabSelection.equals("viewTable")) {
%>
    <%@ include file="viewTable.jsp" %>
<%
} else {
%>
    <%@ include file="viewTimeLine.jsp" %>
<%
        }
    }
%>