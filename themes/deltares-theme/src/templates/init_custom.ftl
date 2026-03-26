<#--
This file allows you to override and define new FreeMarker variables.
-->

<#assign
layoutSet = layout.getLayoutSet()
company_logo = htmlUtil.escape(themeDisplay.getCompanyLogo())
site_name = htmlUtil.escape(themeDisplay.getSiteGroupName())
/>

<#if layoutSet.isLogo() && company_logo??>
    <#assign logo_img = htmlUtil.escape(company_logo) />
<#else>
    <#assign logo_img = images_folder + '/logo.png' />
</#if>

<#assign journalArticleLocalService = serviceLocator.findService("com.liferay.journal.service.JournalArticleLocalService") />

<#assign footerWC = "" />

<#assign footerContentID = getterUtil.getString(theme_settings["page-footer-webcontent-id"]) >
<#assign registration_form_id = getterUtil.getString(theme_settings["registration-form-id"]) >
<#assign menu_extensions_name = getterUtil.getString(theme_settings["menu-extensions-name"]) >
<#assign menu_sites_name = getterUtil.getString(theme_settings["menu-sites-name"]) >

<#assign
    navigationMenuLocalService = serviceLocator.findService("com.liferay.site.navigation.service.SiteNavigationMenuLocalService")
    navigationMenuItemLocalService = serviceLocator.findService("com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService")
    unicodePropertiesBuilder = staticUtil["com.liferay.portal.kernel.util.UnicodePropertiesBuilder"]
/>
<#if menu_extensions_name ?? && menu_extensions_name != "" >

    <#assign
        menu_extensions = navigationMenuLocalService.fetchSiteNavigationMenuByName(themeDisplay.getScopeGroupId(), menu_extensions_name)
        menu_extensions_items = navigationMenuItemLocalService.getSiteNavigationMenuItems(menu_extensions.getSiteNavigationMenuId())
    />

</#if>

<#if menu_sites_name ?? && menu_sites_name != "" >
    <#assign
        menu_sites = navigationMenuLocalService.fetchSiteNavigationMenuByName(themeDisplay.getScopeGroupId(), menu_sites_name)
        menu_sites_items = navigationMenuItemLocalService.getSiteNavigationMenuItems(menu_sites.getSiteNavigationMenuId())
    />
</#if>

<#-- If this page has an open day colour scheme, drop the banner on -->

<#assign footerWCArticleId = footerContentID >

<#if journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroupId(), footerWCArticleId)?? >
    <#assign footerWCContent = journalArticleLocalService.getArticleDisplay(themeDisplay.getScopeGroupId(), footerWCArticleId, "VIEW", locale, themeDisplay ).getContent() >
<#elseif themeDisplay.getScopeGroup().getParentGroupId()?? &&
journalArticleLocalService.fetchArticle(themeDisplay.getScopeGroup().getParentGroupId(), footerWCArticleId)?? >
    <#assign footerWCContent = journalArticleLocalService.getArticleDisplay(themeDisplay.getScopeGroupId(), footerWCArticleId, "VIEW", locale, themeDisplay ).getContent() >
</#if>

<#if is_site_amin?? && !is_site_admin >
    <#assign css_class = stringUtil.replace(css_class, "open", "closed") />
</#if>

<#assign homeUrl =  (themeDisplay.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()) />
<#assign siteUrl = (homeUrl + themeDisplay.getSiteGroup().getFriendlyURL()) />
<#assign imagesUrl = (themeDisplay.getPathThemeImages()) />
<#assign is_shopping_cart = getterUtil.getBoolean(theme_settings["shopping-cart"]) />
