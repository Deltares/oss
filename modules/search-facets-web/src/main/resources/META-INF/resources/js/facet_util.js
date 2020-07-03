AUI.add(
    'deltares-search-facet-util',
    function (A) {
        var FacetUtil = {
            updateQueryString: function (namespace) {

                let startDate = $('input[name$="' + namespace + 'startDate"]').val();
                let endDate = $('input[name$="' + namespace + 'endDate"]').val();
                let url = window.location.href;
                console.log(url);
                if (startDate) {
                    url = this.selectTerm(url, 'startDate', startDate);
                    console.log(url);
                }
                if(endDate) {
                    url = this.selectTerm(url, 'endDate', endDate);
                    console.log(url);
                }
                let type = $('select[name$="' + namespace + 'session-type"]').val();
                if (type) {
                    url = this.selectTerm(url, 'type', type);
                    console.log(url);
                }
                let topic = $('select[name$="' + namespace + 'session-topic"]').val();
                if (topic) {
                    url = this.selectTerm(url, 'topic', topic);
                    console.log(url);
                }
                window.location.href = url;
                //var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path', 'q', 'test');
            },
            selectTerm: function (url, name, value) {
                return Liferay.Search.FacetUtil.setURLParameter(url, name, value);
            }
        }

        Liferay.namespace('Deltares').FacetUtil = FacetUtil;
    },
    '',
    {
        requires: ['liferay-search-facet-util']
    }
);