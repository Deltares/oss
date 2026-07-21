package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import jakarta.servlet.http.HttpServletRequest;
import nl.deltares.model.BadgeInfo;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.model.RegistrationsInfo;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.List;

public class BadgeConfigCheckoutStepDisplayContext {

    private final BadgeInfo _badgeInfo;

    public String getTitle() {
        return "badge-info";
    }

    public BadgeConfigCheckoutStepDisplayContext(HttpServletRequest request, ConfigurationProvider configurationProvider,
                                                 DsdParserUtils dsdParserUtils) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        if (context == null) {
            context = new RegistrationFormContext();
            request.getSession().setAttribute("registration-context", context);
        }
        BadgeInfo badgeInfo = context.getBadgeInfo();
        if (badgeInfo == null) {
            User user = themeDisplay.getUser();
            _badgeInfo = new BadgeInfo();
            _badgeInfo.setTitle(user.getJobTitle());
            _badgeInfo.setInitials(StringUtil.shorten(user.getFirstName(), 1));
            _badgeInfo.setFirstName(user.getFirstName());
            _badgeInfo.setLastName(user.getLastName());

            ExpandoBridge expandoBridge = user.getExpandoBridge();
            if (expandoBridge.hasAttribute(BadgeInfo.badge_name_setting)) {
                _badgeInfo.setNameSetting((String) expandoBridge.getAttribute(BadgeInfo.badge_name_setting));
            }
            if (expandoBridge.hasAttribute(BadgeInfo.badge_title_setting)) {
                _badgeInfo.setTitleSetting((String) expandoBridge.getAttribute(BadgeInfo.badge_title_setting));
            }

            DSDSiteConfiguration _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class,
                    themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId());
            Event event = dsdParserUtils.getEvent(themeDisplay.getSiteGroupId(), String.valueOf(_configuration.eventId()), themeDisplay.getLocale());
            _badgeInfo.setEventTitle(event.getTitle());
            _badgeInfo.setEventTime(DateUtil.getDate(event.getStartTime(), "yyyy", themeDisplay.getLocale()));
            _badgeInfo.setEventBannerURL(event.getEmailBannerURL());

            context.setBadgeInfo(_badgeInfo);
        } else {
            _badgeInfo = badgeInfo;
        }

        RegistrationsInfo registrationsInfo = context.getRegistrationsInfo();
        if (registrationsInfo != null){
            List<RegistrationInfo> allUserRegistrations = registrationsInfo.getAllUserRegistrations();
            allUserRegistrations.stream().findFirst().ifPresent(registrationInfo -> {
                _badgeInfo.setTitle(registrationInfo.getSalutation());
                _badgeInfo.setInitials(StringUtil.shorten(registrationInfo.getFirstName(), 1));
                _badgeInfo.setFirstName(registrationInfo.getFirstName());
                _badgeInfo.setLastName(registrationInfo.getLastName());
            });
        }

    }

    public void storeBadgeSettings(HttpServletRequest httpServletRequest) throws PortalException {

        //Get local attributes
        for (String key : BadgeInfo.ATTRIBUTES) {
            String value = ParamUtil.getString(httpServletRequest, key);
            if (Validator.isNotNull(value) && !Validator.isBlank(value)) {
                _badgeInfo.setAttribute(key, value);
            }
        }

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        User user = themeDisplay.getUser();
        ExpandoBridge expandoBridge = user.getExpandoBridge();
        if (!expandoBridge.hasAttribute(BadgeInfo.badge_name_setting)) {
            expandoBridge.addAttribute(BadgeInfo.badge_name_setting);
        }
        expandoBridge.setAttribute(BadgeInfo.badge_name_setting, _badgeInfo.getNameSetting(), false);

        if (!expandoBridge.hasAttribute(BadgeInfo.badge_title_setting)) {
            expandoBridge.addAttribute(BadgeInfo.badge_title_setting);

        }
        expandoBridge.setAttribute(BadgeInfo.badge_title_setting, _badgeInfo.getTitleSetting(), false);
    }

    public BadgeInfo getBadgeInfo() {
        return _badgeInfo;
    }

}
