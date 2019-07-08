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

<div class="bestpractice-page">
  <#if entries?has_content>
    <#list entries as entry>
      <#assign assetRenderer = entry.getAssetRenderer() />
      <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
      <#assign journalArticle = assetRenderer.getArticle() />
      <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
      <#assign rootElement = document.getRootElement() />
      <#assign cur_content = ""/>
      <#assign cur_expertName = ""/>
      <#assign cur_expertEmailAddress = ""/>
      <#assign cur_expertCompany = ""/>
      <#assign cur_expertJobTitle = ""/>
      <#assign cur_expertPhoto = ""/>
      <#assign cur_selectedExpert = ""/>
      <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
      
      <#list rootElement.elements()>
          <#items as dynamicElement>
              <#if "MandatoryFields"==dynamicElement.attributeValue("name")>
                  <#list dynamicElement.elements() as child>
                      <#if child.getName()=="dynamic-element">
                          <#if "BestPracticeContent"==child.attributeValue("name")>
                              <#assign cur_content = child.element("dynamic-content").getData()/>
                          </#if>
                          <#if "ExpertName"==child.attributeValue("name")>
                              <#assign cur_expertName = child.element("dynamic-content").getData()/>
                          </#if>
                          <#if "ExpertEmailAddress"==child.attributeValue("name")>
                              <#assign cur_expertEmailAddress = child.element("dynamic-content").getData()/>
                          </#if>
                      </#if>
                  </#list>    
              </#if>
              <#if "OptionalFields"==dynamicElement.attributeValue("name")>
                  <#list dynamicElement.elements() as child>
                      <#if child.getName()=="dynamic-element">
                          <#if "ExpertCompany"==child.attributeValue("name")>
                              <#assign cur_expertCompany = child.element("dynamic-content").getData()/>
                          </#if>
                          <#if "ExpertJobTitle"==child.attributeValue("name")>
                              <#assign cur_expertJobTitle = child.element("dynamic-content").getData()/>
                          </#if>
                          <#if "ExpertPhoto"==child.attributeValue("name")>
                              <#assign cur_expertPhoto = child.element("dynamic-content").getData()/>
                          </#if>
                          <#if "SelectExpert_hide"==child.attributeValue("name")>
                              <#assign cur_selectedExpert = child.element("dynamic-content").getData()/>
                          </#if>
                      </#if>
                  </#list>
              </#if>
          </#items>
      </#list>


      <div class="bestpractice-page__item clearfix">
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign rootElement = document.getRootElement() />
        
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
        <!--Best practice title-->
        <h4 class="bestpractice-page__item__meta-data__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
        <!--Best practice content-->
        <div class="bestpractice-page__item__meta-data__content">
          ${stringUtil.shorten(cur_content, 286)} <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
        </div>
      </div>
    </#list>
  </#if>
</div>