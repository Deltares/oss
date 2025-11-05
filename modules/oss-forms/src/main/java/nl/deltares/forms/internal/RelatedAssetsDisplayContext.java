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
import com.liferay.portal.kernel.util.Validator;
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
    private final List<String> _selectedRegistrations = new ArrayList<>();
    private final ThemeDisplay _themeDisplay;

    public RelatedAssetsDisplayContext(HttpServletRequest request, DsdJournalArticleUtils dsdJournalArticleUtils, DsdParserUtils dsdParserUtils) throws Exception {

        _dsdJournalArticleUtils = dsdJournalArticleUtils;
        _dsdParserUtils = dsdParserUtils;
        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        _themeDisplay = cpRequestHelper.getThemeDisplay();
        String ids = ParamUtil.getString(request, "ids");

        if (ids.isEmpty()) return;
        String[] registrationIds = ids.split(",", -1);
        List<String> list = Arrays.asList(registrationIds);
        list.forEach(s -> {if (!Validator.isBlank(s)){ _selectedRegistrations.add(s);}});
        loadRelatedAssets();
    }

    public JournalArticleDisplay getArticleDisplay(PortletRequest portletRequest, PortletResponse portletResponse,
                                                   String ddmTemplateKey, long groupId, String articleId, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(groupId,
                    articleId, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    articleId, ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    public List<String> getSelectedArticleIds(){
        return _selectedRegistrations;
    }

    public List<Registration> getRelatedArticles() {
        return _relatedRegistrations;
    }

    public ThemeDisplay getThemeDisplay() {
        return _themeDisplay;
    }

    private void loadRelatedAssets() throws Exception {

        String[] selectedRegistrations = _selectedRegistrations.toArray(new String[0]);
        List<JournalArticle> relatedArticles = _dsdJournalArticleUtils.getRelatedArticles(_themeDisplay.getScopeGroupId(), selectedRegistrations);
        for (JournalArticle relatedArticle : relatedArticles) {
            if (relatedArticle == null) {
                continue;
            }
            if (Arrays.stream(selectedRegistrations).anyMatch(registrationId -> relatedArticle.getArticleId().equals(registrationId))) {
                continue;
            }

            Registration registration = _dsdParserUtils.getRegistration(relatedArticle);
            if (registration.canUserRegister(_themeDisplay.getScopeGroupId())) {
                _relatedRegistrations.add(registration);
            }

        }
    }
}
