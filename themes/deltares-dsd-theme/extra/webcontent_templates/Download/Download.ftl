<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign download = parserUtils.toDsdArticle(groupId, articleId) />
<#assign showButtons = themeDisplay.isSignedIn() />

<div class="cta_box">
    <strong>${download.getFileName()}</strong>
    &nbsp;
    ${download.getFileTopicName()} | ${download.getFileTypeName()} | ${download.getFileSize()}
    <#if showButtons >
        <span class="d-block" style="float:right">
            <a href="#" data-article-id="${download.getArticleId()}"
               class="btn-lg btn-primary add-download-to-cart"  role="button"
               aria-pressed="true" style="color:#fff">
                    ${languageUtil.get(locale, "shopping.cart.add")}
            </a>
        </span>
    </#if>
    <hr>
</div>
<#if download.getDescriptionArticleId()?? >
    ${journalContent.getContent(groupId, download.getDescriptionArticleId(), viewMode, locale.getLanguage())}
</#if>