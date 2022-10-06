package nl.worth.portal.context.contributor;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.URLUtils;
import nl.deltares.portal.utils.DDLUtils;
import nl.deltares.portal.utils.LayoutUtils;
import nl.deltares.portal.utils.impl.LanguageImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
//            contextObjects.put("user_mailing_url", appendWithReferrer(keycloakUtils.getUserMailingPath(), themeDisplay));
//            contextObjects.put("user_account_url", appendWithReferrer(keycloakUtils.getAccountPath(), themeDisplay));
            contextObjects.put("user_mailing_url", "/subscriptions");
            contextObjects.put("user_account_url", "/account");
        }
        User user = themeDisplay.getUser();
        long portraitId = user.getPortraitId();
        if (portraitId > 0) {
            try {
                contextObjects.put("user_avatar_url", user.getPortraitURL(themeDisplay));
            } catch (PortalException e) {
                //
            }
        }

        final Object isSanctioned = request.getSession().getAttribute("LIFERAY_SHARED_isSanctioned");
        if (isSanctioned != null) {
            contextObjects.put("is_sanctioned", isSanctioned);
            contextObjects.put("sanctionCountry", request.getSession().getAttribute("LIFERAY_SHARED_sanctionCountry"));
        }

        //set languages
        setLanguages(contextObjects, themeDisplay);

        contextObjects.put("checkout_cart_url", urlUtils.getShoppingCartURL(themeDisplay));
        contextObjects.put("download_cart_url", urlUtils.getDownloadCartURL(themeDisplay));
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

    private String appendWithReferrer(String accountPath, ThemeDisplay themeDisplay) {

        int startPath = accountPath.indexOf('?');
        if (startPath > 0) {
            if (startPath == accountPath.length() - 1) {
                accountPath += getReferrerPath(themeDisplay);
                return accountPath;
            }
        }
        accountPath += '?';
        accountPath += getReferrerPath(themeDisplay);
        return accountPath;
    }

    private String getReferrerPath(ThemeDisplay themeDisplay) {
        String path = "referrer_uri=" + themeDisplay.getCDNBaseURL() + themeDisplay.getURLCurrent();
        String descriptiveName;
        try {
            descriptiveName = themeDisplay.getScopeGroup().getDescriptiveName();
        } catch (PortalException e) {
            descriptiveName = "OSS Community Portal";
        }
        path += "&referrer=" + descriptiveName;

        return HttpUtil.encodeParameters(path);
    }

}