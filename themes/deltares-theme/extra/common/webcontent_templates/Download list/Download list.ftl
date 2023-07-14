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
                    <a href="#" data-article-id="${download.getArticleId()}"
                       class="btn-lg btn-primary disabled"
                       role="button" aria-pressed="true" style="color:#fff">
                        ${languageUtil.get(locale, "shopping.cart.sanctioned")}
                    </a>
                <#else >
                    <a href="#" data-article-id="${download.getArticleId()}"
                       class="btn-lg btn-primary add-download-to-cart"
                       role="button" aria-pressed="true" style="color:#fff">
                        ${languageUtil.get(locale, "shopping.cart.add")}
                    </a>
                </#if>
                </span>
            </#if>
        </div>

    </div>
</div>