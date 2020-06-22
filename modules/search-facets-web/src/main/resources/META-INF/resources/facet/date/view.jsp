<%@ include file="/META-INF/resources/init.jsp" %>

<%
	LocalDate startDate = (LocalDate) renderRequest.getAttribute("startDate");
	LocalDate endDate = (LocalDate) renderRequest.getAttribute("endDate");
%>

<aui:form method="post" name="dateRangeFacetForm">
	<div class="row">
		<div class="col pr-2">
			<aui:input
					name="startDate"
					label=""
					cssClass="date-picker input-date"
					placeholder="dd-mm-yyyy"
					value="<%= startDate.format(DateFacetUtil.DATE_TIME_FORMATTER) %>">
				<aui:validator name="required"/>
			</aui:input>
		</div>
		<div class="col pl-2">
			<aui:input
					name="endDate"
					label=""
					cssClass="date-picker input-date"
					placeholder="dd-mm-yyyy"
					value="<%= endDate.format(DateFacetUtil.DATE_TIME_FORMATTER) %>">
				<aui:validator name="required"/>
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
	});
</aui:script>