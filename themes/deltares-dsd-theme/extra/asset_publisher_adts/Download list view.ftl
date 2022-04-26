<!--container-->
<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />
<#assign sanctionUtils = serviceLocator.findService("nl.deltares.portal.utils.SanctionCheckUtils") />


<a href="#"
   onclick="showCountryCode('${request.getRemoteAddr()}')"
   class="btn-lg btn-primary" role="button" aria-pressed="true">
    Show with IP
</a>

<a href="#"
   onclick="showCountryCodeNoIP()"
   class="btn-lg btn-primary" role="button" aria-pressed="true">
    Show without IP
</a>
<#assign baseUrl = "/o/download" />
<#if entries?has_content>

    <#if is_sanctioned?? && is_sanctioned>
        <div class="lfr-status-alert-label" >${languageUtil.get(locale, "download.restriction.country")} ${sanctionCountry}
        </div>
    </#if>
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
                        <#if themeDisplay.isSignedIn() && !(is_sanctioned?? && is_sanctioned) >
                            <span class="d-block" style="float:right">
                            <#assign downloadStatus = downloadUtils.getDownloadStatus(download, themeDisplay.getUser()) />
                            <#assign directDownload = download.isDirectDownload() />
                            <#assign sendLink = download.isSendLink() />
                            <#switch downloadStatus >

                                <#case "payment_pending" >
                                    <#assign buttonText = languageUtil.get(locale, "shopping.cart.paymentPending")/>
                                    <#assign action = "none"/>
                                    <#break >
                                <#case "available">
                                    <#if directDownload >
                                        <#assign action = "directdownload"/>
                                        <#assign buttonText = languageUtil.get(locale, "download.download.again")/>
                                    <#elseif sendLink >
                                        <#assign action = "sendlink"/>
                                        <#assign buttonText = languageUtil.get(locale, "download.sendlink.again")/>
                                    <#else>
                                        <#assign action = "none"/>
                                        <#assign buttonText = languageUtil.get(locale, "shopping.cart.available")/>
                                    </#if>
                                    <#break >
                                <#case "processing">
                                    <#assign buttonText = languageUtil.get(locale, "shopping.cart.processing")/>
                                    <#assign action = "none"/>
                                    <#break >
                                <#default >
                                    <#if directDownload >
                                        <#assign action = "directdownload"/>
                                        <#assign buttonText = languageUtil.get(locale, "download.download")/>
                                    <#elseif sendLink >
                                        <#assign action = "sendlink"/>
                                        <#assign buttonText = languageUtil.get(locale, "download.sendlink")/>
                                    <#else>
                                        <#assign action = "addtocart"/>
                                        <#assign buttonText = languageUtil.get(locale, "shopping.cart.add")/>
                                    </#if>
                            </#switch>

                            <#switch action >
                                <#case "none">
                                    <a href="#" data-article-id="${download.getArticleId()}"
                                       class="btn-lg btn-primary disabled"
                                       role="button" aria-pressed="true" style="color:#fff">
                                        ${buttonText}
                                    </a>
                                    <#break />
                                <#case "directdownload">
                                    <#assign downloadUrl = baseUrl + "/direct/" />
                                    <a href="#" id="${journalArticle.getArticleId()}_download"
                                       onclick="directDownload(this.id,'${downloadUrl}', '${download.getFileId()}',
                                               '${download.getFilePath()}', '${download.getFileName()}', '${download.getArticleId()}',
                                               '${themeDisplay.getScopeGroupId()}')"
                                       class="btn-lg btn-primary" role="button" aria-pressed="true">
                                                ${buttonText}
                                    </a>
                                    <#break />
                                <#case "sendlink">
                                    <#assign downloadUrl = baseUrl + "/sendlink/" />
                                    <a href="#" id="${journalArticle.getArticleId()}_sendlink"
                                       onclick="sendLink(this.id, '${downloadUrl}', '${download.getFilePath()}', '${download.getArticleId()}',
                                               '${themeDisplay.getScopeGroupId()}')"
                                       class="btn-lg btn-primary" role="button" aria-pressed="true">
                                                ${buttonText}
                                    </a>
                                    <#break />
                                <#case "addtocart">
                                    <a href="#" data-article-id="${download.getArticleId()}"
                                       class="btn-lg btn-primary add-download-to-cart"
                                       role="button" aria-pressed="true" style="color:#fff">
                                            ${buttonText}
                                        </a>
                                <#break />
                            </#switch>

                            </span>
                        </#if>
                    </div>
                </div>
            </li>
        </#list>
    </ul>
</#if>

<script>

    function showCountryCodeNoIP(){
        $.ajax({
            type: "GET",
            url: "https://api.ipgeolocation.io/ipgeo?apiKey=2ca0fc5d24fd45ccab288bc461b880c4&fields=country_code2,country_name",
            contentType: "application/json",
            success : function(data, status, xhr) {
                if (xhr.status === 200){
                    json = JSON.stringify(data);
                    alert(json["country_code2"] + ' ' + json["country_name"])
                } else {
                    return "";
                }
            }
        });
    }

    function showCountryCode(ip){
        $.ajax({
            type: "GET",
            url: "https://api.ipgeolocation.io/ipgeo?apiKey=2ca0fc5d24fd45ccab288bc461b880c4&fields=country_code2,country_name&ip=" + ip,
            contentType: "application/json",
            success : function(data, status, xhr) {
                if (xhr.status === 200){
                    json = JSON.stringify(data);
                    alert(json["country_code2"] + ' ' + json["country_name"])
                } else {
                    return "";
                }
            }
        });
    }

    //Send link to user
    function sendLink(button_id, sendLinkUrl, filePath, articleId, groupId) {

        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: sendLinkUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"filePath\": \"" + filePath + "\"," +
                "\"resendLink\": \"true\"," +
                "\"downloadId\": \"" + articleId + "\"," +
                "\"groupId\": \"" + groupId + "\"" +
                "}",
            contentType: "application/json",
            success : function(response, status, xhr) {
                alert(xhr.responseText);
                location.reload();
            },
            failure : function(response, status, xhr) {
                alert(xhr.responseText);
            }
        });

    }

    //Get the direct download link for the file
    function directDownload(button_id, directDownloadUrl, fileId, filePath, fileName, articleId, groupId) {

        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: directDownloadUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"fileId\": \"" + fileId + "\"," +
                "\"filePath\": \"" + filePath + "\"," +
                "\"downloadId\": \"" + articleId + "\"," +
                "\"groupId\": \"" + groupId + "\"" +
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
                    location.reload();
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

</script>