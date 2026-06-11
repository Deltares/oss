<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.range.RangeFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    RangeFacetConfiguration reg_configuration =
            (RangeFacetConfiguration)
                    renderRequest.getAttribute(RangeFacetConfiguration.class.getName());

    String companyIds = "";
    String groupIds = "";
    String termFieldName = "";
    String upperValue = "";
    String lowerValue = "";

    if (Validator.isNotNull(reg_configuration)){
        companyIds = portletPreferences.getValue("companyIds", reg_configuration.companyIds());
        groupIds = portletPreferences.getValue("groupIds", reg_configuration.groupIds());
        termFieldName = portletPreferences.getValue("termFieldName", reg_configuration.termFieldName());
        upperValue = portletPreferences.getValue("upperValue", reg_configuration.upperValue());
        lowerValue = portletPreferences.getValue("lowerValue", reg_configuration.lowerValue());

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
                label="Lower value"
                name="lowerValue"
                helpMessage="Lower value of range. If ommitted then no lower range applied. Dates are expected in format yyyy-MM-dd"
                value='<%= lowerValue %>'
        >
        </aui:input>
        <aui:input
                label="Upper value"
                name="upperValue"
                helpMessage="Upper value of range. If ommitted then no upper range applied. Dates are expected in format yyyy-MM-dd"
                value='<%= upperValue %>'
        >
        </aui:input>        <aui:input
                label="Group IDs (space separated)"
                name="groupIds"
                helpMessage="Group Ids of all sites to search, separated by space. If specified, articles will only be retrieved from given sites."
                value='<%= groupIds %>'
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
