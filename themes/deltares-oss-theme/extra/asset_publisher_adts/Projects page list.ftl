<#assign xmlUtils = serviceLocator.findService("nl.deltares.portal.utils.XmlContentUtils") />

<div class="project-page">
    <#if entries?has_content>
        <#list entries as entry>
            <div class="project-page__item clearfix">
                <#assign assetRenderer = entry.getAssetRenderer() />
                <#assign entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale)) />
                <#assign journalArticle = assetRenderer.getArticle() />
                <#assign document = xmlUtils.parseContent(journalArticle,locale)/>
                <#assign overviewPhotoData = xmlUtils.getDynamicContentByName(document, "ProjectImage", true) />
                <#assign ProjectImage =  ddlUtils.getFileEntryImage(overviewPhotoData)  />
                <#assign viewURL = htmlUtil.escapeHREF(assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, entry, true)) />

                <div class="left-column small-form">
                    <#if ProjectImage?? && ProjectImage!= "">
                        <a class="project-page__item__meta-data__image square-form display-block"
                           style="background-image:url(${ProjectImage})"
                           href="${viewURL}"
                           title="read more about ${entryTitle}">
                            <img src="${ProjectImage}" />
                        </a>
                    <#else>
                        <a class="project-page__item__meta-data__image-backup display-block"
                           href="${viewURL}"
                           title="read more about ${entryTitle}"></a>
                    </#if>
                </div>
                <div class="right-column small-form">
                    <!--Project title-->
                    <h4 class="project-page__item__meta-data__title h1"><a class="type-inherit" href="${viewURL}" title="read more about ${entryTitle}">${entryTitle}</a></h4>

                    <div class="project-page__item__meta-data__content">

                        <#assign fieldSets = xmlUtils.getDynamicElementsByNameAsList(document, "ProjectSectionHeaderFieldSet") />
                        <#list fieldSets as fieldSet>
                            <#assign projectSectionContent = xmlUtils.getDynamicContentByName(fieldSet, "ProjectSectionHeader", true) />
                            <#if projectSectionContent?has_content >
                                <#assign projectSectionContent = projectSectionContent + ": "/>
                            </#if>
                            <#assign projectSectionContent = projectSectionContent + xmlUtils.getDynamicContentByName(fieldSet, "ProjectSectionHTML", false)/>

                        </#list>

                        ${stringUtil.shorten(projectSectionContent, 200)}
                    </div>
                    <!--Read more-->
                    <a class="c-card__link regular-text" href="${viewURL}"><span class="link_underline">Read more</span> &gt;</a>
                </div>
            </div>
        </#list>
    </#if>
</div>