;(function () {
    AUI().applyConfig(
        {
            groups: {
                deltares: {
                    base: MODULE_PATH + '/js/',
                    combine: Liferay.AUI.getCombine(),
                    filter: Liferay.AUI.getFilterConfig(),
                    modules: {
                        'deltares-search-facet-util': {
                            path: 'facet_util.js',
                            requires: []
                        }
                    },
                    root: MODULE_PATH + '/js/'
                }
            }
        }
    );
})();