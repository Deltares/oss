<#assign schedules_date_Data = getterUtil.getString(schedules.date.getData())>
<#if validator.isNotNull(schedules_date_Data)>
    <#assign schedules_date_DateObj = dateUtil.parseDate("yyyy-MM-dd", schedules_date_Data, locale)>
    ${dateUtil.getDate(schedules_date_DateObj, "dd MMM yyyy", locale)}
</#if>