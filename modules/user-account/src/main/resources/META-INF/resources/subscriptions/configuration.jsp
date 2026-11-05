<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="liferay-theme" uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.useraccount.portlet.UserSubscriptionsConfiguration" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%

    UserSubscriptionsConfiguration configuration =
            (UserSubscriptionsConfiguration)
                    renderRequest.getAttribute(UserSubscriptionsConfiguration.class.getName());

    String showConsentGroups = null;
    if (Validator.isNotNull(configuration)){
        showConsentGroups = portletPreferences.getValue("showConsentGroups", configuration.showConsentGroups());
    }
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
                name="showConsentGroups"
                label="Show Consent Groups"
                helpMessage="Switch to toggle between showing List items and Consent Groups"
                type="toggle-switch"
                value='<%= showConsentGroups %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>
