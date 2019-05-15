<#assign EventDate_Data = getterUtil.getString(EventDate.getData())>
<#if EventDate_Data?has_content>
    <#assign EventDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", EventDate_Data, locale)>
</#if>

<#assign EventEndDate_Data = getterUtil.getString(EventEndDate.getData())>
<#if EventEndDate_Data?has_content>
	<#assign EventEndDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", EventEndDate_Data, locale)>
</#if>

<div class="c-events page">
    <div class="c-events__item">
        <div class="clearfix">
            <div class="media-section">
                <#if EventImage.getData()?? && EventImage.getData() != "">
                    <img
                        class="c-events__item__image"
                        alt="${EventImage.getAttribute("alt")}"
                        data-fileentryid="${EventImage.getAttribute("fileEntryId")}"
                        src="${EventImage.getData()}" />
                </#if>
            </div>
            <div class="data-section">
                <div class="c-events__item__date">
                    <#if EventDate_Data?has_content>
                        <span>${dateUtil.getDate(EventDate_DateObj, "dd", locale)}</span>
                        ${dateUtil.getDate(EventDate_DateObj, "MMM", locale)}
                    </#if>
                </div>
                <h3 class="c-events__item__title h1">${.vars['reserved-article-title'].data}</h3>
                <p class="c-events__item__time-date-place">
                    <span class="c-events__item__time-date-place__date">
                        ${dateUtil.getDate(EventDate_DateObj, "dd MMM yyyy", locale)}
                        <#if EventEndDate_Data?has_content>
                            &nbsp;-&nbsp;${dateUtil.getDate(EventEndDate_DateObj, "dd MMM yyyy", locale)}
                        </#if>
                    </span>
                    <span class="c-events__item__time-date-place__time">${EventTime.getData()}</span>
                    <span class="c-events__item__time-date-place__place">${EventLocation.getData()}</span>
                </p>

                <div class="c-events__item__introduction">
                    <p class="font-weight-regular">${EventIntroduction.getData()}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="c-events__item__description">
        ${EventFullDescription.getData()}
    </div>

    
    <#if PresentationTitle?? && PresentationTitle.getData() != "">
        
        <#if PresentationTitle.getSiblings()?has_content>

        <div class="c-events__item__uploads">
            <p class="bold">Download presentations</p>
            <p class="presentation">
                <#list PresentationTitle.getSiblings() as cur_PresentationTitle>
                    <a href="${PresentationTitle.PresentationUpload.getData()}" class="link regular-text">
                        <span class="underline">${cur_PresentationTitle.getData()}</span>&nbsp;&gt;&nbsp;
                    </a>
                </#list>
                <#if PresentationTitle.CompanyName.getSiblings()?has_content>
                    <#list PresentationTitle.CompanyName.getSiblings() as cur_PresentationTitleCompanyName>
                        <#if PresentationTitle.CompanyName.PresenterName.getSiblings()?has_content>
                        <span class="presenters">
                            <#list PresentationTitle.CompanyName.PresenterName.getSiblings() as cur_PresentationTitleCompanyNamePresenterName>
                                <span class="presenter">
                                    ${cur_PresentationTitleCompanyNamePresenterName.getData()}
                                </span>
                            </#list>
                        </span>
                        </#if>
                        <span class="company">${cur_PresentationTitleCompanyName.getData()}</span>
                    </#list>
                </#if>
            </p>
        </div>
        <#else>
            <a class="c-events__item__register regulat-text" href="${EventRegistrationURL.getData()}" ><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Register now!</a>
        </#if>
    </#if>

</div>