<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="nl.deltares.useraccount.model.SoftwareSuite" %>
<%@ page import="java.util.List" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%

    List<SoftwareSuite> suiteList = (List<SoftwareSuite>) renderRequest.getAttribute("records");

%>
<span id="<portlet:namespace/>group-message-block"></span>
<%
    for (SoftwareSuite softwareSuite : suiteList) {
%>

    <aui:fieldset>
        <a href="#softwareSuite-<%=softwareSuite.getSuiteCode()%>" aria-controls="site_configContent" aria-expanded="false" class="collapse-icon collapse-icon-middle sheet-subtitle collapsed" data-toggle="liferay-collapse"  role="button">
            <span class="c-inner" tabindex="-1">
                <span class="collapse-icon-closed">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-plus">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#plus"></use>
                    </svg>
                </span>
                <span class="collapse-icon-open">
                    <svg aria-hidden="true" class="lexicon-icon lexicon-icon-hr">
                        <use xlink:href="<%=themeDisplay.getPathThemeImages()%>/clay/icons.svg#hr"></use>
                    </svg>
                </span>
                &nbsp;
                <span class="h1"><%=softwareSuite.getSuiteName()%></span>
            </span>
        </a>

        <div class="panel-collapse collapse" id="softwareSuite-<%=softwareSuite.getSuiteCode()%>">
            <%@ include file="softwareSuiteSubscriptions.jsp" %>
        </div>

    </aui:fieldset>

<% } %>