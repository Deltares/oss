import React from 'react'
import ReactDOM from 'react-dom'
import FullCalendar from '@fullcalendar/react'
import interaction from '@fullcalendar/interaction'
import resourceDayGrid from '@fullcalendar/resource-daygrid'
import resourceTimeGrid from '@fullcalendar/resource-timegrid'

function loadStartDate(props) {
	$.ajax({
		url: props.baseUrl + "/startTime/" + props.siteId,
		type: "GET",
		success: function(json) {
			return  json;
		}
	});
}
function saveEvent(event) {
	$.ajax({
		url: 'add_events.php',
		data: 'title='+ title+'&start='+ start2 +'&end='+ end2,
		type: "POST",
		success: function(json) {
			$( "#getReason" ).modal('hide');
			$('#mydiv').hide();
			$('body').removeClass('blockMask');//calendar.fullCalendar( 'refetchEvents');
			$('#calendar').fullCalendar('refetchEvents');
		}
	});
}

class Calendar extends React.Component {

	constructor(props, context) {
		super(props, context);

		let start = loadStartDate(this.props);
		this.state = {
			type : 'month',
			defaultDate: Date.now(),
			editable : this.props.editable,
			selectable : this.props.selectable,
			eventLimit : false,
			startDate : start,
			resources : {
                url:  this.props.baseUrl + '/resources/' + this.props.siteId ,
                method: 'GET',
				// data: {
				// 	username: this.props.authUser,
				// 	password:  this.props.authPassword
				// }
            },
			events : {
                url: this.props.baseUrl + '/events/' + this.props.siteId,
                method: 'GET',
				// data: {
				// 	username: this.props.authUser,
				// 	password:  this.props.authPassword
				// }

			}
		}

	}

	onSelect(args) {
		let abc = prompt('Enter Title');
		let newEvent = {};
		newEvent.title = abc;
		newEvent.allDay = args.allDay;
		newEvent.start = args.start;
		newEvent.end = args.end;
		newEvent.resourceId = args.resource.id;

		//todo
		saveEvent(newEvent);
		args.resource._calendar.addEvent(newEvent);

	}

	onEventChanged(arg) {
		console.log(
			'eventChanged',
			arg.event.title + " end is now " + arg.event.end.toISOString()
		);
	}

	render() {
		return (
			<div className='calendar'>
			<FullCalendar
		schedulerLicenseKey={'GPL-My-Project-Is-Open-Source'}
		plugins={[interaction, resourceDayGrid, resourceTimeGrid]}
		defaultView={'resourceTimeGridDay'}
		timeZone={'UTC'}
		editable={this.state.editable === 'true'}
		selectable={this.state.selectable === 'true'}
		eventLimit={this.state.eventLimit}
		defaultDate={this.state.defaultDate}

		header={{
			left:'prev,next,today',
				center:'title',
				right: 'resourceTimeGridDay,resourceTimeGridTwoDay,timeGridWeek,dayGridMonth'
		}}
		views={{
			resourceTimeGridTwoDay : {
				type : 'resourceTimeGrid',
					duration: {days: 2},
				buttonText : '2 days',
			}
		}}

		resources={this.state.resources}
		events={this.state.events}
		select={this.onSelect}
		eventResize={this.onEventChanged}
		/>
		</div>
	)
	}
}

export default function (elementId, canEdit, baseUrl, siteId) {
	ReactDOM.render( <Calendar class="fc" editable={canEdit} selectable={canEdit} baseUrl={baseUrl} siteId={siteId} />, document.getElementById(elementId))
}
