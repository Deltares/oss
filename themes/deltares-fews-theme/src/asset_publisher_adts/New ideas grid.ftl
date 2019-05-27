<#if entries?has_content>
    <div class="c-new-ideas c-grid-3-cols">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
            <#assign rootElement = document.getRootElement() />
            <#assign cur_content = ""/>
            <#assign cur_expertName = ""/>
            <#assign cur_phase = ""/>
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
                                <#if "NewIdeaContent"==child.attributeValue("name")>
                                    <#assign cur_content = child.element("dynamic-content").getData()/>
                                </#if>
                                <#if "ExpertName"==child.attributeValue("name")>
                                    <#assign cur_expertName = child.element("dynamic-content").getData()/>
                                </#if>
                                <#if "ExpertEmailAddress"==child.attributeValue("name")>
                                    <#assign cur_expertEmailAddress = child.element("dynamic-content").getData()/>
                                </#if>
                                <#if "NewIdeaPhase"==child.attributeValue("name")>
                                    <#assign cur_phase =  child.element("dynamic-content").getData()?string?replace('["', "")?replace('"]', "")/>
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
                
            <div class="c-new-ideas__item c-asset-publisher-item">
                <h4 class="c-new-ideas__item__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>
                <div class="c-new-ideas__item__summary">${stringUtil.shorten(cur_content, 136)}</div>
                <div class="c-new-ideas__item__rating clearfix"><@getRatings entry/></div>
                <p class="c-new-ideas__item__phase ${cur_phase}">
                    <strong class="font-weight-regular">Phase:&nbsp;</strong>
                    <span class="submitted">Submitted</span>
                    <span class="planned">Project Plan</span>
                    <span class="financed">Financed</span>
                    <span class="started">Start project</span>
                </p>
                <a class="c-new-ideas__item__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
            </div>
        </#list>
    </div>
</#if>

<#macro getRatings entry>
    <#if getterUtil.getBoolean(enableRatings) && assetRenderer.isRatable()>
        <div class="asset-ratings ideas-adt">
            <@liferay_ui["ratings"]
                className=entry.getClassName()
                classPK=entry.getClassPK()
            />
        </div>
    </#if>
</#macro>