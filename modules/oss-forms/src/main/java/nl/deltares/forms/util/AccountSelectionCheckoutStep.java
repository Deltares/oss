/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.util;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext;
import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
        property = {
                "checkout.step.name=" + AccountSelectionCheckoutStep.NAME,
                "checkout.step.order:Integer=5"
        },
        service = DeltaresCheckoutStep.class
)
public class AccountSelectionCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "account-info";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        AccountSelectionCheckoutStepDisplayContext _displayContext = new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, _accountEntryLocalService,
                _addressLocalService, _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils,
                _configurationProvider);

        _displayContext.storeAccountInfo(httpServletRequest);

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        AccountSelectionCheckoutStepDisplayContext _displayContext = new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, _accountEntryLocalService,
                _addressLocalService, _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils,
                _configurationProvider);

        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _displayContext);

        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/account-info.jsp");

    }

    @Reference
    private Portal _portal;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private AddressLocalService _addressLocalService;

    @Reference
    private CountryLocalService _countryLocalService;

    @Reference
    private PhoneLocalService _phoneLocalService;

    @Reference
    private UserLocalService _userLocalService;

    @Reference
    private AccountUtils _commerceUtils;

    @Reference
    private ConfigurationProvider _configurationProvider;
}