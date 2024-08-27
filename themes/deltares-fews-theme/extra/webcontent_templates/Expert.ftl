<div class="expert-data">
    <div class="grid grid-cols-12 gap-x-8">
        <div class="flex col-span-12 md:col-span-12">
            <#if (ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData()?? && ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData() != "")>
                <div class="expert-data__image block object-cover w-24 h-24 rounded-full overflow-hidden mr-4 shrink-0" style="background-image:url(${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData()})">
                    <img  src="${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertImage.getData()}" />
                </div>
            <#else>
                <div class="expert-data__image block object-cover w-24 h-24 rounded-full overflow-hidden mr-4 shrink-0 placeholder">
                    <img src="${themeDisplay.getPathThemeImages()}/person-placeholder.svg" class="w-full aspect-[160/141] object-cover overflow-hidden" />
                </div>
            </#if>

            <div class="expert-data__content text-app-blue--egyptian shrink flex flex-col justify-center">
                <div class="block mb-2">
                    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="inline-flex items-center text-theme-quaternary w-2 h-2 mb-1 mr-1"><circle fill="currentColor" cx="16" cy="16" r="15"></circle></svg>
                    <span class="text-base font-semibold">${ExpertName.getData()}</span>
                </div>
                <#if ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertJobTitle.getData()?has_content>
                    <span class="text-sm block font-regular">${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertJobTitle.getData()}</span>
                </#if>
                <#if ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertCompany.getData()?has_content>
                    <span class="text-sm block font-regular">${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertCompany.getData()}</span>
                </#if>
                <span class="text-sm block font-regular">
                    <a href="mailto:${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertEmailAddress.getData()}" >${ExpertNameFieldSet.ExpertNameFieldSetFieldSet.ExpertEmailAddress.getData()}</a>
                </span>
            </div>
        </div>
    </div>
</div>