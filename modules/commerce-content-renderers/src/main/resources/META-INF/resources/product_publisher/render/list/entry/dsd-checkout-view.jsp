<%@ include file="../init.jsp" %>

<%
	CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);
	CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

	DeltaresProduct deltaresProduct = (DeltaresProduct) request.getAttribute("DeltaresProduct");
	if (deltaresProduct == null){
		deltaresProduct = commerceUtils.toDeltaresProduct(cpCatalogEntry, commerceContext, themeDisplay.getLocale());
	}

%>

<div class="row no-gutters">

	<%
		String productDetailURL = cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay);
	%>
	<div class="col-2">
	<a href="<%= productDetailURL %>">

		<liferay-adaptive-media:img
				alt="thumbnail"
				class="img-fluid product-card-picture"
				fileVersion="<%= cpContentHelper.getCPDefinitionImageFileVersion(cpCatalogEntry.getCPDefinitionId(), request) %>"
		/>
	</a>
	</div>

	<div class="col-10 px-3">

		<h4>
			<a href="<%= productDetailURL %>">
				<span class="text-truncate-inline">
					<span class="text-truncate"><%= cpCatalogEntry.getName() %></span>
				</span>
			</a>
		</h4>
		<div>
			<span class="c-sessions__item__time-date-place__time">
                <%= deltaresProduct.getTimeString(deltaresProduct.getStartTime()) %> -
				<%= deltaresProduct.getTimeString(deltaresProduct.getEndTime()) %>
				( <%= deltaresProduct.getTimeZone().getID() %> )
            </span>
		</div>

	</div>

</div>
