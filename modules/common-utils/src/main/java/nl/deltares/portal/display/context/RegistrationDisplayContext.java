package nl.deltares.portal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OssConfigurationConstants;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.*;
import nl.deltares.portal.utils.Period;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static nl.deltares.portal.utils.LocalizationUtils.getLocalizedValue;

@SuppressWarnings("unused")
public class RegistrationDisplayContext {


    public RegistrationDisplayContext(Registration registration, int dayIndex, ThemeDisplay themeDisplay) {
        this._themeDisplay = themeDisplay;
        this._registration = registration;
        this._dayIndex = dayIndex;

        ConfigurationProvider configurationProvider = ConfigurationProviderUtil.getConfigurationProvider();
        if (configurationProvider != null) {
            try {
                _dsdSiteConfiguration = configurationProvider
                        .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
            } catch (ConfigurationException e) {
                LOG.error("Error retrieving DsdSiteConfiguration: ", e);
            }
        }

    }

    public double getPrice() {
        return _registration == null ? 0 : _registration.getPrice();
    }

    public String getCurrency() {
        return _registration == null ? "€" : _registration.getCurrency();
    }

    public String getSmallImageURL() {
        String url = "";
        Registration registration = get_registration();
        if (registration != null) {
            url = registration.getSmallImageURL(_themeDisplay);

            if ((url == null || url.isEmpty()) && registration instanceof DinnerRegistration) {
                url = ((DinnerRegistration) registration).getRestaurant().getSmallImageURL(_themeDisplay);
            }
        }
        return url;
    }

    public int getPresenterCount() {
        if (getSession() != null) {
            return getSession().getPresenters().size();
        }
        return 0;
    }

    public String getPresenterSmallImageURL(int i) {
        String url = "";
        if (getSession() != null) {
            url = getSession().getPresenters().get(i).getSmallImageURL(_themeDisplay);
        }
        return url;
    }

    public String getPresenterName(int i) {
        String name = "";
        SessionRegistration session = getSession();
        if (session != null) {
            Expert presenter = session.getPresenters().get(i);
            if (presenter != null) {
                name = presenter.getName();
                if (name == null) {
                    name = presenter.getTitle();
                }
            }
        }
        return name;
    }

    public Registration get_registration() {
        return _registration;
    }

    private SessionRegistration getSession() {
        SessionRegistration sessionRegistration = null;

        if (_registration instanceof SessionRegistration) {
            sessionRegistration = (SessionRegistration) _registration;
        }

        return sessionRegistration;
    }

    public String getStartDate() {
        final Registration registration = get_registration();
        if (registration != null) {
            return DateUtil.getDate(getStartDate(registration), "dd MMMM yyyy", _themeDisplay.getLocale(),
                    TimeZone.getTimeZone(registration.getTimeZoneId()));
        }
        return "";

    }

    public long getStartDateMillis() {
        final Registration registration = get_registration();
        if (registration != null) {
            return getStartDate(registration).getTime();
        }
        return 0;
    }

    private Date getStartDate(Registration registration) {
        if (_dayIndex > 0 && registration.isMultiDayEvent()) {
            final List<Period> startAndEndTimesPerDay = registration.getStartAndEndTimesPerDay();
            final Period period = startAndEndTimesPerDay.get(_dayIndex);
            return period.getStartDate();
        } else {
            return registration.getStartTime();
        }
    }

    private Date getEndDate(Registration registration) {
        if (registration.isMultiDayEvent()) {
            final List<Period> startAndEndTimesPerDay = registration.getStartAndEndTimesPerDay();
            if (startAndEndTimesPerDay.isEmpty()) return registration.getEndTime();
            final Period period = startAndEndTimesPerDay.get(_dayIndex);
            return period.getEndDate();
        } else {
            return registration.getEndTime();
        }
    }

    public String getEndDate() {
        final Registration registration = get_registration();
        if (registration != null) {
            return DateUtil.getDate(getEndDate(registration), "dd MMMM yyyy", _themeDisplay.getLocale(),
                    TimeZone.getTimeZone(registration.getTimeZoneId()));
        }
        return "";
    }

