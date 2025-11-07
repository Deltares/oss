<div class="portlet-body">
    <a href="${Link.getFriendlyUrl()}" >
        <div class="flex-row justify-content-left align-items-center" >
            <#if (Image.getData())?? && Image.getData() != "">
                <img alt="${Image.getAttribute("alt")}" data-fileentryid="${Image.getAttribute("fileEntryId")}" src="${Image.getData()}" width="25px" height="25px"/>
                &nbsp;
            </#if>
            ${Title.getData()}
        </div>
    </a>
</div>