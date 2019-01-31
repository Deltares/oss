/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.worth.deltares.subversion.service.impl;


import java.util.List;

import com.worth.deltares.subversion.model.RepositoryLog;
import com.worth.deltares.subversion.service.base.RepositoryLogLocalServiceBaseImpl;
import com.worth.deltares.subversion.service.persistence.RepositoryLogUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.worth.deltares.subversion.model.Activity;


/**
 * The implementation of the repository log local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.service.RepositoryLogLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogLocalServiceBaseImpl
 * @see com.worth.deltares.subversion.service.RepositoryLogLocalServiceUtil
 */
public class RepositoryLogLocalServiceImpl
	extends RepositoryLogLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.worth.deltares.subversion.service.RepositoryLogLocalServiceUtil} to access the repository log local service.
	 */

  private static Log _log = LogFactoryUtil.getLog(RepositoryLogLocalServiceImpl.class);

  public int getRepositoryLogsCount(
      String screenName, String ipAddress, String repository) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryLog.class)
				.add(PropertyFactoryUtil.forName("screenName").eq(screenName)) 
				.add(PropertyFactoryUtil.forName("ipAddress").eq(ipAddress))
				.add(PropertyFactoryUtil.forName("repository").eq(repository))
				.add(PropertyFactoryUtil.forName("action").eq("CHECKOUT"));
		
		int count = 0;
		
		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			_log.error("Error running DynamicQueryCount.", e);
		}
		
		return count;
	}
	
	public int getRepositoryLogsCount(String repository, String action) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryLog.class)
				.add(PropertyFactoryUtil.forName("repository").eq(repository))
				.add(PropertyFactoryUtil.forName("action").eq(action));
		
		int count = 0;
		
		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			_log.error("Error running DynamicQueryCount.", e);
		}
		
		return count;
	}

	public int getRepositoryLogsCount(String action) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryLog.class)
        .add(PropertyFactoryUtil.forName("action").eq(action));

		dynamicQuery.setProjection(
				ProjectionFactoryUtil.projectionList().add(ProjectionFactoryUtil.count("logId")));
		
		int count = 0;
		
		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			_log.error("Error running DynamicQueryCount.", e);
		}
		
		return count;
	}

  public JSONArray getLastLogs(Integer number) {

		JSONArray logsArray = JSONFactoryUtil.createJSONArray();

		List<RepositoryLog> repositoryLogs = RepositoryLogUtil.findAll(0, number);

		for (RepositoryLog log : repositoryLogs) {
			Activity activity = new Activity(log.getAction());

			if (log.getCity() != null) {
				activity.setLocation(log.getCity());
			}

			if (log.getLatitude() != null) {
				activity.setLatitude(log.getLatitude());
			}

			if (log.getLongitude() != null) {
				activity.setLongitude(log.getLongitude());
			}

			if (log.getRepository() != null) {
				activity.setMessage("repository: " + log.getRepository());
			}

			try {
				logsArray.put(JSONFactoryUtil.createJSONObject(JSONFactoryUtil.serialize(activity)));
			} catch (JSONException e) {
				_log.error("Error building JSONArray: ", e);
			}
		}

		return logsArray;
	}
}