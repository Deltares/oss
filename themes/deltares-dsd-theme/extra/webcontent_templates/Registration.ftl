${name.getData()}
<#assign start_Data = getterUtil.getString(start.getData())>
<#if validator.isNotNull(start_Data)>
    <#assign start_DateObj = dateUtil.parseDate("yyyy-MM-dd", start_Data, locale)>
    ${dateUtil.getDate(start_DateObj, "dd MMM yyyy", locale)}
</#if>
<#assign end_Data = getterUtil.getString(end.getData())>
<#if validator.isNotNull(end_Data)>
    <#assign end_DateObj = dateUtil.parseDate("yyyy-MM-dd", end_Data, locale)>
    ${dateUtil.getDate(end_DateObj, "dd MMM yyyy", locale)}
</#if>
${type.getData()}
${topic.getData()}
${price.getData()}
${capacity.getData()}