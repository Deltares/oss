<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collections" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
    String selection = (String) renderRequest.getAttribute("site-selection");
    Map<String, String> selectionMap = (Map) renderRequest.getAttribute("site-selectionMap");
    if (selectionMap == null){
        selectionMap = Collections.emptyMap();
    }
%>

<liferay-portlet:actionURL
        var="submitURL"
        name="submitForm"
/>
<aui:form method="post" name="programFacetForm" action="<%=submitURL%>" cssClass="program-facet-form">
    <aui:select
            name='<%="user-program-site-selection"%>'
            type="select"
            label="facet.program.select-site.label"
            value="<%= selection  %>">
        <% for (String selectionValue : selectionMap.keySet()) { %>
        <aui:option value="<%=selectionValue%>" label ="<%=selectionMap.get(selectionValue)%>" />
        <%}%>
    </aui:select>

</aui:form>


<aui:script use="deltares-search-facet-util">

    function submitProgramFacetForm() {
        var form = document.querySelector('form[name="<portlet:namespace />programFacetForm"]');
        form.submit();
    }

    let selection = document.getElementById("<portlet:namespace />user-program-site-selection");
    selection.addEventListener('change', submitProgramFacetForm);

</aui:script>