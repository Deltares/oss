<!--container-->
<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign baseUrl = "/o/download" />
<#if entries?has_content>

    <div class="">
        <ul class="c-downloads-list clear-list">

            <#list entries as entry>
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign download = parserUtils.toDsdArticle(journalArticle, locale) />
                <#assign directDownload = download.isDirectDownload() />
                <#assign sendLink = download.isSendLink() />
                <!--repeatable element-->
                <li class="c-downloads-list__item">
                    <label for="${download.getFilePath()}">${download.getFileName()} ( ${download.getFileTypeName()}
                        - ${download.getFileSize()} )</label>
                    <#if themeDisplay.isSignedIn() >
                        <#if directDownload >
                            <#assign downloadUrl = baseUrl + "/direct/" + download.getFileId() />
                            <a href="#" id="${journalArticle.getArticleId()}_download" onclick="directDownload(this.id, '${downloadUrl}', '${download.getFilePath()}')"
                               class="btn btn-primary" role="button" aria-pressed="true">Download
                            </a>
                        <#elseif sendLink >
                            <#assign downloadUrl = baseUrl + "/sendlink/" />
                            <a href="#" id="${journalArticle.getArticleId()}_sendlink" onclick="sendLink(this.id, '${downloadUrl}', '${download.getFilePath()}')"
                               class="btn btn-primary" role="button" aria-pressed="true">Send Link
                            </a>
                        <#else>
                            <a href="#" data-article-id="${download.getArticleId()}" class="btn-lg btn-primary add-to-cart"
                               role="button"
                               aria-pressed="true" style="color:#fff">
                                ${languageUtil.get(locale, "shopping.cart.add")}
                            </a>
                        </#if>
                    </#if>
                </li>
            </#list>
        </ul>

    </div>
</#if>

<script>

    //Send link to user
    function sendLink(button_id, sendLinkUrl, filePath) {

        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: sendLinkUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"filePath\": \"" + filePath + "\"," +
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

    //Get the direct download link for the file
    function directDownload(button_id, directDownloadUrl, fileName) {

        let pAuth = Liferay.authToken;
        $.ajax({
            type: "GET",
            async: "true",
            url: directDownloadUrl + '?p_auth=' + pAuth,
            success : function(response, status, xhr) {
                if (xhr.status !== 200){
                    alert(xhr.responseText);
                } else {
                    saveAs(response, fileName);
                }
            },
            failure : function(response, status, xhr) {
                alert(xhr.responseText);
            }
        });

    }

    //Open the download link in a new tab.
    function saveAs (url, fileName) {
        var a = $("<a />");
        a.attr("download", fileName);
        a.attr("href", url);
        a.attr("target", "_blank");
        $("body").append(a);
        a[0].click();
        $("body").remove(a);
    }
</script>