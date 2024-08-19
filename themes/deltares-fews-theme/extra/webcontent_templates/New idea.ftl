<#assign journalArticleTitle = .vars['reserved-article-title'].data/>

<div class="projects-page">
    <div class="projects-page__item row">
        <div class="projects-page__item__content detail-content col col-12 col-lg-7 xl:pr-16 2xl:pr-24 mb-4">
            <div class="prose prose--app">
                <div class="c-new-idea">
                    <div class="c-new-idea__item">
                        <div class="c-new-idea__item__content">
                            <h2 class="c-new-idea__item__content__title">${journalArticleTitle}</h2>
                            <div class="c-new-idea__item__ratings clearfix">
                                <p class="c-new-idea__item__ratings__votes">Votes: </p>
                            </div>
                            <#assign current_phase = MandatoryFields.NewIdeaPhase.getData()?replace('["', "")?replace('"]', "")/>
                            <p class="c-new-idea__item__content__phase ${current_phase}">
                            Phase:
                                <span class="submitted">Submitted</span> |
                                <span class="planned">Project Plan</span> |
                                <span class="financed">Financed</span> |
                                <span class="started">Start project</span>
                            </p>
                            <div class="c-new-idea__item__content__data">
                                ${MandatoryFields.NewIdeaContent.getData()}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                AUI().ready('aui-module', function(A){
                if($(".liferay-rating-score").length) {
                    $(".liferay-rating-score").appendTo('.c-new-idea__item__ratings'); // move the ratings to the top
                }
                });
            </script>
        </div>
        <div class="projects-page__item__meta-data col col-12 col-lg-5 mb-4">
            <div class="projects-page__item__meta-data__expert">
                <#assign expertExists = false />
                <#if OptionalFields.SelectExpert_hide?? && OptionalFields.SelectExpert_hide.getData()?? &&  OptionalFields.SelectExpert_hide.getData() != "">
                    <#assign cur_webContent_map = OptionalFields.SelectExpert_hide.getData()?eval>
                    <#assign cur_webContent_classPK = cur_webContent_map.classPK>
                    <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                    <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                        <#assign expertExists = true />
                        ${journalContent.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                    </#if>
                </#if>
                <#if !expertExists>
                    <div class="expert-data">
                        <div class="grid grid-cols-12 gap-x-8">
                            <div class="flex col-span-12 pt-5 md:col-span-12 lg:pt-8">
                                <div class="expert-data__image" style="background-image:url(${OptionalFields.ExpertPhoto.getData()})">
                                    <#if OptionalFields.ExpertPhoto.getData()?? && OptionalFields.ExpertPhoto.getData() != "">
                                        <img alt="${OptionalFields.ExpertPhoto.getAttribute("alt")}" data-fileentryid="${OptionalFields.ExpertPhoto.getAttribute("fileEntryId")}" src="${OptionalFields.ExpertPhoto.getData()}" />
                                    <#else> 
                                        <div class="expert-data__image block object-cover w-24 h-24 rounded-full overflow-hidden mr-4 shrink-0 placeholder">
                                            <img src="${themeDisplay.getPathThemeImages()}/person-placeholder.svg" class="w-full aspect-[160/141] object-cover overflow-hidden" />
                                        </div>
                                    </#if>
                                </div>
                                <div class="expert-data__content">
                                    <div class="block mb-2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="inline-flex items-center text-theme-quaternary w-2 h-2 mb-1 mr-1"><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
                                        <span class="text-base font-semibold">${MandatoryFields.ExpertName.getData()}</span>
                                    </div>
                                    <#if OptionalFields.ExpertJobTitle.getData()?has_content>
                                        <span class="text-sm block font-regular">${OptionalFields.ExpertJobTitle.getData()}</span>
                                    </#if>
                                    <#if OptionalFields.ExpertCompany.getData()?has_content>
                                        <span class="text-sm block font-regular">${OptionalFields.ExpertCompany.getData()}</span>
                                    </#if>
                                    <span class="text-sm block font-regular">
                                        <a href="mailto:${MandatoryFields.ExpertEmailAddress.getData()}" >${MandatoryFields.ExpertEmailAddress.getData()}</a>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>