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

<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.npm.react.portlet.fullcalendar.constants.FullCalendarPortletKeys" %>
<%@ page import="nl.deltares.npm.react.portlet.fullcalendar.portlet.FullCalendarConfiguration" %>
<%@ page import="nl.deltares.portal.utils.DsdRegistrationUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="nl.deltares.portal.model.impl.DsdEvent" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
    long siteId = themeDisplay.getSiteGroupId();
    String bootstrapRequire = (String)renderRequest.getAttribute(FullCalendarPortletKeys.BOOTSTRAP_REQUIRE);
    FullCalendarConfiguration configuration =
            (FullCalendarConfiguration)
                    renderRequest.getAttribute(FullCalendarConfiguration.class.getName());
    String baseUrl = "";
    long eventId = 0;
    String startDate = format.format(new Date());
    if (Validator.isNotNull(configuration)) {
        baseUrl = portletPreferences.getValue("baseUrl", configuration.baseUrl());
        eventId = Long.parseLong(portletPreferences.getValue("eventId", String.valueOf(configuration.eventID())));

        try {
            DsdRegistrationUtils dsdUtils = (DsdRegistrationUtils) renderRequest.getAttribute(DsdRegistrationUtils.class.getName());
            DsdEvent dsdEvent = dsdUtils.getDsdEvent(siteId, eventId);
            startDate = format.format(dsdEvent.getStartDay());
        } catch (PortalException e) {
            System.out.println(e.getMessage());
        }
    }


%>