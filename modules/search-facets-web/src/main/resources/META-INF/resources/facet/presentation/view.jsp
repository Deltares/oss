<%@ page import="nl.deltares.search.facet.presentation.PresentationFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

	PresentationFacetConfiguration configuration =
			(PresentationFacetConfiguration)
					renderRequest.getAttribute(PresentationFacetConfiguration.class.getName());
	String visibleConfig = null;
	if (Validator.isNotNull(configuration)){
		visibleConfig = portletPreferences.getValue("visible", configuration.visible());
	}

	String hasPresentation = (String)renderRequest.getAttribute("hasPresentations");
	if (hasPresentation == null){
		hasPresentation = "false";
	}
	String type = "checkbox";
	if (visibleConfig != null && !visibleConfig.isEmpty()){
		if (!Boolean.parseBoolean(visibleConfig)) type = "hidden";
	}

%>

<aui:form method="post" name="presentationFacetForm">

	<aui:input
			name="hasPresentations"
			label="facet.presentation.label"
			cssClass="check-box"
			type="<%=type%>"
			value="<%= hasPresentation %>"
	       	>
	</aui:input>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$(document).ready(function () {
		$(".portlet-presentation-facet .check-box").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});
</aui:script>