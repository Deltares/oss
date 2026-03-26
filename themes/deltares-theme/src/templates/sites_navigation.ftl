<#assign
	unicodePropertiesBuilder = staticUtil["com.liferay.portal.kernel.util.UnicodePropertiesBuilder"]
	/>
<div class="absolute w-full left-0 overflow-hidden sites-navpanel">
	<nav class="${nav_css_class} w-full h-full bg-app-blue--egyptian z-20 overflow-auto sites-mainnav" id="sites-navigation" role="navigation">
		<h1 class="hide-accessible" hidden=""><@liferay.language key="navigation" /></h1>
		<ol aria-label="<@liferay.language key="site-pages" />" class="mobile-mainnav-inner py-3" role="menubar">
			<#list menu_sites_items as menu_sites_item>

				<#assign  settings = unicodePropertiesBuilder.fastLoad(menu_sites_item.getTypeSettings()).build() />

				<#if settings?? && settings.get("url")?? >
					<#assign
					url= settings.get("url")
					defaultLanguageId = settings.get("defaultLanguageId")
					name = settings.get("name_" + defaultLanguageId)
					newTab = settings.get("useNewTab")
					target = (newTab?string == "true")?then("_blank", "_self")
					/>
					<li class="relative border-b border-solid border-white sites-mainnav__item"  role="presentation">
						<a class="block text-xl font-medium text-white" href="${url}" target="${target}" role="menuitem">
							${name}
						</a>
					</li>
				</#if>


			</#list>
		</ol>
	</nav>
</div>