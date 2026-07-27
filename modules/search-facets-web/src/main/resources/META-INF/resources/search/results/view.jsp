<%@ taglib prefix="portlet" uri="http://xmlns.jcp.org/portlet_3_0" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<portlet:defineObjects/>

<%
    String displayType = (String) renderRequest.getAttribute("displayType");
%>

<c:choose>
    <c:when test='<%=( displayType.equals("dsd") ) %>'>
        <jsp:include page="view-dsd.jsp"/>
    </c:when>
    <c:when test='<%=( displayType.equals("download") ) %>'>
        <jsp:include page="view-download.jsp"/>
    </c:when>
    <c:when test='<%=( displayType.equals("article") ) %>'>
        <jsp:include page="view-articles.jsp"/>
    </c:when>
</c:choose>