<!--container-->
<div class="flex flex-row pb-2 lg:pb-0 spotlight-slider">
    <#if entries?has_content>
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
            <#assign rootElement = document.getRootElement() />
            <#if rootElement?? && rootElement != "">
                <#assign overviewPhotoSelector = saxReaderUtil.createXPath("dynamic-element[@name='ImageOfNewsItem']") />
                <#assign overviewPhotoElement = overviewPhotoSelector.selectSingleNode(rootElement) />
                <#assign ImageOfNewsItemElement = htmlUtil.escape(overviewPhotoElement.getStringValue()) />
                <#assign ImageOfNewsItem = ddlUtils.getFileEntryImage(overviewPhotoSelector.selectSingleNode(rootElement).getStringValue()) />
            </#if>
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <!--repeatable element-->
            <div class="flex">
                <#if ImageOfNewsItemElement?? && ImageOfNewsItemElement?contains("url")>
                    <a href="${viewURL}" class="group flex flex-col relative h-full font-medium font-sans transition duration-200 items-start leading-none cursor-pointer text-white z-0" title="read more about ${entryTitle}">
                <#else>
                    <a href="${viewURL}" class="group flex flex-col relative h-full font-medium font-sans transition duration-200 items-start leading-none cursor-pointer text-white z-0 bg-theme-quinary" title="read more about ${entryTitle}">
                </#if>
                    <div class="flex flex-col justify-start self-stretch grow order-2 relative w-full -mt-20 pt-20 px-3 z-20 spotlight-gradient">
                        <h3 class="order-2 text-lg leading-tight text-white font-semibold">${entryTitle}</h3>
                        <span class="order-1 mb-1 pt-2 text-sm font-medium leading-tight">${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</span>
                    </div>
                    <#if ImageOfNewsItemElement?? && ImageOfNewsItemElement?contains("url")>
                        <div class="order-1 w-full relative z-10 overflow-hidden">
                            <div class="block w-full object-cover">
                                <picture>
                                    <img width="256" height="240" src="${ImageOfNewsItem}" class="block w-full object-cover tns-lazy-img loaded tns-complete">
                                </picture>
                            </div>
                        </div>
                    <#else>
                        <div class="order-1 w-full relative z-10 overflow-hidden">
                            <div class="w-full pt-[100%] sphere-green-gradient"></div>
                        </div>
                    </#if>
                </a>
            </div>
        </#list>
    </#if>
</div>

<@liferay.js file_name="${themeDisplay.getPathThemeJavaScript()}/tiny-slider/tiny-slider.js" />
<@liferay.css file_name="${themeDisplay.getPathThemeJavaScript()}/tiny-slider/tiny-slider.css" />

<script>
    var slider = tns({
        container: '.spotlight-slider',
        arrowKeys: true,
        autoWidth: false,
        controls: true,
        controlsPosition: 'bottom',
        edgePadding: 16,
        gutter: 10,
        items: 2,
        lazyload: true,
        loop: false,
        mouseDrag: true,
        navPosition: 'bottom',
        preventScrollOnTouch: 'auto',
        fixedWidth: 254,
        responsive: {
          760: {
            edgePadding: 50,
          },
          1024: {
            edgePadding: 80,
          },
        },
        speed: 400,
        swipeAngle: false,
    });
</script>
