<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="nl.deltares.portal.utils.LayoutUtils" %>
<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	final Integer count = (Integer) request.getAttribute("total");
%>
<aui:input name="runningProcess" type="hidden" />
<span id="<portlet:namespace/>group-message-block"></span>
<aui:form>

	<jsp:useBean id="records" class="java.util.List<nl.deltares.oss.download.model.Download>" scope="request"/>

	<liferay-ui:search-container delta="50" emptyResultsMessage='<%=LanguageUtil.get(locale, "no-download-records")%>' total="<%=count%>">
		<liferay-ui:search-container-results results="<%= records %>" />

		<liferay-ui:search-container-row
				className="nl.deltares.oss.download.model.Download"
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
			<liferay-ui:search-container-column-text property="downloadId" name="Download ID" />
			<liferay-ui:search-container-column-date property="modifiedDate" name="Last download date"/>
			<liferay-ui:search-container-column-user property="userId" name="User"/>
			<liferay-ui:search-container-column-text property="organization" name="Organization"/>
			<liferay-ui:search-container-column-text property="city" name="City"/>
			<liferay-ui:search-container-column-text property="countryCode" name="Country" />
			<liferay-ui:search-container-column-text property="shareId" name="Share ID"/>
			<liferay-ui:search-container-column-text property="filePath" name="File path"/>
			<liferay-ui:search-container-column-text property="directDownloadUrl" name="Direct download" />

		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<aui:button-row>
		<aui:button name="exportResultsButton"  type="submit" value="Export" />
		<aui:button name="deleteSelectedButton"  type="submit" value="Delete selected" />
	</aui:button-row>
</aui:form>
<hr>
<aui:row>
	<aui:col width="100">
		<div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
	</aui:col>
</aui:row>
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
</aui:script>


