<#assign title=.vars['reserved-article-title'].data />
<#assign displaydate=.vars['reserved-article-display-date'].data>
<#assign originalLocale=.locale>
<#setting locale=localeUtil.getDefault()>
<#assign displaydate=displaydate?datetime("EEE, d MMM yyyy HH:mm:ss Z")>
<#assign locale=originalLocale>

<div class="footer-top">
    <div class="container mx-auto">
        <div class="row px-3">
            <div class="col-sm-12 col-md-6 col-lg-4">
                <#if FooterLinksLeft.LinkToPage?? && FooterLinksLeft.LinkToPage.getSiblings()?has_content && validator.isNotNull(FooterLinksLeft.LinkToPage.getSiblings()?first.getData())>
                    <ul class="link-list">
                        <#list FooterLinksLeft.LinkToPage.getSiblings() as cur_FooterLinksLeft>
                            <li>
                                <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, cur_FooterLinksLeft.getData()) />
                                <a href="${cur_FooterLinksLeft.getFriendlyUrl()}" class="underline-offset-2 font-semibold inline-flex items-center transition duration-200 items-start leading-none cursor-pointer text-white text-base tracking-wider">
                                    <div class="text-white">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 32 32" aria-hidden="false" role="img">
                                            <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                                        </svg>
                                    </div>
                                    <span>${linkLayout.getName(themeDisplay.getLocale())}</span>
                                </a>
                            </li>
                        </#list>
                    </ul>
                </#if>
            </div>

            <div class="col-sm-12 col-md-6 col-lg-4">
                <p>${Copyright.getData()}</p>
            </div>

            <div class="col-sm-12 col-md-6 col-lg-4">
                <h2 class="mb-3 text-base font-semibold text-white">Contact</h2>
                <ul class="link-list">
                    <li class="mb-1.5 leading-normal">
                        <a href="/contact" class="underline-offset-2 font-semibold inline-flex items-center transition duration-200 items-start leading-none cursor-pointer text-white text-base tracking-wider">
                            <div class="text-white">
                                <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 32 32" aria-hidden="false" role="img">
                                    <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                                </svg>
                            </div>
                            <span>Neem contact op</span>
                        </a>
                    </li>
                </ul>
                <p class="mb-4 text-sm leading-6">Blijf via social media op de hoogte van waar onze experts mee bezig zijn.</p>

                <ul class="media-links flex gap-2">
                    <#if FooterSocialMediaLinks.Facebook?? && FooterSocialMediaLinks.Facebook.getData()??>
                        <li>
                            <a class="uppercase font-medium text-base inline-flex transition duration-200 items-start rounded-full leading-none tracking-wider cursor-pointer border-current py-2 px-2 border-2 border-solid text-white" href="${FooterSocialMediaLinks.Facebook.getData()}" aria-label="go to our facebook page" title="go to our facebook page">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-5 h-5">
                                    <path fill="currentColor" d="M18.3,31V17.3h4.6l0.7-5.3h-5.3V8.6C18.3,7,18.8,6,21,6l2.8,0V1.2C23.3,1.1,21.6,1,19.7,1c-4.1,0-6.9,2.5-6.9,7V12H8.2v5.3h4.6V31L18.3,31z"></path>
                                </svg>
                            </a>
                        </li>
                    </#if>
                    <#if FooterSocialMediaLinks.Twitter?? && FooterSocialMediaLinks.Twitter.getData()??>
                        <li>
                            <a class="uppercase font-medium text-base inline-flex transition duration-200 items-start rounded-full leading-none tracking-wider cursor-pointer border-current py-2 px-2 border-2 border-solid text-white" href="${FooterSocialMediaLinks.Twitter.getData()}" aria-label="go to our twitter page" title="go to our twitter page">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-5 h-5">
                                    <path fill="currentColor" d="M28,9.9c0,0.3,0,0.5,0,0.8c0,8.1-6.2,17.5-17.5,17.5c-3.5,0-6.7-1-9.5-2.7c0.5,0,1,0.1,1.5,0.1c2.9,0,5.5-1,7.7-2.6c-2.7,0-5-1.8-5.7-4.3c0.4,0.1,0.7,0.1,1.2,0.1c0.5,0,1.1-0.1,1.6-0.2c-2.8-0.5-4.9-3-4.9-6c0,0,0,0,0-0.1C3,12.9,4,13.2,5,13.2c-1.6-1.1-2.7-3-2.7-5.1c0-1.1,0.3-2.2,0.8-3.1c3,3.7,7.6,6.2,12.7,6.4c-0.1-0.4-0.2-0.9-0.2-1.4c0-3.4,2.7-6.2,6.2-6.2c1.8,0,3.4,0.7,4.5,1.9c1.4-0.3,2.7-0.8,3.9-1.5c-0.5,1.5-1.5,2.6-2.7,3.4c1.3-0.2,2.4-0.5,3.5-1C30.2,7.9,29.2,9,28,9.9z"></path>
                                </svg>
                            </a>
                        </li>
                    </#if>
                    <#if FooterSocialMediaLinks.Youtube?? && FooterSocialMediaLinks.Youtube.getData()??>
                        <li>
                            <a class="uppercase font-medium text-base inline-flex transition duration-200 items-start rounded-full leading-none tracking-wider cursor-pointer border-current py-2 px-2 border-2 border-solid text-white" href="${FooterSocialMediaLinks.Youtube.getData()}" aria-label="go to our youtube page" title="go to our youtube page">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-5 h-5">
                                    <path fill="currentColor" fill-rule="evenodd" clip-rule="evenodd" d="M27.7,6.1C29,6.5,30,7.5,30.4,8.8C31,11.1,31,16,31,16s0,4.9-0.6,7.2c-0.3,1.3-1.4,2.3-2.6,2.6C25.4,26.5,16,26.5,16,26.5s-9.4,0-11.7-0.6C3,25.5,2,24.5,1.6,23.2C1,20.9,1,16,1,16s0-4.9,0.6-7.2C2,7.5,3,6.5,4.3,6.1C6.6,5.5,16,5.5,16,5.5S25.4,5.5,27.7,6.1z M20.8,16L13,20.5v-9L20.8,16z"></path>
                                </svg>
                            </a>
                        </li>
                    </#if>
                    <#if FooterSocialMediaLinks.Linkedin?? && FooterSocialMediaLinks.Linkedin.getData()??>
                        <li>
                            <a class="uppercase font-medium text-base inline-flex transition duration-200 items-start rounded-full leading-none tracking-wider cursor-pointer border-current py-2 px-2 border-2 border-solid text-white" href="${FooterSocialMediaLinks.Linkedin.getData()}" aria-label="go to our linkedin page" title="go to our linkedin page">
                                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-5 h-5">
                                    <path fill="currentColor" d="M3.2,11.1h5.3v18.4H3.2V11.1z M5.7,8.8L5.7,8.8c-1.9,0-3.2-1.4-3.2-3.2c0-1.8,1.2-3.1,3.2-3.1c1.9,0,3.1,1.4,3.2,3.2C8.8,7.5,7.6,8.8,5.7,8.8L5.7,8.8z M29.5,29.5h-5.9V20c0-2.4-0.9-4.2-3-4.2c-1.6,0-2.4,1.1-2.8,2.3c-0.1,0.4-0.1,0.9-0.1,1.5v9.9h-5.9c0,0,0.1-16.9,0-18.4h5.9V14c0.4-1.2,2.3-3.1,5.3-3.1c3.8,0,6.6,2.7,6.6,8.4L29.5,29.5L29.5,29.5z"></path>
                                </svg>
                            </a>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="footer-bottom px-4 text-sm">
    <div class="container mx-auto">
        <#if FooterLinksRight.LinkToPage1?? && FooterLinksRight.LinkToPage1.getSiblings()?has_content && validator.isNotNull(FooterLinksRight.LinkToPage1.getSiblings()?first.getData())>
            <ul>
                <#list FooterLinksRight.LinkToPage1.getSiblings() as cur_FooterLinksRight>
                    <li class="my-2 my-md-3 text-sm">
                        <#assign linkLayout=layoutUtils.getLinkToPageLayout(groupId, false, cur_FooterLinksRight.getData()) />
                        <a href="${cur_FooterLinksRight.getFriendlyUrl()}">${linkLayout.getName(themeDisplay.getLocale())}</a>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>
</div>