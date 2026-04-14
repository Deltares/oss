<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
    String selection = (String) renderRequest.getAttribute("selection");
    Map<String, String> selectionMap = (Map) renderRequest.getAttribute("selectionMap");
%>

<liferay-portlet:actionURL
        var="submitURL"
        name="submitForm"
/>
<aui:form method="post" name="programFacetForm" action="<%=submitURL%>" cssClass="program-facet-form">

    <aui:select
            name='<%="user-program-facet-select"%>'
            type="select"
            cssClass='select'
            label="facet.program.select-company.label"  value="<%= selection  %>">
        <aui:option value="undefined" label ="facet.selection-facet.label.select" />
        <% for (String selectionValue : selectionMap.keySet()) { %>
        <aui:option value="<%=selectionValue%>" label ="<%=selectionMap.get(selectionValue)%>" />
        <%}%>
    </aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

    let selectionFacet = document.getElementById("<portlet:namespace />user-program-facet-selection");
    selectionFacet.addEventListener('change', function() {

    var form = document.querySelector('form[name="<portlet:namespace />programFacetForm"]')
    form.submit();
    });
</aui:script>