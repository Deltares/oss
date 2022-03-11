<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>

<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletRequestModel" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>
<%@ page import="nl.deltares.portal.configuration.DownloadSiteConfiguration" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationException" %>
<%@ page import="nl.deltares.search.results.DownloadResultsSearchContainer" %>
<%@ page import="com.liferay.portal.template.ServiceLocator" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.display.context.DownloadDisplayContext" %>

<portlet:defineObjects />
<%
    DsdParserUtils dsdParserUtils = (DsdParserUtils) ServiceLocator.getInstance().findService("nl.deltares.portal.utils.DsdParserUtils");
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }
    String templateKey = "";
    try {
        DownloadSiteConfiguration configuration = ConfigurationProviderUtil.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
        final String templateMapJson = configuration.templateMap();
        Map<String, String> templateMap = JsonContentUtils.parseSessionColorConfig(templateMapJson);
        final String callerPortletId = themeDisplay.getPortletDisplay().getId();
        templateKey = templateMap.getOrDefault(callerPortletId, "DOWNLOAD-LIST");

    } catch (ConfigurationException e) {
        //
    }
    final DownloadResultsSearchContainer downloadResultsSearchContainer = new DownloadResultsSearchContainer(
            searchResultsPortletDisplayContext.getSearchContainer(), themeDisplay, dsdParserUtils);%>

<liferay-ui:search-container
        emptyResultsMessage='<%= LanguageUtil.format(request, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false) %>'
        id='<%= renderResponse.getNamespace() + "searchContainerTag" %>'
        searchContainer="<%= downloadResultsSearchContainer %>"
>
    <liferay-ui:search-container-row
            cssClass="reservation-result-row"
            className="nl.deltares.portal.display.context.DownloadDisplayContext"
            escapedModel="<%= false %>"
            keyProperty="UID"
            modelVar="downloadDisplayContect"
            stringKey="<%= true %>"
    >

        <liferay-ui:search-container-column-text
                colspan="<%= 2 %>"
        >
            <%
                JournalArticleDisplay articleDisplay = DownloadDisplayContext
                        .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey, downloadDisplayContect.getDownload().getArticleId(), themeDisplay);
            %>

            <liferay-journal:journal-article-display
                    articleDisplay="<%= articleDisplay %>"
            />
        </liferay-ui:search-container-column-text>

    </liferay-ui:search-container-row>

    <aui:form useNamespace="<%= false %>">
        <liferay-ui:search-iterator
                displayStyle="descriptive"
                markupView="lexicon"
                type="more"
        />
    </aui:form>
</liferay-ui:search-container>

<aui:script use="aui-base">
    A.one('#<portlet:namespace/>searchContainerTag').delegate(
    'click',
    function(event) {
    var currentTarget = event.currentTarget;

    currentTarget.siblings('.table-details').toggleClass('hide');
    },
    '.expand-details'
    );
</aui:script>