<#--
This file allows you to override and define new FreeMarker variables.
-->

<#assign
layoutSet = layout.getLayoutSet()
company_logo = htmlUtil.escape(themeDisplay.getCompanyLogo())
/>

<#if layoutSet.isLogo() && company_logo??>
    <#assign logo_img = htmlUtil.escape(company_logo) />
<#else>
    <#assign logo_img = images_folder + '/logo.png' />
</#if>

<#assign journalArticleLocalService = serviceLocator.findService("com.liferay.journal.service.JournalArticleLocalService") />

<#assign footerWC = "" />

<#assign footerContentID = getterUtil.getString(theme_settings["page-footer-webcontent-id"]) >

<#-- If this page has an open day colour scheme, drop the banner on -->

<#assign defaultCompanyId = staticUtil["com.liferay.portal.kernel.util.PortalUtil"].getDefaultCompanyId()/>
<#assign groupLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.GroupLocalService") />
<#assign globalGroupId = groupLocalService.getCompanyGroup(defaultCompanyId).getGroupId()/>
<#assign footerWCArticleId = footerContentID >

<#if journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroupId(), footerWCArticleId)?? >
    <#assign footerWCArticle = journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroupId(), footerWCArticleId) >
    <#assign footerWCContent = journalArticleLocalService.getArticleContent(footerWCArticle, footerWCArticle.getDDMTemplateKey(), "VIEW", locale, themeDisplay) />
<#elseif themeDisplay.getScopeGroup().getParentGroupId()?? &&
journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroup().getParentGroupId(), footerWCArticleId)?? >
    <#assign footerWCArticle = journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroup().getParentGroupId(), footerWCArticleId) >
    <#assign footerWCContent = journalArticleLocalService.getArticleContent(footerWCArticle, footerWCArticle.getDDMTemplateKey(), "VIEW", locale, themeDisplay) />
</#if>

<#if !is_site_admin >
    <#assign css_class = stringUtil.replace(css_class, "open", "closed") />
</#if>

<#assign homeUrl =  (themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()) />
<#assign siteUrl = (homeUrl + themeDisplay.getSiteGroup().getFriendlyURL()) />

<#assign is_shopping_cart = getterUtil.getBoolean(theme_settings["shopping-cart"]) />