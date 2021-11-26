<%@ include file="oss_admin_init.jsp" %>

<span id="group-message-block"></span>
<aui:fieldset label="oss.admin.adminPageTitle">
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.siteConfigTitle"/></div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label"><liferay-ui:message key="oss.admin.siteConfigText"/></div>
        </aui:col>
    </aui:row>
    <hr>
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
                    <input id="siteId" value="<%=themeDisplay.getSiteGroupId()%>" class="form-control">
                </c:when>
                <c:otherwise>
                    <input id="siteId" value="<%=themeDisplay.getSiteGroupId()%>" class="form-control" disabled>
                </c:otherwise>
            </c:choose>
        </aui:col>
        <aui:col width="5">
            <button id="deleteButton" class="btn btn-lg" type="button"><liferay-ui:message
                    key="oss.admin.delete"/></button>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" id="Title"><liferay-ui:message key="oss.admin.deleteDisabledTitle"/></div>
        </aui:col>
        <aui:col width="20">
            <div class="control-label"><liferay-ui:message key="oss.admin.disabledTime"/></div>
        </aui:col>
        <aui:col width="25">
            <input id="disabledTime" value="" type="date" class="form-control">
        </aui:col>
        <aui:col width="5">
            <button id="deleteDisabledButton" class="btn btn-lg" type="button"><liferay-ui:message
                    key="oss.admin.delete"/></button>
        </aui:col>
    </aui:row>

    <aui:row>
        <aui:col width="50">
            <div class="panel-title" id="Title"></div>
        </aui:col>
        <aui:col width="45">
            <div id="deleteProgress" style="height:10px;"></div>
        </aui:col>
    </aui:row>
</aui:fieldset>

