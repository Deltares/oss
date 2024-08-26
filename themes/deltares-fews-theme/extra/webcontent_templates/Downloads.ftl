<div class="c-downloads" id="${randomNamespace}_download-list">
	<h5 class="c-downloads-title font-medium text-xl lg:text-2xl text-theme-secondary mb-3 lg:mb-3">${.vars['reserved-article-title'].data}</h5>
	<#if FileDisplayTitle.getSiblings()?has_content>
		<ul class="c-downloads-list clearList">
			<#list FileDisplayTitle.getSiblings() as cur_FileDisplayTitle>
				<li class="c-downloads-list__item mb-1.5">
					<a class="c-downloads-list__item__link relative inline-flex flex-row items-center text-app-blue--egyptian hover:underline transition-colors duration-150 cursor-pointer font-medium" href="${FileDisplayTitle.UploadFile.getData()}">
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="content-start text-theme-quaternary shrink-0 w-3 h-3"><path fill="currentColor" d="M1,17.9h22.8L13.3,28.4L16,31l15-15L16,1l-2.6,2.6l10.4,10.5H1V17.9z"></path></svg>
						<span class="link_underline">${languageUtil.format(locale, "${cur_FileDisplayTitle.getData()}", false)}</span>
					</a>
				</li>
			</#list>
		</ul>
	</#if>
</div>