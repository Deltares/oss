<#ftl output_format="XML">

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
                <h4 class="bestpractice-page__item__meta-data__title font-medium text-lg lg:text-xl text-theme-secondary mb-3 lg:mb-2">
                    <a class="type-inherit hover:underline focus:underline" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle?no_esc}</a>
                </h4>
                <!--Best practice content-->
                <div class="bestpractice-page__item__meta-data__content text-sm sm:text-base">
                    ${stringUtil.shorten(cur_content, 350)?replace('<[^>]+>','','r')}
                </div>
            </div>
        </#list>
    </#if>
</div>