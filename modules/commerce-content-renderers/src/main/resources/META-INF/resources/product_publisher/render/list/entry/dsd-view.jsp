<%@ include file="../init.jsp" %>

<%
	CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

	CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

	CPInstance defaultCPInstance = cpContentHelper.getDefaultCPInstance(cpCatalogEntry);

	boolean hasMultipleCPSkus = cpContentHelper.hasMultipleCPSkus(cpCatalogEntry);

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
		<div class="aspect-ratio-item-bottom-left">
			<c:if test="<%= !hasMultipleCPSkus %>">
				<commerce-ui:availability-label
						CPCatalogEntry="<%= cpCatalogEntry %>"
				/>

				<commerce-ui:discontinued-label
						CPCatalogEntry="<%= cpCatalogEntry %>"
				/>
			</c:if>
		</div>
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
            </span>|
			<% if (deltaresProduct.getPrice() > 0) {%>
				<%=deltaresProduct.getCurrency()%> <%= deltaresProduct.getPrice() %>
			<% } else { %>
				<liferay-ui:message key="dsd.theme.session.free"/>
			<%}%>
			<%
				if(showButtons) {

					long cpInstanceId = 0;
					if (defaultCPInstance != null){
						cpInstanceId = defaultCPInstance.getCPInstanceId();
					}

					final List<CommerceOrderItem> commerceOrderItems = commerceOrder.getCommerceOrderItems(cpInstanceId);
					String languageKey;
					String action;
					if (commerceOrderItems.isEmpty()){
						action = "add-to-cart";
						languageKey = "shopping.cart.add";
					} else {
						action = "remove-from-cart";
						languageKey = "shopping.cart.remove";
					}

			%>
			<span class="d-block" style="float: right">

			<aui:form action="<%= updateCommerceOrderItemURL %>" method="post"  name='<%= randomNamespace + "Fm" %>' portletNamespace="<%= portletNamespace %>">
					<aui:input name="action" type="hidden" value="<%= action %>" />
					<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />
					<aui:button-row>
						<aui:button type="submit" value="<%=languageKey%>" />
					</aui:button-row>
				</aui:form>

			</span>

			<%
				}
			%>

		</div>
	</div>

</div>
