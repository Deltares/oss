<div class="cta_box text_centered">
    
    <#if CallToActionTitle?? &&  CallToActionTitle.getData()??>
        <div class="h1">${CallToActionTitle.getData()}</div>
    </#if>
    <#if CallToActionSubtitle?? &&  CallToActionSubtitle.getData()??>
        <div class="h2">${CallToActionSubtitle.getData()}</div>
    </#if>
    <#assign linkUrl = LinkToPage.getFriendlyUrl() >
    <#if LinkAnchor?? & LinkAnchor.getData()?has_content >
        <#assign linkUrl = linkUrl + "#" + LinkAnchor.getData() />
    </#if>
    
    <a href="${linkUrl}" class="regular-text">
        <img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;
        <#if LinkToPageText?? &&  LinkToPageText.getData()??>
            ${LinkToPageText.getData()}
        <#else>
            Get Started!
        </#if>
    </a>
</div>