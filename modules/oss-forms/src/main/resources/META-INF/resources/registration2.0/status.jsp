<%@ include file="init.jsp" %>
<%
	CheckoutStatusDisplayContext displayContext = (CheckoutStatusDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);
%>
<div class="row">
	<div class="col-2">
		<img src="<%=displayContext.getImageURL()%>" alt="">
	</div>
	<div class="col">
		<h3><strong><liferay-ui:message key="<%=displayContext.getHeaderKey()%>" /></strong></h3>

		<div class="form-group-autofit">
			<liferay-ui:message key="<%=displayContext.getEmailMessageKey()%>" />
		</div>

		<div class="">
			<liferay-ui:message key="<%=displayContext.getPaymentMessageKey()%>" arguments="<%=displayContext.getPaymentMessageArguments()%>" />
		</div>

		<div class="form-group-autofit">
			<aui:button-row>
				<aui:button href="<%= (String)request.getAttribute(OssConstants.MY_REGISTRATIONS_URL) %>"
							primary="<%= true %>" type="submit" value="registrationform.register.view" />

				<aui:button href='<%= (String)request.getAttribute("redirect") %>'
							primary="<%= true %>" type="submit" value="registrationform.register.back" />

			</aui:button-row>
		</div>

	</div>
</div>
<aui:script>

	document.onreadystatechange = function (s) {
		if (s.target.readyState  === 'complete'){
			window.shoppingCart.clearCart();
		}
	}

</aui:script>