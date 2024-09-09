
DsdRegistrationFormsUtil = {

    updateTable : function(namespace, element) {
        let articleId = element.getAttribute('data-article-id');
        let table = document.getElementById(namespace + 'users_table_' + articleId);

        let userCount = parseInt(element.value);

        let rows = table.rows.length - 1; // exclude the header row
        if (userCount < rows){
            table.deleteRow(table.rows.length - 1);
        } else if (userCount > rows){
            let newRow = table.insertRow(table.rows.length);
            newRow.innerHTML = table.rows[1].innerHTML;
        }

        DsdRegistrationFormsUtil.updateRowPrice(namespace, element);
        DsdRegistrationFormsUtil.updateTotalPrice(namespace);
    },

    updateRowPrice: function(namespace, element) {
        let articleId = element.getAttribute('data-article-id');
        let totalEl = document.getElementById(namespace + 'parent_registration_price_' + articleId);

        let basePrice = parseFloat(totalEl.getAttribute('data-price'));
        if (basePrice === 0) return;
        let currency = totalEl.getAttribute('data-currency');
        let userCount = parseInt(element.value);
        totalEl.value = currency + ' ' + (basePrice * userCount).toFixed(2);

    },

    updateTotalPrice : function(namespace) {
        let totolPriceEl = document.getElementsByClassName('parent-registration-price');
        let table = document.getElementById(namespace + 'total_price_table');
        let subTotal = 0;
        let taxTotal = 0;
        let currency = "€";
        [...totolPriceEl].forEach( function(parent) {
            let valueTxt = parent.value;
            let taxPer = parseFloat(parent.attributes['data-tax'].value);
            currency = parent.attributes['data-currency'].value;
            let price =  Number(valueTxt.replace(/[^0-9.-]+/g,""));
            subTotal += price;
            taxTotal+= price * taxPer/100;
        });
        let total = subTotal + taxTotal;

        //subtotal
        table.rows[0].cells[1].innerHTML = currency + ' ' + subTotal.toFixed(2);
        //discount
        table.rows[1].cells[1].innerHTML = currency + ' ' + taxTotal.toFixed(2);
        //total
        table.rows[2].cells[1].innerHTML =  currency + ' ' + total.toFixed(2);
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

    updateBadge : function(namespace) {
        let showTitle = CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_title_setting");
        let nameSetting =CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_name_setting");
        let titles = $(document.getElementById( namespace + "academicTitle")).val();
        let firstName = $(document.getElementById(namespace + "first_name")).val();
        let initials = $(document.getElementById( namespace + "initials")).val();
        let lastName = $(document.getElementById( namespace + "last_name")).val();
        let jobTitle = $(document.getElementById( namespace + "jobTitle" )).val();
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

        $(document.getElementById('badge-title')).text(title);
        $(document.getElementById('job-title')).text(jobTitle);
    },

}
