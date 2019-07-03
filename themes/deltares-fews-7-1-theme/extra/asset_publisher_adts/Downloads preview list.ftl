<#if entries?has_content>
    <div class="c-downloads-container">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
            <#assign rootElement = document.getRootElement() />
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <#assign  maxListElements = 5 />
            <div class="c-downloads">
                <h5 class="c-downloads-title">${entryTitle}</h5>
                <ul class="c-downloads-list clearList">
                    <#list rootElement.elements()>
                        <#items as dynamicElement>
                            <#if (dynamicElement?counter > maxListElements)>
                                <li class="c-downloads-list__item not_visible">
                            <#else>
                                <li class="c-downloads-list__item">
                            </#if>
                                <#assign downloadItemTitle = dynamicElement.element("dynamic-content").getData() />
                                <#assign downloadItemURL = ""/>
                                <#assign linkItemURL = ""/>
                                <#list dynamicElement.elements() as child>
                                    <#if child.getName()=="dynamic-element">
                                        <#assign isDocument = false />
                                        <#if "UploadFile"==child.attributeValue("name")>
                                            <#assign downloadItemRaw = child.element("dynamic-content").getData()/>
                                            <#assign downloadItemURL = htmlUtil.escapeHREF(downloadItemRaw)/>
                                            <#assign isDocument = true />
                                        </#if>
                                        <#if "DownloadLinkURL"==child.attributeValue("name")>
                                            <#assign downloadItemRaw = child.element("dynamic-content").getData()/>
                                            <#assign linkItemURL = htmlUtil.escapeHREF(downloadItemRaw)/> 
                                        </#if>
                                    </#if>
                                    
                                </#list>    
                                <#if isDocument > 
                                    <a  class="c-downloads-list__item__link regular-text"
                                        href="${downloadItemURL}" download >
                                <#else> 
                                    <a  class="c-downloads-list__item__link regular-text"
                                        href="${linkItemURL}" target="_blank" >
                                </#if>
                                    <span class="link_underline">${downloadItemTitle}</span> &gt;
                                </a>
                            </li>
                            <#if (dynamicElement?counter > maxListElements)>
                                <#if dynamicElement?is_last>
                                    <li class="c-downloads-list__item">
                                        <a class="c-downloads-list__item regular-text expand_list">
                                            <span class="link_underline">
                                                <span class="expand">All ${entryTitle}</span> 
                                                <span class="collapse">Hide ${entryTitle}</span> 
                                            </span> &gt;
                                        </a>
                                    </li>
                                </#if>
                            </#if>
                        </#items>
                    </#list>
                </ul>
            </div>
        </#list>
    </div>
    <script> 
        AUI().ready('aui-module', function(A){
            $(".c-downloads-list").on("click",".expand_list", function( event ) {
                event.preventDefault();
                $( event.delegateTarget ).toggleClass("expand");
            });
        });
    </script>
</#if>