<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />

<#if entries?has_content>
    <div class="blog-page">
        <#list entries as entry>
            <div class="blog-page__item clearfix">
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign rootElement = xmlUtils.parseContent(journalArticle,locale)/>
                <#assign cur_content = xmlUtils.getDynamicContentByName(rootElement, "BlogContent", false) />
                <#assign overviewPhotoSelector = xmlUtils.getDynamicContentByName(rootElement, "BlogImage", false)  />
                <#assign cur_image = ddlUtils.getFileEntryImage(overviewPhotoSelector) />
                <#assign cur_expertName = xmlUtils.getDynamicContentByName(rootElement, "ExpertName", false) />
                <#assign cur_expertEmailAddress = xmlUtils.getDynamicContentByName(rootElement, "ExpertEmailAddress", false) />

                <#assign cur_summary = xmlUtils.getDynamicContentByName(rootElement, "Summary", true) />
                <#assign cur_expertCompany = xmlUtils.getDynamicContentByName(rootElement, "ExpertCompany", true) />
                <#assign cur_expertJobTitle = xmlUtils.getDynamicContentByName(rootElement, "ExpertJobTitle", true)/>
                <#assign cur_expertPhoto = xmlUtils.getDynamicContentByName(rootElement, "ExpertPhoto", true) />
                <#assign cur_selectedExpert = xmlUtils.getDynamicContentByName(rootElement, "SelectExpert_hide", true) />
                <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />


                <div class="left-column">
                    <!--Blog image-->
                    <a
                            class="blog-page__item__meta-data__image display-block"
                            style="background-image:url(${cur_image})"
                            href="${viewURL}"
                            title="read more about ${entryTitle}">
                        <img src="${cur_image}" />
                    </a>
                </div>
                <div class="right-column">
                    <!--Blog title-->
                    <h4 class="blog-page__item__meta-data__title font-medium text-lg lg:text-xl text-theme-secondary mb-3 lg:mb-2"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>

                    <!--Blog Expert-->
                    <div class="blog-page__item__meta-data__expert">
                        <#assign expertExists = false />
                        <#if cur_selectedExpert?has_content>
                            <#assign cur_webContent_map = cur_selectedExpert?eval />
                            <#assign cur_webContent_classPK = cur_webContent_map.classPK />
                            <#assign article = journalArticleLocalService.fetchLatestArticle(cur_webContent_classPK?number)! />

                            <#if article?has_content && article.getStatus() == 0> <#-- status 0 == published-->
                                <#assign expertExists = true />
                                ${journalContentUtil.getContent(groupId, article.getArticleId(), viewMode, locale.getLanguage())}
                            </#if>
                        </#if>
                        <#if !expertExists>
                            <div class="expert-data">
                                <#if cur_expertPhoto != "">
                                    <div class="expert-data__image" style="background-image:url(${cur_expertPhoto})">
                                        <img alt="${cur_expertName}" src="${cur_expertPhoto}" />
                                    </div>
                                <#else>
                                    <div class="expert-data__image">
                                        ${stringUtil.shorten(cur_expertName, 1)}
                                    </div>
                                </#if>
                                <div class="expert-data__content">
                                    <p class="bold">${cur_expertName}</p>
                                </div>
                            </div>
                        </#if>

                        <!--Date-->
                        <p class="blog-page__item__meta-data__date"><#if expertExists><span>|</span></#if>${dateUtil.getDate(entry.getPublishDate(), "d MMMM yyyy", locale)}</p>
                    </div>

                    <div class="blog-page__item__meta-data__content text-sm sm:text-base">
                        <#if cur_summary??>
                            ${cur_summary}
                        <#else>
                            ${stringUtil.shorten(cur_content, 350)}
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#if>