<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />

<#if entries?has_content>
    <div class="c-experts__page">
        <#list entries as curentry>
            <#assign entry = curentry />
            <#assign assetRenderer = entry.getAssetRenderer()/>
            <#assign journalArticle = assetRenderer.getArticle()/>
            <#assign expert = dsdParserUtils.getExpert(journalArticle) />
            <#assign cur_expertName = expert.getName()/>
            <#assign cur_expertImage = expert.getSmallImageURL(themeDisplay)/>
            <#assign cur_expertJobTitle><#if expert.getJobTitle()?? >${expert.getJobTitle()}<#else>""</#if></#assign>
            <#assign cur_expertCompany><#if expert.getCompany()?? >${expert.getCompany()}<#else>""</#if></#assign>
            <#assign cur_expertEmail = expert.getEmail() />
                    
            <div class="expert-data">
                <#if cur_expertImage != "">
                    <div class="expert-data__image"  style="background-image:url(${cur_expertImage})">
                        <img alt="${cur_expertName}" src="${cur_expertImage}" />
                    </div>
                <#else>
                    <div class="expert-data__image">
                        ${stringUtil.shorten(cur_expertName, 1)}
                    </div>
                </#if>
                <div class="expert-data__content">
                    <h4 class="h1 clear-margin">${cur_expertName}</h4>
                    <#if cur_expertJobTitle != "">
                        <p><strong>${cur_expertJobTitle}</strong></p>
                    </#if>
                    <#if cur_expertCompany != "">
                        <p>${cur_expertCompany}</p>
                    </#if>
                    <#if cur_expertEmail != "">
                        <p><a href="mailto:${cur_expertEmail}" >${cur_expertEmail}</a></p>
                    </#if>
                </div>
            </div>
        </#list>
    </div>
</#if>