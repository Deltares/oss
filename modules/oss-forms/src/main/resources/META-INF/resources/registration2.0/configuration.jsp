<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.forms.portlet.RegistrationFormConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    RegistrationFormConfiguration configuration =
            (RegistrationFormConfiguration) renderRequest.getAttribute(RegistrationFormConfiguration.class.getName());

    String selectedAssetsTemplate= "";
    String relatedAssetsTemplate="";
    boolean showBadge = true;
    boolean alwaysShowRelatedInfo = false;
    if (Validator.isNotNull(configuration)){
        showBadge = Boolean.parseBoolean(portletPreferences.getValue("showBadgeInfo", String.valueOf(configuration.showBadgeInfo())));
        alwaysShowRelatedInfo = Boolean.parseBoolean(portletPreferences.getValue("alwaysShowRelatedInfo", String.valueOf(configuration.alwaysShowRelatedInfo())));
        selectedAssetsTemplate = portletPreferences.getValue("selectedAssetsTemplate", configuration.selectedAssetsTemplate());
        relatedAssetsTemplate = portletPreferences.getValue("relatedAssetsTemplate", configuration.relatedAssetsTemplate());
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
                    label="registrationform.selectedAssetsTemplate"
                    name="selectedAssetsTemplate"
                    value='<%= selectedAssetsTemplate %>'/>

            <aui:input
                    label="registrationform.relatedAssetsTemplate"
                    name="relatedAssetsTemplate"
                    value='<%= relatedAssetsTemplate %>'/>

            <aui:input
                    label="registrationform.showBadgeInfo"
                    name="showBadgeInfo"
                    type="toggle-switch"
                    value='<%= showBadge %>'/>

            <aui:input
                    label="registrationform.alwaysShowRelatedInfo"
                    name="alwaysShowRelatedInfo"
                    type="toggle-switch"
                    value='<%= alwaysShowRelatedInfo %>'/>

        </aui:fieldset>
            </div>
        </div>
    </div>
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>