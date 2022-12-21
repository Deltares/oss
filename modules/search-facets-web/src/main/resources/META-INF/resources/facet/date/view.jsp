<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="nl.deltares.search.facet.date.DateRangeFacetConfiguration" %>
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
	if (startDate != null){
		formattedStartDate = startDate.format(FacetUtils.DATE_TIME_FORMATTER);
	}
	if (endDate == null){
		if (endDateConfig != null && !endDateConfig.isEmpty()){
			endDate = FacetUtils.getEndDate(endDateConfig);
		}
	}
	String formattedEndDate = "";
	if (endDate != null){
		formattedEndDate = endDate.format(FacetUtils.DATE_TIME_FORMATTER);
	}

%>

<aui:form method="post" name="dateRangeFacetForm">
	<label for="dates"><liferay-ui:message key="facet.date-range.label"/></label>
	<div class="row" id="dates">
		<div class="col pr-2">
			<aui:input
					name="startDate"
					label=""
					cssClass="date-picker input-date"
					placeholder="dd-mm-yyyy"
					value="<%= formattedStartDate %>">
<%--				<aui:validator name="required"/>--%>
			</aui:input>
		</div>
		<div class="col pl-2">
			<aui:input
					name="endDate"
					label=""
					cssClass="date-picker input-date"
					placeholder="dd-mm-yyyy"
					value="<%= formattedEndDate %>">
<%--				<aui:validator name="required"/>--%>
			</aui:input>
		</div>
	</div>
</aui:form>


<aui:script use="deltares-search-facet-util">
	$('.form-group .date-picker').datepicker({
		language: 'nl',
		format: 'dd-mm-yyyy'
	});
	Liferay.Deltares.FacetUtil.initializeDates("<portlet:namespace />", "<%=formattedStartDate%>", "<%=formattedEndDate%>");

	$(document).ready(function () {
		$(".portlet-date-range-facet .date-picker").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});
</aui:script>