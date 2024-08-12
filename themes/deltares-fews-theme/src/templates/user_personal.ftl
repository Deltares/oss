<nav class="flex flex-row justify-end">
    <#if unread_announcements?? && unread_announcements gt 0>
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
            <#if user_mailing_url??>
                <li class="px-3 account-link">
                    <a class="block text-sm font-sm text-white leading-none" href="${user_mailing_url}">Subscriptions</a>
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
        <li class="px-4">
            <a href="${site_default_url}/search" class="flex flex-row items-center text-sm leading-none text-white hover:underline focus:underline">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="shrink-0 w-3 h-3 mt-[2px] mr-2">
                    <path fill="currentColor" d="M12.7,4.3c-2.2,0-4.3,0.9-5.9,2.4c-1.6,1.6-2.4,3.7-2.4,5.9c0,2.2,0.9,4.3,2.4,5.9c1.6,1.6,3.7,2.4,5.9,2.4
                    c2.2,0,4.3-0.9,5.9-2.4c1.6-1.6,2.4-3.7,2.4-5.9c0-2.2-0.9-4.3-2.4-5.9S14.9,4.3,12.7,4.3z M1,12.7C1,10.8,1.4,9,2.3,7.4
                    s2.1-3.1,3.6-4.1c1.5-1.1,3.2-1.8,5.1-2.1c1.8-0.3,3.7-0.1,5.4,0.5c1.8,0.6,3.3,1.6,4.6,2.9c1.3,1.3,2.3,2.9,2.8,4.7
                    c0.6,1.8,0.7,3.6,0.4,5.5c-0.3,1.8-1.1,3.5-2.2,5l8.5,8.5c0.3,0.3,0.5,0.7,0.5,1.2c0,0.4-0.2,0.9-0.5,1.2c-0.3,0.3-0.7,0.5-1.2,0.5
                    c-0.4,0-0.9-0.2-1.2-0.5L19.7,22c-1.7,1.3-3.8,2.1-6,2.3c-2.2,0.2-4.3-0.2-6.3-1.2c-1.9-1-3.6-2.5-4.7-4.3C1.6,17,1,14.8,1,12.7z">
                    </path>
                </svg>
                <span class="block lowercase text-sm text-white leading-none">Search</span>
            </a>
        </li>
    </ol>
</nav>