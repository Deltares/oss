
<#if entries?has_content>

<div class="c-events c-column">
    <#list entries as entry>        
        <#assign assetRenderer=entry.getAssetRenderer() />
        <#assign article=assetRenderer.getArticle() />
        <#assign entryTitle=htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle=assetRenderer.getArticle() />
        <#assign document=saxReaderUtil.read(journalArticle.getContent()) />
        <#assign rootElement=document.getRootElement() />
        <#setting locale=locale.toString()>
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

        <#assign startDayString = "" >
        <#assign startMonthString = "" >
        <#assign startDateString = "" >
        <#assign endDateString = "" >
        <#assign timeString = "" >      
        <#assign isEventPast = "upcoming-event" />
        <#assign locationString = "">
    
        <#list rootElement.elements() as dynamicElement>
            <!--  ${dynamicElement.attributeValue("name")} : ${dynamicElement.element("dynamic-content").getData()}  -->
            <#if "EventDate"==dynamicElement.attributeValue("name")>
                <#assign startDate=dynamicElement.element("dynamic-content").getData() />
                <#if startDate?has_content>
                    <#assign startDate_DateObj=startDate?date("yyyy-MM-dd") />
                    <#assign startDateString=dateUtil.getDate(startDate_DateObj, "dd MMMM yyyy" , locale) />
                    <#assign startDayString=dateUtil.getDate(startDate_DateObj, "dd" , locale) />
                    <#assign startMonthString=dateUtil.getDate(startDate_DateObj, "MMM" , locale) />
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
        <div class="c-events__item ${isEventPast}">
            <div class="c-events__item__date">
                <span>${startDayString}</span>
                ${startMonthString}
            </div>
            <h4 class="c-events__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
            <p class="c-events__item__time-date-place">
                <#if endDateString != "">
                    <span class="c-events__item__time-date-place__date">${startDateString} - ${endDateString}</span>
                </#if>
                <span class="c-events__item__time-date-place__time">${timeString}</span>
                <span class="c-events__item__time-date-place__place">${locationString}</span>
            </p> 
            <a class="c-events__item__link regular-text" href="${viewURL}"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Read more</a>
        </div>
    
    </#list>
</div>
</#if>

