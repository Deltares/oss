AUI.add(
    'deltares-search-facet-util',
    function () {
        Liferay.namespace('Deltares').FacetUtil = {

    // initializeDates: function (namespace, start, end) {
    //     let sp = new URLSearchParams(location.search);
    //     let updated = false;
    //     if (start) {
    //         updated = this.setTerm(sp, 'startDate', start)
    //         let selector = 'input[name$="' + namespace + 'startDate"]';
    //         let input = document.querySelector(selector);
    //         input.value = start;
    //     }
    //     if (end) {
    //         updated = this.setTerm(sp, 'endDate', end) || updated //order is important. setTerm must be called.
    //         let selector = 'input[name$="' + namespace + 'endDate"]';
    //         let input = document.querySelector(selector);
    //         input.value = end;
    //     }
    //     if (updated){
    //         // window.location.href = this.toUrl(location, sp);
    //     }
    // },
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
    validateDateField: function(namespace) {
        let start = document.querySelector('input[name$="' + namespace + 'startDate"]');
        let startTime = 0;
        if (start.value){
            startTime = this.parseDate(start.value, "dd-MM-yyyy", "-").valueOf();
        }
        let end = document.querySelector('input[name$="' + namespace + 'endDate"]');
        let endTime = Number.MAX_SAFE_INTEGER
        if ( end.value ){
            endTime = this.parseDate(end.value,"dd-MM-yyyy", "-").valueOf()
        }
        return startTime < endTime;

    },
    parseDate: function (date, format, delimiter){
          var formatLower = format.toLowerCase();
          var formatItems = formatLower.split(delimiter);
          var dateItems = date.split(delimiter);
          var monthIndex = formatItems.indexOf("mm");
          var dayIndex = formatItems.indexOf("dd");
          var yearIndex = formatItems.indexOf("yyyy");
          var month = parseInt(dateItems[monthIndex]);
          month -= 1;
          return  new Date(dateItems[yearIndex], month, dateItems[dayIndex]);
    },
    updateQueryString: function (namespace, name) {

        let sp = new URLSearchParams(location.search);

        if (name === 'startDate' || name === 'endDate') {
            let selector = 'input[name$="' + namespace + 'startDate"]';
            let input = document.querySelector(selector);
            let updated = false;
            if (input != null) {
                let value = input ? input.value : '';
                updated = this.setTerm(sp, 'startDate', value)
            }
            selector = 'input[name$="' + namespace + 'endDate"]';
            input = document.querySelector(selector);
            if (input != null) {
                let value = input ? input.value : '';
                updated = this.setTerm(sp, 'endDate', value) || updated

            }
            if (updated) {
                window.location.href = this.toUrl(location, sp);
            }
            return;
        }

        let selector = 'select[name$="' + namespace + "selection-facet-" + name + '"]';
        let input = document.querySelector(selector);
        if (input !== null){
            let selection = input ? input.value : '';
            if (selection === 'undefined') {
                updated = this.removeTerm(sp ,name) || updated
            } else {
                updated = this.setTerm(sp ,name, selection) || updated
            }
            if (updated){
                window.location.href = this.toUrl(location, sp);
            }
            return;
        }

        selector = 'select[name$="' + namespace + "checkbox-facet-" + name + '"]';
        input = document.querySelector(selector);
        if (input !== null){
            let selection = input ? input.value : undefined;
            if (selection === 'undefined') {
                updated = this.removeTerm(sp ,name) || updated
            } else {
                updated = this.setTerm(sp ,name, selection) || updated
            }
            if (updated){
                window.location.href = this.toUrl(location, sp);
            }
        }

    },
    clearError : function(namespace, name){
        let errorBlock = document.getElementById(namespace + name + '-message-block');
        errorBlock.innerHTML = '';
    },
    writeError: function(namespace, name, message){
        let errorBlock = document.getElementById(namespace + name + "-message-block");
        let messageNode = document.createElement("div");
        messageNode.classList.add("portlet-msg-error");
        messageNode.innerHTML = message;
        errorBlock.appendChild(messageNode);
    },

};
    },
    '',
    {
        requires: ['liferay-search-facet-util']
    }
);