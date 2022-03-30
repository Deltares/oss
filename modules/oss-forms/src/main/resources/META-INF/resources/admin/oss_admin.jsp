<%@ page import="com.liferay.portal.kernel.module.configuration.ConfigurationProvider" %>
<%@ page import="nl.deltares.portal.configuration.OSSSiteConfiguration" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    ConfigurationProvider configurationProvider =
            (ConfigurationProvider) request.getAttribute(ConfigurationProvider.class.getName());

    OSSSiteConfiguration configuration = configurationProvider.getGroupConfiguration(OSSSiteConfiguration.class, themeDisplay.getScopeGroupId());

    boolean enableSiteId = configuration.enableSiteId();

%>

<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="oss.admin.adminPageTitle">
    <aui:input name="runningProcess" type="hidden" />
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.siteConfigTitle"/></div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label"><liferay-ui:message key="oss.admin.siteConfigText"/></div>
        </aui:col>
    </aui:row>
    <hr>
    <aui:form name="deleteBannedUsers" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.deleteBannedTitle"/></div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="oss.admin.siteId"/></div>
                </aui:col>
                <aui:col width="25">
                    <c:choose>
                        <c:when test="<%=enableSiteId%>">
                            <aui:input name="siteId" label="" value="<%=themeDisplay.getSiteGroupId()%>" />
                        </c:when>
                        <c:otherwise>
                            <aui:input name="siteId" label="" value="<%=themeDisplay.getSiteGroupId()%>" disabled="true"/>
                        </c:otherwise>
                    </c:choose>
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" name="deleteBannedUsersButton" value="oss.admin.delete" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form name="downloadDisabled" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.downloadDisabledTitle"/></div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="oss.admin.disabledTime"/></div>
                </aui:col>
                <aui:col width="25">
                    <aui:input name="disabledTime" value="" type="date"  label=""/>
                </aui:col>
                <aui:col width="5">
                    <aui:button  name="downloadDisabledButton"  type="submit" value="oss.admin.download" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form name="deleteUsers" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.deleteUsersTitle"/></div>
                </aui:col>
                <aui:col width="20">
                    <div class="control-label"><liferay-ui:message key="oss.admin.usersFile"/></div>
                </aui:col>
                <aui:col width="25">
                    <aui:input label="" name="userFile" type="file"  />
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" name="deleteUsersButton" value="oss.admin.delete" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="<portlet:namespace/>progressBar" style="height:10px;display:none; "></div>
        </aui:col>
    </aui:row>
</aui:fieldset>

<aui:script use="event, io, aui-io-request, node, aui-base, aui-progressbar">

    let deleteBannedUsersButton = document.getElementById('<portlet:namespace/>deleteBannedUsersButton');
    deleteBannedUsersButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.deleteBannedUsers("<portlet:resourceURL/>", "<portlet:namespace/>")
    };
    let downloadDisabledButton = document.getElementById('<portlet:namespace/>downloadDisabledButton');
    downloadDisabledButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.downloadDisabled("<portlet:resourceURL/>", "<portlet:namespace/>")
    };
    let deleteUsersButton = document.getElementById('<portlet:namespace/>deleteUsersButton');
    deleteUsersButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.deleteUsers("<portlet:resourceURL/>", "<portlet:namespace/>")
    };
    let year_millis = 86400000 * 365;
    let disabledTime = document.getElementById('<portlet:namespace/>disabledTime');
    disabledTime.innerHTML = new Date(Date.now() - year_millis).toDateString();

</aui:script>
