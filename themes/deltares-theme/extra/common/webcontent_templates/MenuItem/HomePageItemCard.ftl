<div class="home-menu-container">
    <a href="${Link.getFriendlyUrl()}" class="home-menu-holder">
        <div class="home-menu-content" >
            <#if (Image.getData())?? && Image.getData() != "">
                <img alt="${Image.getAttribute("alt")}" data-fileentryid="${Image.getAttribute("fileEntryId")}" src="${Image.getData()}" />
            </#if>
            <ul>
                <li><h2>${Title.getData()}</h2></li>
                <li><h4>${Description.getData()}</h4></li>
            </ul>
        </div>
    </a>
</div>