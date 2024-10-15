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

	final SimpleDateFormat dateHeaderFormat = new SimpleDateFormat("dd MMMM yyyy");
	CPDataSourceResult cpDataSourceResult = (CPDataSourceResult)request.getAttribute(CPWebKeys.CP_DATA_SOURCE_RESULT);

	final List<CPCatalogEntry> cpCatalogEntries = cpDataSourceResult.getCPCatalogEntries();

	long lastDate = 0;

	final List<DeltaresProduct> deltaresProducts = commerceUtils.cpCategoryEntriesToDeltaresProducts(cpCatalogEntries, commerceContext, themeDisplay.getLocale());
	for (DeltaresProduct deltaresProduct : deltaresProducts) {

		liferayPortletRequest.setAttribute("DeltaresProduct", deltaresProduct);

		boolean writeDateHeader = !deltaresProduct.isSameDay(lastDate, deltaresProduct.getStartTime());
		String dateHeader = "";
		if (writeDateHeader){
			dateHeaderFormat.setTimeZone(deltaresProduct.getTimeZone());
			dateHeader = dateHeaderFormat.format(new Date(deltaresProduct.getStartTime()));
		}
		String colorClass;
		if (deltaresProduct.isStartDateInThePast()) {
			colorClass = "past-event";
		} else {
			colorClass = "upcoming-event";
		}
%>
<div class="list-group-item list-group-item-flex">

	<div class="autofit-col autofit-col-expand" >

		<c:if test="<%= writeDateHeader %>">
			<div class="date-title <%= colorClass %>">
				<span><%= dateHeader %></span>
			</div>
		</c:if>
		<liferay-commerce-product:product-list-entry-renderer
				CPCatalogEntry="<%= deltaresProduct.getCpCatalogEntry() %>"
		/>
	</div>

</div>

	<%
		}
	%>
