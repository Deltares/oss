<%@ page import="com.liferay.portal.kernel.json.JSONObject" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collections" %>
<%@ include file="init.jsp" %>
<%
    final JSONObject record = (JSONObject) request.getAttribute("jsonCsv");
    Map<String, String> values = (Map<String, String>) record.get("values");
    if (values == null) values = Collections.emptyMap();

    final String filterColumn = (String) request.getAttribute("filterColumn");
    final String filterValue = (String) request.getAttribute("filterValue");
%>
<aui:fieldset label="table.record.details">

    <portlet:renderURL var="viewURL">
        <portlet:param name="mvcPath" value="/view.jsp"/>
        <portlet:param name="filterColumn" value="<%=filterColumn%>"/>
        <portlet:param name="filterValue" value="<%=filterValue%>"/>
    </portlet:renderURL>

    <aui:form action="<%=viewURL%>" >
        <aui:fieldset>

            <%
                for (Map.Entry<String, String> entrySet : values.entrySet()) {
                    String key = entrySet.getKey();
                    String value = entrySet.getValue();
                    String inputTYpe = value.length() > 50 ? "textarea" : "text";
            %>

            <aui:row>
                <aui:col width="100">
                    <aui:input name="<%=key%>"
                            label="<%=key%>"
                            disabled="true"
                            value="<%=value%>"
                            type="<%=inputTYpe%>"/>
                </aui:col>
            </aui:row>

            <%
                }
            %>

            <aui:row>
                <aui:col width="20">
                    <aui:button type="submit" onClick="<%= viewURL %>" value="table.record.close" />
                </aui:col>
            </aui:row>

        </aui:fieldset>

    </aui:form>

</aui:fieldset>
