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
 * Provides a wrapper for {@link RepositoryLogLocalService}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogLocalService
 * @generated
 */
@ProviderType
public class RepositoryLogLocalServiceWrapper
	implements RepositoryLogLocalService,
		ServiceWrapper<RepositoryLogLocalService> {
	public RepositoryLogLocalServiceWrapper(
		RepositoryLogLocalService repositoryLogLocalService) {
		_repositoryLogLocalService = repositoryLogLocalService;
	}

	/**
	* Adds the repository log to the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryLog the repository log
	* @return the repository log that was added
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog addRepositoryLog(
		com.worth.deltares.subversion.model.RepositoryLog repositoryLog) {
		return _repositoryLogLocalService.addRepositoryLog(repositoryLog);
	}

	/**
	* Creates a new repository log with the primary key. Does not add the repository log to the database.
	*
	* @param logId the primary key for the new repository log
	* @return the new repository log
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog createRepositoryLog(
		long logId) {
		return _repositoryLogLocalService.createRepositoryLog(logId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryLogLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the repository log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param logId the primary key of the repository log
	* @return the repository log that was removed
	* @throws PortalException if a repository log with the primary key could not be found
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog deleteRepositoryLog(
		long logId) throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryLogLocalService.deleteRepositoryLog(logId);
	}

	/**
	* Deletes the repository log from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryLog the repository log
	* @return the repository log that was removed
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog deleteRepositoryLog(
		com.worth.deltares.subversion.model.RepositoryLog repositoryLog) {
		return _repositoryLogLocalService.deleteRepositoryLog(repositoryLog);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _repositoryLogLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _repositoryLogLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _repositoryLogLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _repositoryLogLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _repositoryLogLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _repositoryLogLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryLog fetchRepositoryLog(
		long logId) {
		return _repositoryLogLocalService.fetchRepositoryLog(logId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _repositoryLogLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _repositoryLogLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getLastLogs(
		java.lang.Integer number) {
		return _repositoryLogLocalService.getLastLogs(number);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _repositoryLogLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryLogLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository log with the primary key.
	*
	* @param logId the primary key of the repository log
	* @return the repository log
	* @throws PortalException if a repository log with the primary key could not be found
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog getRepositoryLog(
		long logId) throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryLogLocalService.getRepositoryLog(logId);
	}

	/**
	* Returns a range of all the repository logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository logs
	* @param end the upper bound of the range of repository logs (not inclusive)
	* @return the range of repository logs
	*/
	@Override
	public java.util.List<com.worth.deltares.subversion.model.RepositoryLog> getRepositoryLogs(
		int start, int end) {
		return _repositoryLogLocalService.getRepositoryLogs(start, end);
	}

	/**
	* Returns the number of repository logs.
	*
	* @return the number of repository logs
	*/
	@Override
	public int getRepositoryLogsCount() {
		return _repositoryLogLocalService.getRepositoryLogsCount();
	}

	@Override
	public int getRepositoryLogsCount(java.lang.String action) {
		return _repositoryLogLocalService.getRepositoryLogsCount(action);
	}

	@Override
	public int getRepositoryLogsCount(java.lang.String repository,
		java.lang.String action) {
		return _repositoryLogLocalService.getRepositoryLogsCount(repository,
			action);
	}

	@Override
	public int getRepositoryLogsCount(java.lang.String screenName,
		java.lang.String ipAddress, java.lang.String repository) {
		return _repositoryLogLocalService.getRepositoryLogsCount(screenName,
			ipAddress, repository);
	}

	/**
	* Updates the repository log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryLog the repository log
	* @return the repository log that was updated
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryLog updateRepositoryLog(
		com.worth.deltares.subversion.model.RepositoryLog repositoryLog) {
		return _repositoryLogLocalService.updateRepositoryLog(repositoryLog);
	}

	@Override
	public RepositoryLogLocalService getWrappedService() {
		return _repositoryLogLocalService;
	}

	@Override
	public void setWrappedService(
		RepositoryLogLocalService repositoryLogLocalService) {
		_repositoryLogLocalService = repositoryLogLocalService;
	}

	private RepositoryLogLocalService _repositoryLogLocalService;
}