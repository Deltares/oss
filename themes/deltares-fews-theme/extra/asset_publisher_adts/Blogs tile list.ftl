<#ftl output_format="XML">

<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />

<section class="col-span-12">
    <section id="results" aria-relevant="all" class="pb-14 component-news-list">
        <div class="container mx-auto">
            <ol class="grid grid-cols-12 gap-y-6 sm:gap-8">
                <#if entries?has_content>
                    <#list entries as entry>
                        <#assign assetRenderer = entry.getAssetRenderer() />
                        <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
                        <#assign journalArticle = assetRenderer.getArticle() />
                        <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>
                        <#assign cur_content = xmlUtils.getDynamicContentByName(rootElement, "BlogContent", false) />
                        <#assign overviewPhotoSelector = xmlUtils.getDynamicContentByName(rootElement, "BlogImage", false) />
                        <#assign cur_image = ddlUtils.getFileEntryImage(overviewPhotoSelector) />
                        <#assign cur_expertName = xmlUtils.getDynamicContentByName(rootElement, "ExpertName", false) />
                        <#assign cur_expertEmailAddress = xmlUtils.getDynamicContentByName(rootElement, "ExpertEmailAddress", false) />

                        <#assign cur_summary = xmlUtils.getDynamicContentByName(rootElement, "Summary", true) />
                        <#assign cur_expertCompany = xmlUtils.getDynamicContentByName(rootElement, "ExpertCompany", true) />
                        <#assign cur_expertJobTitle = xmlUtils.getDynamicContentByName(rootElement, "ExpertJobTitle", true)/>
                        <#assign cur_expertPhoto = xmlUtils.getDynamicContentByName(rootElement, "ExpertPhoto", true) />
                        <#assign cur_selectedExpert = xmlUtils.getDynamicContentByName(rootElement, "SelectExpert_hide", true) />
                        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

                        <!--repeatable element-->
                        <li class="col-span-full sm:col-span-6 lg:col-span-4 2xl:col-span-3">
                            <a href="${viewURL}" title="read more about ${entryTitle}" class="c-card-image-text text-theme-secondary rounded overflow-hidden h-full flex sm:flex-col sm:hover:shadow-md sm:focus:shadow-md sm:bg-white transition duration-200 hover:no-underline focus:no-underline hover:text-theme-secondary">
                                <div class="relative shrink-0 w-[100px] sm:w-full">
                                    <div class="c-card-image-text__image sm:rounded-none block w-full object-cover overflow-hidden">
                                        <picture>
                                            <img width="100" height="200" src="${cur_image}" class="block w-full h-full object-cover" />
                                        </picture>
                                    </div>
                                </div>
                                <div class="flex-auto flex flex-col sm:p-4 lg:p-6 px-4">
                                    <h4 class="leading-snug font-semibold font-medium text-lg lg:text-xl text-theme-secondary mb-1 lg:mb-2">${entryTitle?no_esc}</h4>
                                    <p class="order-first line-clamp-3 text-sm leading-snug font-medium mb-2">${cur_expertName}</p>
                                    <#outputformat "HTML">
                                        <div class="text-sm font-medium line-clamp-3">
                                            <#if cur_summary??>
                                                ${cur_summary}
                                            <#else>
                                                ${stringUtil.shorten(cur_content, 350)?replace('<[^>]+>','','r')}
                                            </#if>
                                        </div>
                                    </#outputformat>
                                    <footer class="pt-4 sm:pt-6 mt-auto flex items-center justify-between">
                                        <p class="text-sm font-bold uppercase leading-none">${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="hidden sm:block sm:w-5 sm:h-5">
                                            <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                                        </svg>
                                    </footer>
                                </div>
                            </a>
                        </li>
                    </#list>
                </#if>
            </ol>
        </div>
    </section>
</section>