<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign busTransfer = dsdParserUtils.getRegistration(groupId , articleId) />
<#assign busRoute = busTransfer.getBusRoute() />
<#assign busStops = busRoute.getStops() />
<#assign firstStop = true />
<strong>${busTransfer.getTitle()}</strong>
<p>
    <#list busStops as stop>
        <#if !firstStop >
            ->
        </#if>
        <#assign time = busRoute.getTime(stop) />
        ${time} ${stop.getTitle()}
        <#assign firstStop = false />
    </#list>
</p>