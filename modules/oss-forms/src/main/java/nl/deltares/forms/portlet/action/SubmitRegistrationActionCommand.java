package nl.deltares.forms.portlet.action;

import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.dsd.model.Reservation;
import nl.deltares.emails.DsdEmail;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.model.impl.DinnerRegistration;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Location;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.Room;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DsdSessionUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + OssFormPortletKeys.DSD_REGISTRATIONFORM,
                "mvc.command.name=/submit/register/form"
        },
        service = MVCActionCommand.class
)
public class SubmitRegistrationActionCommand extends BaseMVCActionCommand {

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String redirect = ParamUtil.getString(actionRequest, "redirect");
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        createReservations(actionRequest, user, themeDisplay.getScopeGroupId(), getUserProperties(actionRequest));
        updateUserAttributes(actionRequest, user.getEmailAddress());
        sendRegistrationEmail(actionRequest, user, getReservation(actionRequest, themeDisplay), themeDisplay);
        SessionMessages.add(actionRequest, "registration-success");

        if (!redirect.isEmpty()) {
            sendRedirect(actionRequest, actionResponse, redirect);
        }
    }

    private Map<String, String> getUserProperties(ActionRequest actionRequest) {
        // TODO get all custom save properties?
        return new HashMap<>();
    }

    private void createReservations(PortletRequest request, User user, long siteId, Map<String, String> userProperties) {
        String prefix = "registration_";
        List<String> articleIds = request.getParameterMap()
                .keySet()
                .stream()
                .filter(strings -> strings.startsWith(prefix))
                .filter(key -> ParamUtil.getBoolean(request, key))
                .map(key -> key.substring(prefix.length()))
                .collect(Collectors.toList());


        articleIds.forEach(articleId -> createReservation(request, user, siteId, articleId, userProperties));
    }

    private void createReservation(PortletRequest request, User user, long siteId, String articleId,
                                   Map<String, String> userProperties) {
        try {
            Registration registration = dsdParserUtils.getRegistration(siteId, articleId);
            registrationUtils.registerUser(user, registration, userProperties);
        } catch (Exception e) {
            SessionErrors.add(request, "registration-failed", e.getMessage());
            LOG.debug("Could not create registration", e);
        }
    }

    private Reservation getReservation(ActionRequest actionRequest, ThemeDisplay themeDisplay) {
        String articleId = ParamUtil.getString(actionRequest, "articleId");
        try {
            long siteId = themeDisplay.getSiteGroupId();
            Registration parentRegistration = dsdParserUtils.getRegistration(siteId, articleId);
            Event event = dsdParserUtils.getEvent(siteId, String.valueOf(parentRegistration.getEventId()));

            Reservation reservation = toReservation(parentRegistration, event.getTitle(), themeDisplay);

            List<Registration> childRegistrations = dsdSessionUtils.getChildRegistrations(parentRegistration);
            for (Registration childRegistration : childRegistrations) {
                if (ParamUtil.getString(actionRequest, "registration_" + childRegistration.getArticleId()).equals("true")) {
                    reservation.getChildReservations().add(toReservation(childRegistration, event.getTitle(), themeDisplay));
                }
            }
            return reservation;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not retrieve registration for actionId: " + articleId);
            LOG.debug("Could not retrieve registration for actionId: " + articleId);
        }
        return null;
    }

    private Reservation toReservation(Registration registration, String eventName, ThemeDisplay themeDisplay) throws PortalException {
        Reservation reservation = new Reservation();
        reservation.setEventName(eventName);
        reservation.setName(registration.getTitle());
        reservation.setStartTime(registration.getStartTime().getTime());
        reservation.setEndTime(registration.getEndTime().getTime());
        reservation.setType(registration.getType());
        reservation.setCapacity(registration.getCapacity());
        reservation.setPrice(registration.getPrice());
        reservation.setArticleUrl(PortalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay) + JournalArticleConstants.CANONICAL_URL_SEPARATOR + registration.getJournalArticle().getUrlTitle());
        reservation.setBannerUrl(registration.getSmallImageURL(themeDisplay));
        if (registration instanceof SessionRegistration) {
            Room room = ((SessionRegistration) registration).getRoom();
            reservation.setLocation(room.getTitle());
        } else if (registration instanceof DinnerRegistration) {
            Location location = ((DinnerRegistration) registration).getRestaurant();
            reservation.setLocation(location.getTitle());
        }
        return reservation;
    }


    private void updateUserAttributes(ActionRequest actionRequest, String emailAddress) {
        try {
            keycloakUtils.updateUserAttributes(emailAddress, getKeycloakAttributes(actionRequest));
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "update-attributes-failed", e.getMessage());
            LOG.debug("Could not update keycloak attributes for user [" + emailAddress + "]", e);
        }
    }

    private Map<String, String> getKeycloakAttributes(ActionRequest actionRequest) {
        Map<String, String> attributes = new HashMap<>();

        for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
            String value = ParamUtil.getString(actionRequest, key.name());
            if (Validator.isNotNull(value)) {
                attributes.put(key.name(), value);
                LOG.info(key.name() + ": " + value);
            }
        }

        return attributes;
    }

    private void sendRegistrationEmail(ActionRequest actionRequest, User user, Reservation reservation, ThemeDisplay themeDisplay) {
        try {
            sendRegistrationEmail(user, reservation, themeDisplay);
        } catch (Exception e) {
            SessionErrors.add(actionRequest, "send-email-failed", "Could not send registration email for user [" + user.getEmailAddress() + "] : " + e.getMessage());
            LOG.debug("Could not send registration email for user [" + user.getEmailAddress() + "]", e);
        }
    }

    private void sendRegistrationEmail(User user, Reservation reservation, ThemeDisplay themeDisplay) throws Exception {
        DsdEmail email = new DsdEmail();
        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", themeDisplay.getLocale(), getClass());
        email.setResourceBundle(resourceBundle);
        email.setBanner(new URL(themeDisplay.getCDNBaseURL() + reservation.getBannerUrl()));
        email.sendRegisterEmail(user, reservation, themeDisplay.getLocale().getLanguage());
    }

    @Reference
    private KeycloakUtils keycloakUtils;

    @Reference
    private DsdParserUtils dsdParserUtils;

    @Reference
    private DsdSessionUtils dsdSessionUtils;

    @Reference
    private DsdSessionUtils registrationUtils;

    private static final Log LOG = LogFactoryUtil.getLog(SubmitRegistrationActionCommand.class);
}
