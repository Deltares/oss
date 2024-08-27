<#assign linkValue = "" />
<#if bannerInternalLink.getFriendlyUrl() != ''>
    <#assign linkValue = bannerInternalLink.getFriendlyUrl() />
<#elseif bannerExternalLink.getData() != '' >
    <#assign linkValue = bannerExternalLink.getData() />
</#if>

<#assign bannerSize = "" />
<#if getterUtil.getBoolean(bannerTypeHomepage.getData())>
    <#assign bannerSize = "banner--large" />
</#if>

<div class="relative z-0 h-full bg-app-blue--persian component-banner ${bannerSize}" role="banner" aria-role="banner">
    <div class="grid grid-cols-12 h-full">
        <div class="col-start-1 col-end-13 md:col-end-9 lg:col-end-8 xl:col-end-7 2xl:col-end-6 row-start-2 row-end-3 lg:row-start-1 flex flex-col justify-end lg:justify-center z-20 content-container">
            <h1 class="z-10 font-medium text-5xl md:text-6xl 2xl:text-7xl text-theme-secondary--home">${bannerTitle.getData()}</h1>
            <#if bannerText.getData()?? && bannerText.getData() != "">
                <div class="relative lg:mr-16 text-lg lg:text-xl text-theme-secondary--home font-medium pl-9 dash-left z-10">
                    <p>${bannerText.getData()}</p>
                </div>
            </#if>
            <#if (linkValue)?has_content>
                <a href=${htmlUtil.escapeHREF(linkValue)} class="order-4 ml-9 underline underline-offset-2 hover:underline focus:underline font-medium text-sm inline-flex items-center transition duration-200 items-start leading-none tracking-wider cursor-pointer uppercase text-theme-secondary--home">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-3 h-3 mt-[1px] ml-3 text-theme-secondary--home">
                        <path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path>
                    </svg>
                    <#if bannerLinkText.getData()?? && bannerLinkText.getData() != "">
                        <span>${bannerLinkText.getData()}</span>
                    </#if>
                </a>
            </#if>
        </div>
        <div class="relative col-start-1 col-end-13 row-start-1 row-end-3 pointer-events-none z-10 image-container">
            <div class="w-full h-full">
                <img width="390" height="632" class="block w-full h-full object-cover"  src="${bannerImage.getData()}" />
                <#if bannerImageCopyright.getData()?? && bannerImageCopyright.getData() != "">
                    <figcaption class="absolute right-0 py-2 picture-copyright-gradient opacity-80 font-normal leading-tight text-sm text-white z-10 top-0 bottom-auto md:top-auto md:bottom-0">${bannerImageCopyright.getData()}</figcaption>
                </#if>
            </div>
            <span class="absolute bottom-0 left-0 block w-full h-full overflow-hidden z-0 sphere-banner-gradient">
                <span class="sphere-banner-gradient--inner--lightblue"></span>
            </span>
        </div>
    </div>
</div>

<script>
    var portletNamespace = "<@portlet.namespace />";
    var portletNamespaceId = document.querySelector("div[id$='" + portletNamespace + "']");
    var bannerComponentImage = document.querySelector('.component-banner .image-container picture');
    var bannerComponentFigcaption = document.querySelector('.component-banner .image-container figcaption');

    setTimeout(function () {
        portletNamespaceId.classList.add('component-banner-container');

        if (!!bannerComponentFigcaption) {
            bannerComponentImage.append(bannerComponentFigcaption);
        }
    }, 1);
</script>