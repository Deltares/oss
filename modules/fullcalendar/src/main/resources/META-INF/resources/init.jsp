<%--
/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.portal.model.impl.Event" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.utils.DsdParserUtils" %>
<%@ page import="nl.deltares.portal.configuration.DSDSiteConfiguration" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationException" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="nl.deltares.fullcalendar.portlet.FullCalendarConfiguration" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    String eventId = null;
    DSDSiteConfiguration dsdSiteConfiguration;
    try {
        dsdSiteConfiguration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
        eventId = String.valueOf(dsdSiteConfiguration.eventId());
    } catch (ConfigurationException e) {
        //
    }

    SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    long siteId = themeDisplay.getSiteGroupId();
    FullCalendarConfiguration configuration =
            (FullCalendarConfiguration)
                    renderRequest.getAttribute(FullCalendarConfiguration.class.getName());
    String defaultView = "verticalWeek";
    String baseUrl = "";

    Date startDateTime = (Date) renderRequest.getAttribute("startDate");

    String colorMap = "";
    if (Validator.isNotNull(configuration)) {
        //noinspection UnusedAssignment
        baseUrl = portletPreferences.getValue("baseUrl", configuration.baseUrl());
        String sessionColorMap = portletPreferences.getValue("sessionColorMap", configuration.sessionColorMap());

        try {
            DsdParserUtils dsdUtils = (DsdParserUtils) renderRequest.getAttribute(DsdParserUtils.class.getName());
            Event event = dsdUtils.getEvent(siteId, eventId, themeDisplay.getLocale());
            if (startDateTime == null) {
                startDateTime = new Date();
                if (event != null && (event.getStartTime().after(startDateTime) || event.getEndTime().before(startDateTime))) {
                    //if event is in the future or completely in the past.
                    startDateTime = event.getStartTime();
                }
            }
            //noinspection UnusedAssignment
            Map<String, String> finalColorMap = JsonContentUtils.parseSessionColorConfig(sessionColorMap);
            Map<String, String> typeMap = (Map<String, String>) renderRequest.getAttribute("typeMap");
            typeMap.keySet().forEach(typeKey -> {
                finalColorMap.putIfAbsent(typeKey, "#17a2b8");
            });
            colorMap = JsonContentUtils.formatMapToJson(finalColorMap);
            defaultView = portletPreferences.getValue("defaultView", configuration.defaultView());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    String startDate = format.format(startDateTime == null ? new Date() : startDateTime);

%>