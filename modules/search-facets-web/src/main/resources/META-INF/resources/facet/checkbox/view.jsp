<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String name = (String) renderRequest.getAttribute("name");
	String title = (String) renderRequest.getAttribute("title");
	String selection = (String)renderRequest.getAttribute("selection");
	final Map<String, String> yesNoFieldOptions = FacetUtils.getYesNoFieldOptions();

%>

<liferay-portlet:actionURL
		var="submitURL"
		name="submitForm"
/>
<aui:form method="post" name="checkboxFacetForm" action="<%=submitURL%>">

	<aui:select
			name='<%="checkbox-facet-" + (name)%>'
			type="select"
			cssClass='select'
			label='<%=(title)%>' >
		<aui:option value="undefined" label ="facet.selection-facet.label.select" />
		<% for (String selectionValue : yesNoFieldOptions.keySet()) { %>
		<aui:option value='<%=selectionValue%>'  label ='<%=(yesNoFieldOptions.get(selectionValue))%>' />
		<%}%>
	</aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

	let facet = document.getElementById("<portlet:namespace />checkbox-facet-<%=name%>");
	if (facet) {
		facet.addEventListener('change', function() {
			var form = document.querySelector('form[name="<portlet:namespace />checkboxFacetForm"]')
			form.submit();
			// Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />", "<%=name%>");
		});
		Array.from(facet.options).forEach(function(item){
			item.selected = item.value === '<%=selection%>';
        });
    }
</aui:script>