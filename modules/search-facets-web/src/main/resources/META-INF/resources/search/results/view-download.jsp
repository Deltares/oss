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

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
            (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }
    String templateKey = (String) renderRequest.getAttribute("displayTemplate");
%>
<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="mvcPath" value="/search/results/view-download.jsp" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
        emptyResultsMessage='<%= LanguageUtil.format(request, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false) %>'
        id='<%= renderResponse.getNamespace() + "searchContainerTag" %>'
        iteratorURL="<%= iteratorURL %>"
        delta="<%= searchResultsPortletDisplayContext.getDelta() %>"
        total="<%= searchResultsPortletDisplayContext.getTotalHits() %>"
>
    <liferay-ui:search-container-results results="<%= searchResultsPortletDisplayContext.getDsdArticleResults() %>"/>

    <liferay-ui:search-container-row
            className="nl.deltares.portal.model.DsdArticle"
            modelVar="article"
    >

        <liferay-ui:search-container-column-text
                colspan="<%= 2 %>"
        >
            <%
                JournalArticleDisplay articleDisplay = FacetUtils
                        .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey, article.getArticleId(), themeDisplay);
            %>

            <liferay-journal:journal-article-display
                    articleDisplay="<%= articleDisplay %>"
            />
        </liferay-ui:search-container-column-text>

    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator
            displayStyle="descriptive"
            markupView="lexicon"
            type="more"
    />
</liferay-ui:search-container>