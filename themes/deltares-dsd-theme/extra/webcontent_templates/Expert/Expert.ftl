<div class="expert-data">

    <#if expertName.expertImage.getData()?? && expertName.expertImage.getData() != "">
        <div class="expert-data__image" style="background-image:url(${expertName.expertImage.getData()})">
            <img alt="${expertName.expertImage.getAttribute("alt")}" data-fileentryid="${expertName.expertImage.getAttribute("fileEntryId")}" src="${expertName.expertImage.getData()}" />
        </div>
    <#else>
        <div class="expert-data__image">
            ${stringUtil.shorten(expertName.getData(), 1)}
        </div>
    </#if>

    <div class="expert-data__content">
        <p class="bold">${expertName.getData()}</p>
        <#if expertName.expertJobTitle.getData()?has_content>
            <p>${expertName.expertJobTitle.getData()}</p>
        </#if>
        <#if expertName.expertCompany.getData()?has_content>
            <p>${expertName.expertCompany.getData()}</p>
        </#if>
        <p>
            <a href="mailto:${expertName.expertEmailAddress.getData()}" >${expertName.expertEmailAddress.getData()}</a>
        </p>
    </div>
</div>