var TableFormsUtil = {

    loadSelection: function(namespace, selected) {
        let rowElements = document.getElementsByName(namespace + "rowIds");
        [...rowElements].forEach(function(rowElement) {
            if (rowElement.checked){
                selected.push(rowElement.value);
            }
        });
    },

    deleteSelected: function(resourceUrl, renderUrl, namespace, filename){

        let selected = [];
        this.loadSelection(namespace, selected);
        if (selected.length === 0){
            alert("Please select one or more records before continuing.");
        } else {

            if (confirm("You are about to delete the selected records from the table!\nDo you want to continue?") === false) {
                return;
            }

            let data = {
                selection : selected
            }
            this.callResourceUrl(resourceUrl, namespace, filename, "delete-selected", data, renderUrl);
        }

    },

    deleteAll: function(resourceUrl, renderUrl, namespace, filename){

        if (confirm("You are about to delete all records from the table for the current selection!\n\n" +
            "If you are deleting an Event and have not selected a Session, all sessions related to this Event " +
            "will be deleted! \n\n" +
            "If you are deleting downloads, all downloads will be deleted, including the downloads on the other pages.\n\n" +
            "Do you want to continue?") === false) {
            return;
        }

        this.callResourceUrl(resourceUrl, namespace, filename, "delete-all", {}, renderUrl);
    },

    exportResults: function(resourceUrl, namespace, filename){
        this.callResourceUrl(resourceUrl, namespace, filename, "export", {}, null);
    },

    callResourceUrl: function(resourceUrl, namespace, filename, action, data, redirectUrl){

        CommonFormsUtil.clearError(namespace);
        CommonFormsUtil.setActionButtons(['exportResultsButton', 'deleteSelectedButton']);
        CommonFormsUtil.initProgressBar(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {

            method: 'POST',
            type: 'json',
            data: data,
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status > 299){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeError(namespace,xhr.status + ':' + xhr.responseText);
                        return false;
                    } else if(xhr.status === 204){
                        CommonFormsUtil.stopProgressMonitor(namespace)
                        CommonFormsUtil.writeInfo(namespace, "204: No records found!");
                        return true;
                    } else if (xhr.status === 200){
                        let jsonResponse = JSON.parse(xhr.responseText);
                        if (jsonResponse.status === 'nodata'){
                            CommonFormsUtil.writeInfo(namespace, "No data found for request");
                            return true;
                        } else {
                            CommonFormsUtil.startProgressMonitor(namespace);
                            CommonFormsUtil.setRunningProcess(namespace, setInterval(function () {
                                CommonFormsUtil.callUpdateProgressRequest(resourceUrl, namespace, jsonResponse.id, filename, redirectUrl)
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