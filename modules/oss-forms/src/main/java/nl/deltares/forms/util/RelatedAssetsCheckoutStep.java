package nl.deltares.forms.util;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.RelatedAssetsDisplayContext;
import nl.deltares.forms.portlet.RegistrationFormConfiguration;
import nl.deltares.portal.utils.DsdJournalArticleUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component(
        property = {
                "checkout.step.name=" + RelatedAssetsCheckoutStep.NAME,
                "checkout.step.order:Integer=2"
        },
        service = DeltaresCheckoutStep.class
)
public class RelatedAssetsCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "related-content";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        RegistrationFormConfiguration portletInstanceConfiguration = _configurationProvider.getPortletInstanceConfiguration(RegistrationFormConfiguration.class,
                themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId());

        httpServletRequest.setAttribute("relatedAssetsTemplate", portletInstanceConfiguration.relatedAssetsTemplate());
        httpServletRequest.setAttribute("selectedAssetsTemplate", portletInstanceConfiguration.selectedAssetsTemplate());

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/related-assets.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            RelatedAssetsDisplayContext displayContext = new RelatedAssetsDisplayContext(httpServletRequest,
                    _dsdJournalArticleUtils,
                    _dsdParserUtils);
            httpServletRequest.setAttribute(
                    CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT,
                    displayContext);


            CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
            ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
            Boolean showAlways = _configurationProvider.getPortletInstanceConfiguration(RegistrationFormConfiguration.class,
                    themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId()).alwaysShowRelatedInfo();

            return showAlways || !displayContext.getRelatedArticles().isEmpty();
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(
                    new RegistrationFormException(e.getMessage(), e)));
        }
        return false;
    }

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private DsdParserUtils _dsdParserUtils;

    @Reference
    private DsdJournalArticleUtils _dsdJournalArticleUtils;

    @Reference
    private ConfigurationProvider _configurationProvider;

}
