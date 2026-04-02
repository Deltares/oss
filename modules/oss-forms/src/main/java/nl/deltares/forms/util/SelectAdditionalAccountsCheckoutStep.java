/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.util;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext;
import nl.deltares.forms.internal.FilterAccountSelectionCheckoutStepDisplayContext;
import nl.deltares.forms.portlet.PortletPermissionUtils;
import nl.deltares.portal.utils.AccountUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
        property = {
                "checkout.step.name=" + SelectAdditionalAccountsCheckoutStep.NAME,
                "checkout.step.order:Integer=4"
        },
        service = DeltaresCheckoutStep.class
)
public class SelectAdditionalAccountsCheckoutStep extends BaseCheckoutStep {

    public static final String NAME = "account-filter";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        String selectedAccountEntry = ParamUtil.getString(actionRequest, "selectedAccountEntryId");
        if (selectedAccountEntry != null && !selectedAccountEntry.isEmpty()) {

            HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
            AccountSelectionCheckoutStepDisplayContext _displayContext = new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, _accountEntryLocalService,
                    _addressLocalService, _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils,
                    _configurationProvider);

            try {
                _displayContext.addAccountEntry(selectedAccountEntry);
            } catch (Exception e) {
                SessionErrors.add(httpServletRequest, RegistrationFormException.class,
                        Collections.singletonList(new RegistrationFormException(e.getMessage())));
            }
        }

    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FilterAccountSelectionCheckoutStepDisplayContext _displayContext = new FilterAccountSelectionCheckoutStepDisplayContext(
                httpServletRequest, _commerceUtils, _configurationProvider);

        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, _displayContext);
        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/select-account.jsp");

    }

    @Override
    public boolean isActive(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        CPRequestHelper cpRequestHelper = new CPRequestHelper(httpServletRequest);
        ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();
        return PortletPermissionUtils.isUserSiteAdministrator(themeDisplay.getUserId(), themeDisplay.getSiteGroupId());
    }

    @Override
    public boolean showControls(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return false;
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