
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
    <div class="page-header__data">
        <h1 class="page-header__data__title">${Title.getData()}</h1>
        <a  class="page-header__data__link regular-text"
            href="${LinkText.LinkURL.getData()}"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;${LinkText.getData()}</a>
    </div>
</div>