<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.portal.configuration.OSSSiteConfiguration" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    OSSSiteConfiguration configuration = configurationProvider.getGroupConfiguration(OSSSiteConfiguration.class, themeDisplay.getScopeGroupId());

%>
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
                label="oss.siteId.field.enabled"
                name="enableSiteId"
                type="checkbox"
                value="<%= configuration.enableSiteId() %>"/>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>