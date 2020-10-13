<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String type = (String)renderRequest.getAttribute("type");
	DsdArticle.DSD_SESSION_KEYS[] typeValues = DsdArticle.DSD_SESSION_KEYS.values();
%>

<aui:form method="post" name="sessionTypeFacetForm">

	<aui:select
			name="session-type"
			type="select"
			cssClass="select"
			label="facet.session-type.label"  value="<%= type  %>" >
		<aui:option value="undefined" label ="facet.session-type.label.select" />
		<% for (DsdArticle.DSD_SESSION_KEYS typeValue : typeValues) { %>
			<% String typeLabel = "facet.session-type.label." + typeValue.name(); %>
			<aui:option value="<%=typeValue.name()%>" label ="<%=typeLabel%>" />
		<%}%>
	</aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$(document).ready(function () {
		$(".portlet-session-type-facet .select").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});

</aui:script>