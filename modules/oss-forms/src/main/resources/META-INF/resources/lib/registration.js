
RegistrationFormsUtil = {

    //Load countries on page load, update regions when country changes
    loadCountrySelection: function (namespace, selectedCountryId, selectedRegionId, companyId){

        if (!companyId){
            companyId = Liferay.ThemeDisplay.getCompanyId();
        }

        Liferay.component(
            namespace + 'countrySelects',
            new Liferay.DynamicSelect([
                {
                    select: namespace + 'org_country',
                    selectData: function (callback) {
                        function injectCountryPlaceholder(list) {
                            const callbackList = [
                                {
                                    countryId: '0',
                                    nameCurrentValue: '- ' + Liferay.Language.get('select-country')
                                }
                            ];
                            list.forEach((listElement) => {
                                callbackList.push(listElement);
                            });
                            callback(callbackList);
                        }

                        Liferay.Service(
                            '/country/get-company-countries',
                            {
                                active: true,
                                companyId:companyId
                            },
                            injectCountryPlaceholder
                        );
                    },
                    selectDesc: 'nameCurrentValue',
                    selectId: 'countryId',
                    selectNullable:'false',
                    selectSort:'true',
                    selectVal: selectedCountryId
                },
                {
                    select: namespace + 'org_region',
                    selectData: function (callback, selectKey) {
                        function injectRegionPlaceholder(list) {
                            const callbackList = [
                                {
                                    regionId: '0',
                                    name: '- ' + Liferay.Language.get('select-region'),
                                    nameCurrentValue:'- ' + Liferay.Language.get('select-region'),
                                }
                            ];

                            list.forEach((listElement) => {
                                callbackList.push(listElement);
                            });

                            callback(callbackList);
                        }

                        Liferay.Service( '/region/get-regions',
                            {
                                active: true,
                                countryId: Number(selectKey)
                            }, injectRegionPlaceholder
                        );
                    },
                    selectDesc: 'name',
                    selectId: 'regionId',
                    selectNullable: 'false',
                    selectVal: selectedRegionId
                }
            ])
        );
    },

    addressSelectionChanged: function (namespace, addressSelection, paramName) {

        const addressSelectElm = addressSelection;
        const paramNameElem = document.getElementById(namespace + paramName);

        if (addressSelectElm && paramNameElem) {
            const addressEntryId = addressSelectElm.value;
            this.updateAddressFields(namespace, addressSelectElm);
            paramNameElem.value = addressEntryId;
        }
    },

    accountSelectionChanged: function (namespace, accountSelection, paramName) {

        const accountSelectElm = accountSelection;
        const paramNameElem = document.getElementById(namespace + paramName);

        if (accountSelectElm && paramNameElem) {
            const accountEntryId = accountSelectElm.value;
            this.updateAccountFields(namespace, accountSelectElm);
            this.updateAddressFields(namespace, accountSelectElm);
            paramNameElem.value = accountEntryId;
        }
    },

    updateAccountFields: function (namespace, selectedElement){

        let selectedIndex = selectedElement.selectedIndex
        let clearFields = !selectedIndex || selectedIndex === '0';

        if (selectedElement) {
            const vat = document.getElementById(namespace + "org_vat");
            const externalRef = document.getElementById(namespace + 'companyRegistrationId');
            const regionIdSelect = document.getElementById(namespace + 'org_region');
            const website = document.getElementById(namespace + 'website');

            if (
                vat &&
                externalRef &&
                website
            ) {
                const selectedOption =
                    selectedElement.options[selectedIndex];

                let disabled = selectedOption.dataset.canedit === "false";

                vat.value = clearFields ? "" : selectedOption.dataset.vat;
                externalRef.value = clearFields ? "" : selectedOption.dataset.companyRef;
                website.value = clearFields ? "" : selectedOption.dataset.website;

                vat.disabled = disabled;
                externalRef.disabled = disabled;
                website.disabled = disabled;

                Liferay.Service(
                    '/region/get-regions',
                    {
                        active: true,
                        countryId: parseInt(selectedOption.dataset.country, 10),
                    },
                    function setUIOnlyInputRegionName(regions) {

                        regionIdSelect.options.length = 0;
                        regionIdSelect.append(new Option('- ' + Liferay.Language.get('select-region'), 0));

                        if (regions){
                            regions.forEach((listElement) => {
                                regionIdSelect.append(new Option(listElement.name, listElement.regionId));
                                if (listElement.regionId === selectedOption.dataset.region){
                                    regionIdSelect.value = listElement.regionId
                                }
                            });
                        }
                    }
                );

            }
        }
    },

    updateAddressFields: function (namespace, selectedElement){

        let selectedIndex = selectedElement.selectedIndex
        let clearFields = !selectedIndex || selectedIndex === '0';

        if (selectedElement) {
            const city = document.getElementById(namespace + "org_city");
            const country = document.getElementById(namespace + 'org_country');
            const regionIdSelect = document.getElementById(namespace + 'org_region');
            const orgName = document.getElementById(namespace + 'org_name');
            const orgAddressName = document.getElementById(namespace + 'org_address_name');
            const phoneNumber = document.getElementById(namespace + 'org_phone');
            const street1 = document.getElementById(namespace + 'org_address');
            const zip = document.getElementById(namespace + 'org_postal');

            if (
                city &&
                country &&
                regionIdSelect &&
                orgName &&
                orgAddressName &&
                phoneNumber &&
                street1 &&
                zip
            ) {
                const selectedOption =
                    selectedElement.options[selectedIndex];

                let disabled = selectedOption.dataset.canedit === "false";

                city.value = clearFields ? "" : selectedOption.dataset.city;
                country.value = clearFields ? 0 : selectedOption.dataset.country;
                orgName.value = clearFields ? "" : selectedOption.dataset['orgName'];
                orgAddressName.value = clearFields ? "" : selectedOption.dataset['addressName'];
                phoneNumber.value = clearFields ? "" : selectedOption.dataset['phoneNumber'];
                street1.value = clearFields ? "" : selectedOption.dataset['street-1'];
                zip.value = clearFields ? "" : selectedOption.dataset.zip;
                regionIdSelect.value = clearFields ? "" : selectedOption.dataset.regionid;

                city.disabled = disabled;
                country.disabled = disabled;
                orgName.disabled = disabled;
                phoneNumber.disabled = disabled;
                street1.disabled = disabled;
                zip.disabled = disabled;
                regionIdSelect.disabled = disabled;
                orgAddressName.disabled = disabled;

                Liferay.Service(
                    '/region/get-regions',
                    {
                        active: true,
                        countryId: parseInt(selectedOption.dataset.country, 10),
                    },
                    function setUIOnlyInputRegionName(regions) {

                        regionIdSelect.options.length = 0;
                        regionIdSelect.append(new Option('- ' + Liferay.Language.get('select-region'), 0));

                        if (regions){
                            regions.forEach((listElement) => {
                                regionIdSelect.append(new Option(listElement.name, listElement.regionId));
                                if (listElement.regionId === selectedOption.dataset.region){
                                    regionIdSelect.value = listElement.regionId
                                }
                            });
                        }

                    }
                );

            }
        }
    },

    updatePrice : function (namespace, element) {
        let quantityList = document.getElementsByClassName("registration-quantity");

        let priceSubTotal = 0;
        let vatTotal = 0;
        let priceTotal = 0;
        let currency;
        for (let i = 0; i < quantityList.length; i++){

            let quantityElm = quantityList.item(i);
            let quantity = quantityElm.value;
            let vat = quantityElm.dataset.vat;
            let price = quantityElm.dataset.price;
            currency = quantityElm.dataset.currency;

            let priceItem = (price * quantity)
            let vatItem = priceItem * vat * 0.01;
            priceSubTotal += priceItem;
            vatTotal += vatItem;
        }
        priceTotal = priceSubTotal + vatTotal;

        document.getElementById("registrationform.price.subtotal").innerHTML = currency + ' ' + Number(priceSubTotal).toFixed(2)
        document.getElementById("registrationform.price.tax").innerHTML = currency + ' ' + Number(vatTotal).toFixed(2)
        document.getElementById("registrationform.price.total").innerHTML = currency + ' ' + Number(priceTotal).toFixed(2)

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
                newRow.innerHTML = table.rows[1].innerHTML;
                this.copyTableRow(table.rows[1], newRow, '_' + rows, false)
            }

        }
    },

    copyTable : function(namespace, srcArticleId, destArticleId) {
        document.getElementById(namespace + 'count_registration_' + destArticleId).value =
            document.getElementById(namespace + 'count_registration_' + srcArticleId).value;

        let srcTable = document.getElementById(namespace + 'users_table_' + srcArticleId);
        let destTable = document.getElementById(namespace + 'users_table_' + destArticleId);
        for (i = 1; i < srcTable.rows.length; i++) {
            let destRow;
            let postfix;
            if (i >= destTable.rows.length) {
                destRow = destTable.insertRow(destTable.rows.length);
                destRow.innerHTML = destTable.rows[1].innerHTML;
                postfix = '_' + (i - 1);
            } else {
                destRow = destTable.rows[i];
                postfix = "";
            }
            this.copyTableRow(srcTable.rows[i], destRow, postfix, true);
        }
    },

    //used in user-registration.jsp
    copyTableRow: function (oldRow, newRow, postfix, copyValues) {
        for (let i = 0; i < newRow.cells.length; i++) {
            let div = newRow.cells[i].children[0];
            let oldDiv = oldRow.cells[i].children[0];
            let input = div.querySelector('input, textarea');
            let oldInput = oldDiv.querySelector('input, textarea');

            input.value = copyValues ? oldInput.value : "";
            input.id = input.id + postfix;
            input.name = input.id;
            //remove any existing errors
            for (let c = 1 ; c < div.children.length; c++){
                div.removeChild(div.children[c]);
            }
            div.classList.remove('has-error')
        }

    },

    updateBadge : function(namespace) {
        let showTitle = CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_title_setting");
        let nameSetting =CommonFormsUtil.getRadioButtonsSelection(namespace, "badge_name_setting");
        let badgeInfo = document.getElementById( namespace + "badge-info");


        let firstName = badgeInfo.dataset.firstname;
        let lastName = badgeInfo.dataset.lastname;
        let initials = badgeInfo.dataset.initials;
        let salutation = badgeInfo.dataset.salutation;
        let title = '';

        if (showTitle === 'yes') {
            title += salutation + ' ';
        }

        if (nameSetting === 'name') {
            title += firstName;
        } else if (nameSetting === 'initials') {
            title += initials;
        } else if (nameSetting === 'both') {
            title += initials + ' (' + firstName + ')';
        }

        title += ' ' + lastName;

        document.getElementById('badge-title').textContent = title;
    }

}
