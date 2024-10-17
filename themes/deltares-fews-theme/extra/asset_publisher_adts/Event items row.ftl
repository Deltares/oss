<#if entries?has_content>
    <div class="c-events c-row">
        <#list entries as entry>
            <#assign assetRenderer=entry.getAssetRenderer() />
            <#assign article=assetRenderer.getArticle() />
            <#assign entryTitle=htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle=assetRenderer.getArticle() />
            <#assign document=saxReaderUtil.read(journalArticle.getContent()) />
            <#assign rootElement=document.getRootElement() />
            <#setting locale=locale.toString()>
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

            <#assign startDayString = "">
            <#assign startMonthString = "">
            <#assign startDateString = "">
            <#assign startYearString = "">
            <#assign endDateString = "">
            <#assign timeString = "">
            <#assign isEventPast = "upcoming-event" />
            <#assign locationString = "">

            <#list rootElement.elements() as dynamicElement>
                <#if "EventDate"==dynamicElement.attributeValue("name")>
                    <#assign startDate=dynamicElement.element("dynamic-content").getData() />
                    <#if startDate?has_content>
                        <#assign startDate_DateObj=startDate?date("yyyy-MM-dd") />
                        <#assign startDateString=dateUtil.getDate(startDate_DateObj, "dd MMMM yyyy" , locale) />
                        <#assign startDayString=dateUtil.getDate(startDate_DateObj, "dd" , locale) />
                        <#assign startMonthString=dateUtil.getDate(startDate_DateObj, "MMM" , locale) />
                        <#assign startYearString=dateUtil.getDate(startDate_DateObj, "yyyy" , locale) />
                        <#if .now?date &gt; startDate?date("yyyy-MM-dd")>
                            <#assign isEventPast = "past-event" />
                        </#if>
                    </#if>
                </#if>
                <#if "EventEndDate"==dynamicElement.attributeValue("name")>
                    <#assign endDate=dynamicElement.element("dynamic-content").getData() />
                    <#if endDate?has_content>
                        <#assign endDate_DateObj=endDate?date("yyyy-MM-dd") />
                        <#assign endDateString=dateUtil.getDate(endDate_DateObj, "dd MMMM yyyy" , locale) />
                    </#if>
                </#if>
                <#if "EventTime"==dynamicElement.attributeValue("name")>
                    <#assign timeString = dynamicElement.element("dynamic-content").getData() />
                </#if>
                <#if "EventLocation"==dynamicElement.attributeValue("name")>
                    <#assign locationString = dynamicElement.element("dynamic-content").getData() />
                </#if>
            </#list>
            <a class="type-inherit c-card-image-text text-theme-secondary rounded overflow-hidden h-full flex sm:flex-col sm:hover:shadow-md sm:focus:shadow-md sm:bg-white transition duration-200 hover:no-underline focus:no-underline hover:text-theme-secondary" href="${viewURL}" title="read more about ${entryTitle}">
                <div class="c-events__item flex flex-column w-full h-full ${isEventPast}">
                    <div class="c-events__item__date">
                        <span>${startDayString}&nbsp;</span>
                        <span>${startMonthString}&nbsp;</span>
                        <span>${startYearString}</span>
                    </div>
                    <h4 class="c-events__item__title font-medium text-lg lg:text-xl text-theme-secondary mb-3 lg:mb-2">${entryTitle}</h4>

                    <div class="c-events__item__time-date-place text-sm font-medium line-clamp-3 flex flex-column h-full">
                        <span class="c-events__item__time-date-place__date">
                            <#if endDateString != "">
                                ${startDateString} - ${endDateString}
                            <#else>
                                ${startDateString}
                            </#if>
                        </span>
                        <span class="c-events__item__time-date-place__time">${timeString}</span>
                        <span class="c-events__item__time-date-place__place display-block mt-2">${locationString}</span>

                        <footer class="mt-auto flex items-center justify-end">
                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="hidden sm:block sm:w-5 sm:h-5">
                                <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                            </svg>
                        </footer>
                    </div>
                </div>
            </a>
        </#list>
    </div>
</#if>