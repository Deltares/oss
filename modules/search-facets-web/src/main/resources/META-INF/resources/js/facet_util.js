AUI.add(
    'deltares-search-facet-util',
    function () {
        Liferay.namespace('Deltares').FacetUtil = {

    initializeDates: function (namespace, start, end) {
        let sp = new URLSearchParams(location.search);
        let updated = false;
        if (start) {
            updated = this.setTerm(sp, 'startDate', start)
        }
        if (end) {
            updated = updated || this.setTerm(sp, 'endDate', start)
        }
        if (updated){
            window.location.href = this.toUrl(location, sp);
        }

    },
    removeTerm: function (sp, term) {
        if (sp.has(term)){
            sp.delete(term)
            return true
        }
        return false
    },
    setTerm: function (sp, term, value) {
        let exist_val = sp.get(term);
        if (exist_val && exist_val === value){
            return false;
        } else {
            sp.set(term, value)
            return true;
        }
    },
    toUrl: function (location, sp) {
        return location.origin + location.pathname + '?' + sp.toString();

    },
    updateQueryString: function (namespace, name) {

        let selector = 'input[name$="' + namespace + 'startDate"]';
        let input = document.querySelector(selector);
        let startDate = input ? input.value : '';

        selector = 'input[name$="' + namespace + 'endDate"]';
        input = document.querySelector(selector);
        let endDate = input ? input.value : '';

        let sp = new URLSearchParams(location.search);
        let updated = false;

        if (startDate !== undefined) {
            if (startDate === '') {
                updated = updated || this.removeTerm(sp ,'startDate')
            } else {
                updated = updated || this.setTerm(sp ,'startDate', startDate)
            }
        }
        if (endDate !== undefined) {
            if (endDate === '') {
                updated = updated || this.removeTerm(sp ,'endDate')
            } else {
                updated = updated || this.setTerm(sp ,'endDate', endDate)
            }
        }
        selector = 'select[name$="' + namespace + "selection-facet-" + name + '"]';
        input = document.querySelector(selector);
        let selection = input ? input.value : '';
        if (selection !== undefined) {
            if (selection === 'undefined') {
                updated = updated || this.removeTerm(sp ,name)
            } else {
                updated = updated || this.setTerm(sp ,name, selection)
            }
        }
        selector = 'input[name$="' + namespace + 'showPast"]';
        input = document.querySelector(selector);
        let showPastElement = input ? input.value : undefined;
        if (showPastElement !== undefined){
            if (showPastElement) {
                updated = updated || this.setTerm(sp ,'showPast', showPastElement)
            } else {
                updated = updated || this.removeTerm(sp ,'showPast')
            }
        }
        selector = 'input[name$="' + namespace + 'hasPresentations"]';
        input = document.querySelector(selector);
        let presentationElement = input ? input.value : undefined;
        if (presentationElement !== undefined){
            if (presentationElement) {
                updated = updated || this.setTerm(sp ,'hasPresentations', presentationElement)
            } else {
                updated = updated || this.removeTerm(sp ,'hasPresentations')
            }
        }
        selector = 'select[name$="' + namespace + "checkbox-facet-" + name + '"]';
        input = document.querySelector(selector);
        selection = input ? input.value : undefined;
        if (selection !== undefined) {
            if (selection === 'undefined') {
                updated = updated || this.removeTerm(sp ,name)
            } else {
                updated = updated || this.setTerm(sp ,name, selection)
            }
        }
        if (updated){
            window.location.href = this.toUrl(location, sp);
        }
        //var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path', 'q', 'test');
    }
        };
    },
    '',
    {
        requires: ['liferay-search-facet-util']
    }
);