<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = displayContext.getRegistration() />
<#assign timeZoneId = registration.getTimeZoneId() />
<#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />

<#if registration.isMultiDayEvent() >
    <#assign title = displayContext.getTitle() />
</#if>
<#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
<#assign available = registration.getCapacity() - registrations />
<#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
<div class="row no-gutters">

    <div class="col-2">
        <img class="img-fluid" src="${displayContext.getSmallImageURL()}" alt="${registration.getTitle()}"/>
    </div>
    <div class="col-10 px-3">
        <h4>
            <a href="-/${urltitle}" >
                <strong>${title}</strong>
            </a>
        </h4>
        <div>
            <#assign count = displayContext.getPresenterCount()/>
            <#if count gt 0>
                <#list 0..(count-1) as i >
                    <div class="items-line">
                        <#assign imageUrl = displayContext.getPresenterSmallImageURL(i) />
                        <#if imageUrl?has_content >
                            <img width="32" class="expert-thumbnail" src="${imageUrl}"/>
                        </#if>
                        <#assign name = displayContext.getPresenterName(i) />
                        <#if name?has_content>
                            <span class="expert-name px-2">${name}</span> |
                        </#if>
                    </div>
                </#list>
            </#if>
            <span class="c-sessions__item__time-date-place__time">
                ${displayContext.getStartTime()} - ${displayContext.getEndTime()} (${timeZoneId})
            </span>|
            <#if displayContext.getPrice() gt 0 >
                ${displayContext.getCurrency()} ${displayContext.getPrice()}
            <#else>
                ${languageUtil.get(locale, "dsd.theme.session.free")}
            </#if>
            <#if showButtons >
                <#assign userId = themeDisplay.getUserId() />
                <span class="d-block" style="float:right">
                    <#if displayContext.isUserRegistered()>

                        <#assign joinLink = dsdSessionUtils.getUserJoinLink(themeDisplay.getUser(), registration) />
                        <#if joinLink?? && joinLink != "">
                            <a href="${joinLink}" target="-_blank" class="btn-lg btn-primary" role="button"
                               aria-pressed="true" style="margin-right:5px; color:#fff">
                                         ${languageUtil.get(locale, "registrationform.join")}
                                    </a>
                        </#if>
                            <a href="${displayContext.getUnregisterURL(renderRequest) }" class="btn-lg btn-primary" role="button" aria-pressed="true" style="color:#fff">
                                ${languageUtil.get(locale, "registrationform.unregister")}
                            </a>
                    <#else>
                        <#assign relatedArticles = dsdSessionUtils.getChildRegistrations(registration) />
                        <#assign args = "["/>
                        <#list relatedArticles as relatedArticle >
                            <#assign args = args + relatedArticle.getArticleId() />
                            <#if relatedArticle_has_next >
                                <#assign args = args + ","/>
                            </#if>
                        </#list>
                        <#assign args = args + "]"/>
                        <a href="#" data-article-id="${registration.getArticleId()}" class="btn-lg btn-primary add-to-cart" role="button"
                           aria-pressed="true"  style="color:#fff" onClick="return addRelatedAssets(this, ${args});">
                          ${languageUtil.get(locale, "shopping.cart.add")}
                        </a>
                    </#if>
                </span>

            </#if>
        </div>
        <#if cancellationExceeded >
            <div>
                <#assign courseConditionsUrl = displayContext.getCourseConditionsUrl() />
                <small><i>${languageUtil.get(locale, "registrationform.cancelExpired")?replace("{0}", courseConditionsUrl)}</i></small>
            </div>
        </#if>
    </div>
</div>
<script>

    addRelatedAssets = function(e, relatedArticles) {
        currentArticleId = Number(e.getAttribute('data-article-id'));
        currentBeingAdded =  !shoppingCart._contains(currentArticleId, 'registration');

        relatedArticles.forEach(function(relatedArticleId){
            relationBeingAdded = !shoppingCart._contains(relatedArticleId, 'registration');
            if (currentBeingAdded && relationBeingAdded){
                shoppingCart._addToCart(relatedArticleId, 'registration');
            } else if (!currentBeingAdded && !relationBeingAdded) {
                shoppingCart._removeFromCart(relatedArticleId, 'registration');
            }
        });


    }
</script>