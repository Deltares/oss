<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />

<#if entries?has_content>
    <#list entries as entry>
        <#assign imagesUrl = themeDisplay.getPathThemeImages() />
        <#assign assetRenderer = entry.getAssetRenderer()/>
        <#assign journalArticle = assetRenderer.getArticle()/>
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign presentation = dsdParserUtils.toDsdArticle(journalArticle) />
        <#if presentation.getThumbnailLink()?? >
            <#assign thumbnail = presentation.getThumbnailLink() />
        <#else>
            <#assign thumbnail = "" />
        </#if>
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />


        <div class="presentation">
            <#if presentation.isVideoLink() || presentation.isSlideLink()>
                <a href="${viewURL}">

                    <#if thumbnail?? && thumbnail != "">
                        <img class="videoThumbnail" src="${thumbnail}" />
                    <#else>
                        <i class="icon-film"></i>
                    </#if>
                    <div class="presentation_title">
                        <strong>${presentation.getTitle()}</strong>
                </a>
            <#elseif presentation.isDownloadLink() >
                <a href="${presentation.getPresentationLink()}" class="">
                    <#if thumbnail?? && thumbnail != "">
                        <img class="videoThumbnail" src="${thumbnail}" />
                    <#else>
                        <i class="icon-download-alt"></i>
                    </#if>
                    <div class="presentation_title">
                        <strong>${presentation.getTitle()}</strong>
                </a>
            </#if>
            <p>
                &nbsp;&gt;&nbsp;
                <span>${presentation.getPresenter()}</span>
                <span>(${presentation.getOrganization()})</span>
            </p>

        </div>

    </#list>
</#if>