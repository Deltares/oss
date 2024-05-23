<div class="c-sessions page">
    <#if ExternalLink?? && ExternalLink.getData()?has_content >

        <h3 class="font-medium text-xl lg:text-2xl text-theme-secondary mb-1 lg:mb-2"><@liferay.language key='dsd.theme.session.goto.product' /></h3>
        <#list ExternalLink.getSiblings() as cur_ExternalLink>
            <div class="group flex">
                <div class="shrink-0 px-3 py-3 bg-theme-button group-hover:bg-theme-button--hover group-focus:bg-theme-button--hover transition duration-200 rounded-l">
                    <a href="${cur_ExternalLink.ExternalURL.getData()}">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewbox="0 0 32 32" aria-hidden="false" role="img"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
                    </a>
                </div>
                <div class="flex bg-white items-center font-semibold rounded-r  w-full">
                    <h4>
                        <a class="text-theme-button" href="${cur_ExternalLink.ExternalURL.getData()}">${cur_ExternalLink.getData()}</a>
                    </h4>
                </div>
            </div>
        </#list>
    </#if>
</div>