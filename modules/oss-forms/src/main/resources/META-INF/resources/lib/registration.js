
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

    accountSelectionChanged: function (namespace, accountSelection, paramName) {

        const accountSelectElm = accountSelection;
        const paramNameElem = document.getElementById(namespace + paramName);

        if (accountSelectElm && paramNameElem) {
            const accountEntryId = accountSelectElm.value;
            this.updateAddressFields(namespace, accountSelectElm);
            paramNameElem.value = accountEntryId;
        }
    },

    updateAddressFields: function (namespace, selectedElement){

        let selectedIndex = selectedElement.selectedIndex
        let clearFields = !selectedIndex || selectedIndex === '0';

        if (selectedElement) {
            const city = document.getElementById(namespace + "org_city");
            const country = document.getElementById(namespace + 'org_country');
            const regionIdSelect = document.getElementById(namespace + 'org_region');
            const name = document.getElementById(namespace + 'org_name');
            const phoneNumber = document.getElementById(namespace + 'org_phone');
            const website = document.getElementById(namespace + 'org_website');
            const street1 = document.getElementById(namespace + 'org_address');
            const zip = document.getElementById(namespace + 'org_postal');

            if (
                city &&
                country &&
                regionIdSelect &&
                name &&
                phoneNumber &&
                street1 &&
                zip
            ) {
                const selectedOption =
                    selectedElement.options[selectedIndex];

                let disabled = selectedOption.dataset.canedit === "false";

                city.value = clearFields ? "" : selectedOption.dataset.city;
                country.value = clearFields ? 0 : selectedOption.dataset.country;
                name.value = clearFields ? "" : selectedOption.dataset.name;
                phoneNumber.value = clearFields ? "" : selectedOption.dataset.phoneNumber;
                street1.value = clearFields ? "" : selectedOption.dataset['street-1'];
                zip.value = clearFields ? "" : selectedOption.dataset.zip;
                regionIdSelect.value = clearFields ? "" : selectedOption.dataset.regionid;

                city.disabled = disabled;
                country.disabled = disabled;
                name.disabled = disabled;
                phoneNumber.disabled = disabled;
                street1.disabled = disabled;
                zip.disabled = disabled;
                regionIdSelect.disabled = disabled;

                if (website) {
                    website.value = clearFields ? "" : selectedOption.dataset.website;
                    website.disabled = disabled;
                }

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
        let quantityList = document.getElementsByClassName("parent-registration-quantity");

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
