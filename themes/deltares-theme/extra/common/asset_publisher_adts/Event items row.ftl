<!--container-->
<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />

<#if entries?has_content>

    <div class="c-events c-row">
        <#list entries as entry>
            <#assign assetRenderer=entry.getAssetRenderer() />
            <#assign article=assetRenderer.getArticle() />
            <#assign journalArticle=assetRenderer.getArticle() />
            <#assign event = dsdParserUtils.getEvent(article.getGroupId(),article.getArticleId()) />
            <#assign location = event.getEventLocation() />
            <#assign timeZoneId = event.getTimeZoneId() />
            <#assign timeZone = timeZoneUtil.getTimeZone(timeZoneId) />
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <#assign eventImageUrl = event.getSmallImageURL(themeDisplay) />
            <#assign isEventPast><#if (event.isEventInPast())>past-event<#else>upcoming-event</#if></#assign>

            <#assign startDayString = dateUtil.getDate(event.getStartTime(), "dd", locale, timeZone) >
            <#assign startMonthString = dateUtil.getDate(event.getStartTime(), "MMM", locale, timeZone) >
            <#assign startDateString = dateUtil.getDate(event.getStartTime(), "dd MMMM yyyy", locale, timeZone) >
            <#assign locationString><#if location.isOnline() >${location.getTitle()}<#else>${location.getCity()}, ${location.getCountry()}</#if></#assign>

            <div class="c-events__item ${isEventPast}">
                <div class="c-events__item__date">
                    <span>${startDayString}</span>${startMonthString}
                </div>
                <h4 class="c-events__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${event.getTitle()}">${event.getTitle()}</a></h4>
                <p class="c-events__item__time-date-place">
                    <span class="c-events__item__time-date-place__date">
                        <#if event.isMultiDayEvent() >
                            <#assign endDateString = dateUtil.getDate(event.getEndTime(), "dd MMMM yyyy", locale, timeZone) >
                            ${startDateString} - ${endDateString}
                        <#else>
                            ${startDateString}
                        </#if>
                    </span>
                    <span class="c-events__item__time-date-place__time">${dateUtil.getDate(event.getStartTime(), "HH:mm", locale, timeZone)} - ${dateUtil.getDate(event.getEndTime(), "HH:mm", locale, timeZone)}</span>
                    <span class="c-events__item__time-date-place__place">${locationString}</span>
                </p>
                <a class="c-events__item__link regular-text" href="${viewURL}"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Read more</a>
            </div>

        </#list>
    </div>
</#if>