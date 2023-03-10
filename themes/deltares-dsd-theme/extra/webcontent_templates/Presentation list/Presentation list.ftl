<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign presentations = displayContext.getPresentations() />
<#assign prefix = languageUtil.get(locale, "dsd.theme.session.presentations", "Presentaties van programma:")/>
<#assign siteUrl=themeDisplay.getSiteGroup().getDisplayURL(themeDisplay) />
<#assign urltitle=siteUrl + "/-/" + registration.getJournalArticle().getUrlTitle() />

<div class="row no-gutters">

    <div class="col-12 px-3">
        <p>
        <h4>
            <a href="${urltitle}" target="_self">
                ${prefix}<strong>${title}</strong>
            </a>
        </h4>
        </p>
        <table >
            <#list presentations as presentation>

                <#if presentation.isDownloadLink() >
                    <#assign iconClass = "icon-download-alt" />
                <#else >
                    <#assign iconClass = "icon-film" />
                </#if>

                <#if presentation.getThumbnailLink()?? >
                    <#assign thumbnail = presentation.getThumbnailLink() />
                <#else>
                    <#assign thumbnail = "" />
                </#if>

                <#assign viewURL = siteUrl + "/-/" + presentation.getJournalArticle().getUrlTitle() />
                <tr><td>
                        <div class="row no-gutters presentation">

                            <div class="col-4">
                                <#if thumbnail?? && thumbnail != "">
                                    <img class="videoThumbnail" src="${thumbnail}"  alt="${presentation.getTitle()}"/>
                                <#else>
                                    <i class=${iconClass}></i>
                                </#if>
                            </div>
                            <div class="col-8 px-3">
                                <h4>
                                    <a href="${viewURL}" target="_self">
                                        <strong>${presentation.getTitle()}</strong>
                                    </a>
                                </h4>
                                <#if presentation.getPresenter() != "" >
                                    <div>
                                        &nbsp;&gt;&nbsp;
                                        <span>${presentation.getPresenter()}</span>
                                        <span>(${presentation.getOrganization()})</span>
                                    </div>
                                </#if>
                            </div>

                        </div>
                    </td></tr>
            </#list>
        </table>
    </div>
</div>