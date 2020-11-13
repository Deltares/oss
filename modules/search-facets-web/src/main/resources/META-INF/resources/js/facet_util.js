AUI.add(
    'deltares-search-facet-util',
    function (A) {
        var FacetUtil = {
            updateQueryString: function (namespace) {

                let startDate = $('input[name$="' + namespace + 'startDate"]').val();
                let endDate = $('input[name$="' + namespace + 'endDate"]').val();
                let url = window.location.href;

                if (startDate) {
                    url = this.selectTerm(url, 'startDate', startDate);
                }
                if(endDate) {
                    url = this.selectTerm(url, 'endDate', endDate);
                }
                let type = $('select[name$="' + namespace + 'session-type"]').val();
                if (type) {
                    url = this.selectTerm(url, 'type', type);
                }
                let topic = $('select[name$="' + namespace + 'session-topic"]').val();
                if (topic) {
                    url = this.selectTerm(url, 'topic', topic);
                }

                let showPastElement = $('input[name$="' + namespace + 'showPast"]');
                if (showPastElement.val()){
                    url = this.selectTerm(url, 'showPast', showPastElement[0].checked);
                }

                window.location.href = url;
                //var url = Liferay.Search.FacetUtil.setURLParameter('http://example.com/path', 'q', 'test');
            },
            selectTerm: function (url, name, value) {
                return Liferay.Search.FacetUtil.setURLParameter(url, name, value);
            },

        }

        Liferay.namespace('Deltares').FacetUtil = FacetUtil;
    },
    '',
    {
        requires: ['liferay-search-facet-util']
    }
);