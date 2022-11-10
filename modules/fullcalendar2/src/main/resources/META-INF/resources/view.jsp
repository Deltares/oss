<%@ include file="init.jsp" %>
<div id="<portlet:namespace />fullcalendar"></div>

<aui:script>

	var calendarEl = document.getElementById("<portlet:namespace />fullcalendar");
	var props = {};
	props.baseUrl="<%= baseUrl %>";
	props.siteId="<%= siteId %>";
	props.eventId="<%= eventId %>";
	props.startDate="<%= startDate %>";
	props.defaultView="<%= defaultView %>";
	props.p_auth =  Liferay.authToken;


	construct = function(props){
		let eventUrl = props.baseUrl + '/events/' + props.siteId + '/' + props.eventId + '?p_auth=' + props.p_auth;
		var colorMap = JSON.parse('<%=colorMap%>');
		return {
			schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
			initialView:  props.defaultView,
			timeZone : 'local',
			// slotMinTime: '07:00:00',
			// slotMaxTime: '22:00:00',
			scrollTime: '08:00:00',
			defaultDate: props.startDate,
			weekends : false,
			businessHours: true,
			headerToolbar: {
				left: 'today prev,next',
				center: 'title',
				right: 'horizontalDay,horizontalWeek,verticalWeek,dayGridMonth'
			},
			views: {
				horizontalDay : {
					type : 'resourceTimeline',
					duration : {days : 1},
					buttonText : 'Horizontal: day',
					slotDuration: {hours: 1},
					slotLabelFormat: [
					{ day: '2-digit', month: 'short' },
					{ hour: 'numeric', minute: '2-digit', omitZeroMinute: true, meridiem: false, hour12: false}
					]
				},
				horizontalWeek : {
					type: 'resourceTimeline',
					duration : {days : 5},
					buttonText : 'week',
					slotDuration: {hours: 1},
					slotLabelFormat: [
						{ day: '2-digit', month: 'short' },
                        { hour: 'numeric', minute: '2-digit', omitZeroMinute: true, meridiem: false, hour12: false}
					]
				},
				verticalWeek : {
					type: 'timeGridWeek',
					buttonText : 'Vertical: week',
					slotDuration: {minutes: 30},
					slotLabelFormat: [
						{ hour: 'numeric', minute: '2-digit', omitZeroMinute: true, meridiem: false, hour12: false}
					]
				}
			},
			aspectRatio : 1.8,
			resourceGroupField : 'building',
			resources: {
				url: props.baseUrl + '/resources/' + props.siteId + '/' + props.eventId+ '?p_auth=' + props.p_auth,
				method: 'GET'
			},
			events: function (fetchInfo, successCallback, failureCallback) {
				$.ajax({
					url: eventUrl,
					type: 'GET',
					dataType: 'json',
					data: {
					start: fetchInfo.start.toISOString(),
					end: fetchInfo.end.toISOString()
					},
					success: function (doc) {
					let events = [];
					$(doc).each(function () {
					events.push(
					{
					resourceId: $(this).attr('resourceId'),
					id: $(this).attr('id'),
					start: $(this).attr('start'),
					end: $(this).attr('end'),
					url: $(this).attr('url'),
					title: $(this).attr('title'),
					color: colorMap[$(this).attr('type')]
					});
					});
					successCallback(events);
					},
					error: function (xhr, ajaxOptions, thrownError) {
						failureCallback(thrownError)
					}
				});
			},
			eventTimeFormat: {
				hour: 'numeric',
				minute: '2-digit',
				omitZeroMinute: true,
				meridiem: false,
				hour12: false,
				timeZoneName: 'short'
			}
		}
	};

	var content = construct(props);
	var calendar = new FullCalendar.Calendar(calendarEl, content);
    calendar.render();

</aui:script>
