<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign busTransfer = dsdParserUtils.getRegistration(groupId , articleId) />
<#assign busStops = busTransfer.getStops() />
<strong>${busTransfer.getName()}</strong>
<#assign firstStop = true />
<p>
    <#list busStops as stop>
        <#if !firstStop >
            ->
        </#if>
        <#assign time = busTransfer.getTime(stop) />
        ${time} ${stop.getTitle()}
        <#assign firstStop = false />
    </#list>
</p>