<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:resourceURL id="/activity-map/last-logs" var="lastLogsURL" />

<%-- The property "google.maps.api.key" needs to be added to Liferay's
     portal-ext.properties, and the portal needs to be restarted aftewards. 
	 
     google.maps.api.key=your_key	 
--%>

<%@ page import="com.liferay.portal.kernel.util.PropsUtil" %>

<% String apiKey = PropsUtil.get("google.maps.api.key"); %>
