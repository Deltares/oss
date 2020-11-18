<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String hasPresentation = (String)renderRequest.getAttribute("hasPresentations");
	if (hasPresentation == null){
		hasPresentation = "false";
	}
%>

<aui:form method="post" name="presentationFacetForm">

	<aui:input
			name="hasPresentations"
			label="facet.presentation.label"
			cssClass="check-box"
			type="checkbox"
			value="<%= hasPresentation %>">
	</aui:input>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$(document).ready(function () {
		$(".portlet-presentation-facet .check-box").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});

</aui:script>