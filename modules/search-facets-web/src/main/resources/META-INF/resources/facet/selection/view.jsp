<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String name = (String) renderRequest.getAttribute("name");
	String title = (String) renderRequest.getAttribute("title");
	String selection = (String) renderRequest.getAttribute("selection");
	Map<String, String> selectionMap = (Map) renderRequest.getAttribute("selectionMap");
%>

<liferay-portlet:actionURL
		var="submitURL"
		name="submitForm"
/>
<aui:form method="post" name="selectionFacetForm" action="<%=submitURL%>" cssClass="selection-facet-form">

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

	let selectionFacet = document.getElementById("<portlet:namespace />selection-facet-<%=name%>");
   	selectionFacet.addEventListener('change', function() {

           var form = document.querySelector('form[name="<portlet:namespace />selectionFacetForm"]')
           form.submit();
		// Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />", "<%=name%>");
	});
</aui:script>