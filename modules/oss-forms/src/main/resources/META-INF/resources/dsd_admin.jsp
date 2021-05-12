<%@ include file="dsd_admin_init.jsp" %>

<span id="group-message-block"></span>
<aui:fieldset label="dsd.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="dsd.admin.siteConfigTitle"/>  </div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.siteConfigText"/>  </div>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="dsd.admin.downloadTitle"/>  </div>
        </aui:col>
        <aui:col width="20">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.selectDownload"/>  </div>
        </aui:col>
        <aui:col width="25">
            <select id="eventSelection" name="eventSelection" class="btn btn-lg" style="border-color: lightgray" >
                <% for (JournalArticle event : events) { %>
                <option value="<%=event.getArticleId() %>" ><%= event.getTitle() %></option>
                <% } %>
            </select>
        </aui:col>
        <aui:col width="5">
            <button id="downloadButton"  class="btn btn-lg" type="button"><liferay-ui:message key="dsd.admin.download"/> </button>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" > <liferay-ui:message key="dsd.admin.deleteTitle"/>  </div>
        </aui:col>
        <aui:col width="20">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.enterEventId"/>  </div>
        </aui:col>
        <aui:col width="25">
            <input id="eventId" value="" class="form-control">
        </aui:col>
        <aui:col width="5">
            <button id="deleteButton"  class="btn btn-lg" type="button"><liferay-ui:message key="dsd.admin.delete"/> </button>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="100">
            <div id="progressBar" style="height:10px;"></div>
        </aui:col>
    </aui:row>

</aui:fieldset>

<aui:script use="event, aui-io-request, node,aui-base, aui-progressbar">
    let progressId = '';
    let FormsUtil = {

        writeError: function(message){
            let errorBlock = A.one('#group-message-block');
            let errorMessageNode = A.Node.create('<div class="portlet-msg-error">' + message + '</div>');
            errorMessageNode.appendTo(errorBlock);
        },
        writeInfo: function(message){
            let messageBlock = A.one('#group-message-block');
            let messageNode = A.Node.create('<div class="portlet-msg-info">' + message + '</div>');
            messageNode.appendTo(messageBlock);
        },

        delete: function(resourceUrl, namespace){
            this.clearError();
            var eventArticleId = document.getElementById("eventId").value;

            if (confirm("You are about to delete all registrations for event: " + eventArticleId + "\nDo you want to continue?") === false) {
                eventArticleId = null;
                return;
            }
           if (eventArticleId != null && eventArticleId!=="") {
                resourceUrl = resourceUrl + '&' + namespace + 'eventId=' + eventArticleId;
                FormsUtil.callDownloadRegistrations(resourceUrl, namespace, eventArticleId, "delete")
            } else {
                FormsUtil.writeInfo('Please enter a valid eventId');
            }
        },

        download: function(resourceUrl, namespace){

            this.clearError();
            let selection = document.getElementById( "eventSelection");
            var eventArticleId = selection.options[ selection.selectedIndex ].value;
            if (eventArticleId != null && eventArticleId!=="") {
                resourceUrl = resourceUrl + '&' + namespace + 'eventId=' + eventArticleId;
                FormsUtil.callDownloadRegistrations(resourceUrl, namespace, eventArticleId, "download");
            }
        },

        callDownloadRegistrations : function (resourceUrl, namespace, eventId, action){

            FormsUtil.updateProgressBar(JSON.parse('{"status": "pending", "progress":0, "total":100}'));

            A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {
                on : {
                    success : function(response, status, xhr) {
                        if (xhr.status > 299){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeError(xhr.status + ':' + xhr.responseText);
                            return false;
                        } else if(xhr.status === 204){
                            FormsUtil.stopProgressMonitor()
                            FormsUtil.writeInfo("204: No event found!");
                            return true;
                        } else if (xhr.status === 200){
                            FormsUtil.startProgressMonitor(resourceUrl, namespace);
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.status + ': ' + xhr.responseText);
                    }
                }
            });
        },
        clearError : function(){
            let errorBlock = A.one('#group-message-block');
            errorBlock.html('');
        },
        stopProgressMonitor : function (){
            clearInterval(progressId);
            progressId = '';
            var progressBar = $('#progressBar');
            progressBar.hide();
            progressBar.empty();
            $('#deleteButton').prop('disabled', false);
            $('#downloadButton').prop('disabled', false);
        },
        startProgressMonitor : function(resourceUrl, namespace){
            if (progressId !== '') {
                alert("A process is already running!");
                return
            }
            $('#progressBar').show();
            $('#deleteButton').prop('disabled', true);
            $('#downloadButton').prop('disabled', true);
            progressId = setInterval(function(){FormsUtil.callUpdateProgressRequest(resourceUrl, namespace)}, 1000);
        },
        callUpdateProgressRequest : function (resourceUrl, namespace){

            A.io.request(resourceUrl + '&' + namespace + 'action=updateStatus', {
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
                            FormsUtil.writeInfo("204: No registrations found!");
                            return true;
                        } else if (xhr.status === 200){
                            let responseData = this.get('responseData');
                            let statusMsg = JSON.parse(responseData);
                            if (statusMsg.status === 'terminated'){
                                FormsUtil.stopProgressMonitor();
                            } else if (statusMsg.status === 'available'){
                                FormsUtil.stopProgressMonitor();
                                FormsUtil.callDownloadLogFileRequest(resourceUrl, namespace);
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
        callDownloadLogFileRequest : function (resourceUrl, namespace){
            A.io.request(resourceUrl + '&' + namespace + 'action=downloadLog', {
                sync : 'true',
                cache : 'false',
                on : {
                    success : function(response, status, xhr) {
                        let responseData = this.get('responseData');
                        if (xhr.status !== 200){
                            FormsUtil.writeError(xhr.responseText);
                            return false;
                        } else {
                            FormsUtil.saveAs([responseData], "registered-users.csv");
                        }
                    },
                    failure : function(response, status, xhr) {
                        FormsUtil.writeError(xhr.responseText);
                    }
                }
            });
        },
        updateProgressBar : function (statusMsg) {
            var progressBar = $('#progressBar');
            if (progressBar.is(':visible')){
                progressBar.empty();
                new A.ProgressBar({
                    boundingBox: '#progressBar',
                    orientation: 'horizontal',
                    value : statusMsg.progress,
                    max : statusMsg.total
                }).render();
            }
        },
        saveAs : function (data, fileName) {
            var a = document.createElement('a')
            a.style.cssText = 'display: none';
            document.body.appendChild(a);
            // var a = document.getElementById("downloadLink");
            var blob = new Blob(data, {type: "text/csv;charset=utf-8;"});
            a.href = window.URL.createObjectURL(blob);
            a.download = fileName;
            a.click();
            document.body.removeChild(a);
        }
    }
    $('#downloadButton').on('click', function(){
        FormsUtil.download("<portlet:resourceURL/>", "<portlet:namespace/>")
    });
    $('#deleteButton').on('click', function(){
        FormsUtil.delete("<portlet:resourceURL/>", "<portlet:namespace/>")
    });

    $('#eventSelection').on('focus', function(){
        FormsUtil.clearError()
    });

</aui:script>
