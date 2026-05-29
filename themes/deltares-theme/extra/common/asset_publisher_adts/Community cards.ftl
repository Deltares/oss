<div class="grid grid-cols-10">
    <#if entries?has_content>
        <#list entries as curEntry>
            <#assign
            assetRenderer = curEntry.getAssetRenderer()
            journalArticle = assetRenderer.getAssetObject()
            />
            <div class="col-span-3 m-1 not-prose">
                <@liferay_journal["journal-article"]
                articleId=journalArticle.getArticleId()
                ddmTemplateKey="7582059"
                groupId=journalArticle.getGroupId()

                />
            </div>
        </#list>
    </#if>
</div>