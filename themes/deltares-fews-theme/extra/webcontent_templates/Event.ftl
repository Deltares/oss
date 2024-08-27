<#assign isEventPast = false />
<#assign EventDate_Data = getterUtil.getString(EventDate.getData())>
<#if EventDate_Data?has_content>
    <#if .now?date &gt; EventDate_Data?date("yyyy-MM-dd")>
        <#assign isEventPast = true />
    </#if>
    <#assign EventDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", EventDate_Data, locale)>
</#if>

<#assign EventEndDate_Data = getterUtil.getString(EventEndDate.getData())>
<#if EventEndDate_Data?has_content>
    <#assign EventEndDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", EventEndDate_Data, locale)>
</#if>

<div class="c-events page">
    <div class="container mx-auto">
        <div class="c-events__item">
            <div class="grid grid-cols-12 auto-rows-max">
                <div class="col-span-12 lg:col-span-8 lg:col-start-3">
                    <h1 class="flex font-medium text-3xl md:text-4xl lg:text-5xl text-theme-secondary mb-4 lg:!text-[48px] lg:!leading-[1.15] 2xl:!text-5xl 2xl:!leading-[1.15]">
                        <div aria-hidden="true" class="mr-3 lg:mr-4 2xl:mr-5 pt-3.5 md:pt-4 text-theme-quaternary grow-0 shrink-0 lg:pt-5 2xl:pt-6">
                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-2 h-2 lg:w-2.5 lg:h-2.5 2xl:w-3 2xl:h-3">
                                <circle fill="currentColor" cx="16" cy="16" r="15"></circle>
                            </svg>
                        </div>
                        <span>${.vars['reserved-article-title'].data}</span>
                    </h1>
                    <div class="c-events__item__date text-theme-secondary font-medium text-base lg:text-lg tracking-widest">
                        <p class="c-events__item__time-date-place">
                            <#if isEventPast>
                                <span class="font-bold"><@liferay.language key='fews.theme.event.pastevent' /></span>
                                <span class="text-theme-quaternary"> | </span>
                            </#if>
                            <span class="c-events__item__time-date-place__date">
                                ${dateUtil.getDate(EventDate_DateObj, "dd MMM yyyy", locale)}
                                <#if EventEndDate_Data?has_content>
                                    <span class="text-theme-quaternary"> - </span>${dateUtil.getDate(EventEndDate_DateObj, "dd MMM yyyy", locale)}
                                </#if>
                            </span>
                        </p>
                    </div>
                    <div class="c-events__item__introduction cms-content-simple pt-3 2xl:pr-24 text-base lg:text-lg 2xl:text-xl leading-6 font-medium text-theme-secondary mb-4">
                        <p>${EventIntroduction.getData()}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="grid grid-cols-12 auto-rows-max gap-6">
            <div class="col-span-12 lg:col-span-8 lg:col-start-3">
                <div class="c-events__item__date-panel">
                    <h2 class="text-theme-secondary text-xl font-bold mb-2"><@liferay.language key='fews.theme.event.panel.title' /></h2>
                    <hr class="border-theme-secondary border-t-[3px] lg:-mx-6">
                    <dl class="grid grid-cols-12 auto-rows-max gap-6">
                        <div class="col-span-6">
                            <dt class="block text-theme-secondary text-sm lg:text-base font-medium lg:font-bold mb-1"><@liferay.language key='fews.theme.event.panel.label.date' /></dt>
                            <dd class="block text-sm lg:text-base font-medium">
                                ${dateUtil.getDate(EventDate_DateObj, "dd MMM yyyy", locale)}
                                <#if EventEndDate_Data?has_content>
                                    <span> - </span>${dateUtil.getDate(EventEndDate_DateObj, "dd MMM yyyy", locale)}
                                </#if>
                            </dd>
                        </div>
                        <#if EventTime.getData()?has_content>
                            <div class="col-span-6">
                                <dt class="block text-theme-secondary text-sm lg:text-base font-medium lg:font-bold mb-1"><@liferay.language key='fews.theme.event.panel.label.time' /></dt>
                                <dd class="block text-sm lg:text-base font-medium"><span class="c-events__item__time-date-place__time">${EventTime.getData()}</span></dd>
                            </div>
                        </#if>
                        <div class="col-span-6">
                            <dt class="block text-theme-secondary text-sm lg:text-base font-medium lg:font-bold mb-1"><@liferay.language key='fews.theme.event.panel.label.location' /></dt>
                            <dd class="block text-sm lg:text-base font-medium">${EventLocation.getData()}</dd>
                        </div>
                    </dl>
                </div>
            </div>
        </div>
        <div class="grid grid-cols-12 auto-rows-max gap-6">
            <div class="col-span-12 lg:col-span-8 lg:col-start-3">
                <div class="c-events__item__image">
                    <#if EventImage.getData()?? && EventImage.getData() != "">
                        <img
                            class="c-events__item__image"
                            src="${EventImage.getData()}" />
                    </#if>
                </div>
                <div class="c-events__item__description prose prose--app">
                    ${EventFullDescription.getData()}
                </div>

                <#if PresentationTitle?? && PresentationTitle.getData() != "">
                    <#if PresentationTitle.getSiblings()?has_content>
                        <div class="c-events__item__uploads">
                            <h5 class="font-medium text-xl lg:text-2xl text-theme-secondary mb-3 lg:mb-3"><@liferay.language key='fews.theme.event.download.title' /></h5>
                            <ul>
                                <#list PresentationTitle.getSiblings() as cur_PresentationTitle>
                                    <li class="presentation mb-2">
                                        <a href="${cur_PresentationTitle.PresentationUpload.getData()}" target="_blank" class="relative inline-flex flex-row items-baseline text-app-blue--egyptian hover:underline v-hover--shade transition-colors duration-150 cursor-pointer font-medium">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start text-theme-quaternary shrink-0 w-3 h-3"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
                                            <span class="title">${cur_PresentationTitle.getData()}</span>
                                        </a>
                                        <#if cur_PresentationTitle.CompanyName.getSiblings()?has_content>
                                            <span class="item-metadata display-block">
                                                <#list cur_PresentationTitle.CompanyName.getSiblings() as cur_PresentationTitleCompanyName>
                                                    <#if cur_PresentationTitleCompanyName.PresenterName.getSiblings()?has_content>
                                                        <span class="presenters">
                                                            <#list cur_PresentationTitleCompanyName.PresenterName.getSiblings() as cur_PresentationTitleCompanyNamePresenterName>
                                                                <#assign prepWord = ",">
                                                                <#if cur_PresentationTitleCompanyNamePresenterName?is_last><#assign prepWord = "and"></#if>
                                                                <#if cur_PresentationTitleCompanyNamePresenterName?is_first><#assign prepWord = "By"></#if>
                                                                <span class="presenter-back">${prepWord}&nbsp;${cur_PresentationTitleCompanyNamePresenterName.getData()}</span>
                                                            </#list>
                                                        </span>
                                                    </#if>
                                                    <span class="company">${cur_PresentationTitleCompanyName.getData()}</span>
                                                </#list>
                                            </span>
                                        </#if>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    <#else>
                        <a class="c-events__item__register regulat-text" href="${EventRegistrationURL.getData()}" ><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Register now!</a>
                    </#if>
                </#if>
            </div>
        </div>
    </div>
</div>