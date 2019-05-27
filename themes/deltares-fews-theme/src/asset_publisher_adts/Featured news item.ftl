<div class="c-featured-news">
    <#if entry?has_content>
    
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign assetSummary = assetRenderer.getSummary() />
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign rootElement = document.getRootElement() />
        <#if rootElement?? && rootElement != "">
            <#assign overviewContentSelector = saxReaderUtil.createXPath("dynamic-element[@name='ContentOfNewsItem']") />
            <#assign ContentOfNewsItem = htmlUtil.extractText(overviewContentSelector.selectSingleNode(rootElement).getStringValue()) />
            <#assign overviewPhotoSelector = saxReaderUtil.createXPath("dynamic-element[@name='ImageOfNewsItem']") />
            <#assign ImageOfNewsItem = htmlUtil.escapeHREF(overviewPhotoSelector.selectSingleNode(rootElement).getStringValue()) />
        </#if> 

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