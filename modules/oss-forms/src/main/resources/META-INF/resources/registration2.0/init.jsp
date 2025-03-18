<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ taglib prefix="liferay-adaptive-media" uri="http://liferay.com/tld/adaptive-media-image" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>

<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="nl.deltares.forms.constants.CheckoutWebKeys" %>
<%@ page import="nl.deltares.forms.exception.RegistrationFormException" %>
<%@ page import="nl.deltares.forms.internal.CartOverviewDisplayContext" %>
<%@ page import="nl.deltares.forms.internal.CheckoutDisplayContext" %>
<%@ page import="nl.deltares.forms.util.DeltaresCheckoutStep" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    String ids = (String) request.getAttribute("ids");
    CheckoutDisplayContext checkoutDisplayContext = (CheckoutDisplayContext) request.getAttribute(CheckoutWebKeys.PORTLET_DISPLAY_CONTEXT);
    String currentURL = PortalUtil.getCurrentURL(request);
%>
