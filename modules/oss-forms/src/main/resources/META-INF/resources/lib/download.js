DownloadFormsUtil = {

    validateFirstStep: function () {
        let downloads = document.getElementsByClassName('download');
        let isFirstStepValid = false;
        [...downloads].forEach(function(download) {
            if (download.checked) {
                isFirstStepValid = true;
            }
        });
        return isFirstStepValid;
    },

    checkSelection : function(namespace) {

        let downloads = document.getElementsByClassName('download');

        let userInfoEnabled = false;
        let subscriptionEnabled = false;
        let billingInfoEnabled = false;
        let termsEnabled = false;
        let licensesEnabled = false;
        [...downloads].forEach(function(download) {

            if (download.checked){

                if ( download.getAttribute('userinfo') === "true" ) {
                    userInfoEnabled = true;
                }

                if ( download.getAttribute('subscription') === "true" ) {
                    subscriptionEnabled = true;
                }
                if ( download.getAttribute('billinginfo') === "true" ) {
                    billingInfoEnabled = true;
                }

                if ( download.getAttribute('terms') === "true" ) {
                    termsEnabled = true;
                }

                if ( download.getAttribute('licenseinfo') === "true" ) {
                    licensesEnabled = true;
                }
            }

        });
        let step2 = $(document.getElementById(namespace + 'nav-stepper-step-2'));
        if (userInfoEnabled){
            step2.removeClass('disabled'); //remove
        } else {
            step2.addClass('disabled'); //add;
        }

        let step3 = $(document.getElementById(namespace + 'nav-stepper-step-3'));
        if (billingInfoEnabled){
            step3.removeClass('disabled'); //remove
        } else {
            step3.addClass('disabled'); //add;
        }

        let step3b = $(document.getElementById(namespace + 'nav-stepper-step-3b'));
        if (licensesEnabled){
            step3b.removeClass('disabled'); //remove
        } else {
            step3b.addClass('disabled'); //add;
        }

        let step4 = $(document.getElementById(namespace + 'nav-stepper-step-4'));
        if (subscriptionEnabled){
            step4.removeClass('disabled'); //remove
        } else {
            step4.addClass('disabled'); //add;
        }

        // let courseCond = $(document.getElementById(namespace + 'course-conditions-div'));
        let step5 = $(document.getElementById(namespace + 'nav-stepper-step-5'));
        if (termsEnabled){
            step5.removeClass('disabled'); //remove
        } else {
            step5.addClass('disabled'); //add
        }
    },




}
