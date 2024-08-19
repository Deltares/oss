<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />

<#if entries?has_content>
    <!--input type="checkbox" id="terms" name="terms" class="toggle-container">
    <label for="terms">To enable downloads please accept our <a href="terms-of-use" target="_blank">Terms of Use</a></label -->

    <div class="c-downloads-container">
        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>
            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <#assign maxListElements = 5 />

            <div class="c-downloads">
                <h5 class="c-downloads-title font-medium text-xl lg:text-2xl text-theme-secondary mb-3 lg:mb-3">${entryTitle}</h5>
                <ul class="c-downloads-list clearList">
                    <#assign fieldSets = xmlUtils.getDynamicElementsByNameAsList(rootElement, "FileDisplayTitleFieldSet") />
                    <#if fieldSets?size = 0>
                        <!-- Is List of download links -->
                        <#assign fieldSets = xmlUtils.getDynamicElementsByNameAsList(rootElement, "LinkTitleFieldSet") />
                        <#assign isDocument = false />
                    <#else>
                        <!-- Is List of download files -->
                        <#assign isDocument = true />
                    </#if>
                    <#list fieldSets as fieldSet>
                        <#if (fieldSet?counter gte maxListElements)>
                            <li class="c-downloads-list__item not_visible">
                        <#else>
                            <li class="c-downloads-list__item mb-1.5">
                        </#if>
                        <#if isDocument>
                            <#assign downloadItemTitle = xmlUtils.getDynamicContentByName(fieldSet, "FileDisplayTitle", false) />
                            <#assign downloadItemURL = xmlUtils.getDynamicContentByName(fieldSet, "UploadFile", false) />
                            <#assign downloadItemURL = ddlUtils.getFileEntryImage(downloadItemURL)/>
                        <#else>
                            <#assign downloadItemTitle = xmlUtils.getDynamicContentByName(fieldSet, "LinkTitle", false) />
                            <#assign downloadItemURL = xmlUtils.getDynamicContentByName(fieldSet, "DownloadLinkURL", false)/>
                        </#if>
                        <#assign hasTerms = xmlUtils.getDynamicContentByName(fieldSet, "hasTerms", true) />
                        <#if hasTerms?has_content && hasTerms?boolean>
                            <#if isDocument>
                                <a class="download-link-termsPop c-downloads-list__item__link relative inline-flex flex-row items-center text-app-blue--egyptian hover:underline v-hover--shade transition-colors duration-150 cursor-pointer font-medium" data-location="${downloadItemURL}" href="#" download>
                            <#else>
                                <a class="download-link-termsPop c-downloads-list__item__link relative inline-flex flex-row items-center text-app-blue--egyptian hover:underline v-hover--shade transition-colors duration-150 cursor-pointer font-medium" data-location="${downloadItemURL}" href="#">
                            </#if>
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start text-theme-quaternary shrink-0 w-3 h-3"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
                                <span class="link_underline">${downloadItemTitle}</span>
                            </a>
                        <#else>
                            <#if isDocument>
                                <a class="c-downloads-list__item__link relative inline-flex flex-row items-center text-app-blue--egyptian hover:underline v-hover--shade transition-colors duration-150 cursor-pointer font-medium" href="${downloadItemURL}" download>
                            <#else>
                                <a class="c-downloads-list__item__link relative inline-flex flex-row items-center text-app-blue--egyptian hover:underline v-hover--shade transition-colors duration-150 cursor-pointer font-medium" href="${downloadItemURL}" target="_blank">
                            </#if>
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start text-theme-quaternary shrink-0 w-3 h-3"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
                                <span class="link_underline">${downloadItemTitle}</span>
                            </a>
                        </#if>
                        </li>
                        <#if (fieldSet?counter gte maxListElements)>
                            <#if fieldSet?is_last>
                                <li class="c-downloads-list__item more-link">
                                    <a class="c-downloads-list__item expand_list relative inline-flex flex-row items-center text-app-blue--egyptian transition-colors duration-150 cursor-pointer font-medium">
                                        <span class="more-link-text">
                                            <span class="expand">All ${entryTitle}</span>
                                            <span class="collapse">Hide ${entryTitle}</span>
                                        </span>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start shrink-0 w-3 h-3"><path fill="currentColor" d="M1.5,8.1c0.4-0.4,0.8-0.5,1.3-0.5c0.5,0,1,0.2,1.3,0.5L16,19.9L27.8,8.1C28,8,28.2,7.8,28.4,7.7
        c0.2-0.1,0.5-0.1,0.7-0.2c0.2,0,0.5,0,0.7,0.1c0.2,0.1,0.4,0.2,0.6,0.4c0.2,0.2,0.3,0.4,0.4,0.6C31,9,31,9.2,31,9.4
        c0,0.2-0.1,0.5-0.2,0.7c-0.1,0.2-0.2,0.4-0.4,0.6L17.3,23.9c-0.4,0.4-0.8,0.5-1.3,0.5c-0.5,0-1-0.2-1.3-0.5L1.5,10.8
        C1.2,10.4,1,10,1,9.5C1,9,1.2,8.5,1.5,8.1z"></path></svg>
                                    </a>
                                </li>
                            </#if>
                        </#if>
                    </#list>
                </ul>
            </div>
        </#list>
        <div id="terms_popup_cont" style="display: none;">
            <div id="terms_popup" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 class="pop-title font-semibold text-theme-secondary">Terms and conditions</h2>
                        <span class="pop-close">&times;</span>
                    </div>
                    <div class="modal-body">
                        <p class="mb-4">
                            These files are provided without service and support. In order to download these files you have to agree to our <a href="terms-of-use" target="_blank">Terms of Use</a>.
                        </p>
                        <input type="checkbox" id="terms" name="terms" class="toggle-container">
                        <label for="terms">I have read and accept the <a href="terms-of-use" target="_blank">Terms of Use</a></label>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary accept">Download</button>
                        <button class="btn reject">Reject</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        AUI().ready('aui-module', function(A) {
            var inputElements = $(".toggle-container");
            var modalcontainter = $("#terms_popup_cont");
            var modal = $("#terms_popup");
            var webLink="";

            $(".c-downloads-list").on("click",".expand_list", function(event) {
                event.preventDefault();
                $(event.delegateTarget).toggleClass("expand");
                $(event.delegateTarget).find('.not_visible').toggleClass("mb-1.5");
                $(this).find('svg').toggleClass("rotate-180");
            });

            $(".download-link-termsPop").on("click", function(event) {
                event.preventDefault();
                webLink=$(this).attr("data-location");
                console.log("clicked link", webLink);
                modalcontainter.css("display", "block");
            });

            $(".modal .modal-header .pop-close").on("click", function() {
                modalcontainter.css("display", "none");
                inputElements.prop('checked', false);
            });

            $(".modal .modal-footer .reject").on("click", function() {
                modalcontainter.css("display", "none");
                inputElements.prop('checked', false);
            });

            $(".modal .modal-footer .accept").on("click", function() {
                var checked = inputElements.is(":checked")
                if (checked == true) {
                    inputElements.prop('checked', false);
                    var anchor = document.createElement('a');

                    anchor.href = webLink;
                    anchor.target = '_blank';
                    anchor.download = webLink;
                    anchor.click();

                    modalcontainter.css("display", "none");
                }
            });

            window.onclick = function(event) {
                console.log("no true");

                if (event.target == $("#terms_popup_cont")[0] || event.target == $("#terms_popup")[0]) {
                    console.log($(event.target), modal, modalcontainter)
                    modalcontainter.css("display", "none");
                }
            }

            /*
            $(".toggle-container").on("click", function( event ) {
                links = document.getElementsByClassName("c-downloads-list__item__link");

                for (let index = 0; index < links.length; ++index){
                    var link = links[index];
                    if (event.target.checked){
                        link.classList.remove("disabled");
                    } else {
                        link.classList.add("disabled");
                    }
                };
            });*/
        });
    </script>
</#if>