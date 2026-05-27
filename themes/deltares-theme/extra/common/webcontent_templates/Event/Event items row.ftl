<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign eventId = .vars['reserved-article-id'].getData() />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign displayContext = dsdParserUtils.getDSDArticleDisplayContextInstance(groupId, eventId, themeDisplay) />
<#assign event = displayContext.getDsdArticle() />
<#assign location = event.getEventLocation() />
<#assign eventImageUrl = event.getSmallImageURL(themeDisplay) />
<#assign isEventPast><#if event.isEventInPast()>past-event<#else>upcoming-event</#if></#assign>

<#assign startDayString = dateUtil.getDate(event.getStartTime(), "dd", locale) >
<#assign startMonthString = dateUtil.getDate(event.getStartTime(), "MMM", locale) >
<#assign startDateString = dateUtil.getDate(event.getStartTime(), "dd MMMM yyyy", locale) >
<#assign startYearString=dateUtil.getDate(event.getStartTime(), "yyyy" , locale) />
<#assign locationString><#if location.isOnline() >${location.getTitle()}<#else>${location.getCity()}, ${location.getCountry()}</#if></#assign>
<#assign viewURL = displayContext.getViewURL(event) />
<#assign entryTitle = displayContext.getTitle() />
<div class="c-events__item ${isEventPast}">
    <div class="c-events__item__date">
        <span>${startDayString}</span>${startMonthString}
    </div>
    <h4 class="c-events__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${event.getTitle()}">${event.getTitle()}</a></h4>
    <p class="c-events__item__time-date-place">
      <span class="c-events__item__time-date-place__date">
        <#if event.isMultiDayEvent() >
            <#assign endDateString = dateUtil.getDate(event.getEndTime(), "dd MMMM yyyy", locale) >
            ${startDateString} - ${endDateString}
        <#else>
            ${startDateString}
        </#if>
      </span>
        <span class="c-events__item__time-date-place__time">${dateUtil.getDate(event.getStartTime(), "HH:mm", locale)} - ${dateUtil.getDate(event.getEndTime(), "HH:mm", locale)}</span>
        <span class="c-events__item__time-date-place__place">${locationString}</span>
    </p>
    <a class="c-events__item__link regular-text" href="${viewURL}"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Read more</a>
</div>