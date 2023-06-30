<!--container-->
<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />

<#assign baseUrl = "/o/download" />
<#assign showButtons = true />
<#assign isLoggedIn = themeDisplay.isSignedIn() />
<#if entries?has_content>

    <ul class="c-downloads-list clear-list">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign download = parserUtils.toDsdArticle(journalArticle, locale) />
            <#assign count = downloadUtils.getDownloadCount(download) />
            <li class="list-group-item list-group-item-flex">
                <div class="col-12 px-3">
                    <h4>
                        <strong>${download.getFileName()}</strong>
                    </h4>
                    <div>
                        ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()} | ${count} downloads
                        <#if showButtons >
                            <span class="d-block" style="float:right">

                                <#assign directDownload = download.isDirectDownload() />
                                <#if directDownload >
                                    <span class="d-block" style="float:right">
                                        <#assign registerUrl = baseUrl + "/register/" />
                                        <#assign fileName = download.getFileName() />
                                        <#assign shareLink = download.getFilePath() />
                                        <#assign articleId = journalArticle.getArticleId() />
                                        <#assign buttonText = languageUtil.get(locale, "download.download")/>
                                        <a href="${shareLink}" target="_blank" id="${articleId}_download" onclick="registerClick('${registerUrl}', '${fileName}', '${shareLink}', '${articleId}', '${themeDisplay.getScopeGroupId()}')"
                                           class="btn-lg btn-primary" role="button" aria-pressed="true">
                                                        ${buttonText}
                                        </a>
                                    </span>
                                <#elseif isLoggedIn>
                                    <#assign buttonText = languageUtil.get(locale, "shopping.cart.add")/>
                                    <a href="#" data-article-id="${download.getArticleId()}"
                                       class="btn-lg btn-primary add-download-to-cart"
                                       role="button" aria-pressed="true" style="color:#fff" >
                                            ${buttonText}
                                    </a>
                                </#if>

                            </span>
                        </#if>
                    </div>
                    <div id="${download.getArticleId()}-alert" class="alert alert-dismissible hidden" role="alert">
                        <button aria-label="Close" class="close" data-dismiss="alert" type="button">
                            <span id="download-alert-icon">
                                <svg aria-hidden="true" class="lexicon-icon lexicon-icon-times" focusable="false" viewBox="0 0 512 512">
                                    <path class="lexicon-icon-outline" d="M301.1,256.1L502.3,54.9c30.1-30.1-16.8-73.6-45.2-45.2L255.9,210.8L54.6,9.7C24.6-20.4-19,26.5,9.4,54.9l201.2,201.2L9.3,457.3c-28.9,28.9,15.8,74.6,45.2,45.2l201.3-201.2l201.3,201.2c28.9,28.9,74.2-16.3,45.2-45.2L301.1,256.1z"></path>
                                </svg>
                            </span>
                            <span class="sr-only">Close</span>
                        </button>

                        <div id="${download.getArticleId()}-message" />
                    </div>
                </div>
            </li>
        </#list>
    </ul>
</#if>

<script>

    function registerClick(registerUrl, fileName, shareLink, articleId, groupId){
        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: registerUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"fileName\": \"" + fileName + "\"," +
                "\"fileShare\": \"" + shareLink + "\"," +
                "\"downloadId\": \"" + articleId + "\"," +
                "\"groupId\": \"" + groupId + "\"" +
                "}",
            contentType: "application/json",
            success : function(response, status, xhr) {
                logSuccess(articleId, response)
            },
            error : function(request, status, error) {
                logError(articleId, request.responseText)
            }
        });
    }

    function logSuccess(id, response) {
        let alertEl = document.getElementById(id + "-alert");
        alertEl.classList.replace("hidden", "alert-success");
        let messageEl = document.getElementById(id + "-message");
        messageEl.innerHTML = "<strong class=\"lead\">Success:</strong>" + response
    }

    function logError(id, error) {
        let alertEl = document.getElementById(id + "-alert");
        alertEl.classList.replace("hidden", "alert-warning");
        let messageEl = document.getElementById(id + "-message");
        messageEl.innerHTML = "<strong class=\"lead\">Warning:</strong>" + error

    }
</script>