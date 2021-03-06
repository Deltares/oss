<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign presentations = displayContext.getPresentations() />

<#list presentations as presentation>

     <#if presentation.isVideoLink() || presentations.isSlideLink()>
         <#assign iconClass = "icon-film" />
     <#elseif presentation.isDownloadLink() >
         <#assign iconClass = "icon-download-alt" />
     </#if>

    <#if presentation.getThumbnailLink()?? >
        <#assign thumbnail = presentation.getThumbnailLink() />
    <#else>
        <#assign thumbnail = "" />
    </#if>

    <#assign viewURL = displayContext.getViewURL(presentation) />
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
                <a href="${viewURL}">
                    <strong>${title}</strong>
                </a>
            </h4>
            <div>
                &nbsp;&gt;&nbsp;
                <span>${presentation.getPresenter()}</span>
                <span>(${presentation.getOrganization()})</span>
            </div>
        </div>

    </div>

</#list>