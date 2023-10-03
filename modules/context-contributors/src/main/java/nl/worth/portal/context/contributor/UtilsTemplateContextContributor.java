package nl.worth.portal.context.contributor;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.*;
import nl.deltares.portal.utils.impl.LanguageImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
        service = TemplateContextContributor.class
)
public class UtilsTemplateContextContributor implements TemplateContextContributor {

    private static final Log LOG = LogFactoryUtil.getLog(UtilsTemplateContextContributor.class);

    @Reference
    private JournalArticleLocalService journalArticleLocalService;

    @Reference
    private JournalContent journalContent;

    @Reference
    private LayoutUtils layoutUtils;

    @Reference
    private DDLUtils ddlUtils;

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private URLUtils urlUtils;

    @Reference
    private GeoIpUtils geoIpUtils;

    @Reference
    private SanctionCheckUtils sanctionCheckUtils;

    @Override
    public void prepare(Map<String, Object> contextObjects, HttpServletRequest request) {
        contextObjects.put("journalArticleLocalService", journalArticleLocalService);
        contextObjects.put("journalContentUtil", journalContent);
        contextObjects.put("layoutUtils", layoutUtils);
        contextObjects.put("ddlUtils", ddlUtils);

        /*
          Section below is used in user_personal.ftl
         */
        boolean isAdmin = false;
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            isAdmin = (themeDisplay.isSignedIn() && GroupPermissionUtil.contains(
                    themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
                    ActionKeys.VIEW_SITE_ADMINISTRATION));

        } catch (PortalException e) {
            LOG.warn(e);
        }
        contextObjects.put("is_site_admin", isAdmin);
        contextObjects.put("user_signout_url", themeDisplay.getURLSignOut());
        if (keycloakUtils.isActive()) {
            contextObjects.put("user_mailing_url", "/subscriptions");
            contextObjects.put("user_account_url", "/account");
            contextObjects.put("user_announcements_url", "/announcements");
        }

        //set languages
        setLanguages(contextObjects, themeDisplay);

        if (themeDisplay.isSignedIn()) {
            User user = themeDisplay.getUser();
            long portraitId = user.getPortraitId();
            if (portraitId > 0) {
                try {
                    contextObjects.put("user_avatar_url", user.getPortraitURL(themeDisplay));
                } catch (PortalException e) {
                    //
                }
            }
            final Object isSanctioned_from_postlogin = request.getSession().getAttribute("LIFERAY_SHARED_isSanctioned");
            boolean isSanctioned = isSanctioned_from_postlogin != null && (boolean) isSanctioned_from_postlogin;
            String sanctionCountry = (String) request.getSession().getAttribute("LIFERAY_SHARED_sanctionCountry");
            if (geoIpUtils != null && isSanctioned_from_postlogin == null) {
                final Map<String, String> clientIpInfo = geoIpUtils.getClientIpInfo(request.getRemoteAddr());
                final String countryIso2Code = geoIpUtils.getCountryIso2Code(clientIpInfo);
                isSanctioned = sanctionCheckUtils.isSanctionedByCountyCode(themeDisplay.getCompanyId(), countryIso2Code);
                sanctionCountry = geoIpUtils.getCountryName(clientIpInfo);
                request.getSession().setAttribute("LIFERAY_SHARED_isSanctioned", false);
                request.getSession().setAttribute("LIFERAY_SHARED_sanctionCountry", sanctionCountry == null ? "unknown" : sanctionCountry);
            }
            contextObjects.put("is_sanctioned", isSanctioned);
            contextObjects.put("sanctionCountry", sanctionCountry == null ? "" : sanctionCountry);

            contextObjects.put("checkout_cart_url", urlUtils.getShoppingCartURL(themeDisplay));
            contextObjects.put("download_cart_url", urlUtils.getDownloadCartURL(themeDisplay));

            Integer unreadAnnouncements = (Integer) request.getSession().getAttribute("LIFERAY_SHARED_userAnnouncements");
            if (unreadAnnouncements == null || unreadAnnouncements > 0) {
                try {
                    int count = getUnreadUserAnnouncementCount(user);
                    request.getSession().setAttribute("LIFERAY_SHARED_userAnnouncements", count);
                    contextObjects.put("unread_announcements", count);
                } catch (Exception e) {
                    LOG.warn(String.format("Error retrieving user announcements for user %s: %s", user.getEmailAddress(), e.getMessage()));
                }
            } else {
                contextObjects.put("unread_announcements", unreadAnnouncements);
            }
        }

    }

    private int getUnreadUserAnnouncementCount(User user) {

        final DynamicQuery dynamicQuery = AnnouncementsFlagLocalServiceUtil.dynamicQuery();
        dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", user.getCompanyId()));
        dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", user.getUserId()));
        final List<AnnouncementsFlag> flags = AnnouncementsFlagLocalServiceUtil.dynamicQuery(dynamicQuery);
        int count = 0;
        for (AnnouncementsFlag flag : flags) {
            final int value = flag.getValue();
            if (value == 1) count++; //unread
            else if (value == 2) count--; //read
        }
        return count;
    }

    private void setLanguages(Map<String, Object> contextObjects, ThemeDisplay themeDisplay) {
        ArrayList<LanguageImpl> languages = new ArrayList<>();
        String urlCurrent = themeDisplay.getURLCurrent();
        final String noLanguagePath;
        final String currLanguage;
        if (urlCurrent.startsWith("/en") || urlCurrent.startsWith("/nl")) {
            noLanguagePath = urlCurrent.substring(3);
            currLanguage = urlCurrent.substring(1, 3);
        } else {
            noLanguagePath = urlCurrent;
            currLanguage = themeDisplay.getLocale().getLanguage();
        }
        languages.add(new LanguageImpl("nl", "NL", themeDisplay.getURLPortal() + "/nl" + noLanguagePath, themeDisplay));
        languages.add(new LanguageImpl("en", "EN", themeDisplay.getURLPortal() + "/en" + noLanguagePath, themeDisplay));
        contextObjects.put("languages", languages);

        contextObjects.put("curr_language", new LanguageImpl(currLanguage, currLanguage.toUpperCase(), themeDisplay.getURLPortal() + "/" + currLanguage + noLanguagePath, themeDisplay));


    }

}