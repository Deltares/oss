<%@ include file="/init.jsp" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%--
  Created by IntelliJ IDEA.
  User: rooij_e
  Date: 22-3-2020
  Time: 07:30
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ include file="/init.jsp" %>--%>
<liferay-portlet:actionURL portletConfiguration="<%=true%>"
                           var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%=true%>"
                           var="configurationRenderURL" />

<aui:form action="<%=configurationActionURL%>" method="post" name="fm">
    <aui:input name="<%=Constants.CMD%>" type="hidden"
               value="<%=Constants.UPDATE%>" />

    <aui:input name="redirect" type="hidden"
               value="<%=configurationRenderURL%>" />

    <aui:fieldset>
        <aui:input type="text" name="eventId" label="Event ID" value="<%=eventId%>"/>

        <aui:input type="text" name="baseUrl" label="Path to rest event services URL" value="<%=baseUrl%>" />

<%--        <aui:input type="text" name="authUser" label="Authentication User name" />--%>

<%--        <aui:input type="password" name="authPassword" label="Authentication Password" />--%>

    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>
