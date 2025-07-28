<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
<div class="flex flex-col pb-10 lg:pb-0 spotlight-slider">
    <#if entries?has_content>
        <h2 class="portlet-title-text" style="font-size:28px; display:block" >
            Related Sessions
        </h2>
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign registration = dsdParserUtils.getRegistration(journalArticle) />
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <#assign imageUrl = registration.getSmallImageURL(themeDisplay) />
            <#assign displayContext = dsdParserUtils.getDisplayContextInstance(registration.getArticleId(), themeDisplay) />
            <#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />
            <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
            <#assign available = registration.getCapacity() - registrations />
            <!--repeatable element-->
            <div class="flex">
                <div class="flex flex-col pt-10" >
                    <a href="${viewURL}" class="group flex flex-col relative h-full font-medium font-sans transition duration-200 items-start leading-none cursor-pointer text-white z-0" title="read more about ${entryTitle}">
                        <div class="flex flex-col justify-start self-stretch grow order-2 relative w-full -mt-20 pt-20 px-3 z-20 spotlight-gradient">
                            <h3 class="order-2 text-lg leading-tight text-white font-semibold">${entryTitle}</h3>
                            <span class="order-1 mb-1 pt-2 text-sm font-medium leading-tight">${dateUtil.getDate(registration.getStartTime(), "d MMMM yyyy", locale)}</span>
                        </div>
                        <div class="order-1 w-full relative z-10 overflow-hidden">
                            <div class="block w-full object-cover">
                                <picture>
                                    <img width="256" height="240" src="${imageUrl}" class="block w-full object-cover tns-lazy-img loaded tns-complete">
                                </picture>
                            </div>
                        </div>
                    </a>
                    <#if showButtons >
                        <#if available gt 0>
                            <a href="#" data-article-id="${registration.getArticleId()}" class="btn-lg btn-primary add-to-cart" role="button"
                               aria-pressed="true"  style="color:#fff">
                                ${languageUtil.get(locale, "shopping.cart.add")}
                            </a>
                        </#if>
                    </#if>
                </div>
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
        controls: false,
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