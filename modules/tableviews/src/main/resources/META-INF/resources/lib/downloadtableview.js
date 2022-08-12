

TableFormsUtil = {

    loadSelection: function(selected) {
        let rowElements = document.getElementsByClassName("downloadTableRecord");
        [...rowElements].forEach(function(rowElement) {
            if (rowElement.checked){
                selected.push(rowElement.getAttribute('recordid'));
            }
        });
    },

    deleteSelected: function(resourceUrl, renderUrl, namespace, filename){

        let selected = [];
        this.loadSelection(selected);
        if (selected.length === 0){
            alert("Please select one or more downloads before continuing.");
        } else {
            this.callResourceUrl(resourceUrl, namespace, filename, "delete-selected", selected, renderUrl);
        }

    },

    paidSelected: function(resourceUrl, renderUrl, namespace, filename){

        let selected = [];
        this.loadSelection(selected);
        if (selected.length === 0){
            alert("Please select one or more downloads before continuing.");
        } else {
            this.callResourceUrl(resourceUrl, namespace, filename, "paid-selected", selected, renderUrl);
        }

    },

    exportResults: function(resourceUrl, namespace, filename){
        this.callResourceUrl(resourceUrl, namespace, filename, "export", null, null);
    },

    callResourceUrl: function(resourceUrl, namespace, filename, action, data, redirectUrl){

        CommonFormsUtil.clearError(namespace);
        CommonFormsUtil.setActionButtons(['exportResultsButton', 'deleteSelectedButton', 'paidSelectedButton']);
        CommonFormsUtil.initProgressBar(namespace);

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=' + action, {

            method: 'POST',
            type: 'json',
            data: {
                selection : data
            },
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
                            CommonFormsUtil.writeInfo("No data found for request");
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