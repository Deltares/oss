<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />

</head>
<script src="${javascript_folder}/calendar/calendar.js" ></script>
<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<#if is_site_admin?? && is_site_admin>
	<@liferay.control_menu />
</#if>

<div id="wrapper" class="antialiased bg-gradient bg-theme-tertiary">
	<#include "${full_templates_path}/header.ftl" />

	<section id="content">
		<div class="inner">
			<h1 class="hide-accessible" hidden="" >${the_title}</h1>

			<#if selectable>
				<@liferay_util["include"] page=content_include />
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				<@liferay_theme["wrap-portlet"] page="portlet.ftl">
					<@liferay_util["include"] page=content_include />
				</@>
			</#if>
		</div>
	</section>

	<#include "${full_templates_path}/footer.ftl" />
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

<!-- inject:js -->
<!-- endinject -->

</body>
<script>
	<#if is_shopping_cart?? && is_shopping_cart >
		var checkoutCartURL = '${checkout_cart_url}';
		var downloadCartURL = '${download_cart_url}';

		var shoppingCart = ShoppingCart.init({'languageKeys': {
				'add-to-cart': '${languageUtil.get(locale, "shopping.cart.add")}',
				'remove-from-cart': '${languageUtil.get(locale, "shopping.cart.remove")}'
			},
			'registrationFormId' : '${registration_form_id}'
		});
		shoppingCart.refreshCart();
	</#if>

</script>

</html>