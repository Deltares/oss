<div class="absolute w-full bottom-[58px] left-0 overflow-hidden mobile-navpanel">
	<#assign preferences = freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
	<nav class="${nav_css_class} w-full h-full bg-app-blue--egyptian z-20 overflow-auto mobile-mainnav" id="mobile-navigation" role="navigation">
		<h1 class="hide-accessible" hidden=""><@liferay.language key="navigation" /></h1>
		<ol aria-label="<@liferay.language key="site-pages" />" class="mobile-mainnav-inner py-3" role="menubar">
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

				<li ${nav_item_attr_selected} class="${nav_item_css_class} relative border-b border-solid border-white mobile-mainnav__item" id="layout_${nav_item.getLayoutId()}" style="transition-delay: ${nav_item?index * 100}ms;" role="presentation">
					<#if nav_item.hasChildren()>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="absolute top-0 left-0 -ml-5 w-1.5 h-1.5 text-app-green--caribbean--light" data-v-2b33845a=""><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
						<button type="button" aria-expanded="false" class="relative flex flex-row w-full items-center justify-between text-xl font-medium text-white" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} role="menuitem">
							<span class="block"><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span>
							<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="ml-auto shrink-0 w-4 h-4 transition-transform duration-150 rotate-0" data-v-2b33845a=""><path fill="currentColor" d="M1.5,8.1c0.4-0.4,0.8-0.5,1.3-0.5c0.5,0,1,0.2,1.3,0.5L16,19.9L27.8,8.1C28,8,28.2,7.8,28.4,7.7c0.2-0.1,0.5-0.1,0.7-0.2c0.2,0,0.5,0,0.7,0.1c0.2,0.1,0.4,0.2,0.6,0.4c0.2,0.2,0.3,0.4,0.4,0.6C31,9,31,9.2,31,9.4c0,0.2-0.1,0.5-0.2,0.7c-0.1,0.2-0.2,0.4-0.4,0.6L17.3,23.9c-0.4,0.4-0.8,0.5-1.3,0.5c-0.5,0-1-0.2-1.3-0.5L1.5,10.8C1.2,10.4,1,10,1,9.5C1,9,1.2,8.5,1.5,8.1z"></path></svg>
						</button>
						<div class="overflow-hidden -mx-5 v-mainnav_subpanel--mobile">
							<div class="pb-1 border-t border-solid border-white">
								<ol role="menu">
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
											<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start shrink-0 w-1.5 h-1.5 -ml-4 mr-3 text-app-green--caribbean--light" data-v-2b33845a=""><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
											<a class="flex flex-row items-center w-full text-white" aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">
												<span class="block text-base font-medium">${nav_child.getName()}</span>
												<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-end ml-auto shrink-0 w-4 h-4" data-v-2b33845a=""><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
											</a>
										</li>
									</#list>
								</ol>
							</div>
						</div>
					<#else>
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="absolute top-0 left-0 -ml-5 w-1.5 h-1.5 text-app-green--caribbean--light" data-v-2b33845a=""><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
						<a class="block text-xl font-medium text-white" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
							${nav_item.getName()}
						</a>
					</#if>
				</li>
			</#list> 
		</ol>
		<#include "${full_templates_path}/mobile_user_personal.ftl" />
	</nav>
</div>