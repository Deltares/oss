/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import nl.deltares.forms.constants.CheckoutWebKeys;
import nl.deltares.forms.exception.RegistrationFormException;
import nl.deltares.forms.internal.AccountSelectionCheckoutStepDisplayContext;
import nl.deltares.portal.utils.CommerceUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

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
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
        AccountSelectionCheckoutStepDisplayContext displayContext =
                new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, _accountEntryLocalService,
                        _addressLocalService, _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils);

        AccountEntry accountEntry = displayContext.storeAccountInfo();

        if (accountEntry != null) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("selectedAccountEntryId", accountEntry.getAccountEntryId());


//            List<RegistrationInfo> registrationInfos = (List<RegistrationInfo>) session.getAttribute("registrationInfos");
//            BillingInfo billingInfo = (BillingInfo) session.getAttribute("billingInfo");
//            if (registrationInfos != null) {
//                UserRegistrationContext registrationContext = new UserRegistrationContext(httpServletRequest,
//                        _dsdSessionUtils, _dsdParserUtils, _webinarUtilsFactory, _userLocalService, _keycloakUtils);
//
//                registrationContext.storeUserInformation(registrationInfos, billingInfo, accountEntry);
//            }
        }
    }

    @Override
    public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        AccountSelectionCheckoutStepDisplayContext displayContext =
                new AccountSelectionCheckoutStepDisplayContext(httpServletRequest, _accountEntryLocalService,
                        _addressLocalService, _countryLocalService, _phoneLocalService, _userLocalService, _commerceUtils);

        httpServletRequest.setAttribute(CheckoutWebKeys.CHECKOUT_STEP_DISPLAY_CONTEXT, displayContext);

        try {
            displayContext.loadAccounts();
        } catch (Exception e) {
            SessionErrors.add(httpServletRequest, RegistrationFormException.class, Collections.singletonList(new RegistrationFormException(e.getMessage(), e)));
        }

        Object value = httpServletRequest.getSession().getAttribute("selectedAccountEntryId");
        if (value != null) {
            httpServletRequest.setAttribute("selectedAccountEntryId", value);
        }

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
    private CommerceUtils _commerceUtils;

}