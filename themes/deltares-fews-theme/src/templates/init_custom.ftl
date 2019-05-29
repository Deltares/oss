<#--
This file allows you to override and define new FreeMarker variables.
-->

<#assign logo_img = images_folder + '/logo.png' />


<#assign journalArticleLocalService = serviceLocator.findService("com.liferay.journal.service.JournalArticleLocalService") />

<#assign footerWC = "" />



<#assign defaultCompanyId = staticUtil["com.liferay.portal.kernel.util.PortalUtil"].getDefaultCompanyId()/>
<#assign groupLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.GroupLocalService") />
<#assign globalGroupId = groupLocalService.getCompanyGroup(defaultCompanyId).getGroupId()/>
<#assign footerWCArticleId = getterUtil.getString(themeDisplay.getThemeSetting("page-footer-webcontent-id"))>

<#if journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroupId(), footerWCArticleId)?? >
    <#assign footerWCArticle = journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroupId(), footerWCArticleId) >
    <#assign footerWCContent = journalArticleLocalService.getArticleContent(footerWCArticle, footerWCArticle.getDDMTemplateKey(), "VIEW", locale, themeDisplay) />
</#if>


<#assign google_tag_id = getterUtil.getString(themeDisplay.getThemeSetting("google-tag-id")) /> <#-- google analytics -->