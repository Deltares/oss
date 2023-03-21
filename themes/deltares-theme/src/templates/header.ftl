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
        <div class="menu-overlay fixed bottom-0 left-0 w-full bg-app-blue--egyptian opacity-0 transition-all duration-200 v-navi-overlay"></div>
    </div>
</header>