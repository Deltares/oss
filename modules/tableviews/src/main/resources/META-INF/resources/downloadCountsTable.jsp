<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="java.util.Map" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
    final String filterId = (String) request.getAttribute("filterId");
    final Map<String, String> topics = (Map<String, String>)  request.getAttribute("topics");
    final boolean isAdmin = permissionChecker.isGroupAdmin(themeDisplay.getScopeGroupId());
%>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:input name="runningProcess" type="hidden"/>
<aui:fieldset label="table.downloads.count.title">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/downloadCountsTable.jsp" />
    </portlet:renderURL>

    <portlet:actionURL name="filter" var="filterTableURL" />
    <portlet:actionURL name="deleteSelection" var="deleteSelectionURL" />

    <liferay-ui:error key="filter-failed">
        <liferay-ui:message key="filter-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "filter-failed") %>'/>
    </liferay-ui:error>
    <aui:form action="<%=filterTableURL%>" name="<portlet:namespace />filterForm" >
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.filter.label"/></div>
                </aui:col>
                <aui:col width="20">
                    <aui:select name="filterSelection" label="" value="<%=filterId%>">
                        <aui:option value="none" label="table.filter.option.none" />
                        <%
                            for (Map.Entry<String, String> entry : topics.entrySet()) {
                        %>
                        <aui:option value="<%=entry.getKey()%>" label="<%=entry.getValue()%>"/>
                        <%
                            }
                        %>

                    </aui:select>
                </aui:col>
                <aui:col width="50"/>
                <aui:col width="20">
                    <aui:button type="submit" value="table.filter.button" />
                    <aui:button type="submit" onClick="<%= viewURL %>" value="table.filter.clear"/>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <aui:form >
        <jsp:useBean id="records" class="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" delta="50" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-count-records")%>'
                                     total="<%=count%>">
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayDownloadCount"
                    modelVar="entry"
            >
                <% if (isAdmin) { %>
                <liferay-ui:search-container-column-text >
                    <aui:input
                            name="download_${entry.getId()}"
                            label=""
                            recordId="${entry.getId()}"
                            type="checkbox"
                            cssClass="downloadCountsTableRecord"
                            checked="false" />
                </liferay-ui:search-container-column-text>
                <% } %>
                <liferay-ui:search-container-column-text property="fileTopic" name="Software package" orderable="true" orderableProperty="fileTopic"/>
                <liferay-ui:search-container-column-text property="fileName" name="File" orderable="true" orderableProperty="fileName"/>
                <liferay-ui:search-container-column-text property="count" name="Count" orderable="true" orderableProperty="count"/>

            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
        <aui:input name="selectedFilterId" type="hidden" value="<%=filterId%>"/>

        <aui:button-row>
            <% if(isAdmin) { %>
            <aui:button name="deleteSelectedButton" type="submit" value="Delete filter selection"/>
            <% } %>
        </aui:button-row>
    </aui:form>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
        </aui:col>
    </aui:row>
</aui:fieldset>
<aui:script use="event, io, aui-io-request, node, aui-base, aui-progressbar">

    let deleteSelectedButton = document.getElementById('<portlet:namespace/>deleteSelectedButton');
    if (deleteSelectedButton) {
        deleteSelectedButton.onclick = function(event){
        event.preventDefault();
        CountsTableFormsUtil.deleteSelected("<portlet:resourceURL/>", "<liferay-portlet:renderURL/>", "<portlet:namespace/>", "delete-selected-download-counts.csv")
        };
    }

</aui:script>
