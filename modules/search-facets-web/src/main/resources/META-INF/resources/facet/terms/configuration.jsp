<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.terms.TermsFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    TermsFacetConfiguration reg_configuration =
            (TermsFacetConfiguration)
                    renderRequest.getAttribute(TermsFacetConfiguration.class.getName());

    String companyIds = "";
    String groupIds = "";
    String articleIds = "";
    String termValue = "";
    String termFieldName = "";
    String useWildcard = "false";

    if (Validator.isNotNull(reg_configuration)){
        companyIds = portletPreferences.getValue("companyIds", reg_configuration.companyIds());
        groupIds = portletPreferences.getValue("groupIds", reg_configuration.groupIds());
        articleIds = portletPreferences.getValue("articleIds", reg_configuration.articleIds());
        termValue = portletPreferences.getValue("termValue", reg_configuration.termValue());
        termFieldName = portletPreferences.getValue("termFieldName", reg_configuration.termFieldName());
        useWildcard = portletPreferences.getValue("useWildcard", reg_configuration.useWildcard());
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
                name="companyIds"
                helpMessage="Company Ids of companies from which to retrieve articles. Space separated"
                value='<%= companyIds %>'
        >
        </aui:input>

        <aui:input
                label="Term field name"
                name="termFieldName"
                helpMessage="Term field name of the articles to retrieve"
                value='<%= termFieldName %>'
        >
        </aui:input>
        <aui:input
                label="Term value"
                name="termValue"
                helpMessage="Term value of the articles to retrieve"
                value='<%= termValue %>'
        >
        </aui:input>
        <aui:input
                label="Use wildcard"
                name="useWildcard"
                helpMessage="Does the Term value contain wildcards? If true, the term value will be used as is, if false, the term value will be wrapped in asterisks to match any term containing the value."
                value='<%= useWildcard %>'
                type="toggle-switch"
        >
        </aui:input>
        <aui:input
                label="Group IDs (space separated)"
                name="groupIds"
                helpMessage="Group Ids of all sites to search, separated by space. If specified, articles will only be retrieved from given sites."
                value='<%= groupIds %>'
        >
        </aui:input>

        <aui:input
                label="Article IDs (space separated)"
                name="articleIds"
                helpMessage="Article Ids of articles to retrieve, separated by space. If specified, only these articles will be retrieved, otherwise all articles matching the companyId and ddmStructureKey will be retrieved."
                value='<%= articleIds %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
