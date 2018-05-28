<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@page import="com.liferay.taglib.servlet.PipingServletResponse"%>
<%@page import="com.liferay.util.portlet.PortletProps"%>
<%@page import="com.liferay.portal.kernel.configuration.Filter"%>
<%@page import="com.liferay.portal.kernel.util.TermsOfUseContentProvider"%>
<%@page import="com.liferay.portal.kernel.util.TermsOfUseContentProviderRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

String referer = ParamUtil.getString(request, WebKeys.REFERER, currentURL);

if (referer.equals(themeDisplay.getPathMain() + "/portal/update_terms_of_use")) {
	referer = themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId();
}

TermsOfUseContentProvider termsOfUseContentProvider = TermsOfUseContentProviderRegistryUtil.getTermsOfUseContentProvider();

Enumeration enumeration = user.getExpandoBridge().getAttributeNames();

String articleId = "";
String groupId = "";

while(enumeration.hasMoreElements()) {
	String key = (String)enumeration.nextElement();

	if(key.endsWith("EULA")) {
		try {
			if(user.getExpandoBridge().getAttribute(key).equals("false")){
			
				groupId = key.substring(0, key.lastIndexOf("DeveloperEULA") );
			
				articleId = PortletProps.getArray("deltares.oss.communities.eula", 
						new Filter( groupId) )[0];
			}
		} catch (Exception e) {
			
		}
	}
}
%>

<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_terms_of_use" %>' name="fm">
	<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
	<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= referer %>" />
	
	<c:choose>
		<c:when test="<%= termsOfUseContentProvider != null %>">

			<%
			termsOfUseContentProvider.includeView(request, PipingServletResponse.createPipingServletResponse(pageContext));
			%>

		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portal/terms_of_use_default.jsp" />
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(groupId) %>">
			<liferay-ui:journal-article groupId="<%= PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID %>" articleId="<%= articleId %>" />
		</c:when>
		<c:when test="<%= (PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID > 0) && Validator.isNotNull(PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_ID) %>">
			<liferay-ui:journal-article groupId="<%= PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID %>" articleId="<%= PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_ID %>" />
		</c:when>
	</c:choose>


	<c:if test="<%= !user.isAgreedToTermsOfUse() %>">
		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="i-agree" />
			<aui:input type="hidden" name="DeveloperEULAAgreedOn" value="<%= groupId %>" />
			
			<%
			String taglibOnClick = "alert('" + UnicodeLanguageUtil.get(request, "you-must-agree-with-the-terms-of-use-to-continue") + "');";
			%>

			<aui:button cssClass="btn-lg" onClick="<%= taglibOnClick %>" type="cancel" value="i-disagree" />
		</aui:button-row>
	</c:if>
	
</aui:form>