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

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
        page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
        page import="com.liferay.portal.kernel.util.WebKeys" %><%@
        page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultFieldDisplayContext" %><%@
        page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %><%@
        page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %>

<portlet:defineObjects />

<%
    SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = (SearchResultsPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

    if (searchResultsPortletDisplayContext.isRenderNothing()) {
        return;
    }

    com.liferay.portal.kernel.dao.search.SearchContainer<com.liferay.portal.kernel.search.Document> searchContainer1 = searchResultsPortletDisplayContext.getSearchContainer();
%>

<style>
    .taglib-asset-tags-summary a.badge, .taglib-asset-tags-summary a.badge:hover {
        color: #65B6F0;
    }

    .search-total-label {
        margin-top: 35px;
    }

    .search-asset-type-sticker {
        color: #869CAD;
    }

    .search-document-content {
        font-weight: 400;
    }

    .search-result-thumbnail-img {
        height: 44px;
        width: 44px;
    }

    .tabular-list-group .list-group-item-content h6.search-document-tags {
        margin-top: 13px;
    }
</style>

<p class="search-total-label text-default">
    <liferay-ui:message arguments='<%= new String[] {String.valueOf(searchContainer1.getTotal()), "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"} %>' key="x-results-for-x" />
</p>



<liferay-ui:search-container
        emptyResultsMessage='<%= LanguageUtil.format(request, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>", false) %>'
        id='<%= renderResponse.getNamespace() + "searchContainerTag" %>'
        searchContainer="<%= searchContainer1 %>"
>
    <liferay-ui:search-container-row
            className="com.liferay.portal.kernel.search.Document"
            escapedModel="<%= false %>"
            keyProperty="UID"
            modelVar="document"
            stringKey="<%= true %>"
    >

        <%
            SearchResultSummaryDisplayContext searchResultSummaryDisplayContext = java.util.Objects.requireNonNull(searchResultsPortletDisplayContext.getSearchResultSummaryDisplayContext(document));
        %>

        <c:choose>
            <c:when test="<%= !searchResultSummaryDisplayContext.isTemporarilyUnavailable() %>">
                <liferay-ui:search-container-column-text>
                    <c:if test="<%= searchResultSummaryDisplayContext.isUserPortraitVisible() %>">
                        <liferay-ui:user-portrait
                                userId="<%= searchResultSummaryDisplayContext.getAssetEntryUserId() %>"
                        />
                    </c:if>

                    <c:if test="<%= searchResultSummaryDisplayContext.isThumbnailVisible() %>">
                        <img alt="blog cover image" class="img-rounded search-result-thumbnail-img" src="<%= searchResultSummaryDisplayContext.getThumbnailURLString() %>" />
                    </c:if>

                    <c:if test="<%= searchResultSummaryDisplayContext.isIconVisible() %>">
						<span class="search-asset-type-sticker sticker sticker-rounded sticker-secondary sticker-static">
							<svg class="lexicon-icon">
								<use xlink:href="<%= searchResultSummaryDisplayContext.getPathThemeImages() %>/lexicon/icons.svg#<%= searchResultSummaryDisplayContext.getIconId() %>" />

								<title><%= searchResultSummaryDisplayContext.getIconId() %></title>
							</svg>
						</span>
                    </c:if>
                </liferay-ui:search-container-column-text>

                <liferay-ui:search-container-column-text
                        colspan="<%= 2 %>"
                >
                    <h4>
                        <a href="<%= searchResultSummaryDisplayContext.getViewURL() %>">
                            <strong><%= searchResultSummaryDisplayContext.getHighlightedTitle() %></strong>
                        </a>
                    </h4>

                    <h6 class="text-default">
                        <strong><%= searchResultSummaryDisplayContext.getModelResource() %></strong> &#183;

                        <c:if test="<%= searchResultSummaryDisplayContext.isLocaleReminderVisible() %>">
                            <liferay-ui:icon
                                    image='<%= "../language/" + searchResultSummaryDisplayContext.getLocaleLanguageId() %>'
                                    message="<%= searchResultSummaryDisplayContext.getLocaleReminder() %>"
                            />
                        </c:if>

                        <c:if test="<%= searchResultSummaryDisplayContext.isCreatorVisible() %>">
                            <liferay-ui:message key="written-by" /> <strong><%= HtmlUtil.escape(searchResultSummaryDisplayContext.getCreatorUserName()) %></strong>
                        </c:if>

                        <c:if test="<%= searchResultSummaryDisplayContext.isCreationDateVisible() %>">
                            <liferay-ui:message key="on-date" /> <%= searchResultSummaryDisplayContext.getCreationDateString() %>
                        </c:if>
                    </h6>

                    <c:if test="<%= searchResultSummaryDisplayContext.isContentVisible() %>">
                        <h6 class="search-document-content text-default">
                            <%= searchResultSummaryDisplayContext.getContent() %>
                        </h6>
                    </c:if>

                    <c:if test="<%= searchResultSummaryDisplayContext.isAssetCategoriesOrTagsVisible() %>">
                        <h6 class="search-document-tags text-default">
                            <liferay-asset:asset-tags-summary
                                    className="<%= searchResultSummaryDisplayContext.getClassName() %>"
                                    classPK="<%= searchResultSummaryDisplayContext.getClassPK() %>"
                                    paramName="<%= searchResultSummaryDisplayContext.getFieldAssetTagNames() %>"
                                    portletURL="<%= searchResultSummaryDisplayContext.getPortletURL() %>"
                            />

                            <liferay-asset:asset-categories-summary
                                    className="<%= searchResultSummaryDisplayContext.getClassName() %>"
                                    classPK="<%= searchResultSummaryDisplayContext.getClassPK() %>"
                                    paramName="<%= searchResultSummaryDisplayContext.getFieldAssetCategoryIds() %>"
                                    portletURL="<%= searchResultSummaryDisplayContext.getPortletURL() %>"
                            />
                        </h6>
                    </c:if>

                    <c:if test="<%= searchResultSummaryDisplayContext.isDocumentFormVisible() %>">
                        <h6 class="expand-details text-default"><a href="javascript:;"><liferay-ui:message key="details" />...</a></h6>

                        <div class="hide table-details table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>
                                        <liferay-ui:message key="key" />
                                    </th>
                                    <th>
                                        <liferay-ui:message key="value" />
                                    </th>
                                </tr>
                                </thead>

                                <tbody>

                                <%
                                    for (SearchResultFieldDisplayContext searchResultFieldDisplayContext : searchResultSummaryDisplayContext.getDocumentFormFieldDisplayContexts()) {
                                %>

                                <tr>
                                    <td style="word-break: break-all;" width="25%">
                                        <strong><%= HtmlUtil.escape(searchResultFieldDisplayContext.getName()) %></strong>
                                    </td>
                                    <td>
                                        <code>
                                            <%= HtmlUtil.escape(searchResultFieldDisplayContext.getValuesToString()) %>
                                        </code>
                                    </td>
                                </tr>

                                <%
                                    }
                                %>

                                </tbody>
                            </table>
                        </div>
                    </c:if>
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