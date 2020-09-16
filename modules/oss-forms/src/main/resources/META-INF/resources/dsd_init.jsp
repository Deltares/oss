<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ taglib uri="http://liferay.com/tld/journal" prefix="liferay-journal" %>


<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>
<%@ page import="nl.deltares.portal.model.impl.Event" %>
<%@ page import="nl.deltares.portal.model.impl.Registration" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.utils.KeycloakUtils" %>
<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    ConfigurationProvider configurationProvider =
    (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    DSDSiteConfiguration configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

    String conditionsURL = configuration.conditionsURL();

    String homeUrl = themeDisplay.getCDNBaseURL();
    String webUrl = themeDisplay.getPathFriendlyURLPublic();
    String groupUrl = themeDisplay.getSiteGroup().getFriendlyURL();
    String privacyURL = homeUrl + webUrl + groupUrl + configuration.privacyURL();
    String contactURL = homeUrl + webUrl + groupUrl + configuration.contactURL();
%>