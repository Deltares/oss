<header id="banner" role="banner">
    <div id="header" class="w-full">
        <div class="header-container hidden relative flex-col items-center w-full bg-white">
            <#if has_navigation && is_setup_complete>
                <#include "${full_templates_path}/navigation.ftl" />
            </#if>
        </div>
        <div class="mobile-container">
            <#include "${full_templates_path}/mobile_menu.ftl" />
        </div>
        <#if menu_sites_items?? && menu_sites_items?size != 0>
            <div class="sites-container">
                <#include "${full_templates_path}/sites_navigation.ftl" />
            </div>
        </#if>
    </div>
</header>