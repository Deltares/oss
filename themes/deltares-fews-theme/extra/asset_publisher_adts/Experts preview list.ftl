<#if entries?has_content>
    <#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />

    <section id="results" class="expert-list">
        <div class="w-full mx-auto">
            <ol class="grid grid-cols-12 auto-rows-max gap-y-10 gap-6 sm:gap-10 2xl:gap-20">
                <#list entries as curentry>
                    <#assign entry = curentry />
                    <#assign assetRenderer = entry.getAssetRenderer()/>
                    <#assign journalArticle = assetRenderer.getArticle()/>
                    <#assign viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, assetRenderer, entry, !stringUtil.equals(assetLinkBehavior, "showFullContent"))/>
                    <#assign document = xmlUtils.parseContent(journalArticle, locale)/>
                    <#assign cur_expertName =  xmlUtils.getDynamicContentByName(document, "ExpertName", false) />
                    <#assign overviewPhotoSelector = xmlUtils.getDynamicContentByName(document, "ExpertImage", false)  />
                    <#assign cur_expertImage = ddlUtils.getFileEntryImage(overviewPhotoSelector) />
                    <#assign cur_expertJobTitle = xmlUtils.getDynamicContentByName(document, "ExpertJobTitle", false)/>
                    <#assign cur_expertCompany = xmlUtils.getDynamicContentByName(document, "ExpertCompany", false) />
                    <#assign cur_expertEmail = xmlUtils.getDynamicContentByName(document, "ExpertEmailAddress", false) />
                    <li class="col-span-6 sm:col-span-4 lg:col-span-6 xl:col-span-4">
                        <article class="relative group flex flex-col font-medium font-sans leading-none">
                            <div class="order-2 relative w-full content-container">
                                <h2 class="font-semibold text-theme-secondary text-base">${cur_expertName}</h2>
                                <#if cur_expertJobTitle != "">
                                    <p class="mb-3 text-[12px] expert-jobtitle"><strong>${cur_expertJobTitle}</strong></p>
                                </#if>
                                <#if cur_expertCompany != "">
                                    <p class="mb-3 text-[12px] expert-company">${cur_expertCompany}</p>
                                </#if>
                                <#if cur_expertEmail != "">
                                    <span class="block mb-3 lg:mb-5 border-b border-theme-primary"></span>
                                    <div class="expert-contact-details">
                                        <h4 class="block text-[12px] font-semibold text-theme-secondary">Contact</h4>
                                        <p class="expert-email">
                                            <a class="inline-block text-[12px] underline hover:text-theme-secondary" href="mailto:${cur_expertEmail}">${cur_expertEmail}</a>
                                        </p>
                                    </div>
                                </#if>
                            </div>
                            <div class="order-1 relative w-full overflow-hidden image-container">
                                <div class="block w-full object-cover overflow-hidden">
                                    <#if cur_expertImage != "">
                                        <div class="expert-image">
                                            <img width="640" height="564" class="block w-full h-full object-cover" alt="${cur_expertName}" src="${cur_expertImage}" />
                                        </div>
                                    <#else>
                                        <div class="expert-image">
                                            <img src="${themeDisplay.getPathThemeImages()}/person-placeholder.svg" class="w-full aspect-[160/141] object-cover overflow-hidden" />
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </article>
                    </li>
                </#list>
            </ol>
        </div>
    </section>
</#if>