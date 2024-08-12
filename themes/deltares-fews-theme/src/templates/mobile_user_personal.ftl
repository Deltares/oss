<ol class="mobile-servicenav grid grid-cols-2 auto-rows-auto">
    <#if is_signed_in>
        <#if user_account_url??>
            <li class="flex flex-row items-center even:pl-10 odd:border-r odd:border-solid odd:border-white account-link">
                <a class="block text-sm font-medium text-white" href="${user_account_url}">
                    <#if user_avatar_url??>
                        <div id="user-avatar" class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})"></div>
                    </#if>
                    <span>${user_name}</span>
                </a>
            </li>
        <#else>
            <li class="flex flex-row items-center even:pl-10 odd:border-r odd:border-solid odd:border-white account-link">
                <a class="block text-sm font-medium text-white">
                    <#if user_avatar_url??>
                        <div id="user-avatar" class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})"></div>
                    </#if>
                    <span>${user_name}</span>
                </a>
            </li>
        </#if>
        <li class="flex flex-row items-center even:pl-10 odd:border-r odd:border-solid odd:border-white">
            <a class="block text-sm font-medium text-white leading-none" href="${user_signout_url}">Logout</a>
        </li>
    <#elseif show_sign_in>
        <li class="flex flex-row items-center even:pl-10 odd:border-r odd:border-solid odd:border-white">
            <a class="block text-sm font-medium text-white leading-none" href="${sign_in_url}">Login</a>
        </li>
    </#if>
</ol>