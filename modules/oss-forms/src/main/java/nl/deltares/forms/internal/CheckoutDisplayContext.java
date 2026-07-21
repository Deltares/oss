/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.internal;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.taglib.servlet.PipingServletResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.PageContext;
import nl.deltares.forms.util.DeltaresCheckoutStep;
import nl.deltares.forms.util.DeltaresCheckoutStepRegistry;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CheckoutDisplayContext {

    public CheckoutDisplayContext(
            DeltaresCheckoutStepRegistry checkoutStepRegistry,
            LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse, Portal portal) {

        _commerceCheckoutStepRegistry = checkoutStepRegistry;
        _httpServletRequest = portal.getHttpServletRequest(
                liferayPortletRequest);
        _httpServletResponse = portal.getHttpServletResponse(
                liferayPortletResponse);

        String checkoutStepName = ParamUtil.getString(
                liferayPortletRequest, "checkoutStepName");

        DeltaresCheckoutStep commerceCheckoutStep =
                checkoutStepRegistry.getCheckoutStep(
                        checkoutStepName);

        if ((commerceCheckoutStep == null)) {
            List<DeltaresCheckoutStep> commerceCheckoutSteps =
                    checkoutStepRegistry.getCheckoutSteps(
                            _httpServletRequest, _httpServletResponse, true);

            commerceCheckoutStep = commerceCheckoutSteps.getFirst();
        }

        _commerceCheckoutStep = commerceCheckoutStep;
    }

    public List<DeltaresCheckoutStep> getCheckoutSteps() {

        return _commerceCheckoutStepRegistry.getCheckoutSteps(
                _httpServletRequest, _httpServletResponse, true);
    }

    public String getCurrentCheckoutStepName() {
        return _commerceCheckoutStep.getName();
    }

    public String getPreviousCheckoutStepName() {
        DeltaresCheckoutStep commerceCheckoutStep =
                _commerceCheckoutStepRegistry.getPreviousCheckoutStep(
                        _commerceCheckoutStep.getName(), _httpServletRequest,
                        _httpServletResponse);

        if (commerceCheckoutStep == null) return null;
        return commerceCheckoutStep.getName();
    }

    public boolean isSennaDisabled() {
        return _commerceCheckoutStep.isSennaDisabled();
    }

    public void renderCurrentCheckoutStep(PageContext pageContext) throws Exception {

        _commerceCheckoutStep.render(
                _httpServletRequest,
                PipingServletResponseFactory.createPipingServletResponse(
                        pageContext));
    }

    public boolean showControls() {
        return _commerceCheckoutStep.showControls(
                _httpServletRequest, _httpServletResponse);
    }

    private final DeltaresCheckoutStep _commerceCheckoutStep;
    private final DeltaresCheckoutStepRegistry _commerceCheckoutStepRegistry;
    private final HttpServletRequest _httpServletRequest;
    private final HttpServletResponse _httpServletResponse;

}