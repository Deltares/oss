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

                        <#assign overviewPhotoSelector = xmlUtils.getDynamicContentByName(rootElement, "ProjectImage", true) />
                        <#assign ProjectImage = ddlUtils.getFileEntryImage(overviewPhotoSelector) />

                        <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

                        <li class="col-span-full sm:col-span-6 lg:col-span-4 2xl:col-span-3">
                            <a href="${viewURL}" title="read more about ${entryTitle}" class="c-card-image-text text-theme-secondary rounded overflow-hidden h-full flex sm:flex-col sm:hover:shadow-md sm:focus:shadow-md sm:bg-white transition duration-200 hover:no-underline focus:no-underline hover:text-theme-secondary">
                                <#if ProjectImage?? && ProjectImage!= "">
                                    <div class="relative shrink-0 w-[100px] sm:w-full">
                                        <div class="c-card-image-text__image sm:rounded-none block w-full object-cover overflow-hidden">
                                            <picture>
                                                <img width="100" height="200" src="${ProjectImage}" class="block w-full h-full object-cover" />
                                            </picture>
                                        </div>
                                    </div>
                                </#if>
                                <div class="flex-auto flex flex-col sm:p-4 lg:p-6 px-4">
                                    <h4 class="leading-snug font-semibold font-medium text-lg lg:text-xl text-theme-secondary mb-1 lg:mb-2">${entryTitle?no_esc}</h4>
                                    <#assign fieldSets = xmlUtils.getDynamicElementsByNameAsList(rootElement, "ProjectSectionHeaderFieldSet") />
                                    <#list fieldSets as fieldSet>
                                        <#assign projectSectionContent = xmlUtils.getDynamicContentByName(fieldSet, "ProjectSectionHeader", true) />
                                        <#if projectSectionContent?has_content>
                                            <#assign projectSectionContent = projectSectionContent + ": "/>
                                        </#if>
                                        <#assign projectSectionContent = projectSectionContent + xmlUtils.getDynamicContentByName(fieldSet, "ProjectSectionHTML", false)/>
                                    </#list>
                                    <#outputformat "HTML">
                                        <div class="text-sm font-medium line-clamp-3">${stringUtil.shorten(projectSectionContent, 200)?replace('<[^>]+>','','r')}</div>
                                    </#outputformat>
                                    <footer class="pt-4 sm:pt-6 mt-auto flex items-center justify-end">
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