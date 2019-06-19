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

package nl.worth.deltares.oss.subversion.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RepositoryFolderPermissionService}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionService
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionServiceWrapper
	implements RepositoryFolderPermissionService,
			   ServiceWrapper<RepositoryFolderPermissionService> {

	public RepositoryFolderPermissionServiceWrapper(
		RepositoryFolderPermissionService repositoryFolderPermissionService) {

		_repositoryFolderPermissionService = repositoryFolderPermissionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _repositoryFolderPermissionService.getOSGiServiceIdentifier();
	}

	@Override
	public RepositoryFolderPermissionService getWrappedService() {
		return _repositoryFolderPermissionService;
	}

	@Override
	public void setWrappedService(
		RepositoryFolderPermissionService repositoryFolderPermissionService) {

		_repositoryFolderPermissionService = repositoryFolderPermissionService;
	}

	private RepositoryFolderPermissionService
		_repositoryFolderPermissionService;

}