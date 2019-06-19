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

package nl.worth.deltares.oss.subversion.model.impl;


import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import nl.worth.deltares.oss.subversion.constants.PermissionConstants;
import nl.worth.deltares.oss.subversion.model.Repository;
import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryLocalServiceUtil;


/**
 * The extended model implementation for the RepositoryFolderPermission service. Represents a row in the &quot;Subversion_RepositoryFolderPermission&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission<code> interface.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 */
@ProviderType
public class RepositoryFolderPermissionImpl
	extends RepositoryFolderPermissionBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a repository folder permission model instance should use the {@link nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission} interface instead.
	 */
	public RepositoryFolderPermissionImpl() {
	}

	private static final long serialVersionUID = 5070436644064713125L;

	public RepositoryFolder getFolder() throws PortalException, SystemException {
		return RepositoryFolderLocalServiceUtil.getRepositoryFolder(getFolderId());
	}

	public Repository getRepository() throws PortalException, SystemException {
		return RepositoryLocalServiceUtil.getRepository(getFolder().getRepositoryId());
	}

	public Role getRole() throws PortalException, SystemException {

		if (getEntityType().equals(PermissionConstants.TYPE_USER)) {
			return RoleLocalServiceUtil.getRole(getEntityId());
		}

		return null;
	}

	public User getUser() throws PortalException, SystemException {

		if (getEntityType().equals(PermissionConstants.TYPE_USER)) {
			return UserLocalServiceUtil.getUser(getEntityId());
		}

		return null;
	}

	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getGroup(getRepository().getGroupId());
	}
}