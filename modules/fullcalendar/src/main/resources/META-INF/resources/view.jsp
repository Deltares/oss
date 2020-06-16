<%@ include file="/init.jsp" %>
<div id="<portlet:namespace />-root"></div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />-root', "<%= false %>", "<%= baseUrl %>", "<%= siteId %>",
	"<%= eventId %>", "<%= startDate %>" , "<%= portletId %>", "<%= layoutUuid %>");
</aui:script>
