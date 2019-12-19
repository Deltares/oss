package nl.worth.deltares.oss.portlet;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import nl.worth.deltares.oss.portlet.constants.UserPortraitsPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=OSS",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.footer-portlet-css=/css/main.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + UserPortraitsPortletKeys.USER_PORTRAITS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UserPortraitsPortlet extends MVCPortlet {

	private static final Log LOG = LogFactoryUtil.getLog(UserPortraitsPortlet.class);

	private static final int DEFAULT_PORTRAITS = 12;

	private SecureRandom random = new SecureRandom();

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		// TODO: make portrait load amount configurable
		List<String> randomPortraits = getRandomPortraits(themeDisplay, DEFAULT_PORTRAITS);
		Principal userPrincipal = request.getUserPrincipal();
		if (userPrincipal != null) {
			randomPortraits.add(0, getPortrait(Long.parseLong(userPrincipal.getName()), themeDisplay));
		};
		request.setAttribute("userPortraitsList",
				randomPortraits);

		super.render(request, response);
	}

	private String getPortrait(long userId, ThemeDisplay themeDisplay) {

		try {
			User user = userLocalService.getUser(userId);
			if (user.getPortraitId() != 0L){
				Image image = imageLocalService.getImage(user.getPortraitId());
				if (image == null || image.getTextObj() == null){
					//This portraitId is not valid so remove it.
					LOG.warn(String.format("Un-setting portrait %d for user %s", user.getPortraitId(),  user.getScreenName()));
					user.setPortraitId(0);
					userLocalService.updateUser(user);
					return "/o/user-portraits/images/missing_avatar.png";
				}
				return user.getPortraitURL(themeDisplay);
			}
		} catch (Exception e){
			LOG.error("Error retrieving portrait URL for user " + userId, e);
		}
		return "/o/user-portraits/images/missing_avatar.png";
	}

	private List<String> getRandomPortraits(ThemeDisplay themeDisplay, int number) {

		Set<User> usersWithPortraits = getRandomUsersWithPortrait(themeDisplay, number);
		List<String> portraitUrlList = new ArrayList<>();

		try {
			for (User user : usersWithPortraits) {
				String portraitURL = user.getPortraitURL(themeDisplay);
				portraitUrlList.add(portraitURL);
			}
		} catch (PortalException e) {
			LOG.error("Error retrieving portrait URLs", e);
		}

		return portraitUrlList;
	}

	private Set<User> getRandomUsersWithPortrait(ThemeDisplay themeDisplay ,int number) {

		Set<User> usersWithPortrait = new HashSet<>();

		try {
			//Retrieve only 1000 users to search for portraits. Start position is random.
			int allUserCount = userLocalService.getUsersCount();

			int countUsersWithPortrait = 0;

			int startIndex;
			int endIndex;
			if (allUserCount < 1000){
			    startIndex = 0;
			    endIndex = allUserCount;
            } else {
                startIndex = random.nextInt(allUserCount - 1000);
                endIndex = startIndex + 1000;
            }

			List<User> allUsers = userLocalService.getCompanyUsers(themeDisplay.getCompanyId(),
					startIndex, endIndex);

			for (User user : allUsers) {
				if (countUsersWithPortrait > number) break; // we have enough portraits
				if (user.getPortraitId() != 0L) {

					try {
						Image image = imageLocalService.getImage(user.getPortraitId());
						if (image == null || image.getTextObj() == null){
							//This portraitId is not valid so remove it.
							LOG.warn(String.format("Un-setting portrait %d for user %s", user.getPortraitId(),  user.getScreenName()));
							user.setPortraitId(0);
							userLocalService.updateUser(user);
						} else {
							//Found a portrait so add it.
							usersWithPortrait.add(user);
							countUsersWithPortrait++;
						}
					} catch (Exception e){
						LOG.warn(String.format("Un-setting portrait %d for user %s", user.getPortraitId(),  user.getScreenName()));
						user.setPortraitId(0);
						userLocalService.updateUser(user);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error retrieving company users", e);
		}

		return usersWithPortrait;
	}


	private UserLocalService userLocalService;
	private ImageLocalService imageLocalService;

	@Reference(unbind = "-")
	private void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	private void setImageLocalService(ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}
}