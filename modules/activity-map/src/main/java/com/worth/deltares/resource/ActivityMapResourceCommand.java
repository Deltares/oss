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
import com.liferay.portal.kernel.util.WebKeys;

import com.worth.deltares.constants.ActivityMapPortletKeys;
import com.worth.deltares.subversion.service.RepositoryLogLocalService;
import com.worth.deltares.subversion.model.RepositoryLog;


/**
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@Component(
  property = {
    "javax.portlet.name=" + ActivityMapPortletKeys.ActivityMap,
    "mvc.command.name=/activity-map/last-logs"
  },
  service = MVCResourceCommand.class
)
public class ActivityMapResourceCommand implements MVCResourceCommand {

  private static final Log _log = LogFactoryUtil.getLog(ActivityMapResourceCommand.class);

  private static final Integer DEFAULT_LOGS = 10;

  @Override
  public boolean serveResource(ResourceRequest request, ResourceResponse response)
      throws PortletException {

    Integer number = DEFAULT_LOGS;

    try {
      number = Integer.parseInt(request.getParameter("number"));
    } catch (NumberFormatException e) {
      number = DEFAULT_LOGS;
    }

    try {
      JSONObject responseJson = getLogsJson(number);
      _log.debug(responseJson);

      response.setContentType("application/json");
      response.setContentLength(responseJson.length());
      response.getWriter().write(responseJson.toJSONString());

      return true;
    } catch (Exception e) {
      _log.error("Error getting random portraits.", e);

      return false;
    }
  }

  private JSONObject getLogsJson(Integer number) {

    JSONObject logsJson = JSONFactoryUtil.createJSONObject();
    JSONArray logsArray = _repositoryLogLocalService.getLastLogs(number);

    logsJson.put("total", logsArray.length());
    logsJson.put("objects", logsArray);

    return logsJson;
  }

  private RepositoryLogLocalService _repositoryLogLocalService;

  @Reference(unbind="-")
  private void setRepositoryLogLocalService(RepositoryLogLocalService repositoryLogLocalService) {

    this._repositoryLogLocalService = repositoryLogLocalService;
  }
}
