<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.selection.SelectionFacetConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="nl.deltares.portal.utils.JsonContentUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.liferay.portal.kernel.json.JSONException" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    SelectionFacetConfiguration sel_configuration =
            (SelectionFacetConfiguration)
                    renderRequest.getAttribute(SelectionFacetConfiguration.class.getName());

    Map<String, String> titleMap = new HashMap<>();
    String structureName = "";
    String fieldName = "";
    if (Validator.isNotNull(sel_configuration)){
        String title = portletPreferences.getValue("titleMap", sel_configuration.titleMap());
        structureName = portletPreferences.getValue("structureName", sel_configuration.structureName());
        try {
            titleMap = JsonContentUtils.parseJsonToMap(title);
            Map<String, String> finalTitleMap = titleMap;
            LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()).forEach(availableLocale ->
                    finalTitleMap.putIfAbsent(availableLocale.toString(), "Title " + availableLocale.getDisplayLanguage()));

        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        fieldName = portletPreferences.getValue("fieldName", sel_configuration.fieldName());
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

        <% for (String key : titleMap.keySet()) { %>
        <aui:input
                label='<%="Title of facet (" + ( key ) + ")"%>'
                name='<%="title_" + ( key )%>'
                value='<%= titleMap.get(key) %>'
        >
        </aui:input>
        <% } %>
        <aui:input
                label="Name of structure containing selection list"
                name="structureName"
                value='<%= structureName %>'
        >
        </aui:input>
        <aui:input
                label="Name of field containing selection list"
                name="fieldName"
                value='<%= fieldName %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"/>
    </aui:button-row>
</aui:form>
