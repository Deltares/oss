<div class="main-navbar relative flex flex-row order-2 w-full px-10">
	<div class="site-logo pb-4">
		<a href="https://www.deltares.nl" class="inline-block text-app-blue--egyptian" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
			<svg xmlns="http://www.w3.org/2000/svg" width="773" height="149" viewBox="0 0 773 149" aria-hidden="true" aria-label="<@liferay.language key='dsd.theme.logo.alt' />" class="w-44 h-auto">
				<title><@liferay.language key='dsd.theme.logo.alt' /></title>
				<path fill="currentColor" d="M124.1,71.2C124.1,21.4,95.4,1,48.9,1c-14.6,0-28.7,0-36,0.4C5.6,1.9,1,2.8,1,9.9v135.9c13.8,1.4,27.6,2.2,41.5,2.2C89.9,148,124.1,120.9,124.1,71.2z M93.6,73.4c0,32-20.1,51.1-48.3,51.1c-5.2,0-10.4-0.3-15.5-1.1V24.8c0,0,8.2-0.7,17.3-0.7C79.9,24.1,93.6,41.4,93.6,73.4z M139.6,93.4c0,36.4,18.2,54.6,55.6,54.6c11.8,0.1,23.6-1.9,34.7-5.8v-20.9c-10,3.4-20.4,5.2-31,5.3c-20.1,0-31-10.2-31-29.8H224c9.1,0,12.8-2.2,12.8-8.4v-4c0-26.6-14.6-47.5-45.6-47.5C158.3,37,139.6,61.4,139.6,93.4z M190.3,57.4c11.9,0,18.2,8,18.2,21.3h-40.1C170.2,65.4,178.4,57.4,190.3,57.4z M286,1.4h-15.5c-9.1,0-12.8,2.2-12.8,8.4v103c0,24.9,7.8,35.1,28.7,35.1c6.3-0.1,12.6-1,18.7-2.9v-20.2c-3.1,0.8-6.3,1.2-9.6,1.3c-5.7,0-9.6-2.7-9.6-14.2V1.4z M350.8,1.4h-15.1c-9.1,0-12.8,2.2-12.8,8.4v103c0,24.9,8.2,35.1,31.9,35.1c7.5,0,15.1-0.9,22.3-2.9V124c-4.3,1.1-8.8,1.7-13.2,1.8c-8.2,0-13.2-4-13.2-17.3v-48h13.7c9.1,0,12.8-2.2,12.8-8.4V39.2h-26.5V1.4z M390.9,97.8c0,36.4,23.7,50.2,52.9,50.2c18.2,0,36.9-3.6,47.9-8V42.7C479,39,465.8,37.1,452.5,37C414.2,37,390.9,61.4,390.9,97.8z M420.1,95.1c0-25.8,13.2-36.9,29.6-36.9c4.8,0,9.5,0.5,14.1,1.6V124c0,0-5.9,2.7-17.8,2.7C427.9,126.7,420.1,114.7,420.1,95.1z M520.5,145.8h28.3V80.9c0-15.1,4.1-20.4,15-20.4h1.4c8.2,0,12.3-1.3,12.3-8.4V39.2h-17.3c-31,0-39.7,18.7-39.7,41.7V145.8z M583.9,93.4c0,36.4,18.2,54.6,55.6,54.6c11.8,0.1,23.6-1.9,34.7-5.8v-20.9c-10,3.4-20.5,5.2-31,5.3c-20.1,0-31-10.2-31-29.8h56.1c9.1,0,12.8-2.2,12.8-8.4v-4c0-26.6-14.6-47.5-45.6-47.5C602.6,37,583.9,61.4,583.9,93.4z M634.5,57.4c11.9,0,18.2,8,18.2,21.3h-40.1C614.4,65.4,622.6,57.4,634.5,57.4z M698.3,71.2c0,35.5,44.5,25.8,44.5,44.4c0,7.1-7.1,11.1-18,11.1c-7.9,0-15.8-1.1-23.5-3.1l-0.5,22.2c7.4,1.5,15,2.2,22.6,2.2c29.2,0,48.6-13.8,48.6-35.1c0-34.6-44.9-27.5-44.9-44.4c0-7.1,6.4-10.7,16.4-10.7c3.6,0,7.3,0.2,10,0.2c6.4,0,11.4-1.1,11.4-11.8v-7.5c-6.6-1.1-13.3-1.7-20.1-1.8C713.8,37,698.3,52.5,698.3,71.2z"></path>
			</svg>
			<span class="sr-only"><@liferay.language key='dsd.theme.logo.link.description' /></span>
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