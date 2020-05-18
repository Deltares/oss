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


<#if entries?has_content>
    <div class="blog-page">
        <#list entries as entry>
            <div class="blog-page__item clearfix">
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
                <#assign rootElement = document.getRootElement() />
                <#assign cur_content = ""/>
                <#assign cur_image = ""/>
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
                                    <#if "BlogContent"==child.attributeValue("name")>
                                        <#assign cur_content = child.element("dynamic-content").getData()/>
                                    </#if>
                                    <#if "BlogImage"==child.attributeValue("name")>
                                        <#assign overviewPhotoSelector = saxReaderUtil.createXPath("dynamic-element[@name='BlogImage']") />
                                        <#assign cur_image = ddlUtils.getFileEntryImage(overviewPhotoSelector.selectSingleNode(dynamicElement).getStringValue())  />
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
                <div class="left-column">
                    <!--Blog image-->
                    <a 
                        class="blog-page__item__meta-data__image display-block"  
                        style="background-image:url(${cur_image})"
                        href="${viewURL}" 
                        title="read more about ${entryTitle}">
                        <img src="${cur_image}" />
                    </a>
                </div>
                <div class="right-column">
                <!--Blog title-->
                <h4 class="blog-page__item__meta-data__title h1" ><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                <!--Blog Expert-->
                <div class="blog-page__item__meta-data__expert">
                        
                    <#assign expertExists = false />
                    <#if cur_selectedExpert?has_content >
                        <#assign cur_webContent_map = cur_selectedExpert?eval />
                        <#assign cur_webContent_classPK = cur_webContent_map.classPK />
                        <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                        <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                            <#assign expertExists = true />
                            ${journalContentUtil.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                        </#if>
                    </#if>
                    <#if !expertExists >
                        <div class="expert-data">
                            <#if cur_expertPhoto != "">
                                <div class="expert-data__image" style="background-image:url(${cur_expertPhoto})">
                                    <img alt="${cur_expertName}" src="${cur_expertPhoto}" />
                                </div>
                            <#else>
                                <div class="expert-data__image">
                                    ${stringUtil.shorten(cur_expertName, 1)}
                                </div>
                            </#if>
                            <div class="expert-data__content">
                                <p class="bold">${cur_expertName}</p>
                            </div>
                        </div>
                    </#if>
                </div>
                <!--Date-->
                <p class="blog-page__item__meta-data__date"><span>|</span>${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
                
                <div class="blog-page__item__meta-data__content">
                    ${stringUtil.shorten(cur_content, 200)}
                </div>
                <!--Read more-->
                <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
                </div>
            </div>
        </#list> 
    </div>
</#if>