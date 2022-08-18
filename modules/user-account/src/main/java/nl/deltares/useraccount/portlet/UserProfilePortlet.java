package nl.deltares.useraccount.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.*;
import nl.deltares.portal.utils.KeycloakUtils;
import nl.deltares.useraccount.constants.UserProfilePortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rooij_e
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=OSS-account",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=UserProfile",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/userprofile.jsp",
		"javax.portlet.name=" + UserProfilePortletKeys.USERPROFILE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UserProfilePortlet extends MVCPortlet {

	@Reference
	private KeycloakUtils keycloakUtils;

	@Override
	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		if (!user.isDefaultUser() && user.isActive()) {
			try {
				final Map<String, String> userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
				request.setAttribute("attributes", userAttributes);
			} catch (Exception e) {
				SessionErrors.add(request, "update-profile-failed", "Error reading user attributes: " + e.getMessage());
				request.setAttribute("attributes", new HashMap<>());
			}

			super.render(request, response);
		}
	}

	/**
	 * Save user attributes to database
	 *
	 * @param actionRequest  Save action
	 * @param actionResponse Save response
	 */
	@SuppressWarnings("unused")
	public void saveUserProfile(ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();

		Map<String, String> userAttributes = getUserAttributes(actionRequest);
		updateUserProfile(actionRequest, user, userAttributes);

	}

	/**
	 * Save user photo to database
	 *
	 * @param actionRequest  Save action
	 * @param actionResponse Save response
	 */
	@SuppressWarnings("unused")
	public void saveUserAvatar(ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		try {
			InputStream in = new BufferedInputStream(uploadRequest.getFileAsStream("image"));
			final byte[] image = FileUtil.getBytes(in);

			final User user1 = UserLocalServiceUtil.updatePortrait(user.getUserId(), image);
			user.setPortraitId(user1.getPortraitId()); //force update so page will refresh
			updateUserAvatar(actionRequest, user, image, uploadRequest.getFileName("image"));
		} catch (IOException | PortalException e) {
			SessionErrors.add(actionRequest, "update-profile-failed", e.getMessage());
		}

	}

	/**
	 * Save user photo to database
	 *
	 * @param actionRequest  Save action
	 * @param actionResponse Save response
	 */
	@SuppressWarnings("unused")
	public void deleteUserAvatar(ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();

		try {//Force updating user profile so page will refresh correctly
			UserLocalServiceUtil.deletePortrait(user.getUserId());
			user.setPortraitId(0);
			UserLocalServiceUtil.updateUser(user);
			keycloakUtils.deleteUserAvatar(user.getEmailAddress());
			SessionMessages.add(actionRequest, "update-profile-success");
		} catch (Exception e) {
			SessionErrors.add(actionRequest, "update-profile-failed", e.getMessage());
		}

	}


	private Map<String, String> getUserAttributes(ActionRequest actionRequest) {
		Map<String, String> attributes = new HashMap<>();

		//Get keycloak attributes
		for (KeycloakUtils.ATTRIBUTES key : KeycloakUtils.ATTRIBUTES.values()) {
			String value = ParamUtil.getString(actionRequest, key.name());
			if (Validator.isNotNull(value)) {
				attributes.put(key.name(), value);
			}
		}
		return attributes;
	}

	private void updateUserProfile(ActionRequest actionRequest, User user, Map<String, String> attributes) {

		try {
			final String currentEmail = user.getEmailAddress();

			boolean includeUserInfo = false;
			final String newEmail = attributes.get(KeycloakUtils.ATTRIBUTES.email.name());
			if (!currentEmail.equals(newEmail) && Validator.isEmailAddress(newEmail)){
				checkEmailAllSites(newEmail);
				updateEmailAllSites(actionRequest, user, currentEmail);
				user.setEmailAddress(newEmail);
				includeUserInfo = true;
			}
			final String firstName = attributes.get(KeycloakUtils.ATTRIBUTES.first_name.name());
			if (!user.getFirstName().equals(firstName) && !Validator.isBlank(firstName)) {
				user.setFirstName(firstName);
				includeUserInfo = true;
			}
			final String lastName = attributes.get(KeycloakUtils.ATTRIBUTES.last_name.name());
			if (!user.getLastName().equals(lastName) && !Validator.isBlank(lastName)) {
				user.setLastName(lastName);
				includeUserInfo = true;
			}
			if (includeUserInfo) {
				UserLocalServiceUtil.updateUser(user);
				keycloakUtils.updateUserProfile(currentEmail, attributes);
			} else {
				keycloakUtils.updateUserAttributes(currentEmail, attributes);
			}

			SessionMessages.add(actionRequest, "update-profile-success");
		} catch (Exception e) {
			SessionErrors.add(actionRequest, "update-profile-failed", e.getMessage());
		}

	}

	private void checkEmailAllSites(String newEmail) throws PortletException {

		final List<VirtualHost> virtualHosts = VirtualHostLocalServiceUtil.getVirtualHosts(0, 10);
		for (VirtualHost virtualHost : virtualHosts) {
			final long companyId = virtualHost.getCompanyId();
			final User otherSiteUser = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, newEmail);
			if (otherSiteUser != null){
				throw new PortletException(String.format("Email %s already exists for another user on site %s", newEmail, virtualHost.getHostname()));
			}
		}
	}

	private void updateEmailAllSites(ActionRequest actionRequest,  User user, String oldEmail) {

		final List<VirtualHost> virtualHosts = VirtualHostLocalServiceUtil.getVirtualHosts(0, 10);
		for (VirtualHost virtualHost : virtualHosts) {
			final long companyId = virtualHost.getCompanyId();
			if (companyId == user.getCompanyId()) continue;
			final User otherSiteUser = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, oldEmail);
			if (otherSiteUser == null) continue;
			otherSiteUser.setEmailAddress(user.getEmailAddress());
			try {
				UserLocalServiceUtil.updateUser(otherSiteUser);
			} catch (Exception e) {
				SessionErrors.add(actionRequest, "update-profile-failed", String.format("Failed to update user email for virtual host %s: %s", virtualHost.getHostname(), e.getMessage()));
			}
		}
	}

	private void updateUserAvatar(ActionRequest actionRequest, User user, byte[] image, String fileName) {
		try {
			keycloakUtils.updateUserAvatar(user.getEmailAddress(), image, fileName);
			SessionMessages.add(actionRequest, "update-profile-success");
		} catch (Exception e) {
			SessionErrors.add(actionRequest, "update-profile-failed", e.getMessage());
		}

	}
}