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
                            <#assign downloadUrl = baseUrl + "/direct/" />
                            <a href="#" id="${journalArticle.getArticleId()}_download" onclick="directDownload(this.id,
                                    '${downloadUrl}', '${download.getFileId()}', '${download.getFileName()}')"
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

        updateButton(button_id, "Sending link...");
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
                updateButton(button_id, "Link sent")
            },
            failure : function(response, status, xhr) {
                alert(xhr.responseText);
            }
        });

    }

    //Get the direct download link for the file
    function directDownload(button_id, directDownloadUrl, fileId, fileName) {

        updateButton(button_id, "Downloading...")
        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: directDownloadUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"fileId\": \"" + fileId + "\"," +
                "\"fileName\": \"" + fileName + "\"" +
                "}",
            contentType: "application/json",
            xhrFields: {
                responseType: 'blob' // to avoid binary data being mangled on charset conversion
            },
            success : function(blob, status, xhr) {
                if (xhr.status !== 200){
                    alert(xhr.responseText);
                } else {
                    saveAs(blob, fileName);
                    updateButton(button_id, "Download completed")
                }
            },
            error : function(request, status, error) {
                alert(request.responseText);
            }
        });

    }

    //Open the download link in a new tab.
    function saveAs (blob, fileName) {

        var downloadUrl = window.URL.createObjectURL(blob);
        var a = document.createElement('a')
        a.href = downloadUrl;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();

        setTimeout(function () {
            window.URL.revokeObjectURL(downloadUrl);
            document.body.removeChild(a);
        }, 100); // cleanup

    }

    function updateButton(id, buttonText){
        let button = document.getElementById(id);
        button.classList.add('disabled');
        button.textContent = buttonText;
    }
</script>