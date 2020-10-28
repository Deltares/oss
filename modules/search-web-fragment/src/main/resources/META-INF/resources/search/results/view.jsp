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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
        taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
        taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@
        page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
        page import="com.liferay.portal.kernel.search.Document" %><%@
        page import="com.liferay.portal.kernel.theme.ThemeDisplay" %><%@
        page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
        page import="com.liferay.portal.kernel.util.Validator" %><%@
        page import="com.liferay.portal.kernel.util.WebKeys" %><%@
        page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %>
<%@ page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %>
<%@ page import="nl.deltares.portal.display.context.RegistrationDisplayContext" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="nl.deltares.portal.kernel.util.comparator.SearchResultsComparator" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.utils.impl.DsdParserUtilsImpl" %>

<portlet:defineObjects />

<%
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    DsdParserUtils dsdParserUtils = new DsdParserUtilsImpl();
    String lastDate = "";
%>

<%
    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = (SearchResultsPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }

    com.liferay.portal.kernel.dao.search.SearchContainer<com.liferay.portal.kernel.search.Document> searchContainer1 = searchResultsPortletDisplayContext.getSearchContainer();
    List<Document> results = searchContainer1.getResults();
    Collections.sort(results, new SearchResultsComparator(dsdParserUtils));
%>

<style>
    .list-group-item-flex .autofit-col {
        padding: 0;
    }

    .expert-name  {
        font-weight: bold;
    }

    .date-title {
        text-align: center;
        color: white; }
    .date-title span {
        font-size: 25px;
        display: block;
        padding-top: 5px;
        padding-bottom: 5px;
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
        searchContainer="<%= searchContainer1 %>"
>
    <liferay-ui:search-container-row
            cssClass="reservation-result-row"
            className="com.liferay.portal.kernel.search.Document"
            escapedModel="<%= false %>"
            keyProperty="UID"
            modelVar="document"
            stringKey="<%= true %>"
    >

        <%
            SearchResultSummaryDisplayContext searchResultSummaryDisplayContext = java.util.Objects.requireNonNull(searchResultsPortletDisplayContext.getSearchResultSummaryDisplayContext(document));
            RegistrationDisplayContext registrationDisplayContext = new RegistrationDisplayContext(searchResultSummaryDisplayContext.getClassPK(), themeDisplay, dsdParserUtils);

            String date = registrationDisplayContext.getStartDate();
            boolean writeDateHeader = !date.isEmpty() && !lastDate.equals(date);
            lastDate = date;

            String colorClass;
           if (registrationDisplayContext.isPastEvent()){
               colorClass = "past-event";
           } else {
               colorClass = "upcoming-event";
           }

        %>

        <c:choose>
            <c:when test="<%= !searchResultSummaryDisplayContext.isTemporarilyUnavailable() %>">
                <liferay-ui:search-container-column-text
                        colspan="<%= 2 %>"
                >
                    <c:if test="<%= writeDateHeader %>">
                        <div class="date-title <%= colorClass %>">
                            <span><%= date %></span>
                        </div>
                    </c:if>
                    <div class="row no-gutters">
                        <div class="col-2">
                            <img class="img-fluid" src="<%= registrationDisplayContext.getSmallImageURL() %>"/>
                        </div>
                        <div class="col-10 px-3">
                            <h4>
                                <a href="<%= searchResultSummaryDisplayContext.getViewURL() %>">
                                    <strong><%= searchResultSummaryDisplayContext.getHighlightedTitle() %></strong>
                                </a>
                            </h4>

                            <div>
                                <c:if test="<%= Validator.isNotNull(registrationDisplayContext.getPresenterSmallImageURL()) %>">
                                    <img width="32" class="expert-thumbnail" src="<%= registrationDisplayContext.getPresenterSmallImageURL() %>" />
                                </c:if>
                                <c:if test="<%= Validator.isNotNull(registrationDisplayContext.getPresenterName()) %>">
                                    <span class="expert-name px-2"><%= registrationDisplayContext.getPresenterName() %></span> |
                                </c:if>
                                 <span class="event-time pl-2"><%= registrationDisplayContext.getStartTime() %> - <%= registrationDisplayContext.getEndTime() %></span> |
                                <c:choose>
                                    <c:when test="<%= registrationDisplayContext.getPrice() > 0%>">
                                        <%= registrationDisplayContext.getCurrency() %> <%= registrationDisplayContext.getPrice() %>
                                    </c:when>
                                    <c:otherwise>
                                        <liferay-ui:message key="dsd.theme.session.free" translateArguments="<%= true %>" />
                                    </c:otherwise>
                                </c:choose>

                            </div>

                            <c:if test="<%= searchResultSummaryDisplayContext.isContentVisible() %>">
                                <p class="search-document-content text-default">
                                    <%= HtmlUtil.stripHtml(HtmlUtil.unescape(registrationDisplayContext.getSummary())) %>
                                </p>
                            </c:if>
                        </div>
                    </div>

                </liferay-ui:search-container-column-text>
            </c:when>
            <c:otherwise>
                <liferay-ui:search-container-column-text
                        colspan="<%= 3 %>"
                >
                    <div class="alert alert-danger">
                        <liferay-ui:message arguments="result" key="is-temporarily-unavailable" translateArguments="<%= true %>" />
                    </div>
                </liferay-ui:search-container-column-text>
            </c:otherwise>
        </c:choose>
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
    A.one('#<portlet:namespace />searchContainerTag').delegate(
    'click',
    function(event) {
    var currentTarget = event.currentTarget;

    currentTarget.siblings('.table-details').toggleClass('hide');
    },
    '.expand-details'
    );
</aui:script>