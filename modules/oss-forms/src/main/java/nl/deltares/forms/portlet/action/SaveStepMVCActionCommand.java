/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.*;
import nl.deltares.forms.util.DeltaresCheckoutStep;
import nl.deltares.forms.util.DeltaresCheckoutStepRegistry;
import nl.deltares.portal.constants.OssConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"javax.portlet.name=" + OssConstants.REGISTRATIONFORM,
		"mvc.command.name=/submit/register/save_step"
	},
	service = MVCActionCommand.class
)
public class SaveStepMVCActionCommand extends BaseMVCActionCommand {

	public String getRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String checkoutStepName)
		throws Exception {

		String redirect = GetterUtil.getString(
			actionRequest.getAttribute(WebKeys.REDIRECT));

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(actionRequest);
		if (!SessionErrors.isEmpty(httpServletRequest)) {
			return _getPortletURL(
				actionRequest, actionResponse, checkoutStepName);
		}

		DeltaresCheckoutStep commerceCheckoutStep =
			_checkoutStepRegistry.getNextCheckoutStep(
				checkoutStepName, httpServletRequest,
				_portal.getHttpServletResponse(actionResponse));

		if (commerceCheckoutStep == null) {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		return _getPortletURL(
			actionRequest, actionResponse, commerceCheckoutStep.getName());
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String checkoutStepName = ParamUtil.getString(
			actionRequest, "checkoutStepName");

		DeltaresCheckoutStep commerceCheckoutStep =
				_checkoutStepRegistry.getCheckoutStep(
				checkoutStepName);

		commerceCheckoutStep.processAction(actionRequest, actionResponse);

		hideDefaultSuccessMessage(actionRequest);

		String redirect = getRedirect(
			actionRequest, actionResponse, checkoutStepName);

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	private String _getPortletURL(
		ActionRequest actionRequest, ActionResponse actionResponse,
		String checkoutStepName) {

		String ids = ParamUtil.getString(actionRequest, "ids");

		return PortletURLBuilder.createRenderURL(
			_portal.getLiferayPortletResponse(actionResponse)
		).setParameter(
			"checkoutStepName", checkoutStepName
		).setParameter(
		"ids", ids
		)
		.buildString();
	}

	@Reference
	private DeltaresCheckoutStepRegistry _checkoutStepRegistry;

	@Reference
	private Portal _portal;

}