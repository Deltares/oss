<%@ taglib prefix="aui" uri="http://alloy.liferay.com/tld/alloy_util" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%
    ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

    String columnValue = (String)row.getParameter("columnValue");
    String columnName = (String)row.getParameter("columnName");
    String columnColor = (String)row.getParameter("columnColor");
%>
<div id="<%=columnName + row.getRowId()%>" class="color-column"><%=columnValue%></div>
<script >
    var backgroundColor = "<%=columnColor%>";
    var borderColor = darkenHex(backgroundColor, 12);
    var color = darkenHex(borderColor, 12);

    var cell = document.getElementById('<%=columnName + row.getRowId()%>');
    cell.style.color = color;
    cell.style.background = backgroundColor;
    cell.style.border = "1px solid " + borderColor;
</script>
