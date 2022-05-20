<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
    final String filterId = (String) request.getAttribute("filterId");

%>
<aui:input name="runningProcess" type="hidden"/>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.portlet.title">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/downloadsTable.jsp" />
    </portlet:renderURL>

    <portlet:actionURL name="filter" var="filterTableURL" />

    <portlet:actionURL name="updateShares" var="updateSharesURL" />

    <liferay-ui:error key="action-failed">
        <liferay-ui:message key="action-failed"
                            arguments='<%= SessionErrors.get(liferayPortletRequest, "action-failed") %>'/>
    </liferay-ui:error>
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
                        <aui:option value="pendingpayment" label="table.filter.option.payment"/>
                        <aui:option value="processing" label="table.filter.option.processing"/>
                        <aui:option value="direct" label="table.filter.option.direct"/>
                    </aui:select>
                </aui:col>
                <aui:col width="50"/>
                <aui:col width="20">
                    <aui:button type="submit" value="table.filter.button" />
                    <aui:button type="cancel" onClick="<%= viewURL %>" value="table.filter.clear"/>
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <aui:form action="<%=updateSharesURL%>" name="<portlet:namespace />updateForm" >
        <aui:fieldset>
            <aui:row>
                <aui:col width="20">
                    <div class="control-label" title="<liferay-ui:message key="table.update.help"/>"><liferay-ui:message key="table.update.label"/></div>
                </aui:col>
                <aui:col width="70"/>
                <aui:col width="20">
                    <aui:button type="submit" value="table.update.button" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form >
        <jsp:useBean id="records" class="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" delta="50" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-records")%>'
                                     total="<%=count%>">
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayDownload"
                    modelVar="entry"
            >
                <liferay-ui:search-container-column-text>
                    <aui:input
                            name="download_${entry.getId()}"
                            label=""
                            recordId="${entry.getId()}"
                            type="checkbox"
                            cssClass="downloadTableRecord"
                            checked="false"/>
                </liferay-ui:search-container-column-text>
                <liferay-ui:search-container-column-text property="downloadId" name="Download ID"/>
                <liferay-ui:search-container-column-date property="modifiedDate" name="Last download date"/>
                <liferay-ui:search-container-column-text property="email" name="User"/>
                <liferay-ui:search-container-column-text property="organization" name="Organization"/>
                <liferay-ui:search-container-column-text property="city" name="City"/>
                <liferay-ui:search-container-column-text property="countryCode" name="Country"/>
                <liferay-ui:search-container-column-text property="shareId" name="Share ID"/>
                <liferay-ui:search-container-column-text property="filePath" name="File path"/>
                <liferay-ui:search-container-column-text property="directDownloadUrl" name="Direct download"/>

            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
        <aui:input name="selectedFilterId" type="hidden" value="<%=filterId%>"/>
        <aui:button-row>
            <aui:button name="exportResultsButton" type="submit" value="Export"/>
            <aui:button name="deleteSelectedButton" type="submit" value="Delete selected"/>
            <aui:button name="paidSelectedButton" type="submit" value="Set to paid"/>
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

    let paidSelectedButton = document.getElementById('<portlet:namespace/>paidSelectedButton');
    paidSelectedButton.onclick = function(event){
    event.preventDefault();
    TableFormsUtil.paidSelected("<portlet:resourceURL/>", "<liferay-portlet:renderURL/>", "<portlet:namespace/>", "paid-selected-downloads.csv")
    };

</aui:script>


