<#if LinkTitle.getSiblings()?has_content>
    <ul class="link-list clearList">
        <#list LinkTitle.getSiblings() as cur_LinkTitle>
            <li class="link-list__item">
                <h4 class="group-hover:underline group-focus:underline decoration-1 font-medium text-lg lg:text-xl text-theme-secondary">
                    <a href="${cur_LinkTitle.LinkURL.getData()}" target="_blank">${cur_LinkTitle.getData()}</a>
                </h4>
                <span class="block">${cur_LinkTitle.LinkExplanatoryText.getData()}</span>
            </li>
        </#list>
    </ul>
</#if>