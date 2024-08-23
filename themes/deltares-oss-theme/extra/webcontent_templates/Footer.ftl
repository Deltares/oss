<#assign title=.vars['reserved-article-title'].data />
<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign originalLocale=.locale>
<#setting locale=localeUtil.getDefault()>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>
<#assign locale=originalLocale>

<#if FooterLinksLeftFieldSet.FooterLinksLeftFieldSetFieldSet.LinkToPage.getSiblings()?has_content>

    <ul>
        <#list FooterLinksLeftFieldSet.FooterLinksLeftFieldSetFieldSet.LinkToPage.getSiblings() as cur_FooterLinksLeft>
            <li>
                <#assign linkData = cur_FooterLinksLeft.getData()?eval >
                <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, linkData.layoutId?number) />
                <a href="${cur_FooterLinksLeft.getFriendlyUrl()}">${linkLayout.getName(themeDisplay.getLocale())}</a>
            </li>
        </#list>
    </ul>
</#if>

<#if Copyright?? && Copyright.getData()??>
    <p>${Copyright.getData()}</p>
</#if>

<div>
    <ul class="media-links">
        <#assign linkedIn = FooterSocialMediaLinksFieldSet.FooterSocialMediaLinksFieldSetFieldSet.Linkedin />
        <#if linkedIn?? &&  linkedIn.getData()??>
            <li>
                <a href="${linkedIn.getData()}" aria-label="go to our linkedin page" title="go to our linkedin page"><img src="${themeDisplay.getPathThemeImages()}/linkedin.png"></a>
            </li>
        </#if>
        <#assign youTube = FooterSocialMediaLinksFieldSet.FooterSocialMediaLinksFieldSetFieldSet.Youtube />
        <#if youTube?? &&  youTube.getData()??>
            <li>
                <a href="${youTube.getData()}" aria-label="go to our youtube page" title="go to our youtube page"><img src="${themeDisplay.getPathThemeImages()}/youtube.png"></a>
            </li>
        </#if>
        <#assign faceBook = FooterSocialMediaLinksFieldSet.FooterSocialMediaLinksFieldSetFieldSet.Facebook />
        <#if faceBook?? &&  faceBook.getData()??>
            <li>
                <a href="${faceBook.getData()}" aria-label="go to our facebook page" title="go to our facebook page"><img src="${themeDisplay.getPathThemeImages()}/facebook.png"></a>
            </li>
        </#if>
        <#assign twitter = FooterSocialMediaLinksFieldSet.FooterSocialMediaLinksFieldSetFieldSet.Twitter />
        <#if twitter?? &&  twitter.getData()??>
            <li>
                <a href="${twitter.getData()}" aria-label="go to our twitter page" title="go to our twitter page"><img src="${themeDisplay.getPathThemeImages()}/twitter.png"></a>
            </li>
        </#if>
    </ul>

    <#if FooterLinksRightFieldSet.FooterLinksRightFieldSetFieldSet.LinkToPage1.getSiblings()?has_content>
        <ul>
            <#list FooterLinksRightFieldSet.FooterLinksRightFieldSetFieldSet.LinkToPage1.getSiblings() as cur_FooterLinksRight>
                <li>
                    <#assign linkData = cur_FooterLinksRight.getData()?eval >
                    <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, linkData.layoutId?number) />
                    <a href="${cur_FooterLinksRight.getFriendlyUrl()}">${linkLayout.getName(themeDisplay.getLocale())}</a>
                </li>
            </#list>
        </ul>
    </#if>

</div>