<div class="main-navbar relative flex flex-row order-2 w-full px-10">
	<div class="site-logo pb-4">
		<a class="custom-logo inline-block text-app-blue--egyptian" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
			<#if logo_img??>
				<img alt="${logo_description}" class="h-8 w-auto" src="${logo_img}" />
			<#else>
			</#if>
		</a>
	</div>
	<div class="ignore-close flex flex-row items-center w-full">
		<div class="nav-menu ml-auto">
			<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
			<nav class="${nav_css_class}" id="navigation" role="navigation">
				<h1 class="hide-accessible" hidden=""><@liferay.language key="navigation" /></h1>

				<ol aria-label="<@liferay.language key="site-pages" />" class="flex flex-row items-center" role="menubar">
					<#list nav_items as nav_item>
						<#assign
							nav_item_attr_has_popup = ""
							nav_item_attr_selected = ""
							nav_item_css_class = ""
							nav_item_layout = nav_item.getLayout()
						/>

						<#if nav_item.isSelected()>
							<#assign
								nav_item_attr_has_popup = "aria-haspopup='true'"
								nav_item_attr_selected = "aria-selected='true'"
								nav_item_css_class = "selected"
							/>
						</#if>

						<li ${nav_item_attr_selected} class="${nav_item_css_class} mx-4 last:ml-4" id="layout_${nav_item.getLayoutId()}" role="presentation">
							<#if nav_item.hasChildren()>
								<button type="button" aria-expanded="false" class="block group relative text-lg font-medium text-app-blue--egyptian" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} role="menuitem">
									<span class="block"><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span>
									<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="absolute top-0 left-0 mt-9 ml-[50%] w-2 h-2 text-app-green--caribbean -translate-x-1/2 transition duration-150 opacity-0"><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
								</button>
								<div class="nav-subpanel absolute top-0 right-0 left-0 mt-[91px] p-3 overflow-hidden bg-app-white--cosmic-latte rounded-br-md rounded-bl-md v-mainnav_subpanel--desktop">
									<div class="container mx-auto v-mainnav_subpanel--desktop__inner">
										<ol class="child-menu grid grid-cols-3 auto-rows-auto v-divider--subitems" role="menu">
											<#list nav_item.getChildren() as nav_child>
												<#assign
													nav_child_attr_selected = ""
													nav_child_css_class = ""
												/>

												<#if nav_child.isSelected()>
													<#assign
														nav_child_attr_selected = "aria-selected='true'"
														nav_child_css_class = "selected"
													/>
												</#if>

												<li ${nav_child_attr_selected} class="${nav_child_css_class} flex flex-row items-center" id="layout_${nav_child.getLayoutId()}" role="presentation">
													<a class="relative flex flex-row items-center w-full h-full mx-3 text-app-blue--egyptian v-hover--shade transition-colors duration-150" aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">
														<span class="block text-base font-medium">${nav_child.getName()}</span>
														<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-end ml-auto shrink-0 w-4 h-4 mr-4" data-v-75934d3a=""><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
													</a>
												</li>
											</#list>
										</ol>
									</div>
								</div>
							<#else>
								<a class="group relative block text-lg font-medium" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
									<span class="block"><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span>
									<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="absolute top-0 left-0 mt-9 ml-[50%] w-2 h-2 text-app-green--caribbean -translate-x-1/2 transition duration-150 opacity-0"><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
								</a>
							</#if>
						</li>
					</#list>
				</ol>
			</nav>
		</div>
	</div>
</div>

<div class="service-navbar order-1 w-full px-10">
	<#include "${full_templates_path}/user_personal.ftl" />
</div>