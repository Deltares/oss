<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ include file="/META-INF/resources/init.jsp" %>


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
                name="startDate"
                label="Start search period (dd-mm-yyyy)"
                value="<%= startDateConfig %>">
        </aui:input>
        <aui:input
                name="endDate"
                label="End search period (dd-mm-yyyy)"
                value="<%= endDateConfig %>">
        </aui:input>
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit"></aui:button>
    </aui:button-row>
</aui:form>
