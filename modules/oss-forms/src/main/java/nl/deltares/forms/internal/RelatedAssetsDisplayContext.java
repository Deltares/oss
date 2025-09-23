package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelatedAssetsDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(RelatedAssetsDisplayContext.class);
    private final DsdJournalArticleUtils _dsdJournalArticleUtils;
    private final DsdParserUtils _dsdParserUtils;
    private final List<Registration> _relatedRegistrations = new ArrayList<>();
    private final ThemeDisplay _themeDisplay;
    private final String ids;

    public RelatedAssetsDisplayContext(HttpServletRequest request, DsdJournalArticleUtils dsdJournalArticleUtils, DsdParserUtils dsdParserUtils) throws Exception {

        _dsdJournalArticleUtils = dsdJournalArticleUtils;
        _dsdParserUtils = dsdParserUtils;
        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        ids = ParamUtil.getString(request, "ids");
        loadRelatedAssets(ids);
    }

    public JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                   String ddmTemplateKey, JournalArticle journalArticle, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    journalArticle, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    journalArticle.getArticleId(), ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    public List<Registration> getRelatedArticles() {
        return _relatedRegistrations;
    }

    public ThemeDisplay getThemeDisplay() {
        return _themeDisplay;
    }

    private void loadRelatedAssets(String ids) throws Exception {
        if (ids.isEmpty()) return;
        String[] registrationIds = ids.split(",", -1);

        List<JournalArticle> relatedArticles = _dsdJournalArticleUtils.getRelatedArticles(_themeDisplay.getScopeGroupId(), registrationIds);
        for (JournalArticle relatedArticle : relatedArticles) {
            if (relatedArticle == null) {
                continue;
            }
            if (Arrays.stream(registrationIds).anyMatch(registrationId -> relatedArticle.getArticleId().equals(registrationId))) {
                continue;
            }

            Registration registration = _dsdParserUtils.getRegistration(relatedArticle);
            if (registration.canUserRegister(_themeDisplay.getScopeGroupId())) {
                _relatedRegistrations.add(registration);
            }

        }
    }

}
