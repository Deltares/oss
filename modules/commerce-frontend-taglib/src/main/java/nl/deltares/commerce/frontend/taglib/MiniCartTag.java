package nl.deltares.commerce.frontend.taglib;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.taglib.util.IncludeTag;
import nl.deltares.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import nl.deltares.portal.constants.OssConstants;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class MiniCartTag extends IncludeTag {

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest httpServletRequest = getRequest();

        CommerceContext commerceContext =
                (CommerceContext) httpServletRequest.getAttribute(
                        CommerceWebKeys.COMMERCE_CONTEXT);

        try {

            _commerceChannelId = 0;

            if (commerceContext != null) {
                _commerceChannelId = commerceContext.getCommerceChannelId();
            }
            if (_commerceChannelId == 0) {
                _checkoutURL = StringPool.BLANK;
                _itemsQuantity = 0;
                _orderId = 0;

                return super.doStartTag();
            }

            _checkoutURL = StringPool.BLANK;

            PortletURL portletURL = PortletProviderUtil.getPortletURL(
                    httpServletRequest, OssConstants.DSD_REGISTRATIONFORM,
                    PortletProvider.Action.VIEW);

            if (portletURL != null) {
                _checkoutURL = PortletURLBuilder.create(
                        portletURL
                ).buildString();
            }

            CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

            if (commerceOrder != null) {
                _itemsQuantity = _getItemsQuantity(commerceOrder);
                _orderId = commerceOrder.getCommerceOrderId();

            } else {
                _orderId = 0;
            }

        } catch (PortalException portalException) {
            _log.error(portalException);

            _checkoutURL = StringPool.BLANK;
            _itemsQuantity = 0;
            _orderId = 0;
        }
        return super.doStartTag();
    }

    @Override
    public void setPageContext(PageContext pageContext) {
        super.setPageContext(pageContext);
        setServletContext(ServletContextUtil.getServletContext());
    }

    @Override
    protected void cleanUp() {
        super.cleanUp();

        _commerceChannelId = 0;
        _checkoutURL = null;
        _itemsQuantity = 0;
        _orderId = 0;
    }

    @Override
    protected String getPage() {
        return _PAGE;
    }

    @Override
    protected void setAttributes(HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute(
                "liferay-commerce:cart:commerceChannelId", _commerceChannelId);
        httpServletRequest.setAttribute(
                "liferay-commerce:cart:checkoutURL", _checkoutURL);
        httpServletRequest.setAttribute(
                "liferay-commerce:cart:itemsQuantity", _itemsQuantity);

    }

    private int _getItemsQuantity(CommerceOrder commerceOrder)
            throws PortalException {

        List<CommerceOrderItem> commerceOrderItems =
                commerceOrder.getCommerceOrderItems();

        return commerceOrderItems.size();
    }


    private static final String _PAGE = "/mini_cart/page.jsp";

    private static final Log _log = LogFactoryUtil.getLog(MiniCartTag.class);

    private long _commerceChannelId;
    private String _checkoutURL;
    private int _itemsQuantity;
    private long _orderId;

}
