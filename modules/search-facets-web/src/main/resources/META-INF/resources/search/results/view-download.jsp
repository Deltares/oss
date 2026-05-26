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
<%@ page import="nl.deltares.portal.model.facet.FacetSelection" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
            (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }
    String templateKey = (String) renderRequest.getAttribute("displayTemplate");
    FacetSelection facetSelection = searchResultsPortletDisplayContext.getFacetSelection();
%>
<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="mvcPath" value="/search/results/view-download.jsp" />
</liferay-portlet:renderURL>


<div class="c-events c-row">

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

        <%
            try {
                portletSession.setAttribute("search-results-registration-articleId", article.getArticleId());
                if (facetSelection != null){
                    portletSession.setAttribute("search-results-facet-selection", facetSelection);
                }
            } catch (Exception e){
                SessionErrors.add(renderRequest, "session error " + e.getMessage());
            }

        %>


        <%
            JournalArticleDisplay articleDisplay = FacetUtils
                    .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey,
                            article.getGroupId(), article.getArticleId(), themeDisplay);
        %>

        <liferay-journal:journal-article-display
                articleDisplay="<%= articleDisplay %>"
        />
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator
            displayStyle="descriptive"
            markupView="lexicon"
            type="more"
    />
</liferay-ui:search-container>
</div>