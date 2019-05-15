<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>
	<meta content="initial-scale=1.0, width=device-width" name="viewport" />
	<@liferay_util["include"] page=top_head_include />
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-137616064-1"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());
		gtag('config', 'UA-137616064-1');
	</script>
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<@liferay.control_menu />

<div id="wrapper">
	<header id="banner" role="banner" class="container-fluid-1280">
		<div id="heading">
			<#if has_navigation && is_setup_complete>
				<#include "${full_templates_path}/navigation.ftl" />
			</#if>
			<h1 class="site-title">
				<a class="custom-logo" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<img alt="${logo_description}" src="${logo_img}" />
				</a>
			</h1>
		</div>
	</header>

	<section id="content">
		<h1 class="hide-accessible">${the_title}</h1>

		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>
	</section>

	<footer id="footer" role="contentinfo">
		<div class="container-fluid-1280">
			<#if footerWCContent?hasContent>
				${footerWCContent}
			</#if>
		</div>
	</footer>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

<!-- inject:js -->
<!-- endinject -->

<!-- inject:js -->
<!-- endinject -->

</body>

</html>