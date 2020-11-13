<%@ page import="nl.deltares.search.util.DateFacetUtil" %>
<%@ page import="java.time.LocalDate" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	LocalDate startDate = (LocalDate) renderRequest.getAttribute("startDate");
	LocalDate endDate = (LocalDate) renderRequest.getAttribute("endDate");
	Boolean showPast = (Boolean) renderRequest.getAttribute("showPast");

	if (startDate == null){
		if (startDateConfig != null && !startDateConfig.isEmpty()){
			startDate = DateFacetUtil.getStartDate(startDateConfig);
		}
	}
	String formattedStartDate = "";
	if (startDate != null){
		formattedStartDate = startDate.format(DateFacetUtil.DATE_TIME_FORMATTER);
	}
	if (endDate == null){
		if (endDateConfig != null && !endDateConfig.isEmpty()){
			endDate = DateFacetUtil.getEndDate(endDateConfig);
		}
	}
	String formattedEndDate = "";
	if (endDate != null){
		formattedEndDate = endDate.format(DateFacetUtil.DATE_TIME_FORMATTER);
	}

	if (showPast == null){
		showPast = Boolean.parseBoolean(showPastConfig);
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
	<div class="row">
		<div class="col pr-2">
			<aui:input
					name="showPast"
					type="checkbox"
					cssClass="check-box"
					label="facet.show-past.label"
					value="<%= showPast  %>" >
			</aui:input>
		</div>
	</div>
</aui:form>


<aui:script use="deltares-search-facet-util">
	$('.form-group .date-picker').datepicker({
		language: 'nl',
		format: 'dd-mm-yyyy'
	});

	$(document).ready(function () {
		$(".portlet-date-range-facet .date-picker").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
		$(".portlet-date-range-facet .check-box").change(function () {
		Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});
</aui:script>