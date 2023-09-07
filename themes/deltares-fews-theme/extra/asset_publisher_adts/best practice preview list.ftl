<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />
<div class="bestpractice-page">
    <#if entries?has_content>
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>
            <#assign cur_content = xmlUtils.getDynamicContentByName(rootElement, "BestPracticeContent", false) />
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

            <div class="bestpractice-page__item clearfix">
                <!--Best practice title-->
                <h4 class="bestpractice-page__item__meta-data__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                <!--Best practice content-->
                <div class="bestpractice-page__item__meta-data__content">
                    ${stringUtil.shorten(cur_content, 286)} <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
                </div>
            </div>
        </#list>
    </#if>
</div>