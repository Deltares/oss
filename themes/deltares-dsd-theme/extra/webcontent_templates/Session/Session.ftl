<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign dsdSessionUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdSessionUtils") />
<#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign timeZoneId = registration.getTimeZoneId() />
<#assign room = registration.getRoom() />
<#if registration.isEventInPast() >
    <#assign isEventPast = "past-event"/>
<#else>
    <#assign isEventPast = "upcoming-event"/>
</#if>
<#assign typeMap = dsdJournalArticleUtils.getStructureFieldOptions(groupId,"SESSION","registrationType", registration.getLocale()) />
<#if typeMap?? >
    <#assign typeDisplayName = typeMap[registration.getType()] />
<#else>
    <#assign typeDisplayName = registration.getType() />
</#if>

<#assign eventImageUrl = registration.getSmallImageURL(themeDisplay) />
<#assign price = registration.getPrice() />
<#assign vat = registration.getVAT() />
<div class="c-sessions page">
    <div class="c-sessions__item ${isEventPast}">
        <div class="clearfix">
            <div class="media-section">
                <#if entImageUrl??>
                    <img class="c-sessions__item__image" src="${eventImageUrl}" />
                </#if>
            </div>
            <div class="data-section">
                <div class="c-sessions__item__date">
                    <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale)}</span>
                    ${dateUtil.getDate(registration.getStartTime(), "MMM", locale)}
                </div>
                <h3 class="c-sessions__item__title h1">${registration.getTitle()}</h3>
                <#if !registration.isOpen() || registration.isEventInPast() >
                    <b>${languageUtil.get(locale, "dsd.theme.session.closed")}</b>
                </#if>
                <p class="c-sessions__item__time-date-place">
                    <#if registration.isMultiDayEvent() >
                        <#if registration.isDaily() >
                            <span class="c-sessions__item__time-date-place__date">
                                ${dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale)}&nbsp;-&nbsp;${dateUtil.getDate(registration.getEndTime(), "dd MMM yyyy", locale)}
                            </span>
                            <span class="c-sessions__item__time-date-place__time">
                                ${displayContext.getStartTime()} - ${displayContext.getEndTime()} (${timeZoneId})
                            </span>
                        <#else>
                            <#assign periods = registration.getStartAndEndTimesPerDay() />
                            <#list periods as period >
                                <span class="c-sessions__item__time-date-place__date">
                                    ${dateUtil.getDate(period.getStartDate(), "dd MMM yyyy", locale)}
                                </span>
                                <span class="c-sessions__item__time-date-place__time">
                                        ${dateUtil.getDate(registration.getStartTime(), "HH:mm", locale)} - ${dateUtil.getDate(registration.getEndTime(), "HH:mm", locale)} (${timeZoneId})
                                </span>
                            </#list>
                        </#if>
                    <#else>
                        <span class="c-sessions__item__time-date-place__date">
                            ${dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale)}
                        </span>
                        <span class="c-sessions__item__time-date-place__time">
                            ${displayContext.getStartTime()} - ${displayContext.getEndTime()} (${timeZoneId})
                        </span>
                    </#if>
                    <br />
                    <span class="c-sessions__item__time-date-place__place">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()?lower_case}.png"> ${typeDisplayName}
                        <br>
                        <#if registration.isOpen() && !registration.isEventInPast() >
                            <br>
                            ${registration.getCurrency()}
                            <#if price == 0 >
                            ${languageUtil.get(locale, "dsd.theme.session.free")}
                        <#else>
                            <#assign vatText = languageUtil.get(locale, "dsd.theme.session.vat")?replace("%d", vat) />
                            ${registration.getPrice()}&nbsp;(${vatText})
                        </#if>
                            <br>

                            <#if registration.getEventId() gt 0 >
                            <#assign event = dsdParserUtils.getEvent(groupId, registration.getEventId()?string) />
                        </#if>

                            ${languageUtil.get(locale, "dsd.theme.session.room")} :
                            <#if room??>

                            ${room.getTitle()}
                            <#if event?? && event.findBuilding(room)?? >
                                <#assign building = event.findBuilding(room) />
                                -  ${languageUtil.get(locale, "dsd.theme.session.building")} : ${building.getTitle()}
                            </#if>
                        </#if>
                            <br>
                            <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
                            <#assign available = registration.getCapacity() - registrations />
                            ${languageUtil.get(locale, "dsd.theme.session.available")} : ${available}
                        </#if>

                    </span>
                    <br>

                    <#list registration.getPresenters() as presenter >
                        <#assign expert = presenter />
                        <#assign expertImageUrl = expert.getSmallImageURL(themeDisplay) />
                        <span>
                                <#if expertImageUrl?? && expertImageUrl != "">
                                    <img class="expert-data__image" src="${expertImageUrl}" />
                                </#if>
                                <a href="mailto:${expert.getEmail()}" >${expert.getName()}</a>
                            </span>
                    </#list>

                    <#if registration.canUserRegister(user.getUserId()) >
                        <#assign isRegistered = dsdSessionUtils.isUserRegisteredFor(user, registration) />
                        <span class="d-block">
                            <#if isRegistered >
                                <a href="${displayContext.getUnregisterURL(renderRequest)}" class="btn-lg btn-primary" role="button" aria-pressed="true">
                                    ${languageUtil.get(locale, "registrationform.unregister")}
                                </a>
                                &nbsp;
                                <a href="${displayContext.getUpdateURL(renderRequest)}" class="btn-lg btn-primary" role="button" aria-pressed="true">
                                     ${languageUtil.get(locale, "registrationform.update")}
                                </a>
                                &nbsp;
                            <#else>
                                <a href="#" data-article-id="${articleId}" class="btn-lg btn-primary add-to-cart" role="button" aria-pressed="true">
                                    ${languageUtil.get(locale, "shopping.cart.add")}
                                </a>
                            </#if>

                            <#assign joinLink = dsdSessionUtils.getUserJoinLink(user, registration) />
                            <#if joinLink?? && joinLink != "">
                                <a href="${joinLink}" target="-_blank" class="btn-lg btn-primary" role="button" aria-pressed="true">
                                     ${languageUtil.get(locale, "registrationform.join")}
                                </a>
                            </#if>
                        </span>
                    </#if>
                </p>
            </div>
        </div>
    </div>
    <div class="c-sessions__item__description">
        ${description.getData()}
    </div>

    <#if schedules?? && schedules.getSiblings()?has_content && validator.isNotNull(schedules.getSiblings()?first.getData())>

        <#list schedules.getSiblings() as cur_Schedule>
            <#if cur_Schedule.scheduleDate?has_content >
                <h3 class="c-sessions__item__title h1">
                    <#assign schedules_date_Data = getterUtil.getString(cur_Schedule.scheduleDate.getData())>
                    <#if validator.isNotNull(schedules_date_Data)>
                        <#assign schedules_date_DateObj = dateUtil.parseDate("yyyy-MM-dd", schedules_date_Data, locale)>
                        ${languageUtil.get(locale, "dsd.theme.session.schedule")} - ${dateUtil.getDate(schedules_date_DateObj, "dd MMM yyyy", locale)}
                    </#if>
                </h3>
            </#if>
            <div class="c-sessions__item__description">
                ${cur_Schedule.getData()}
            </div>
        </#list>

    </#if>
    <#if (registration.getPresentations()?size > 0) >
    <div class="c-events__item__uploads">
        <p class="bold">Presentaties</p>
        <#list registration.getPresentations() as presentation>

        <#if presentation.getThumbnailLink()?? >
            <#assign thumbnail = presentation.getThumbnailLink() />
        <#else>
            <#assign thumbnail = ""/>
        </#if>
        <div class="presentation">
            <#if presentation.isVideoLink() >
                <a href="${presentation.getJournalArticle().getUrlTitle()}" >
                    <#if thumbnail?? && thumbnail != "">
                        <img class="videoThumbnail" src="${thumbnail}" />
                    <#else>
                        <i class="icon-film"></i>
                    </#if>
                    <div class="presentation_title">
                        <strong>${presentation.getTitle()}</strong>
                </a>
            <#elseif presentation.isDownloadLink() >
                <a href="${presentation.getPresentationLink()}" class="">
                    <#if thumbnail?? && thumbnail != "">
                        <img class="videoThumbnail" src="${thumbnail}" />
                    <#else>
                        <i class="icon-download-alt"></i>
                    </#if>
                    <div class="presentation_title">
                        <strong>${presentation.getTitle()}</strong>
                </a>
            </#if>
            <p>
                &nbsp;&gt;&nbsp;
                <span>${presentation.getPresenter()}</span>
                <span>(${presentation.getOrganization()})</span>
            </p>
        </div>
    </div>
    </#list>
</div>
</#if>
</div>