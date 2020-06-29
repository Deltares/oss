<#assign date_Data = getterUtil.getString(date.getData())>
<#if validator.isNotNull(date_Data)>
    <#assign date_DateObj = dateUtil.parseDate("yyyy-MM-dd", date_Data, locale)>
    ${dateUtil.getDate(date_DateObj, "dd MMM yyyy", locale)}
</#if>