<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign downloadUtils = serviceLocator.findService("nl.deltares.portal.utils.DownloadUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = dsdParserUtils.toDsdArticle(themeDisplay.getScopeGroupId(), articleId) />
<#assign showButtons = themeDisplay.isSignedIn() />

<div class="row no-gutters">

    <div class="col-12 px-3">
        <h4>
            <a href="${download.getGroupPage(themeDisplay)}" target="_blank">
                <strong>${download.getFileName()}</strong>

            </a>
        </h4>
        <div>
            ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()}
            <#if showButtons>
                <span class="d-block" style="float:right">
                <#if is_sanctioned?? && is_sanctioned >
                    <#assign buttonText = languageUtil.get(locale, "shopping.cart.sanctioned")/>
                    <#assign buttonDisable = true />
                <#else >
                    <#assign downloadStatus = downloadUtils.getDownloadStatus(download, themeDisplay.getUser()) />
                    <#switch downloadStatus >

                        <#case "payment_pending" >
                            <#assign buttonText = languageUtil.get(locale, "shopping.cart.paymentPending")/>
                            <#assign buttonDisable = true/>
                            <#break >
                        <#case "available">
                            <#assign buttonText = languageUtil.get(locale, "shopping.cart.available")/>
                            <#assign buttonDisable = true/>
                            <#break >
                        <#case "processing">
                            <#assign buttonText = languageUtil.get(locale, "shopping.cart.processing")/>
                            <#assign buttonDisable = true/>
                            <#break >
                        <#default >
                            <#assign buttonText = languageUtil.get(locale, "shopping.cart.add")/>
                            <#assign buttonDisable = false />
                    </#switch>
                </#if>

                    <#if buttonDisable >
                        <a href="#" data-article-id="${download.getArticleId()}"
                           class="btn-lg btn-primary disabled"
                           role="button" aria-pressed="true" style="color:#fff">
                        ${buttonText}
                    </a>
                <#else >
                        <a href="#" data-article-id="${download.getArticleId()}"
                           class="btn-lg btn-primary add-download-to-cart"
                           role="button" aria-pressed="true" style="color:#fff">
                        ${buttonText}
                    </a>
                    </#if>
            </span>
            </#if>
        </div>

    </div>
</div>