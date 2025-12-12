if (!window.DsdRegistrationFormsUtil) {
    window.DsdRegistrationFormsUtil = {

        validateFirstStep: function (FIRST_STEP_ERROR_MESSAGE, FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING) {
            let isParentSelectionValid = false;
            let isChildSelectionValid = true;
            let registrations = document.getElementsByClassName('registration-item');
            Array.from(registrations).forEach(function (registration) {
                let parentChecked = registration.getElementsByClassName("parent-registration")[0].checked;
                if (parentChecked) {
                    isParentSelectionValid = true;
                }
                let children = registration.getElementsByClassName('child-registration');
                Array.from(children).forEach(function (child) {
                    if (child.checked && !parentChecked) {
                        isChildSelectionValid = false;
                    }
                })
            });
            if (!isParentSelectionValid) {
                return FIRST_STEP_ERROR_MESSAGE;
            }
            if (!isChildSelectionValid) {
                return FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING;
            }
            return null;
        },

        checkSelection: function (namespace) {
            let parents = document.getElementsByClassName('parent-registration');

            let priceEnabled = false;
            let courseTermsEnabled = false;
            Array.from(parents).forEach(function (parent) {
                if (parent.checked) {

                    if (parseFloat(parent.getAttribute('data-price')) > 0) {
                        priceEnabled = true;
                    }

                    if (parent.getAttribute('course') === "true") {
                        courseTermsEnabled = true;
                    }
                }

            });

            let children = document.getElementsByClassName('child-registration');
            Array.from(children).forEach(function (child) {
                if (child.checked) {
                    if (parseFloat(child.getAttribute('data-price')) > 0) {
                        priceEnabled = true;
                    }
                    if (child.getAttribute('course') === "true") {
                        courseTermsEnabled = true;
                    }
                }
            });

            let step3 = document.getElementById(namespace + 'nav-stepper-step-3');
            if (priceEnabled) {
                step3.classList.remove('disabled'); //remove
            } else {
                step3.classList.add('disabled'); //add;
            }

            let courseCond = document.getElementById(namespace + 'course-conditions-div');
            if (courseTermsEnabled) {
                courseCond.hidden = false;
                document.querySelector('input[name="' + namespace + 'course_conditions"]').disabled = false;
            } else {
                courseCond.hidden = true;
                document.querySelector('input[name="' + namespace + 'course_conditions"]').disabled = true;
            }
        },

        updateBadge: function (namespace) {
            let showTitle = CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_title_setting");
            let nameSetting = CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_name_setting");
            let titles = document.getElementById(namespace + "academicTitle").value;
            let firstName = document.getElementById(namespace + "first_name").value;
            let initials = document.getElementById(namespace + "initials").value;
            let lastName = document.getElementById(namespace + "last_name").value;
            // let jobTitle = document.getElementById( namespace + "jobTitle" ).value;
            let title = '';

            if (showTitle === 'yes') {
                title += titles + ' ';
            }

            if (nameSetting === 'name') {
                title += firstName;
            } else if (nameSetting === 'initials') {
                title += initials;
            } else if (nameSetting === 'both') {
                title += initials + ' (' + firstName + ')';
            }

            title += ' ' + lastName;

            document.getElementById('badge-title').innerText = title;
            // document.getElementById('job-title').innerText = jobTitle;
        }

    }
}