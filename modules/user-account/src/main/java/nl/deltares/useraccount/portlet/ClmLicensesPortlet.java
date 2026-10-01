package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.json.JSONArray;
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
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.portal.utils.LicenseManagerUtils;
import nl.deltares.tasks.DataRequest;
import nl.deltares.tasks.DataRequestManager;
import nl.deltares.tasks.impl.SendLicenseFilesRequest;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import nl.deltares.useraccount.model.*;
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
                "javax.portlet.init-param.view-template=/softwareMainPage.jsp",
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

    @Reference
    private KeycloakUtils keycloakUtils;

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
            String tabSelection =  ParamUtil.getString(renderRequest, "tabSelection", "softwareSuites");
            User user = themeDisplay.getUser();
            JSONArray customerContacts = licenseManagerUtils.getCustomerContactsForUser(user);
            Map<Long, String> customerInfo = LicenseManagerUtils.parseCustomerIdAndName(customerContacts);
            List<?> models = null;
            String maconomyId = "";
            Map<String, String> keycloakGroups = null;
            if (customerInfo.isEmpty()) {
                logger.warn(String.format("Found no customer ID for CLM user %s!", user.getEmailAddress()));
            } else {
                if (customerSelection == 0L) {
                    customerSelection = customerInfo.keySet().iterator().next();
                }
                Map<String, Object> customerContactInfo = LicenseManagerUtils.parseCustomerContact(customerContacts, customerSelection);
                Long customerContactId = (Long) customerContactInfo.getOrDefault("customerContactId", 0L);
                Boolean customerContactManageLicenses = (Boolean) customerContactInfo.getOrDefault("customerContactManageLicenses", false);
                JSONArray customerLicenses = licenseManagerUtils.getCustomerLicenses(user, selectedState, customerSelection, customerContactId, customerContactManageLicenses);
                if (customerLicenses != null && customerLicenses.length() > 0) {
                    if (tabSelection.equals("softwareSuites")) {
                        models = convertToSoftwareSuiteModel(customerLicenses);
                    } else {
                        keycloakGroups = getKeycloakGroups(user);
                        models = convertToSoftwareGroupModel(customerLicenses);
                    }
                }
                maconomyId = (String) customerContactInfo.getOrDefault("customerMaconomyId", "");
            }
            renderRequest.setAttribute("keycloakGroups", keycloakGroups == null ? Collections.emptyList() : keycloakGroups);
            renderRequest.setAttribute("records", models == null ? Collections.emptyList() : models);
            renderRequest.setAttribute("customerInfo", customerInfo);
            renderRequest.setAttribute("maconomyId", maconomyId);
            renderRequest.setAttribute("filterSelection", selectedState);
            renderRequest.setAttribute("customerSelection", customerSelection);
            renderRequest.setAttribute("tabSelection", tabSelection);
        } catch (Exception e) {
            throw new PortletException(e);
        }
        super.render(renderRequest, renderResponse);
    }

    private Map<String, String> getKeycloakGroups(User user) throws Exception {

        Map<String, String> userInfo = keycloakUtils.getUserInfo(user.getEmailAddress());
        if (userInfo.isEmpty()) {return Collections.emptyMap();}
        return keycloakUtils.getUserGroups(userInfo.get("id"));

    }

    private void updateGroupMemberschip(User user, String groupName, boolean member) throws Exception {
        Map<String, String> userInfo = keycloakUtils.getUserInfo(user.getEmailAddress());
        if (userInfo.isEmpty()) return;
        String keycloakUserId = userInfo.get("id");
        String keycloakGroupId = keycloakUtils.getGroupIdentifier(groupName);

        if (member) {
            keycloakUtils.addUserGroup(keycloakUserId, keycloakGroupId);
        } else {
            keycloakUtils.removeUserGroup(keycloakUserId, keycloakGroupId);
        }

    }
    /**
     * Update user group memberschip
     */
    @SuppressWarnings("unused")
    public void updateUserGroup(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        String changedGroupName = actionRequest.getActionParameters().getValue("checkboxNames");
        if (changedGroupName == null) return;
        String changedGroupValue = actionRequest.getActionParameters().getValue(changedGroupName);
        if (changedGroupValue == null) return;

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
                .getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        updateGroupMemberschip(user, changedGroupName, Boolean.parseBoolean(changedGroupValue));

    }

    /**
     * Call sendLicenseFile action
     *
     * @param actionRequest  Filter action
     * @param actionResponse Filter response
     */
    @SuppressWarnings("unused")
    public void sendLicenseFiles(ActionRequest actionRequest, ActionResponse actionResponse) {

        String tabSelection =  ParamUtil.getString(actionRequest, "tabSelection", "softwareSuites");
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
            if (dataRequest.getStatus() == DataRequest.STATUS.PENDING ||
                dataRequest.getStatus() == DataRequest.STATUS.RUNNING) {
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

    private List<SoftwareSuite> convertToSoftwareSuiteModel(JSONArray customerData) throws ParseException {

        ArrayList<SoftwareSuite> suites = new ArrayList<>();
        for (int i = 0; i < customerData.length(); i++) {
            JSONObject suiteObject = customerData.getJSONObject(i);
            suites.add(convertToSuit(suiteObject));
        }
        return suites;
    }

    private List<SoftwareGroup> convertToSoftwareGroupModel(JSONArray customerData) throws ParseException {

        ArrayList<SoftwareGroup> groups = new ArrayList<>();
        for (int i = 0; i < customerData.length(); i++) {
            JSONObject suiteObject = customerData.getJSONObject(i);
            SoftwareSuite softwareSuite = convertToSuit(suiteObject);
            List<SoftwareSuiteSubscription> subscriptionList = softwareSuite.getSubscriptionList();
            if (subscriptionList.isEmpty()) continue;
            for (SoftwareSuiteSubscription subscription : subscriptionList) {
                for (String suiteGroup : subscription.getGroups()) {
                    SoftwareGroup softwareGroup = new SoftwareGroup(suiteGroup);
                    int index = groups.indexOf(softwareGroup);
                    if (index >= 0) {
                        softwareGroup = groups.get(index);
                    } else {
                        groups.add(softwareGroup);
                    }
                    softwareGroup.addPackageName(subscription.getServicePackageName());

                }
            }
        }
        return groups;
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
        String terminationDateString = subscriptionObject.getString("subscriptionTerminationDate", null);
        if (terminationDateString != null) subscription.setTerminationDate(dateFormat.parse(terminationDateString));
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

        JSONObject subscriptionPackage = subscriptionObject.getJSONObject("subscriptionPackage");
        if (subscriptionPackage != null) {
            subscription.setServicePackageName(subscriptionPackage.getString("packageName"));
            JSONArray packageGroupNames = subscriptionPackage.getJSONArray("packageGroupNames");
            if (packageGroupNames != null && packageGroupNames.length() > 0) {
                for (int i = 0; i < packageGroupNames.length(); i++) {
                    subscription.addGroup(packageGroupNames.getString(i));
                }
            }
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