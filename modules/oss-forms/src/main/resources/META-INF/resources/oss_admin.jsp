<%@ include file="oss_admin_init.jsp" %>

<span id="group-error-block"></span>
<a id="downloadLink" style="display: none" href=""></a>
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
    var siteId = document.getElementById( "siteId").value;

    if (confirm("You are about to delete all banned users from site: " + siteId + "\nDo you want to continue?") == false) {
    siteId = null;
    return;
    }

    var deleteUrl = resourceUrl + '&' + namespace + 'siteId=' + siteId + '&' + namespace + 'action=deleteBannedUsers';

    if (siteId != null && siteId!=="") {
    A.io.request(deleteUrl, {
    on : {
    success : function(response, status, xhr) {
    let responseData = this.get('responseData');
    let contentType = xhr.getResponseHeader("content-type") || "";
    if(contentType.includes('application/json')){
    FormsUtil.writeError([responseData]);
    return false;
    } else {
    var fileName = siteId + "-deleted-users.log";
    FormsUtil.saveAs([responseData], fileName);
    }
    },
    failure : function(response, status, xhr) {
    alert(xhr.status + ':' + xhr.responseText);
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
    }
    }
    $('#deleteButton').on('click', function(){
    FormsUtil.delete("<portlet:resourceURL/>", "<portlet:namespace/>")
    });

</aui:script>
