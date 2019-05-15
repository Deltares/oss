<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>

<div class="projects-page">
    <div class="projects-page__item">
        <div class="projects-page__item__content">
            <h1 class="projects-page__item__content__title">${.vars['reserved-article-title'].data}</h1>
                <p class="projects-page__item__content__date">${displaydate?string["d MMMM yyyy"]}</p>
            <div class="projects-page__item__content__data">
                <#if ProjectSectionHeader.getSiblings()?has_content>
                <#list ProjectSectionHeader.getSiblings() as cur_ProjectSectionHeader>
                    ${cur_ProjectSectionHeader.getData()}
                    ${cur_ProjectSectionHeader.ProjectSectionHTML.getData()}
                </#list>
                </#if>
            </div>
        </div>
        <div class="projects-page__item__meta-data">
            <#if ProjectImage.getData()?? && ProjectImage.getData() != "">
                <img class="projects-page__item__meta-data__image" alt="${ProjectImage.getAttribute("alt")}" data-fileentryid="${ProjectImage.getAttribute("fileEntryId")}" src="${ProjectImage.getData()}" />
            </#if>
            <div class="projects-page__item__meta-data__expert">
                <#if ProjectExpert?? && ProjectExpert.getData()?? &&  ProjectExpert.getData() != "">
                    <#assign cur_webContent_map = ProjectExpert.getData()?eval>
                    <#assign cur_webContent_classPK = cur_webContent_map.classPK>
                    <#assign article = journalArticleLocalService.getLatestArticle(cur_webContent_classPK?number)>

                    ${journalContent.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                <#else>
                    <div class="expert-data">
                        <div class="expert-data__image">D</div>
                        <div class="expert-data__content">
                            <p class="bold">Deltares</p>
                            <p>Water resources and environmental modelling</p>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>
