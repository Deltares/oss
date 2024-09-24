DsdRegistrationFormsUtil = {

    attributes: {},
    accounts: [],
    selectedEntryId: -1,

    addressSelectionChanged: function (namespace, addressSelection){
        let street;
        let postal;
        let city;
        let country;
        let phone;
        let addressId = addressSelection.value;

        let accountSelected = addressId !== "0" && this.selectedEntryId !== "0";
        if (accountSelected){
            let account = this.accounts.find(acc => acc['accountEntryId'] === this.selectedEntryId);
            let address = account["addresses"].find(addr => addr['addressId'] === addressId);
            street = address["org_address"];
            postal = address["org_postal"];
            city = address["org_city"];
            country = address["org_country"];
            phone = address["org_phone"];
        } else {
            street = document.getElementById(namespace + "org_address").value;
            postal = document.getElementById(namespace + "org_postal").value;
            city = document.getElementById(namespace + "org_city").value;
            country = document.getElementById(namespace + "org_country").value;
            phone = document.getElementById(namespace + "org_phone").value;
            document.getElementById(namespace  + "org_vat").value = "";
            document.getElementById(namespace + "org_external_reference_code").value = "";
        }

        document.getElementById(namespace + "billing_org_address").value = street;
        document.getElementById(namespace + "billing_org_postal").value = postal;
        document.getElementById(namespace + "billing_org_city").value = city;
        document.getElementById(namespace + "billing_org_country").value = country;
        document.getElementById(namespace + "billing_org_phone").value = phone;

        //not allowed to update existing add
        document.getElementById(namespace + "billing_org_address").disabled = addressSelection.selectedIndex > 0;
        document.getElementById(namespace + "billing_org_postal").disabled = addressSelection.selectedIndex > 0;
        document.getElementById(namespace + "billing_org_city").disabled = addressSelection.selectedIndex > 0;
        document.getElementById(namespace + "billing_org_country").disabled = addressSelection.selectedIndex > 0;
        document.getElementById(namespace + "billing_org_phone").disabled = addressSelection.selectedIndex > 0;
        //additional: only allow editing of writing own addressIndex
        document.getElementById(namespace + "billing_org_name").disabled = addressSelection.selectedIndex > 0;

        document.getElementById(namespace + "billing_org_vat").disabled = !accountSelected;
        document.getElementById(namespace + "billing_org_external_reference_code").disabled = !accountSelected;
    },

    accountSelectionChanged: function (namespace, orgSelection){

        let name = "";
        let website = "";
        let street = "";
        let postal = "";
        let city = "";
        let country = "NL";
        let phone = "";
        let domain = "";
        let disabled = false;
        this.selectedEntryId = orgSelection.value;
        if (this.selectedEntryId !== "0"){
            let account = this.accounts.find(acc => acc['accountEntryId'] === this.selectedEntryId);
            name = account["org_name"];
            website = account["org_website"];
            domain = account["domains"];

            disabled = account["type"] === 'business';
            let address = DsdRegistrationFormsUtil.getDefaultBillingAddress(account);
            street = address["org_address"] ? address["org_address"]: "";
            postal = address["org_postal"] ? address["org_postal"] : "";
            city = address["org_city"] ? address["org_city"] : "";
            country = address["org_country"] ? address["org_country"] : "";
            phone = address["org_phone"] ? address["org_phone"] : "";

        } else if (this.attributes) {
            name = this.attributes["org_name"]?this.attributes["org_name"]:"";
            website = this.attributes["org_website"]?this.attributes["org_website"]:"";
            street = this.attributes["org_address"]?this.attributes["org_address"]:"";
            postal = this.attributes["org_postal"]?this.attributes["org_postal"]:"";
            city = this.attributes["org_city"]?this.attributes["org_city"]:"";
            country = this.attributes["org_country"]?this.attributes["org_country"]:"";
            phone = this.attributes["org_phone"]?this.attributes["org_phone"]:"";

        } else {
            return;
        }

        document.getElementById(namespace + "org_name").value = name;
        document.getElementById(namespace + "org_website").value = website;
        document.getElementById(namespace + "org_address").value = street;
        document.getElementById(namespace + "org_postal").value = postal;
        document.getElementById(namespace + "org_city").value = city;
        document.getElementById(namespace + "org_country").value = country;
        document.getElementById(namespace + "org_phone").value = phone;
        document.getElementById(namespace + "org_domains").value = domain;

        document.getElementById(namespace + "org_name").disabled = disabled;
        document.getElementById(namespace + "org_website").disabled = disabled;
        document.getElementById(namespace + "org_address").disabled = disabled;
        document.getElementById(namespace + "org_postal").disabled = disabled;
        document.getElementById(namespace + "org_city").disabled = disabled;
        document.getElementById(namespace + "org_country").disabled = disabled;
        document.getElementById(namespace + "org_phone").disabled = disabled;
    },

    getDefaultBillingAddress : function (account){
        let defaultAddress = { };
        if (account.addresses) {
            account.addresses.forEach(address => {if (address.default) defaultAddress = address} );
        }
        return defaultAddress;
    },

    loadAddressList: function (namespace, accountAddresses) {

        let address_selection = document.getElementById(namespace + "select_address")
        let options = address_selection.options;
        //remove address linked to old account selection
        let removeOptions = []
        for (let i = 1 ; i < options.length; i++) {
            removeOptions.push(options[i]);
        }
        for (const removeOption of removeOptions) {
            address_selection.removeChild(removeOption)
        }
        //add new addresses linked to current account selection
        for (let i = 0; i < accountAddresses.length; i++) {
            let option = document.createElement("option");
            option.value = accountAddresses[i]['addressId'];
            option.label = accountAddresses[i]['name']
            address_selection.add(option)
            if (accountAddresses[i].default) address_selection.selectedIndex = i + 1
        }
    },

    updateValidator : function (namespace, rows) {
        //copy validator rules to the new row fields
        var myFormValidator = Liferay.Form.get(namespace + 'fm').formValidator;
        var _ruleData = myFormValidator.get('fieldStrings');
        _ruleData[namespace + 'jobTitles' + rows] = _ruleData[namespace + 'jobTitles']
        _ruleData[namespace + 'firstName' + rows] = _ruleData[namespace + 'firstName']
        _ruleData[namespace + 'lastName' + rows] = _ruleData[namespace + 'lastName']
        _ruleData[namespace + 'email' + rows] = _ruleData[namespace + 'email']

        var _rules = myFormValidator.get('rules');
        _rules[namespace + 'jobTitles' + rows] = _rules[namespace + 'jobTitles']
        _rules[namespace + 'firstName' + rows] = _rules[namespace + 'firstName']
        _rules[namespace + 'lastName' + rows] = _rules[namespace + 'lastName']
        _rules[namespace + 'email' + rows] = _rules[namespace + 'email']

    },
    checkEmailDomain : function (namespace, val, fldNode) {
        let domains = document.getElementById(namespace + "org_domains").value
        if (!domains || domains.length === 0) return true; //no domains configured
        let isValid = false;
        if (val !== "" && val.includes('@')){
            let domain = val.split("@")[1];
            isValid = domains.includes(domain);
        }

        if (isValid) return true;

        var myFormValidator = Liferay.Form.get(namespace + 'fm').formValidator;
        var _ruleData = myFormValidator.get('fieldStrings')[fldNode.get('name')];
        if (!_ruleData.original_message){
            _ruleData.original_message = _ruleData.email_custom_1
        }
        _ruleData.email_custom_1 = _ruleData.original_message + domains;
        return false;
    },

    //used in step2.jsp
    updateTable : function(namespace, element) {
        let articleId = element.getAttribute('data-article-id');
        let table = document.getElementById(namespace + 'users_table_' + articleId);

        let userCount = parseInt(element.value);

        let rows = table.rows.length - 1; // exclude the header row
        if (userCount < rows){
            table.deleteRow(table.rows.length - 1);
        } else if (userCount > rows){
            let newRow = table.insertRow(table.rows.length);
            this.copyTableRow(table.rows[1], newRow, rows)
            this.updateValidator(namespace, rows)
        }

        DsdRegistrationFormsUtil.updateRowPrice(namespace, element);
        DsdRegistrationFormsUtil.updateTotalPrice(namespace);
    },

    copyTableRow: function (oldRow, newRow, rows){
        newRow.innerHTML = oldRow.innerHTML;

        for (let i = 0; i < newRow.cells.length; i++) {
            let div = newRow.cells[i].children[0];
            let input = div.children[0];
            input.value = ""
            input.id = input.id + rows
            input.name = input.id
            //remove any existing errors
            for (let c = 1 ; c < div.children.length; c++){
                div.removeChild(div.children[c]);
            }
            div.classList.remove('has-error')
        }
    },

    updateRowPrice: function(namespace, element) {
        let articleId = element.getAttribute('data-article-id');
        let totalEl = document.getElementById(namespace + 'price_parent_registration_' + articleId);

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

        this.setActiveStateStep3(namespace, subTotal);
    },

    setActiveStateStep3: function (namespace, price){

        let step3 = document.getElementById(namespace + 'nav-stepper-step-3');
        if (price > 0){
            step3.classList.remove('disabled')
        } else {
            step3.classList.add('disabled')
        }
    },
    activateStep1: function(namespace){

        let orgSelection = document.getElementById(namespace + "select_organization");

        if (DsdRegistrationFormsUtil.accounts.length > 0){
            orgSelection.selectedIndex = 1
        } else {
            orgSelection.selectedIndex = 0
        }

        //initialize the selection
        DsdRegistrationFormsUtil.accountSelectionChanged(namespace, document.getElementById(namespace + "select_organization"));
    },

    activateStep2: function (namespace){
        DsdRegistrationFormsUtil.checkSelection(namespace)
        DsdRegistrationFormsUtil.updateTotalPrice(namespace);
    },

    activateStep3: function (namespace){

        if (this.selectedEntryId !== "0") {
            let account = this.accounts.find(acc => acc['accountEntryId'] === this.selectedEntryId);
            //update addresses in step 3
            if (account["addresses"] !== undefined) {
                DsdRegistrationFormsUtil.loadAddressList(namespace, account["addresses"]);
            }

            if (account["type"] === 'person') {
                //Only personal accounts may update their content
                this.copyAddressStep1ToStep3(namespace)
            }
        } else {
            this.copyAddressStep1ToStep3(namespace)
        }

    },

    copyAddressStep1ToStep3: function (namespace){
        document.getElementById(namespace + "billing_org_name").value = document.getElementById(namespace + "org_name").value
        document.getElementById(namespace + "billing_org_address").value = document.getElementById(namespace + "org_address").value
        document.getElementById(namespace + "billing_org_postal").value = document.getElementById(namespace + "org_postal").value
        document.getElementById(namespace + "billing_org_city").value = document.getElementById(namespace + "org_city").value
        document.getElementById(namespace + "billing_org_country").value = document.getElementById(namespace + "org_country").value
        document.getElementById(namespace + "billing_org_phone").value = document.getElementById(namespace + "org_phone").value

    },
    activateNextTab : function (namespace, tabIndex){

        switch (tabIndex){
            case 0:
                DsdRegistrationFormsUtil.activateStep1(namespace);
                break;
            case 1:
                DsdRegistrationFormsUtil.activateStep2(namespace);
                break;
            case 2:
                DsdRegistrationFormsUtil.activateStep3(namespace);
                break;
            default:
        }

    },

    checkSelection : function(namespace) {
        let parents = document.getElementsByClassName('parent-registration');

        let priceEnabled = false;
        let courseTermsEnabled = false;
        [...parents].forEach( function(parent) {
            if ( parseFloat(parent.getAttribute('data-price')) > 0) {
                priceEnabled = true;
            }

            if ( parent.getAttribute('course') === "true" ) {
                courseTermsEnabled = true;
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
    }

}
