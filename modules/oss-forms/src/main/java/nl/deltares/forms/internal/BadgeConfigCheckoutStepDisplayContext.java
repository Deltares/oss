package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.model.BadgeInfo;
import nl.deltares.model.RegistrationInfo;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BadgeConfigCheckoutStepDisplayContext {

    private final ThemeDisplay _themeDisplay;
    private final Event _event;

    private BadgeInfo _badgeInfo;

    public String getTitle() {
        return "badge-config";
    }

    public BadgeConfigCheckoutStepDisplayContext(HttpServletRequest request, ConfigurationProvider configurationProvider,
                                                 DsdParserUtils dsdParserUtils) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        User _user = _themeDisplay.getUser();

        DSDSiteConfiguration _configuration = configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, _themeDisplay.getScopeGroupId());
        _event = dsdParserUtils.getEvent(_themeDisplay.getSiteGroupId(), String.valueOf(_configuration.eventId()), _themeDisplay.getLocale());

        _badgeInfo = (BadgeInfo) request.getSession().getAttribute("badgeInfo");
        if (_badgeInfo == null) {
            _badgeInfo = new BadgeInfo();
        }
        List<RegistrationInfo> registrationInfos = (List<RegistrationInfo>) request.getSession().getAttribute("registrationInfos");
        if (registrationInfos != null) {
            RegistrationInfo registrationInfo = registrationInfos.get(0);
            _badgeInfo.setTitle(registrationInfo.getSalutation());
            _badgeInfo.setInitials(StringUtil.shorten(registrationInfo.getFirstName(), 1));
            _badgeInfo.setFirstName(registrationInfo.getFirstName());
            _badgeInfo.setLastName(registrationInfo.getLastName());
        } else {
            _badgeInfo.setTitle(_user.getJobTitle());
            _badgeInfo.setInitials(StringUtil.shorten(_user.getFirstName(), 1));
            _badgeInfo.setFirstName(_user.getFirstName());
            _badgeInfo.setLastName(_user.getLastName());
        }
        ExpandoBridge expandoBridge = _user.getExpandoBridge();
        if (expandoBridge.hasAttribute(BadgeInfo.badge_name_setting)) {
            _badgeInfo.setNameSetting((String) expandoBridge.getAttribute(BadgeInfo.badge_name_setting));
        }
        if (expandoBridge.hasAttribute(BadgeInfo.badge_title_setting)) {
            _badgeInfo.setTitleSetting((String) expandoBridge.getAttribute(BadgeInfo.badge_title_setting));
        }

    }

    public BadgeInfo storeBadgeSettings(HttpServletRequest httpServletRequest) throws PortalException {

        //Get local attributes
        for (String key : BadgeInfo.ATTRIBUTES) {
            String value = ParamUtil.getString(httpServletRequest, key);
            if (Validator.isNotNull(value) && !Validator.isBlank(value)) {
                _badgeInfo.setAttribute(key, value);
            }
        }

        User user = _themeDisplay.getUser();
        ExpandoBridge expandoBridge = user.getExpandoBridge();
        if (!expandoBridge.hasAttribute(BadgeInfo.badge_name_setting)) {
            expandoBridge.addAttribute(BadgeInfo.badge_name_setting);
        }
        expandoBridge.setAttribute(BadgeInfo.badge_name_setting, _badgeInfo.getNameSetting(), false);

        if (!expandoBridge.hasAttribute(BadgeInfo.badge_title_setting)) {
            expandoBridge.addAttribute(BadgeInfo.badge_title_setting);

        }
        expandoBridge.setAttribute(BadgeInfo.badge_title_setting, _badgeInfo.getTitleSetting(), false);

        return _badgeInfo;
    }

    public BadgeInfo getBadgeInfo() {
        return _badgeInfo;
    }

    public String getBannerUrl() {
        return _event == null ? "" : _event.getEmailBannerURL();
    }

    public String getEventTitle() {
        return _event == null ? "" : _event.getTitle();
    }

    public String getEventDate() {
        return _event == null ? "" : DateUtil.getDate(_event.getStartTime(), "yyyy", _themeDisplay.getLocale());
    }
}
