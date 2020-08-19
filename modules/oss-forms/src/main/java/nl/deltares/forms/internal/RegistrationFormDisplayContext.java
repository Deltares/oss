package nl.deltares.forms.internal;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.util.ArrayList;
import java.util.List;

public class RegistrationFormDisplayContext {

    public RegistrationFormDisplayContext(PortletRequest portletRequest, PortletResponse portletResponse,
                                          DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils,
                                          long groupId, String registrationId) {
        this.portletRequest = portletRequest;
        this.portletResponse = portletResponse;
        this.groupId = groupId;
        this.registrationId = registrationId;
        this.dsdParserUtils = dsdParserUtils;
        this.dsdSessionUtils = dsdSessionUtils;
    }

    public List<Registration> getChildRegistrations(long siteId, String registrationId) {
        List<Registration> children = new ArrayList<>();
        try {
            children = dsdSessionUtils.getChildRegistrations(
                    dsdParserUtils.getRegistration(siteId, registrationId));
        } catch (Exception e) {
            LOG.debug("Error retrieving children for registration [" + registrationId + "]", e);
        }
        return children;
    }

    public JournalArticleDisplay getArticleDisplay(String ddmTemplateKey, String articleId, ThemeDisplay themeDisplay) {
        JournalArticleDisplay articleDisplay = null;
        try {
            articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
                    themeDisplay.getScopeGroupId(), articleId, ddmTemplateKey, "VIEW",
                    themeDisplay.getLanguageId(), 1, new PortletRequestModel(portletRequest, portletResponse),
                    themeDisplay);
        } catch (Exception e) {
            String message = String.format("Error getting article display object for article [%s] with template ID [%s]",
                    articleId, ddmTemplateKey);
            LOG.debug(message, e);
        }
        return articleDisplay;
    }

    private final long groupId;
    private final String registrationId;
    private final DsdSessionUtils dsdSessionUtils;
    private final DsdParserUtils dsdParserUtils;
    private final PortletRequest portletRequest;
    private final PortletResponse portletResponse;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationFormDisplayContext.class);
}
