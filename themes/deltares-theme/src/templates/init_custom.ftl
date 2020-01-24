<#--
This file allows you to override and define new FreeMarker variables.
-->

<#assign variable_name = getterUtil.getBoolean(theme_settings["language-key"])>

<#assign VOID = freeMarkerPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone") />

<#assign portal_url = htmlUtil.escape(theme_display.getPortalURL()) />

<#assign current_url = htmlUtil.escape(theme_display.getURLCurrent()) />

<#if !is_site_admin >
  <#assign css_class = stringUtil.replace(css_class, "open", "closed") />
</#if>

<#if current_url == "/" || current_url == "/web/guest" || current_url == "/web/guest/home" || current_url == "/home">
  <#assign is_home = "home" />
<#else>
  <#assign is_home = "nohome" />
</#if>
