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

        let startDate = $('input[name$="' + namespace + 'startDate"]').val();
        let endDate = $('input[name$="' + namespace + 'endDate"]').val();

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
        let selection = $('select[name$="' + namespace + "selection-facet-" + name + '"]').val();
        if (selection !== undefined) {
            if (selection === 'undefined') {
                updated = updated || this.removeTerm(sp ,name)
            } else {
                updated = updated || this.setTerm(sp ,name, selection)
            }
        }
        let showPastElement = $('input[name$="' + namespace + 'showPast"]');
        if (showPastElement.val() !== undefined) {
            if (showPastElement[0].checked) {
                updated = updated || this.setTerm(sp ,'showPast', showPastElement[0].checked)
            } else {
                updated = updated || this.removeTerm(sp ,'showPast')
            }
        }
        let presentationElement = $('input[name$="' + namespace + 'hasPresentations"]');
        if (presentationElement.val() !== undefined) {
            if (presentationElement[0].checked) {
                updated = updated || this.setTerm(sp ,'hasPresentations', presentationElement[0].checked)
            } else {
                updated = updated || this.removeTerm(sp ,'hasPresentations')
            }
        }
        selection = $('select[name$="' + namespace + "checkbox-facet-" + name + '"]').val();
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