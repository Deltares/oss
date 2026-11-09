<%@ taglib prefix="aui" uri="http://alloy.liferay.com/tld/alloy_util" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="nl.deltares.portal.model.subscriptions.SubscriptionSelection" %>
<%
    ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
    SubscriptionSelection subscriptionSelection = (SubscriptionSelection) row.getObject();
%>
<div>
    <h2><%=subscriptionSelection.getName()%></h2>
    <%
        if(subscriptionSelection.isConsentGroup()){
    %>
    <p><%=subscriptionSelection.getDescription()%></p>
    <%
        }
    %>
</div>