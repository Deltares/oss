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
import nl.deltares.model.BadgeInfo;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.servlet.http.HttpServletRequest;

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
            _badgeInfo.setTitle(_user.getJobTitle());
            _badgeInfo.setInitials(StringUtil.shorten(_user.getFirstName(), 1));
            _badgeInfo.setFirstName(_user.getFirstName());
            _badgeInfo.setLastName(_user.getLastName());
            ExpandoBridge expandoBridge = _user.getExpandoBridge();
            if (expandoBridge.hasAttribute(BadgeInfo.ATTRIBUTES.badge_name_setting.toString())) {
                _badgeInfo.setNameSetting((String) expandoBridge.getAttribute(BadgeInfo.ATTRIBUTES.badge_name_setting.toString()));
            }
            if (expandoBridge.hasAttribute(BadgeInfo.ATTRIBUTES.badge_title_setting.toString())) {
                _badgeInfo.setTitleSetting((String) expandoBridge.getAttribute(BadgeInfo.ATTRIBUTES.badge_title_setting.toString()));
            }

        }
    }

    public BadgeInfo storeBadgeSettings(HttpServletRequest httpServletRequest) throws PortalException {

        //Get local attributes
        for (BadgeInfo.ATTRIBUTES key : BadgeInfo.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(httpServletRequest, key.name());
            if (Validator.isNotNull(value) && !Validator.isBlank(value)) {
                _badgeInfo.setAttribute(key, value);
            }
        }

        User user = _themeDisplay.getUser();
        ExpandoBridge expandoBridge = user.getExpandoBridge();
        if (!expandoBridge.hasAttribute(BadgeInfo.ATTRIBUTES.badge_name_setting.toString())) {
            expandoBridge.addAttribute(BadgeInfo.ATTRIBUTES.badge_name_setting.toString());
        }
        expandoBridge.setAttribute(BadgeInfo.ATTRIBUTES.badge_name_setting.toString(), _badgeInfo.getNameSetting(), false);

        if (!expandoBridge.hasAttribute(BadgeInfo.ATTRIBUTES.badge_title_setting.toString())) {
            expandoBridge.addAttribute(BadgeInfo.ATTRIBUTES.badge_title_setting.toString());

        }
        expandoBridge.setAttribute(BadgeInfo.ATTRIBUTES.badge_title_setting.toString(), _badgeInfo.getTitleSetting(), false);

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
