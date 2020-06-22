<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>

<div class="news-page">
    <div class="news-page__item">
        <div class="news-page__item__content">
            <h1 class="news-page__item__content__title">${.vars['reserved-article-title'].data}</h1>
            <p class="news-page__item__content__date">${displaydate?string["d MMMM yyyy"]}</p>
            <div class="news-page__item__content__data">${contentOfNewsItem.getData()}</div>
        </div>
        <div class="news-page__item__meta-data">
            <#if imageOfNewsItem.getData()?? && imageOfNewsItem.getData() != "">
                <img class="news-page__item__meta-data__image" alt="${imageOfNewsItem.getAttribute("alt")}" data-fileentryid="${imageOfNewsItem.getAttribute("fileEntryId")}" src="${ImageOfNewsItem.getData()}" />
            </#if>
            <div class="news-page__item__meta-data__expert">
                <#assign expertExists = false />
                <#if expert?? && expert.getData()?? &&  expert.getData() != "">
                    <#assign cur_webContent_map = expert.getData()?eval />
                    <#assign cur_webContent_classPK = cur_webContent_map.classPK />
                    <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                    <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                        <#assign expertExists = true />
                        ${journalContent.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                    </#if>
                </#if>
                <#if !expertExists >
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