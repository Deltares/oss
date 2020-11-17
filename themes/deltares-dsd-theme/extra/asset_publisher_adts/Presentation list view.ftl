<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />

<#if entries?has_content>
    <#list entries as entry>
        <#assign imagesUrl = themeDisplay.getPathThemeImages() />
        <#assign assetRenderer = entry.getAssetRenderer()/>
        <#assign journalArticle = assetRenderer.getArticle()/>
        <#assign presentation = dsdParserUtils.toDsdArticle(journalArticle) />
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
        <div class="presentation">
            <#if presentation.isVideoLink() >
                <a href="${viewURL}">
                    <i class="icon-film"></i>
                    <strong>${presentation.getTitle()}</strong>&nbsp;&gt;&nbsp;
                </a>
            <#elseif presentation.isDownloadLink() >
                <a href="${presentation.getPresentationLink()}" class="">
                    <i class="icon-download-alt"></i>
                    <strong>${presentation.getTitle()}</strong>&nbsp;&gt;&nbsp;
                </a>
            </#if>
            <span>${presentation.getPresenter()}</span>
            <span>(${presentation.getOrganization()})</span>
        </div>

    </#list>
    </div>
</#if>