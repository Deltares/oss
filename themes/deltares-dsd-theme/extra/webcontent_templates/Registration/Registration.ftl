<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign registration = dsdParserUtils.getRegistration(groupId,articleId) />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />

<div class="row no-gutters">
    <div class="col-2">
        <img class="img-fluid" src="${displayContext.getSmallImageURL()}"/>
    </div>
    <div class="col-10 px-3">
        <h4>
            <a href="#">
                <strong>${title}</strong>
            </a>
        </h4>

        <div>
            <#if displayContext.getPresenterSmallImageURL()?? >
                <img width="32" class="expert-thumbnail" src="${displayContext.getPresenterSmallImageURL()}"/>
            </#if>
            <#if displayContext.getPresenterName()?has_content>
                <span class="expert-name px-2">${displayContext.getPresenterName()}</span> |
            </#if>
            <span class="event-time pl-2">${displayContext.getStartTime()} - ${displayContext.getEndTime()}</span>
        </div>

        <p class="search-document-content text-default">
            ${displayContext.getSummary()}
        </p>
    </div>
</div>