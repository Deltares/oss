package nl.deltares.forms.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.configuration.DSDSiteConfiguration;
import nl.deltares.portal.model.impl.BusTransfer;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdTransferUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.text.SimpleDateFormat;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssFormPortletKeys.DSD_BUS_REGISTRATION_FORM,
                "mvc.command.name=/submit/transfer/form"
        },
        service = MVCActionCommand.class
)
public class SubmitBusRegistrationActionActionCommand extends BaseMVCActionCommand {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        long groupId = themeDisplay.getSiteGroupId();

        try {
            DSDSiteConfiguration configuration = _configurationProvider
                    .getGroupConfiguration(DSDSiteConfiguration.class, themeDisplay.getScopeGroupId());

            Event event = parserUtils.getEvent(groupId, String.valueOf(configuration.eventId()));

            event.getBusTransfers(themeDisplay.getLocale()).stream()
                    .forEach(busTransfer -> registerUser(actionRequest, event, user, busTransfer));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "registration-failed", e.getMessage());
            LOG.debug("Could not get configuration instance", e);
        }
    }

    private void registerUser(ActionRequest actionRequest, Event event, User user, BusTransfer busTransfer) {
        busTransfer.getTransferDates().forEach(date -> {

            String registrationParam = "registration_" + busTransfer.getResourceId() + "_" + DATE_FORMAT.format(date);
            String errorMessage = String.format("Could not register user [%s] for transfer [%s] on [%s]",
                    user.getEmailAddresses(), busTransfer.getResourceId(), date.toString());

            try {
                Registration registration = event.getRegistration(busTransfer.getResourceId(), actionRequest.getLocale());

                if (ParamUtil.getString(actionRequest, registrationParam).equals("true")) {
                    dsdTransferUtils.registerUser(user, registration, date);
                } else {
                    if (dsdTransferUtils.isUserRegisteredFor(user, registration, date)) {
                        dsdTransferUtils.unRegisterUser(user, registration, date);
                    }
                }
            } catch (Exception e) {
                SessionErrors.add(actionRequest, "registration-failed", e.getMessage());
                LOG.debug(errorMessage, e);
            }
        });
    }

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
