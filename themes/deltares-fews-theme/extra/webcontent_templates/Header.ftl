<#assign imageTextContrast = "light-text">
<#if HeaderImage.ImageTextContrast?? && HeaderImage.ImageTextContrast.getData()??>
    <#assign imageTextContrast = HeaderImage.ImageTextContrast.getData()?replace('["', "")?replace('"]', "")/>
</#if>

<div class="page-header ${imageTextContrast}" role="banner" aria-role="banner">
    <#if HeaderImage.getData()?? && HeaderImage.getData() != "">
        <div class="page-header__background" style="background-image:url(${HeaderImage.getData()})">
            <img class="page-header__image"  src="${HeaderImage.getData()}" />
        </div>
    </#if>
    <div class="page-header__data guest">
        <h1 class="page-header__data__title">${Guests.GuestsTitle.getData()}</h1>
        <a  class="page-header__data__link regular-text" href="${Guests.GuestsLinkToPage.getFriendlyUrl()}">
            <img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;${Guests.GuestsLinkText.getData()}
        </a>
    </div>
    <div class="page-header__data user">
        <h1 class="page-header__data__title">${Users.UsersTitle.getData()}</h1>
        <a  class="page-header__data__link regular-text" href="${Users.UsersLinkToPage.getFriendlyUrl()}">
            <img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;${Users.UsersLinkText.getData()}
        </a>
    </div>
</div>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        if(Liferay.ThemeDisplay.isSignedIn()){
            $(".page-header").addClass("logged_in");
        } else {
            $(".page-header").addClass("logged_out");
        }
    });
</script>