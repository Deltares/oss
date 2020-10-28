<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ include file="/META-INF/resources/init.jsp" %>


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

        <%    for (DsdArticle.DSD_REGISTRATION_STRUCTURE_KEYS key : DsdArticle.DSD_REGISTRATION_STRUCTURE_KEYS.values()) { %>
        <aui:input
                label="<%= key.name() %>"
                name="<%= key.name() %>"
                type="checkbox"
                value="<%= String.valueOf(structureList.contains(key.name().toUpperCase())) %>"
                checked="<%= structureList.contains(key.name().toUpperCase()) %>"
        >
        </aui:input>
        <% } %>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
