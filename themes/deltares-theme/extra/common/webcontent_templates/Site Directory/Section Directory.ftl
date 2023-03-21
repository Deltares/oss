<div class="slide-container">
    <#if siteName.getSiblings()?has_content>
        <#list siteName.getSiblings() as cur_site>
            <a href="${cur_site.path.getData()}" target="_blank" class="slide-holder">
                <div class="slide-content">
                    <#if cur_site.icon.getData()?? && cur_site.icon.getData() != "">
                        <img alt="${cur_site.icon.getAttribute("alt")}" data-fileentryid="${cur_site.icon.getAttribute("fileEntryId")}" src="${cur_site.icon.getData()}" width="91" />
                    </#if>
                    <ul>
                        <li><h2>${cur_site.getData()}</h2></li>
                        <li><h3>${cur_site.subTitle.getData()}</h3></li>
                    </ul>
                    <div class="description">
                        <p>${cur_site.description.getData()}</p>
                    </div>
                </div>
            </a>
        </#list>
    </#if>
</div>