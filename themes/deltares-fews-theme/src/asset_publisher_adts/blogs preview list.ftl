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


<div class="blog-page">
  <#if entries?has_content>
    <#list entries as entry>
      <div class="blog-page__item clearfix">
        <#assign assetRenderer = entry.getAssetRenderer() />
        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
        <#assign journalArticle = assetRenderer.getArticle() />
        <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
        <#assign rootElement = document.getRootElement() />
        <#if rootElement?has_content>
          <#list rootElement.elements() as dynamicElement>
            <#if "BlogImage"==dynamicElement.attributeValue("name")>
              <#assign overviewPhotoData = dynamicElement.element("dynamic-content").getData()/>
              <#assign BlogImage = htmlUtil.escapeHREF(overviewPhotoData) />
            </#if>
          </#list>
        </#if>
        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
        
        <!--Blog image: needs to be cropped-->
        <div class="left-column">
          <#if BlogImage?? && BlogImage!= "">
              <div class="blog-page__item__meta-data__image"  style="background-image:url(${BlogImage})">
                <img src="${BlogImage}" />
              </div>
          <#else>
              <div class="blog-page__item__meta-data__image-backup"></div>
          </#if>
        </div>
        <div class="right-column">
          <!--Blog title-->
          <div class="blog-page__item__meta-data__title h1">${entry.getTitle(locale)}</div>
          <!--TODO Expert data (taken stuff from template): only image and name need to be visible-->
          <div class="blog-page__item__meta-data__expert">
            <#assign blogExpertData = getArticleProperty(journalArticle,"BlogExpert")/>
            <#if blogExpertData?has_content >
                <#assign cur_webContent_map = blogExpertData?eval>
                <#assign cur_webContent_classPK = cur_webContent_map.classPK>
                <#assign expertArticle = journalArticleLocalService.getLatestArticle(cur_webContent_classPK?number)>

                ${journalContentUtil.getContent(groupId, expertArticle.getArticleId(), viewMode, locale.getLanguage())}
            <#else>
                <div class="expert-data">
                    <div class="expert-data__image">D</div>
                    <div class="expert-data__content">
                        <p class="bold">Deltares</p>
                        <p>Water resources and environmental modelling</p>
                    </div>
                </div>
            </#if>
          </div>
          <!--Date-->
          <p class="blog-page__item__meta-data__date"><span>|</span>${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
          <!--TODO First 3 lines of html content: need to be limited to 3 lines (200 chars? PLEASE CHECK)-->
          <#assign blogSectionContent = ""/>
          <div class="blog-page__item__meta-data__content">
            <#assign blogSectionContent = "" />
            <#list rootElement.elements() as dynamicElement>
              <#if "BlogSectionHeader"==dynamicElement.attributeValue("name") && !blogSectionContent?has_content>
                <#assign blogSectionContent = dynamicElement.element("dynamic-content").getData() />
                <#if blogSectionContent?has_content >
                  <#assign blogSectionContent = blogSectionContent?string + ": "/>
                </#if>
                <#assign blogSectionContent = blogSectionContent + dynamicElement.element("dynamic-element").element("dynamic-content").getData()?string/>
              </#if>
            </#list>

            ${stringUtil.shorten(blogSectionContent, 200)}
          </div>
          <!--Read more-->
          <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
        </div>
      </div>
    </#list>
  </#if>
</div>