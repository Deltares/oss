<#assign journalArticleTitle = .vars['reserved-article-title'].data/>

<div class="section-content">
    <h1 class="flex font-medium text-3xl md:text-4xl lg:text-5xl text-theme-secondary mb-4 lg:!text-[48px] lg:!leading-[1.15] 2xl:!text-5xl 2xl:!leading-[1.15]">
        <div aria-hidden="true" class="mr-3 lg:mr-4 2xl:mr-5 pt-3.5 md:pt-4 text-theme-quaternary grow-0 shrink-0 lg:pt-5 2xl:pt-6">
            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="w-2 h-2 lg:w-2.5 lg:h-2.5 2xl:w-3 2xl:h-3">
                <circle fill="currentColor" cx="16" cy="16" r="15"></circle>
            </svg>
        </div>
        <span>${journalArticleTitle}</span>
    </h1>
    <div class="content prose prose--app">
        ${content.getData()}
    </div>
</div>