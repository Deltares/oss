<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/userlicenses.jsp"/>
</portlet:renderURL>

<aui:row>
    <aui:col width="100">
        <span><liferay-ui:message key="userprofileform.licenseInfo"/></span>
        <aui:form>
            <jsp:useBean id="records" class="java.util.List" scope="request"/>

            <liferay-ui:search-container id="tableResults"
                                         emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-records")%>'>
                <liferay-ui:search-container-results results="<%= records %>"/>

                <liferay-ui:search-container-row
                        className="nl.deltares.useraccount.model.DisplayDownload"
                        modelVar="entry"
                        keyProperty="id"
                >
                    <liferay-ui:search-container-column-text property="fileName" name="File name" orderable="<%= true %>" />
                    <liferay-ui:search-container-column-date property="modifiedDate" name="Last download date"/>
                    <liferay-ui:search-container-column-date property="expirationDate" name="Expiration date"/>
                    <liferay-ui:search-container-column-text property="licenseDownloadUrlHTML" name="License download"/>


                </liferay-ui:search-container-row>
                <liferay-ui:search-iterator/>
            </liferay-ui:search-container>

        </aui:form>

    </aui:col>
</aui:row>