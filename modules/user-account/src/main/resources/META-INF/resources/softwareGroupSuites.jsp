<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="java.util.List" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    List<String> packageNames = softwareGroup.getPackageNames();
    for (String name : packageNames) {
%>

<aui:fieldset cssClass="c-subscription-container">
    <aui:row>

        <aui:col width="33">
            <div><strong><%=name%></strong></div>
        </aui:col>

    </aui:row>

</aui:fieldset>

<%
    }
%>