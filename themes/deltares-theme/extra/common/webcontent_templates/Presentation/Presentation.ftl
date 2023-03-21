<#if themeDisplay.isSignedIn() >


    <#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
    <#assign dsdJournalArticleUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdJournalArticleUtils") />
    <#assign articleId = .vars['reserved-article-id'].getData() />
    <#assign article = dsdJournalArticleUtils.getJournalArticle(groupId,articleId) />
    <#assign presentation = dsdParserUtils.toDsdArticle(article) />

    <div class="presentation">

        <#if presentation.isDownloadLink() >
            <a href="${presentation.getPresentationLink()}" class="">
                <div class="presentation_title">
                    <strong>${presentation.getTitle()}</strong>
                </div>
            </a>
        <#else >
            <iframe src="${presentation.getPresentationLink()}" allow="autoplay; fullscreen" width="100%" height="500px">

            </iframe>
            <div class="presentation_title">
                <strong>${presentation.getTitle()}</strong>
            </div>
        </#if>
        <#if presentation.getPresenter() != "" >
            <div>
                &nbsp;&gt;&nbsp;
                <span>${presentation.getPresenter()}</span>
                <span>(${presentation.getOrganization()})</span>
            </div>
        </#if>
    </div>
<#else>
    <strong>Om de presentaties te bekijken, gaarne inloggen.</strong>
</#if>