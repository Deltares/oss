<!--container-->
<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />

<#assign baseUrl = "/o/download" />
<#assign buttonText = languageUtil.get(locale, "download.download")/>
<#assign showButtons = themeDisplay.isSignedIn() />

<#if entries?has_content>

    <div id="messageBox" />
    <#if showButtons>
        <#if is_sanctioned?? && is_sanctioned >
            <#assign showButtons = false />
            <div class="lfr-status-alert-label" >${languageUtil.get(locale, "download.restriction.country")} ${sanctionCountry}
            </div>
        </#if>
    <#else>
        <div class="lfr-status-info-label" >
            <div id="login_link"> </div>
        </div>
    </#if>

    <ul class="c-downloads-list clear-list">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign download = parserUtils.toDsdArticle(journalArticle, locale) />
            <#assign count = downloadUtils.getDownloadCount(download) />
            <#assign multipleDownloadUrls = downloadUtils.hasMultipleDownloadUrls() />
            <#assign isShareLink = download.isShareLink() />
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
                                    <#assign fileName = download.getFileName() />
                                    <#assign filePath = download.getFilePath() />
                                    <#assign articleId = journalArticle.getArticleId() />
                                    <span class="d-block" style="float:right">
									<#if multipleDownloadUrls && !isShareLink >
                                        <div class="dropdown">
                                            <button class="dropbtn">${buttonText}</button>
                                            <div class="dropdown-content">
                                                <#assign countryCodes = downloadUtils.getDownloadServerCountryCodes() >
                                                <#list countryCodes as countryCode >
                                                    <#assign countryName = downloadUtils.getDownloadServerCountryName(countryCode) />
                                                    <a href="#" onclick="processClickEvent(
                                                            '${baseUrl}', '${countryCode}', '${fileName}', '${filePath}', '${articleId}', '${themeDisplay.getScopeGroupId()}', false)"
                                                       class="btn-lg btn-primary" role="button" aria-pressed="true">
                                                        from ${countryName}
                                                    </a>
                                                </#list>
                                            </div>
                                        </div>
									<#else>
                                        <a href="#" onclick="processClickEvent('${baseUrl}', '', '${fileName}', '${filePath}', '${articleId}', '${themeDisplay.getScopeGroupId()}', ${isShareLink?c})"
                                           class="btn-lg btn-primary" role="button" aria-pressed="true">
                                            ${buttonText}
                                        </a>
                                    </#if>
                                    </span>
                                <#else>
                                    <a href="#" data-article-id="${download.getArticleId()}"
                                       class="btn-lg btn-primary add-download-to-cart"
                                       role="button" aria-pressed="true" style="color:#fff" >
                                            ${languageUtil.get(locale, "shopping.cart.add")}
                                    </a>
                                </#if>

                            </span>
                        </#if>
                    </div>
                </div>
            </li>
        </#list>
    </ul>
</#if>

<script>

    function processClickEvent(baseUrl, countryCode, fileName, filePath, articleId, groupId, isShareLink){
        logStarted(fileName);
        if (isShareLink){
            downloadFile(filePath, fileName)
            registerClick(baseUrl, fileName, filePath, articleId, groupId);
        } else {
            createShareLink(baseUrl, countryCode, fileName, filePath, articleId, groupId);
        }
    }
    function createShareLink(baseUrl, countryCode, fileName, filePath, articleId, groupId){

        shareLinkUrl = baseUrl + "/createShareLink/"

        let pAuth = Liferay.authToken;
        $.ajax({
            type: "POST",
            url: shareLinkUrl + '?p_auth=' + pAuth,
            data: "{" +
                "\"fileName\": \"" + fileName + "\"," +
                "\"filePath\": \"" + filePath + "\"," +
                "\"downloadId\": \"" + articleId + "\"," +
                "\"groupId\": \"" + groupId + "\"," +
                "\"countryCode\": \"" + countryCode + "\"" +
                "}",
            contentType: "application/json",
            success : function(response, status, xhr) {
                downloadFile(response.url, fileName);
                logSuccess(fileName)
            },
            error : function(request, status, error) {
                let errorMessage = request.responseJSON.errorMessage;
                logError(fileName, errorMessage);
            }
        });

    }

    function registerClick(baseUrl, fileName, shareLink, articleId, groupId){
        let pAuth = Liferay.authToken;

        registerUrl = baseUrl + "/register/"
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
            success : function(response, status, xhr) {;
                logSuccess(fileName)
            },
            error : function(request, status, error) {
                logError(articleId, request.responseText)
            }
        });
    }

    function logStarted(fileName) {

        let alertEl = document.getElementById("download-alert");
        if (!alertEl) {
            alertEl = document.createElement("div");
            document.getElementById("messageBox").appendChild(alertEl);
            alertEl.innerHTML =
            '<div id="download-alert" class="alert alert-dismissible hidden" role="alert">' +
            '    <button aria-label="Close" class="close" data-dismiss="alert" type="button">' +
            '        <span id="download-alert-icon">' +
            '           <svg aria-hidden="true" class="lexicon-icon lexicon-icon-times" focusable="false" viewBox="0 0 512 512">' +
            '               <path class="lexicon-icon-outline" d="M301.1,256.1L502.3,54.9c30.1-30.1-16.8-73.6-45.2-45.2L255.9,210.8L54.6,9.7C24.6-20.4-19,26.5,9.4,54.9l201.2,201.2L9.3,457.3c-28.9,28.9,15.8,74.6,45.2,45.2l201.3-201.2l201.3,201.2c28.9,28.9,74.2-16.3,45.2-45.2L301.1,256.1z"></path>' +
            '           </svg>' +
            '        </span>' +
            '        <span class="sr-only">Close</span>' +
            '    </button>' +
            '    <div id="download-alert-message" ></div>' +
            '</div>';
        }
        alertEl.classList.replace("hidden", "alert-success");
        let messageEl = document.getElementById("download-alert-message");
        messageEl.innerHTML = "<strong class=\"lead\">Started download process for file:</strong><br/>" + fileName +
            "<br/><p>A new tab with a link to the download will open shortly.</p>"
    }

    function downloadFile(url, fileName){
        const aElement = document.createElement('a');
        aElement.href = url;
        aElement.style.display = 'none';
        aElement.download = fileName;
        aElement.target = '_blank';
        document.body.appendChild(aElement);
        aElement.click();
    }

    function logSuccess(fileName) {
        let alertEl = document.getElementById("download-alert");
        alertEl.classList.replace("hidden", "alert-success");
        let messageEl = document.getElementById("download-alert-message");
        messageEl.innerHTML = "<strong class=\"lead\">Finished download process for file:</strong><br/>" + fileName
    }

    function logError(fileName, error) {
        let alertEl = document.getElementById("download-alert");
        alertEl.classList.replace("hidden", "alert-warning");
        let messageEl = document.getElementById("download-alert-message");
        messageEl.innerHTML = "<strong class=\"lead\">Error downloading file:</strong><br/>" + fileName + "<br/>Error: " + error

    }

    if (!Liferay.ThemeDisplay.isSignedIn()){
        var homeUrl = Liferay.ThemeDisplay.getCDNBaseURL();
        var path = Liferay.ThemeDisplay.getLayoutRelativeURL();
        var langPath = path.substring(0, path.lastIndexOf('/'));
        document.getElementById('login_link').innerHTML =
            "<b>You must log in before you can download software.</b><a href=" + homeUrl + langPath + "/c/portal/login >Click here to log in</a>"
    }



</script>