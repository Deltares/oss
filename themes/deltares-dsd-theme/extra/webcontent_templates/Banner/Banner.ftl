<#assign imageTextContrast = "light-text">
<#if HeaderImage.ImageTextContrast?? && HeaderImage.ImageTextContrast.getData()??>
    <#assign imageTextContrast = HeaderImage.ImageTextContrast.getData()?replace('["', "")?replace('"]', "")/>
</#if>

<div class="page-header ${imageTextContrast}" role="banner" aria-role="banner">
    <#if HeaderImage.getData()?? && HeaderImage.getData() != "">
        <div class="page-header__background" style="background-image:url(${HeaderImage.getData()})">
            <img class="page-header__image" data-fileentryid="${HeaderImage.getAttribute("fileEntryId")}" alt="${HeaderImage.getAttribute("alt")}" src="${HeaderImage.getData()}" />
        </div>
    </#if>
</div>