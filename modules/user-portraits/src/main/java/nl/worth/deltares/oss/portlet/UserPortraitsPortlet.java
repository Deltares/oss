package nl.worth.deltares.oss.portlet;


import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import nl.worth.deltares.oss.portlet.constants.UserPortraitsPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import javax.portlet.Portlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


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

	public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		// TODO: make portrait load amount configurable
		request.setAttribute("userPortraitsList",
				getRandomPortraits(themeDisplay, DEFAULT_PORTRAITS));

		super.render(request, response);
	}

	private List<String> getRandomPortraits(ThemeDisplay themeDisplay, int number) {

		Set<User> usersWithPortraits = getRandomUsersWithPortrait(themeDisplay, number);
		List<String> portraitUrlList = new ArrayList<>();

		try {
			for (User user : usersWithPortraits) {
				portraitUrlList.add(user.getPortraitURL(themeDisplay));
			}
		} catch (PortalException e) {
			LOG.error("Error retrieving portrait URLs", e);
		}

		return portraitUrlList;
	}

	private Set<User> getRandomUsersWithPortrait(ThemeDisplay themeDisplay ,int number) {

		List<User> users = getAllUsersWithPortrait(themeDisplay);
		Set<User> randomUsers = new HashSet<>();

		if (users.size() == 0) {
			return randomUsers;
		}

		while (randomUsers.size() < number) {
			Random random = new Random();
			User randomUser = users.get(random.nextInt(users.size()));

			randomUsers.add(randomUser);
		}

		return randomUsers;
	}

	private List<User> getAllUsersWithPortrait(ThemeDisplay themeDisplay) {

		List<User> usersWithPortrait = new ArrayList<>();

		try {
			List<User> allUsers = userLocalService.getCompanyUsers(themeDisplay.getCompanyId(),
			    QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (User user : allUsers) {
				if (user.getPortraitId() != 0L) {
					usersWithPortrait.add(user);
				}
			}
		} catch (Exception e) {
			LOG.error("Error retrieving company users", e);
		}

		return usersWithPortrait;
	}

	private UserLocalService userLocalService;

	@Reference(unbind = "-")
	private void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}
}