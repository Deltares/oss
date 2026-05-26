<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.terms.TermsFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    TermsFacetConfiguration reg_configuration =
            (TermsFacetConfiguration)
                    renderRequest.getAttribute(TermsFacetConfiguration.class.getName());

    String companyId = "";
    if (Validator.isNotNull(reg_configuration)){
        companyId = portletPreferences.getValue("companyId", reg_configuration.companyId());
    }

    String groupIds = "";
    if (Validator.isNotNull(reg_configuration)){
        groupIds = portletPreferences.getValue("groupIds", reg_configuration.groupIds());
    }

    String articleIds = "";
    if (Validator.isNotNull(reg_configuration)){
        articleIds = portletPreferences.getValue("articleIds", reg_configuration.articleIds());
    }

    String ddmStructureKey = "";
    if (Validator.isNotNull(reg_configuration)){
        ddmStructureKey = portletPreferences.getValue("ddmStructureKey", reg_configuration.ddmStructureKey());
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
                label="Company ID"
                name="companyId"
                value='<%= companyId %>'
        >
        </aui:input>

        <aui:input
                label="DDM Structure Key"
                name="ddmStructureKey"
                value='<%= ddmStructureKey %>'
        >
        </aui:input>

        <aui:input
                label="Group IDs (space separated)"
                name="groupIds"
                value='<%= groupIds %>'
        >
        </aui:input>

        <aui:input
                label="Article IDs (space separated)"
                name="articleIds"
                value='<%= articleIds %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
