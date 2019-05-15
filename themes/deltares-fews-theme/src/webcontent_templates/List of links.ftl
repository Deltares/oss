<#if LinkTitle.getSiblings()?has_content>
    <ul class="link-list clearList">
        <#list LinkTitle.getSiblings() as cur_LinkTitle>
            <li class="link-list__item"><span> ${cur_LinkTitle.getData()}</span>
                <a class="c-card__link regular-text" href="${cur_LinkTitle.LinkURL.getData()}" target="_blank"><img src="${themeDisplay.getPathThemeImages()}/chevron_right.svg">&nbsp;Read more</a>
            </li>
        </#list>
    </ul>
</#if>