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
                <a class="fs-smallest" href="${user_account_url}" >My Account</a>
            </#if>
            <#if user_mailing_url?? >
                <a class="fs-smallest" href="${user_mailing_url}" >My Mailings</a>
            </#if>
            <hr>
            <a class="fs-smallest" href="${user_signout_url}"  >Logout</a>
        </div>
<#--        <#if user_avatar_url??>-->
<#--            <img id="user-image"  src="${user_avatar_url}" hidden>-->
<#--        </#if>-->

    <#else>
        <div class="dropbtn">
            <a class="fs-smallest" href="${sign_in_url}" >Login</a>
        </div>
        <div class="dropdown-content">
            <a class="fs-smallest" href="${sign_in_url}" >Register</a>
        </div>
    </#if>
</div>