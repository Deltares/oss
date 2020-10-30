<%@ include file="/init.jsp" %>
<div id="<portlet:namespace />-root"></div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />-root', "<%= baseUrl %>", "<%= siteId %>",
	"<%= eventId %>", "<%= startDate %>" , "<%= portletId %>", "<%= layoutUuid %>", "<%=themeDisplay.getLocale()%>");
</aui:script>
