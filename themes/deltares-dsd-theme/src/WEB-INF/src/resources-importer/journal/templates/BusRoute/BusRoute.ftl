<#assign journalArticleTitle = .vars['reserved-article-title'].data/>
${journalArticleTitle}
<p>
    <#if location.getSiblings()?has_content>
        <#list location.getSiblings() as cur_location>
            <#assign busstopMap = cur_location.getData()?eval />
            <#assign cur_webContent_classPK = busstopMap.classPK />
            <#assign location = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />
            ${cur_location.time.getData()} - ${location.title}<br>
        </#list>
    </#if>
</p>