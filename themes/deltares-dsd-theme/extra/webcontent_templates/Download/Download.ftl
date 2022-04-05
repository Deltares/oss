<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = parserUtils.toDsdArticle(groupId, articleId) />
<#assign count = downloadUtils.getDownloadCount(download) />

<div class="row no-gutters">
    <div class="col-12 px-3">
        <h4>
            <a href="${download.getGroupPage()}" target="_blank">
                <strong>${download.getFileName()}</strong>
            </a>
        </h4>
        <div>
            ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()} | ${count} downloads
        </div>
    </div>
</div>