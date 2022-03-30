DownloadFormsUtil = {

    validateFirstStep: function () {
        let isFirstStepValid = document.getElementsByClassName('download')[0].checked;
        if (!isFirstStepValid) {
            return isFirstStepValid;
        }
        return isFirstStepValid;
    },

    checkSelection : function(namespace) {

        let downloads = document.getElementsByClassName('download');

        let userInfoEnabled = false;
        let subscriptionEnabled = false;
        let billingInfoEnabled = false;
        let termsEnabled = false;
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

        let step4 = $(document.getElementById(namespace + 'nav-stepper-step-4'));
        if (subscriptionEnabled){
            step4.removeClass('disabled'); //remove
        } else {
            step4.addClass('disabled'); //add;
        }

        let courseCond = $(document.getElementById(namespace + 'course-conditions-div'));
        if (termsEnabled){
            courseCond[0].hidden = false;
            $('input[name="' + namespace + 'course_conditions"]')[0].disabled = false;
        } else {
            courseCond[0].hidden = true;
            $('input[name="' + namespace + 'course_conditions"]')[0].disabled = true;
        }
    },




}
