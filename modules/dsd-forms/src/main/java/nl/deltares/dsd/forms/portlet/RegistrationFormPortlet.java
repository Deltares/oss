package nl.deltares.dsd.forms.portlet;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import nl.deltares.dsd.emails.impl.ExampleEmail;
import nl.deltares.dsd.forms.constants.DsdFormPortletKeys;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
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
		"javax.portlet.display-name=RegistrationForm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/registration.jsp",
		"javax.portlet.name=" + DsdFormPortletKeys.REGISTRATIONFORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class RegistrationFormPortlet extends MVCPortlet {

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
			if (!attributes.containsKey(KeycloakUtils.ATTRIBUTES.org_preferred_payment.name())){
				attributes.put(KeycloakUtils.ATTRIBUTES.org_preferred_payment.name(), "payLink");
			}
			request.setAttribute("attributes", attributes);
		}
		super.render(request, response);
	}

	public void addRegistration(ActionRequest request, ActionResponse response) {

		Map<String, String> attributes = getAttributes(request);

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();

		try{
			//todo validate user information
			validateRegistration(attributes, user);
		} catch (Exception e){
			SessionErrors.add(request, "validation-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_registration.jsp");
			return;
		}

		try {
			keycloakUtils.updateUserAttributes(user.getEmailAddress(), attributes);
		} catch (Exception e) {
			SessionErrors.add(request, "update-attributes-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_registration.jsp");
			return;
		}

		try{
			//todo register user information
			validateRegistration(attributes, user);
		} catch (Exception e){
			SessionErrors.add(request, "registration-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_registration.jsp");
			return;
		}

		try {
			sendRegistrationEmail(user);
		} catch (Exception e){
			SessionErrors.add(request, "send-email-failed",  e.getMessage());
			response.getRenderParameters().setValue("mvcPath","/add_registration.jsp");
			return;
		}
		SessionMessages.add(request, "registration-success");
		response.getRenderParameters().setValue("mvcPath","/registration.jsp");

	}

	private void sendRegistrationEmail(User user) throws Exception {
		ExampleEmail exampleEmail = new ExampleEmail();
		exampleEmail.sendEmail();
	}

	private void validateRegistration(Map<String, String> attributes, User user) {

	}

	private Map getAttributes(ActionRequest request){
		HashMap<String, String> attributes = new HashMap<>();
		for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
			String attValue = ParamUtil.getString(request, key.name());
			attributes.put(key.name(), attValue);
		}
		return attributes;
	}

	public void delRegistration(ActionRequest request, ActionResponse response) {

	}

	@Reference
	private KeycloakUtils keycloakUtils;
}