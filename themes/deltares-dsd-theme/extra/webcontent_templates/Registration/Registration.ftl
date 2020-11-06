<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign title=.vars['reserved-article-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
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
            <#assign count = displayContext.getPresenterCount()/>
            <#if count gt 0>
                <#list 0..(count-1) as i >
                    <#assign imageUrl = displayContext.getPresenterSmallImageURL(i) />
                    <#if imageUrl?has_content >
                        <img width="32" class="expert-thumbnail" src="${imageUrl}"/>
                    </#if>
                    <#assign name = displayContext.getPresenterName(i) />
                    <#if name?has_content>
                        <span class="expert-name px-2">${name}</span> |
                    </#if>
                </#list>
            </#if>

            <span class="event-time pl-2">${displayContext.getStartTime()} - ${displayContext.getEndTime()}</span> |
            <#if displayContext.getPrice() gt 0 >
                ${displayContext.getCurrency()} ${displayContext.getPrice()}
            <#else>
                ${languageUtil.get(locale, "dsd.theme.session.free")}
            </#if>
        </div>

        <p class="search-document-content text-default">
            ${displayContext.getSummary()}
        </p>
    </div>
</div>