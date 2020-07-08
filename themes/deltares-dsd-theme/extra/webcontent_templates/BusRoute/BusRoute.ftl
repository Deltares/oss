<#assign journalArticleTitle = .vars['reserved-article-title'].data/>
${journalArticleTitle}
<p>
    <#if location.getSiblings()?has_content>
        <#list location.getSiblings() as cur_location>
            <#assign busstop = cur_location.getData()?eval />
            ${cur_location.time.getData()} - ${busstop.title}<br>
        </#list>
    </#if>
</p>