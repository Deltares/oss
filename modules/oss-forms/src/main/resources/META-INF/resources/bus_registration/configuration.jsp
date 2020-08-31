<%@ include file="init.jsp" %>

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

    <div class="sheet sheet-lg">
        <aui:fieldset>

            <aui:input
                    label="Event ID"
                    name="eventId"
                    value="<%= busTransferFormConfiguration.eventId() %>"
            >

            </aui:input>

        </aui:fieldset>

        <aui:button-row>
            <aui:button type="submit"></aui:button>
        </aui:button-row>
    </div>

</aui:form>