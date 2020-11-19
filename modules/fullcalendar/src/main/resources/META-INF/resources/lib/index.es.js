import React from 'react'
import ReactDOM from 'react-dom'
import FullCalendar from '@fullcalendar/react'
import interaction from '@fullcalendar/interaction'
import resourceTimeline from '@fullcalendar/resource-timeline'
import resourceDayGrid from '@fullcalendar/resource-daygrid'
import resourceTimeGrid from '@fullcalendar/resource-timegrid'

class Calendar extends React.Component {


	constructor(props, context) {
		super(props, context);
		this.state = {
			type: 'month',
			defaultDate: this.props.startDate,
			eventLimit: false,
			businessHours: true,
			resources: {
				url: this.props.baseUrl + '/resources/' + this.props.siteId + '/' + this.props.eventId+ '?locale=' + this.props.locale + '&p_auth=' + this.props.p_auth,
				method: 'GET',
				// data: {
				// 	username: this.props.authUser,
				// 	password:  this.props.authPassword
				// }
			},
			events: {
				url: this.props.baseUrl + '/events/' + this.props.siteId + '/' + this.props.eventId
					+ '?portletId=' + this.props.portletId + '&layoutUuid=' + this.props.layoutUuid + '&locale=' + this.props.locale+ '&p_auth=' + this.props.p_auth,
				method: 'GET',
				// data: {
				// 	username: this.props.authUser,
				// 	password:  this.props.authPassword
				// }
			}
		}
	}

	render() {
		return (
					<div className='calendar' id='calendar'>
						<FullCalendar
							schedulerLicenseKey={'GPL-My-Project-Is-Open-Source'}
							plugins={[interaction, resourceTimeline, resourceDayGrid, resourceTimeGrid]}
							defaultView={'horizontalWeek'}
							timeZone={'UTC'}
							eventLimit={this.state.eventLimit}
							defaultDate={this.state.defaultDate}
							weekends={false}
							minTime={'08:00'}
							maxTime={'22:00'}
							header={ {
								left: 'prev,next',
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
							contentHeight={'auto'}
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
								hour12: false

							}}
						/>
					</div>);
	}
}

export default function (elementId, baseUrl, siteId, eventId, startDate, portletId, layoutUuid, locale, p_auth) {

	ReactDOM.render( <Calendar class="fc" baseUrl={baseUrl}
							   siteId={siteId} eventId={eventId} startDate={startDate}
							   portletId={portletId} layoutUuid={layoutUuid} locale={locale} p_auth={p_auth}/>, document.getElementById(elementId))

}
