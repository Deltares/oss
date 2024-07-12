<div class="fixed w-full z-30 p-2 bg-white">
    <a href="${site_default_url}" class="site-logo inline-block text-app-blue--egyptian align-top" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
        <img alt="${logo_description}" class="w-auto" src="${logo_img}" />
    </a>
</div>

<ol class="fixed flex flex-row justify-around w-full bottom-0 left-0 bg-white z-50 u-bottom-safe-area">
    <li class="pt-2 pb-1 btn-empty"></li>
    <li class="pt-2 pb-1">
        <button type="button" aria-expanded="false" class="mobile-btn mobile-menu-btn relative flex flex-col items-center text-app-blue--egyptian font-medium z-30">
            <span class="block w-8 h-8 flex flex-col items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="mobile-icon mobile-icon-menu shrink-0 w-5 h-5">
                    <g fill="currentColor">
                        <path d="M29.3,27.7H2.7c-0.4,0-0.9-0.2-1.2-0.5C1.2,26.9,1,26.4,1,26s0.2-0.9,0.5-1.2c0.3-0.3,0.7-0.5,1.2-0.5h26.7c0.4,0,0.9,0.2,1.2,0.5c0.3,0.3,0.5,0.7,0.5,1.2s-0.2,0.9-0.5,1.2C30.2,27.5,29.8,27.7,29.3,27.7z M29.3,17.7H2.7c-0.4,0-0.9-0.2-1.2-0.5S1,16.4,1,16c0-0.4,0.2-0.9,0.5-1.2s0.7-0.5,1.2-0.5h26.7c0.4,0,0.9,0.2,1.2,0.5S31,15.6,31,16c0,0.4-0.2,0.9-0.5,1.2S29.8,17.7,29.3,17.7z M29.3,7.7H2.7c-0.4,0-0.9-0.2-1.2-0.5S1,6.4,1,6s0.2-0.9,0.5-1.2s0.7-0.5,1.2-0.5h26.7c0.4,0,0.9,0.2,1.2,0.5S31,5.6,31,6s-0.2,0.9-0.5,1.2S29.8,7.7,29.3,7.7z"></path>
                    </g>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" aria-hidden="false" role="img" class="mobile-icon-close hidden shrink-0 w-8 h-8 text-app-green--caribbean">
                    <g>
                        <path fill="currentColor" d="M16,1L16,1c8.3,0,15,6.7,15,15l0,0c0,8.3-6.7,15-15,15l0,0C7.7,31,1,24.3,1,16l0,0C1,7.7,7.7,1,16,1z"></path>
                        <path fill="#ffffff" d="M21,11c0.2,0.2,0.3,0.4,0.3,0.6c0,0.2-0.1,0.5-0.3,0.6L17.2,16l3.7,3.7c0.2,0.2,0.3,0.4,0.3,0.6c0,0.2-0.1,0.5-0.3,0.6c-0.2,0.2-0.4,0.3-0.6,0.3c-0.2,0-0.5-0.1-0.6-0.3L16,17.2L12.3,21c-0.2,0.2-0.4,0.3-0.6,0.3c-0.2,0-0.5-0.1-0.6-0.3c-0.2-0.2-0.3-0.4-0.3-0.6c0-0.2,0.1-0.5,0.3-0.6l3.7-3.7L11,12.3c-0.2-0.2-0.3-0.4-0.3-0.6c0-0.2,0.1-0.5,0.3-0.6c0.2-0.2,0.4-0.3,0.6-0.3c0.2,0,0.5,0.1,0.6,0.3l3.7,3.7l3.7-3.7c0.2-0.2,0.4-0.3,0.6-0.3C20.6,10.8,20.8,10.8,21,11z"></path>
                    </g>
                </svg>
            </span>
            <span class="block lowercase text-sm leading-[14px]">Menu</span>
        </button>
        <#include "${full_templates_path}/mobile_navigation.ftl" />
    </li>
    <li class="pt-2 pb-1 btn-empty"></li>
</ol>