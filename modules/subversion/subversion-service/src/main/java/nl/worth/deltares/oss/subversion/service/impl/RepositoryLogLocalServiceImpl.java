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

package nl.worth.deltares.oss.subversion.service.impl;


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
import java.util.List;

import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import nl.worth.deltares.oss.subversion.constants.PropConstants;
import nl.worth.deltares.oss.subversion.model.Activity;
import nl.worth.deltares.oss.subversion.model.RepositoryLog;
import nl.worth.deltares.oss.subversion.service.RepositoryLogLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryLogServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryLogLocalServiceBaseImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryLogUtil;


/**
 * The implementation of the repository log local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryLogLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogLocalServiceBaseImpl
 */
public class RepositoryLogLocalServiceImpl
	extends RepositoryLogLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.worth.deltares.oss.subversion.service.RepositoryLogLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.worth.deltares.oss.subversion.service.RepositoryLogLocalServiceUtil</code>.
	 */
	private static Log LOG = LogFactoryUtil.getLog(RepositoryLogLocalServiceImpl.class);

	public int getRepositoryLogsCount(String screenName, String ipAddress, String repository) {

		DynamicQuery dynamicQuery = RepositoryLogLocalServiceUtil.getService().dynamicQuery();
		dynamicQuery.add(PropertyFactoryUtil.forName(PropConstants.SCREENNAME).eq(screenName))
				.add(PropertyFactoryUtil.forName(PropConstants.IP_ADDRESS).eq(ipAddress))
				.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY).eq(repository))
				.add(PropertyFactoryUtil.forName(PropConstants.ACTION).eq(PropConstants.ACTION_CHECKOUT));

		int count = 0;

		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			LOG.error("Error running RepositoryLog DynamicQueryCount", e);
		}

		return count;
	}

	public int getRepositoryLogsCount(String repository, String action) {

		DynamicQuery dynamicQuery = RepositoryLogLocalServiceUtil.getService().dynamicQuery();
		dynamicQuery.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY).eq(repository))
				.add(PropertyFactoryUtil.forName(PropConstants.ACTION).eq(action));

		int count = 0;

		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			LOG.error("Error running RepositoryLog DynamicQueryCount", e);
		}

		return count;
	}

	public int getRepositorLogsCount(String action) {

		DynamicQuery dynamicQuery = RepositoryLogLocalServiceUtil.getService().dynamicQuery();
		dynamicQuery.add(PropertyFactoryUtil.forName(PropConstants.ACTION).eq(action));
		dynamicQuery.setProjection(ProjectionFactoryUtil.projectionList().add(ProjectionFactoryUtil.count(PropConstants.LOG_ID)));

		int count = 0;

		try {
			count = ((Long) this.dynamicQueryCount(dynamicQuery)).intValue();
		} catch (SystemException e) {
			LOG.error("Error running RepositoryLog DynamicQueryCount", e);
		}

		return count;
	}

	public JSONArray getLastActivityLogs(int number) {

		JSONArray logsArray = JSONFactoryUtil.createJSONArray();

		List<RepositoryLog> repositoryLogList = RepositoryLogUtil.findAll(0, number);

		for (RepositoryLog repositoryLog : repositoryLogList) {
		  Activity activity = new Activity(repositoryLog);

		  try {
		  	logsArray.put(JSONFactoryUtil.createJSONObject(JSONFactoryUtil.serialize(activity)));
			} catch (JSONException e) {
		  	LOG.error("Error building RepositoryLogs JSONArray", e);
			}
		}

		return logsArray;
	}
}