<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign sessionId = .vars['reserved-article-id'].getData() />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign displayContext = dsdParserUtils.getDSDArticleDisplayContextInstance(groupId, sessionId, themeDisplay) />
<#assign session = displayContext.getDsdArticle() />
<#assign eventImageUrl = session.getSmallImageURL(themeDisplay) />
<#assign isEventPast><#if session.isEventInPast()>past-event<#else>upcoming-event</#if></#assign>

<#assign startDayString = dateUtil.getDate(session.getStartTime(), "dd", locale) >
<#assign startMonthString = dateUtil.getDate(session.getStartTime(), "MMM", locale) >
<#assign startDateString = dateUtil.getDate(session.getStartTime(), "dd MMMM yyyy", locale) >
<#assign startYearString=dateUtil.getDate(session.getStartTime(), "yyyy" , locale) />

<#if session.getEventId() gt 0 >
    <#assign event = dsdParserUtils.getEvent(groupId, session.getEventId()?string, themeDisplay.getLocale()) />
    <#assign location = event.getEventLocation() />
    <#assign locationString><#if location.isOnline() >${location.getTitle()}<#else>${location.getCity()}, ${location.getCountry()}</#if></#assign>
<#else>
    <#assign location = session.getRoom() />
    <#assign locationString = location.getTitle() />
</#if>
<#assign viewURL = displayContext.getViewURL(session) />
<#assign entryTitle = displayContext.getTitle() />
<div class="c-events__item ${isEventPast}">
    <div class="c-events__item__date">
        <span>${startDayString}</span>${startMonthString} ${startYearString}
    </div>
    <h4 class="c-events__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${session.getTitle()}">${session.getTitle()}</a></h4>
    <p class="c-events__item__time-date-place">
      <span class="c-events__item__time-date-place__date">
        <#if session.isMultiDayEvent() >
            <#assign endDateString = dateUtil.getDate(session.getEndTime(), "dd MMMM yyyy", locale) >
            ${startDateString} - ${endDateString}
        <#else>
            ${startDateString}
        </#if>
      </span>
        <span class="c-events__item__time-date-place__time">${dateUtil.getDate(session.getStartTime(), "HH:mm", locale)} - ${dateUtil.getDate(session.getEndTime(), "HH:mm", locale)}</span>
        <span class="c-events__item__time-date-place__place">${locationString}</span>
    </p>
    <a class="c-events__item__link regular-text" href="${viewURL}"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Read more</a>
</div>