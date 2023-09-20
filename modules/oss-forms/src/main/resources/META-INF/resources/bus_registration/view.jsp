<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="nl.deltares.forms.internal.RegistrationDisplayUtils" %>
<%@ page import="nl.deltares.portal.model.impl.BusTransfer" %>
<%@ page import="com.liferay.portal.kernel.util.DateUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.portal.utils.DsdTransferUtils" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:actionURL name="/submit/transfer/form" var="submitTransferForm"/>

<%
    DsdTransferUtils dsdTransferUtils = (DsdTransferUtils) request.getAttribute("dsdTransferUtils");
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
%>

<liferay-ui:error key="registration-failed">
    <liferay-ui:message key="registration-failed" arguments='<%= SessionErrors.get(liferayPortletRequest, "registration-failed") %>' />
</liferay-ui:error>

<aui:form action="<%= submitTransferForm %>" name="fm">

    <c:forEach var="busTransfer" items="${transfers}">
        <%
            BusTransfer busTransfer = (BusTransfer) pageContext.getAttribute("busTransfer");
            JournalArticleDisplay articleDisplay = RegistrationDisplayUtils.getArticleDisplay(
                    liferayPortletRequest, liferayPortletResponse,
                    ddmTemplateKey, busTransfer.getArticleId(), themeDisplay
            );
            int totalRegistrations = dsdTransferUtils.getRegistrationCount(busTransfer);
            int remainingPlaces = busTransfer.getCapacity() - totalRegistrations;
        %>
        <aui:row>
            <aui:col width="20">
                <div class="float-left p-3">
                    <aui:input
                            name="registration_${busTransfer.resourceId}"
                            label=""
                            checked="<%= dsdTransferUtils.isUserRegisteredFor(user, busTransfer) %>"
                            type="checkbox"/>
                </div>
                <div class="float-left w-200">
                    <%= DateUtil.getDate(busTransfer.getStartTime(), "EE, dd MMM", locale) %>
                </div>
            </aui:col>
            <aui:col width="50">
                <liferay-journal:journal-article-display
                        articleDisplay="<%= articleDisplay %>"
                />
            </aui:col>
            <aui:col width="30">
                <div class="float-right">
                    ( <liferay-ui:message key="dsd.transfer.remaining.places"/> : <%= remainingPlaces %> )
                </div>

            </aui:col>
        </aui:row>
    </c:forEach>

    <c:if test="<%=themeDisplay.isSignedIn()%>">
        <aui:button-row>
            <aui:button type="submit" value="dsd.transfer.save" />
        </aui:button-row>
    </c:if>

</aui:form>