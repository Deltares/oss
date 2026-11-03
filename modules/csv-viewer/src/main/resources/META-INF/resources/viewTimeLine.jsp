<%@ page import="nl.deltares.portal.utils.Period" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%

    //Timeline
    String resourceColumn = "";
    String eventTitleColumn = "";
    String eventStartColumn = "";
    String eventEndColumn = "";

    if (Validator.isNotNull(configuration)) {
        resourceColumn = portletPreferences.getValue("resourceColumn", configuration.resourceColumn());
        eventTitleColumn = portletPreferences.getValue("eventTitleColumn", configuration.eventTitleColumn());
        eventStartColumn = portletPreferences.getValue("eventStartColumn", configuration.eventStartColumn());
        eventEndColumn = portletPreferences.getValue("eventEndColumn", configuration.eventEndColumn());
    }

    List<String> resourceValues = CsvDataService.getColumnValueKeys(
            configuration.resourceColumn(), rows);
    Period viewPeriod = CsvDataService.getDateRange(eventStartColumn, eventEndColumn, rows);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String startDateString = simpleDateFormat.format(viewPeriod.getStartDate());
    String endDateString = simpleDateFormat.format(viewPeriod.getEndDate());
%>
<div id="<portlet:namespace />csv-timeline-view"></div>

<aui:script>

    var calendarEl = document.getElementById("<portlet:namespace />csv-timeline-view");
    var calendar = new FullCalendar.Calendar(calendarEl, {

        initialView: 'resourceTimelineYear',
        views: {
            resourceTimelineYear: {
                type: 'resourceTimeline',
                visibleRange: {
                    start: '<%= startDateString %>',
                    end: '<%= endDateString %>',
                },
                duration: {milliseconds: '<%= viewPeriod.getDuration() %>'},
                slotDuration : {year: 1}
            }
        },
        initialDate: '<%= startDateString %>',
        aspectRatio: 1.8,
        schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
        resourceGroupField: '<%= resourceColumn %>',
        resources: [
            <% for (String resource : resourceValues) {%>
                {
                    id: '<%=  resource %>',
                    title: '<%= resource %>'
                },
            <% } %>
        ],
        events: [
            <% for (JSONObject row : rows) {
                int rowNumber = row.getInt("rowNumber");
                Map<String, String> values = (Map<String, String>) row.get("values");

                RenderURL renderURL = renderResponse.createRenderURL();
                MutableRenderParameters renderParameters = renderURL.getRenderParameters();
                renderParameters.setValue("rowNumber", String.valueOf(rowNumber));
                renderParameters.setValue("mvcPath", "/viewEntry.jsp");
                String eventColor = "";
                String eventColorFieldValue = values.get(eventColorColumn);
                if (map.containsKey(eventColorFieldValue)) {
                    eventColor = map.get(eventColorFieldValue);
                }
            %>

    {
                id: '<%=  rowNumber %>',
                resourceIds: ['<%= values.get(resourceColumn) %>'],
                title: '<%= values.get(eventTitleColumn) %>',
                start: '<%= values.get(eventStartColumn) %>',
                end: '<%= values.get(eventEndColumn) %>',
                url: '<%= renderURL %>',
                color: '<%= eventColor %>'
            },
            <% } %>
        ]
    });

    calendar.render();

</aui:script>