<aui:script use="event, aui-io-request, node, aui-base, aui-progressbar">
    let year_millis = 86400000 * 365;
    let progressId = '';
    let FormsUtil = {

        writeError: function(message){
            let errorBlock = A.one('#group-message-block');
            let messageNode = A.Node.create('<div class="portlet-msg-error">' + message + '</div>');
            messageNode.appendTo(errorBlock);
        },
        writeInfo: function(message){
            let messageBlock = A.one('#group-message-block');
            let messageNode = A.Node.create('<div class="portlet-msg-info">' + message + '</div>');
            messageNode.appendTo(messageBlock);
        },
        delete: function(resourceUrl, namespace){
            this.clearMessage();
            var siteId = document.getElementById("siteId").value;
            if (confirm("You are about to delete all banned users from site: " + siteId + "\nDo you want to continue?") === false) {
                siteId = null;
                return;
            }

            if (siteId != null && siteId!=="") {
                FormsUtil.callDeleteBannedUsers(resourceUrl + '&' + namespace + 'siteId=' + siteId + '&' + namespace);
            }
        },
        deleteDisabled: function(resourceUrl, namespace){
            this.clearMessage();
            var disabledTime = document.getElementById("disabledTime").value;
            if (confirm("You are about to delete all disabled users that have been disabled after: " + disabledTime + "\nDo you want to continue?") === false) {
                disabledTime = null;
                return;
            }
            FormsUtil.callDeleteDisabledUsers(resourceUrl + '&' + namespace + 'disabledTime=' + new Date(disabledTime).getTime() + '&' + namespace);

        },
        callDeleteBannedUsers : function (resourceUrl){

            FormsUtil.updateProgressBar(JSON.parse('{"status": "pending", "progress":0, "total":100}'));
            A.io.request(resourceUrl + 'action=deleteBannedUsers', {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        if (xhr.status > 299){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            return false;
                        } else if(xhr.status === 204){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeInfo("204: No Banned users found!");
                            return true;
                        } else if (xhr.status === 200){
                            FormsUtil.startProgressMonitor(resourceUrl);
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                        FormsUtil.stopProgressMonitor()
                    }
                }
            });
        },
        callDeleteDisabledUsers : function (resourceUrl){

            FormsUtil.updateProgressBar(JSON.parse('{"status": "pending", "progress":0, "total":100}'));
            A.io.request(resourceUrl + 'action=deleteDisabledUsers', {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        if (xhr.status > 299){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            return false;
                        } else if(xhr.status === 204){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeInfo("204: No disabled users found!");
                            return true;
                        } else if (xhr.status === 200){
                            FormsUtil.startProgressMonitor(resourceUrl);
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                        FormsUtil.stopProgressMonitor()
                    }
                }
            });
        },
        clearMessage : function(){
            let errorBlock = A.one('#group-message-block');
            errorBlock.html('');
        },
        stopProgressMonitor : function (){
            clearInterval(progressId);
            progressId = '';
            var progressBar = $('#deleteProgress');
            progressBar.hide();
            progressBar.empty();
            $('#deleteButton').prop('disabled', false);
            $('#deleteDisabledButton').prop('disabled', false);
        },
        startProgressMonitor : function(resourceUrl){
            if (progressId !== '') {
                alert("Process already running!");
                return
            }
            $('#deleteProgress').show();
            $('#deleteButton').prop('disabled', true);
            $('#deleteDisabledButton').prop('disabled', true);
            progressId = setInterval(function(){FormsUtil.callUpdateProgressRequest(resourceUrl)}, 1000);
        },
        updateProgressBar : function (statusMsg) {
            var progressBar = $('#deleteProgress');
            if (progressBar.is(':visible')){
                progressBar.empty();
                new A.ProgressBar({
                boundingBox: '#deleteProgress',
                orientation: 'horizontal',
                value : statusMsg.progress,
                max : statusMsg.total
                }).render();
            }

        },

        callUpdateProgressRequest: function (resourceUrl ){

            A.io.request(resourceUrl + 'action=updateStatus', {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        let responseData = this.get('responseData');
                        if (xhr.status === 204){
                            FormsUtil.writeInfo("204: No data found!");
                            FormsUtil.stopProgressMonitor();
                        } else if (xhr.status !== 200){
                            FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            FormsUtil.stopProgressMonitor();
                        } else {
                            let statusMsg = JSON.parse(responseData);
                            if (statusMsg.status === 'terminated'){
                                FormsUtil.stopProgressMonitor();
                            } else if (statusMsg.status === 'available'){
                                FormsUtil.stopProgressMonitor();
                                var downloadUrl = resourceUrl + 'action=downloadLog'
                                FormsUtil.callDownloadLogFileRequest(downloadUrl);
                            } else {
                                FormsUtil.updateProgressBar(statusMsg);
                            }
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.stopProgressMonitor();
                        FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                    }
                }
            });

        },
        callDownloadLogFileRequest : function (requestUrl){

            A.io.request(requestUrl, {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        let responseData = this.get('responseData');
                        if (xhr.status !== 200){
                            FormsUtil.writeError(xhr.responseText);
                            return false;
                        } else {
                            FormsUtil.saveAs([responseData], "deleted-users.log");
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.responseText);
                    }
                }
            });
        },
        saveAs : function (data, fileName) {
            var a = document.createElement('a')
            a.style.cssText = 'display: none';
            document.body.appendChild(a);
            // var a = document.getElementById("downloadLink");
            var blob = new Blob(data, {type: "text/plain;charset=utf-8;"});
            a.href = window.URL.createObjectURL(blob);
            a.download = fileName;
            a.click();
            document.body.removeChild(a);
        }
    }
    $('#deleteButton').on('click', function(){
        FormsUtil.delete("<portlet:resourceURL/>", "<portlet:namespace/>")
    });
    $('#deleteDisabledButton').on('click', function(){
        FormsUtil.deleteDisabled("<portlet:resourceURL/>", "<portlet:namespace/>")
    });
    $('#disabledTime').value = new Date(Date.now() - year_millis).toDateString();
</aui:script>
