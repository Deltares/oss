<#if entries?has_content>
    <div class="c-forum">
        <#list entries as entry>
            <#assign
                entry           = entry
                entryId         = entry.getEntryId()
                userUserId      = entry.getUserId()
                userName        = entry.getUserName()
                assetRenderer   = entry.getAssetRenderer()
                entryTitle      = htmlUtil.escape(assetRenderer.getTitle(locale))
                viewURL         = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, assetRenderer, entry, !stringUtil.equals(assetLinkBehavior, "showFullContent"))
                publishedDate   = dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)
                entryContent    = htmlUtil.stripHtml(assetRenderer.getSummary(renderRequest, renderResponse))
                entrySummary    = stringUtil.shorten(entryContent, 200)
                entryViewCount  = entry.viewCount 
            />
            <div class="c-forum__row">
                <div class="c-forum__item">
                    <div class="c-forum__item__avatar"><@liferay_ui["user-portrait"] userId=userUserId userName=userName cssClass="user-icon-lg"/></div>
                    <div class="c-forum__item__data">
                        <h3 class="c-forum__item__data__title h1 clear-margin">${entryTitle}</h1>
                        <p>
                            ${entrySummary}
                        </p>
                        <p class="c-forum__item__data__user">
                            ${userName} <span>|</span> 
                            ${publishedDate} <span>|</span> 
                            ${entryViewCount} Views
                        </p>
                        <a href="${viewURL}" class="regular-text c-forum__item__data__link">
                            <span class="link_underline">Read more</span> &gt;
                        </a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#if>