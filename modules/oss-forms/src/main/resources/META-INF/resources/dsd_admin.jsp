<%@ include file="/META-INF/resources/dsd_admin_init.jsp" %>

<span id="group-error-block"></span>
<a id="downloadLink" style="display: none" href=""></a>
<aui:fieldset label="dsd.admin.adminPageTitle"  >
    <aui:row>
        <aui:col width="50" >
            <div class="panel-title" id="Title"> <liferay-ui:message key="dsd.admin.downloadTitle"/>  </div>
        </aui:col>
        <aui:col width="20">
            <div class="control-label" > <liferay-ui:message key="dsd.admin.selectDownload"/>  </div>
        </aui:col>
        <aui:col width="20">
            <select id="eventSelection" name="eventSelection" class="btn btn-lg" style="border-color: lightgray" >
                <% for (JournalArticle event : events) { %>
                <option value="<%=event.getArticleId() %>" ><%= event.getTitle() %></option>
                <% } %>
            </select>
        </aui:col>
        <aui:col width="10">
            <button id="downloadButton"  class="btn btn-lg" type="button"><liferay-ui:message key="dsd.admin.download"/> </button>
        </aui:col>
    </aui:row>
</aui:fieldset>

<aui:script use="event, aui-io-request, node">
    let FormsUtil = {
        writeError: function(data){
            let message = JSON.parse(data).message;
            let errorMessageNode = A.Node.create('<div class="portlet-msg-error">' + message + '</div>');
            errorMessageNode.appendTo(errorBlock);
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

    $('#eventSelection').on('focus', function(){
        FormsUtil.clearError()
    });

</aui:script>
