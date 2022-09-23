import React from 'react';
import ReactDOM from 'react-dom';
import FullCalendar from '@fullcalendar/react';
import interaction from '@fullcalendar/interaction';
import resourceTimeline from '@fullcalendar/resource-timeline';
import resourceDayGrid from '@fullcalendar/resource-daygrid';
import resourceTimeGrid from '@fullcalendar/resource-timegrid';
import momentTimezonePlugin from '@fullcalendar/moment-timezone';

class Calendar extends React.Component {

	constructor(props, context) {
		super(props, context);

		let eventUrl = this.props.baseUrl + '/events/' + this.props.siteId + '/' + this.props.eventId + '?p_auth=' + this.props.p_auth;
		let colorMap = JSON.parse(this.props.colorMap);

		this.state = {
			type: 'month',
			defaultDate: this.props.startDate,
			defaultTimeZoneId: Intl.DateTimeFormat().resolvedOptions().timeZone,
			eventLimit: false,
			businessHours: true,
			defaultView: this.props.defaultView,
			resources: {
				url: this.props.baseUrl + '/resources/' + this.props.siteId + '/' + this.props.eventId + '?p_auth=' + this.props.p_auth,
				method: 'GET',
				// data: {
				// 	username: this.props.authUser,
				// 	password:  this.props.authPassword
				// }
			},
			events: function (fetchInfo, successCallback, failureCallback){

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
								}
							);
						});
						successCallback(events);
					},
					error: function (xhr, ajaxOptions, thrownError) {
						failureCallback(thrownError)
					}
				});
			}
		}
	}

	render() {
		return (
					<div className='calendar' id='calendar'>
						<FullCalendar
							schedulerLicenseKey={'GPL-My-Project-Is-Open-Source'}
							plugins={[interaction, resourceTimeline, resourceDayGrid, resourceTimeGrid,momentTimezonePlugin ]}
							defaultView={this.state.defaultView}
							timeZone={this.state.defaultTimeZoneId}
							eventLimit={this.state.eventLimit}
							defaultDate={this.state.defaultDate}
							weekends={false}
							scrollTime={'08:00:00'}
							header={ {
								left: 'today,prev,next',
								center: 'title',
								right: 'horizontalDay,horizontalWeek,verticalDay,verticalWeek'
							}}
							views={{
								horizontalDay : {
									type : 'resourceTimelineDay',
									buttonText : 'Horizontal: day'
								},
								horizontalWeek : {
									type:"resourceTimeline",
									duration : {weeks : 1},
									buttonText : 'week'
								},
								verticalDay : {
									type: 'resourceTimeGridDay' ,
									buttonText : 'Vertical: day'
								},
								verticalWeek : {
									type: 'timeGridWeek',
									buttonText : 'week'
								},
							}}
							aspectRatio={1.4}
							resourceGroupField={'building'}
							resources={this.state.resources}
							events={this.state.events}
							slotLabelFormat={
								[{ day: '2-digit', month: 'short' },
								{
									hour: 'numeric',
									minute: '2-digit',
									omitZeroMinute: true,
									meridiem: false,
									hour12: false
								}]
							}
							eventTimeFormat={{
								hour: 'numeric',
								minute: '2-digit',
								omitZeroMinute: true,
								meridiem: false,
								hour12: false,
								timeZoneName: 'short'

							}}
						/>
					</div>);
	}
}

export default function (elementId, baseUrl, siteId, eventId, startDate, defaultView, colorMap, p_auth) {

	ReactDOM.render( <Calendar class="fc" baseUrl={baseUrl}
							   siteId={siteId} eventId={eventId} startDate={startDate} defaultView={defaultView}
							   colorMap={colorMap}  p_auth={p_auth}/>, document.getElementById(elementId));
}
