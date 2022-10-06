<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.checkbox.CheckboxFacetConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.json.JSONException" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    CheckboxFacetConfiguration configuration =
            (CheckboxFacetConfiguration)
                    renderRequest.getAttribute(CheckboxFacetConfiguration.class.getName());

    String visible = null;
    String defaultValue = null;
    String explicitSearch = null;
    Map<String, String> titleMap = new HashMap<>();
    String structureName = "";
    String fieldName = "";
    if (Validator.isNotNull(configuration)){
        visible = portletPreferences.getValue("visible", configuration.visible());
        defaultValue = portletPreferences.getValue("defaultValue", configuration.defaultValue());
        explicitSearch = portletPreferences.getValue("explicitSearch", configuration.explicitSearch());
        String title = portletPreferences.getValue("titleMap", configuration.titleMap());
        try {
            titleMap = JsonContentUtils.parseJsonToMap(title);
            Map<String, String> finalTitleMap = titleMap;
            LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()).forEach(availableLocale ->
                    finalTitleMap.putIfAbsent(availableLocale.toString(), "Title " + availableLocale.getDisplayLanguage()));
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        structureName = portletPreferences.getValue("structureName", configuration.structureName());
        fieldName = portletPreferences.getValue("fieldName", configuration.fieldName());
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
                name="visible"
                label="Show portlet"
                type="checkbox"
                value='<%= visible %>'
        >
        </aui:input>

        <aui:input
                name="defaultValue"
                label="Default value"
                type="checkbox"
                value='<%= defaultValue %>'
        >
        </aui:input>

        <aui:input
                name="explicitSearch"
                label="Explicit Search (If selected only returns results containing search field, else also returns results that do not contain field)"
                type="checkbox"
                value='<%= explicitSearch %>'
        >
        </aui:input>

        <% for (String key : titleMap.keySet()) { %>
        <aui:input
                label='<%="Title of facet (" + ( key ) + ")"%>'
                name='<%="title_" + ( key )%>'
                value='<%= titleMap.get(key) %>'
        >
        </aui:input>
        <% } %>
        <aui:input
                label="Name of structure containing checkbox"
                name="structureName"
                value='<%= structureName %>'
        >
        </aui:input>
        <aui:input
                label="Name of field containing checkbox"
                name="fieldName"
                value='<%= fieldName %>'
        >
        </aui:input>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>
