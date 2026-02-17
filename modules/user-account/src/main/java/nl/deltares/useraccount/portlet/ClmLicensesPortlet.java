package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.emails.LicenseFilesEmail;
import nl.deltares.portal.utils.LicenseManagerUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.SendLicenseFilesRequest;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import nl.deltares.useraccount.model.Asset;
import nl.deltares.useraccount.model.CustomerContact;
import nl.deltares.useraccount.model.SoftwareSuite;
import nl.deltares.useraccount.model.SoftwareSuiteSubscription;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                "javax.portlet.display-name=CLM Licenses",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/softwareSuites.jsp",
                "javax.portlet.name=" + UserProfilePortletKeys.CLM_LICENSES,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class ClmLicensesPortlet extends MVCPortlet {
    private static final Log logger = LogFactoryUtil.getLog(ClmLicensesPortlet.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Reference
    private LicenseManagerUtils licenseManagerUtils;

    public ClmLicensesPortlet() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        try {
            String selectedState = ParamUtil.getString(renderRequest, "filterSelection", "Active");
            long customerSelection = ParamUtil.getLong(renderRequest, "customerSelection", 0L);
            User user = themeDisplay.getUser();
            JSONArray customerContacts = licenseManagerUtils.getCustomerContactsForUser(user);
            Map<Long, String> customerInfo = LicenseManagerUtils.parseCustomerIdAndName(customerContacts);

            if (customerInfo.isEmpty()) {
                logger.warn(String.format("Found no customer ID for CLM user %s!", user.getEmailAddress()));
            } else {
                renderRequest.setAttribute("customerInfo", customerInfo);
                Long customerId;
                if (customerSelection == 0L) {
                    customerId = customerInfo.keySet().iterator().next();
                } else {
                    customerId = customerSelection;
                }
                Map<String, Object> customerContactInfo = LicenseManagerUtils.parseCustomerContact(customerContacts, customerId);
                Long customerContactId = (Long) customerContactInfo.getOrDefault("customerContactId", 0L);
                Boolean customerContactManageLicenses = (Boolean) customerContactInfo.getOrDefault("customerContactManageLicenses", false);
                JSONArray customerLicenses = licenseManagerUtils.getCustomerLicenses(user, selectedState, customerId, customerContactId, customerContactManageLicenses);
                if (customerLicenses != null && customerLicenses.length() > 0) {
                    List<SoftwareSuite> suites = convertToModel(customerLicenses);
                    renderRequest.setAttribute("records", suites);
                } else {
                    renderRequest.setAttribute("records", Collections.emptyList());
                }

            }
            renderRequest.setAttribute("filterSelection", selectedState);
            renderRequest.setAttribute("customerSelection", customerSelection);
        } catch (JSONException | ParseException e) {
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

    /**
     * Call sendLicenseFile action
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void sendLicenseFiles(ActionRequest actionRequest, ActionResponse actionResponse) {

        final long customerId = ParamUtil.getLong(actionRequest, "customerId", 0);
        final String customerName = ParamUtil.getString(actionRequest, "customerName", "");
        if (customerId == 0) {
            SessionErrors.add(actionRequest, "send-licenses-failed", "You are not recognized as a registered software license contact!");
            return;
        }

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        DataRequestManager instance = DataRequestManager.getInstance();
        String dataRequestId = SendLicenseFilesRequest.class.getName() + "_" + customerId + "_" + themeDisplay.getUser().getUserId();

        DataRequest dataRequest = instance.getDataRequest(dataRequestId);
        if (dataRequest != null) {
            if (dataRequest.getStatus() == DataRequest.STATUS.pending ||
                dataRequest.getStatus() == DataRequest.STATUS.running) {
                SessionMessages.add(actionRequest, "send-licenses-success");
                return;
            } else {
                instance.removeDataRequest(dataRequest);
            }
        }

        try {
            ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
            LicenseFilesEmail licenseFilesEmail = new LicenseFilesEmail(customerName, themeDisplay.getUser(), resourceBundle);
            dataRequest = new SendLicenseFilesRequest(dataRequestId, customerId, themeDisplay.getUser(),
                    licenseFilesEmail, licenseManagerUtils);
        } catch (IOException e) {
            SessionErrors.add(actionRequest, "send-licenses-failed", e.getMessage());
            return;
        }
        instance.addToQueue(dataRequest);
        SessionMessages.add(actionRequest, "send-licenses-success");

    }

    private List<SoftwareSuite> convertToModel(JSONArray customerData) throws ParseException {

        ArrayList<SoftwareSuite> suites = new ArrayList<>();
        for (int i = 0; i < customerData.length(); i++) {
            JSONObject suiteObject = customerData.getJSONObject(i);
            suites.add(convertToSuit(suiteObject));
        }
        return suites;
    }

    private SoftwareSuite convertToSuit(JSONObject suiteObject) throws ParseException {

        SoftwareSuite softwareSuite = new SoftwareSuite();
        softwareSuite.setSuiteCode(suiteObject.getString("softwareSuiteCode"));
        softwareSuite.setSuiteName(suiteObject.getString("softwareSuiteName"));
        softwareSuite.setSuiteId(suiteObject.getInt("softwareSuiteId"));

        JSONArray subscriptionObjects = suiteObject.getJSONArray("softwareSuiteSubscriptions");
        for (int i = 0; i < subscriptionObjects.length(); i++) {
            JSONObject subscriptionObject = subscriptionObjects.getJSONObject(i);
            softwareSuite.addSubscription(convertToSubscription(subscriptionObject));
        }

        return softwareSuite;
    }

    private SoftwareSuiteSubscription convertToSubscription(JSONObject subscriptionObject) throws ParseException {

        SoftwareSuiteSubscription subscription = new SoftwareSuiteSubscription();
        subscription.setSubscriptionId(subscriptionObject.getInt("subscriptionId"));
        subscription.setContractType(subscriptionObject.getString("subscriptionType"));
        subscription.setSubscriptionState(subscriptionObject.getString("subscriptionState"));
        subscription.setSoftwareVersion(subscriptionObject.getString("subscriptionLatestVersion"));

        String startDateString = subscriptionObject.getString("subscriptionStartDate", null);
        if (startDateString != null) subscription.setStartDate(dateFormat.parse(startDateString));
        String endDateString = subscriptionObject.getString("subscriptionEndDate", null);
        if (endDateString != null) subscription.setEndDate(dateFormat.parse(endDateString));
        subscription.setLicenseCount(subscriptionObject.getInt("subscriptionLicenseCount"));
        subscription.setLicenseUsed(subscriptionObject.getInt("subscriptionLicenseUsed"));
        subscription.setSupportHours(subscriptionObject.getInt("subscriptionSupportHours"));

        JSONObject softwareProductObject = subscriptionObject.getJSONObject("subscriptionSoftwareProduct");
        subscription.setSoftwareProductName(softwareProductObject.getString("softwareProductName"));

        JSONArray supportProductArray = subscriptionObject.getJSONArray("subscriptionSupportProducts");
        if (supportProductArray.length() > 0) {
            JSONObject supportProductObject = supportProductArray.getJSONObject(0);
            subscription.setSupportLevelName(supportProductObject.getString("supportProductSupportLevelName"));
            subscription.setSupportLevelValue(supportProductObject.getInt("supportProductSupportLevelValue"));
        }

        JSONArray assetsArray = subscriptionObject.getJSONArray("subscriptionAssets");
        for (int i = 0; i < assetsArray.length(); i++) {
            subscription.addAsset(convertToAsset(assetsArray.getJSONObject(i)));
        }

        JSONArray contactsArray = subscriptionObject.getJSONArray("subscriptionCustomerContacts");
        for (int i = 0; i < contactsArray.length(); i++) {
            subscription.addCustomerContact(convertToContact(contactsArray.getJSONObject(i)));
        }

        return subscription;
    }

    private Asset convertToAsset(JSONObject assetObject) {
        Asset asset = new Asset();

        asset.setHardwareId(assetObject.getString("subscriptionAssetHardwareId", null));
        asset.setType(assetObject.getString("subscriptionAssetType", null));
        asset.setServerName(assetObject.getString("subscriptionAssetServerName", null));
        asset.setUserCount(assetObject.getInt("subscriptionAssetUserCount", 0));
        return asset;
    }

    private CustomerContact convertToContact(JSONObject contactObject) {
        CustomerContact contact = new CustomerContact();
        contact.setContactId(contactObject.getInt("customerContactId", 0));
        contact.setContactName(contactObject.getString("customerContactName", null));
        contact.setContactSalutation(contactObject.getString("customerContactSalutation", null));
        contact.setContactEmail(contactObject.getString("customerContactEmail", null));
        return contact;
    }

}