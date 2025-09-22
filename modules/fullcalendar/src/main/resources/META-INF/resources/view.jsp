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
				right: 'horizontalWeek,verticalWeek,dayGridMonth'
			},
			views: {
				horizontalWeek : {
					type: 'resourceTimeline',
					duration : {days : 5},
					buttonText : 'horizontal',
					slotDuration: {hours: 1},
					slotLabelFormat: [
						{ day: '2-digit', month: 'short' },
                        { hour: 'numeric', minute: '2-digit', omitZeroMinute: true, meridiem: false, hour12: false}
					]
				},
				verticalWeek : {
					type: 'timeGridWeek',
					buttonText : 'vertical',
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

                fetch(eventUrl, {
                    method: 'GET',
					data : {
						start: fetchInfo.start.toISOString(),
						end: fetchInfo.end.toISOString()
					},
					headers: {
                    	'Accept' : 'application/json'
				  	}
				})
				.then( response => {
					if (!response.ok) failureCallback(response.statusText);
					const contentType = response.headers.get('content-type');
					if (contentType && contentType.includes('application/json')) {
						return response.json();
					} else {
						return []
					}
				})
				.then(data => {
                    let events = [];
                    data.forEach(function (event){
						events.push(
						{
							resourceId: event.resourceId,
							id: event.id,
							start: event.start,
							end: event.end,
							url: event.url,
							title: event.title,
							color: colorMap[event.type]
						});
					});
					successCallback(events);
				})
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
    calendar.gotoDate(props.startDate)

</aui:script>
