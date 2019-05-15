<#if entries?has_content>
    <div class="c-new-ideas c-grid-3-cols">
        <#list entries as entry>
            <#assign
                entry = entry
                assetRenderer = entry.getAssetRenderer()
                journalArticle = assetRenderer.getArticle()
                entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale))
                viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, assetRenderer, entry, !stringUtil.equals(assetLinkBehavior, "showFullContent"))
                document = saxReaderUtil.read(journalArticle.getContent())
                rootElement = document.getRootElement()
            />
            <#assign sectionContent = "" />
            <#assign sectionPhase = "" />
            <#list rootElement.elements() as dynamicElement>
                <#if "NewIdeaSectionHeader"==dynamicElement.attributeValue("name") && !sectionContent?has_content>
                    <#assign sectionContent = dynamicElement.element("dynamic-content").getData() />
                    <#if sectionContent?has_content >
                        <#assign sectionContent = sectionContent?string + ": "/>
                    </#if>
                    <#assign sectionContent = sectionContent + dynamicElement.element("dynamic-element").element("dynamic-content").getData()?string/>
                </#if>
                <#if "NewIdeaPhase"==dynamicElement.attributeValue("name")>
                    <#assign sectionPhase =  dynamicElement.element("dynamic-content").getData()?string?replace('["', "")?replace('"]', "")/>
                </#if>
            </#list>
            
            <div class="c-new-ideas__item c-asset-publisher-item">
                <h3 class="c-new-ideas__item__title h1">${entryTitle}</h2>
                <div class="c-new-ideas__item__summary">${stringUtil.shorten(sectionContent, 136)}</div>
                <div class="c-new-ideas__item__rating clearfix"><@getRatings /></div>
                <p class="c-new-ideas__item__phase ${sectionPhase}">
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

<#macro getRatings>
    <#if getterUtil.getBoolean(enableRatings) && assetRenderer.isRatable()>
        <div class="asset-ratings only_average">
            <p>Votes:&nbsp;</p>
            <@liferay_ui["ratings"]
                className=entry.getClassName()
                classPK=entry.getClassPK()
            />
        </div>
    </#if>
</#macro>