<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />
<#if entries?has_content>
    <div class="c-new-ideas c-grid-3-cols">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>
            <#assign cur_phase = xmlUtils.getDynamicContentByName(rootElement, "NewIdeaPhase", false) />
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

            <div class="c-new-ideas__item c-asset-publisher-item">
                <h4 class="c-new-ideas__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                <div class="c-new-ideas__item__summary">${stringUtil.shorten(assetRenderer.getSummary(), 136)}</div>
                <div class="c-new-ideas__item__rating clearfix"><@getRatings entry/></div>
                <p class="c-new-ideas__item__phase ${cur_phase}">
                    <strong class="font-weight-regular">Phase:&nbsp;</strong>
                    <span class="submitted">Submitted</span>
                    <span class="planned">Project Plan</span>
                    <span class="financed">Financed</span>
                    <span class="started">Start project</span>
                </p>
                <a class="c-new-ideas__item__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
            </div>
        </#list>
    </div>
</#if>

<#macro getRatings entry>
    <#if getterUtil.getBoolean(enableRatings) && assetRenderer.isRatable()>
        <div class="asset-ratings">
            <@liferay_ratings["ratings"]
            className=entry.getClassName()
            classPK=entry.getClassPK()
            />
        </div>
    </#if>
</#macro>