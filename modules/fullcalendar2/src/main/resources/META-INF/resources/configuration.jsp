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

<%@ page import="com.liferay.portal.kernel.util.Constants" %>


<%@ include file="init.jsp" %>

<liferay-portlet:actionURL
        portletConfiguration="<%= true %>"
        var="configurationActionURL"
/>

<liferay-portlet:renderURL
        portletConfiguration="<%= true %>"
        var="configurationRenderURL"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
    <aui:input
            name="<%= Constants.CMD %>"
            type="hidden"
            value="<%= Constants.UPDATE %>"
    />

    <aui:input
            name="redirect"
            type="hidden"
            value="<%= configurationRenderURL %>"
    />

    <aui:fieldset>
        <aui:input
                label="Calender Base URL"
                name="baseUrl"
                value="<%= baseUrl %>"
                >

        </aui:input>

        <%    for (String key : colorMap.keySet()) { %>
        <aui:input
                label='<%=( key ) + " Color"%>'
                name="<%= key %>"
                type="color"
                value="<%= colorMap.get(key) %>"
        >
        </aui:input>
        <% } %>

        <aui:input
                label="Defaul calendar view"
                name="defaultView"
                helpMessage="full-calendar.defaultValue"
                value="<%= defaultView %>"
        >

        </aui:input>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>