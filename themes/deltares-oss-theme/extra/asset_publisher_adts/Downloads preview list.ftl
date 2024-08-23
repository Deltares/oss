<style type="text/css">
    a.disabled {
        pointer-events: none;
        cursor: default;
    }

    #terms_popup_cont{
        position:absolute;
        top:0px;
        left:0px;
        width:100vw;
        height:100vh;
    }

    #terms_popup{
        margin: auto;
        display:flex;
        flex-direction: column;
        justify-content: center;
        min-height: 100vh;
        max-width:800px;
    }

    #terms_popup.modal-header {
        padding: 2px 16px;
    }


    #terms_popup .modal-header .pop-close{
        display: block;
        position: absolute;
        right: 8px;
        margin: 8px;
        cursor: pointer;
    }

    /* Modal Body */
    #terms_popup.modal-body {
        position:relative;
        padding: 2px 16px;
    }

    /* Modal Footer */
    #terms_popup.modal-footer {
        padding: 8px 16px;
    }

    #terms_popup.modal-footer button{
        float:right;
        display:block;
        margin:12px;
    }

    /* Modal Content */
    #terms_popup.modal-content {
        background-color: #fefefe;
        margin: auto;
        padding: 0;
        border: 1px solid #888;
        width: 80%;
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
    }

</style>
<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />
<#if entries?has_content>
    <!--input type="checkbox" id="terms" name="terms" class="toggle-container">
    <label for="terms">To enable downloads please accept our <a href="terms-of-use" target="_blank">Terms of Use</a></label -->
    </br>
    <div class="c-downloads-container">

        <#list entries as entry>
            <#assign assetRenderer = entry.getAssetRenderer() />
            <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
            <#assign journalArticle = assetRenderer.getArticle() />
            <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>

            <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />
            <#assign  maxListElements = 5 />
            <div class="c-downloads">
                <h5 class="c-downloads-title">${entryTitle}</h5>
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
                            <li class="c-downloads-list__item">
                        </#if>
                        <#if isDocument >
                            <#assign downloadItemTitle = xmlUtils.getDynamicContentByName(fieldSet, "FileDisplayTitle", false) />
                            <#assign downloadItemURL =  xmlUtils.getDynamicContentByName(fieldSet, "UploadFile", false) />
                            <#assign downloadItemURL = ddlUtils.getFileEntryImage(downloadItemURL)/>
                        <#else>
                            <#assign downloadItemTitle = xmlUtils.getDynamicContentByName(fieldSet, "LinkTitle", false) />
                            <#assign downloadItemURL = xmlUtils.getDynamicContentByName(fieldSet, "DownloadLinkURL", false)/>
                        </#if>
                        <#assign hasTerms = xmlUtils.getDynamicContentByName(fieldSet, "hasTerms", true) />

                    <#if hasTerms?has_content && hasTerms?boolean >
                    <#if isDocument >
                        <a  class="download-link-termsPop c-downloads-list__item__link regular-text "
                            data-location="${downloadItemURL}" href="#" download >
                            <#else>
                            <a  class="download-link-termsPop c-downloads-list__item__link regular-text "
                                data-location="${downloadItemURL}"  href="#">
                                </#if>
                                <span class="link_underline">${downloadItemTitle}</span> &gt;
                            </a>
                            <#else>
                            <#if isDocument >
                            <a  class="c-downloads-list__item__link regular-text "
                                href="${downloadItemURL}" download >
                                <#else>
                                <a  class="c-downloads-list__item__link regular-text "
                                    href="${downloadItemURL}" target="_blank" >
                                    </#if>
                                    <span class="link_underline">${downloadItemTitle}</span> &gt;
                                </a>
                                </#if>

                        </li>
                        <#if (fieldSet?counter gte maxListElements)>
                            <#if fieldSet?is_last>
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

                    </#list>
                </ul>
            </div>
        </#list>
        <div id="terms_popup_cont"  style="display: none;">
            <div id="terms_popup" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="pop-close">&times;</span>
                        <h1 class="pop-title" style="position: absolute; top: 4px;">Terms and conditions</h1>
                    </div>
                    <div class="modal-body">
                        <p>
                            These files are provided without service and support. In order to download these files you have to agree to our <a href="terms-of-use" target="_blank">Terms of Use</a>.
                        </p>
                        <input type="checkbox" id="terms" name="terms" class="toggle-container">
                        <label for="terms">I have read and accept the <a href="terms-of-use" target="_blank">Terms of Use</a></label>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="accept">Download</button>
                        <button class="reject">Reject</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        AUI().ready('aui-module', function(A){

            var inputElements = $(".toggle-container");
            var modalcontainter = $("#terms_popup_cont");
            var modal = $("#terms_popup");
            var webLink="";


            $(".c-downloads-list").on("click",".expand_list", function( event ) {
                event.preventDefault();
                $( event.delegateTarget ).toggleClass("expand");
            });

            $(".download-link-termsPop").on("click", function( event ) {
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
                if (checked == true)
                {
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