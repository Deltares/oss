<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = dsdParserUtils.toDsdArticle(themeDisplay.getScopeGroupId(), articleId) />
<#assign showButtons = themeDisplay.isSignedIn() />
<#assign directDownload = download.isDirectDownload() />
<#assign baseUrl = "/o/download" />

<div class="row no-gutters">

    <div class="col-12 px-3">
        <h4>
            <strong>${download.getFileName()}</strong>
        </h4>
        <div>
            ${download.getFileType()} | ${download.getFileSize()} | <i>todo</i> downloads
            <#if showButtons >
                <span class="d-block" style="float:right">
                    <#if directDownload >
                        <#assign downloadUrl = baseUrl + "/sendlink" />
                        <a href="#" onclick="sendLink('${downloadUrl}', '${download.getFilePath()}')"
                           class="btn btn-primary" role="button" aria-pressed="true">Download
                        </a>
                    <#else>
                        <a href="#" data-article-id="${download.getArticleId()}"
                           class="btn-lg btn-primary add-to-cart"  role="button"
                           aria-pressed="true" style="color:#fff">
                            ${languageUtil.get(locale, "shopping.cart.add")}
                        </a>
                    </#if>
                </span>

            </#if>
        </div>

    </div>
</div>

<script>

    function sendLink(sendLinkUrl, filePath) {
        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: sendLinkUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"filePath\": \"" + filePath + "\"" +
                "\"resendLink\": \"true\"" +
                "}",
            contentType: "application/json",
            success : function(response, status, xhr) {
                alert(xhr.responseText);
            },
            failure : function(response, status, xhr) {
                alert(xhr.responseText);
            }
        });

    }
</script>