    public long getEndDateMillis() {
        final Registration registration = get_registration();
        if (registration != null) {
            return getEndDate(registration).getTime();
        }
        return 0;
    }

    public String getTitle() {
        final String title = _registration.getTitle();
        if (_registration.isShowMultipleDaysAsSingleDate()) return title;
        final String postFix = LanguageUtil.format(_themeDisplay.getLocale(),
                "program-list.day.count", new String[]{String.valueOf((getDayCount() + 1)), String.valueOf(getNumberOfDays())});
        return (title + " (" + postFix + ")");
    }

    public int getDayCount() {
        return _dayIndex;
    }

    public int getNumberOfDays() {
        if (!_registration.isMultiDayEvent()) return 0;
        return _registration.getStartAndEndTimesPerDay().size();
    }

    public boolean isPastEvent() {
        final Registration registration = get_registration();
        if (registration != null) {

            if (registration.isToBeDetermined()) return false;
            return getEndDate(_registration).getTime() < System.currentTimeMillis();
        }
        return true;
    }

    public boolean isOpen() {
        if (get_registration() != null) {
            return get_registration().isOpen();
        }
        return false;
    }

    public boolean canUserRegister() {
        return get_registration() != null && get_registration().canUserRegister(_themeDisplay.getUserId());
    }

    public String getStartTime() {
        final Registration registration = get_registration();
        if (registration != null) {
            return DateUtil.getDate(getStartDate(registration), "HH:mm", _themeDisplay.getLocale(),
                    TimeZone.getTimeZone(registration.getTimeZoneId()));
        }
        return "";
    }

    public String getEndTime() {
        final Registration registration = get_registration();
        if (registration != null) {
            return DateUtil.getDate(getEndDate(registration), "HH:mm", _themeDisplay.getLocale(),
                    TimeZone.getTimeZone(registration.getTimeZoneId()));
        }
        return "";
    }

    public String getSummary() {
        String summary = "";
        if (_registration == null) return summary;
        try {
            AssetEntry assetEntry = AssetEntryLocalServiceUtil
                    .getEntry(JournalArticle.class.getName(), _registration.getJournalArticle().getResourcePrimKey());
            summary = StringUtil.shorten(HtmlUtil.stripHtml(assetEntry.getSummary(_themeDisplay.getLocale())), 150);
        } catch (Exception e) {
            LOG.debug("Could not get the AssetEntry for article [" + _registration.getArticleId() + "]");
        }
        return summary;
    }

    public String getContactEmail() {
        if (_dsdSiteConfiguration != null) {
            return _dsdSiteConfiguration.replyToEmail();
        }
        return "mydeltares@deltares.nl";
    }

    public String getCourseConditionsUrl() {
        if (_dsdSiteConfiguration != null) {
            final String language = _themeDisplay.getLocale().getLanguage();
            return getLocalizedValue(_dsdSiteConfiguration.conditionsURL(), language);
        }
        return "";
    }

    @SuppressWarnings("unused")
    public String getUnregisterURL(HttpServletRequest httpServletRequest) {
        return getPortletRequest(httpServletRequest, OssConfigurationConstants.REGISTRATION_UNREGISTER, null, _themeDisplay.getURLCurrent());
    }

    @SuppressWarnings("unused")
    public String getUnregisterURL(PortletRequest portletRequest, long userId) {
        return getPortletRequest(portletRequest, OssConfigurationConstants.REGISTRATION_UNREGISTER, userId, getConfiguredRegistrationFormId(), OssConstants.SUBMIT_REGISTER_FORM_URL);
    }

    public String getUnregisterURL(PortletRequest portletRequest) {
        return getPortletRequest(portletRequest, OssConfigurationConstants.REGISTRATION_UNREGISTER, null, getConfiguredRegistrationFormId(), OssConstants.SUBMIT_REGISTER_FORM_URL);
    }

    @SuppressWarnings("unused")
    public String getUnregisterURL(PortletRequest portletRequest, long userId, String registrationFormName, String actionCommand) {
        return getPortletRequest(portletRequest, OssConfigurationConstants.REGISTRATION_UNREGISTER, userId, registrationFormName, actionCommand);
    }

