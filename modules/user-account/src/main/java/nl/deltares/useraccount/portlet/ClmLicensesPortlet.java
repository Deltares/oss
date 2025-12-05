package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.json.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;
import nl.deltares.useraccount.model.Asset;
import nl.deltares.useraccount.model.CustomerContact;
import nl.deltares.useraccount.model.SoftwareSuite;
import nl.deltares.useraccount.model.SoftwareSuiteSubscription;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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

    public ClmLicensesPortlet() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                .getAttribute(WebKeys.THEME_DISPLAY);

        try {
            List<SoftwareSuite> suites = convertToModel(getExampleData());
            renderRequest.setAttribute("records", suites);
        } catch (JSONException | ParseException e) {
            throw new PortletException(e);
        }
        super.render(renderRequest, renderResponse);
    }

    private List<SoftwareSuite> convertToModel(JSONArray exampleData) throws ParseException {

        ArrayList<SoftwareSuite> suites = new ArrayList<>();
        for (int i = 0; i < exampleData.length(); i++) {
            JSONObject suiteObject = exampleData.getJSONObject(i);
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

        JSONArray contactsArray = subscriptionObject.getJSONArray("subscriptionContact");
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

        contact.setContactName(contactObject.getString("customerContactName", null));
        contact.setContactSalutation(contactObject.getString("customerContactSalutation", null));
        contact.setContactEmail(contactObject.getString("customerContactEmail", null));
        return contact;
    }

    private JSONArray getExampleData() throws JSONException {

        String json = "[\n" +
                "  {\n" +
                "    \"softwareSuiteSubscriptions\": [\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 1,\n" +
                "          \"softwareProductLicenseUsed\": 1,\n" +
                "          \"softwareProductUpdatesAllowed\": false,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Foundations\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Foundations\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DFO\",\n" +
                "            \"softwareSuiteId\": 3,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Foundations Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DFO.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 3,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 29,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Lootens\",\n" +
                "            \"customerContactSalutation\": \"Mr.\",\n" +
                "            \"customerContactRemarks\": null,\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 133,\n" +
                "            \"customerContactMetaModificationDate\": \"2019-07-18T14:14:55.4677211\",\n" +
                "            \"customerContactMetaUserName\": \"vernij\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": null,\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Lootens\",\n" +
                "          \"customerContactSalutation\": \"Mr.\",\n" +
                "          \"customerContactRemarks\": null,\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 133,\n" +
                "          \"customerContactMetaModificationDate\": \"2019-07-18T14:14:55.4677211\",\n" +
                "          \"customerContactMetaUserName\": \"vernij\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 1,\n" +
                "            \"subscriptionAssetHardwareId\": \"28d2440113be\",\n" +
                "            \"subscriptionAssetType\": \"StandAlone\",\n" +
                "            \"subscriptionAssetServerName\": null,\n" +
                "            \"subscriptionAssetLoginUserName\": null,\n" +
                "            \"subscriptionAssetUri\": null,\n" +
                "            \"subscriptionAssetLoginPassword\": null,\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": null,\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 292,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2018-01-22T16:40:32.777\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"system\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 1,\n" +
                "        \"subscriptionLicenseUsed\": 1,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"17.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": false,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": \"Nee\",\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": null,\n" +
                "        \"subscriptionEndDate\": \"2018-03-14T00:00:00\",\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Expired\",\n" +
                "        \"subscriptionHasLicense\": false,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 29,\n" +
                "        \"subscriptionRemarks\": \"Student Jochem Lootens\",\n" +
                "        \"subscriptionPackageId\": null,\n" +
                "        \"subscriptionType\": \"NoSupportTemp\",\n" +
                "        \"subscriptionId\": 280,\n" +
                "        \"subscriptionMetaModificationDate\": \"2018-01-22T16:49:34.1577718\",\n" +
                "        \"subscriptionMetaUserName\": \"post-processor\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 99,\n" +
                "          \"softwareProductLicenseUsed\": 0,\n" +
                "          \"softwareProductUpdatesAllowed\": true,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Foundations\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Foundations\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DFO\",\n" +
                "            \"softwareSuiteId\": 3,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Foundations Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DFO.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 3,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 29,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [\n" +
                "          {\n" +
                "            \"supportProductSupportHoursUsed\": 0.0,\n" +
                "            \"supportProductSupportHours\": -1,\n" +
                "            \"supportProductHasPhoneSupport\": true,\n" +
                "            \"supportProductSupportLevelName\": \"Basic\",\n" +
                "            \"supportProductSupportLevelValue\": 1,\n" +
                "            \"supportProductSupportLevelId\": 2,\n" +
                "            \"supportProductRemarks\": null,\n" +
                "            \"supportProductName\": \"D-Foundations Educational\",\n" +
                "            \"supportProductProductCode\": \"SC.DFO.EDUC\",\n" +
                "            \"supportProductMaconomyId\": null,\n" +
                "            \"supportProductStatus\": \"Sellable\",\n" +
                "            \"supportProductId\": 31,\n" +
                "            \"supportProductMetaModificationDate\": \"2018-01-22T16:40:25.49\",\n" +
                "            \"supportProductMetaUserName\": \"system\",\n" +
                "            \"supportProductEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Lefers\",\n" +
                "            \"customerContactSalutation\": \"Ms.\",\n" +
                "            \"customerContactRemarks\": null,\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 135,\n" +
                "            \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "            \"customerContactMetaUserName\": \"steenweg\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": {\n" +
                "          \"packageLicenseCount\": null,\n" +
                "          \"packageRemoteDesktopAccess\": true,\n" +
                "          \"packageUpdatesAllowed\": true,\n" +
                "          \"packageSupportHours\": null,\n" +
                "          \"packageHasPhoneSupport\": true,\n" +
                "          \"packageName\": \"D-Foundations Educational Package\",\n" +
                "          \"packageDescription\": null,\n" +
                "          \"packagePeriod\": 12,\n" +
                "          \"packageSoftwareProductId\": 29,\n" +
                "          \"packageMaconomyId\": null,\n" +
                "          \"packageStatus\": \"Sellable\",\n" +
                "          \"packageId\": 29,\n" +
                "          \"packageMetaModificationDate\": \"2018-01-22T16:40:30.09\",\n" +
                "          \"packageMetaUserName\": \"system\",\n" +
                "          \"packageEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Lefers\",\n" +
                "          \"customerContactSalutation\": \"Ms.\",\n" +
                "          \"customerContactRemarks\": null,\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 135,\n" +
                "          \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "          \"customerContactMetaUserName\": \"steenweg\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [],\n" +
                "        \"subscriptionLicenseCount\": 99,\n" +
                "        \"subscriptionLicenseUsed\": 0,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"16.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": true,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": null,\n" +
                "        \"subscriptionEndDate\": null,\n" +
                "        \"subscriptionTerminationDate\": \"2016-12-31T00:00:00\",\n" +
                "        \"subscriptionState\": \"Terminated\",\n" +
                "        \"subscriptionHasLicense\": true,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 29,\n" +
                "        \"subscriptionRemarks\": \"Opgezegd; Voormalig SCIA Klant; Licentie was niet goed. Nieuwe gestuurd 10 feb voor 99 gebruikers i.p.v. 1 gebruiker. 12-01-2018 hebben zich vergist in opzeggen. Opnieuw offerte gestuurd.\\r\\n\",\n" +
                "        \"subscriptionPackageId\": 29,\n" +
                "        \"subscriptionType\": \"OngoingSupportAutoRenewal\",\n" +
                "        \"subscriptionId\": 3123,\n" +
                "        \"subscriptionMetaModificationDate\": \"2018-02-06T13:54:34.3858102\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 99,\n" +
                "          \"softwareProductLicenseUsed\": 99,\n" +
                "          \"softwareProductUpdatesAllowed\": true,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Foundations\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Foundations\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DFO\",\n" +
                "            \"softwareSuiteId\": 3,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Foundations Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DFO.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 3,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 29,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [\n" +
                "          {\n" +
                "            \"supportProductSupportHoursUsed\": 0.0,\n" +
                "            \"supportProductSupportHours\": 0,\n" +
                "            \"supportProductHasPhoneSupport\": true,\n" +
                "            \"supportProductSupportLevelName\": \"Basic\",\n" +
                "            \"supportProductSupportLevelValue\": 1,\n" +
                "            \"supportProductSupportLevelId\": 2,\n" +
                "            \"supportProductRemarks\": null,\n" +
                "            \"supportProductName\": \"D-Foundations Educational\",\n" +
                "            \"supportProductProductCode\": \"SC.DFO.EDUC\",\n" +
                "            \"supportProductMaconomyId\": null,\n" +
                "            \"supportProductStatus\": \"Sellable\",\n" +
                "            \"supportProductId\": 31,\n" +
                "            \"supportProductMetaModificationDate\": \"2018-01-22T16:40:25.49\",\n" +
                "            \"supportProductMetaUserName\": \"system\",\n" +
                "            \"supportProductEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Lefers\",\n" +
                "            \"customerContactSalutation\": \"Ms.\",\n" +
                "            \"customerContactRemarks\": null,\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 135,\n" +
                "            \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "            \"customerContactMetaUserName\": \"steenweg\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": {\n" +
                "          \"packageLicenseCount\": null,\n" +
                "          \"packageRemoteDesktopAccess\": true,\n" +
                "          \"packageUpdatesAllowed\": true,\n" +
                "          \"packageSupportHours\": null,\n" +
                "          \"packageHasPhoneSupport\": true,\n" +
                "          \"packageName\": \"D-Foundations Educational Package\",\n" +
                "          \"packageDescription\": null,\n" +
                "          \"packagePeriod\": 12,\n" +
                "          \"packageSoftwareProductId\": 29,\n" +
                "          \"packageMaconomyId\": null,\n" +
                "          \"packageStatus\": \"Sellable\",\n" +
                "          \"packageId\": 29,\n" +
                "          \"packageMetaModificationDate\": \"2018-01-22T16:40:30.09\",\n" +
                "          \"packageMetaUserName\": \"system\",\n" +
                "          \"packageEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Lefers\",\n" +
                "          \"customerContactSalutation\": \"Ms.\",\n" +
                "          \"customerContactRemarks\": null,\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 135,\n" +
                "          \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "          \"customerContactMetaUserName\": \"steenweg\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 99,\n" +
                "            \"subscriptionAssetHardwareId\": \"005056b47ba7\",\n" +
                "            \"subscriptionAssetType\": \"Server\",\n" +
                "            \"subscriptionAssetServerName\": \"FLEXLM1\",\n" +
                "            \"subscriptionAssetLoginUserName\": null,\n" +
                "            \"subscriptionAssetUri\": null,\n" +
                "            \"subscriptionAssetLoginPassword\": null,\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": null,\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 291,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2018-01-22T16:40:32.777\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"system\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 99,\n" +
                "        \"subscriptionLicenseUsed\": 99,\n" +
                "        \"subscriptionSupportHours\": 0,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": true,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": true,\n" +
                "        \"subscriptionLatestVersion\": \"25.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": true,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2017-01-01T00:00:00\",\n" +
                "        \"subscriptionEndDate\": null,\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Active\",\n" +
                "        \"subscriptionHasLicense\": true,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 29,\n" +
                "        \"subscriptionRemarks\": \"Hadden per vergissing opgezegd. Met terugwerkende kracht geactiveerd.\\n\",\n" +
                "        \"subscriptionPackageId\": 29,\n" +
                "        \"subscriptionType\": \"OngoingSupportAutoRenewal\",\n" +
                "        \"subscriptionId\": 4869,\n" +
                "        \"subscriptionMetaModificationDate\": \"2018-02-06T14:05:59.0612144\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      }\n" +
                "    ],\n" +
                "    \"softwareSuiteName\": \"D-Foundations\",\n" +
                "    \"softwareSuiteRemarks\": null,\n" +
                "    \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "    \"softwareSuiteCode\": \"DFO\",\n" +
                "    \"softwareSuiteId\": 3,\n" +
                "    \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "    \"softwareSuiteMetaUserName\": \"system\",\n" +
                "    \"softwareSuiteEntryIsActive\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"softwareSuiteSubscriptions\": [\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 99,\n" +
                "          \"softwareProductLicenseUsed\": 0,\n" +
                "          \"softwareProductUpdatesAllowed\": true,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Geo Stability\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DST\",\n" +
                "            \"softwareSuiteId\": 5,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DST.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 5,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 40,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [\n" +
                "          {\n" +
                "            \"supportProductSupportHoursUsed\": 0.0,\n" +
                "            \"supportProductSupportHours\": -1,\n" +
                "            \"supportProductHasPhoneSupport\": true,\n" +
                "            \"supportProductSupportLevelName\": \"Basic\",\n" +
                "            \"supportProductSupportLevelValue\": 1,\n" +
                "            \"supportProductSupportLevelId\": 2,\n" +
                "            \"supportProductRemarks\": null,\n" +
                "            \"supportProductName\": \"D-Geo Stability Educational\",\n" +
                "            \"supportProductProductCode\": \"SC.DST.EDUC\",\n" +
                "            \"supportProductMaconomyId\": null,\n" +
                "            \"supportProductStatus\": \"Sellable\",\n" +
                "            \"supportProductId\": 42,\n" +
                "            \"supportProductMetaModificationDate\": \"2018-01-22T16:40:25.49\",\n" +
                "            \"supportProductMetaUserName\": \"system\",\n" +
                "            \"supportProductEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Lefers\",\n" +
                "            \"customerContactSalutation\": \"Ms.\",\n" +
                "            \"customerContactRemarks\": null,\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 135,\n" +
                "            \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "            \"customerContactMetaUserName\": \"steenweg\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": {\n" +
                "          \"packageLicenseCount\": null,\n" +
                "          \"packageRemoteDesktopAccess\": true,\n" +
                "          \"packageUpdatesAllowed\": true,\n" +
                "          \"packageSupportHours\": null,\n" +
                "          \"packageHasPhoneSupport\": true,\n" +
                "          \"packageName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"packageDescription\": null,\n" +
                "          \"packagePeriod\": 12,\n" +
                "          \"packageSoftwareProductId\": 40,\n" +
                "          \"packageMaconomyId\": null,\n" +
                "          \"packageStatus\": \"Sellable\",\n" +
                "          \"packageId\": 40,\n" +
                "          \"packageMetaModificationDate\": \"2018-01-22T16:40:30.09\",\n" +
                "          \"packageMetaUserName\": \"system\",\n" +
                "          \"packageEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Lefers\",\n" +
                "          \"customerContactSalutation\": \"Ms.\",\n" +
                "          \"customerContactRemarks\": null,\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 135,\n" +
                "          \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "          \"customerContactMetaUserName\": \"steenweg\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [],\n" +
                "        \"subscriptionLicenseCount\": 99,\n" +
                "        \"subscriptionLicenseUsed\": 0,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"16.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": true,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": null,\n" +
                "        \"subscriptionEndDate\": null,\n" +
                "        \"subscriptionTerminationDate\": \"2016-12-31T00:00:00\",\n" +
                "        \"subscriptionState\": \"Terminated\",\n" +
                "        \"subscriptionHasLicense\": true,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 40,\n" +
                "        \"subscriptionRemarks\": \"Opgezegd; Voormalig SCIA Klant; Licentie was niet goed. Nieuwe gestuurd 10 feb voor 99 gebruikers i.p.v. 1 gebruiker. 12-01-2018 hebben zich vergist in opzeggen. Opnieuw offerte gestuurd.\\r\\n\",\n" +
                "        \"subscriptionPackageId\": 40,\n" +
                "        \"subscriptionType\": \"OngoingSupportAutoRenewal\",\n" +
                "        \"subscriptionId\": 3124,\n" +
                "        \"subscriptionMetaModificationDate\": \"2018-02-06T13:56:00.003102\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 99,\n" +
                "          \"softwareProductLicenseUsed\": 99,\n" +
                "          \"softwareProductUpdatesAllowed\": true,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Geo Stability\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DST\",\n" +
                "            \"softwareSuiteId\": 5,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DST.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 5,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 40,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [\n" +
                "          {\n" +
                "            \"supportProductSupportHoursUsed\": 0.0,\n" +
                "            \"supportProductSupportHours\": 0,\n" +
                "            \"supportProductHasPhoneSupport\": true,\n" +
                "            \"supportProductSupportLevelName\": \"Basic\",\n" +
                "            \"supportProductSupportLevelValue\": 1,\n" +
                "            \"supportProductSupportLevelId\": 2,\n" +
                "            \"supportProductRemarks\": null,\n" +
                "            \"supportProductName\": \"D-Geo Stability Educational\",\n" +
                "            \"supportProductProductCode\": \"SC.DST.EDUC\",\n" +
                "            \"supportProductMaconomyId\": null,\n" +
                "            \"supportProductStatus\": \"Sellable\",\n" +
                "            \"supportProductId\": 42,\n" +
                "            \"supportProductMetaModificationDate\": \"2018-01-22T16:40:25.49\",\n" +
                "            \"supportProductMetaUserName\": \"system\",\n" +
                "            \"supportProductEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Lefers\",\n" +
                "            \"customerContactSalutation\": \"Ms.\",\n" +
                "            \"customerContactRemarks\": null,\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 135,\n" +
                "            \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "            \"customerContactMetaUserName\": \"steenweg\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": {\n" +
                "          \"packageLicenseCount\": null,\n" +
                "          \"packageRemoteDesktopAccess\": true,\n" +
                "          \"packageUpdatesAllowed\": true,\n" +
                "          \"packageSupportHours\": null,\n" +
                "          \"packageHasPhoneSupport\": true,\n" +
                "          \"packageName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"packageDescription\": null,\n" +
                "          \"packagePeriod\": 12,\n" +
                "          \"packageSoftwareProductId\": 40,\n" +
                "          \"packageMaconomyId\": null,\n" +
                "          \"packageStatus\": \"Sellable\",\n" +
                "          \"packageId\": 40,\n" +
                "          \"packageMetaModificationDate\": \"2018-01-22T16:40:30.09\",\n" +
                "          \"packageMetaUserName\": \"system\",\n" +
                "          \"packageEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Lefers\",\n" +
                "          \"customerContactSalutation\": \"Ms.\",\n" +
                "          \"customerContactRemarks\": null,\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 135,\n" +
                "          \"customerContactMetaModificationDate\": \"2018-12-12T11:47:39.8597806\",\n" +
                "          \"customerContactMetaUserName\": \"steenweg\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 99,\n" +
                "            \"subscriptionAssetHardwareId\": \"005056b47ba7\",\n" +
                "            \"subscriptionAssetType\": \"Server\",\n" +
                "            \"subscriptionAssetServerName\": \"FLEXLM1\",\n" +
                "            \"subscriptionAssetLoginUserName\": null,\n" +
                "            \"subscriptionAssetUri\": null,\n" +
                "            \"subscriptionAssetLoginPassword\": null,\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": null,\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 291,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2018-01-22T16:40:32.777\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"system\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 99,\n" +
                "        \"subscriptionLicenseUsed\": 99,\n" +
                "        \"subscriptionSupportHours\": 0,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": true,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": true,\n" +
                "        \"subscriptionLatestVersion\": \"18.2.2\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": true,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2017-01-01T00:00:00\",\n" +
                "        \"subscriptionEndDate\": null,\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Active\",\n" +
                "        \"subscriptionHasLicense\": true,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 40,\n" +
                "        \"subscriptionRemarks\": \"Hadden per vergissing opgezegd. Met terugwerkende kracht geactiveerd.\\n\",\n" +
                "        \"subscriptionPackageId\": 40,\n" +
                "        \"subscriptionType\": \"OngoingSupportAutoRenewal\",\n" +
                "        \"subscriptionId\": 4872,\n" +
                "        \"subscriptionMetaModificationDate\": \"2018-02-06T14:06:28.1628964\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 1,\n" +
                "          \"softwareProductLicenseUsed\": 1,\n" +
                "          \"softwareProductUpdatesAllowed\": false,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Geo Stability\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DST\",\n" +
                "            \"softwareSuiteId\": 5,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DST.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 5,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 40,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Hogeweij\",\n" +
                "            \"customerContactSalutation\": \"Mr.\",\n" +
                "            \"customerContactRemarks\": \"\",\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 11496,\n" +
                "            \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "            \"customerContactMetaUserName\": \"kieb\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": null,\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Hogeweij\",\n" +
                "          \"customerContactSalutation\": \"Mr.\",\n" +
                "          \"customerContactRemarks\": \"\",\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 11496,\n" +
                "          \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "          \"customerContactMetaUserName\": \"kieb\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 1,\n" +
                "            \"subscriptionAssetHardwareId\": \"ANY\",\n" +
                "            \"subscriptionAssetType\": \"StandAlone\",\n" +
                "            \"subscriptionAssetServerName\": \"\",\n" +
                "            \"subscriptionAssetLoginUserName\": \"\",\n" +
                "            \"subscriptionAssetUri\": \"\",\n" +
                "            \"subscriptionAssetLoginPassword\": \"\",\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": \"Asset voor tijdelijke COVID-19 licenties\",\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 12499,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2021-05-31T10:51:02.20116\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"borsboom\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 1,\n" +
                "        \"subscriptionLicenseUsed\": 1,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"18.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": false,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2020-03-24T00:00:00\",\n" +
                "        \"subscriptionEndDate\": \"2020-06-15T10:00:00\",\n" +
                "        \"subscriptionTerminationDate\": \"2020-06-21T00:00:00\",\n" +
                "        \"subscriptionState\": \"Terminated\",\n" +
                "        \"subscriptionHasLicense\": false,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 40,\n" +
                "        \"subscriptionRemarks\": \"I.v.m.  Corona (COVID-19).\",\n" +
                "        \"subscriptionPackageId\": null,\n" +
                "        \"subscriptionType\": \"NoSupportTemp\",\n" +
                "        \"subscriptionId\": 16592,\n" +
                "        \"subscriptionMetaModificationDate\": \"2020-06-22T15:21:21.2473158\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 1,\n" +
                "          \"softwareProductLicenseUsed\": 1,\n" +
                "          \"softwareProductUpdatesAllowed\": false,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Geo Stability\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DST\",\n" +
                "            \"softwareSuiteId\": 5,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DST.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 5,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 40,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Hogeweij\",\n" +
                "            \"customerContactSalutation\": \"Mr.\",\n" +
                "            \"customerContactRemarks\": \"\",\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 11496,\n" +
                "            \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "            \"customerContactMetaUserName\": \"kieb\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": null,\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Hogeweij\",\n" +
                "          \"customerContactSalutation\": \"Mr.\",\n" +
                "          \"customerContactRemarks\": \"\",\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 11496,\n" +
                "          \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "          \"customerContactMetaUserName\": \"kieb\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 1,\n" +
                "            \"subscriptionAssetHardwareId\": \"ANY\",\n" +
                "            \"subscriptionAssetType\": \"StandAlone\",\n" +
                "            \"subscriptionAssetServerName\": \"\",\n" +
                "            \"subscriptionAssetLoginUserName\": \"\",\n" +
                "            \"subscriptionAssetUri\": \"\",\n" +
                "            \"subscriptionAssetLoginPassword\": \"\",\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": \"Asset voor tijdelijke COVID-19 licenties\",\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 12499,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2021-05-31T10:51:02.20116\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"borsboom\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 1,\n" +
                "        \"subscriptionLicenseUsed\": 1,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"18.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": false,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2020-06-22T00:00:00\",\n" +
                "        \"subscriptionEndDate\": \"2020-08-31T10:00:00\",\n" +
                "        \"subscriptionTerminationDate\": \"2020-12-02T00:00:00\",\n" +
                "        \"subscriptionState\": \"Terminated\",\n" +
                "        \"subscriptionHasLicense\": false,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 40,\n" +
                "        \"subscriptionRemarks\": \"I.v.m.  Corona (COVID-19).\",\n" +
                "        \"subscriptionPackageId\": null,\n" +
                "        \"subscriptionType\": \"NoSupportTemp\",\n" +
                "        \"subscriptionId\": 16779,\n" +
                "        \"subscriptionMetaModificationDate\": \"2020-12-03T16:13:53.6257117\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 1,\n" +
                "          \"softwareProductLicenseUsed\": 1,\n" +
                "          \"softwareProductUpdatesAllowed\": false,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-Geo Stability\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"DST\",\n" +
                "            \"softwareSuiteId\": 5,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "            \"softwareSuiteMetaUserName\": \"system\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"D-Geo Stability Educational Package\",\n" +
                "          \"softwareProductProductCode\": \"DST.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 5,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 40,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"Hogeweij\",\n" +
                "            \"customerContactSalutation\": \"Mr.\",\n" +
                "            \"customerContactRemarks\": \"\",\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 11496,\n" +
                "            \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "            \"customerContactMetaUserName\": \"kieb\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": null,\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"Hogeweij\",\n" +
                "          \"customerContactSalutation\": \"Mr.\",\n" +
                "          \"customerContactRemarks\": \"\",\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 11496,\n" +
                "          \"customerContactMetaModificationDate\": \"2020-03-24T16:07:32.2469794\",\n" +
                "          \"customerContactMetaUserName\": \"kieb\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 1,\n" +
                "            \"subscriptionAssetHardwareId\": \"ANY\",\n" +
                "            \"subscriptionAssetType\": \"StandAlone\",\n" +
                "            \"subscriptionAssetServerName\": \"\",\n" +
                "            \"subscriptionAssetLoginUserName\": \"\",\n" +
                "            \"subscriptionAssetUri\": \"\",\n" +
                "            \"subscriptionAssetLoginPassword\": \"\",\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": \"Asset voor tijdelijke COVID-19 licenties\",\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 12499,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2021-05-31T10:51:02.20116\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"borsboom\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 1,\n" +
                "        \"subscriptionLicenseUsed\": 1,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"18.1\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": false,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2020-12-03T00:00:00\",\n" +
                "        \"subscriptionEndDate\": \"2021-03-31T10:00:00\",\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Expired\",\n" +
                "        \"subscriptionHasLicense\": false,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 40,\n" +
                "        \"subscriptionRemarks\": \"I.v.m.  Corona (COVID-19).\",\n" +
                "        \"subscriptionPackageId\": null,\n" +
                "        \"subscriptionType\": \"NoSupportTemp\",\n" +
                "        \"subscriptionId\": 27068,\n" +
                "        \"subscriptionMetaModificationDate\": \"2020-12-03T16:13:53.5598653\",\n" +
                "        \"subscriptionMetaUserName\": \"kieb\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      }\n" +
                "    ],\n" +
                "    \"softwareSuiteName\": \"D-Geo Stability\",\n" +
                "    \"softwareSuiteRemarks\": null,\n" +
                "    \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "    \"softwareSuiteCode\": \"DST\",\n" +
                "    \"softwareSuiteId\": 5,\n" +
                "    \"softwareSuiteMetaModificationDate\": \"2018-01-22T16:40:24.567\",\n" +
                "    \"softwareSuiteMetaUserName\": \"system\",\n" +
                "    \"softwareSuiteEntryIsActive\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"softwareSuiteSubscriptions\": [\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 500,\n" +
                "          \"softwareProductLicenseUsed\": 500,\n" +
                "          \"softwareProductUpdatesAllowed\": true,\n" +
                "          \"softwareProductRemoteDesktopAccess\": false,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-HYDRO 1D2D / Delft3D FM 1D2D\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-HYDRO 1D2D / Delft3D FM 1D2D\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"SBK\",\n" +
                "            \"softwareSuiteId\": 15,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2023-11-23T14:47:08.384232\",\n" +
                "            \"softwareSuiteMetaUserName\": \"wit_sy\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"SOBEK Educational\",\n" +
                "          \"softwareProductProductCode\": \"SBK.EDUC\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 15,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 4,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [\n" +
                "          {\n" +
                "            \"supportProductSupportHoursUsed\": 0.0,\n" +
                "            \"supportProductSupportHours\": 0,\n" +
                "            \"supportProductHasPhoneSupport\": false,\n" +
                "            \"supportProductSupportLevelName\": \"None\",\n" +
                "            \"supportProductSupportLevelValue\": 0,\n" +
                "            \"supportProductSupportLevelId\": 1,\n" +
                "            \"supportProductRemarks\": null,\n" +
                "            \"supportProductName\": \"SOBEK Educational Distribution Package\",\n" +
                "            \"supportProductProductCode\": \"SC.SBK.EDUC.DPC\",\n" +
                "            \"supportProductMaconomyId\": null,\n" +
                "            \"supportProductStatus\": \"Sellable\",\n" +
                "            \"supportProductId\": 11,\n" +
                "            \"supportProductMetaModificationDate\": \"2018-01-22T16:40:25.49\",\n" +
                "            \"supportProductMetaUserName\": \"system\",\n" +
                "            \"supportProductEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"ICTO\",\n" +
                "            \"customerContactSalutation\": \"Mr. / Ms. \",\n" +
                "            \"customerContactRemarks\": \"Was rg.folgers@windesheim.nl\",\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 134,\n" +
                "            \"customerContactMetaModificationDate\": \"2021-12-17T16:23:02.6401177\",\n" +
                "            \"customerContactMetaUserName\": \"kieb\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": {\n" +
                "          \"packageLicenseCount\": null,\n" +
                "          \"packageRemoteDesktopAccess\": true,\n" +
                "          \"packageUpdatesAllowed\": true,\n" +
                "          \"packageSupportHours\": null,\n" +
                "          \"packageHasPhoneSupport\": true,\n" +
                "          \"packageName\": \"SOBEK Educational Distribution Package\",\n" +
                "          \"packageDescription\": null,\n" +
                "          \"packagePeriod\": 12,\n" +
                "          \"packageSoftwareProductId\": 4,\n" +
                "          \"packageMaconomyId\": null,\n" +
                "          \"packageStatus\": \"Sellable\",\n" +
                "          \"packageId\": 11,\n" +
                "          \"packageMetaModificationDate\": \"2024-09-24T09:54:22.6234214\",\n" +
                "          \"packageMetaUserName\": \"wit_sy\",\n" +
                "          \"packageEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"ICTO\",\n" +
                "          \"customerContactSalutation\": \"Mr. / Ms. \",\n" +
                "          \"customerContactRemarks\": \"Was rg.folgers@windesheim.nl\",\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 134,\n" +
                "          \"customerContactMetaModificationDate\": \"2021-12-17T16:23:02.6401177\",\n" +
                "          \"customerContactMetaUserName\": \"kieb\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 500,\n" +
                "            \"subscriptionAssetHardwareId\": \"005056b47ba7\",\n" +
                "            \"subscriptionAssetType\": \"Server\",\n" +
                "            \"subscriptionAssetServerName\": \"FLEXLM1\",\n" +
                "            \"subscriptionAssetLoginUserName\": null,\n" +
                "            \"subscriptionAssetUri\": null,\n" +
                "            \"subscriptionAssetLoginPassword\": null,\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": null,\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 291,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2018-01-22T16:40:32.777\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"system\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 500,\n" +
                "        \"subscriptionLicenseUsed\": 500,\n" +
                "        \"subscriptionSupportHours\": 0,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": true,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": true,\n" +
                "        \"subscriptionLatestVersion\": \"2026.01\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": true,\n" +
                "        \"subscriptionRemoteDesktopAccess\": false,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": null,\n" +
                "        \"subscriptionEndDate\": null,\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Active\",\n" +
                "        \"subscriptionHasLicense\": true,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 4,\n" +
                "        \"subscriptionRemarks\": \"Dijk: 11-10-16 opgevraagd per mail.\\r\\n\",\n" +
                "        \"subscriptionPackageId\": 11,\n" +
                "        \"subscriptionType\": \"OngoingSupportAutoRenewal\",\n" +
                "        \"subscriptionId\": 3122,\n" +
                "        \"subscriptionMetaModificationDate\": \"2023-08-30T10:10:18.146087\",\n" +
                "        \"subscriptionMetaUserName\": \"steenweg\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      },\n" +
                "      {\n" +
                "        \"subscriptionSoftwareProduct\": {\n" +
                "          \"softwareProductLicenseCount\": 1,\n" +
                "          \"softwareProductLicenseUsed\": 1,\n" +
                "          \"softwareProductUpdatesAllowed\": false,\n" +
                "          \"softwareProductRemoteDesktopAccess\": true,\n" +
                "          \"softwareProductSoftwareSuiteName\": \"D-HYDRO 1D2D / Delft3D FM 1D2D\",\n" +
                "          \"softwareProductSoftwareSuite\": {\n" +
                "            \"softwareSuiteName\": \"D-HYDRO 1D2D / Delft3D FM 1D2D\",\n" +
                "            \"softwareSuiteRemarks\": null,\n" +
                "            \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "            \"softwareSuiteCode\": \"SBK\",\n" +
                "            \"softwareSuiteId\": 15,\n" +
                "            \"softwareSuiteMetaModificationDate\": \"2023-11-23T14:47:08.384232\",\n" +
                "            \"softwareSuiteMetaUserName\": \"wit_sy\",\n" +
                "            \"softwareSuiteEntryIsActive\": true\n" +
                "          },\n" +
                "          \"softwareProductStatus\": \"Sellable\",\n" +
                "          \"softwareProductName\": \"SOBEK Full\",\n" +
                "          \"softwareProductProductCode\": \"SBK.FULL\",\n" +
                "          \"softwareProductMaconomyId\": null,\n" +
                "          \"softwareProductSoftwareSuiteId\": 15,\n" +
                "          \"softwareProductRemarks\": null,\n" +
                "          \"softwareProductId\": 1,\n" +
                "          \"softwareProductMetaModificationDate\": \"2018-01-22T16:40:25.047\",\n" +
                "          \"softwareProductMetaUserName\": \"system\",\n" +
                "          \"softwareProductEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionSupportProducts\": [],\n" +
                "        \"subscriptionCustomerContacts\": [\n" +
                "          {\n" +
                "            \"customerContactName\": \"ICTO\",\n" +
                "            \"customerContactSalutation\": \"Mr. / Ms. \",\n" +
                "            \"customerContactRemarks\": \"Was rg.folgers@windesheim.nl\",\n" +
                "            \"customerContactCustomerId\": 69,\n" +
                "            \"customerContactId\": 134,\n" +
                "            \"customerContactMetaModificationDate\": \"2021-12-17T16:23:02.6401177\",\n" +
                "            \"customerContactMetaUserName\": \"kieb\",\n" +
                "            \"customerContactEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionPackage\": null,\n" +
                "        \"subscriptionAgent\": null,\n" +
                "        \"subscriptionContact\": {\n" +
                "          \"customerContactName\": \"ICTO\",\n" +
                "          \"customerContactSalutation\": \"Mr. / Ms. \",\n" +
                "          \"customerContactRemarks\": \"Was rg.folgers@windesheim.nl\",\n" +
                "          \"customerContactCustomerId\": 69,\n" +
                "          \"customerContactId\": 134,\n" +
                "          \"customerContactMetaModificationDate\": \"2021-12-17T16:23:02.6401177\",\n" +
                "          \"customerContactMetaUserName\": \"kieb\",\n" +
                "          \"customerContactEntryIsActive\": true\n" +
                "        },\n" +
                "        \"subscriptionAssets\": [\n" +
                "          {\n" +
                "            \"subscriptionAssetUserCount\": 1,\n" +
                "            \"subscriptionAssetHardwareId\": \"ANY\",\n" +
                "            \"subscriptionAssetType\": \"StandAlone\",\n" +
                "            \"subscriptionAssetServerName\": \"\",\n" +
                "            \"subscriptionAssetLoginUserName\": \"\",\n" +
                "            \"subscriptionAssetUri\": \"\",\n" +
                "            \"subscriptionAssetLoginPassword\": \"\",\n" +
                "            \"subscriptionAssetIsDeprecated\": false,\n" +
                "            \"subscriptionAssetRemarks\": \"Asset voor tijdelijke COVID-19 licenties\",\n" +
                "            \"subscriptionAssetCustomerId\": 69,\n" +
                "            \"subscriptionAssetId\": 12499,\n" +
                "            \"subscriptionAssetMetaModificationDate\": \"2021-05-31T10:51:02.20116\",\n" +
                "            \"subscriptionAssetMetaUserName\": \"borsboom\",\n" +
                "            \"subscriptionAssetEntryIsActive\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"subscriptionLicenseCount\": 1,\n" +
                "        \"subscriptionLicenseUsed\": 1,\n" +
                "        \"subscriptionSupportHours\": -1,\n" +
                "        \"subscriptionSupportHoursUsed\": 0.0,\n" +
                "        \"subscriptionHasSupport\": false,\n" +
                "        \"subscriptionShouldCustomerCurrentlyReceiveUpdates\": false,\n" +
                "        \"subscriptionLatestVersion\": \"2024.01\",\n" +
                "        \"subscriptionIsBetaVersion\": false,\n" +
                "        \"subscriptionUpdatesAllowed\": false,\n" +
                "        \"subscriptionRemoteDesktopAccess\": true,\n" +
                "        \"subscriptionMaconomyId\": null,\n" +
                "        \"subscriptionAgentId\": null,\n" +
                "        \"subscriptionBundleId\": null,\n" +
                "        \"subscriptionStartDate\": \"2023-12-22T00:00:00\",\n" +
                "        \"subscriptionEndDate\": \"2024-02-15T00:00:00\",\n" +
                "        \"subscriptionTerminationDate\": null,\n" +
                "        \"subscriptionState\": \"Expired\",\n" +
                "        \"subscriptionHasLicense\": false,\n" +
                "        \"subscriptionCustomerId\": 69,\n" +
                "        \"subscriptionSoftwareProductId\": 1,\n" +
                "        \"subscriptionRemarks\": \"\",\n" +
                "        \"subscriptionPackageId\": null,\n" +
                "        \"subscriptionType\": \"NoSupportTemp\",\n" +
                "        \"subscriptionId\": 48151,\n" +
                "        \"subscriptionMetaModificationDate\": \"2023-12-22T15:06:00.1093321\",\n" +
                "        \"subscriptionMetaUserName\": \"wit_sy\",\n" +
                "        \"subscriptionEntryIsActive\": true\n" +
                "      }\n" +
                "    ],\n" +
                "    \"softwareSuiteName\": \"D-HYDRO 1D2D / Delft3D FM 1D2D\",\n" +
                "    \"softwareSuiteRemarks\": null,\n" +
                "    \"softwareSuiteUsesLicenseFiles\": true,\n" +
                "    \"softwareSuiteCode\": \"SBK\",\n" +
                "    \"softwareSuiteId\": 15,\n" +
                "    \"softwareSuiteMetaModificationDate\": \"2023-11-23T14:47:08.384232\",\n" +
                "    \"softwareSuiteMetaUserName\": \"wit_sy\",\n" +
                "    \"softwareSuiteEntryIsActive\": true\n" +
                "  }" +
                "]";

        return JSONFactoryUtil.createJSONArray(json);
    }

}