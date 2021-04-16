<#if themeDisplay.isSignedIn() >

    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign article = dsdJournalArticleUtils.getJournalArticle(groupId,articleId) />
    <#assign presentation = dsdParserUtils.toDsdArticle(article) />
    <#if presentation.getThumbnailLink()?? >
        <#assign thumbnail = presentation.getThumbnailLink() />
    </#if>

    <p class="presentation">

        <#if presentation.isVideoLink() >
            <iframe src="${presentation.getPresentationLink()}" frameborder="0" allow="autoplay; fullscreen" width="100%" height="500px"></iframe>

            <#if thumbnail?? && thumbnail != "">
                <img class="videoThumbnail" src="${thumbnail}" />
            <#else>
                <i class="icon-film"></i>
            </#if>

            <span class="underline">${presentation.getTitle()} </span>&nbsp;&gt;&nbsp;
        <#elseif presentation.isDownloadLink() >
            <a href="${presentation.getPresentationLink()}" target="_blank" class="link ">

                <#if thumbnail?? && thumbnail != "">
                    <img class="videoThumbnail" src="${thumbnail}" />
                <#else>
                    <i class="icon-download-alt"></i>
                </#if>

                <span class="underline">${presentation.getTitle()} </span>&nbsp;&gt;&nbsp;
            </a>
        </#if>
        <span>${presentation.getPresenter()}</span>
        <span>(${presentation.getOrganization()})</span>
    </p>
<#else>
    <strong>To watch the videos, please sign in.</strong>
</#if>