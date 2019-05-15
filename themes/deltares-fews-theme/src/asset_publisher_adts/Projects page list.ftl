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
        
        <div class="left-column">
            <#if ProjectImage?? && ProjectImage!= "">
                <div class="project-page__item__meta-data__image square-form" style="background-image:url(${ProjectImage})">
                <img src="${ProjectImage}" />
                </div>
            <#else>
                <div class="project-page__item__meta-data__image-backup"></div>
            </#if>
        </div>
        <div class="right-column small-form">
            <!--Project title-->
            <div class="project-page__item__meta-data__title h1">${entry.getTitle(locale)}</div>
            
            <!-- <div class="project-page__item__meta-data__expert">
            <#assign projectExpertData = getArticleProperty(journalArticle,"ProjectExpert")/>
            <#if projectExpertData?has_content >
                <#assign cur_webContent_map = projectExpertData?eval>
                <#assign cur_webContent_classPK = cur_webContent_map.classPK>
                <#assign expertArticle = journalArticleLocalService.getLatestArticle(cur_webContent_classPK?number)>

                ${journalContentUtil.getContent(groupId, expertArticle.getArticleId(), viewMode, locale.getLanguage())} -->
            <#else>
                <div class="expert-data">
                    <div class="expert-data__image">D</div>
                    <div class="expert-data__content">
                        <p class="bold">Deltares</p>
                        <p>Water resources and environmental modelling</p>
                    </div>
                </div>
            </#if>
            </div>-->
            <!--Date-->
            <!--<p class="project-page__item__meta-data__date"><span>|</span>${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>-->
            
            <#assign projectSectionContent = ""/>
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