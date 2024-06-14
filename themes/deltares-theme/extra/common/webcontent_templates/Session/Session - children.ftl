<#-- DO NOT LOAD SESSIONS when in search resutls portlet -->
<#assign portletName = themeDisplay.getPortletDisplay().getPortletName() >
<#if !(portletName?ends_with("SearchResultsPortlet")) >

    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign urltitle=.vars['reserved-article-url-title'].data />
    <#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
    <#assign childRegistrations = dsdSessionUtils.getChildRegistrations(registration) />
    <#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
    <#assign timeZoneId = registration.getTimeZoneId() />
    <#assign timeZone = timeZoneUtil.getTimeZone(timeZoneId) />
    <#assign room = registration.getRoom() />
    <#if registration.isEventInPast() >
        <#assign isEventPast = "past-event"/>
    <#else>
        <#assign isEventPast = "upcoming-event"/>
    </#if>
    <#assign typeMap = dsdJournalArticleUtils.getStructureFieldOptions(groupId,"SESSION","registrationType", themeDisplay.getLocale()) />
    <#if typeMap?? >
        <#assign typeDisplayName = typeMap[registration.getType()] />
    <#else>
        <#assign typeDisplayName = registration.getType() />
    </#if>

    <#assign calDescription = "">

    <#assign price = 0 />
    <#if registration.getCapacity() == 0 >
        <#assign available = ""  />
    <#else>
        <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
        <#assign available = registration.getCapacity() - registrations />
    </#if>
    <#assign locale = themeDisplay.getLocale() />
    <#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
    <#assign isRegistered = displayContext.isUserRegistered() />
    <#list childRegistrations as child >

        <#assign price = price + child.getPrice() />
        <#assign displayContext = dsdParserUtils.getDisplayContextInstance(child.getArticleId(), themeDisplay) />
        <#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />
        <div class="row no-gutters">
            <div class="col-2">
                <img class="img-fluid" src="${displayContext.getSmallImageURL()}" alt="${child.getTitle()}"/>
            </div>
            <div class="col-10 px-3">
                <h4>
                    <strong>${child.getTitle()}</strong>
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

                    <#if child.isMultiDayEvent() >
                        <#if child.isDaily() >
                            <#assign dateString = dateUtil.getDate(child.getStartTime(), "dd MMM yyyy", locale, timeZone)
                            + "&nbsp;-&nbsp;" + dateUtil.getDate(child.getEndTime(), "dd MMM yyyy", locale, timeZone) />
                            <#assign timeString = displayContext.getStartTime() + "&nbsp;-&nbsp;" +  displayContext.getEndTime() + " (" + timeZoneId + ")" />
                            <span class="c-sessions__item__time-date-place__date">
                                ${dateString}
                            </span>
                            <span class="c-sessions__item__time-date-place__time">
                                ${timeString}
                            </span>
                        <#else>
                            <#assign periods = child.getStartAndEndTimesPerDay() />
                            <#list periods as period >
                                <#assign dateString = dateUtil.getDate(period.getStartDate(), "dd MMM yyyy", locale, timeZone) />
                                <#assign timeString = dateUtil.getDate(period.getStartDate(), "HH:mm", locale, timeZone)
                                + "&nbsp;-&nbsp;" + dateUtil.getDate(period.getEndDate(), "HH:mm", locale, timeZone)
                                + " (" + timeZoneId + ")" />
                                <span class="c-sessions__item__time-date-place__date">
                                    ${dateString}
                                </span>
                                <span class="c-sessions__item__time-date-place__time">
                                    ${timeString}
                                </span>
                            </#list>
                        </#if>
                    <#else>
                        <#assign dateString = dateUtil.getDate(child.getStartTime(), "dd MMM yyyy", locale, timeZone) />
                        <#assign timeString = displayContext.getStartTime() + "&nbsp;-&nbsp;" + displayContext.getEndTime() + " (" + timeZone.getID() + ")" />
                        <span class="c-sessions__item__time-date-place__date">
                            ${dateString}
                        </span>
                        <span class="c-sessions__item__time-date-place__time">
                            ${timeString}
                        </span>
                    </#if>

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
                       <#assign joinLink = dsdSessionUtils.getUserJoinLink(themeDisplay.getUser(), child) />
                       <#if joinLink?? && joinLink != "">
                           <a href="${joinLink}" target="-_blank" class="btn-lg btn-primary" role="button"
                              aria-pressed="true" style="margin-right:5px; color:#fff">
                          ${languageUtil.get(locale, "registrationform.join")}
                       </a>
                       </#if>
                     <a href="${displayContext.getUnregisterURL(renderRequest) }" class="btn-lg btn-primary" role="button" aria-pressed="true" style="color:#fff">
                            ${languageUtil.get(locale, "registrationform.unregister")}
                     </a>
                   <#elseif !isRegistered && available gt 0 >
                       <a href="#" data-article-id="${child.getArticleId()}" class="btn-lg btn-primary add-to-cart btn-child" role="button"
                           aria-pressed="true"  style="color:#fff" onClick="return addParentAsset(this, ${registration.getArticleId()});">
                       ${languageUtil.get(locale, "shopping.cart.add")}
                     </a>
                   </#if>
                   </span>
                    </#if>
                </div>
            </div>
        </div>
    </#list>

    <script>

        addParentAsset = function(e, parentArticleId) {
            currentArticleId = Number(e.getAttribute('data-article-id'));
            currentBeingAdded =  !shoppingCart._contains(currentArticleId, 'registration');

            parentBeingAdded = !shoppingCart._contains(parentArticleId, 'registration');
            if (currentBeingAdded && parentBeingAdded){
                shoppingCart._addToCart(parentArticleId, 'registration');
            }
        };
    </script>
</#if>
