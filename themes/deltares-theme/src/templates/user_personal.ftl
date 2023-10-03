<nav class="flex flex-row justify-end">
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
    <#if languages?size != 0>
        <ol class="flex flex-row items-center language-selector">
            <#list languages as language>
                <li class="px-1 text-sm leading-none text-white border-right border-white">
                    <#if language.getId() == themeDisplay.getLocale().getLanguage()>
                        <span class="font-medium underline">
                            <span aria-hidden="true">${language.getName()}</span>
                            <span class="sr-only">${language.getName()}</span>
                        </span>
                    <#else>
                        <a href="${language.getUrl()}" class="text-white">
                            <span aria-hidden="true">${language.getName()}</span>
                            <span class="sr-only">${language.getName()}</span>
                        </a>
                    </#if>
                </li>
            </#list>
        </ol>
    </#if>
</nav>