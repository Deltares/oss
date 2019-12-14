<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name} </title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_util["include"] page=body_top_include />

<#if is_site_admin>
	<@liferay.control_menu />
</#if>

<div id="wrapper">
	<header class="container-fluid-1280" >
		<div class="pull-right">
			<#include "${full_templates_path}/user_personal.ftl" />
		</div>
	</header>
	<header class="container-fluid-1280 ${is_home}" id="banner" role="banner">
		<div class="row">
			<div class="navbar-header" id="heading">
				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<img alt="${logo_description}" height="90" src="${site_logo}" width="auto" />
				</a>

				<#if is_home == "nohome">
					<#if show_site_name>
						<span class="site-name" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
							${site_name}
						</span>
					</#if>
				</#if>

				<#if is_setup_complete>
					<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
						<span class="icon-bar"></span>

						<span class="icon-bar"></span>

						<span class="icon-bar"></span>
					</button>

				</#if>


			</div>

			<#if is_home == "nohome">
				<#include "${full_templates_path}/navigation.ftl" />
			</#if>
			<#if is_home == "home">
				<#include "${full_templates_path}/navigation_home.ftl" />
			</#if>

		</div>

		<#if is_home == "home">
			<div class="row">
				<div class="statement">
					<blockquote>
						<img class="statement-portrait" src="${images_folder}/Kwadijk-Jaap-175x200.jpg" />
						We believe in openness and transparency, as is evident from the free availability of our software and models. It is our firm conviction that sharing knowledge and innovative insights worldwide enables living in deltas.
						<span>- Jaap Kwadijk science director Deltares</span>
					</blockquote>
				</div>
			</div>
		</#if>

	</header>

		<section class="container-fluid-1280" id="content">
			<h1 class="sr-only">${the_title}</h1>

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
		<#if variable_name>
		<nav id="variable_name">
		</nav>
		</#if>

</div>



<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

<!-- inject:js -->
<!-- endinject -->

</body>

</html>