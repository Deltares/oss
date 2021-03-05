<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="nl.deltares.portal.configuration.OSSSiteConfiguration" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    OSSSiteConfiguration configuration = configurationProvider.getGroupConfiguration(OSSSiteConfiguration.class, themeDisplay.getScopeGroupId());

    boolean enableSiteId = configuration.enableSiteId();

%>
