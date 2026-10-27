<%@ include file="init.jsp" %>

<portlet:resourceURL id="loadCsv" var="loadCsvURL" />

<%
    String jsonCsv = (String) renderRequest.getAttribute("jsonCsv");
%>

<div class="csv-viewer-container"
     data-portlet-namespace="<portlet:namespace />"
     data-json="<%=jsonCsv%>"
     data-filterable-columns="<%=filterableColumns%>"
     data-strict-validation="<%=strictValidation%>">

    <div class="csv-viewer-toolbar">
        <input type="text"
               id="<portlet:namespace />search"
               class="form-control"
               placeholder="Search all columns" />
    </div>

    <div class="csv-viewer-status" id="<portlet:namespace />summary"></div>
    <div class="csv-viewer-filters" id="<portlet:namespace />filter-panel"></div>

    <div class="csv-viewer-table-wrap">
        <table id="<portlet:namespace />csv-table" class="table table-striped table-bordered">
            <thead></thead>
            <tbody></tbody>
        </table>
    </div>

    <div id="<portlet:namespace />errors" class="alert alert-danger hidden"></div>
</div>
