<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />

<#if entries?has_content>


    <div class="blog-page">
        <#list entries as curentry>
            <div class="blog-page__item clearfix">
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdParserUtils.getLocation(journalArticle) />
                <#if location.getLocationType() == "event" >
                    <#assign buildings = location.getBuildings() />
                </#if>
                <#assign location_img = location.getSmallImageURL(themeDisplay) />
                <#if location_img == "" >
                    <#assign location_img = themeDisplay.getPathThemeImages() + "/dsd/building.png" />
                </#if>
                <#assign website = location.getWebsite() />
                <div class="left-column">
                    <img style="max-height: 200px; max-width: 200px" src="${location_img}" />
                </div>
                <div class="right-column">
                    <div class="expert-data__content">
                        <h4 class="h1 clear-margin">${journalArticle.getTitle()}</h4>
                        <p>${location.getAddress()}, ${location.getCity()}<br>
                            <#if website?? >
                                <a target="_blank" href="${website}" >${website}</a><br>
                            </#if>
                            <a target="_blank" href="https://www.google.com/maps/search/?api=1&query=${location.getLatitude()},${location.getLongitude()}">
                                ${languageUtil.get(locale, "dsd.theme.locations.direction")}</a>
                        </p>
                    </div>
                </div>
            </div>
        </#list>
    </div>
    <#if buildings?? >
        <strong>${languageUtil.get(locale, "dsd.theme.locations.buildingof")}: ${location.getTitle()}</strong>
        <div class="c-grid news-row">
            <#list buildings as building>
                <div class="c-card news-item">
                    <#assign building_img = building.getSmallImageURL(themeDisplay) />
                    <#if building_img == "" >
                        <#assign building_img = themeDisplay.getPathThemeImages() + "/dsd/building.png" />
                    </#if>
                    <img style="max-height: 100px; max-width: 100px" src="${building_img}" />
                    <h4 class="c-card__title h1">${building.getTitle()}</h4>
                </div>
            </#list>
        </div>
    </#if>
</#if>