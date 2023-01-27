<!--container-->
<#assign parserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#if entries?has_content>

    <div class="slide-container">

            <#list entries as entry>
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign downloadGroup = parserUtils.toDsdArticle(journalArticle, locale) />
                <#assign imageUrl = downloadGroup.getSmallImageURL(themeDisplay) />
                <#assign groupPageUrl = downloadGroup.getGroupPage(themeDisplay) />

                <!--repeatable element-->
                <a href="${groupPageUrl}" target="_blank" class="slide-holder">
                    <div class="slide-content">

                        <img alt="${downloadGroup.getTitle()}" src="${imageUrl}" width="91"/>

                        <ul>
                            <li><h2>${downloadGroup.getName()}</h2></li>
                        </ul>
                        <div class="description"><p>${downloadGroup.getDescription()}</p></div>

                    </div>
                </a>

            </#list>
    </div>
</#if>
