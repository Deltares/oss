<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>

<div class="news-page">
    <div class="news-page__item row">
        <div class="news-page__item__content detail-content col col-12 col-lg-7 xl:pr-16 2xl:pr-24 mb-4">
            <div class="prose prose--app">
                <h2 class="news-page__item__content__title">${.vars['reserved-article-title'].data}</h2>
                <p class="news-page__item__content__date text-theme-secondary font-medium text-base lg:text-lg tracking-widest pb-2">${displaydate?string["d MMMM yyyy"]}</p>
                <div class="news-page__item__content__data">
                    ${ContentOfNewsItem.getData()}
                </div>
            </div>
        </div>
        <div class="news-page__item__meta-data col col-12 col-lg-5 mb-4">
            <#if ImageOfNewsItem.getData()?? && ImageOfNewsItem.getData() != "">
                <img class="news-page__item__meta-data__image"  src="${ImageOfNewsItem.getData()}" />
            </#if>
            <div class="news-page__item__meta-data__expert">
                <#assign expertExists = false />
                <#if Expert?? && Expert.getData()?? && Expert.getData() != "">
                    <#assign cur_webContent_map = Expert.getData()?eval />
                    <#assign cur_webContent_classPK = cur_webContent_map.classPK />
                    <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                    <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                        <#assign expertExists = true />
                        ${journalContent.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                    </#if>
                </#if>
                <#if !expertExists>
                    <div class="expert-data">
                        <div class="grid grid-cols-12 gap-x-8">
                            <div class="flex col-span-12 md:col-span-12">
                                <div class="expert-data__image block object-cover w-24 h-24 rounded-full overflow-hidden mr-4 shrink-0 placeholder">
                                    <img src="${themeDisplay.getPathThemeImages()}/person-placeholder.svg" class="w-full aspect-[160/141] object-cover overflow-hidden" />
                                </div>

                                <div class="expert-data__content text-app-blue--egyptian shrink flex flex-col justify-center">
                                    <div class="block mb-2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="inline-flex items-center text-theme-quaternary w-2 h-2 mb-1 mr-1"><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
                                        <span class="text-base font-semibold">Deltares</span>
                                    </div>
                                    <span class="text-sm block font-regular">Water resources and environmental modelling</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>