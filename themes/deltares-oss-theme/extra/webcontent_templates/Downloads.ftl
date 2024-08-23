<div class="c-downloads" id="${randomNamespace}_download-list">
	<h5 class="c-downloads-title">${.vars['reserved-article-title'].data}</h5>
	<#if FileDisplayTitle.getSiblings()?has_content>
		<ul class="c-downloads-list clearList">
			<#list FileDisplayTitle.getSiblings() as cur_FileDisplayTitle>
				<li class="c-downloads-list__item">
					<a class="c-downloads-list__item__link regular-text" href="${FileDisplayTitle.UploadFile.getData()}">
					<span class="link_underline">${languageUtil.format(locale, "${cur_FileDisplayTitle.getData()}", false)}</span> &gt;</a>
				</li>
			</#list>
		</ul>
	</#if>
</div>