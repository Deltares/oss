package nl.deltares.forms.internal;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;
import jakarta.servlet.http.HttpServletRequest;
import nl.deltares.forms.portlet.RegistrationFormConfiguration;
import nl.deltares.model.RegistrationFormContext;
import nl.deltares.model.RegistrationsInfo;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;

import java.util.List;

public class RelatedAssetsDisplayContext {

    private static final Log LOG = LogFactoryUtil.getLog(RelatedAssetsDisplayContext.class);
    private final RegistrationsInfo _registrationsInfo;
    private final RegistrationFormConfiguration _portletInstanceConfiguration;

    public RelatedAssetsDisplayContext(HttpServletRequest request, ConfigurationProvider configurationProvider,
                                       DsdJournalArticleUtils dsdJournalArticleUtils,
                                       DsdParserUtils dsdParserUtils) throws Exception {

        RegistrationFormContext context = (RegistrationFormContext) request.getSession().getAttribute("registration-context");
        if (context == null) {
            context = new RegistrationFormContext();
            request.getSession().setAttribute("registration-context", context);
        }
        CPRequestHelper cpRequestHelper = new CPRequestHelper(request);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

        RegistrationsInfo registrationsInfo = context.getRegistrationsInfo();
        if (registrationsInfo == null) {
            _registrationsInfo = new RegistrationsInfo(dsdParserUtils, themeDisplay);
            context.setRegistrationsInfo(_registrationsInfo);
        } else {
            _registrationsInfo = registrationsInfo;
        }

        _portletInstanceConfiguration = configurationProvider.getPortletInstanceConfiguration(RegistrationFormConfiguration.class,
                themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());

        RegistrationsInfo.loadRegistrations(request, _registrationsInfo);
        RegistrationsInfo.loadRegistrationEvents(_registrationsInfo);
        RegistrationsInfo.loadRelatedArticles(_registrationsInfo, dsdJournalArticleUtils, dsdParserUtils);
        RegistrationsInfo.loadChildArticles(_registrationsInfo);
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
        return _registrationsInfo.getRegistrationArticleIds();
    }

    public List<String> getRelatedArticleIds() {
        return _registrationsInfo.getRelatedArticleIds();
    }

    public String getRelatedAssetsTemplate(){
        return _portletInstanceConfiguration.relatedAssetsTemplate();
    }

    public String getSelectedAssetsTemplate(){
        return _portletInstanceConfiguration.selectedAssetsTemplate();
    }

    public boolean isActive() {
        return _portletInstanceConfiguration.alwaysShowRelatedInfo() || !_registrationsInfo.getRelatedArticleIds().isEmpty();
    }
}
