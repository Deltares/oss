package com.worth.deltares.resource;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.worth.deltares.constants.UserPortraitsPortletKeys;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
  property = {
    "javax.portlet.name=" + UserPortraitsPortletKeys.UserPortraits,
    "mvc.command.name=/user-portraits/random"
  },
  service = MVCResourceCommand.class
)
public class RandomPortraitsResourceCommand implements MVCResourceCommand {

  private static final Log _log = LogFactoryUtil.getLog(RandomPortraitsResourceCommand.class);

  private static final Integer DEFAULT_PORTRAITS = 12;

  @Override
  public boolean serveResource(ResourceRequest request, ResourceResponse response)
      throws PortletException {

    ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

    Integer number = DEFAULT_PORTRAITS;

    try {
      number = Integer.parseInt(request.getParameter("number"));
    } catch (NumberFormatException e) {
      number = DEFAULT_PORTRAITS;
    }

    try {
      JSONObject responseJson = getPortraitJson(td, number);
      _log.debug(responseJson);

      response.setContentType("application/json");
      String jsonString = responseJson.toJSONString();
      response.setContentLength(jsonString.length());
      response.getWriter().write(jsonString);
      return true;
    } catch (Exception e) {
      _log.error("Error getting random portraits.", e);

      return false;
    }
  }

  private JSONObject getPortraitJson(ThemeDisplay td, Integer number) {

    JSONObject portraitsJson = JSONFactoryUtil.createJSONObject();
    List<String> portraitsList = getRandomPortraits(td, number);

    JSONArray portraitsArray = JSONFactoryUtil.createJSONArray();

    for (String portrait : portraitsList) {
      portraitsArray.put(portrait);
    }

    portraitsJson.put("total", portraitsList.size());
    portraitsJson.put("objects", portraitsArray);

    return portraitsJson;
  }

  private List<String> getRandomPortraits(ThemeDisplay td, Integer number) {
    Set<User> usersWithPortraits = getRandomUsersWithPortrait(number);
    List<String> portraitURLs = new ArrayList<>();

    try {
      for (User user : usersWithPortraits) {
        portraitURLs.add(user.getPortraitURL(td));
      }
    } catch (PortalException e) {
      _log.error("Error retrieving portrait URLs.", e);
    }

    return portraitURLs;
  }

  private UserLocalService _userLocalService;

  @Reference(unbind="-")
  private void setUserLocalService(UserLocalService userLocalService) {

    this._userLocalService = userLocalService;
  }

  private List<User> getAllUsersWithPortrait() {

    List<User> usersWithPortrait = new ArrayList<>();

    try {
      List<User> allUsers = _userLocalService.getCompanyUsers(10131L, 0, Integer.MAX_VALUE);

      for (User user : allUsers) {
        if (user.getPortraitId() != 0L) {
          usersWithPortrait.add(user);
        }
      }
    } catch (SystemException e) {
      _log.error("Error retrieving company users.", e);
    }

    return usersWithPortrait;
  }

  private Set<User> getRandomUsersWithPortrait(int elements) {

    List<User> users = getAllUsersWithPortrait();
    Set<User> randomUsers = new HashSet<>();

    if (users.size() == 0) return randomUsers;
    while (randomUsers.size() < elements) {
      Random r = new Random();
      User randomUser = users.get(r.nextInt(users.size()));

      randomUsers.add(randomUser);
    }

    return randomUsers;
  }
}
