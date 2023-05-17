<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = parserUtils.toDsdArticle(groupId, articleId) />
<#assign count = downloadUtils.getDownloadCount(download) />

<div class="cta_box">
    <strong>${download.getFileName()}</strong>&nbsp;
    ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()} | ${count} downloads
</div>
