<header id="banner" role="banner" class="container-fluid-1280">
    <div id="heading">
        <#if has_navigation && is_setup_complete>
            <#include "${full_templates_path}/navigation.ftl" />
        </#if>
        
        <h1 class="site-title">
            <a class="custom-logo" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
                <#if logo_img??>
                    <img alt="${logo_description}" src="${logo_img}" />
                <#else>
                </#if>
            </a>
        </h1>
    </div>
</header>




