<#if entries?has_content>
    <div class="c-faq-page">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign cur_question = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign cur_answer = "" />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign document = saxReaderUtil.read(journalArticle.getContent()) />
            <#assign rootElement = document.getRootElement() />
            <#list rootElement.elements() as dynamicElement>
                <#if "FAQAnswer"==dynamicElement.attributeValue("name")>
                    <#assign cur_answer = dynamicElement.element("dynamic-content").getData() />
                </#if>
            </#list>
            <#assign relatedPath = saxReaderUtil.createXPath("dynamic-element[@name='RelatedQuestion']")
            />
            <#assign relatedQuestions = relatedPath.selectNodes(rootElement) />
            <div id="${assetRenderer.getClassPK()}" class="c-faq__item collapsed">
                <div class="c-faq__item__content">
                    <h4 class="c-faq__item__content__title">
                        <a href="#" class="toggler flex items-center justify-between w-full font-medium text-theme-secondary hover:no-underline focus:no-underline hover:text-theme-secondary">
                            <span>${cur_question}</span>
                            <svg data-v-082866e3="" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="ml-auto shrink-0 w-3 h-3 transition-transform duration-150 rotate-0"><path fill="currentColor" d="M1.5,8.1c0.4-0.4,0.8-0.5,1.3-0.5c0.5,0,1,0.2,1.3,0.5L16,19.9L27.8,8.1C28,8,28.2,7.8,28.4,7.7
        c0.2-0.1,0.5-0.1,0.7-0.2c0.2,0,0.5,0,0.7,0.1c0.2,0.1,0.4,0.2,0.6,0.4c0.2,0.2,0.3,0.4,0.4,0.6C31,9,31,9.2,31,9.4
        c0,0.2-0.1,0.5-0.2,0.7c-0.1,0.2-0.2,0.4-0.4,0.6L17.3,23.9c-0.4,0.4-0.8,0.5-1.3,0.5c-0.5,0-1-0.2-1.3-0.5L1.5,10.8
        C1.2,10.4,1,10,1,9.5C1,9,1.2,8.5,1.5,8.1z"></path></svg>
                        </a>
                    </h4>
                    <div class="c-faq__item__content__data">
                        ${cur_answer}
                        <#if (relatedQuestions?size gt 0)>
                            <p>
                                <strong>Related Questions:</strong></br>
                                <#list relatedQuestions as relatedQuestion>
                                    <#assign questionContent=relatedQuestion.getStringValue()?eval />
                                    <a href="#${questionContent.classPK}" onClick="toggleBookMark('${questionContent.classPK}')" >${questionContent.title}</a></br>
                                </#list>
                            </p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <script>
        AUI().ready('aui-module', 'node', function(A){
            $(".c-faq__item").on("click", ".toggler", function( event ) {
                event.preventDefault();
                $(event.delegateTarget).toggleClass("collapsed expanded");
                $(event.delegateTarget).find('svg').toggleClass("rotate-0 rotate-180");
            });

            toggleBookMark = function(id){
                var question = A.one("#" + id);
                question.addClass("expanded");
            }
        });
    </script>
</#if>