<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>

<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="nl.deltares.search.results.SearchResultsPortletDisplayContext" %>
<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    String lastDate = "";
    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
            (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }
    String templateKey = (String) renderRequest.getAttribute("displayTemplate");
    PortletURL iteratorURL = (PortletURL) renderRequest.getAttribute("iteratorURL");

%>

<liferay-ui:search-container
        emptyResultsMessage='<%= LanguageUtil.format(request, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false) %>'
        id='<%= renderResponse.getNamespace() + "searchContainerTag" %>'
        iteratorURL="<%= iteratorURL %>"
        delta="<%= searchResultsPortletDisplayContext.getDelta() %>"
        total="<%= searchResultsPortletDisplayContext.getTotalHits() %>"
>
    <liferay-ui:search-container-results results="<%= searchResultsPortletDisplayContext.getRegistrationResults() %>"/>

    <liferay-ui:search-container-row
            className="nl.deltares.portal.display.context.RegistrationDisplayContext"
            modelVar="context"
    >

        <%
            String date = context.getStartDate();
            boolean writeDateHeader = !date.isEmpty() && !lastDate.equals(date);
            lastDate = date;

            String colorClass;
            if (context.isPastEvent()) {
                colorClass = "past-event";
            } else {
                colorClass = "upcoming-event";
            }
            try {
                portletSession.setAttribute("program-list-registration-articleId", context.getRegistration().getArticleId());
                portletSession.setAttribute("program-list-registration-day", context.getDayCount());
            } catch (Exception e){
                SessionErrors.add(renderRequest, "session error " + e.getMessage());
            }

        %>

        <liferay-ui:search-container-column-text
                colspan="<%= 2 %>"
        >
            <c:if test="<%= writeDateHeader %>">
                <div class="date-title <%= colorClass %>">
                    <span><%= date %></span>
                </div>
            </c:if>
            <%
                JournalArticleDisplay articleDisplay = FacetUtils
                        .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey, context.getRegistration().getArticleId(), themeDisplay);
            %>
            <liferay-journal:journal-article-display
                    articleDisplay="<%= articleDisplay %>"
            />
        </liferay-ui:search-container-column-text>

        <%
            //clean up after use (still to be tested)
            try {
                portletSession.setAttribute("program-list-registration-articleId", null);
                portletSession.setAttribute("program-list-registration-day", null);
            } catch (Exception e){
                SessionErrors.add(renderRequest, "session error " + e.getMessage());
            }

        %>
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator
            displayStyle="descriptive"
            markupView="lexicon"
            type="more"
    />
</liferay-ui:search-container>