<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>

<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="nl.deltares.search.results.SearchResultsPortletDisplayContext" %>
<%@ page import="nl.deltares.search.util.FacetUtils" %>
<%@ page import="nl.deltares.portal.model.DsdArticle" %>
<%@ page import="java.util.List" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
            (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }
    String templateKey = (String) renderRequest.getAttribute("displayTemplate");
    List<DsdArticle> dsdArticleResults = searchResultsPortletDisplayContext.getDsdArticleResults();
%>
<div class="c-events c-row">

    <% for (DsdArticle article : dsdArticleResults) {

        JournalArticleDisplay articleDisplay = FacetUtils
                .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey,
                        article.getGroupId(), article.getArticleId(), themeDisplay);

    %>

    <liferay-journal:journal-article-display
            articleDisplay="<%= articleDisplay %>"
    />

    <%
        }
    %>
</div>