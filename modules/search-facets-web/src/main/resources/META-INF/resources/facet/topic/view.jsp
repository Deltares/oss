<%@ page import="java.util.Map" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String type = (String)renderRequest.getAttribute("topic");
	Map<String, String> topicsMap = (Map<String,String>) renderRequest.getAttribute("topicMap");
%>

<aui:form method="post" name="sessionTopicFacetForm">

	<aui:select
			name="session-topic"
			type="select"
			cssClass="select"
			label="facet.session-topic.label"  value="<%= type  %>" >
		<aui:option value="undefined" label ="facet.session-topic.label.select" />
		<% for (String topicValue : topicsMap.keySet()) { %>
			<aui:option value="<%=topicValue%>" label ="<%=topicsMap.get(topicValue)%>" />
		<%}%>
	</aui:select>
</aui:form>


<aui:script use="deltares-search-facet-util">

	$(document).ready(function () {
		$(".portlet-session-topic-facet .select").change(function () {
			Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace />");
		});
	});

</aui:script>