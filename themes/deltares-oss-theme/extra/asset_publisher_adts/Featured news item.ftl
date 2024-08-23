<div class="c-featured-news">
    <#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />
    <#if entry?has_content>

        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign assetSummary = assetRenderer.getSummary() />
        <#assign document = xmlUtils.parseContent(journalArticle, locale)/>

        <#assign ContentOfNewsItem = xmlUtils.getDynamicContentByName(document, "ContentOfNewsItem", false) />
        <#assign overviewPhotoSelector = xmlUtils.getDynamicContentByName(document, "ImageOfNewsItem", false) />
        <#assign ImageOfNewsItem = ddlUtils.getFileEntryImage(overviewPhotoSelector) />
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />


        <div class="data-column">
            <h1 class="c-featured-news__title"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h1>
            <p class="c-featured-news__date">${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
            <p class="c-featured-news__abstract">
                ${stringUtil.shorten(ContentOfNewsItem, 560)}
                <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
            </p>
        </div>
        <#if ImageOfNewsItem?? && ImageOfNewsItem != "">
            <div class="media-column">
                <a class="img-cropper c-featured-news__image display-block"
                   style="background-image:url(${ImageOfNewsItem})"
                   href="${viewURL}"
                   title="read more about ${entryTitle}">
                    <img src="${ImageOfNewsItem}" />
                </a>
            </div>
        </#if>
    </#if>
</div>