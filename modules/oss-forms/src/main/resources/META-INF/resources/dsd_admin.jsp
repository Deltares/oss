<%@ include file="/META-INF/resources/dsd_admin_init.jsp" %>

<span id="group-error-block"></span>

<div class="row">
        <span class="col-xs-10">
            <label for="eventSelection"><h2><liferay-ui:message key="dsd.admin.downloadTitle" /> </h2></label>

                <select id="eventSelection" name="eventSelection">
                    <%    for (JournalArticle event : events) { %>
                    <option value="<%=event.getArticleId() %>" label ='<%= event.getTitle() %>'></option>
                    <% } %>
                </select>
        </span>
    <span class="col-sm-2">
            <div style="text-align: right;">
                <input id="downloadButton" type="button" value="<liferay-ui:message key="dsd.admin.download" />"/>
            </div>
        </span>
</div>
<a id="downloadLink" style="display: none" href=""></a>

<script type="text/javascript">

    AUI().use('event', 'aui-io-request','node', function(A) {
        var downloadButton = A.one('#downloadButton');
        var eventSelection=  A.one('#eventSelection');
        var errorBlock = A.one('#group-error-block');

        var saveAs = (function (data, fileName) {
            var a = document.getElementById("downloadLink");
            var blob = new Blob(data, {type: "text/csv;charset=utf-8;"});
            a.href = window.URL.createObjectURL(blob);
            a.download = fileName;
            a.click();
            window.URL.revokeObjectURL(url);
        });

        downloadButton.on('click', function(){
            errorBlock.html('');
            let selection = document.getElementById("eventSelection");
            var eventArticleId = selection.options[ selection.selectedIndex ].value;
            var eventArticleTitle = selection.options[ selection.selectedIndex ].label;
            var downloadUrl = '<portlet:resourceURL/>&<portlet:namespace/>eventId=' + eventArticleId;

            if (eventArticleId != null && eventArticleId!=="") {
                A.io.request(downloadUrl, {
                    on : {
                        success : function(response, status, xhr) {
                            let contentType = xhr.getResponseHeader("content-type") || "";
                            if(contentType.includes('application/json')){
                                let message = JSON.parse(this.get('responseData')).message;
                                let errorMessageNode = A.Node.create('<div class="portlet-msg-error">' + message + '</div>');
                                errorMessageNode.appendTo(errorBlock);
                                return false;
                            } else {
                                var fileName = eventArticleTitle + "-download.csv";
                                let responseData = this.get('responseData');
                                saveAs([responseData], fileName);
                            }
                        },
                        failure : function() {
                            alert('bad request');
                        }
                    }
                });
            }

        });
        eventSelection.on('focus', function(){
            errorBlock.html('');
        });

    });

</script>
