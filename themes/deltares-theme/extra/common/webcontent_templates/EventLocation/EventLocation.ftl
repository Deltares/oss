<#if buildings.getSiblings()?has_content>
    <#list buildings.getSiblings() as cur_buildings>
        ${cur_buildings.getData()}
    </#list>
</#if>
<#if rooms.getSiblings()?has_content>
    <#list rooms.getSiblings() as cur_rooms>
        ${cur_rooms.getData()}
    </#list>
</#if>