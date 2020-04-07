<%@ include file="/init.jsp" %>
<div id="<portlet:namespace />-root"></div>

<aui:script require="<%= mainRequire %>">
	main.default('<portlet:namespace />-root', "<%= canEdit %>", "<%= eventId %>", "<%= baseUrl %>");
</aui:script>
