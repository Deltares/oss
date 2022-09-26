<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="nl.deltares.portal.configuration.WebinarSiteConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider"  %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.portal.configuration.WebinarSiteConfiguration" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());
    WebinarSiteConfiguration configuration = configurationProvider.getGroupConfiguration(WebinarSiteConfiguration.class, themeDisplay.getScopeGroupId());

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
                label="GOTO base URL"
                name="gotoURL"
                value="<%= configuration.gotoURL() %>"/>

        <aui:input
                label="GOTO client ID"
                name="gotoClientId"
                value="<%= configuration.gotoClientId() %>"/>

        <aui:input
                label="GOTO client Secret"
                name="gotoClientSecret"
                value="<%= configuration.gotoClientSecret() %>"/>

        <aui:input
                label="GOTO user name"
                name="gotoUserName"
                value="<%= configuration.gotoUserName() %>"/>


        <aui:input
                label="GOTO user password"
                name="gotoUserPassword"
                type="password"
                value="<%= configuration.gotoUserPassword() %>"/>

        <aui:input
                label="Cache GOTO tokens?"
                name="gotoCacheToken"
                type="checkbox"
                value="<%= configuration.gotoCacheToken() %>"/>

        <aui:input
                label="Cache GOTO responses?"
                name="gotoCacheResponse"
                type="checkbox"
                value="<%= configuration.gotoCacheResponse() %>"/>

        <hr>

        <aui:input
                label="aNewsSpring base URL"
                name="aNewSpringURL"
                value="<%= configuration.aNewSpringURL() %>"/>

        <aui:input
                label="aNewSpring API key"
                name="aNewSpringApiKey"
                value="<%= configuration.aNewSpringApiKey() %>"/>

        <aui:input
                label="Cache aNewSpring tokens?"
                name="aNewSpringCacheToken"
                type="checkbox"
                value="<%= configuration.aNewSpringCacheToken() %>"/>


    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>