<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.forms.portlet.DsdRegistrationFormConfiguration" %>
<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    DsdRegistrationFormConfiguration configuration = configurationProvider.getGroupConfiguration(DsdRegistrationFormConfiguration.class, themeDisplay.getScopeGroupId());

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
    <div class="lfr-form-content">
        <div class="sheet sheet-lg">
            <div aria-multiselectable="true" class>
        <aui:fieldset id="site_config" collapsible="true" label="Site config">
            <aui:input
                    label="registrationform.successpage"
                    name="registerSuccessURL"
                    value="<%= configuration.registerSuccessURL() %>"/>

            <aui:input
                    label="registrationform.unregistersuccesspage"
                    name="unregisterSuccessURL"
                    value="<%= configuration.unregisterSuccessURL() %>"/>

            <aui:input
                    label="registrationform.updatesuccesspage"
                    name="updateSuccessURL"
                    value="<%= configuration.updateSuccessURL() %>"/>

            <aui:input
                    label="registrationform.failpage"
                    name="failURL"
                    value="<%= configuration.failURL() %>"/>

            <aui:input
                    label="registrationform.childheader-text"
                    name="childHeaderText"
                    value="<%= configuration.childHeaderText() %>"/>
        </aui:fieldset>
            </div>
        </div>
    </div>
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>