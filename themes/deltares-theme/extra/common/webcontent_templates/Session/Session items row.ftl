<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign displayContext = dsdParserUtils.getDSDArticleDisplayContextInstance(groupId, articleId, themeDisplay) />
<#assign registration = displayContext.getDsdArticle() />
<#assign eventImageUrl = displayContext.getSmallImageURL() />
<#assign viewURL = displayContext.getViewURL(registration) />
<#assign entryTitle = displayContext.getTitle() />

<div class="c-card news-item">
    <#if eventImageUrl??>
        <a class="img-cropper display-block"
           style="background-image:url(${eventImageUrl})"
           href="${viewURL}"
           title="read more about ${entryTitle}">
            <img src="${eventImageUrl}" />
        </a>
    </#if>
    <p class="c-card__date">${dateUtil.getDate(registration.getStartTime(), "d MMMM yyyy", locale)}</p>
    <h4 class="c-card__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
    <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
</div>