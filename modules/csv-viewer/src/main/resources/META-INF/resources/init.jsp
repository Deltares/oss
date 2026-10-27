<%@ page import="nl.deltares.csvviewer.configuration.CsvViewerConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
    CsvViewerConfiguration configuration = (CsvViewerConfiguration) renderRequest.getAttribute(CsvViewerConfiguration.class.getName());
    String csvFileUrl = "";
    String filterableColumns = "";
    String password = "";
    String username = "";
    boolean strictValidation = false;
    if (Validator.isNotNull(configuration)) {
        csvFileUrl = portletPreferences.getValue("csvFileUrl", configuration.csvFileUrl());
        filterableColumns = portletPreferences.getValue("filterableColumns", configuration.filterableColumns());
        username = portletPreferences.getValue("username", configuration.username());
        password = portletPreferences.getValue("password", configuration.password());
        strictValidation = GetterUtil.getBoolean(portletPreferences.getValue("strictValidation", String.valueOf(configuration.strictValidation())));
    }
%>

