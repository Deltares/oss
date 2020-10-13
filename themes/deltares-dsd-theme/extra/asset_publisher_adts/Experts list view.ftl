<#if entries?has_content>
    <div class="c-experts__page">
        <#list entries as curentry>
            <#assign entry = curentry />
            <#assign assetRenderer = entry.getAssetRenderer()/>
            <#assign journalArticle = assetRenderer.getArticle()/>
            <#assign viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, assetRenderer, entry, !stringUtil.equals(assetLinkBehavior, "showFullContent"))/>
            <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
            <#assign rootElement = document.getRootElement()/>
            <#assign cur_expertName = ""/>
            <#assign cur_expertImage = ""/>
            <#assign cur_expertJobTitle = ""/>
            <#assign cur_expertCompany = ""/>
            <#assign cur_expertEmail = ""/>

            <#list rootElement.elements()>
                <#items as dynamicElement>
                    <#if "expertName"==dynamicElement.attributeValue("name")>
                        <#assign cur_expertName = dynamicElement.element("dynamic-content").getData() />
                    </#if>
                    
                    <#list dynamicElement.elements() as child>
                        <#if child.getName()=="dynamic-element">
                            <#if "expertImage"==child.attributeValue("name")>
                                <#assign overviewPhotoData = child.element("dynamic-content").getData()/>
                                <#assign cur_expertImage = ddlUtils.getFileEntryImage(overviewPhotoData) />
                            </#if>
                            <#if "expertJobTitle"==child.attributeValue("name")>
                                <#assign cur_expertJobTitle = child.element("dynamic-content").getData() />
                            </#if>
                            <#if "expertCompany"==child.attributeValue("name")>
                                <#assign cur_expertCompany = child.element("dynamic-content").getData() />
                            </#if>
                            <#if "expertEmailAddress"==child.attributeValue("name")>
                                <#assign cur_expertEmail = child.element("dynamic-content").getData() />
                            </#if>
                        </#if>
                    </#list>
                    
                    <div class="expert-data">
                        <#if cur_expertImage != "">
                            <div class="expert-data__image"  style="background-image:url(${cur_expertImage})">
                                <img alt="${cur_expertName}" src="${cur_expertImage}" />
                            </div>  
                        <#else>
                            <div class="expert-data__image">
                                ${stringUtil.shorten(cur_expertName, 1)}
                            </div>
                        </#if>
                        <div class="expert-data__content">
                            <h4 class="h1 clear-margin">${cur_expertName}</h4>
                            <#if cur_expertJobTitle != "">
                                <p><strong>${cur_expertJobTitle}</strong></p>
                            </#if>
                            <#if cur_expertCompany != "">
                                <p>${cur_expertCompany}</p>
                            </#if>
                            <#if cur_expertEmail != "">
                                <p><a href="mailto:${cur_expertEmail}" >${cur_expertEmail}</a></p>
                            </#if>
                        </div>
                    </div>    
                </#items>
            </#list>
        </#list>
    </div>
</#if>