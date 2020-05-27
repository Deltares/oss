<%@ include file="/init.jsp" %>
<div id="<portlet:namespace />-root"></div>

<aui:script require="<%= bootstrapRequire %>">
	bootstrapRequire.default('<portlet:namespace />-root', "<%= canEdit %>", "<%= baseUrl %>", "<%= siteId %>");
</aui:script>
