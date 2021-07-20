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

<portlet:defineObjects />

<%
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    DSDSiteConfiguration configuration = ConfigurationProviderUtil.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
%>

<c:choose>
    <c:when test='<%= configuration.dsdSite()  %>'>
        <jsp:include page="view-dsd.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="view-original.jsp" />
    </c:otherwise>
</c:choose>