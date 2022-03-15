<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = parserUtils.toDsdArticle(groupId, articleId) />

<div class="download">
    <label for="${download.getFilePath()}">${download.getFileName()} ( ${download.getFileTypeName()}
        - ${download.getFileSize()} )</label>
</div>