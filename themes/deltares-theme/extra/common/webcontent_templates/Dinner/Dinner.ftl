<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign restaurant = registration.getRestaurant() />
<#if registration.isEventInPast() >
    <#assign isEventPast = "past-event"/>
<#else>
    <#assign isEventPast = "upcoming-event"/>
</#if>
<#assign eventImageUrl = displayContext.getSmallImageURL() />
<#assign price = registration.getPrice() />
<#assign vat = registration.getVAT() />
<div class="c-sessions page">
    <div class="c-sessions__item ${isEventPast}">
        <div class="clearfix">
            <div class="media-section">
                <#if eventImageUrl??>
                    <img
                            class="c-sessions__item__image"
                            src="${eventImageUrl}" />
                </#if>
            </div>
            <div class="data-section">
                <div class="c-sessions__item__date">
                    <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale)}</span>
                    ${dateUtil.getDate(registration.getStartTime(), "MMM", locale)}
                </div>
                <h3 class="c-sessions__item__title h1">${registration.getTitle()}</h3>
                <#if !registration.isOpen() >
                    <b>${languageUtil.get(locale, "dsd.theme.session.closed")}</b>
                </#if>
                <p class="c-sessions__item__time-date-place">
                    <span class="c-sessions__item__time-date-place__date">
                        ${dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale)}

                    </span>
                    <span class="c-sessions__item__time-date-place__time">
                        ${dateUtil.getDate(registration.getStartTime(), "HH:mm", locale)} -
                        ${dateUtil.getDate(registration.getEndTime(), "HH:mm", locale)}</span>
                    <br>
                    <span class="c-sessions__item__time-date-place__place">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()}.png"> ${registration.getType()}&nbsp;
                        <br>
                        ${registration.getCurrency()}
                        <#if price == 0 >
                            ${languageUtil.get(locale, "dsd.theme.session.free")}&nbsp;
                        <#else>
                            <#assign vatText = languageUtil.get(locale, "dsd.theme.session.vat")?replace("%d", vat) />
                            ${registration.getPrice()}&nbsp;(${vatText})
                        </#if>
                        <br>
                        <#assign event = dsdParserUtils.getEvent(groupId, registration.getEventId()?string) />
                        ${languageUtil.get(locale, "dsd.theme.session.room")} :
                        <#if restaurant??>

                            ${restaurant.getTitle()}

                        </#if>
                        <br>
                        <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
                        <#assign available = registration.getCapacity() - registrations />
                        ${languageUtil.get(locale, "dsd.theme.session.available")} : ${available}
                    </span>

                    <#if themeDisplay.isSignedIn() >
                        <#assign isRegistered = dsdSessionUtils.isUserRegisteredFor(user, registration) />
                        <span class="d-block">
                            <#if isRegistered >
                                <a href="${displayContext.getUnregisterURL(renderRequest)}" class="btn btn-primary" role="button" aria-pressed="true">
                                    ${languageUtil.get(locale, "registrationform.unregister")}
                                </a>
                                <a href="${displayContext.getRegisterURL(renderRequest)}" class="btn btn-primary" role="button" aria-pressed="true">
                                     ${languageUtil.get(locale, "registrationform.update")}
                                </a>
                            <#else>
                                <a href="${displayContext.getRegisterURL(renderRequest)}" class="btn btn-primary" role="button" aria-pressed="true">
                                     ${languageUtil.get(locale, "registrationform.register")}
                                </a>
                            </#if>
                        </span>
                    </#if>
                </p>
            </div>
        </div>
    </div>
    <div class="c-sessions__item__description">
        ${displayContext.getSummary()}
    </div>

    <#if schedules?? && schedules.getSiblings()?has_content && validator.isNotNull(schedules.getSiblings()?first.getData())>

        <#list schedules.getSiblings() as cur_Schedule>
            <h3 class="c-sessions__item__title h1">
                <#assign schedules_date_Data = getterUtil.getString(cur_Schedule.date.getData())>
                <#if validator.isNotNull(schedules_date_Data)>
                    <#assign schedules_date_DateObj = dateUtil.parseDate("yyyy-MM-dd", schedules_date_Data, locale)>
                    ${languageUtil.get(locale, "dsd.theme.session.schedule")} - ${dateUtil.getDate(schedules_date_DateObj, "dd MMM yyyy", locale)}
                </#if>
            </h3>
            <div class="c-sessions__item__description">
                ${cur_Schedule.getData()}
            </div>
        </#list>

    </#if>
</div>