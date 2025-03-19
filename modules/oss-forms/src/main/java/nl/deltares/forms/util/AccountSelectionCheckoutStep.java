/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.util;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext;
import nl.deltares.forms.internal.UserRegistrationValidationContext;
import nl.deltares.portal.utils.CommerceUtils;
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
                "checkout.step.name=" + AccountSelectionCheckoutStep.NAME,
                "checkout.step.order:Integer=10"
        },
        service = DeltaresCheckoutStep.class
)
public class AccountSelectionCheckoutStep extends BaseCheckoutStep {

    private static final Log LOG = LogFactoryUtil.getLog(AccountSelectionCheckoutStep.class);

    public static final String NAME = "account-info";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        AccountSelectionCheckoutStepDisplayContext displayContext =
                new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, accountEntryLocalService,
                        _commerceUtils, addressLocalService, countryLocalService);

        displayContext.storeAccountSelection(httpServletRequest);

        if (SessionErrors.contains(httpServletRequest, RegistrationFormException.class)){
            return;
        }
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        try {
            AccountSelectionCheckoutStepDisplayContext displayContext =
                    new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, accountEntryLocalService,
                            _commerceUtils, addressLocalService, countryLocalService);

            displayContext.loadAccounts();

            httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, displayContext);

        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(new RegistrationFormException(e.getMessage(), e)));
        }
        _jspRenderer.renderJSP(
                httpServletRequest, httpServletResponse,
                "/registration2.0/account-info.jsp");

    }

    @Reference
    private Portal _portal;

    @Reference
    private CommerceUtils _commerceUtils;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private AccountEntryLocalService accountEntryLocalService;

    @Reference
    private AddressLocalService addressLocalService;

    @Reference
    private CountryLocalService countryLocalService;

}