DsdAdminFormsUtil = {

    downloadRegistrations: function(resourceUrl, namespace){

        CommonFormsUtil.clearError(namespace);
        let element = document.getElementById( namespace + "eventSelection");
        var eventArticleId = element.options[ element.selectedIndex ].value;
        let checkBox = $('input[name='+ namespace + "removeMissing" + ']');

        if (eventArticleId != null && eventArticleId!=="") {
            resourceUrl = resourceUrl + '&' + namespace + 'articleId=' + eventArticleId;
            let action;
            if (checkBox[0].checked){
                action = "removeMissing"
            } else {
                action = "download"
            }
            this.callDownloadRegistrations(resourceUrl, namespace, action);
        }
    },
    downloadRepro: function(resourceUrl, namespace){

        CommonFormsUtil.clearError(namespace);
        let element = document.getElementById( namespace + "eventSelection");
        var eventArticleId = element.options[ element.selectedIndex ].value;
        if (eventArticleId != null && eventArticleId!=="") {
            resourceUrl = resourceUrl + '&' + namespace + 'articleId=' + eventArticleId;
            let action = "downloadRepro";
            this.callDownloadRegistrations(resourceUrl, namespace, action);
        }
    },

    deleteRegistrations: function(resourceUrl, namespace){
        CommonFormsUtil.clearError(namespace);
        let eventArticleId = document.getElementById( namespace + "articleId").value;
        let checkBox = $('input[name='+ namespace + "isResourcePrimKey" + ']');

        if (confirm("You are about to delete all registrations for event: " + eventArticleId + "\nDo you want to continue?") === false) {
            eventArticleId = null;
            return;
        }

        let action;
        if (checkBox[0].checked){
            action = "deletePrimKey"
        } else {
            action = "delete"
        }
        if (eventArticleId != null && eventArticleId!=="") {
            resourceUrl = resourceUrl + '&' + namespace + 'articleId=' + eventArticleId;
            this.callDownloadRegistrations(resourceUrl, namespace, action)
        } else {
            CommonFormsUtil.writeInfo(namespace,'Please enter a valid articleId of Event or Registration');
        }
    },

    callDownloadRegistrations : function (resourceUrl, namespace, action){

        CommonFormsUtil.setActionButtons(['downloadRegistrationsButton', 'deleteRegistrationsButton', 'downloadReproButton']);
        CommonFormsUtil.initProgressBar(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status > 299){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeError(namespace,xhr.status + ':' + xhr.responseText);
                        return false;
                    } else if(xhr.status === 204){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeInfo(namespace, "204: No event found!");
                        return true;
                    } else if (xhr.status === 200){
                        let jsonResponse = JSON.parse(xhr.responseText);
                        if (jsonResponse.status === 'nodata'){
                            CommonFormsUtil.writeInfo("No data found for request");
                        } else {
                            CommonFormsUtil.startProgressMonitor(namespace);
                            CommonFormsUtil.setRunningProcess(namespace, setInterval(function () {
                                CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace, jsonResponse.id, 'downloadRegistrations.csv')
                            }, 1000));
                        }
                    } else {
                        CommonFormsUtil.stopProgressMonitor(namespace)
                    }
                },
                failure : function(response, status, xhr) {
                    CommonFormsUtil.stopProgressMonitor(namespace)
                    CommonFormsUtil.writeError(namespace, xhr.status + ': ' + xhr.responseText);
                },
                error : function (){
                    alert("Error")
                }
            }
        });
    }

}
