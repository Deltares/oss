<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    final Integer count = (Integer) request.getAttribute("total");
%>
<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="table.downloads.count.title">

    <aui:form >
        <jsp:useBean id="records" class="java.util.List" scope="request"/>

        <liferay-ui:search-container id="tableResults" delta="50" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-count-records")%>'
                                     total="<%=count%>">
            <liferay-ui:search-container-results results="<%= records %>" />

            <liferay-ui:search-container-row
                    className="nl.deltares.tableview.model.DisplayDownloadCount"
                    modelVar="entry"
            >
                <liferay-ui:search-container-column-text property="downloadId" name="Download ID"/>
                <liferay-ui:search-container-column-text property="fileName" name="File"/>
                <liferay-ui:search-container-column-text property="siteName" name="Site"/>
                <liferay-ui:search-container-column-text property="count" name="Count"/>

            </liferay-ui:search-container-row>
            <liferay-ui:search-iterator/>
        </liferay-ui:search-container>
    </aui:form>
</aui:fieldset>
