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
                    <div class="minium-topbar__end c-cart__cart c-cart__checkout__cart">
                        <div class="minium-topbar__cart-wrapper">
                            <@deltares_commerce_ui["mini-cart"] />
                        </div>
                    </div>
                </li>

            </#if>
            <#if user_signout_url?? >
                <li class="px-3 border-white border-left">
                    <a class="block text-sm font-sm text-white leading-none" href="${user_signout_url}">Logout</a>
                </li>
            </#if>
        <#elseif show_sign_in>
            <li class="px-3">
                <a class="block text-sm font-sm text-white leading-none" href="${sign_in_url}">Login</a>
            </li>
        </#if>
    </ol>
    <#if languages?? && languages?size != 0>
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