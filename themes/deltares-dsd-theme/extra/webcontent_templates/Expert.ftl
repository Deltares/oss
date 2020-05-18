<div class="expert-data">

    Hello this if from the Expert.ftl
    <#if ExpertName.ExpertImage.getData()?? && ExpertName.ExpertImage.getData() != "">
        <div class="expert-data__image" style="background-image:url(${ExpertName.ExpertImage.getData()})">
            <img alt="${ExpertName.ExpertImage.getAttribute("alt")}" data-fileentryid="${ExpertName.ExpertImage.getAttribute("fileEntryId")}" src="${ExpertName.ExpertImage.getData()}" />
        </div>
    <#else>
        <div class="expert-data__image">
            ${stringUtil.shorten(ExpertName.getData(), 1)}
        </div>
    </#if>
    
    <div class="expert-data__content">
        <p class="bold">${ExpertName.getData()}</p>
        <#if ExpertName.ExpertJobTitle.getData()?has_content>
            <p>${ExpertName.ExpertJobTitle.getData()}</p>
        </#if>
        <#if ExpertName.ExpertCompany.getData()?has_content>
        <p>${ExpertName.ExpertCompany.getData()}</p>
        </#if>
        <p>
            <a href="mailto:${ExpertName.ExpertEmailAddress.getData()}" >${ExpertName.ExpertEmailAddress.getData()}</a>
        </p>
    </div>
</div>