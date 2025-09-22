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
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="nl.deltares.fullcalendar.portlet.FullCalendarConfiguration" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.TimeZone" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    long siteId = themeDisplay.getSiteGroupId();
    String eventIds = (String) renderRequest.getAttribute("eventIds");
    Date start = (Date) renderRequest.getAttribute("startDate");

    SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));

    FullCalendarConfiguration configuration =
            (FullCalendarConfiguration)
                    renderRequest.getAttribute(FullCalendarConfiguration.class.getName());
    String defaultView = "verticalWeek";
    String baseUrl = "";

    String colorMap = "";
    if (Validator.isNotNull(configuration)) {
        //noinspection UnusedAssignment
        baseUrl = portletPreferences.getValue("baseUrl", configuration.baseUrl());
        String sessionColorMap = portletPreferences.getValue("sessionColorMap", configuration.sessionColorMap());

        try {
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
    String startDate = format.format(start == null ? new Date() : start);

%>