<#-- DO NOT LOAD SESSIONS when in search resutls portlet -->
<#assign portletName = themeDisplay.getPortletDisplay().getPortletName() >
<#if !(portletName?ends_with("SearchResultsPortlet")) >

    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
    <#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
    <#if registration.isEventInPast() >
        <#assign isEventPast = "past-event"/>
    <#else>
        <#assign isEventPast = "upcoming-event"/>
    </#if>
    <#if registration.getCapacity() == 0 >
        <#assign available = ""  />
    <#else>
        <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
        <#assign available = registration.getCapacity() - registrations />
    </#if>
    <#assign locale = themeDisplay.getLocale() />
    <#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
    <div class="c-sessions page">
        <#assign isRegistered = dsdSessionUtils.isUserRegisteredFor(user, registration) />
        <#if isRegistered >
            <a href="${displayContext.getUnregisterURL(renderRequest)}" class="btn-lg btn-primary"
               role="button" aria-pressed="true">
                ${languageUtil.get(locale, "registrationform.unregister")}
            </a>
            &nbsp;
            <a href="${displayContext.getUpdateURL(renderRequest)}" class="btn-lg btn-primary"
               role="button" aria-pressed="true">
                ${languageUtil.get(locale, "registrationform.update")}
            </a>
            <#if cancellationExceeded >
                <div>
                    <#assign courseConditionsUrl = displayContext.getCourseConditionsUrl() />
                    <small><i>${languageUtil.get(locale, "registrationform.cancelExpired")?replace("{0}", courseConditionsUrl)}</i></small>
                </div>
            </#if>
        <#else >
            <#if registration.canUserRegister(user.getUserId()) && themeDisplay.isSignedIn() && available gt 0>
                <#assign event = dsdParserUtils.getEvent(groupId, registration.getEventId()?string, locale) />
                <#assign eventRegistrations = event.getRegistrations(locale) />
                <#assign relatedArticles = dsdSessionUtils.getChildRegistrations(registration, eventRegistrations) />
                <#assign args = "["/>
                <#list relatedArticles as relatedArticle >
                    <#assign args = args + relatedArticle.getArticleId() />
                    <#if relatedArticle_has_next >
                        <#assign args = args + ","/>
                    </#if>
                </#list>
                <#assign args = args + "]"/>
                <a href="#" data-article-id="${articleId}" class="btn-lg btn-primary add-to-cart" onClick="return addRelatedAssets(this, ${args});"
                   role="button" aria-pressed="true">
                    ${languageUtil.get(locale, "shopping.cart.add")}
                </a>
            </#if>
        </#if>
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
            })
        };
    </script>
</#if>