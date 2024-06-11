<#-- DO NOT LOAD SESSIONS when in search resutls portlet -->
<#assign portletName = themeDisplay.getPortletDisplay().getPortletName() >
<#if !(portletName?ends_with("SearchResultsPortlet")) >

    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign urltitle=.vars['reserved-article-url-title'].data />
    <#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
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
    <#assign vat = registration.getVAT() />
    <#if registration.getCapacity() == 0 >
        <#assign available = ""  />
    <#else>
        <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
        <#assign available = registration.getCapacity() - registrations />
    </#if>
    <#assign locale = themeDisplay.getLocale() />
    <#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
    <div class="c-sessions page">
        <div class="c-sessions__item ${isEventPast}">
            <div class="clearfix">

                <div class="data-section">
                    <div class="c-sessions__item__date">
                        <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale, timeZone)}</span>
                        ${dateUtil.getDate(registration.getStartTime(), "MMM", locale, timeZone)}
                    </div>
                    <h3 class="c-sessions__item__title h1">${registration.getTitle()}</h3>

                    <div class="media-section">
                        <img class="c-sessions__item__image" src="${displayContext.getSmallImageURL()}" alt="${registration.getTitle()}" style="max-width:400px;max-height:400px;"/>
                    </div>
                    <#assign calDescription += (registration.getTitle() + "<br/>") />
                    <#if !registration.isOpen() || registration.isEventInPast() >
                        <b>${languageUtil.get(locale, "dsd.theme.session.closed")}</b>
                    </#if>
                    <p class="c-sessions__item__time-date-place">
                        <#if registration.isMultiDayEvent() >
                            <#if registration.isDaily() >
                                <#assign dateString = dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale, timeZone)
                                + "&nbsp;-&nbsp;" + dateUtil.getDate(registration.getEndTime(), "dd MMM yyyy", locale, timeZone) />
                                <#assign timeString = displayContext.getStartTime() + "&nbsp;-&nbsp;" +  displayContext.getEndTime() + " (" + timeZoneId + ")" />
                                <span class="c-sessions__item__time-date-place__date">
                                ${dateString}
                            </span>
                                <span class="c-sessions__item__time-date-place__time">
                                ${timeString}
                            </span>
                            <#else>
                                <#assign periods = registration.getStartAndEndTimesPerDay() />
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
                            <#assign dateString = dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale, timeZone) />
                            <#assign timeString = displayContext.getStartTime() + "&nbsp;-&nbsp;" + displayContext.getEndTime() + " (" + timeZone.getID() + ")" />
                            <span class="c-sessions__item__time-date-place__date">
                            ${dateString}
                        </span>
                            <span class="c-sessions__item__time-date-place__time">
                            ${timeString}
                        </span>
                        </#if>

                        <span class="c-sessions__item__time-date-place__place">
                        <div class="items-line">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()?lower_case}.png"
                             alt=""> &nbsp; ${typeDisplayName} </img>
                         </div>
                        <#assign calDescription += typeDisplayName + "<br/>"/>
                        <div class="items-line">
                            ${registration.getCurrency()} <div id='${articleId}_price'>0</div>
                            <#if vat gt 0 >
                                <#assign vatText = languageUtil.get(locale, "dsd.theme.session.vat")?replace("%d", vat) />
                                &nbsp;(${vatText})
                            </#if>
						</div>
                        <br/>
                        <#if registration.getEventId() gt 0 >
                            <#assign event = dsdParserUtils.getEvent(groupId, registration.getEventId()?string, themeDisplay.getLocale()) />
                        </#if>
                            ${languageUtil.get(locale, "dsd.theme.session.room")} :
                        <#if room??>
                            ${room.getTitle()}
                            <#if event?? && event.findBuilding(room)?? >
                                <#assign building = event.findBuilding(room) />
                                -  ${languageUtil.get(locale, "dsd.theme.session.building")} : ${building.getTitle()}
                            </#if>
                        </#if>
                        <br/>
                        <#if registration.isOpen() && !registration.isEventInPast() >
                            ${languageUtil.get(locale, "dsd.theme.session.available")} : ${available}
                        </#if>
                    </span>

                        <#list registration.getPresenters() as presenter >
                            <#assign expert = presenter />
                            <#assign expertImageUrl = expert.getSmallImageURL(themeDisplay) />
                            <dev class="items-line">
                                <#if expertImageUrl?? && expertImageUrl != "">
                                    <img class="expert-data__image" src="${expertImageUrl}" alt="expert image"/>
                                </#if>
                                <a href="mailto:${expert.getEmail()}">${expert.getName()}</a>
                            </dev>
                        </#list>
                        <#assign isRegistered = dsdSessionUtils.isUserRegisteredFor(user, registration) />
                        <span class="d-block">
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
                                <#assign relatedArticles = dsdSessionUtils.getChildRegistrations(registration) />
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
                            <#assign joinLink = dsdSessionUtils.getUserJoinLink(user, registration) />
                            <#if joinLink?? && joinLink != "">
                                <a href="${joinLink}" target="-_blank" class="btn-lg btn-primary" role="button"
                                   aria-pressed="true">
                                ${languageUtil.get(locale, "registrationform.join")}
                            </a>
                                <#assign calDescription += (languageUtil.get(locale, "registrationform.join") + ": " + joinLink )/>
                            </#if>
                        <div class="add-to-calendar c-session__item__calendar"></div>
                    </span>
                </div>
            </div>
        </div>

    </div>
    <script>
        var myCalendar = createCalendar({
            options: {
                class: '',
                id: '${articleId}' // You need to pass an ID. If you don't, one will be generated for you.
            },
            data: {
                title: '${registration.getTitle()}',     // Event title
                start: new Date(${registration.getStartTime()?long}),
                end: new Date(${registration.getEndTime()?long}),
                // If an end time is set, this will take precedence over duration
                address: '${registration.getRoom().getTitle()}',

                description: '${calDescription}'
            }
        });

        document.querySelector('.add-to-calendar').appendChild(myCalendar);

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