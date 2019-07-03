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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.DateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.worth.deltares.oss.subversion.constants.PermissionConstants;
import nl.worth.deltares.oss.subversion.constants.PropConstants;
import nl.worth.deltares.oss.subversion.exception.DuplicateRepositoryFolderPermissionException;
import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryFolderPermissionLocalServiceBaseImpl;


/**
 * The implementation of the repository folder permission local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionLocalServiceBaseImpl
 */
public class RepositoryFolderPermissionLocalServiceImpl
	extends RepositoryFolderPermissionLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalServiceUtil</code>.
	 */
	private static Log LOG = LogFactoryUtil.getLog(RepositoryFolderPermissionLocalServiceImpl.class);

	public RepositoryFolderPermission createRepositoryFolderPermission() {

		RepositoryFolderPermission repositoryFolderPermission = null;

		try {
			repositoryFolderPermission = RepositoryFolderPermissionLocalServiceUtil.createRepositoryFolderPermission(
					CounterLocalServiceUtil.increment(RepositoryFolderPermission.class.getName()));
			repositoryFolderPermission.setCreateDate(DateUtil.newDate());
			repositoryFolderPermission.setModifiedDate(DateUtil.newDate());
		} catch (SystemException e) {
			LOG.error("Error creating RepositoryFolderPermission", e);
		}

		return repositoryFolderPermission;
	}

	public RepositoryFolderPermission createRepositoryFolderPermission(long repositoryId, String folderName, User user, String permission) {

		RepositoryFolder repositoryFolder = RepositoryFolderLocalServiceUtil.addRepositoryFolder(repositoryId, folderName);

		return createRepositoryFolderPermission(repositoryFolder, user, permission);
	}

	public RepositoryFolderPermission createRepositoryFolderPermission(long repositoryId, String folderName, Role role, String permission) {

		RepositoryFolder repositoryFolder = RepositoryFolderLocalServiceUtil.addRepositoryFolder(repositoryId, folderName);

		return createRepositoryFolderPermission(repositoryFolder, role, permission);
	}

	public RepositoryFolderPermission createRepositoryFolderPermission(RepositoryFolder repositoryFolder, User user, String permission) {

		RepositoryFolderPermission repositoryFolderPermission = createRepositoryFolderPermission();

		repositoryFolderPermission.setFolderId(repositoryFolder.getFolderId());
		repositoryFolderPermission.setPermission(permission);
		repositoryFolderPermission.setEntityType(PermissionConstants.TYPE_USER);
		repositoryFolderPermission.setEntityId(user.getUserId());

		return repositoryFolderPermission;
	}

	public RepositoryFolderPermission createRepositoryFolderPermission(RepositoryFolder repositoryFolder, Role role, String permission) {

		RepositoryFolderPermission repositoryFolderPermission = createRepositoryFolderPermission();

		repositoryFolderPermission.setFolderId(repositoryFolder.getFolderId());
		repositoryFolderPermission.setPermission(permission);
		repositoryFolderPermission.setEntityType(PermissionConstants.TYPE_ROLE);
		repositoryFolderPermission.setEntityId(role.getRoleId());

		return repositoryFolderPermission;
	}

	public RepositoryFolderPermission addRepositoryFolderPermission(RepositoryFolder repositoryFolder, User user, String permission, boolean recurse) {

		try {
			RepositoryFolderPermission repositoryFolderPermission = createRepositoryFolderPermission(repositoryFolder, user, permission);

			return super.addRepositoryFolderPermission(repositoryFolderPermission);
		} catch (SystemException e) {
			LOG.warn("Could not add repository", e);

			return null;
		}
	}

	public RepositoryFolderPermission addRepositoryFolderPermission(RepositoryFolder repositoryFolder, Role role, String permission, boolean recurse) {

		try {
			RepositoryFolderPermission repositoryFolderPermission = createRepositoryFolderPermission(repositoryFolder, role, permission);

			return super.addRepositoryFolderPermission(repositoryFolderPermission);
		} catch (SystemException e) {
			LOG.warn("Could not add repository", e);

			return null;
		}
	}

	public RepositoryFolderPermission addRepositoryFolderPermission(RepositoryFolderPermission repositoryFolderPermission) throws SystemException {

		try {
			if (repositoryFolderPermission.getRecurse()) {
				RepositoryFolderLocalServiceUtil.deleteRepositoryFolders(repositoryFolderPermission.getFolder().getChildren());
			}
		} catch (PortalException e) {
			LOG.error("Failed removing a child! From: " + repositoryFolderPermission.getFolderId(), e);
		}

		return repositoryFolderPermission;
	}

	public List<RepositoryFolderPermission> addRepositoryFolderPermissions(RepositoryFolder repositoryFolder, List<RepositoryFolderPermission> permissions, boolean recurse) throws SystemException, PortalException {

		Set<String> tempSet = new HashSet<>();

		for (RepositoryFolderPermission permission : permissions) {
			String permissionHash = permission.getEntityType() + permission.getEntityId();

			if (!tempSet.add(permissionHash)) {
				throw new DuplicateRepositoryFolderPermissionException("Duplicate permissions detected for " + permissionHash);
			}
		}

		RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermissions(repositoryFolder.getRepositoryId(), repositoryFolder.getFolderId());

		if (recurse) {
			RepositoryFolderLocalServiceUtil.deleteRepositoryFolders(RepositoryFolderLocalServiceUtil.getRepositoryFolderChildren(repositoryFolder));
		}

		for (RepositoryFolderPermission permission : permissions) {
			RepositoryFolderPermissionLocalServiceUtil.addRepositoryFolderPermission(permission);

			if (recurse) {
				permission.setRecurse(true);
			}
		}

		return permissions;
	}

	public List<RepositoryFolderPermission> getRepositoryFolderPermissions(long folderId) {

		List<RepositoryFolderPermission> permissions = new ArrayList<>();

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolderPermission.class)
				.add(PropertyFactoryUtil.forName(PropConstants.FOLDER_ID).eq(folderId));

		try {
			permissions = RepositoryFolderPermissionLocalServiceUtil.dynamicQuery(query);
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolderPermission DynamicQuery", e);
		}

		return permissions;
	}

	public RepositoryFolderPermission getRepositoryFolderPermission(long repositoryId, long folderId, long roleId) {

		RepositoryFolderPermission repositoryFolderPermission = null;

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolderPermission.class)
				.add(PropertyFactoryUtil.forName(PropConstants.FOLDER_ID).eq(folderId))
				.add(PropertyFactoryUtil.forName(PropConstants.ROLE_ID).eq(roleId));

		try {
			repositoryFolderPermission = (RepositoryFolderPermission) RepositoryFolderPermissionLocalServiceUtil.dynamicQuery(query).get(0);
		} catch (SystemException e) {
			LOG.error("Error running RepositoryFolderPermission DynamicQuery", e);
		}

		return repositoryFolderPermission;
	}

	public void deleteRepositoryFolderPermissions(long repositoryId, long folderId) {

		List<RepositoryFolderPermission> permissions = RepositoryFolderPermissionLocalServiceUtil.getRepositoryFolderPermissions(folderId);

		for (RepositoryFolderPermission permission : permissions) {
			try {
				RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermission(permission.getPermissionId());
			} catch (PortalException | SystemException e) {
				LOG.error("Error deleting RepositoryFolderPermission", e);
			}
		}
	}

	public void deleteRepositoryFolderPermission(long repositoryId, long folderId, long roleId) {

		RepositoryFolderPermission repositoryFolderPermission = RepositoryFolderPermissionLocalServiceUtil.getRepositoryFolderPermission(repositoryId, folderId, roleId);

		try {
			RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermission(repositoryFolderPermission.getPermissionId());
		} catch (PortalException | SystemException e) {
			LOG.error("Error deleting RepositoryFolderPermission", e);
		}
	}
}