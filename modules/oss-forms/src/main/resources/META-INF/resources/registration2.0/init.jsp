<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>
<%@ taglib prefix="liferay-adaptive-media" uri="http://liferay.com/tld/adaptive-media-image" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>

<%@ page import="com.liferay.account.model.AccountEntry" %>
<%@ page import="com.liferay.portal.kernel.model.Address" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Address" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.DateUtil" %>
<%@ page import="com.liferay.dynamic.data.mapping.expression.model.Term" %>

<%@ page import="nl.deltares.model.RegistrationInfo" %>
<%@ page import="nl.deltares.portal.model.impl.Terms" %>
<%@ page import="nl.deltares.portal.utils.Period" %>
<%@ page import="nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext" %>
<%@ page import="nl.deltares.forms.constants.OrganizationConstants" %>
<%@ page import="nl.deltares.forms.constants.BillingConstants" %>
<%@ page import="nl.deltares.forms.constants.OrganizationConstants" %>
<%@ page import="nl.deltares.forms.internal.BillingDetailsCheckoutStepDisplayContext" %>
<%@ page import="nl.deltares.model.BillingInfo" %>
<%@ page import="nl.deltares.forms.constants.CheckoutWebKeys" %>
<%@ page import="nl.deltares.forms.exception.RegistrationFormException" %>
<%@ page import="nl.deltares.forms.internal.CheckoutDisplayContext" %>
<%@ page import="nl.deltares.forms.util.DeltaresCheckoutStep" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.forms.internal.*" %>
<%@ page import="nl.deltares.portal.model.subscriptions.SubscriptionSelection" %>
<%@ page import="nl.deltares.portal.constants.OssConstants" %>

<%@ page import="java.util.Collections" %>
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
<portlet:renderURL var="previousStepURL">
    <portlet:param name="checkoutStepName" value="<%= checkoutDisplayContext.getPreviousCheckoutStepName() %>" />
    <portlet:param name="ids" value="<%= ids %>" />
</portlet:renderURL>

