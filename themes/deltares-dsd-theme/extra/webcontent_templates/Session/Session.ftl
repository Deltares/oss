<#assign dsdUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdRegistrationUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign registration = dsdUtils.getRegistration(groupId,articleId) />
<#assign room = registration.getRoom() />
<#if registration.isEventInPast() >
    <#assign isEventPast = "past-event"/>
<#else>
    <#assign isEventPast = "upcoming-event"/>
</#if>
<#assign price = registration.getPrice() />
<div class="c-events page">
    <div class="c-events__item ${isEventPast}">
        <div class="clearfix">
            <div class="media-section">
                <#if eventImage.getData()?? && eventImage.getData() != "">
                    <img
                            class="c-events__item__image"
                            alt="${eventImage.getAttribute("alt")}"
                            data-fileentryid="${eventImage.getAttribute("fileEntryId")}"
                            src="${eventImage.getData()}" />
                </#if>
            </div>
            <div class="data-section">
                <div class="c-events__item__date">
                    <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale)}</span>
                    ${dateUtil.getDate(registration.getStartTime(), "MMM", locale)}
                </div>
                <h3 class="c-events__item__title h1">${registration.getTitle()}</h3>
                <#if !registration.isOpen() >
                    <b>${languageUtil.get(locale, "dsd.theme.session.closed")}</b>
                </#if>
                <p class="c-events__item__time-date-place">
                    <span class="c-events__item__time-date-place__date">
                        ${dateUtil.getDate(registration.getStartTime(), "dd MMM yyyy", locale)}
                        <#if registration.isMultiDayEvent() >
                            &nbsp;-&nbsp;${dateUtil.getDate(registration.getEndTime(), "dd MMM yyyy", locale)}
                        </#if>
                    </span>
                    <span class="c-events__item__time-date-place__time">
                        ${dateUtil.getDate(registration.getStartTime(), "HH:mm", locale)} -
                        ${dateUtil.getDate(registration.getEndTime(), "HH:mm", locale)}</span>
                    <br>
                    <span class="c-events__item__time-date-place__place">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()}.png"> ${registration.getType()}&nbsp;
                        <br>
                        ${registration.getCurrency()}
                        <#if price == 0 >
                            ${languageUtil.get(locale, "dsd.theme.session.free")}&nbsp;
                        <#else>
                            ${registration.getPrice()}&nbsp;
                        </#if>
                        <br>
                        <#assign registrations = dsdUtils.getRegistrationCount(registration) />
                        <#assign available = registration.getCapacity() - registrations />
                        ${room.getTitle()} ( ${languageUtil.get(locale, "dsd.theme.session.available")} : ${available} )
                    </span>

                </p>
            </div>
        </div>
    </div>
    <div class="c-events__item__description">
        ${description.getData()}
    </div>

    <#if schedules?? && schedules.getSiblings()?has_content && validator.isNotNull(schedules.getSiblings()?first.getData())>

        <#list schedules.getSiblings() as cur_Schedule>
            <h3 class="c-events__item__title h1">
                <#assign schedules_date_Data = getterUtil.getString(cur_Schedule.date.getData())>
                <#if validator.isNotNull(schedules_date_Data)>
                    <#assign schedules_date_DateObj = dateUtil.parseDate("yyyy-MM-dd", schedules_date_Data, locale)>
                    ${languageUtil.get(locale, "dsd.theme.session.schedule")} - ${dateUtil.getDate(schedules_date_DateObj, "dd MMM yyyy", locale)}
                </#if>
            </h3>
            <div class="c-events__item__description">
                ${cur_Schedule.getData()}
            </div>
        </#list>

    </#if>
</div>