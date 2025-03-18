
RegistrationFormsUtil = {

    validateFirstStep: function (FIRST_STEP_ERROR_MESSAGE, FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING) {
        let isParentSelectionValid = false;
        let isChildSelectionValid = true;
        let registrations = document.getElementsByClassName('registration-item');
        [...registrations].forEach(function(registration) {
            let parentChecked = registration.getElementsByClassName("parent-registration")[0].checked;
            if (parentChecked){
                isParentSelectionValid = true;
            }
            let children = registration.getElementsByClassName('child-registration');
            [...children].forEach(function(child) {
                if (child.checked && !parentChecked){
                    isChildSelectionValid = false;
                }
            })
        });
        if (!isParentSelectionValid){
            return FIRST_STEP_ERROR_MESSAGE;
        }
        if(!isChildSelectionValid){
            return FIRST_STEP_ERROR_MESSAGE_PARENT_MISSING;
        }
        return null;
    },

    checkSelection : function(namespace) {
        let parents = document.getElementsByClassName('parent-registration');

        let priceEnabled = false;
        let courseTermsEnabled = false;
        [...parents].forEach( function(parent) {
            if (parent.checked){

                if ( parseFloat(parent.getAttribute('data-price')) > 0) {
                    priceEnabled = true;
                }

                if ( parent.getAttribute('course') === "true" ) {
                    courseTermsEnabled = true;
                }
            }

        });

        let children = document.getElementsByClassName('child-registration');
        [...children].forEach(function( child ) {
            if (child.checked){
                if (parseFloat(child.getAttribute('data-price')) > 0){
                    priceEnabled = true;
                }
                if ( child.getAttribute('course') === "true" ) {
                    courseTermsEnabled = true;
                }
            }
        });

        let step3 = $(document.getElementById(namespace + 'nav-stepper-step-3'));
        if (priceEnabled){
            step3.removeClass('disabled'); //remove
        } else {
            step3.addClass('disabled'); //add;
        }

        let courseCond = $(document.getElementById(namespace + 'course-conditions-div'));
        if (courseTermsEnabled){
            courseCond[0].hidden = false;
            $('input[name="' + namespace + 'course_conditions"]')[0].disabled = false;
        } else {
            courseCond[0].hidden = true;
            $('input[name="' + namespace + 'course_conditions"]')[0].disabled = true;
        }
    },

    updatePrice : function (namespace, element) {


    },

    updateTable : function(namespace, element) {
        let articleId = element.dataset.articleid;

        let table = document.getElementById(namespace + 'users_table_' + articleId);

        let userCount = parseInt(element.value);
        let rows = table.rows.length - 1; // exclude the header row
        if (userCount < rows){
            table.deleteRow(table.rows.length - 1);
        } else if (userCount > rows){
            let newRowsCount = userCount - rows;
            for (let i = 0; i < newRowsCount; i++) {
                let newRow = table.insertRow(table.rows.length);
                this.copyTableRow(table.rows[1], newRow, rows)
                // this.updateValidator(namespace, rows, orderItemId)
            }

        }
    },

    //used in cart-overview.jsp
    copyTableRow: function (oldRow, newRow, rows){
        newRow.innerHTML = oldRow.innerHTML;

        for (let i = 0; i < newRow.cells.length; i++) {
            let div = newRow.cells[i].children[0];
            let input = div.children[0];
            input.value = ""
            input.id = input.id + '_' + rows
            input.name = input.id
            //remove any existing errors
            for (let c = 1 ; c < div.children.length; c++){
                div.removeChild(div.children[c]);
            }
            div.classList.remove('has-error')
        }

    }

}
