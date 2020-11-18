<#if entries?has_content>
    <div class="c-faq-page">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign cur_question = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign cur_answer = "" />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign document = saxReaderUtil.read(journalArticle.getContent())/>
            <#assign rootElement = document.getRootElement() />
            <#list rootElement.elements() as dynamicElement>
                <#if "FAQAnswer"==dynamicElement.attributeValue("name")>
                    <#assign cur_answer = dynamicElement.element("dynamic-content").getData() />
                </#if>
            </#list>
            <div class="c-faq__item collapsed">
                <div class="c-faq__item__content">
                    <h4 class="c-faq__item__content__title">
                        <a href="#" class="toggler regular-text">
                            ${cur_question}
                            &nbsp;&nbsp;<span>&#9654;</span>
                        </a>
                    </h4>
                    <div class="c-faq__item__content__data">
                        ${cur_answer}
                    </div>
                </div>
            </div>
        </#list>
    </div>
    <script>
        AUI().ready('aui-module', function(A){
            $(".c-faq__item").on("click", ".toggler", function( event ) {
                event.preventDefault();
                $( event.delegateTarget ).toggleClass("collapsed expanded");
            });
        });
    </script>
</#if>