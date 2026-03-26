<nav class="flex flex-row">

    <ol class="flex flex-row  w-full">
<#--        Create ol element to push other menu items to the right -->
        <#if menu_sites_items?? && menu_sites_items?size != 0>
            <li class="px-3 border-white">
                <button type="button" aria-expanded="false" class="sites-btn sites-menu-btn relative flex flex-row items-center">
                    <span class="block w-8 flex flex-row items-center justify-center">
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="sites-icon sites-icon-menu shrink-0 w-5 h-5">
                            <g fill="#ffffff">
                                <use xlink:href="${imagesUrl}/clay/icons.svg#grid"></use>
                            </g>
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="sites-icon-close hidden shrink-0 w-5 h-5 text-app-green--caribbean">
                            <g>
                                <path fill="currentColor" d="M16,1L16,1c8.3,0,15,6.7,15,15l0,0c0,8.3-6.7,15-15,15l0,0C7.7,31,1,24.3,1,16l0,0C1,7.7,7.7,1,16,1z"></path>
                                <path fill="#ffffff" d="M21,11c0.2,0.2,0.3,0.4,0.3,0.6c0,0.2-0.1,0.5-0.3,0.6L17.2,16l3.7,3.7c0.2,0.2,0.3,0.4,0.3,0.6c0,0.2-0.1,0.5-0.3,0.6c-0.2,0.2-0.4,0.3-0.6,0.3c-0.2,0-0.5-0.1-0.6-0.3L16,17.2L12.3,21c-0.2,0.2-0.4,0.3-0.6,0.3c-0.2,0-0.5-0.1-0.6-0.3c-0.2-0.2-0.3-0.4-0.3-0.6c0-0.2,0.1-0.5,0.3-0.6l3.7-3.7L11,12.3c-0.2-0.2-0.3-0.4-0.3-0.6c0-0.2,0.1-0.5,0.3-0.6c0.2-0.2,0.4-0.3,0.6-0.3c0.2,0,0.5,0.1,0.6,0.3l3.7,3.7l3.7-3.7c0.2-0.2,0.4-0.3,0.6-0.3C20.6,10.8,20.8,10.8,21,11z"></path>
                            </g>
                        </svg>
                    </span>
                    <span class="block text-sm leading-[14px] text-white">Deltares sites</span>
                </button>
            </li>
        </#if>

    </ol>
    <#if menu_extensions_items?? && menu_extensions_items?size != 0>
    <ol class="flex flex-row">
        <#list menu_extensions_items as menu_extension_item>
            <#assign  settings = unicodePropertiesBuilder.fastLoad(menu_extension_item.getTypeSettings()).build() />

            <#if settings?? && settings.get("url")?? >
                <#assign
                    url= settings.get("url")
                    defaultLanguageId = settings.get("defaultLanguageId")
                    name = settings.get("name_" + defaultLanguageId)
                    newTab = settings.get("useNewTab")
                    target = (newTab?string == "true")?then("_blank", "_self")
                />
                <li class="px-3">
                    <a class="block text-sm font-sm text-white leading-none" href="${url}" target="${target}">
                        ${name}
                    </a>
                </li>
            </#if>
        </#list>
        <div class="border-right" />
    </ol>
    </#if>

    <#if unread_announcements?? && unread_announcements gt 0 >
    <ol class="flex flex-row">
        <li class="px-3 account-link">
            <a class="c-announcements block text-sm font-medium text-white" href="${user_announcements_url}">
                <span class="c-announcements__counter"><@liferay.language key='announcements' /></span>
            </a>
        </li>
    </ol>
    </#if>
    <ol class="flex flex-row">
        <#if is_signed_in>
            <#if user_account_url??>
                <li class="px-3 account-link">
                    <a class="block text-sm font-sm font-bold text-white leading-none relative" href="${user_account_url}">
                        <#if user_avatar_url??>
                            <div id="user-avatar" class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})"></div>
                        </#if>
                        <span>${user_name}</span>
                    </a>
                </li>
            <#else>
                <li class="px-3 account-link">
                    <a class="block text-sm font-sm font-bold text-white leading-none relative">
                        <#if user_avatar_url??>
                            <div id="user-avatar" class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})"></div>
                        </#if>
                        <span>${user_name}</span>
                    </a>
                </li>
            </#if>
            <#if is_signed_in && is_shopping_cart>
                <li class="px-3 border-white border-left border-right">
                    <a href="#" class="c-cart__cart c-cart__checkout__cart text-white flex text-sm align-top relative">
                        <svg width="1.3em" height="1.3em" viewBox="0 0 16 16" class="bi bi-cart shrink-0 w-3 h-3 mr-2" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"></path>
                        </svg>
                        <span class="c-cart__item__counter">0</span>
                    </a>
                </li>
            </#if>
            <li class="px-3 border-white border-left">
                <a class="block text-sm font-sm text-white leading-none" href="${user_signout_url}">Logout</a>
            </li>
        <#elseif show_sign_in>
            <li class="px-3">
                <a class="block text-sm font-sm text-white leading-none" href="${sign_in_url}">Login</a>
            </li>
        </#if>
    </ol>
<#--    <#if languages?? && languages?size != 0>-->
<#--        <ol class="flex flex-row items-center language-selector">-->
<#--            <#list languages as language>-->
<#--                <li class="px-1 text-sm leading-none text-white border-right border-white">-->
<#--                    <#if language.getId() == themeDisplay.getLocale().getLanguage()>-->
<#--                        <span class="font-medium underline">-->
<#--                            <span aria-hidden="true">${language.getName()}</span>-->
<#--                            <span class="sr-only">${language.getName()}</span>-->
<#--                        </span>-->
<#--                    <#else>-->
<#--                        <a href="${language.getUrl()}" class="text-white">-->
<#--                            <span aria-hidden="true">${language.getName()}</span>-->
<#--                            <span class="sr-only">${language.getName()}</span>-->
<#--                        </a>-->
<#--                    </#if>-->
<#--                </li>-->
<#--            </#list>-->
<#--        </ol>-->
<#--    </#if>-->
</nav>