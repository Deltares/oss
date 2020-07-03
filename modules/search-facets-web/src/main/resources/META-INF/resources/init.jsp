<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.portal.model.DsdArticle" %>
<%@ page import="nl.deltares.search.facet.event.EventFacetConfiguration" %>
<%@ page import="nl.deltares.search.util.DateFacetUtil" %>
<%@ page import="java.time.LocalDate" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%

    EventFacetConfiguration configuration =
            (EventFacetConfiguration)
                    renderRequest.getAttribute(EventFacetConfiguration.class.getName());
    String eventId = "0";

    if (Validator.isNotNull(configuration)) {
        eventId = portletPreferences.getValue("eventId", String.valueOf(configuration.eventId()));
    }
%>