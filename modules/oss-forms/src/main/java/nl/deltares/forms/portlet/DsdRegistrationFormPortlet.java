package nl.deltares.forms.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.dsd.model.*;
import nl.deltares.emails.DsdEmail;
import nl.deltares.forms.constants.OssFormPortletKeys;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=OSS",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DsdRegistrationForm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/dsd_registration.jsp",
		"javax.portlet.name=" + OssFormPortletKeys.DSD_REGISTRATIONFORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supported-locale=en",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DsdRegistrationFormPortlet extends MVCPortlet {

	@Reference
	private KeycloakUtils keycloakUtils;

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		request.setAttribute("registered", false); //todo
		request.setAttribute("requiresPayment", true); //todo

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		if (!user.isDefaultUser()) {
            Map<String, String> attributes = null;
            try {
                attributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
            } catch (IOException e) {
                SessionErrors.add(request, "update-attributes-failed",  e.getMessage());
            }
            if (attributes == null) {
				attributes = new HashMap<>();
			}
			request.setAttribute("attributes", attributes);
		}
		super.render(request, response);
	}

	public void delRegistration(ActionRequest request, ActionResponse response) {

	}

	public void addRegistration(ActionRequest request, ActionResponse response) {

		Map<String, String> attributes = getAttributes(request);

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        User user = themeDisplay.getUser();
		//TODO we need to get Registration from somewhere.
		Reservation reservation = null;
		try {
			reservation = getReservation(request);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try{
			//todo validate user information
			validateRegistration(attributes, user);
		} catch (Exception e){
			SessionErrors.add(request, "validation-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_dsd_registration.jsp");
			return;
		}

		try {
			keycloakUtils.updateUserAttributes(user.getEmailAddress(), attributes);
		} catch (Exception e) {
			SessionErrors.add(request, "update-attributes-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_dsd_registration.jsp");
			return;
		}

		try {
			sendRegistrationEmail(user, reservation, themeDisplay);
		} catch (Exception e){
			SessionErrors.add(request, "send-email-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_dsd_registration.jsp");
			return;
		}
		SessionMessages.add(request, "registration-success");
		response.getRenderParameters().setValue("mvcPath","/dsd_registration.jsp");

	}

	private Reservation getReservation(ActionRequest request) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		Session reservation = new Session();
		reservation.setName("WANDA - Basis Cursus");
		reservation.setStartTime(simpleDateFormat.parse("2020-06-16T09:00").getTime());
		reservation.setEndTime(simpleDateFormat.parse("2020-06-17T17:00").getTime());
		reservation.setCapacity(23);
		reservation.setEventName("Deltares Software Days");
		reservation.setType(Type.course);
		reservation.setPrice(1125);

		Person presenter = new Person();
		presenter.setEmail("erik.derooij@deltares.nl");
		presenter.setFirstName("Erik");
		presenter.setLastName("de Rooij");
		presenter.setOrganization("Deltares");
		presenter.setAcademicTitle("Mr.");
		presenter.setJobTitle("Software engineer");

		reservation.addPresenter(presenter);

		Location location = new Location();
		location.setName("Deltares");
		location.setAddress("Boussinesqweg 1");
		location.setPostalCode("2629 HV");
		location.setCity("Delft");
		location.setCountry("The Netherlands");
		Building building = new Building("Toren", location);
		Room colloquium = new Room("Colloquium", building);
		colloquium.setCapacity(130);

		reservation.setRoom(colloquium);
		return reservation;
	}

	private void sendRegistrationEmail(User user, Reservation reservation, ThemeDisplay themeDisplay) throws Exception {

		//todo Make configurable in portlet configuration.
		DsdEmail email = new DsdEmail(DsdEmail.DSD_SITE.dsdint);
		email.setResourceBundle(getResourceBundle(themeDisplay.getLocale()));
		email.setSiteUrl(themeDisplay.getCDNBaseURL() + themeDisplay.getURLCurrent());
		email.sendRegisterEmail(user, reservation);
	}

	private void validateRegistration(Map<String, String> attributes, User user) {

	}

	private Map<String, String> getAttributes(ActionRequest request){
		HashMap<String, String> attributes = new HashMap<>();
		for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
			String attValue = ParamUtil.getString(request, key.name());
			attributes.put(key.name(), attValue);
		}
		return attributes;
	}
}