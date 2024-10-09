<#if languages?? && languages?size != 0>
    <div class="dropdown">

            <div class="dropbtn">
                <a class="fs-small"  >
                    <span>${curr_language.getName()}</span>
                </a>
            </div>
            <div class="dropdown-content">
                <#list languages as language>
                    <a class="fs-small" href="${language.getUrl()}" >${language.getName()}</a>
                </#list>
            </div>
    </div>

</#if>