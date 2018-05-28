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

package com.worth.deltares.subversion.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RepositoryLogService}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogService
 * @generated
 */
@ProviderType
public class RepositoryLogServiceWrapper implements RepositoryLogService,
	ServiceWrapper<RepositoryLogService> {
	public RepositoryLogServiceWrapper(
		RepositoryLogService repositoryLogService) {
		_repositoryLogService = repositoryLogService;
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getLastLogs(
		java.lang.Integer number) {
		return _repositoryLogService.getLastLogs(number);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _repositoryLogService.getOSGiServiceIdentifier();
	}

	@Override
	public RepositoryLogService getWrappedService() {
		return _repositoryLogService;
	}

	@Override
	public void setWrappedService(RepositoryLogService repositoryLogService) {
		_repositoryLogService = repositoryLogService;
	}

	private RepositoryLogService _repositoryLogService;
}