<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="nl.deltares.search.facet.terms.TermsFacetConfiguration" %>
<%@ include file="/META-INF/resources/init.jsp" %>

<%

    TermsFacetConfiguration reg_configuration =
            (TermsFacetConfiguration)
                    renderRequest.getAttribute(TermsFacetConfiguration.class.getName());
    String multipleTermValues = "";
    String termValue = "";
    String termFieldName = "";
    String useWildcard = "false";
    String isDdmField = "";

    if (Validator.isNotNull(reg_configuration)){
        multipleTermValues = portletPreferences.getValue("multipleTermValues", reg_configuration.multipleTermValues());
        termValue = portletPreferences.getValue("termValue", reg_configuration.termValue());
        termFieldName = portletPreferences.getValue("termFieldName", reg_configuration.termFieldName());
        useWildcard = portletPreferences.getValue("useWildcard", reg_configuration.useWildcard());
        isDdmField = portletPreferences.getValue("isDdmField", reg_configuration.isDdmField());
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
                name="isDdmField"
                label="Is this a Ddm Field"
                type="toggle-switch"
                value='<%= isDdmField %>'
        >
        </aui:input>

        <aui:input
                label="Term field contains multiple values (space separated)"
                name="multipleTermValues"
                helpMessage="If the terms field contains multiple values then a TermsFilter is applied."
                value='<%= multipleTermValues %>'
                type="toggle-switch"
        >
        </aui:input>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
