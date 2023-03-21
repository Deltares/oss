<!--container-->
<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<div class="c-grid news-row">
<#if entries?has_content>
    <#list entries as entry>
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign article=assetRenderer.getArticle() />
        <#assign news = dsdParserUtils.toDsdArticle(article.getGroupId(),article.getArticleId()) />
        <#assign ImageOfNewsItem = news.getSmallImageURL(themeDisplay) />
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
        <!--repeatable element-->
        <div class="c-card news-item">
            <#if ImageOfNewsItem?? && ImageOfNewsItem != "">
                <a class="img-cropper display-block" 
                    style="background-image:url(${ImageOfNewsItem})"
                    href="${viewURL}" 
                    title="read more about ${entryTitle}">
                    <img src="${ImageOfNewsItem}"  alt=""/>
                </a>
            </#if>
            <p class="c-card__date">${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
            <h4 class="c-card__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${news.getTitle()}">${news.getTitle()}</a></h4>
            <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
        </div>
    </#list>
</#if>
</div>