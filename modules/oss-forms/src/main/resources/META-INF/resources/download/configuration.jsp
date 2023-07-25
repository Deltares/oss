<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="nl.deltares.forms.portlet.DownloadFormConfiguration" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>
<%

    DownloadFormConfiguration configuration =
            (DownloadFormConfiguration)
                    renderRequest.getAttribute(DownloadFormConfiguration.class.getName());

    String successURL = null;
    String failURL = null;
    if (Validator.isNotNull(configuration)){
        successURL = portletPreferences.getValue("successURL", configuration.successURL());
        failURL = portletPreferences.getValue("failURL", configuration.failureURL());
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
    <div class="lfr-form-content">
        <div class="sheet sheet-lg">
            <div aria-multiselectable="true" class>
        <aui:fieldset id="site_config" collapsible="true" label="Site config">
            <aui:input
                    label="downloadform.successpage"
                    name="successURL"
                    value="<%= successURL %>"/>

            <aui:input
                    label="downloadform.failpage"
                    name="failURL"
                    value="<%= failURL %>"/>
        </aui:fieldset>
            </div>
        </div>
    </div>
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>