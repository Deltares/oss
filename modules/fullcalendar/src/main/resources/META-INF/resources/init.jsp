<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    String mainRequire = (String)renderRequest.getAttribute("mainRequire");
    String eventId = "blank";
    String baseUrl = "/o/dsd";
//    String authUser = StringPool.BLANK;
//    String authPassword = StringPool.BLANK;
    //todo configuration not working
//    FullCalendarConfiguration configuration = (FullCalendarConfiguration) renderRequest.getAttribute(FullCalendarConfiguration.class.getName());
//    if (Validator.isNotNull(configuration)) {
//        eventId = configuration.eventId();
//        baseUrl = configuration.baseUrl();
//        authUser = configuration.authUser();
//        authPassword = configuration.authPassword();
//    }
    boolean canEdit = (boolean) renderRequest.getAttribute("hasEditPermission");
%>

