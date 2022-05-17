<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletRequestModel" %>
<%@ page import="nl.deltares.portal.model.impl.Download" %>
<%@ page import="java.util.List" %>

<%
    String ddmTemplateKey = (String) request.getAttribute("ddmTemplateKey");
    List<String> downloadList = (List<String>) request.getAttribute("downloadList");
%>

<c:forEach var="downloadId" items="${downloadList}">

    <c:if test="${not empty downloadId}">

        <%
            String downloadId = (String) pageContext.getAttribute("downloadId");
            Download download;
            try {
                download = (Download) dsdParserUtils.toDsdArticle(themeDisplay.getScopeGroupId(), downloadId);
            } catch (PortalException e) {
                String message = String.format("Error getting download %s", downloadId);
                SessionErrors.add(liferayPortletRequest, "sendlink-failed", message);
                continue;
            }
        %>

        <div class="registration-item">

            <div class="d-flex">
                <div class="float-left p-3">
                    <aui:input
                            name="download_${downloadId}"
                            label=""
                            type="checkbox"
                            userinfo="<%= download.isUserInfoRequired() %>"
                            subscription="<%= download.isShowSubscription() %>"
                            billinginfo="<%= download.isBillingRequired() %>"
                            terms="<%= download.isTermsOfUseRequired() %>"
                            cssClass="download"
                            checked="true"/>
                </div>
                <div class="float-left w-100">
                    <%
                        JournalArticleDisplay articleDisplay;
                        try {
                            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                                    themeDisplay.getScopeGroupId(), downloadId, ddmTemplateKey, "VIEW",
                                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(renderRequest, renderResponse),
                                    themeDisplay);
                        } catch (PortalException e) {
                            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                                    downloadId, ddmTemplateKey);
                            SessionErrors.add(liferayPortletRequest, "sendlink-failed", message);
                            continue;
                        }
                    %>
                    <liferay-journal:journal-article-display
                            articleDisplay="<%= articleDisplay %>"
                    />
                </div>
            </div>
        </div>
    </c:if>

</c:forEach>