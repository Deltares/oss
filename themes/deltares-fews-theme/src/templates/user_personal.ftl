<div class="dropdown">
    <#if unread_announcements?? && unread_announcements gt 0 >
        <div class="dropbtn">
            <a class="c-announcements fs-smallest" href="${user_announcements_url}">
                <span class="c-announcements__counter"><@liferay.language key='announcements' /></span>
            </a>
        </div>
    </#if>
</div>
<div class="dropdown">
    <#if is_signed_in>
        <div class="dropbtn">
            <a class="fs-smallest"  >
                <#if user_avatar_url?? >
                    <div id="user-avatar" class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})" ></div>
                </#if>
                <span>${user_name}</span>
            </a>
        </div>
        <div class="dropdown-content">
            <#if user_account_url?? >
                <a class="fs-smallest" href="${user_account_url}" >Account</a>
            </#if>
            <#if user_mailing_url?? >
                <a class="fs-smallest" href="${user_mailing_url}" >Subscriptions</a>
            </#if>
            <hr>
            <a class="fs-smallest" href="${user_signout_url}"  >Logout</a>
        </div>
<#--        <#if user_avatar_url??>-->
<#--            <img id="user-image"  src="${user_avatar_url}" hidden>-->
<#--        </#if>-->

    <#elseif show_sign_in>
        <div class="dropbtn">
            <a class="fs-smallest" href="${sign_in_url}" >Login</a>
        </div>
        <div class="dropdown-content">
            <a class="fs-smallest" href="${sign_in_url}" >Register</a>
        </div>
    </#if>
</div>