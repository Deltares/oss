<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="liferay-commerce-product" uri="http://liferay.com/tld/commerce-product"%>
<%@ taglib prefix="liferay-frontend" uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="liferay-adaptive-media" uri="http://liferay.com/tld/adaptive-media-image"%>
<%@ taglib prefix="commerce-ui" uri="http://liferay.com/tld/commerce-ui" %>
<%@ page import="com.liferay.commerce.constants.CommercePortletKeys" %>
<%@ page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %>
<%@ page import="com.liferay.commerce.product.data.source.CPDataSourceResult" %>
<%@ page import="com.liferay.commerce.product.constants.CPWebKeys" %>
<%@ page import="com.liferay.commerce.product.model.CPInstance" %>
<%@ page import="com.liferay.commerce.product.content.helper.CPContentHelper" %>
<%@ page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %>
<%@ page import="com.liferay.commerce.service.CommerceOrderItemService" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.liferay.commerce.model.CommerceOrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.commerce.model.CommerceOrder" %>
<%@ page import="com.liferay.commerce.constants.CommerceWebKeys" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />
<liferay-portlet:actionURL name="/commerce_cart_content/update_order_items" portletName="<%= CommercePortletKeys.COMMERCE_CART_CONTENT %>" var="updateCommerceOrderItemURL" />
<%
    String portletNamespace = PortalUtil.getPortletNamespace(CommercePortletKeys.COMMERCE_CART_CONTENT);
    String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_commerce_cart_add_to_cart_button") + StringPool.UNDERLINE;

    CommerceOrder commerceOrder = (CommerceOrder) request.getAttribute(CommerceWebKeys.COMMERCE_ORDER);

    boolean showButtons = themeDisplay.isSignedIn() && commerceOrder != null;

%>



