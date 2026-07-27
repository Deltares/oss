<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ taglib prefix="portlet" uri="http://xmlns.jcp.org/portlet_3_0" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="nl.deltares.search.results.SearchResultsPortletDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.portal.model.facet.FacetSelection" %>
<%@ page import="jakarta.portlet.PortletURL" %>

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

    FacetSelection facetSelection = searchResultsPortletDisplayContext.getFacetSelection();
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
            String dateHeader;
            if (context.getRegistration().isToBeDetermined()) {
                dateHeader = LanguageUtil.format(locale, "dsd.theme.session.tobedetermined", java.util.Optional.empty());
            } else {
                dateHeader = date;
            }
            String colorClass;
            if (context.isPastEvent()) {
                colorClass = "past-event";
            } else {
                colorClass = "upcoming-event";
            }
            try {
                portletSession.setAttribute("search-results-registration-articleId", context.getRegistration().getArticleId());
                portletSession.setAttribute("search-results-registration-day", context.getDayCount());
                if (facetSelection != null){
                    portletSession.setAttribute("search-results-facet-selection", facetSelection);
                }
            } catch (Exception e){
                SessionErrors.add(renderRequest, "session error " + e.getMessage());
            }

        %>

        <liferay-ui:search-container-column-text
                colspan="<%= 2 %>"
        >
            <c:if test="<%= writeDateHeader %>">
                <div class="date-title <%= colorClass %>">
                    <span><%= dateHeader %></span>
                </div>
            </c:if>
            <%
                Registration registration = context.getRegistration();
                //Point to the site of the article.
                if (facetSelection != null){
                    themeDisplay.setScopeGroupId(facetSelection.getSiteGroupId());
                }
            %>
            <liferay-journal:journal-article
                    article="<%= registration.getJournalArticle() %>" ddmTemplateKey="<%= templateKey %>" groupId="<%= themeDisplay.getSiteGroupId() %>"
            />
        </liferay-ui:search-container-column-text>

        <%
            //clean up after use (still to be tested)
            try {
                portletSession.setAttribute("search-results-registration-articleId", null);
                portletSession.setAttribute("search-results-registration-day", null);
                portletSession.setAttribute("search-results-facet-selection", null);
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