<@liferay_aui.fieldset cssClass="search-bar">
    <div class="flex lg:border-t lg:border-b lg:border-solid border-theme-secondary search-bar-container">
        <@liferay_aui.input
            cssClass="search-bar-empty-search-input"
            name="emptySearchEnabled"
            type="hidden"
            value=searchBarPortletDisplayContext.isEmptySearchEnabled()
        />

        <div class="input-group ${searchBarPortletDisplayContext.isLetTheUserChooseTheSearchScope()?then("search-bar-scope","search-bar-simple")}">
            <div class="input-group-item search-bar-keywords-input-wrapper">
                <input
                    autocomplete="off"
                    class="search-bar-keywords-input w-3/5 md:w-2/5 italic text-sm text-theme-secondary font-medium rounded-l border-t border-b border-l border-theme-primary placeholder:italic placeholder:text-theme-secondary search-bar-input"
                    data-qa-id="searchInput"
                    id="${namespace + stringUtil.randomId()}"
                    name="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywordsParameterName())}"
                    placeholder="${searchBarPortletDisplayContext.getInputPlaceholder()}"
                    title="${languageUtil.get(locale, "search")}"
                    type="search"
                    value="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywords())}"
                />

                <div class="search-bar-submit">
                    <button aria-label="${languageUtil.get(locale, 'search')}" type="submit" data-qa-id="searchInput" class="shrink-0 bg-theme-button group-hover:bg-theme-button--hover group-focus:bg-theme-button--hover transition duration-200 rounded-r">
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="flex w-5 h-5">
                            <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                        </svg>
                    </button>
                </div>

                <@liferay_aui.input
                    name=htmlUtil.escape(searchBarPortletDisplayContext.getScopeParameterName())
                    type="hidden"
                    value=searchBarPortletDisplayContext.getScopeParameterValue()
                />
            </div>
        </div>
    </div>
</@>