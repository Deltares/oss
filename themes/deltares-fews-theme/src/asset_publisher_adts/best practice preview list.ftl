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
      <div class="bestpractice-page__item clearfix">
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign rootElement = document.getRootElement() />
        
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
          <!--Best practice title-->
          <div class="bestpractice-page__item__meta-data__title h1">${entry.getTitle(locale)}</div>
          <!--TODO First 3 lines of html content: need to be limited to 3 lines (200 chars? PLEASE CHECK)-->
          <#assign bestpracticeSectionContent = ""/>
          <div class="bestpractice-page__item__meta-data__content">
            <#assign bestpracticeSectionContent = "" />
            <#list rootElement.elements() as dynamicElement>
              <#if "BestPracticeSectionHeader"==dynamicElement.attributeValue("name") && !bestpracticeSectionContent?has_content>
                <#assign bestpracticeSectionContent = dynamicElement.element("dynamic-content").getData() />
                <#if bestpracticeSectionContent?has_content >
                  <#assign bestpracticeSectionContent = bestpracticeSectionContent?string + ": "/>
                </#if>
                <#assign bestpracticeSectionContent = bestpracticeSectionContent + dynamicElement.element("dynamic-element").element("dynamic-content").getData()?string/>
              </#if>
            </#list>
            ${stringUtil.shorten(bestpracticeSectionContent, 286)} <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
          </div>
      </div>
    </#list>
  </#if>
</div>
