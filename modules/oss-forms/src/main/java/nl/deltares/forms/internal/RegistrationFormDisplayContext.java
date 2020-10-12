package nl.deltares.forms.internal;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
                                          DsdParserUtils dsdParserUtils, DsdSessionUtils dsdSessionUtils) {
        this.portletRequest = portletRequest;
        this.portletResponse = portletResponse;
        this.dsdParserUtils = dsdParserUtils;
        this.dsdSessionUtils = dsdSessionUtils;
    }

    public List<Registration> getChildRegistrations(long siteId, String articleId) {
        List<Registration> children = new ArrayList<>();
        try {
            children = dsdSessionUtils.getChildRegistrations(
                    dsdParserUtils.getRegistration(siteId, articleId));
        } catch (Exception e) {
            LOG.debug("Error retrieving children for registration [" + articleId + "]", e);
        }
        return children;
    }

    public JournalArticleDisplay getArticleDisplay(String ddmTemplateKey, String articleId, ThemeDisplay themeDisplay) {
        return RegistrationDisplayUtils
                .getArticleDisplay(portletRequest, portletResponse, ddmTemplateKey, articleId, themeDisplay);
    }

    private final DsdSessionUtils dsdSessionUtils;
    private final DsdParserUtils dsdParserUtils;
    private final PortletRequest portletRequest;
    private final PortletResponse portletResponse;

    private static final Log LOG = LogFactoryUtil.getLog(RegistrationFormDisplayContext.class);
}
