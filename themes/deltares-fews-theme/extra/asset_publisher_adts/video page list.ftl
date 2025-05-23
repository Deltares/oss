<#function getArticleProperty journalArticle propertyName>
    <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
    <#assign rootElement = document.getRootElement() />
    <#assign defaultLanguageId = journalArticle.defaultLanguageId/>

    <#assign introDefaultPropertySelector = saxReaderUtil.createXPath("dynamic-element[@name='${propertyName}']/dynamic-content[@language-id='${defaultLanguageId}']") />
    <#assign introLocalisedPropertySelector = saxReaderUtil.createXPath("dynamic-element[@name='${propertyName}']/dynamic-content[@language-id='${locale}']") />

    <#assign propertyValue = "" />
    <#assign propertyValueDefaultNode = introDefaultPropertySelector.selectSingleNode(rootElement)! />
    <#assign propertyValueLocalised = introLocalisedPropertySelector.selectSingleNode(rootElement)! />
    <#if propertyValueDefaultNode?has_content>
        <#assign propertyValue = propertyValueDefaultNode.getStringValue() />
    </#if>
    <#if propertyValueLocalised?has_content>
        <#assign propertyValue = propertyValueLocalised.getStringValue() />
    </#if>
    <#return propertyValue />
</#function>

<div class="project-page">
    <#if entries?has_content>
        <#list entries as entry>
            <div class="project-page__item clearfix">
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
                <#assign rootElement = document.getRootElement() />
                <#if rootElement?has_content>
                    <#list rootElement.elements() as dynamicElement>
                        <#if "ThumbnailLink"==dynamicElement.attributeValue("name")>
                            <#assign thumbnailLink = dynamicElement.element("dynamic-content").getData()/>
                        <#elseif "VideoTitle"==dynamicElement.attributeValue("name")>
                            <#assign entryTitle = dynamicElement.element("dynamic-content").getData()/>
                        </#if>
                    </#list>
                </#if>
                <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

                <div class="left-column small-form">
                    <#if thumbnailLink?? && thumbnailLink!= "">
                        <a class="project-page__item__meta-data__image square-form display-block"
                           style="max-height:250px;max-width:250px;background-image:url(${thumbnailLink});background-size: inherit inherit"
                           href="${viewURL}"
                           title="read more about ${entryTitle}">

                        </a>
                    <#else>
                        <a class="project-page__item__meta-data__image-backup display-block"
                           href="${viewURL}"
                           title="read more about ${entryTitle}"></a>
                    </#if>
                </div>
                <div class="right-column small-form">
                    <!--Project title-->
                    <h4 class="project-page__item__meta-data__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                </div>
            </div>
        </#list>
    </#if>
</div>