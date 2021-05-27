<%@ page import="java.util.Map" %>
<%@ include file="init.jsp" %>

<%
	String name = (String) renderRequest.getAttribute("name");
	String title = (String) renderRequest.getAttribute("title");
	String selection = (String) renderRequest.getAttribute("selection");
	Map<String, String> selectionMap = (Map) renderRequest.getAttribute("selectionMap");
%>
<aui:form method="post" name="selectionFacetForm">

	<aui:select
			name='<%="selection-facet-" + (name)%>'
			type="select"
			cssClass='select'
			label="<%=title%>"  value="<%= selection  %>">
		<aui:option value="undefined" label ="facet.selection-facet.label.select" />
		<% for (String selectionValue : selectionMap.keySet()) { %>
			<aui:option value="<%=selectionValue%>" label ="<%=selectionMap.get(selectionValue)%>" />
		<%}%>
	</aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$(document).ready(function () {
		$("#<portlet:namespace />selection-facet-<%=name%>").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />", "<%=name%>");
		});
	});
</aui:script>