
<%@ include file="init.jsp" %>

<liferay-ui:error key="update-cart-failed">
	<liferay-ui:message key="update-cart-failed"
						arguments='<%= SessionErrors.get(liferayPortletRequest.getHttpServletRequest(), "update-cart-failed") %>'/>
</liferay-ui:error>

<liferay-ui:success key="update-cart-failed">
	<liferay-ui:message key="update-cart-failed"
						arguments='<%= SessionMessages.get(liferayPortletRequest.getHttpServletRequest(), "update-cart-failed") %>'/>
</liferay-ui:success>
<%
	CPDataSourceResult cpDataSourceResult = (CPDataSourceResult)request.getAttribute(CPWebKeys.CP_DATA_SOURCE_RESULT);

	for (CPCatalogEntry cpCatalogEntry : cpDataSourceResult.getCPCatalogEntries()) {
%>
<div class="list-group-item list-group-item-flex">

	<div class="autofit-col autofit-col-expand" >

		<div class="date-title upcoming-event">
			<span>09 October 2024</span>
		</div>
	<liferay-commerce-product:product-list-entry-renderer
			CPCatalogEntry="<%= cpCatalogEntry %>"
	/>
	</div>

</div>

	<%
		}
	%>
