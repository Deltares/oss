package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.utils.LicenseManagerUtils;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import nl.deltares.useraccount.model.CustomerContact;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;

/**
 * @author rooij_e
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS-account",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=CLM Customer Contacts",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/customerContacts.jsp",
                "javax.portlet.name=" + UserProfilePortletKeys.CLM_CUSTOMER_CONTACTS,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class ClmCustomerContactsPortlet extends MVCPortlet {
    private static final Log logger = LogFactoryUtil.getLog(ClmCustomerContactsPortlet.class);

    @Reference
    private LicenseManagerUtils licenseManagerUtils;

    public ClmCustomerContactsPortlet() {
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        try {
            String filter = ParamUtil.getString(renderRequest, "filterSelection", "all");
            long customerSelection = ParamUtil.getLong(renderRequest, "customerSelection", 0L);
            User user = themeDisplay.getUser();
            JSONArray customerContactsForUser = licenseManagerUtils.getCustomerContactsForUser(user);
            Map<Long, String> customerInfo = LicenseManagerUtils.parseCustomerIdAndName(customerContactsForUser);
            if (customerInfo.isEmpty()) {
                logger.warn(String.format("Found no customer ID for CLM user %s!", user.getEmailAddress()));
            } else {
                renderRequest.setAttribute("customerInfo", customerInfo);

                if (customerSelection == 0L && !customerInfo.isEmpty()) {
                    customerSelection = customerInfo.keySet().iterator().next();
                }

                boolean hasManageLicensesPermission = false;
                for (int i = 0; i < customerContactsForUser.length(); i++) {
                    CustomerContact customerContact = convertToContact(customerContactsForUser.getJSONObject(i));
                    if (customerContact.getCustomerId() != customerSelection) continue;
                    if (!customerContact.isContactManageLicenses()){
                        SessionErrors.add(renderRequest, "customer-contacts-unauthorized", customerInfo.get(customerSelection));
                        break;
                    }
                        hasManageLicensesPermission = true;
                }

                if (hasManageLicensesPermission) {
                    final int curPage = ParamUtil.getInteger(renderRequest, "cur", 1);
                    final int deltas = ParamUtil.getInteger(renderRequest, "delta", 25);
                    int start = (curPage - 1) * deltas;

                    List<CustomerContact> filteredContacts = new ArrayList<>();
                    JSONArray customerContactsForCustomer = licenseManagerUtils.getCustomerContactsForCustomerAndFilter(
                            customerSelection, "beta-tester".equals(filter), "license-manager".equals(filter));

                    int end = Math.min(start + deltas, customerContactsForCustomer.length());
                    for (int i = start; i < end; i++) {
                        filteredContacts.add(convertToContact(customerContactsForCustomer.getJSONObject(i)));
                    }
                    renderRequest.setAttribute("customerContactList", filteredContacts);
                    renderRequest.setAttribute("totalCustomerContactCount", customerContactsForCustomer.length());

                }


            }
            renderRequest.setAttribute("filterSelection", filter);
            renderRequest.setAttribute("customerSelection", customerSelection);
        } catch (JSONException e) {
            throw new PortletException(e);
        }
        super.render(renderRequest, renderResponse);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void filter(ActionRequest actionRequest, ActionResponse actionResponse) {

        final String filter = ParamUtil.getString(actionRequest, "filterSelection", "Active");
        actionResponse.getRenderParameters().setValue("filterSelection", filter);
    }

    /**
     * Pass the selected filter options to the render request
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void customerSelect(ActionRequest actionRequest, ActionResponse actionResponse) {

        final Long filter = ParamUtil.getLong(actionRequest, "customerSelection", 0L);
        actionResponse.getRenderParameters().setValue("customerSelection", String.valueOf(filter));
    }

    private CustomerContact convertToContact(JSONObject contactObject) {
        CustomerContact contact = new CustomerContact();
        JSONObject customer = contactObject.getJSONObject("customerContactCustomer");
        contact.setCustomerId(contactObject.getLong("customerId", customer.getLong("customerId")));

        contact.setContactId(contactObject.getLong("customerContactId", 0L));
        contact.setContactName(contactObject.getString("customerContactName", null));
        contact.setContactSalutation(contactObject.getString("customerContactSalutation", null));
        contact.setContactManageLicenses(contactObject.getBoolean("customerContactManageLicenses", false));

        JSONArray contactData = contactObject.getJSONArray("customerContactDataViews");
        for (int i = 0; i < contactData.length(); i++) {
            JSONObject dataObject = contactData.getJSONObject(i);
            String type = dataObject.getString("customerContactDataType");
            if (!"Email".equals(type)) continue;
            contact.setContactEmail(dataObject.getString("customerContactDataValue", null));
        }
        return contact;
    }

}