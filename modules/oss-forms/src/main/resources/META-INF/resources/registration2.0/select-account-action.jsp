<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="clay" uri="http://liferay.com/tld/clay" %>
<%@ page import="nl.deltares.forms.util.FilterAccountSelectionCheckoutStep" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="java.util.Map" %>
<%@ include file="init.jsp" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
    String rowId = row.getRowId();
    Long accountEntryId = (Long)row.getParameter("accountEntryId");
%>
<aui:form action="${saveStepURL}" name='<%="selectOrganizationForm" + accountEntryId%>'
          method="post" >
    <aui:input name="rowId" type="hidden" value="<%=rowId%>"/>
    <aui:input name="accountEntryId" type="hidden" value="<%=accountEntryId%>"/>
    <aui:input name="checkoutStepName" type="hidden" value="<%= FilterAccountSelectionCheckoutStep.NAME %>"/>
    <aui:input name="ids" type="hidden" value="<%= ids %>"/>
    <aui:input name="redirect" type="hidden" value="<%= currentURL %>"/>
    <aui:button name="selectOrganization" type="submit" value="registrationform.select"
                cssClass="btn btn-secondary"/>
</aui:form>
