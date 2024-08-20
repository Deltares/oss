<div class="expert-data">

    <#if (ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData())?? && ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData() != "">
        <div class="expert-data__image" style="background-image:url(${ExpertName.ExpertImage.getData()})">
            <img alt="${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getAttribute("alt")}" src="${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData()}" />
        </div>
    <#else>
        <div class="expert-data__image">
            ${stringUtil.shorten(ExpertNameFieldSet.ExpertName.getData(), 1)}
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