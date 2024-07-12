<div class="container mx-auto px-3">
    <div class="cta cta_box text_centered">
        <#if CallToActionTitle?? && CallToActionTitle.getData()??>
            <div class="h1">${CallToActionTitle.getData()}</div>
        </#if>
        <#if CallToActionSubtitle?? && CallToActionSubtitle.getData()??>
            <div class="h2">${CallToActionSubtitle.getData()}</div>
        </#if>
        <#assign linkUrl = LinkToPage.getFriendlyUrl()>
        <#if LinkAnchor?? & LinkAnchor.getData()?has_content>
            <#assign linkUrl = linkUrl + "#" + LinkAnchor.getData() />
        </#if>

        <ul class="grid sm:grid-cols-2">
            <li></li>
            <li>
                <a href="${linkUrl}" class="group flex text-theme-button">
                    <div class="shrink-0 w-14 flex items-center justify-center bg-theme-button group-hover:bg-theme-button--hover group-focus:bg-theme-button--hover transition duration-200 rounded-l">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 32 32" aria-hidden="false" role="img">
                            <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                        </svg>
                    </div>
                    <div class="w-full flex flex-col bg-white font-semibold rounded-r">
                        <#if LinkToPageText?? && LinkToPageText.getData()??>
                            <h3 class="text-lg leading-5 order-2">${LinkToPageText.getData()}</h3>
                        <#else>
                            <h3 class="text-lg leading-5 order-2">Get Started!</h3>
                        </#if>
                    </div>
                </a>
            </li>
        </ul>
    </div>
</div>