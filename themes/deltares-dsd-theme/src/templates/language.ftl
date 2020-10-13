<#if languages?size != 0>
    <div class="dropdown">

            <div class="dropbtn">
                <a class="fs-smallest"  >
                    <span>${curr_language.getName()}</span>
                </a>
            </div>
            <div class="dropdown-content">
                <#list languages as language>
                    <a class="fs-smallest" href="${language.getUrl()}" >${language.getName()}</a>
                </#list>
            </div>
    </div>

</#if>