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

<%@ page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationException" %>
<%@ page import="nl.deltares.portal.configuration.DownloadSiteConfiguration" %>

<portlet:defineObjects />

<%
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

    String templateKey = null;
    boolean dsdPage = false;
    boolean downloadPage = false;
    try {
        DSDSiteConfiguration configuration = ConfigurationProviderUtil.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
        final String templateMapJson = configuration.templateMap();
        Map<String, String> templateMap = JsonContentUtils.parseSessionColorConfig(templateMapJson);
        final String callerPortletId = themeDisplay.getPortletDisplay().getId();
        templateKey = templateMap.getOrDefault(callerPortletId, null);

        dsdPage = templateKey != null;
    } catch (ConfigurationException e) {
        //
    }

    try {
        DownloadSiteConfiguration configuration = ConfigurationProviderUtil.getGroupConfiguration(DownloadSiteConfiguration.class, themeDisplay.getScopeGroupId());
        final String templateMapJson = configuration.templateMap();
        Map<String, String> templateMap = JsonContentUtils.parseSessionColorConfig(templateMapJson);
        final String callerPortletId = themeDisplay.getPortletDisplay().getId();
        templateKey = templateMap.getOrDefault(callerPortletId, null);

        downloadPage = templateKey != null;
    } catch (ConfigurationException e) {
        //
    }
%>

<c:choose>
    <c:when test='<%= dsdPage %>'>
        <jsp:include page="view-dsd.jsp" />
    </c:when>
    <c:when test='<%= downloadPage %>'>
        <jsp:include page="view-download.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="view-original.jsp" />
    </c:otherwise>
</c:choose>