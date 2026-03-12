<%@ page import="nl.deltares.model.RegistrationsInfo" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ include file="init.jsp" %>
<%
    SubmitOrderDisplayContext displayContext = (SubmitOrderDisplayContext) request.getAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT);

    RegistrationsInfo registrationsInfo = displayContext.getRegistrationsInfo();

    NumberFormat currencyFormat = new DecimalFormat("#0.00");
%>

<liferay-portlet:renderURL varImpl="iteratorURL">
</liferay-portlet:renderURL>

<clay:row>

    <div class="prose prose--app">
        <h3><liferay-ui:message key="registrationform.order.summary"/></h3>
    </div>
</clay:row>
<clay:row>

    <div class="w-100">
        <aui:form >
             <liferay-ui:search-container id="registrationItems" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-records")%>'
                                         iteratorURL="<%= iteratorURL %>" total="<%=registrationsInfo.getRegistrationArticleIds().size()%>"
            >
                <liferay-ui:search-container-results results="<%= registrationsInfo.getRegistrations() %>" />
                <liferay-ui:search-container-row
                        className="nl.deltares.portal.model.impl.Registration"
                        modelVar="currentRegistration"
                        keyProperty="articleId"
                >
                    <%
                        String title = currentRegistration.getTitle();
                        double price = currentRegistration.getPrice();
                        String priceText = currentRegistration.getCurrency() + " " + currencyFormat.format(price);

                        float vat = displayContext.defineTaxPercentage(currentRegistration);
                        int quantity = registrationsInfo.getUserRegistrations(currentRegistration.getArticleId()).size();
                        double totalAmount = price * quantity * (1 + vat * 0.01);
                        String totalAmountText = currentRegistration.getCurrency() + " " + currencyFormat.format(totalAmount);

                    %>

                    <liferay-ui:search-container-column-text value="<%=String.valueOf(quantity) %>" name="Quantity" cssClass="col-1"/>
                    <liferay-ui:search-container-column-text value="<%=title%>" name="Article" cssClass="col-3"/>
                    <liferay-ui:search-container-column-text value="<%=priceText%>" name="Price per item" cssClass="col-1"/>
                    <liferay-ui:search-container-column-text value='<%=vat + " %"%>' name="Tax %" cssClass="col-1"/>
                    <liferay-ui:search-container-column-text value="<%=totalAmountText%>" name="Total amount" cssClass="col-1"/>

                </liferay-ui:search-container-row>
                <liferay-ui:search-iterator/>
            </liferay-ui:search-container>
        </aui:form>
    </div>
</clay:row>

