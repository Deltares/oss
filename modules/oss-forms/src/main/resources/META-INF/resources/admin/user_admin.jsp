<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors" %>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/portlet_3_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<portlet:actionURL name="update" var="updateURL" />

<liferay-ui:success key="update-success" message="">
    <liferay-ui:message key="update-success"
                        arguments='<%= SessionMessages.get(liferayPortletRequest, "update-success") %>'/>
</liferay-ui:success>

<liferay-ui:error key="update-failed">
    <liferay-ui:message key="update-failed"
                        arguments='<%= SessionErrors.get(liferayPortletRequest, "update-failed") %>'/>
</liferay-ui:error>

<span id="<portlet:namespace/>group-message-block"></span>
<aui:fieldset label="oss.admin.adminPageTitle">
    <aui:input name="runningProcess" type="hidden" />
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
    <aui:form name="checkUsersExist" enctype="multipart/form-data" >
        <aui:fieldset >
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.checkUsersExistTitle"/></div>
                </aui:col>
                <aui:col width="45">
                    <aui:row>
                        <div class="control-label"><liferay-ui:message key="oss.admin.checkUsersFile"/></div>
                    </aui:row>
                    <aui:row >
                        <aui:input label="" name="checkUserFile" type="file"  />
                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" name="checkUsersButton" value="oss.admin.checkUsers" />
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
                        <aui:input label="" name="deleteUserFile" type="file"  />
                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" name="deleteUsersButton" value="oss.admin.delete" />
                </aui:col>
            </aui:row>
        </aui:fieldset>
    </aui:form>
    <hr>
    <aui:form action="<%=updateURL%>" name="<portlet:namespace />ChangeUserEmail"  >
        <aui:fieldset >
            <aui:input name="action" type="hidden" value="changeUserEmail" />
            <aui:row>
                <aui:col width="50">
                    <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.changeUserEmailTitle"/></div>
                </aui:col>
                <aui:col width="45">
                    <aui:row>
                        <div class="control-label"><liferay-ui:message key="oss.admin.currentUserEmail"/></div>
                    </aui:row>
                    <aui:row style="display:block">
                        <aui:input label="" name="currentUserEmail" type="email" >
                            <aui:validator name="email" errorMessage="oss.admin.invalidEmail"/>
                        </aui:input>
                    </aui:row>
                    <aui:row>
                        <div class="control-label"><liferay-ui:message key="oss.admin.newUserEmail"/></div>
                    </aui:row>
                    <aui:row style="display:block">
                        <aui:input label="" name="newUserEmail" type="email"  >
                            <aui:validator name="email" errorMessage="oss.admin.invalidEmail"/>
                        </aui:input>
                    </aui:row>
                </aui:col>
                <aui:col width="5">
                    <aui:button type="submit" value="oss.admin.update" />
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
    let checkUsersButton = document.getElementById('<portlet:namespace/>checkUsersButton');
    checkUsersButton.onclick = function(event){
    event.preventDefault();
    OssFormsUtil.checkUsersExist("<portlet:resourceURL/>", "<portlet:namespace/>")
    };
    let deleteUsersButton = document.getElementById('<portlet:namespace/>deleteUsersButton');
    deleteUsersButton.onclick = function(event){
        event.preventDefault();
        OssFormsUtil.deleteUsers("<portlet:resourceURL/>", "<portlet:namespace/>")
    };

</aui:script>
