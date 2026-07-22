/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import nl.deltares.forms.util.DeltaresCheckoutStepRegistry;
import nl.deltares.forms.util.SelectAdditionalAccountsCheckoutStep;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
        property = {
                "jakarta.portlet.name=" + OssConstants.REGISTRATIONFORM,
                "mvc.command.name=/submit/register/select_org_step"
        },
        service = MVCActionCommand.class
)
public class SelectOrganizationActionCommand extends SaveStepMVCActionCommand {

    @Override
    protected void doProcessAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        String checkoutStepName = ParamUtil.getString(
                actionRequest, "checkoutStepName");

        assert SelectAdditionalAccountsCheckoutStep.NAME.equals(checkoutStepName);

        super._checkoutStepRegistry = _checkoutStepRegistry;
        super._portal = _portal;

        super.doProcessAction(actionRequest, actionResponse);
    }

    @Reference
    private DeltaresCheckoutStepRegistry _checkoutStepRegistry;

    @Reference
    private Portal _portal;

}