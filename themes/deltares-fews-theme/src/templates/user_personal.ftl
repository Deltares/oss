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
    </ol>
</nav>