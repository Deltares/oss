<%@ include file="/META-INF/resources/init.jsp" %>

<%
	String type = (String)renderRequest.getAttribute("topic");
	DsdArticle.DSD_TOPIC_KEYS[] topicValues = DsdArticle.DSD_TOPIC_KEYS.values();
%>

<aui:form method="post" name="sessionTopicFacetForm">

	<aui:select
			name="session-topic"
			type="select"
			cssClass="select"
			label="facet.session-topic.label"  value="<%= type  %>" >
		<aui:option value="undefined" label ="facet.session-topic.label.select" />
		<% for (DsdArticle.DSD_TOPIC_KEYS topicValue : topicValues) { %>
			<% String topicLabel = "facet.session-topic.label." + topicValue.name(); %>
			<aui:option value="<%=topicValue.name()%>" label ="<%=topicLabel%>" />
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