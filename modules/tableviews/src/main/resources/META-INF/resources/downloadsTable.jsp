<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.dao.search.RowChecker" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
    final String filterValue = (String) request.getAttribute("filterValue");
%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.download.title" collapsible="true">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/downloadsTable.jsp" />
    </portlet:renderURL>

    <liferay-portlet:renderURL varImpl="iteratorURL">
        <portlet:param name="filterValue" value="<%=filterValue%>"/>
    </liferay-portlet:renderURL>

    <portlet:actionURL name="filterDownloads" var="filterDownloadURL"/>

    <liferay-ui:error key="action-failed">
        <liferay-ui:message key="action-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "action-failed") %>'/>
    </liferay-ui:error>
    <liferay-ui:error key="filter-failed">
        <liferay-ui:message key="filter-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "filter-failed") %>'/>
    </liferay-ui:error>

    <aui:form action="<%=filterDownloadURL%>" name="filterDownloadForm">
        <!-- Hidden field to indicate which action was triggered. The id is namespaced for uniqueness on the page. -->
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="table.filter.selection.label"/></div>
                </aui:col>
                <aui:col width="70">
                    <aui:input name="filterValue" label="" value="<%=filterValue%>"/>
                </aui:col>
                <aui:col width="10">
                    <aui:button-row>
                        <aui:button type="submit" value="table.filter.button" cssClass="float-right" />
                    </aui:button-row>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <aui:button-row>
        <%--    Don't pass filter values so filter fields will be emptied.--%>
        <portlet:renderURL var="clearFilterURL">
            <portlet:param name="mvcPath" value="/downloadsTable.jsp"/>
        </portlet:renderURL>
        <aui:button type="submit" value="table.filter.clear" href="<%=clearFilterURL%>"/>
    </aui:button-row>
    <hr>
    <aui:form >
        <jsp:useBean id="records" type="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-records")%>'
                                     total="<%=count%>" rowChecker="<%= new RowChecker(renderResponse) %>" >
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayDownload"
                    modelVar="entry"
                    keyProperty="id"
            >
                <liferay-ui:search-container-column-text property="fileName" name="File name" orderable="true" orderableProperty="fileName"/>
                <liferay-ui:search-container-column-text property="email" name="User"/>
                <liferay-ui:search-container-column-text property="organization" name="Organization" orderable="true" orderableProperty="organization"/>
                <liferay-ui:search-container-column-text property="city" name="City"/>
                <liferay-ui:search-container-column-text property="countryCode" name="Country"/>
                <liferay-ui:search-container-column-text property="fileShareUrl" name="Share link"/>
                <liferay-ui:search-container-column-text property="licenseDownloadUrl" name="License download"/>
                <liferay-ui:search-container-column-date property="modifiedDate" name="Last download date" orderable="true" orderableProperty="modifiedDate"/>
                <liferay-ui:search-container-column-date property="expirationDate" name="Expiration date" orderable="true" orderableProperty="expiryDate"/>

            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>

        <aui:button-row>
            <aui:button name="exportResultsButton" type="submit" value="Export"/>
            <aui:button name="deleteSelectedButton" type="submit" value="Delete selected"/>
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

    let exportResultsButton = document.getElementById('<portlet:namespace/>exportResultsButton');
    exportResultsButton.onclick = function(event){
        event.preventDefault();
        TableFormsUtil.exportResults("<portlet:resourceURL/>", "<portlet:namespace/>", "export-downloads.csv")
    };

    let deleteSelectedButton = document.getElementById('<portlet:namespace/>deleteSelectedButton');
    deleteSelectedButton.onclick = function(event){
        event.preventDefault();
        TableFormsUtil.deleteSelected("<portlet:resourceURL/>", "<liferay-portlet:renderURL/>", "<portlet:namespace/>", "delete-selected-downloads.csv")
    };

</aui:script>


