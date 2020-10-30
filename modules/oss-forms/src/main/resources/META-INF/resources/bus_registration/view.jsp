<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="nl.deltares.forms.internal.RegistrationDisplayUtils" %>
<%@ page import="nl.deltares.portal.model.impl.BusTransfer" %>
<%@ page import="com.liferay.portal.kernel.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.portal.utils.DsdTransferUtils" %>
<%@ include file="init.jsp" %>

<portlet:actionURL name="/submit/transfer/form" var="submitTransferForm"/>

<%
    Event event = (Event) request.getAttribute("event");
    DsdTransferUtils dsdTransferUtils = (DsdTransferUtils) request.getAttribute("dsdTransferUtils");
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
%>

<liferay-ui:error key="registration-failed">
    <liferay-ui:message key="registration-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "registration-failed") %>' />
</liferay-ui:error>

<aui:form action="<%= submitTransferForm %>" name="fm">
    <aui:button-row>
        <aui:button type="submit" value="dsd.transfer.save"></aui:button>
    </aui:button-row>

    <c:forEach var="busTransfer" items="${transfers}">
        <div class="row">
            <div class="col">

                <div class="py-3">

                    <%
                        BusTransfer busTransfer = (BusTransfer) pageContext.getAttribute("busTransfer");
                        JournalArticleDisplay articleDisplay = RegistrationDisplayUtils.getArticleDisplay(
                                liferayPortletRequest, liferayPortletResponse,
                                ddmTemplateKey, busTransfer.getArticleId(), themeDisplay
                        );
                    %>
                    <liferay-journal:journal-article-display
                            articleDisplay="<%= articleDisplay %>"
                    />

                </div>

                <c:forEach var="transferDate" items="${busTransfer.transferDates}">
                    <%
                        Date transferDate = (Date) pageContext.getAttribute("transferDate");
                        Registration registration = event.getRegistration(busTransfer.getResourceId(), themeDisplay.getLocale());
                        int totalRegistrations = dsdTransferUtils.getRegistrationCount(registration,transferDate);
                        int remainingPlaces = registration.getCapacity() - totalRegistrations;
                    %>
                    <div class="row">
                        <c:set var="formattedDate" value='<%= format.format(transferDate) %>'/>
                        <div class="col">
                            <div class="float-left p-3">
                                <aui:input
                                        name="registration_${busTransfer.resourceId}_${formattedDate}"
                                        label=""
                                        checked="<%= dsdTransferUtils.isUserRegisteredFor(user, registration, transferDate) %>"
                                        type="checkbox"/>
                            </div>
                            <div class="float-left w-200">
                                <%= DateUtil.getDate(transferDate, "EE, dd MMMM yyyy", locale) %>
                            </div>
                        </div>
                        <div class="col">
                            <div class="float-right">
                                ( <liferay-ui:message key="dsd.transfer.remaining.places"/> : <%= remainingPlaces %> )
                            </div>

                        </div>
                    </div>

                </c:forEach>

            </div>
        </div>
    </c:forEach>

    <aui:button-row>
        <aui:button type="submit" value="dsd.transfer.save"></aui:button>
    </aui:button-row>

</aui:form>