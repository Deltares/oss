var actionButtons = [];

var CommonFormsUtil = {

    registerOther: function (namespace) {
        let registerOther = document.getElementById(namespace + "registration_other").checked;
        let firstName = document.getElementById(namespace + "first_name");
        let lastName = document.getElementById(namespace + "last_name");
        let email = document.getElementById(namespace + "email");
        firstName.disabled = !registerOther;
        lastName.disabled = !registerOther;
        email.disabled = !registerOther;
        if (registerOther) {
            firstName.classList.remove("disabled");
            lastName.classList.remove("disabled");
            email.classList.remove("disabled");
        } else {
            firstName.classList.add("disabled");
            lastName.classList.add("disabled");
            email.classList.add("disabled");
            firstName.value = firstName.getAttribute('original_value');
            lastName.value = lastName.getAttribute('original_value');
            email.value = email.getAttribute('original_value');
        }
    },

    writeError: function (namespace, message) {
        let errorBlock = document.getElementById(namespace + "group-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-error");
        messageNode.innerHTML = message;
        messageNode.style = "display: flex; justify-content: space-between";

        let closeButton = document.createElement("button");
        let span = document.createElement("span");
        span.innerText = "X";
        closeButton.appendChild(span)
        closeButton.addEventListener('click', function (evt) {
            const alert = evt.target.closest('.portlet-msg-error');
            if (alert) {
                alert.remove();
            }
        });
        messageNode.appendChild(closeButton);
        errorBlock.appendChild(messageNode);
    },
    writeInfo: function (namespace, message) {
        let messageBlock = document.getElementById(namespace + "group-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-info");
        messageNode.innerHTML = message;
        messageBlock.appendChild(messageNode);
    },
    clearError: function (namespace) {
        let errorBlock = document.getElementById(namespace + 'group-message-block');
        errorBlock.innerHTML = '';
    },
    setActionButtons: function (buttons) {
        buttons.forEach(function (value) {
            if (!actionButtons.includes(value)) {
                actionButtons.push(value);
            }
        });
    },
    initProgressBar: function (namespace) {
        var progressBar = document.getElementById(namespace + 'progressBar');
        progressBar.style.display = 'block';
        //disable each time. It is possible that user goes to different page and comes back
        this.setButtonDisabledState(namespace, true);
        progressBar.innerHTML = '';
        let A = new AUI();
        new A.ProgressBar({
            boundingBox: '#' + namespace + "progressBar",
            orientation: 'horizontal',
            value: 0,
            max: 100
        }).render();
    },
    stopProgressMonitor: function (namespace) {
        clearInterval(this.getRunningProcess(namespace));
        this.setRunningProcess(namespace, undefined);

        let progressBar = document.getElementById(namespace + 'progressBar');
        progressBar.style.display = 'none';
        progressBar.innerHTML = '';
        this.setButtonDisabledState(namespace, false);
    },
    startProgressMonitor: function (namespace) {
        document.getElementById(namespace + 'progressBar').style.display = 'block';
        this.setButtonDisabledState(namespace, true);
    },
    updateProgressBar: function (namespace, statusMsg) {
        var progressBar = document.getElementById(namespace + 'progressBar');
        if (progressBar.style.display !== 'none') {
            //disable each time. It is possible that user goes to different page and comes back
            this.setButtonDisabledState(namespace, true);
            progressBar.innerHTML = '';
            let A = new AUI();
            new A.ProgressBar({
                boundingBox: '#' + namespace + "progressBar",
                orientation: 'horizontal',
                value: statusMsg.progress,
                max: statusMsg.total
            }).render();
        }
    },
    callUpdateProgressRequest: function (resourceUrl, namespace, id, downloadFileName) {

        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=updateStatus' + '&' + namespace + 'id=' + id, {
            sync: 'true',
            cache: 'false',
            on: {
                success: function (response, status, xhr) {
                    if (xhr.status > 299) {
                        CommonFormsUtil.stopProgressMonitor(namespace);
                        CommonFormsUtil.writeError(namespace, xhr.status + ':' + xhr.responseText);
                    } else if (xhr.status === 204) {
                        CommonFormsUtil.stopProgressMonitor(namespace);
                        CommonFormsUtil.writeInfo(namespace, "204: No records found!");
                    } else if (xhr.status === 200) {
                        let responseData = this.get('responseData');
                        let statusMsg = JSON.parse(responseData);
                        if (statusMsg.status === 'terminated') {
                            CommonFormsUtil.stopProgressMonitor(namespace);
                        } else if (statusMsg.status === 'available') {
                            CommonFormsUtil.stopProgressMonitor(namespace);
                            CommonFormsUtil.callDownloadLogFileRequest(resourceUrl, namespace, id, downloadFileName);
                        } else {
                            CommonFormsUtil.updateProgressBar(namespace, statusMsg);
                        }
                    } else {
                        CommonFormsUtil.stopProgressMonitor(namespace)
                    }
                },
                failure: function (response, status, xhr) {
                    CommonFormsUtil.stopProgressMonitor(namespace);
                    CommonFormsUtil.writeError(namespace, xhr.status + ':' + xhr.responseText);
                }
            }
        });

    },
    setButtonDisabledState: function (namespace, disabled) {
        if (!actionButtons) return;
        actionButtons.forEach(function (button) {
            let buttonElement = document.getElementById(namespace + button);
            if (buttonElement) {
                buttonElement.disabled = disabled;
            }
            // else button from other portlet
        });

    },
    callDownloadLogFileRequest: function (resourceUrl, namespace, id, downloadFileName) {
        let A = new AUI();
        A.io.request(resourceUrl + '&' + namespace + 'action=downloadLog' + '&' + namespace + 'id=' + id, {
            on: {
                success: function (response, status, xhr) {
                    let responseData = this.get('responseData');
                    if (xhr.status !== 200) {
                        CommonFormsUtil.writeError(namespace, xhr.responseText);
                    } else {
                        CommonFormsUtil.saveAs([responseData], downloadFileName);
                    }
                },
                failure: function (response, status, xhr) {
                    CommonFormsUtil.writeError(namespace, xhr.responseText);
                }
            }
        });
    },
    saveAs: function (data, fileName) {
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
    getRunningProcess: function (namespace) {
        let runningProcess = document.getElementById(namespace + "runningProcess");
        return runningProcess.value;
    },
    setRunningProcess: function (namespace, processId) {
        let runningProcess = document.getElementById(namespace + "runningProcess");
        runningProcess.value = processId;
    },

    getFormName: function (namespace) {
        return namespace + "fm";
    },

    updatePaymentAddress: function (namespace, checked) {
        let paymentFirstNameInput = document.getElementById(namespace + "billing_firstname");
        let paymentLastNameInput = document.getElementById(namespace + "billing_lastname");
        let paymentCompanyInput = document.getElementById(namespace + "billing_company");
        let paymentAddressInput = document.getElementById(namespace + "billing_address");
        let paymentPostCodeInput = document.getElementById(namespace + "billing_postal");
        let paymentCityInput = document.getElementById(namespace + "billing_city");
        let paymentCountryInput = document.getElementById(namespace + "billing_country");
        let paymentEmailInput = document.getElementById(namespace + "billing_email");
        let paymentPhoneInput = document.getElementById(namespace + "billing_phone");

        if (checked) {

            //cache billing info
            paymentFirstNameInput.billing_value = paymentFirstNameInput.value;
            paymentLastNameInput.billing_value = paymentLastNameInput.value;
            paymentCompanyInput.billing_value = paymentCompanyInput.value;
            paymentAddressInput.billing_value = paymentAddressInput.value;
            paymentPostCodeInput.billing_value = paymentPostCodeInput.value;
            paymentCityInput.billing_value = paymentCityInput.value;
            paymentCountryInput.billing_value = paymentCountryInput.value;
            paymentEmailInput.billing_value = paymentEmailInput.value;
            paymentPhoneInput.billing_value = paymentPhoneInput.value;

            //replace billing info with user attributes info
            let company = document.getElementById(namespace + "org_name").value;
            let address = document.getElementById(namespace + "org_address").value;
            let postCode = document.getElementById(namespace + "org_postal").value;
            let city = document.getElementById(namespace + "org_city").value;
            let country = document.getElementById(namespace + "org_country").value;
            let email = document.getElementById(namespace + "email").value;
            let firstName = document.getElementById(namespace + "first_name").value;
            let lastName = document.getElementById(namespace + "last_name").value;
            let phone = document.getElementById(namespace + "org_phone").value;

            paymentFirstNameInput.value = firstName;
            paymentLastNameInput.value = lastName;
            paymentAddressInput.value = address;
            paymentPostCodeInput.value = postCode;
            paymentCityInput.value = city
            paymentCountryInput.value = country;
            paymentEmailInput.value = email;
            paymentCompanyInput.value = company
            paymentPhoneInput.value = phone;

            paymentCompanyInput.disabled = true;
            paymentFirstNameInput.disabled = true;
            paymentLastNameInput.disabled = true;
            paymentAddressInput.disabled = true;
            paymentPostCodeInput.disabled = true;
            paymentCityInput.disabled = true;
            paymentCountryInput.disabled = true;
            // paymentEmailInput.prop('disabled', true);

        } else {
            //restore billing info
            let firstName = paymentFirstNameInput.billing_value;
            let lastName = paymentLastNameInput.billing_value
            let company = paymentCompanyInput.billing_value
            let address = paymentAddressInput.billing_value
            let postCode = paymentPostCodeInput.billing_value
            let city = paymentCityInput.billing_value
            let country = paymentCountryInput.billing_value
            let email = paymentEmailInput.billing_value
            let phone = paymentPhoneInput.billing_value

            paymentCompanyInput.value = company;
            paymentFirstNameInput.value = firstName;
            paymentLastNameInput.value = lastName;
            paymentAddressInput.value = address;
            paymentPostCodeInput.value = postCode;
            paymentCityInput.value = city;
            paymentCountryInput.value = country;
            paymentEmailInput.value = email;
            paymentPhoneInput.value = phone;

            paymentCompanyInput.disabled = false;
            paymentFirstNameInput.disabled = false;
            paymentLastNameInput.disabled = false;
            paymentAddressInput.disabled = false;
            paymentPostCodeInput.disabled = false;
            paymentCityInput.disabled = false;
            paymentCountryInput.disabled = false;
            // paymentEmailInput.prop('disabled', false);

        }
    },

    getRadioButtonsSelection: function (namespace, name) {
        let radioButtons = document.getElementsByName(namespace + name);

        let selectedValue;
        Array.from(radioButtons).forEach(function (radioButton) {
            if (radioButton.checked) {
                selectedValue = radioButton.value;
            }
        });
        return selectedValue;
    },

    removeArticleFromUrl: function (url, name, articleId) {
        let urlParts = url.split('?');
        if (urlParts.length < 2) return url;
        let newUrl = [];
        newUrl.push(urlParts[0]);
        let queryParts = urlParts[1].split('&');
        let newParts = [];
        for (let queryPart of queryParts) {
            if (queryPart.startsWith(name)) {
                newParts.push(queryPart.replace(articleId, ''));
            } else {
                newParts.push(queryPart);
            }
        }
        newUrl.push(newParts.join('&'));
        return newUrl.join('?');
    },

    checkStep: function (form, requiredStep) {
        return (this.getCurrentStep(form) === requiredStep);
    },

    getCurrentStep: function (form) {
        // Find the form by name
        var formElement = document.querySelector('form[name="' + form + '"]');
        if (formElement) {
            // Find the closest ancestor with class 'bs-stepper'
            var stepper = formElement.closest('.bs-stepper');
            if (stepper) {
                // Find the child with classes 'tab-pane active'
                var activePane = stepper.querySelector('.tab-pane.active');
                if (activePane) {
                    var currentStep = activePane.id.charAt(activePane.id.length - 1);
                    return Number(currentStep);
                }
            }
        }

    }
};
