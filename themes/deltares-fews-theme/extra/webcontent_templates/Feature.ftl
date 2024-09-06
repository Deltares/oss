<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>

<div class="projects-page">
    <div class="projects-page__item row">
        <div class="projects-page__item__content detail-content col col-12 col-lg-7 xl:pr-16 2xl:pr-24 mb-4">
            <div class="prose prose--app">
                <h2 class="projects-page__item__content__title">${.vars['reserved-article-title'].data}</h2>
                <p class="projects-page__item__content__date text-theme-secondary font-medium text-base lg:text-lg tracking-widest pb-2">${displaydate?string["d MMMM yyyy"]}</p>
                <div class="projects-page__item__content__data">
                    <#if parentStructureFieldSet2393206.ProjectSectionHeaderFieldSet.getSiblings()?has_content>
                        <#list parentStructureFieldSet2393206.ProjectSectionHeaderFieldSet.getSiblings() as curr_ProjectSectionHeaderFieldSet>
                            ${curr_ProjectSectionHeaderFieldSet.ProjectSectionHeader.getData()}
                            ${curr_ProjectSectionHeaderFieldSet.ProjectSectionHeaderFieldSetFieldSet.ProjectSectionHTML.getData()}
                    </#list>
                    </#if>
                </div>
            </div>
        </div>
        <div class="projects-page__item__meta-data col col-12 col-lg-5 mb-4">
            <#if (parentStructureFieldSet2393206.ProjectImage.getData())?? && parentStructureFieldSet2393206.ProjectImage.getData() != "">
                <img class="projects-page__item__meta-data__image" alt="${parentStructureFieldSet2393206.ProjectImage.getAttribute("alt")}" src="${parentStructureFieldSet2393206.ProjectImage.getData()}" />
            </#if>
            <div class="projects-page__item__meta-data__expert">
                <#assign expertExists = false />
                <#if parentStructureFieldSet2393206.ProjectExpert.getData()?? &&  parentStructureFieldSet2393206.ProjectExpert.getData() != "">
                    <#assign cur_webContent_map = parentStructureFieldSet2393206.ProjectExpert.getData()?eval />
                    <#assign cur_webContent_classPK = cur_webContent_map.classPK />
                    <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                    <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                        <#assign expertExists = true />
                        ${journalContent.getContent(article.getGroupId(), article.getArticleId(), viewMode, locale.getLanguage())}
                    </#if>
                </#if>
            </div>
        </div>
    </div>
</div>