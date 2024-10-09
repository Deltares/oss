package nl.deltares.commerce.product.content.web.renderer.list;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

@Component(
        property = {
                "commerce.product.content.list.renderer.key=" + ProductItemsListRenderer.KEY,
                "commerce.product.content.list.renderer.order=" + Integer.MIN_VALUE,
                "commerce.product.content.list.renderer.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
                "commerce.product.content.list.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS
        },
        service = CPContentListRenderer.class
)
public class ProductItemsListRenderer implements CPContentListRenderer {

    public static final String KEY = "list-dsd";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getLabel(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
                "content.Language", locale, getClass());

        return _language.get(resourceBundle, "Deltares product list");
    }

    @Override
    public void render(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws Exception {

        httpServletRequest.setAttribute(
                CPContentWebKeys.CP_CONTENT_HELPER, _cpContentHelper);


        httpServletRequest.setAttribute(CommerceWebKeys.COMMERCE_ORDER, _commerceOrderHttpHelper.getCurrentCommerceOrder(httpServletRequest));

        _jspRenderer.renderJSP(
                _servletContext, httpServletRequest, httpServletResponse,
                "/product_publisher/render/list/dsd-view.jsp");
    }

    @Reference
    private CommerceOrderHttpHelper _commerceOrderHttpHelper;

    @Reference
    private CPContentHelper _cpContentHelper;

    @Reference
    private JSPRenderer _jspRenderer;

    @Reference
    private Language _language;

    @Reference(
            target = "(osgi.web.symbolicname=nl.deltares.commerce.product.content.web)"
    )
    private ServletContext _servletContext;

}