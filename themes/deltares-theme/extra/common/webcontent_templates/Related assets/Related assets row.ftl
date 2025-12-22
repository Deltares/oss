<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />

<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = displayContext.getRegistration() />
<!-- <#assign redirectUrl= themeDisplay.getSiteGroup().getDisplayURL(themeDisplay) + "/program" /> -->
<#assign redirectUrl = themeDisplay.getURLCurrent() />

<#assign imageUrl = displayContext.getSmallImageURL() />
<#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />
<#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
<#assign available = registration.getCapacity() - registrations />

<#if registration.isMultiDayEvent() >
    <#assign title = displayContext.getTitle() />
</#if>

<!--repeatable element-->
<div class="flex">
    <div class="flex flex-col" >
        <a href="-/${urltitle}?redirect=${redirectUrl}" class="group flex flex-col relative h-full font-medium font-sans transition duration-200 items-start leading-none cursor-pointer text-white z-0" title="read more about ${title}">
            <div class="flex flex-col justify-start self-stretch grow order-2 relative w-full -mt-20 pt-20 px-3 z-20 spotlight-gradient">
                <h3 class="order-2 text-lg leading-tight text-white font-semibold">${title}</h3>
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