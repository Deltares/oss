<%@ include file="/META-INF/resources/dsd_init.jsp" %>

<portlet:actionURL name="/submit/transfer/form" var="submitTransferForm"/>

<aui:form action="<%= submitTransferForm %>" name="fm">

    <c:forEach var="busTransfer" items="${transfers}">
        <c:forEach var="transferDate" items="${busTransfer.transferDates}">
            ${transferDate}
        </c:forEach>
    </c:forEach>

    <aui:button-row>
        <aui:button type="submit" value="send.message"></aui:button>
    </aui:button-row>

</aui:form>