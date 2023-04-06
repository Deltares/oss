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

    <#assign price = registration.getPrice() />
    <#assign vat = registration.getVAT() />
    <#if registration.getCapacity() == 0 >
        <#assign available = ""  />
    <#else>
        <#assign registrations = dsdSessionUtils.getRegistrationCount(registration) />
        <#assign available = registration.getCapacity() - registrations />
    </#if>
    <#assign locale = themeDisplay.getLocale() />

    <style type="text/css">
        .text-theme-button{
            text-weight:500;
            padding-left: 1rem;
            padding-right: 1rem;
            text-decoration: none;
        }
        @media (min-width: 640px){
            .d-grid-cols-2 {
                grid-template-columns: repeat(2,minmax(0,1fr));
            }
        }
    </style>

    <div class="c-sessions page">
        <div class="c-sessions__item ${isEventPast}">
            <div class="clearfix">

                <div class="data-section">
                    <div class="c-sessions__item__date">
                        <#if registration.isToBeDetermined() >
                            <span></span>
                        <#else>
                            <span>${dateUtil.getDate(registration.getStartTime(), "dd", locale, timeZone)}</span>
                            ${dateUtil.getDate(registration.getStartTime(), "MMM", locale, timeZone)}
                        </#if>
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
                        <#elseif registration.isToBeDetermined() >
                            <span class="c-sessions__item__time-date-place__date">
                            ${languageUtil.get(locale, "dsd.theme.session.tobedetermined")}
                        </span>
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
                        <dev class="items-line">
                        <img src="${themeDisplay.getPathThemeImages()}/dsd/${registration.getType()?lower_case}.png"
                             alt=""> &nbsp; ${typeDisplayName} </img>
                         </dev>
                        <#assign calDescription += typeDisplayName + "<br/>"/>
                        <br/>
                        <br/>
                        ${registration.getCurrency()}
                            <#assign calDescription += registration.getCurrency()/>
                            <#if price == 0 >
                                ${languageUtil.get(locale, "dsd.theme.session.free")}
                                <#assign calDescription += (languageUtil.get(locale, "dsd.theme.session.free") + "<br/>") />
                            <#else>
                                <#assign vatText = languageUtil.get(locale, "dsd.theme.session.vat")?replace("%d", vat) />
                                ${registration.getPrice()}&nbsp;(${vatText})
                                <#assign calDescription += (registration.getPrice() + "&nbsp;" +  vatText + "<br/>") />
                            </#if>
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
                        <#if registration.canUserRegister(user.getUserId()) && themeDisplay.isSignedIn()>
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
                                &nbsp;
                            <#else >
                                <a href="#" data-article-id="${articleId}" class="btn-lg btn-primary add-to-cart"
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
                <p class="bold">${languageUtil.get(locale, "dsd.theme.session.presentations")}</p>
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
        <#-- This section below is need to translate Registration text based on Language selection -->
        <#if RegistrationInfo?? && RegistrationInfo.getData()?? &&  RegistrationInfo.getData() != "">
            <#assign cur_webContent_map = RegistrationInfo.getData()?eval />
            <#assign cur_webContent_classPK = cur_webContent_map.classPK />
            <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />
            <#if article?has_content && article.getStatus() == 0>
                <#if Language?? && Language.getData()?? && Language.getData() == "nl" >
                    <#assign localeId = "nl_NL" />
                <#else>
                    <#assign localeId = locale.toString() />
                </#if>
                <div class="c-sessions__item__description">
                    ${journalContent.getContent(article.getGroupId(), article.getArticleId(), viewMode, localeId)}

                </div>
            </#if>
        </#if>
        <#if ExternalLink?? && ExternalLink.getData()?has_content >

            <div class="c-sessions__item__description">
                <h3 class="font-medium text-xl lg:text-2xl text-theme-secondary mb-1 lg:mb-2"><@liferay.language key='dsd.theme.session.goto.product' /></h3>
                <#list ExternalLink.getSiblings() as cur_ExternalLink>
                    <div class="group flex">
                        <div class="shrink-0 px-3 py-3 bg-theme-button group-hover:bg-theme-button--hover group-focus:bg-theme-button--hover transition duration-200 rounded-l">
                            <a href="${cur_ExternalLink.ExternalURL.getData()}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewbox="0 0 32 32" aria-hidden="false" role="img"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
                            </a>
                        </div>
                        <div class="flex bg-white items-center font-semibold rounded-r  w-full">
                            <h4>
                                <a class="text-theme-button" href="${cur_ExternalLink.ExternalURL.getData()}">${cur_ExternalLink.getData()}</a>
                            </h4>
                        </div>
                    </div>
                </#list>
            </div>

        </#if>
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
    </script>
</#if>