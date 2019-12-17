package nl.deltares.mvc.commands.portlet;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import nl.deltares.common.MyDeltaresUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

/**
 * @author rooij_e
 */
@Component(
		immediate = true,
		property = { "key=login.events.post" },
		service = LifecycleAction.class
)
public class MyDeltaresPostLoginActionPortlet implements LifecycleAction {

	private static final Log LOG = LogFactoryUtil.getLog(
			MyDeltaresPostLoginActionPortlet.class);

	private UserLocalService userLocalService;

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {


		HttpServletRequest request = lifecycleEvent.getRequest();
		if (request == null) return;


		String avatarUrl;
		try {
			avatarUrl = MyDeltaresUtil.getAdminAvatarPath();
		} catch (IllegalStateException e) {
			//not configured
			LOG.warn(e.getMessage());
			return;
		}

		Principal userPrincipal = request.getUserPrincipal();
		if (userPrincipal == null) return;
		User user = userLocalService.fetchUserById(Long.parseLong(userPrincipal.getName()));
		if (user == null){
			LOG.warn("Could not fine user for principal " + userPrincipal.getName());
			return;
		}
		LOG.info("Updating avatar for user " + user.getFullName());

		try {
			byte[] bytes = downloadAvatar(avatarUrl, user.getEmailAddress(), request.getSession(true));
			if (bytes != null && bytes.length > 0) {
				userLocalService.updatePortrait(user.getUserId(), bytes);
			} else {
				userLocalService.deletePortrait(user.getUserId());
			}
		} catch (Exception e) {
			LOG.warn(String.format("Error updating portrait %d for user %s", user.getPortraitId(), user.getScreenName()), e);
		}

	}

	private byte[] downloadAvatar(String avatarUrl, String email, HttpSession session) throws IOException {

		URL url = new URL(avatarUrl + "?email=" + email);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(false);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Authorization" , "Bearer " + getAccessToken(session));
		byte[] bytes = MyDeltaresUtil.readAllBytes(urlConnection.getInputStream());
		return bytes;
	}

	private String getAccessToken(HttpSession session) {
		String keycloak_token = (String) session.getAttribute(MyDeltaresUtil.SESSION_TOKEN_KEY);
		if (keycloak_token != null) {
			Long expiryTime = (Long) session.getAttribute(MyDeltaresUtil.SESSION_EXPIRY_KEY);
			if (expiryTime != null && expiryTime > System.currentTimeMillis()){
				return keycloak_token;
			}
		}
		try {
			String jsonResponse = MyDeltaresUtil.getAccessTokenJson(
					MyDeltaresUtil.getTokenPath(),
					MyDeltaresUtil.getOpenIdClientId(),
					MyDeltaresUtil.getOpenIdClientSecret());
			return parseJson(jsonResponse, session);

		} catch (IOException | JSONException e){
			LOG.warn("Failed to get access token: " + e.getMessage(), e);
		}
		return null;
	}

	private String parseJson(String jsonResponse, HttpSession session) throws JSONException {
		// parsing file "JSONExample.json"
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(jsonResponse);
		long expMillis = jsonObject.getInt("expires_in") * 1000;
		long expTime = expMillis + System.currentTimeMillis();

		String accessToken = jsonObject.getString("access_token");

		session.setAttribute(MyDeltaresUtil.SESSION_TOKEN_KEY, accessToken);
		session.setAttribute(MyDeltaresUtil.SESSION_EXPIRY_KEY, expTime);
		return accessToken;
	}

	@Reference(unbind = "-")
	private void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

}