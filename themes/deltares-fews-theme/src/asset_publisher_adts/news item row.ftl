<!--container-->
<div class="c-grid news-row">
<#if entries?has_content>
    <#list entries as entry>
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign rootElement = document.getRootElement() />
        <#if rootElement?? && rootElement != "">
            <#assign overviewPhotoSelector = saxReaderUtil.createXPath("dynamic-element[@name='ImageOfNewsItem']") />
            <#assign ImageOfNewsItem = htmlUtil.escapeHREF(overviewPhotoSelector.selectSingleNode(rootElement).getStringValue()) />
        </#if>
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
        <!--repeatable element-->
        <div class="c-card news-item">
            <#if ImageOfNewsItem?? && ImageOfNewsItem != "">
                <div class="img-cropper" style="background-image:url(${ImageOfNewsItem})">
                    <img src="${ImageOfNewsItem}" />
                </div>
            </#if>
            <p class="c-card__date">${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
            <h4 class="c-card__title h1">${entry.getTitle(locale)}</h4>
            <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
        </div>
    </#list>
</#if>
</div>
