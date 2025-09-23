<#assign dsdParserUtils = serviceLocator.findService("nl.deltares.portal.utils.DsdParserUtils") />
<#assign userLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.UserLocalService")>
<#assign title=.vars['reserved-article-title'].data />
<#assign urltitle=.vars['reserved-article-url-title'].data />
<#assign articleId = .vars['reserved-article-id'].getData() />
<#assign displayContext = dsdParserUtils.getDisplayContextInstance(articleId, themeDisplay) />
<#assign registration = displayContext.getRegistration() />
<#assign timeZoneId = registration.getTimeZoneId() />
<#assign showButtons = displayContext.canUserRegister() && themeDisplay.isSignedIn() />
<#assign cancellationExceeded = registration.isCancellationPeriodExceeded() />
<#assign redirectUrl= themeDisplay.getSiteGroup().getDisplayURL(themeDisplay) + "/program" />
<#if registration.isMultiDayEvent() >
    <#assign title = displayContext.getTitle() />
</#if>

<div class="row no-gutters">

    <div class="col-2">
        <img class="img-fluid" src="${displayContext.getSmallImageURL()}" alt=""/>
    </div>
    <div class="col-10 px-3">
        <h4>
            <a href="-/${urltitle}?redirect=${redirectUrl}" >
                <strong>${title}</strong>
            </a>
        </h4>
        <div>
            <#assign count = displayContext.getPresenterCount()/>
            <#if count gt 0>
                <#list 0..(count-1) as i >
                    <div class="items-line">
                        <#assign imageUrl = displayContext.getPresenterSmallImageURL(i) />
                        <#if imageUrl?has_content >
                            <img width="32" class="expert-thumbnail" src="${imageUrl}" alt=""/>
                        </#if>
                        <#assign name = displayContext.getPresenterName(i) />
                        <#if name?has_content>
                            <span class="expert-name px-2">${name}</span> |
                        </#if>
                    </div>
                </#list>
            </#if>
            <span class="c-sessions__item__time-date-place__time">
                ${displayContext.getStartTime()} - ${displayContext.getEndTime()} (${timeZoneId})
            </span>|
            <#if displayContext.getPrice() gt 0 >
                ${displayContext.getCurrency()} ${displayContext.getPrice()}
            <#else>
                ${languageUtil.get(locale, "dsd.theme.session.free")}
            </#if>
            <#if showButtons >
                <#assign userId = themeDisplay.getUserId() />
                <#assign registrationDatas = dsdSessionUtils.getRegistrationDataByAuthorAndResourceId(themeDisplay.getUser(), registration.getResourceId()) />
                <span class="d-block" style="float:right">
                    <table >
                        <#list registrationDatas as registrationData>
                        <#if  userLocalService.fetchUser(registrationData.getUserId())?? >
                            <#assign registeredUser =  userLocalService.fetchUser(registrationData.getUserId()) />
                        <tr><td>
                            <a href="${displayContext.getUnregisterURL(renderRequest, registeredUser.getUserId(),
                            displayContext.getConfiguredRegistrationFormId(),  "/submit/unregister/form") }" class="btn-lg btn-primary" role="button" aria-pressed="true" style="color:#fff">
                                ${languageUtil.get(locale, "registrationform.unregister")}
                                &nbsp;
                                ${registeredUser.getFullName()}
                            </a>
                        </td></tr>
                        </#if>
                        </#list>
                    </table>
                </span>
            </#if>
        </div>
        <#if cancellationExceeded >
            <div>
                <#assign courseConditionsUrl = displayContext.getCourseConditionsUrl() />
                <small><i>${languageUtil.get(locale, "registrationform.cancelExpired")?replace("{0}", courseConditionsUrl)}</i></small>
            </div>
        </#if>
    </div>
</div>