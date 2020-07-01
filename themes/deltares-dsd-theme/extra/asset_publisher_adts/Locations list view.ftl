<#assign dsdUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdRegistrationUtils") />

<#if entries?has_content>


    <div class="blog-page">
        <#list entries as curentry>
            <div class="blog-page__item clearfix">
                <#assign entry = curentry />
                <#assign assetRenderer = entry.getAssetRenderer()/>
                <#assign journalArticle = assetRenderer.getArticle()/>
                <#assign location = dsdUtils.getLocation(journalArticle) />
                <#if location.getLocationType() == "event" >
                    <#assign buildings = location.getBuildings() />
                </#if>
                <#assign website = location.getWebsite() />
                <div class="left-column">
                    <img src="${location.getSmallImageURL(themeDisplay)}" />
                </div>
                <div class="right-column">
                    <div class="expert-data__content">
                        <#if buildings?? >
                            <div style="width: 60%; float:right">
                                <strong>${languageUtil.get(locale, "dsd.theme.buildings")}</strong>
                                <table>
                                <#list buildings as building>
                                    <#assign building_img = building.getSmallImageURL(themeDisplay) />
                                    <#if building_img == "" >
                                        <#assign building_img = themeDisplay.getPathThemeImages() + "dsd/building.png" />
                                    </#if>
                                    <tr>
                                        <td>${building.getTitle()}</td>
                                        <td><img style="max-height: 150px; max-width: 150px" src="${building_img}" /></td>
                                    </tr>
                                </#list>
                                </table>
                            </div>
                        </#if>
                        <h4 class="h1 clear-margin">${journalArticle.getTitle()}</h4>
                        <p><strong>${location.getAddress()}, ${location.getCity()}</strong></p>
                        <#if website?? >
                            <p><a href="https://${website}" >${website}</a></p>
                        </#if>

                    </div>
                </div>

            </div>
        </#list>

    </div>

</#if>
