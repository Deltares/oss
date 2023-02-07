<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

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
                <aui:col width="45">
                    <div class="control-label"><liferay-ui:message key="oss.admin.deleteBannedInfo"/></div>
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" name="deleteBannedUsersButton" value="oss.admin.delete" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form name="downloadInvalid" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.downloadDisabledTitle"/></div>
                </aui:col>
                <aui:col width="45">
                    <div class="control-label"><liferay-ui:message key="oss.admin.downloadDisabledInfo"/></div>
                </aui:col>
                <aui:col width="5">
                    <aui:button  name="downloadInvalidButton"  type="submit" value="oss.admin.download" />
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
                <aui:col width="45">
                    <aui:row>
                        <div class="control-label"><liferay-ui:message key="oss.admin.usersFile"/></div>
                    </aui:row>
                    <aui:row >
                        <aui:input label="" name="userFile" type="file"  />
                    </aui:row>
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
    let downloadInvalidButton = document.getElementById('<portlet:namespace/>downloadInvalidButton');
    downloadInvalidButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.downloadInvalid("<portlet:resourceURL/>", "<portlet:namespace/>")
    };
    let deleteUsersButton = document.getElementById('<portlet:namespace/>deleteUsersButton');
    deleteUsersButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.deleteUsers("<portlet:resourceURL/>", "<portlet:namespace/>")
    };

</aui:script>
