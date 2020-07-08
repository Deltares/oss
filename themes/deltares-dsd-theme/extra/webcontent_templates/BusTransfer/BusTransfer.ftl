<#assign journalArticleTitle = .vars['reserved-article-title'].data >
${journalArticleTitle}
<#assign pickupOption = pickupDates.getData()>
<p>
    <#if pickupOption == "daily">
        <#assign start_Data = getterUtil.getString(start.getData())>
        <#assign start_DateObj = dateUtil.parseDate("yyyy-MM-dd", start_Data, locale)>
        <#assign end_Data = getterUtil.getString(end.getData())>
        <#assign end_DateObj = dateUtil.parseDate("yyyy-MM-dd", end_Data, locale)>
        Daily from ${dateUtil.getDate(start_DateObj, "dd MMM yyyy", locale)}  to ${dateUtil.getDate(end_DateObj, "dd MMM yyyy", locale)}
    <#else>
        On the following days:<br>
        <#if date.getSiblings()?has_content>
            <#list date.getSiblings() as cur_date>
                <#assign date_Data = getterUtil.getString(cur_date.getData())>
                <#if validator.isNotNull(date_Data)>
                    <#assign date_DateObj = dateUtil.parseDate("yyyy-MM-dd", date_Data, locale)>
                    ${dateUtil.getDate(date_DateObj, "dd MMM yyyy", locale)}, <br>
                </#if>
            </#list>
        </#if>
    </#if>
</p>
<p>
    Route: <br>
    <#if busRoute?? && busRoute.getData()?? &&  busRoute.getData() != "">
        <#assign busRouteMap = busRoute.getData()?eval />
        <#assign cur_webContent_classPK = busRouteMap.classPK />
        <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

        <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
            ${journalContent.getContent(article.getGroupId(), article.getArticleId(), viewMode, locale.getLanguage())}
        </#if>
    </#if>

</p>