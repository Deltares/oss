<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = parserUtils.toDsdArticle(groupId, articleId) />

<div class="cta_box">
    <strong>${download.getFileName()}</strong>&nbsp;
    ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()}
</div>
