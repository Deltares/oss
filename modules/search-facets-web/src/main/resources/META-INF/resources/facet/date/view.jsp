<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="nl.deltares.search.facet.date.DateRangeFacetConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
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

	LocalDate startDate = (LocalDate) renderRequest.getAttribute("startDate");
	LocalDate endDate = (LocalDate) renderRequest.getAttribute("endDate");

	if (startDate == null){
		if (startDateConfig != null && !startDateConfig.isEmpty()){
			startDate = FacetUtils.getStartDate(startDateConfig);
		} else if (Boolean.parseBoolean(setStartNowConfig)){
			startDate = LocalDate.now();
		}
	}
	String formattedStartDate = "";
	Calendar startCalendar = null;
	if (startDate != null){
		formattedStartDate = startDate.format(FacetUtils.DATE_TIME_FORMATTER);
		startCalendar = CalendarFactoryUtil.getCalendar();
		startCalendar.set(startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
	}
	Calendar endCalendar = null;
	if (endDate == null){
		if (endDateConfig != null && !endDateConfig.isEmpty()){
			endDate = FacetUtils.getEndDate(endDateConfig);
		}
	}
	String formattedEndDate = "";

	if (endDate != null){
		formattedEndDate = endDate.format(FacetUtils.DATE_TIME_FORMATTER);
		endCalendar = CalendarFactoryUtil.getCalendar();
		endCalendar.set(endDate.getYear(), endDate.getMonth().getValue() - 1, endDate.getDayOfMonth());
	}

%>

<aui:form method="post" name="dateRangeFacetForm">
	<label for="dates"><liferay-ui:message key="facet.date-range.label"/></label>
	<div class="row" id="dates">
		<div class="col pr-2">
			<% if(startCalendar == null) { %>
				<liferay-ui:input-date name="startDate"
									   cssClass="date-picker input-date"
									   nullable="true"
				/>

			<% } else { %>
				<liferay-ui:input-date name="startDate"
						   cssClass="date-picker input-date"
						   nullable="true"
						   yearValue="<%= startCalendar.get(Calendar.YEAR) %>"
						   monthValue="<%= startCalendar.get(Calendar.MONTH) %>"
						   dayValue="<%= startCalendar.get(Calendar.DATE) %>"
				/>

			<% } %>
		</div>
		<div class="col pl-2">
			<% if(endCalendar == null) { %>
			<liferay-ui:input-date name="endDate"
								   cssClass="date-picker input-date"
			/>

			<% } else { %>
			<liferay-ui:input-date name="endDate"
								   cssClass="date-picker input-date"
								   yearValue="<%= endCalendar.get(Calendar.YEAR) %>"
								   monthValue="<%= endCalendar.get(Calendar.MONTH) %>"
								   dayValue="<%= endCalendar.get(Calendar.DATE) %>"
			/>

			<% } %>
		</div>
	</div>
</aui:form>


<aui:script use="deltares-search-facet-util">

	Liferay.Deltares.FacetUtil.initializeDates("<portlet:namespace />", "<%=formattedStartDate%>", "<%=formattedEndDate%>");

	document.addEventListener('DOMContentLoaded', function () {
		document.querySelectorAll('.portlet-date-range-facet .date-picker').forEach(function(element) {
			element.addEventListener('change', function () {
				Liferay.Deltares.FacetUtil.updateQueryString('<portlet:namespace />');
			});
		});
	});
</aui:script>