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
Local = ${locale}
<div class="c-events page">
    <div class="c-events__item ${isEventPast}">
        <div class="clearfix">
            <div class="data-section">
                <div class="c-events__item__date">
                    <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale)}</span>
                    ${dateUtil.getDate(registration.getStartTime(), "MMM", locale)}
                </div>
                <h3 class="c-events__item__title h1">${registration.getTitle()}</h3>
                <#if !registration.isOpen() >
                    <b>${languageUtil.get(locale, "registration.closed")}</b>
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

                    <span class="c-events__item__time-date-place__place">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()}.png"> ${registration.getType()}&nbsp;
                        ${registration.getCurrency()}
                        <#if price == 0 >
                            ${languageUtil.get(locale, "price.free")}&nbsp;
                        <#else>
                            ${registration.getPrice()}&nbsp;
                        </#if>


                        ${room.getTitle()}
                    </span>

                </p>
            </div>
        </div>
    </div>
    <div class="c-events__item__description">
        ${description.getData()}
    </div>

</div>