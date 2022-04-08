var actionButtons = [];

CommonFormsUtil = {

    writeError: function(namespace,  message){
        let errorBlock = document.getElementById(namespace + "group-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-error");
        let messageContentNode = document.createTextNode(message);
        messageNode.appendChild(messageContentNode);
        errorBlock.appendChild(messageNode);
    },
    writeInfo: function(namespace, message){
        let messageBlock = document.getElementById(namespace + "group-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-info");
        let messageContentNode = document.createTextNode(message);
        messageNode.appendChild(messageContentNode);
        messageBlock.appendChild(messageNode);
    },
    clearError : function(namespace){
        let errorBlock = document.getElementById(namespace + 'group-message-block');
        errorBlock.innerHTML = '';
    },
    setActionButtons: function (buttons){
        buttons.forEach(function (value){
            if (!actionButtons.includes(value)){
                actionButtons.push(value);
            }
        });
    },
    initProgressBar: function (namespace){
        var progressBar = document.getElementById(namespace + 'progressBar');
        progressBar.style.display = 'block';
        //disable each time. It is possible that user goes to different page and comes back
        this.setButtonDisabledState(namespace, true);
        progressBar.innerHTML = '';
        let A = new AUI();
        new A.ProgressBar({
            boundingBox: '#' + namespace + "progressBar",
            orientation: 'horizontal',
            value : 0,
            max : 100
        }).render();
    },
    stopProgressMonitor : function (namespace){
        clearInterval(this.getRunningProcess(namespace));
        this.setRunningProcess(namespace, undefined);

        let progressBar = document.getElementById(namespace + 'progressBar');
        progressBar.style.display = 'none';
        progressBar.innerHTML = '';
        this.setButtonDisabledState(namespace, false);
    },
    startProgressMonitor : function(namespace){
        document.getElementById(namespace + 'progressBar').style.display = 'block';
        this.setButtonDisabledState(namespace, true);
    },
    updateProgressBar : function (namespace, statusMsg) {
        var progressBar = document.getElementById(namespace + 'progressBar');
        if (progressBar.style.display !== 'none'){
            //disable each time. It is possible that user goes to different page and comes back
            this.setButtonDisabledState(namespace, true);
            progressBar.innerHTML = '';
            let A = new AUI();
            new A.ProgressBar({
                boundingBox: '#' + namespace + "progressBar",
                orientation: 'horizontal',
                value : statusMsg.progress,
                max : statusMsg.total
            }).render();
        }
    },
    callUpdateProgressRequest : function (resourceUrl, namespace, id){

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=updateStatus' + '&' + namespace + 'id=' + id, {
            sync : 'true',
            cache : 'false',
            on : {
                success : function(response, status, xhr) {
                    if (xhr.status > 299){
                        CommonFormsUtil.stopProgressMonitor(namespace);
                        CommonFormsUtil.writeError(namespace, xhr.status + ':' + xhr.responseText);
                    } else if(xhr.status === 204){
                        CommonFormsUtil.stopProgressMonitor(namespace);
                        CommonFormsUtil.writeInfo(namespace,"204: No registrations found!");
                    } else if (xhr.status === 200){
                        let responseData = this.get('responseData');
                        let statusMsg = JSON.parse(responseData);
                        if (statusMsg.status === 'terminated'){
                            CommonFormsUtil.stopProgressMonitor(namespace);
                        } else if (statusMsg.status === 'available'){
                            CommonFormsUtil.stopProgressMonitor(namespace);
                            CommonFormsUtil.callDownloadLogFileRequest(resourceUrl, namespace, id);
                        } else {
                            CommonFormsUtil.updateProgressBar(namespace, statusMsg);
                        }
                    } else {
                        CommonFormsUtil.stopProgressMonitor(namespace)
                    }
                },
                failure : function(response, status, xhr) {
                    CommonFormsUtil.stopProgressMonitor(namespace);
                    CommonFormsUtil.writeError(namespace,  xhr.status + ':' + xhr.responseText);
                }
            }
        });

    },
    setButtonDisabledState: function (namespace, disabled){
        if (!actionButtons) return;
        actionButtons.forEach(function (button){
            let buttonElement = document.getElementById(namespace + button);
            if (buttonElement) {
                buttonElement.disabled = disabled;
            }
            // else button from other portlet
        });

    },
    callDownloadLogFileRequest : function (resourceUrl, namespace, id){
        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=downloadLog' + '&' + namespace + 'id=' + id, {
            on : {
                success : function(response, status, xhr) {
                    let responseData = this.get('responseData');
                    if (xhr.status !== 200){
                        CommonFormsUtil.writeError(namespace, xhr.responseText);
                    } else {
                        CommonFormsUtil.saveAs([responseData], "oss-admin-download.csv");
                    }
                },
                failure : function(response, status, xhr) {
                    CommonFormsUtil.writeError(namespace, xhr.responseText);
                }
            }
        });
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
    },
    getRunningProcess: function (namespace){
        let runningProcess = document.getElementById(namespace + "runningProcess");
        return runningProcess.value;
    },
    setRunningProcess: function (namespace, processId){
        let runningProcess = document.getElementById(namespace + "runningProcess");
        runningProcess.value = processId;
    },

    getFormName : function(namespace) {
        return namespace + "fm";
    },

    updatePaymentAddress : function(namespace, checked) {
        let paymentNameInput = $(document.getElementById(namespace + "billing_name"));
        let paymentAddressInput = $(document.getElementById(namespace + "billing_address"));
        let paymentPostCodeInput = $(document.getElementById( namespace + "billing_postal"));
        let paymentCityInput = $(document.getElementById( namespace + "billing_city"));
        let paymentCountryInput = $(document.getElementById(namespace + "billing_country"));
        let paymentEmailInput = $(document.getElementById( namespace + "billing_email"));

        if (checked) {

            //cache billing info
            paymentNameInput.prop('billing_value', paymentNameInput.val());
            paymentAddressInput.prop('billing_value', paymentAddressInput.val());
            paymentPostCodeInput.prop('billing_value', paymentPostCodeInput.val());
            paymentCityInput.prop('billing_value', paymentCityInput.val());
            paymentCountryInput.prop('billing_value', paymentCountryInput.val());
            paymentEmailInput.prop('billing_value', paymentEmailInput.val());

            //replace billing info with user attributes info
            let name = $(document.getElementById(namespace + "org_name")).val();
            let address = $(document.getElementById(namespace + "org_address")).val();
            let postCode = $(document.getElementById(namespace + "org_postal")).val();
            let city = $(document.getElementById(namespace + "org_city")).val();
            let country = $(document.getElementById(namespace + "org_country")).val();
            let email = $(document.getElementById(namespace + "email")).val();

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);

            paymentNameInput.prop('disabled', true);
            paymentAddressInput.prop('disabled', true);
            paymentPostCodeInput.prop('disabled', true);
            paymentCityInput.prop('disabled', true);
            paymentCountryInput.prop('disabled', true);
            paymentEmailInput.prop('disabled', true);

        } else {
            //restore billing info
            let name = paymentNameInput.prop('billing_value');
            let address = paymentAddressInput.prop('billing_value');
            let postCode = paymentPostCodeInput.prop('billing_value');
            let city = paymentCityInput.prop('billing_value');
            let country = paymentCountryInput.prop('billing_value');
            let email = paymentEmailInput.prop('billing_value');

            paymentNameInput.val(name);
            paymentAddressInput.val(address);
            paymentPostCodeInput.val(postCode);
            paymentCityInput.val(city);
            paymentCountryInput.val(country);
            paymentEmailInput.val(email);

            paymentNameInput.prop('disabled', false);
            paymentAddressInput.prop('disabled', false);
            paymentPostCodeInput.prop('disabled', false);
            paymentCityInput.prop('disabled', false);
            paymentCountryInput.prop('disabled', false);
            paymentEmailInput.prop('disabled', false);

        }
    }
}
