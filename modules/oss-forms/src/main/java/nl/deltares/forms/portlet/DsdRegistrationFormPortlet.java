package nl.deltares.forms.portlet;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OrganizationConstants;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.DeltaresProduct;
import nl.deltares.portal.model.subscriptions.SubscriptionSelection;
import nl.deltares.portal.utils.CommerceUtils;
import nl.deltares.portal.utils.EmailSubscriptionUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.*;

import static nl.deltares.portal.utils.LocalizationUtils.getLocalizedValue;

/**
 * @author rooij_e
 */
@Component(
        configurationPid = OssConstants.DSD_REGISTRATIONFORM_CONFIGURATIONS_PID,
        immediate = true,
        property = {
                "javax.portlet.version=3.0",
                "com.liferay.portlet.display-category=OSS",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.header-portlet-javascript=/lib/dsd-registration.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/common.js",
                "com.liferay.portlet.header-portlet-javascript=/lib/commerce-utils.js",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=DsdRegistrationForm",
                "javax.portlet.init-param.config-template=/registration/configuration.jsp",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/registration/dsd_register.jsp",
                "javax.portlet.name=" + OssConstants.DSD_REGISTRATIONFORM,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.supported-locale=en",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DsdRegistrationFormPortlet extends MVCPortlet {

    @Reference
    private CommerceUtils _commerceUtils;

    @Reference
    private CPContentHelper _cpContentHelper;

    @Reference
    private CommerceOrderHttpHelper _commerceOrderHttpHelper;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    private static final Log LOG = LogFactoryUtil.getLog(DsdRegistrationFormPortlet.class);

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private EmailSubscriptionUtils subscriptionUtils;

    @Reference
    private CommerceTaxCalculation _commerceTaxCalculation;

    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        if (!user.isGuestUser()) {

            String domain = user.getEmailAddress().split("@")[1];
            try {
                List<AccountEntry> accounts = _commerceUtils.getAccountsByDomain(domain, themeDisplay.getCompanyId());
                if (accounts.isEmpty()) {
                    final AccountEntry personalAccount = _commerceUtils.getPersonalAccount(themeDisplay.getUser());
                    accounts = personalAccount == null ? Collections.emptyList() : Collections.singletonList(personalAccount);
                }

                request.setAttribute("accounts", convertToJson(accounts));
            } catch (Exception e) {
                SessionErrors.add(request, "registration-error", String.format("Error getting accounts for domain %s: %s", domain, e.getMessage()));
                request.setAttribute("accounts", JSONFactoryUtil.createJSONArray().toJSONString());
            }

            try {
                final Map<String, String> userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
                request.setAttribute("attributes", userAttributes);
            } catch (Exception e) {
                SessionErrors.add(request, "update-attributes-failed", "Error reading user attributes: " + e.getMessage());
                request.setAttribute("attributes", Collections.emptyMap());
            }

            final String language = themeDisplay.getLocale().getLanguage();
            try {
                DSDSiteConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());
                request.setAttribute("conditionsURL", getLocalizedValue(dsdConfig.conditionsURL(), language));
                request.setAttribute("privacyURL", getLocalizedValue(dsdConfig.privacyURL(), language));
                request.setAttribute("contactURL", getLocalizedValue(dsdConfig.contactURL(), language));
                request.setAttribute("eventId", dsdConfig.eventId());
                List<String> mailingIdsList = Arrays.asList(dsdConfig.mailingIds().split(";"));
                request.setAttribute("subscriptionSelection", getSubscriptionSelection(user.getEmailAddress(), mailingIdsList));
                request.setAttribute("subscribed", subscriptionUtils.isSubscribed(user.getEmailAddress(), mailingIdsList));
            } catch (Exception e) {
                SessionErrors.add(request, "registration-error", String.format("Error getting subscriptions for user %s: %s", user.getEmailAddress(), e.getMessage()));
                request.setAttribute("subscribed", false);
            }
            try {
                DsdRegistrationFormConfiguration dsdConfig = _configurationProvider.getGroupConfiguration(DsdRegistrationFormConfiguration.class, themeDisplay.getScopeGroupId());
                request.setAttribute("childHeaderText", getLocalizedValue(dsdConfig.childHeaderText(), language));
            } catch (Exception e) {
                LOG.warn("Error getting DsdRegistrationFormConfiguration: " + e.getMessage());
                request.setAttribute("childHeaderText", null);
            }

            try {
                CommerceOrder commerceOrder = _commerceOrderHttpHelper.getCurrentCommerceOrder(PortalUtil.getHttpServletRequest(request));
                if (commerceOrder != null) {
                    final CommerceContext commerceContext = (CommerceContext) request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);
                    final List<DeltaresProduct> deltaresProducts = _commerceUtils.commerceOrderItemsToDeltaresProducts(commerceOrder.getCommerceOrderItems(), commerceContext, themeDisplay.getLocale());
                    request.setAttribute("deltaresProducts", deltaresProducts);

                    request.setAttribute(CommerceWebKeys.COMMERCE_ORDER, commerceOrder);
                    final CommerceMoney taxAmount = _commerceTaxCalculation.getTaxAmount(commerceOrder, commerceContext.getCommerceCurrency());
                    final List<CommerceTaxValue> commerceTaxValues = _commerceTaxCalculation.getCommerceTaxValues(commerceOrder);

                }
                request.setAttribute(CommerceUtils.class.getName(), _commerceUtils);
                request.setAttribute(CPContentWebKeys.CP_CONTENT_HELPER, _cpContentHelper);
                request.setAttribute("CP_CONTENT_LIST_ENTRY_RENDERER_KEYS",
                        Collections.singletonMap("virtual", "checkout-entry-dsd"));

            } catch (PortalException e) {
                throw new RuntimeException(e);
            }
        }
        String action = ParamUtil.getString(request, "action");
        request.setAttribute("callerAction", action);


        request.setAttribute(ConfigurationProvider.class.getName(), _configurationProvider);

        //Set the proper renderer for product items.

        super.render(request, response);
    }

    private String convertToJson(List<AccountEntry> accounts) {

        final JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        for (AccountEntry account : accounts) {

            final JSONObject accountJson = JSONFactoryUtil.createJSONObject();
            accountJson.put("companyId", String.valueOf(account.getCompanyId()));
            accountJson.put("accountEntryId", String.valueOf(account.getAccountEntryId()));
            accountJson.put("domains", account.getDomains());
            accountJson.put("type", account.getType());
            accountJson.put(OrganizationConstants.ORG_NAME, account.getName());
            accountJson.put(OrganizationConstants.ORG_VAT, account.getTaxIdNumber());
            List<Address> addresses = AddressLocalServiceUtil.getAddresses(account.getCompanyId(), AccountEntry.class.getName(), account.getAccountEntryId());
            if (addresses.isEmpty() && account.getDefaultBillingAddressId() > 0) {
                final Address defaultAddress = AddressLocalServiceUtil.fetchAddress(account.getDefaultBillingAddressId());
                if (defaultAddress != null) addresses = Collections.singletonList(defaultAddress);
            }
            if (!addresses.isEmpty()) {
                final JSONArray addressesJson = JSONFactoryUtil.createJSONArray();
                accountJson.put("addresses", addressesJson);
                long defaultId = account.getDefaultBillingAddressId();

                for (Address address : addresses) {
                    final JSONObject addressJson = JSONFactoryUtil.createJSONObject();
                    addressJson.put("name", address.getName());
                    addressJson.put("addressId", address.getAddressId());
                    addressJson.put(OrganizationConstants.ORG_STREET, address.getStreet1());
                    addressJson.put(OrganizationConstants.ORG_POSTAL, address.getZip());
                    addressJson.put(OrganizationConstants.ORG_CITY, address.getCity());
                    addressJson.put(OrganizationConstants.ORG_COUNTRY_CODE, address.getCountry().getA2());
                    addressJson.put(OrganizationConstants.ORG_PHONE, address.getPhoneNumber());
                    addressJson.put("default", address.getAddressId() == defaultId);
                    addressesJson.put(addressJson);
                }
                accountJson.put("addresses", addressesJson);
            }

            //Get additional parameters
            accountJson.put(OrganizationConstants.ORG_WEBSITE, (String) account.getExpandoBridge().getAttribute("website", false));

            jsonArray.put(accountJson);
        }
        return jsonArray.toJSONString();
    }


    private List<SubscriptionSelection> getSubscriptionSelection(String email, List<String> configuredSubscriptionIds) {

        try {
            final List<SubscriptionSelection> subset = new ArrayList<>();
            final List<SubscriptionSelection> allSubscriptionSelections = subscriptionUtils.getSubscriptions(email);
            allSubscriptionSelections.forEach(s -> {
                if (configuredSubscriptionIds.contains(s.getId())) subset.add(s);
            });
            return subset;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}