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

package nl.deltares.oss.download.service.impl;

import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.base.DownloadLocalServiceBaseImpl;
import nl.deltares.oss.download.service.persistence.DownloadUtil;

import java.util.List;

/**
 * The implementation of the download local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.deltares.oss.download.service.DownloadLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadLocalServiceBaseImpl
 */
public class DownloadLocalServiceImpl extends DownloadLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.deltares.oss.download.service.DownloadLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.oss.download.service.DownloadLocalServiceUtil</code>.
	 */

	public List<Download> getPendingUserDownloads(long groupId, long userId){
		return DownloadUtil.findByPendingUserDownloads(groupId, userId);
	}

	public Download getUserDownload(long groupId, long userId, long downloadId){
		return DownloadUtil.fetchByUserDownload(groupId, userId, downloadId);
	}
}