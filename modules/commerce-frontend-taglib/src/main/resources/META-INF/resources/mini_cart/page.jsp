<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>
<%@ include file="init.jsp" %>

<c:choose>
	<c:when test="<%= commerceChannelId == 0 %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="cart-root" id="<%= miniCartId %>">
			<div class="mini-cart">
				<div class="mini-cart-overlay">
					<%
						if (itemsQuantity > 0){
					%>
					<a href="<%=checkoutURL%>" data-badge-count="<%=itemsQuantity%>" type="button" class="has-badge mini-cart-opener">
						<clay:icon symbol="shopping-cart" />
					</a>
					<%
						} else {
					%>
					<a href="<%=checkoutURL%>" data-badge-count="0" type="button" class="mini-cart-opener">
						<clay:icon symbol="shopping-cart" />
					</a>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>