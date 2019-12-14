<div class="dropdown" >
    <#if is_signed_in>
    <div class="dropbtn">
        <a class="fs-smallest" href="${user_signout_url}">
            <#if has_user_avatar_url>
                <div class="aspect-ratio-bg-cover user-icon" style="background-image:url(${user_avatar_url})"></div>
            </#if>
            <span>${user_name} -- Logout</span>
        </a>
        </di>
        <div class="dropdown-content">
            <a class="fs-smallest" href="${user_account_url}">My Account</a>
            <a class="fs-smallest" href="${user_mailing_url}">My Mailings</a>
        </div>
        <#else>
            <div class="dropbtn">
                <a class="fs-smallest" data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}">Login</a>
            </div>
            <div class="dropdown-content">
                <a class="fs-smallest" data-redirect="${is_login_redirect_required?string}" href="${sign_in_url}">Register</a>
            </div>
        </#if>
    </div>