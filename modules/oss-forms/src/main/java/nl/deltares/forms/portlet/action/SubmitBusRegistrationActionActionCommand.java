package nl.deltares.forms.portlet.action;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.constants.OssConstants;
import nl.deltares.portal.model.impl.BusTransfer;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssConstants.DSD_BUS_REGISTRATION_FORM,
                "mvc.command.name=/submit/transfer/form"
        },
        service = MVCActionCommand.class
)
public class SubmitBusRegistrationActionActionCommand extends BaseMVCActionCommand {

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getSiteGroupId();

        try {
            final User registrationUser;
            final User user;
            if (isRegisterSomeoneElse(actionRequest)){
                registrationUser = themeDisplay.getUser();
                final String email = ParamUtil.getString(actionRequest, KeycloakUtils.ATTRIBUTES.email.name());
                final String firstName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.first_name.name());
                final String lastName = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.last_name.name());
                final String jobTitle = ParamUtil.getString(actionRequest,  KeycloakUtils.ATTRIBUTES.jobTitle.name());
                user = adminUtils.getOrCreateRegistrationUser(themeDisplay.getCompanyId(), registrationUser,
                        email, firstName, lastName, jobTitle, themeDisplay.getLocale());
            } else {
                registrationUser = null;
                user = themeDisplay.getUser();
            }
            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            Event event = parserUtils.getEvent(groupId, String.valueOf(configuration.eventId()), themeDisplay.getLocale());

            event.getBusTransfers(themeDisplay.getLocale())
                    .forEach(busTransfer -> registerUser(actionRequest, event, user, busTransfer, registrationUser));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "registration-failed", e.getMessage());
            LOG.debug("Could not get configuration instance", e);
        }
    }

    private boolean isRegisterSomeoneElse(ActionRequest actionRequest) {
        return Boolean.parseBoolean(ParamUtil.getString(actionRequest, "registration_other"));
    }

    private void registerUser(ActionRequest actionRequest, Event event, User user, BusTransfer busTransfer, User registrationUser) {

        String registrationParam = "registration_" + busTransfer.getResourceId();
        String errorMessage = String.format("Could not register user [%s] for transfer [%s] on [%s]",
                user.getEmailAddresses(), busTransfer.getResourceId(), busTransfer.getTransferDay());

        try {
            Registration registration = event.getRegistration(busTransfer.getResourceId(), actionRequest.getLocale());

            if (ParamUtil.getString(actionRequest, registrationParam).equals("true")) {
                dsdTransferUtils.registerUser(user, registration, registrationUser);
            } else {
                if (dsdTransferUtils.isUserRegisteredFor(user, registration)) {
                    dsdTransferUtils.unRegisterUser(user, registration);
                }
            }
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "registration-failed", e.getMessage());
            LOG.debug(errorMessage, e);
        }

    }

    @Reference
    AdminUtils adminUtils;

    @Reference
    DsdParserUtils parserUtils;

    private ConfigurationProvider _configurationProvider;

    @Reference
    protected void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        _configurationProvider = configurationProvider;
    }

    @Reference
    private DsdTransferUtils dsdTransferUtils;

    private static final Log LOG = LogFactoryUtil.getLog(SubmitBusRegistrationActionActionCommand.class);
}
