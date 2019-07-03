<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>

<div class="projects-page">
    <div class="projects-page__item">
        <div class="projects-page__item__content">
            <h1 class="projects-page__item__content__title">${.vars['reserved-article-title'].data}</h1>
            <p class="projects-page__item__content__date">${displaydate?string["d MMMM yyyy"]}</p>
            <div class="projects-page__item__content__data">
                ${MandatoryFields.BestPracticeContent.getData()}
            </div>
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
                        ${journalContentUtil.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
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

