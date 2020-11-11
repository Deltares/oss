<%@ include file="/META-INF/resources/dsd_admin_init.jsp" %>

<span id="group-error-block"></span>
<a id="downloadLink" style="display: none" href=""></a>
<aui:fieldset label="dsd.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" id="Title"> <liferay-ui:message key="dsd.admin.siteConfigTitle"/>  </div>
        </aui:col>
        <aui:col width="50">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.siteConfigText"/>  </div>
        </aui:col>
    </aui:row>
    <hr>
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" id="Title"> <liferay-ui:message key="dsd.admin.downloadTitle"/>  </div>
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
            <div class="panel-title" id="Title"> <liferay-ui:message key="dsd.admin.deleteTitle"/>  </div>
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

</aui:fieldset>

<aui:script use="event, aui-io-request, node">
    let FormsUtil = {
        writeError: function(data){
            let errorBlock = A.one('#group-error-block');
            let message = JSON.parse(data).message;
            let errorMessageNode = A.Node.create('<div class="portlet-msg-error">' + message + '</div>');
            errorMessageNode.appendTo(errorBlock);
        },
        delete: function(resourceUrl, namespace){
            this.clearError();
            var eventArticleId = document.getElementById( "eventSelection").value;

            if (confirm("You are about to delete all registrations for event: " + eventArticleId + "\nDo you want to continue?") == false) {
                eventArticleId = null;
                return;
            }

            var deleteUrl = resourceUrl + '&' + namespace + 'eventId=' + eventArticleId + '&' + namespace + 'action=delete';

            if (eventArticleId != null && eventArticleId!=="") {
                A.io.request(deleteUrl, {
                    on : {
                        success : function(response, status, xhr) {
                            let responseData = this.get('responseData');
                            let contentType = xhr.getResponseHeader("content-type") || "";
                            if(contentType.includes('application/json')){
                                FormsUtil.writeError([responseData]);
                                return false;
                            } else {
                                var fileName = eventArticleId + "-deleted.csv";
                                FormsUtil.saveAs([responseData], fileName);
                            }
                        },
                        failure : function() {
                            alert('bad request');
                        }
                    }
                });
            }
        },
        download: function(resourceUrl, namespace){
            this.clearError();
            let selection = document.getElementById( "eventSelection");
            var eventArticleId = selection.options[ selection.selectedIndex ].value;
            var eventArticleTitle = selection.options[ selection.selectedIndex ].label;
            var downloadUrl = resourceUrl + '&' + namespace + 'eventId=' + eventArticleId;

            if (eventArticleId != null && eventArticleId!=="") {
                A.io.request(downloadUrl, {
                    on : {
                        success : function(response, status, xhr) {
                            let responseData = this.get('responseData');
                            let contentType = xhr.getResponseHeader("content-type") || "";
                            if(contentType.includes('application/json')){
                                FormsUtil.writeError([responseData]);
                                return false;
                            } else {
                                var fileName = eventArticleTitle + "-download.csv";
                                FormsUtil.saveAs([responseData], fileName);
                            }
                        },
                        failure : function() {
                            alert('bad request');
                        }
                    }
                });
            }
        },
        clearError : function(){
            let errorBlock = A.one('#group-error-block');
            errorBlock.html('');
        },
        saveAs : function (data, fileName) {
            var a = document.getElementById("downloadLink");
            var blob = new Blob(data, {type: "text/csv;charset=utf-8;"});
            a.href = window.URL.createObjectURL(blob);
            a.download = fileName;
            a.click();
        },
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
