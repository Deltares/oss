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


import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

import nl.worth.deltares.oss.subversion.constants.PropConstants;
import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryFolderLocalServiceBaseImpl;


/**
 * The implementation of the repository folder local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderLocalServiceBaseImpl
 */
public class RepositoryFolderLocalServiceImpl
	extends RepositoryFolderLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil</code>.
	 */
	private static Log LOG = LogFactoryUtil.getLog(RepositoryFolderLocalServiceImpl.class);

	public RepositoryFolder createRepositoryFolder() {

		RepositoryFolder repositoryFolder = null;

		try {
			repositoryFolder = RepositoryFolderLocalServiceUtil.createRepositoryFolder(
					CounterLocalServiceUtil.increment(RepositoryFolder.class.getName()));
		} catch (SystemException e) {
			LOG.error("Error creating RepositoryFolder", e);
		}

		repositoryFolder.setCreateDate(DateUtil.newDate());
		repositoryFolder.setModifiedDate(DateUtil.newDate());

		return repositoryFolder;
	}

	public RepositoryFolder addRepositoryFolder(RepositoryFolder repositoryFolder) throws SystemException {

		RepositoryFolder folder = getRepositoryFolder(repositoryFolder.getRepositoryId(), repositoryFolder.getName());

		if (folder == null) {
			folder = super.addRepositoryFolder(repositoryFolder);
		}

		return folder;
	}

	public RepositoryFolder addRepositoryFolder(long repositoryId, String name) {

		RepositoryFolder repositoryFolder = getRepositoryFolder(repositoryId, name);

		if (repositoryFolder == null) {
			repositoryFolder = createRepositoryFolder();
			repositoryFolder.setName(name);
		}

		return repositoryFolder;
	}

	public RepositoryFolder getRepositoryFolder(long repositoryId, String name) {

		RepositoryFolder repositoryFolder = null;
		List<RepositoryFolder> folders;

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
				.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY_ID).eq(repositoryId))
				.add(PropertyFactoryUtil.forName(PropConstants.NAME).eq(name));

		try {
			folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);

			if (!folders.isEmpty()) {
				repositoryFolder = (RepositoryFolder) folders.get(0);
			}
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolder DynamicQuery", e);
		}

		return repositoryFolder;
	}

	@Deprecated
	public List<RepositoryFolder> getRepositoryFolders(long repositoryId, String name) {

		List<RepositoryFolder> folders = new ArrayList<>();

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
				.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY_ID).eq(repositoryId))
				.add(RestrictionsFactoryUtil.like(PropConstants.NAME, name + "/%"));

		try {
			folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolder DynamicQuery", e);
		}

		return folders;
	}

	public List<RepositoryFolder> getRepositoryFolderChildren(RepositoryFolder repositoryFolder) {

		List<RepositoryFolder> folders = new ArrayList<>();

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
				.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY_ID).eq(repositoryFolder.getRepositoryId()))
				.add(RestrictionsFactoryUtil.like(PropConstants.NAME, repositoryFolder.getName() + "/%"));

		try {
			folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolder DynamicQuery", e);
		}

		return folders;
	}

	public RepositoryFolder deleteRepositoryFolder(RepositoryFolder repositoryFolder) throws SystemException {

		RepositoryFolder deleted = null;

		RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermissions(repositoryFolder.getRepositoryId(), repositoryFolder.getFolderId());

		try {
			deleted = super.deleteRepositoryFolder(repositoryFolder.getFolderId());
		} catch (PortalException e) {
			LOG.error("Error deleting RepositoryFolder", e);
		}

		return deleted;
	}

	public List<RepositoryFolder> getRepositoryFolders(long repositoryId) {

		List<RepositoryFolder> folders = new ArrayList<>();

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
				.add(PropertyFactoryUtil.forName(PropConstants.REPOSITORY_ID).eq(repositoryId));

		try {
			folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolder DynamicQuery", e);
		}

		return folders;
	}

	public void deleteRepositoryFolders(List<RepositoryFolder> folders) {

		for (RepositoryFolder folder : folders) {
			try {
				RepositoryFolderLocalServiceUtil.deleteRepositoryFolder(folder);
			} catch (SystemException e) {
				LOG.error("Error deleting RepositoryFolder", e);
			}
		}
	}

	public RepositoryFolder getRepositoryFolderParent(long repositoryId, String folderName) {

		RepositoryFolder repositoryFolder = null;
		String[] folders = folderName.split("/");
		StringBuilder sb = new StringBuilder("/");

		for (String name : folders) {
			if (sb.lastIndexOf("/") == sb.length() - 1) {
				sb.append(name);
			} else {
			  sb.append("/");
				sb.append(name);
			}

			if (getRepositoryFolder(repositoryId, sb.toString()) != null) {
				repositoryFolder = getRepositoryFolder(repositoryId, sb.toString());
			}
		}

		if (repositoryFolder == null) {
			repositoryFolder = getRepositoryFolder(repositoryId, StringPool.BLANK);
		}

		return repositoryFolder;
	}
}