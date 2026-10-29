<%@ page import="com.liferay.portal.kernel.json.JSONObject" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="nl.deltares.csvviewer.service.CsvDataService" %>
<%@ include file="init.jsp" %>

<%
    JSONObject jsonCsv = (JSONObject) renderRequest.getAttribute("jsonCsv");

    int totalCount = jsonCsv != null ? jsonCsv.getInt("totalRows") : 0;
    List<String> columnNames = jsonCsv != null ? (List<String>) jsonCsv.get("columns") : Collections.emptyList();
    List<JSONObject> rows = jsonCsv != null ? (List<JSONObject>) jsonCsv.get("rows") : Collections.emptyList();

    final String filterColumn = (String) request.getAttribute("filterColumn");
    final String filterValue = (String) request.getAttribute("filterValue");
%>

<portlet:actionURL name="filter" var="filterURL">
    <portlet:param name="filterColumn" value="<%=filterColumn%>"/>
    <portlet:param name="filterValue" value="<%=filterValue%>"/>
</portlet:actionURL>
<portlet:renderURL var="clearFilterURL">
    <portlet:param name="mvcPath" value="/view.jsp"/>
</portlet:renderURL>

<liferay-portlet:renderURL varImpl="iteratorURL">
    <portlet:param name="filterColumn" value="<%=filterColumn%>"/>
    <portlet:param name="filterValue" value="<%=filterValue%>"/>
</liferay-portlet:renderURL>

<aui:fieldset>
    <aui:form action="<%=filterURL%>" name="filterForm" >

        <aui:row>
            <aui:col width="20">
                <div class="d-flex justify-content-start">
                    <aui:select name="filterColumn" label="column.select.label" value="<%=filterColumn%>" >

                        <%
                            for (String columnName : columnNames) {
                        %>
                        <aui:option value="<%=columnName%>" label="<%=columnName%>" />
                        <%
                            }
                        %>
                    </aui:select>
                </div>
            </aui:col>
            <aui:col width="50">
                <aui:input name="filterValue" label="filter.value.label" value="<%=filterValue%>"/>
            </aui:col>
            <aui:col width="30">
                <aui:button-row cssClass="align-center float-right">
                    <aui:button type="submit" value="table.filter.button"  />
                    <aui:button type="submit" value="table.filter.clear" href="<%=clearFilterURL%>"/>
                </aui:button-row>
            </aui:col>
        </aui:row>
    </aui:form>

</aui:fieldset>

<aui:form>

    <liferay-ui:search-container id="tableResults" iteratorURL="<%= iteratorURL %>" delta="25"
                                 emptyResultsMessage='<%=LanguageUtil.get(locale, "no-csv-records")%>'
                                 total='<%=totalCount%>'  >

        <liferay-ui:search-container-results results='<%= rows %>' />


        <liferay-ui:search-container-row
                className="com.liferay.portal.kernel.json.JSONObject"
                modelVar="entry" keyProperty=""
        >
            <%
                Map<String, String> values = (Map<String, String>) entry.get("values");
                int rowNumber = entry.getInt("rowNumber");
            %>
            <portlet:renderURL var="rowURL">
                <portlet:param name="mvcPath" value="/viewEntry.jsp" />
                <portlet:param name="rowNumber" value="<%= String.valueOf(rowNumber) %>" />
                <portlet:param name="filterColumn" value="<%=filterColumn%>"/>
                <portlet:param name="filterValue" value="<%=filterValue%>"/>
            </portlet:renderURL>
            <%
                for (String columnName : columnNames) {
                    String columnValue = values.get(columnName);
                    boolean isUrl = CsvDataService.isUrl(columnValue);
            %>
                <liferay-ui:search-container-column-text value="<%=columnValue%>" name="<%=columnName%>"
                                                         orderable="true" orderableProperty="<%=columnName%>"
                                                         href="<%=isUrl ? columnValue : null %>" target="_blank"/>
            <%
                }
            %>
        </liferay-ui:search-container-row>
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>

</aui:form>


<liferay-ui:error key="config-error">
    <liferay-ui:message key="config-error" arguments='<%= SessionErrors.get(liferayPortletRequest, "config-error") %>' />
</liferay-ui:error>

<liferay-ui:error key="csv-error">
    <liferay-ui:message key="csv-error" arguments='<%= SessionErrors.get(liferayPortletRequest, "csv-error") %>' />
</liferay-ui:error>

