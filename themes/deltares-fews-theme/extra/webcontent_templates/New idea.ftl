<#assign journalArticleTitle = .vars['reserved-article-title'].data/>

<div class="projects-page">
    <div class="projects-page__item">
        <div class="projects-page__item__content">
            <div class="c-new-idea">
                <div class="c-new-idea__item">
                    <div class="c-new-idea__item__content">
                        <h1 class="c-new-idea__item__content__title">${journalArticleTitle}</h1>
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
            <script> 
                AUI().ready('aui-module', function(A){
                if($(".liferay-rating-score").length) {
                    $(".liferay-rating-score").appendTo('.c-new-idea__item__ratings');// move the ratings to the top
                }
                });
            </script>
        </div>
        <div class="projects-page__item__meta-data">
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
                <#if !expertExists >
                    <div class="expert-data">
                        <div class="expert-data__image" style="background-image:url(${OptionalFields.ExpertPhoto.getData()})">
                            <#if OptionalFields.ExpertPhoto.getData()?? && OptionalFields.ExpertPhoto.getData() != "">
                                <img alt="${OptionalFields.ExpertPhoto.getAttribute("alt")}" data-fileentryid="${OptionalFields.ExpertPhoto.getAttribute("fileEntryId")}" src="${OptionalFields.ExpertPhoto.getData()}" />
                            <#else> 
                                ${stringUtil.shorten(MandatoryFields.ExpertName.getData(), 1)}
                            </#if>
                        </div>
                            
                        <div class="expert-data__content">
                            <p class="bold">${MandatoryFields.ExpertName.getData()}</p>
                            <#if OptionalFields.ExpertJobTitle.getData()?has_content>
                                <p>${OptionalFields.ExpertJobTitle.getData()}</p>
                            </#if>
                            <#if OptionalFields.ExpertCompany.getData()?has_content>
                                <p>${OptionalFields.ExpertCompany.getData()}</p>
                            </#if>
                            <p><a href="mailto:${MandatoryFields.ExpertEmailAddress.getData()}" >${MandatoryFields.ExpertEmailAddress.getData()}</a></p>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>