<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign timeZoneId = registration.getTimeZoneId() />
<div class="row no-gutters">
    <div class="col-2">
        <img class="img-fluid" src="${displayContext.getSmallImageURL()}"/>
    </div>
    <div class="col-10 px-3">
        <h4>
            <a href="#">
                <strong>${title}</strong>
            </a>
        </h4>
        <div>
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
                    <#assign first = true />
                    <#list periods as period >
                        <span class="c-sessions__item__time-date-place__date">
                            ${dateUtil.getDate(period.getStartDate(), "dd MMM yyyy", locale)}
                            </span>
                        <span class="c-sessions__item__time-date-place__time">
                            ${dateUtil.getDate(registration.getEndTime(), "HH:mm", locale)} - ${dateUtil.getDate(registration.getEndTime(), "HH:mm", locale)} (${timeZoneId})
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
            <#assign count = displayContext.getPresenterCount()/>
            <#if count gt 0>
                <#list 0..(count-1) as i >
                    <#assign imageUrl = displayContext.getPresenterSmallImageURL(i) />
                    <#if imageUrl?has_content >
                        <img width="32" class="expert-thumbnail" src="${imageUrl}"/>
                    </#if>
                    <#assign name = displayContext.getPresenterName(i) />
                    <#if name?has_content>
                        <span class="expert-name px-2">${name}</span> |
                    </#if>
                </#list>
            </#if>

            <span class="event-time pl-2">${displayContext.getStartTime()} - ${displayContext.getEndTime()}</span> |
            <#if displayContext.getPrice() gt 0 >
                ${displayContext.getCurrency()} ${displayContext.getPrice()}
            <#else>
                ${languageUtil.get(locale, "dsd.theme.session.free")}
            </#if>
        </div>

        <p class="search-document-content text-default">
            ${displayContext.getSummary()}
        </p>
    </div>
</div>