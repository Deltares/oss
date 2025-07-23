package nl.deltares.forms.util;

import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.RelatedAssetsDisplayContext;
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

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/related-assets.jsp");
    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            RelatedAssetsDisplayContext displayContext = new RelatedAssetsDisplayContext(httpServletRequest,
                    _assAssetEntryService,
                    _dsdParserUtils);
            httpServletRequest.setAttribute(
                    CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT,
                    displayContext);

            return !displayContext.getRelatedArticles().isEmpty();
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
    private AssetEntryService _assAssetEntryService;

}
