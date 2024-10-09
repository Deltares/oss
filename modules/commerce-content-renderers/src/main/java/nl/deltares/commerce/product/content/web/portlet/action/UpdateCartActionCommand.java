/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.commerce.product.content.web.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
        property = {
                "javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT,
                "javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
                "mvc.command.name=/commerce_cart_content/update_order_items"
        },
        service = MVCActionCommand.class
)
public class UpdateCartActionCommand
        extends BaseMVCActionCommand {

    @Override
    protected void doProcessAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {
        final String action = ParamUtil.getString(actionRequest, "action");
        if ("remove-from-cart".equals(action)) {
            deleteFromCartAction(actionRequest);
        } else if ("add-to-cart".equals(action)) {
            addToCartAction(actionRequest);
        }
    }

    private void deleteFromCartAction(ActionRequest actionRequest) {

        long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

        CommerceContext commerceContext =
                (CommerceContext) actionRequest.getAttribute(
                        CommerceWebKeys.COMMERCE_CONTEXT);

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
                actionRequest);

        try {

            CommerceOrder commerceOrder =
                    _commerceOrderHttpHelper.getCurrentCommerceOrder(
                            httpServletRequest);
            if (commerceOrder != null){
                final List<CommerceOrderItem> commerceOrderItems = commerceOrder.getCommerceOrderItems(cpInstanceId);
                for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
                    _commerceOrderItemService.deleteCommerceOrderItem(commerceOrderItem.getCommerceOrderItemId(), commerceContext);
                }
            }
        } catch (PortalException e) {
            SessionErrors.add(httpServletRequest, "update-cart-failed", e.getLocalizedMessage());
        }
    }

    private void addToCartAction(ActionRequest actionRequest) {

        long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

        HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
                actionRequest);

        try {
            CommerceOrder commerceOrder =
                    _commerceOrderHttpHelper.getCurrentCommerceOrder(
                            httpServletRequest);

            if (commerceOrder == null) {
                commerceOrder = _commerceOrderHttpHelper.addCommerceOrder(
                        httpServletRequest);
            }

            _commerceOrderItemService.addCommerceOrderItem(
                    commerceOrder.getCommerceOrderId(), cpInstanceId,
                    null, BigDecimal.ONE,
                    0, BigDecimal.ZERO,
                    ParamUtil.getString(actionRequest, "unitOfMeasureKey"),
                    (CommerceContext) httpServletRequest.getAttribute(
                            CommerceWebKeys.COMMERCE_CONTEXT),
                    ServiceContextFactory.getInstance(
                            CommerceOrderItem.class.getName(), httpServletRequest));


        } catch (CommerceOrderValidatorException
                commerceOrderValidatorException) {

            List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
                    commerceOrderValidatorException.
                            getCommerceOrderValidatorResults();

            JSONArray errorJSONArray = _jsonFactory.createJSONArray();

            for (CommerceOrderValidatorResult commerceOrderValidatorResult :
                    commerceOrderValidatorResults) {

                JSONObject errorJSONObject = _jsonFactory.createJSONObject();

                errorJSONObject.put(
                        "message",
                        commerceOrderValidatorResult.getLocalizedMessage());

                errorJSONArray.put(errorJSONObject);
            }

            SessionErrors.add(httpServletRequest, "update-cart-failed", errorJSONArray.toJSONString());
        } catch (Exception exception) {
            _log.error(exception);

            SessionErrors.add(httpServletRequest, "update-cart-failed", exception.getLocalizedMessage());

        }
    }

    private static final Log _log = LogFactoryUtil.getLog(
            UpdateCartActionCommand.class);
    @Reference
    private CommerceOrderItemService _commerceOrderItemService;

    @Reference
    private CommerceOrderHttpHelper _commerceOrderHttpHelper;

    @Reference
    private Portal _portal;

    @Reference
    private JSONFactory _jsonFactory;

}