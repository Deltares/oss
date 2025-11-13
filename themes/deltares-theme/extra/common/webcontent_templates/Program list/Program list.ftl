<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = displayContext.getRegistration() />
<#assign timeZoneId = registration.getTimeZoneId() />
<#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />
<#assign redirectUrl= themeDisplay.getSiteGroup().getDisplayURL(themeDisplay) + "/program" />

<#if registration.isMultiDayEvent() >
    <#assign title = displayContext.getTitle() />
</#if>
<#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
<#assign available = registration.getCapacity() - registrations />
<#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
<div class="row no-gutters">

    <div class="col-2">
        <img class="img-fluid" src="${displayContext.getSmallImageURL()}" alt=""/>
    </div>
    <div class="col-10 px-3">
        <h4>
            <a href="-/${urltitle}?redirect=${redirectUrl}" >
                <strong>${title}</strong>
            </a>
        </h4>
        <p>
            <#assign count = displayContext.getPresenterCount()/>
            <#if count gt 0>
                <#list 0..(count-1) as i >
                    <div class="items-line">
                        <#assign imageUrl = displayContext.getPresenterSmallImageURL(i) />
                        <#if imageUrl?has_content >
                            <img width="32" class="expert-thumbnail" src="${imageUrl}" alt=""/>
                        </#if>
                        <#assign name = displayContext.getPresenterName(i) />
                        <#if name?has_content>
                            <span class="expert-name px-2">${name}</span> |
                        </#if>
                    </div>
                </#list>
            </#if>
            <span class="c-sessions__item__time-date-place__time">
						    <#if registration.isToBeDetermined() >
                                ${languageUtil.get(locale, "dsd.theme.session.tobedetermined")}
                            <#else>
                                ${displayContext.getStartTime()} - ${displayContext.getEndTime()} (${timeZoneId})
                            </#if>
            </span>|
            <#if displayContext.getPrice() gt 0 >
                ${displayContext.getCurrency()} ${displayContext.getPrice()?string(",#00")}
            <#else>
                ${languageUtil.get(locale, "dsd.theme.session.free")}
            </#if>
            <#if showButtons >
                <#assign userId = themeDisplay.getUserId() />
                <span class="d-block" style="float:right">
                    <#if available gt 0>
                        <a href="#" data-article-id="${registration.getArticleId()}" class="btn-lg btn-primary add-to-cart" role="button"
                           aria-pressed="true"  style="color:#fff">
                          ${languageUtil.get(locale, "shopping.cart.add")}
                        </a>
                    </#if>
                </span>

            </#if>
        </p>
    </div>
</div>