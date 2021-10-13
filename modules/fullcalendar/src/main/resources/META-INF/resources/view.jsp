<%@ include file="/init.jsp" %>
<div id="<portlet:namespace />-root"></div>

<aui:script require="<%= bootstrapRequire %>">
	let p_auth = Liferay.authToken;
	bootstrapRequire.default('<portlet:namespace />-root', "<%= baseUrl %>", "<%= siteId %>",
	"<%= eventId %>", "<%= startDate %>" , "<%= defaultView %>", "<%= portletId %>", "<%= layoutUuid %>",  p_auth);
</aui:script>
