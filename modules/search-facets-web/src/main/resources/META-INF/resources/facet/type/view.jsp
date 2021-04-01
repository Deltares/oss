<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String type = (String)renderRequest.getAttribute("type");
	Map<String, String> typeMap = (Map<String,String>) renderRequest.getAttribute("typeMap");
%>

<aui:form method="post" name="sessionTypeFacetForm">

	<aui:select
			name="session-type"
			type="select"
			cssClass="select"
			label="facet.session-type.label"  value="<%= type  %>" >
		<aui:option value="undefined" label ="facet.session-type.label.select" />
		<% for (String topicValue : typeMap.keySet()) { %>
			<aui:option value="<%=topicValue%>" label ="<%=typeMap.get(topicValue)%>" />
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