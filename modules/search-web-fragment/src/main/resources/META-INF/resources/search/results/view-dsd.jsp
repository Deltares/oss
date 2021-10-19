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
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationException" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %>
<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %>
<%@ page import="com.liferay.portal.template.ServiceLocator" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>
<%@ page import="nl.deltares.portal.display.context.RegistrationDisplayContext" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.search.results.DsdResultsSearchContainer" %>

<portlet:defineObjects/>
<%
    DsdParserUtils dsdParserUtils = (DsdParserUtils) ServiceLocator.getInstance().findService("nl.deltares.portal.utils.DsdParserUtils");
    String lastDate = "";

    NumberFormat numberFormat = NumberFormat.getInstance();
    numberFormat.setGroupingUsed(false);
    numberFormat.setMinimumFractionDigits(0);
    numberFormat.setMaximumFractionDigits(2);

    DSDSiteConfiguration configuration;
    String templateKey = "";

    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    try {
        configuration = ConfigurationProviderUtil.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
        final String templateMapJson = configuration.templateMap();
        Map<String, String> templateMap = JsonContentUtils.parseSessionColorConfig(templateMapJson);
        final String callerPortletId = themeDisplay.getPortletDisplay().getId();
        templateKey = templateMap.getOrDefault(callerPortletId, "PROGRAM-LIST");

    } catch (ConfigurationException e) {
        //
    }
%>

<%
    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = (SearchResultsPortletDisplayContext) java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }

    final DsdResultsSearchContainer dsdResultsSearchContainer = new DsdResultsSearchContainer(
            searchResultsPortletDisplayContext.getSearchContainer(), themeDisplay, dsdParserUtils);
%>

<style>
    .date-title {
        text-align: center;
        color: white;
    }

    .date-title span {
        font-size: 25px;
        display: block;
        padding-top: 5px;
        padding-bottom: 5px;
    }

    .list-group-item-flex .autofit-col {
        padding: 0;
    }

    .past-event {
        background: #9B9B9B;
    }

    .upcoming-event {
        background: #0D38E0;
    }

</style>

<liferay-ui:search-container
        emptyResultsMessage='<%= LanguageUtil.format(request, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false) %>'
        id='<%= renderResponse.getNamespace() + "searchContainerTag" %>'
        searchContainer="<%= dsdResultsSearchContainer %>"
>
    <liferay-ui:search-container-row
            cssClass="reservation-result-row"
            className="nl.deltares.portal.display.context.RegistrationDisplayContext"
            escapedModel="<%= false %>"
            keyProperty="UID"
            modelVar="registrationDisplayContext"
            stringKey="<%= true %>"
    >

        <%
            String date = registrationDisplayContext.getStartDate();
            boolean writeDateHeader = !date.isEmpty() && !lastDate.equals(date);
            lastDate = date;

            String colorClass;
            if (registrationDisplayContext.isPastEvent()) {
                colorClass = "past-event";
            } else {
                colorClass = "upcoming-event";
            }
            liferayPortletRequest.getPortletSession().setAttribute("program-list-registration-articleId", registrationDisplayContext.getRegistration().getArticleId());
            liferayPortletRequest.getPortletSession().setAttribute("program-list-registration-day", registrationDisplayContext.getDayCount());
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
                JournalArticleDisplay articleDisplay = RegistrationDisplayContext
                        .getArticleDisplay(liferayPortletRequest, liferayPortletResponse, templateKey, registrationDisplayContext.getRegistration().getArticleId(), themeDisplay);
            %>
            <liferay-journal:journal-article-display
                    articleDisplay="<%= articleDisplay %>"
            />
        </liferay-ui:search-container-column-text>

        <%
            //clean up after use (still to be tested)
            liferayPortletRequest.getPortletSession().setAttribute("program-list-registration-articleId", null);
            liferayPortletRequest.getPortletSession().setAttribute("program-list-registration-day", null);
        %>
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