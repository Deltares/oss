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
                    <#if "ProjectImage"==dynamicElement.attributeValue("name")>
                        <#assign overviewPhotoData = dynamicElement.element("dynamic-content").getData()/>
                        <#assign ProjectImage = htmlUtil.escapeHREF(overviewPhotoData) />
                    </#if>
                    </#list>
                </#if>
                <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
                
                <div class="left-column small-form">
                    <#if ProjectImage?? && ProjectImage!= "">
                        <a class="project-page__item__meta-data__image small-form display-block" 
                            style="background-image:url(${ProjectImage})"
                            href="${viewURL}" 
                            title="read more about ${entryTitle}">
                            <img src="${ProjectImage}" />
                        </a>
                    <#else>
                        <a class="project-page__item__meta-data__image-backup display-block"
                            href="${viewURL}" 
                            title="read more about ${entryTitle}"><a>
                    </#if>
                </div>
                <div class="right-column small-form">
                    <!--Project title-->
                    <h4 class="project-page__item__meta-data__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                    
                    
                    <div class="project-page__item__meta-data__content">
                        <#assign projectSectionContent = "" />
                        <#list rootElement.elements() as dynamicElement>
                            <#if "ProjectSectionHeader"==dynamicElement.attributeValue("name") && !projectSectionContent?has_content>
                            <#assign projectSectionContent = dynamicElement.element("dynamic-content").getData() />
                            <#if projectSectionContent?has_content >
                                <#assign projectSectionContent = projectSectionContent?string + ": "/>
                            </#if>
                            <#assign projectSectionContent = projectSectionContent + dynamicElement.element("dynamic-element").element("dynamic-content").getData()?string/>
                            </#if>
                        </#list>

                        ${stringUtil.shorten(projectSectionContent, 200)}
                    </div>
                    <!--Read more-->
                    <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
                </div>
            </div>
        </#list>
    </#if>
</div>