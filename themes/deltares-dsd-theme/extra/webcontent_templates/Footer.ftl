<#assign title=.vars['reserved-article-title'].data />
<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign originalLocale=.locale>
<#setting locale=localeUtil.getDefault()>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>
<#assign locale=originalLocale>

<#if FooterLinksLeft.LinkToPage?? && FooterLinksLeft.LinkToPage.getSiblings()?has_content && validator.isNotNull(FooterLinksLeft.LinkToPage.getSiblings()?first.getData())>

    <ul>
        <#list FooterLinksLeft.LinkToPage.getSiblings() as cur_FooterLinksLeft>
            <li>
                <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, cur_FooterLinksLeft.getData()) />
                <a href="${cur_FooterLinksLeft.getFriendlyUrl()}">${linkLayout.getName(themeDisplay.getLocale())}</a>
            </li>
        </#list>
    </ul>
</#if>

<p>${Copyright.getData()}</p>

<div>
    <ul class="media-links">
        <#if FooterSocialMediaLinks.Linkedin?? &&  FooterSocialMediaLinks.Linkedin.getData()??>
            <li>
                <a href="${FooterSocialMediaLinks.Linkedin.getData()}" aria-label="go to our linkedin page" title="go to our linkedin page"><img src="${themeDisplay.getPathThemeImages()}/linkedin.png"></a>
            </li>
        </#if>
        <#if FooterSocialMediaLinks.Youtube?? &&  FooterSocialMediaLinks.Youtube.getData()??>
            <li>
                <a href="${FooterSocialMediaLinks.Youtube.getData()}" aria-label="go to our youtube page" title="go to our youtube page"><img src="${themeDisplay.getPathThemeImages()}/youtube.png"></a>
            </li>
        </#if>
        <#if FooterSocialMediaLinks.Facebook?? &&  FooterSocialMediaLinks.Facebook.getData()??>
            <li>
                <a href="${FooterSocialMediaLinks.Facebook.getData()}" aria-label="go to our facebook page" title="go to our facebook page"><img src="${themeDisplay.getPathThemeImages()}/facebook.png"></a>
            </li>
        </#if>
        <#if FooterSocialMediaLinks.Twitter?? &&  FooterSocialMediaLinks.Twitter.getData()??>
            <li>
                <a href="${FooterSocialMediaLinks.Twitter.getData()}" aria-label="go to our twitter page" title="go to our twitter page"><img src="${themeDisplay.getPathThemeImages()}/twitter.png"></a>
            </li>
        </#if>
    </ul>

    <#if FooterLinksRight.LinkToPage1?? && FooterLinksRight.LinkToPage1.getSiblings()?has_content && validator.isNotNull(FooterLinksRight.LinkToPage1.getSiblings()?first.getData())>
        <ul>
            <#list FooterLinksRight.LinkToPage1.getSiblings() as cur_FooterLinksRight>
                <li>
                    <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, cur_FooterLinksRight.getData()) />
                    <a href="${cur_FooterLinksRight.getFriendlyUrl()}">${linkLayout.getName(themeDisplay.getLocale())}</a>
                </li>
            </#list>
        </ul>
    </#if>
</div>