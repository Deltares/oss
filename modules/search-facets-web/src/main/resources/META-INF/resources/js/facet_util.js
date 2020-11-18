AUI.add(
    'deltares-search-facet-util',
    function (A) {
        var FacetUtil = {
            updateQueryString: function (namespace) {

                let startDate = $('input[name$="' + namespace + 'startDate"]').val();
                let endDate = $('input[name$="' + namespace + 'endDate"]').val();
                let url = window.location.href;

                if (startDate !== undefined) {
                    if (startDate === ''){
                        url = this.removeTerm(url, 'startDate');
                    } else {
                        url = this.selectTerm(url, 'startDate', startDate);
                    }
                }
                if(endDate !== undefined) {
                    if (endDate === ''){
                        url = this.removeTerm(url, 'endDate');
                    } else {
                        url = this.selectTerm(url, 'endDate', startDate);
                    }
                }
                let type = $('select[name$="' + namespace + 'session-type"]').val();
                if (type !== undefined) {
                    if (type === 'undefined' ){
                        url = this.removeTerm(url, 'type');
                    } else {
                        url = this.selectTerm(url, 'type', type);
                    }
                }
                let topic = $('select[name$="' + namespace + 'session-topic"]').val();
                if (topic !== undefined) {
                    if (topic === 'undefined'){
                        url = this.removeTerm(url, 'topic');
                    } else {
                        url = this.selectTerm(url, 'topic', topic);
                    }
                }
                let showPastElement = $('input[name$="' + namespace + 'showPast"]');
                if (showPastElement.val() !== undefined){
                    if (showPastElement[0].checked) {
                        url = this.selectTerm(url, 'showPast', showPastElement[0].checked);
                    } else {
                        url = this.removeTerm(url, 'showPast');
                    }
                }
                let presentationElement = $('input[name$="' + namespace + 'hasPresentations"]');
                if (presentationElement.val() !== undefined){
                    if (presentationElement[0].checked) {
                        url = this.selectTerm(url, 'hasPresentations', presentationElement[0].checked);
                    } else {
                        url = this.removeTerm(url, 'hasPresentations');
                    }
                }
                window.location.href = url;
                //var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path', 'q', 'test');
            },
            selectTerm: function (url, name, value) {
                return Liferay.Search.FacetUtil.setURLParameter(url, name, value);
            },
            removeTerm: function (url, name) {
                let urlParts = url.split('?');
                if (urlParts.length < 2) return url;
                let newUrl = [];
                newUrl.push(urlParts[0]);

                let queryParts = urlParts[1].split('&');
                let newParts = [];
                for (let queryPart of queryParts){
                    if (queryPart.startsWith(name)) continue;
                    newParts.push(queryPart);
                }
                newUrl.push(newParts.join('&'));
                return newUrl.join('?');
            },
        }

        Liferay.namespace('Deltares').FacetUtil = FacetUtil;
    },
    '',
    {
        requires: ['liferay-search-facet-util']
    }
);