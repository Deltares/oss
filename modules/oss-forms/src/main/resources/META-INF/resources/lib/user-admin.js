OssFormsUtil = {

    getActionButtons: function (){
        return ['deleteBannedUsersButton', 'downloadInvalidButton', 'deleteUsersButton', 'checkUsersButton'];
    },

    deleteBannedUsers: function(resourceUrl, namespace){
        CommonFormsUtil.clearError(namespace);

        if (confirm("You are about to delete all banned users from all sites !\nDo you want to continue?") === false) {
            return;
        }
        this.callOssAdminResource(resourceUrl, namespace, "deleteBannedUsers", "No banned users found!", "deleteBannedUsers.log")
    },

    downloadInvalid: function(resourceUrl, namespace){

        CommonFormsUtil.clearError(namespace);
        this.callOssAdminResource(resourceUrl, namespace, "downloadInvalidUsers", "No invalid users found!", "invalidUsers.csv");
    },

    checkUsersExist: function(resourceUrl, namespace){
        CommonFormsUtil.clearError(namespace);
        let usersFile = document.getElementById(namespace + "userFile").files[0];
        if (!usersFile){
            alert("Please select file to upload!");
            return;
        }
        this.callOssAdminPostResource(resourceUrl, namespace, "checkUsersExist", "checkUsersExist", "nonExistingUserEmails.csv");

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

        this.callOssAdminPostResource(resourceUrl, namespace, "deleteUsers", "deleteUsers", "deleteUsers.log");

    },

    callOssAdminPostResource: function (resourceUrl, namespace, action, form, downloadFileName){
        CommonFormsUtil.setActionButtons(this.getActionButtons());
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
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeError(namespace, 'Error: internal server error.');
                    } else {
                        let jsonResponse = JSON.parse(xhr.responseText);
                        if (jsonResponse.status === 'nodata'){
                            CommonFormsUtil.writeInfo("No data found for request");
                        } else if (jsonResponse.status === 'error'){
                            CommonFormsUtil.writeError(namespace, jsonResponse.status + ': ' + jsonResponse.message);
                        } else {
                            CommonFormsUtil.startProgressMonitor(namespace);
                            CommonFormsUtil.setRunningProcess(namespace, setInterval(function () {
                                CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace, jsonResponse.id, downloadFileName)
                            }, 1000));
                        }
                    }
                }
            }
        });
    },

    callOssAdminResource : function (resourceUrl, namespace, action, notfoundMessage, downloadFileName){

        CommonFormsUtil.setActionButtons(this.getActionButtons());
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
                        if (jsonResponse.status === 'nodata'){
                          CommonFormsUtil.writeInfo("No data found for request")  ;
                        } else {
                            CommonFormsUtil.startProgressMonitor(namespace);
                            CommonFormsUtil.setRunningProcess(namespace, setInterval(function () {
                                CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace, jsonResponse.id, downloadFileName)
                            }, 1000));
                        }
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
