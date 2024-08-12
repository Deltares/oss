<div class="search-results">
    <#if !entries?has_content>
        <div class="lg:container mx-auto">
            <div class="flex flex-row items-center pb-2 border-b border-theme-primary">
                <span class="inline-block py-2 text-sm text-theme-secondary v-uppercase-initial">
                    ${languageUtil.format(request, "no-results-were-found", false)}
                </span>
            </div>
        </div>
    <#else>
        <div class="lg:container mx-auto">
            <div class="flex flex-row items-center pb-2 border-b border-theme-primary">
                <span class="inline-block py-2 text-sm text-theme-secondary v-uppercase-initial">
                    <#if searchContainer.getTotal() == 1>
                        ${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
                    <#else>
                        ${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
                    </#if>
                </span>
            </div>
        </div>

        <div class="search-result-list">
            <div class="display-list">
                <div class="lg:container mx-auto">
                    <ol>
                        <#list entries as entry>
                            <li>
                                <a href="${entry.getViewURL()}" class="h-full flex flex-col justify-between items-stretch md:hover:shadow-md md:focus:shadow-md bg-theme-tertiary border-theme-secondary border-t-[3px] transition duration-200 hover:no-underline focus:no-underline">
                                    <span>
                                        <h4 class="font-medium text-lg lg:text-xl text-theme-secondary">${entry.getHighlightedTitle()}</h4>
                                        <#if entry.isContentVisible()>
                                            <span class="text-sm sm:text-base">
                                                ${entry.getContent()}
                                            </span>
                                        </#if>
                                    </span>
                                </a>
                            </li>
                        </#list>
                    </ol>
                </div>
            </div>
        </div>

        <div class="search-result-list-pagination">
            <@liferay_aui.form useNamespace=false>
                <@liferay_ui["search-paginator"]
                    id="${namespace + 'searchContainerTag'}"
                    markupView="lexicon"
                    searchContainer=searchContainer
                />
            </@liferay_aui.form>
        </div>
    </#if>
</div>