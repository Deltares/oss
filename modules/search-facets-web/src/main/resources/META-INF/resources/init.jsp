<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.search.facet.date.DateRangeFacetConfiguration" %>
<%--Required by implementing JSPs--%>
<%@ page import="nl.deltares.portal.model.DsdArticle" %>
<liferay-theme:defineObjects />

<portlet:defineObjects />

<%

    DateRangeFacetConfiguration configuration =
            (DateRangeFacetConfiguration)
                    renderRequest.getAttribute(DateRangeFacetConfiguration.class.getName());
    String startDateConfig = null;
    String endDateConfig = null;
    String setStartNowConfig = null;

    if (Validator.isNotNull(configuration)) {
        startDateConfig = portletPreferences.getValue("startDate", configuration.startDate());
        endDateConfig = portletPreferences.getValue("endDate", configuration.endDate());
        setStartNowConfig = portletPreferences.getValue("setStartNow", configuration.setStartNow());
    }

%>