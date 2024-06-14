<#-- DO NOT LOAD SESSIONS when in search resutls portlet -->
<#assign portletName = themeDisplay.getPortletDisplay().getPortletName() >
<#if !(portletName?ends_with("SearchResultsPortlet")) >

    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign urltitle=.vars['reserved-article-url-title'].data />
    <#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
    <#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
    <#assign timeZoneId = registration.getTimeZoneId() />
    <#assign timeZone = timeZoneUtil.getTimeZone(timeZoneId) />
    <#assign locale = themeDisplay.getLocale() />

    <div class="c-sessions page">
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
                            ${languageUtil.get(locale, "dsd.theme.session.schedule")} -
                            ${dateUtil.getDate(schedules_date_DateObj, "dd MMM yyyy", locale, timeZone)}
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
                <!--<p class="bold">${languageUtil.get(locale, "dsd.theme.session.presentations")}</p>-->
                <p class="bold">Go to presentation</p>
                <#list registration.getPresentations() as presentation>
                    <#if presentation.isDownloadLink() >
                        <#assign iconClass = "icon-download-alt" />
                    <#else >
                        <#assign iconClass = "icon-film" />
                    </#if>
                    <#if presentation.getThumbnailLink()?? >
                        <#assign thumbnail = presentation.getThumbnailLink() />
                    <#else>
                        <#assign thumbnail = "" />
                    </#if>
                    <#assign viewURL = displayContext.getViewURL(presentation) />
                    <div class="presentation">
                        <a href="${viewURL}">
                            <#if thumbnail?? && thumbnail != "">
                                <img class="videoThumbnail" src="${thumbnail}" alt="${presentation.getTitle()}"/>
                            <#else>
                                <i class=${iconClass}></i>
                            </#if>
                            <div class="presentation_title">
                                <strong>${presentation.getTitle()}</strong>
                            </div>
                        </a>
                        <#if presentation.getPresenter() != "" >
                            <div>
                                &nbsp;&gt;&nbsp;
                                <span>${presentation.getPresenter()}</span>
                                <span>(${presentation.getOrganization()})</span>
                            </div>
                        </#if>
                    </div>
                </#list>
            </div>
        </#if>
    </div>
</#if>