    @SuppressWarnings("unused")
    public String getRegisterURL(PortletRequest portletRequest) {
        return getPortletRequest(portletRequest, "register", null, getConfiguredRegistrationFormId(), OssConstants.SUBMIT_REGISTER_FORM_URL);
    }

    public String getViewURL(DsdArticle article) {
        return _themeDisplay.getSiteGroup().getDisplayURL(_themeDisplay) + "/-/" + article.getJournalArticle().getUrlTitle();
    }

    private String getPortletRequest(HttpServletRequest httpServletRequest, String action, Long userId, String redirect) {

        if (_dsdSiteConfiguration != null) {
            long groupId = _themeDisplay.getScopeGroupId();

            try {
                Layout registrationPage = LayoutLocalServiceUtil
                        .fetchLayoutByFriendlyURL(groupId, false, _dsdSiteConfiguration.registrationURL());

                if (registrationPage != null) {
                    PortletURL portletURL = PortletURLFactoryUtil
                            .create(httpServletRequest,
                                    _themeDisplay.getThemeSetting("registration-form-id"),
                                    registrationPage.getPlid(),
                                    action.equals(OssConfigurationConstants.REGISTRATION_UNREGISTER) ? PortletRequest.ACTION_PHASE : PortletRequest.RENDER_PHASE);
                    portletURL.setWindowState(LiferayWindowState.NORMAL);
                    portletURL.setPortletMode(LiferayPortletMode.VIEW);
                    portletURL.getRenderParameters().setValue("javax.portlet.action", OssConstants.SUBMIT_REGISTER_FORM_URL);
                    portletURL.getRenderParameters().setValue("articleId", get_registration().getArticleId());
                    portletURL.getRenderParameters().setValue("action", action);
                    if (userId != null) portletURL.getRenderParameters().setValue("userId", userId.toString());
                    portletURL.getRenderParameters().setValue("redirect", redirect);
                    return portletURL.toString();
                }
            } catch (Exception e) {
                LOG.error("Error creating portlet url", e);
            }
        }
        return "";
    }

    public String getConfiguredRegistrationFormId(){
        return _themeDisplay.getThemeSetting("registration-form-id");
    }

    public boolean hasPresentations() {
        final SessionRegistration session = getSession();
        if (session == null) return false;
        return !session.getPresentations().isEmpty();
    }

    public List<Presentation> getPresentations() {
        final SessionRegistration session = getSession();
        if (session == null) return Collections.emptyList();
        return session.getPresentations();
    }


    public String getPortletRequest(PortletRequest portletRequest, String action, Long userId, String formName, String actionCommand) {

        if (_dsdSiteConfiguration != null) {
            long groupId = _themeDisplay.getScopeGroupId();

            try {
                Layout registrationPage = LayoutLocalServiceUtil
                        .fetchLayoutByFriendlyURL(groupId, false, _dsdSiteConfiguration.registrationURL());

                if (registrationPage != null) {
                    PortletURL portletURL = PortletURLFactoryUtil
                            .create(portletRequest,
                                    formName,
                                    registrationPage.getPlid(),
                                    action.equals("unregister") ? PortletRequest.ACTION_PHASE : PortletRequest.RENDER_PHASE);
                    portletURL.setWindowState(LiferayWindowState.NORMAL);
                    portletURL.setPortletMode(LiferayPortletMode.VIEW);
                    portletURL.setParameter("javax.portlet.action", actionCommand);
                    portletURL.setParameter("articleId", get_registration().getArticleId());
                    portletURL.setParameter("action", action);
                    if (userId != null) portletURL.setParameter("userId", userId.toString());
                    return portletURL.toString();
                }
            } catch (Exception e) {
                LOG.error("Error creating portlet url", e);
            }
        }
        return "";
    }

    private DSDSiteConfiguration _dsdSiteConfiguration = null;
    private final ThemeDisplay _themeDisplay;
    private final Registration _registration;
    private final int _dayIndex;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationDisplayContext.class);
}
