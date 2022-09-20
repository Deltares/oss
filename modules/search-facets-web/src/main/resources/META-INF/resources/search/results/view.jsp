<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

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
</c:choose>