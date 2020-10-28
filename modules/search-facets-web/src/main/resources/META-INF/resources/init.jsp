<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.search.facet.date.DateRangeFacetConfiguration" %>
<%@ page import="nl.deltares.portal.model.DsdArticle" %>
<%@ page import="nl.deltares.search.facet.registration.RegistrationFacetConfiguration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.json.JSONException" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%

    DateRangeFacetConfiguration configuration =
            (DateRangeFacetConfiguration)
                    renderRequest.getAttribute(DateRangeFacetConfiguration.class.getName());
    String startDateConfig = null;
    String endDateConfig = null;

    if (Validator.isNotNull(configuration)) {
        startDateConfig = portletPreferences.getValue("startDate", configuration.startDate());
        endDateConfig = portletPreferences.getValue("endDate", configuration.endDate());
    }

    RegistrationFacetConfiguration reg_configuration =
            (RegistrationFacetConfiguration)
                    renderRequest.getAttribute(RegistrationFacetConfiguration.class.getName());

    List<String> structureList = new ArrayList<>();

    if (Validator.isNotNull(reg_configuration)){
        try {
            structureList = JsonContentUtils.parseJsonArrayToList(portletPreferences.getValue("structureList", reg_configuration.structureList()));
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

%>