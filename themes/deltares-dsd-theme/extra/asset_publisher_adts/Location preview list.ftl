<#assign dsdUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdRegistrationUtils") />
<#if entries?has_content>
    <div class="blog-page">
        <#list entries as curentry>
            <div class="blog-page__item clearfix">
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdUtils.getLocation(journalArticle) />
                <#assign website = location.getWebsite() />
                <div class="left-column">
                    <img src="${location.getSmallImageURL(themeDisplay)}" />
                </div>
                <div class="right-column">
                    <div class="expert-data__content">
                        <h4 class="h1 clear-margin">${journalArticle.getTitle()}</h4>
                        <p><strong>${location.getAddress()}, ${location.getCity()}</strong></p>
                        <#if website?? >
                            <p><a href="${website}" >${website}</a></p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#if>