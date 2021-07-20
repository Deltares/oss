<#if themeDisplay.isSignedIn() >
    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign article = dsdJournalArticleUtils.getJournalArticle(groupId,articleId) />
    <#assign presentation = dsdParserUtils.toDsdArticle(article) />
    <#if presentation.getThumbnailLink()?? >
        <#assign thumbnail = presentation.getThumbnailLink() />
    </#if>

    <div class="presentation">

        <#if presentation.isVideoLink() >
        <iframe src="${presentation.getPresentationLink()}" frameborder="0" allow="autoplay; fullscreen" width="100%" height="500px"></iframe>

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
    </div>
<#else>
    <strong>To watch the videos, please sign in.</strong>
</#if>