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

package com.worth.deltares.subversion.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the RepositoryFolderPermission service. Represents a row in the &quot;deltares_RepositoryFolderPermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionModel
 * @see com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionImpl
 * @see com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionModelImpl
 * @generated
 */
@ImplementationClassName("com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionImpl")
@ProviderType
public interface RepositoryFolderPermission
	extends RepositoryFolderPermissionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RepositoryFolderPermission, Long> PERMISSION_ID_ACCESSOR =
			new Accessor<RepositoryFolderPermission, Long>() {
				@Override
				public Long get(
						RepositoryFolderPermission repositoryFolderPermission) {
					return repositoryFolderPermission.getPermissionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<RepositoryFolderPermission> getTypeClass() {
					return RepositoryFolderPermission.class;
				}
			};

	public Repository getRepository()
			throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public RepositoryFolder getFolder()
			throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.model.Role getRole()
			throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.model.User getUser()
			throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.model.Group getGroup()
			throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

}