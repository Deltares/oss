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
                <!--repeatable element-->
                <li class="c-downloads-list__item">
                    <label for="${download.getFilePath()}">${download.getFileName()} ( ${download.getFileType()}
                        - ${download.getFileSize()} )</label>
                    <#if themeDisplay.isSignedIn() >
                        <#if directDownload >
<#--                            <#assign downloadUrl = baseUrl + "/direct/" + download.getFileId() />-->
                            <#assign downloadUrl = baseUrl + "/sendlink" />
                            <a href="#" onclick="sendLink('${downloadUrl}', '${download.getFilePath()}')"
                               class="btn btn-primary" role="button" aria-pressed="true">Download
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

    function directDownload(directDownloadUrl, fileName) {
        let pAuth = Liferay.authToken;
        $.ajax({
            type: "GET",
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

    function saveAs (url, fileName) {
        var link = document.createElement("a");
        link.download = fileName;
        link.href = url;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        delete link;
    }
</script>