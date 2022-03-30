OssFormsUtil = {

    deleteBannedUsers: function(resourceUrl, namespace){
        CommonFormsUtil.clearError(namespace);
        let siteId = document.getElementById(namespace + 'siteId').value;

        if (confirm("You are about to delete all banned users from site: " + siteId + "\nDo you want to continue?") === false) {
            siteId = null;
            return;
        }

        if (siteId != null && siteId !== ''){
            resourceUrl = resourceUrl + '&' + namespace + 'siteId=' + siteId;
            this.callOssAdminResource(resourceUrl, namespace, "deleteBannedUsers", "No Banned users found!")
        } else {
            CommonFormsUtil.writeInfo(namespace,'Please enter a valid articleId of Event or Registration');
        }
    },

    downloadDisabled: function(resourceUrl, namespace){

        CommonFormsUtil.clearError(namespace);
        let disabledTime = document.getElementById(namespace + 'disabledTime').value;

        if (confirm("You are about to download all disabled users that have been disabled after: " + disabledTime + "\nDo you want to continue?") === false) {
            disabledTime = null;
            return;
        }
        resourceUrl = resourceUrl + '&' + namespace + 'disabledTime=' + new Date(disabledTime).getTime();
        this.callOssAdminResource(resourceUrl, namespace, "downloadDisabledUsers", "No disabled users found!");
    },

    deleteUsers: function(resourceUrl, namespace){
        CommonFormsUtil.clearError(namespace);
        let usersFile = document.getElementById(namespace + "userFile").files[0];
        if (!usersFile){
            alert("Please select file to upload!");
            return;
        }
        if (confirm("You are about to delete users from file: " + usersFile.name + "\nDo you want to continue?") === false) {
            usersFile = null;
            return;
        }

        this.callDeleteUsersFromFile(resourceUrl, namespace, "deleteUsers", "deleteUsers");

    },

    callDeleteUsersFromFile: function (resourceUrl, namespace, action, form){
        CommonFormsUtil.setActionButtons(['deleteBannedUsersButton', 'downloadDisabledButton', 'deleteUsersButton']);
        CommonFormsUtil.initProgressBar(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {
            method: 'POST',
            form: {
                //load the form
                id: document.getElementById(namespace + form),
                upload: true
            },
            sync: true,
            on : {
                //We do it different here because we are posting from a FORM
                complete : function(response, status, xhr) {
                    if (xhr.responseText === undefined || xhr.responseText === ''){
                        // POST does not return response.
                        CommonFormsUtil.startProgressMonitor(namespace);
                        CommonFormsUtil.setRunningProcess(namespace, setInterval(function(){
                            CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace)
                        }, 1000));
                    } else {
                        let statusMsg = JSON.parse(xhr.responseText);
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeError(namespace, statusMsg.status + ':' + statusMsg.message);
                    }
                }
            }
        });
    },

    callOssAdminResource : function (resourceUrl, namespace, action, notfoundMessage){

        CommonFormsUtil.setActionButtons(['deleteBannedUsersButton', 'downloadDisabledButton', 'deleteUsersButton']);
        CommonFormsUtil.initProgressBar(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status > 299){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeError(namespace, xhr.status + ':' + xhr.responseText);
                        return false;
                    } else if(xhr.status === 204){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeInfo(namespace,"204: " + notfoundMessage);
                        return true;
                    } else if (xhr.status === 200){
                        let jsonResponse = JSON.parse(xhr.responseText);
                        CommonFormsUtil.startProgressMonitor(namespace);
                        CommonFormsUtil.setRunningProcess(namespace, setInterval(function(){
                            CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace, jsonResponse.id)
                        }, 1000));
                    } else {
                        CommonFormsUtil.stopProgressMonitor(namespace)
                    }
                },
                failure : function(response, status, xhr) {
                    CommonFormsUtil.stopProgressMonitor(namespace)
                    CommonFormsUtil.writeError(namespace, xhr.status + ': ' + xhr.responseText);
                }
            }
        });
    }


}
