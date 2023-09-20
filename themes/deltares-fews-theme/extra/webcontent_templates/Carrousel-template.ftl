<div class="slide-container">
    <#if slidetitleFieldSet.getSiblings()?has_content>
        <#list slidetitleFieldSet.getSiblings() as cur_slidetitleFieldSet>

            <a href="${cur_slidetitleFieldSet.slidetitleFieldSetFieldSet.slidelink.getData()}" target="_blank" class="slide-holder">
                <div class="slide-content ${cur_slidetitleFieldSet.slidetitleFieldSetFieldSet.slidelogo.getData()}">

                    <img alt="${cur_slidetitleFieldSet.slidetitle.getData()}" src="${cur_slidetitleFieldSet.slidetitleFieldSetFieldSet.slideimage.getData()}" width="91"/>
                    <ul>
                        <li><h2>${cur_slidetitleFieldSet.slidetitle.getData()}</h2></li>
                        <li><h3>${cur_slidetitleFieldSet.slidetitleFieldSetFieldSet.slidesubtitle.getData()}</h3></li>
                    </ul>
                    <div class="description"><p>${cur_slidetitleFieldSet.slidetitleFieldSetFieldSet.slidedescription.getData()}</p></div>
                </div>
            </a>

        </#list>
    </#if>
</div>