
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<span id="group-message-block"></span>
<aui:fieldset label="Dummy Admin Console">
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" >Action no background thread</div>
        </aui:col>
        <aui:col width="50">
            <button id="executeButton1" class="btn btn-lg" type="button">Execute</button>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" >Action with background thread</div>
        </aui:col>
        <aui:col width="50">
            <button id="executeButton2" class="btn btn-lg" type="button">Execute</button>
        </aui:col>
    </aui:row>
    <aui:row>
        <aui:col width="50">
            <div class="panel-title" id="Title"></div>
        </aui:col>
        <aui:col width="50">
            <div id="deleteProgress" style="height:10px;"></div>
        </aui:col>
    </aui:row>
</aui:fieldset>

<aui:script use="event, aui-io-request, node, aui-base, aui-progressbar">
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
        deleteDirect: function(resourceUrl, namespace){
        this.clearMessage();

        A.io.request(resourceUrl + '&' + namespace + 'action=deleteDirect', {
        sync : 'true',
        cache : 'false',
        on : {
            success : function(response, status, xhr) {

                let responseData = this.get('responseData');
                if (xhr.status !== 200){
                FormsUtil.writeError(responseData);
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
        delete: function(resourceUrl, namespace){
            this.clearMessage();
            FormsUtil.callDeleteBannedUsers(resourceUrl + '&' + namespace);
        },
        callDeleteBannedUsers : function (resourceUrl){

            FormsUtil.updateProgressBar(JSON.parse('{"status": "pending", "progress":1, "total":100}'));
            A.io.request(resourceUrl + 'action=deleteBannedUsers', {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        if (xhr.status > 299){
    FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            FormsUtil.stopProgressMonitor();
                            return false;
                        } else if(xhr.status === 204){
    FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            FormsUtil.stopProgressMonitor();
                            return true;
                        } else if (xhr.status === 200){
                            FormsUtil.startProgressMonitor(resourceUrl);
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                        FormsUtil.stopProgressMonitor();
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
        },
        startProgressMonitor : function(resourceUrl){
            if (progressId !== '') {
                alert("DeleteBannedUsers process already running!");
                return
            }
            $('#deleteProgress').show();
            $('#deleteButton').prop('disabled', true);
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
                        if (xhr.status !== 200){
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
                            FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            return false;
                        } else {
                            FormsUtil.saveAs([responseData], "deleted-users.log");
                        }
                    },
                    failure : function(response, status, xhr) {
                        alert(xhr.status + ':' + xhr.responseText);
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
    $('#executeButton1').on('click', function(){
        FormsUtil.deleteDirect("<portlet:resourceURL/>", "<portlet:namespace/>")
    });
    $('#executeButton2').on('click', function(){
    FormsUtil.delete("<portlet:resourceURL/>", "<portlet:namespace/>")
    });
</aui:script>
