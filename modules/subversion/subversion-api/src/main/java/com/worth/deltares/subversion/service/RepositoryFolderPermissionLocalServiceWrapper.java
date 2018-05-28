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
 * Provides a wrapper for {@link RepositoryFolderPermissionLocalService}.
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionLocalService
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionLocalServiceWrapper
	implements RepositoryFolderPermissionLocalService,
		ServiceWrapper<RepositoryFolderPermissionLocalService> {
	public RepositoryFolderPermissionLocalServiceWrapper(
		RepositoryFolderPermissionLocalService repositoryFolderPermissionLocalService) {
		_repositoryFolderPermissionLocalService = repositoryFolderPermissionLocalService;
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission addRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolder folder,
		com.liferay.portal.kernel.model.Role role, java.lang.String permission,
		boolean recurse) {
		return _repositoryFolderPermissionLocalService.addRepositoryFolderPermission(folder,
			role, permission, recurse);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission addRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolder folder,
		com.liferay.portal.kernel.model.User user, java.lang.String permission,
		boolean recurse) {
		return _repositoryFolderPermissionLocalService.addRepositoryFolderPermission(folder,
			user, permission, recurse);
	}

	/**
	* Adds the repository folder permission to the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryFolderPermission the repository folder permission
	* @return the repository folder permission that was added
	* @throws SystemException
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission addRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolderPermission repositoryFolderPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermissionLocalService.addRepositoryFolderPermission(repositoryFolderPermission);
	}

	@Override
	public java.util.List<com.worth.deltares.subversion.model.RepositoryFolderPermission> addRepositoryFolderPermissions(
		com.worth.deltares.subversion.model.RepositoryFolder folder,
		java.util.List<com.worth.deltares.subversion.model.RepositoryFolderPermission> permissions,
		boolean recurse)
		throws java.lang.Exception,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermissionLocalService.addRepositoryFolderPermissions(folder,
			permissions, recurse);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission() {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission();
	}

	/**
	* Creates a new repository folder permission with the primary key. Does not add the repository folder permission to the database.
	*
	* @param permissionId the primary key for the new repository folder permission
	* @return the new repository folder permission
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission(
		long permissionId) {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission(permissionId);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission(
		long repositoryId, java.lang.String folderName,
		com.liferay.portal.kernel.model.Role role, java.lang.String permission) {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission(repositoryId,
			folderName, role, permission);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission(
		long repositoryId, java.lang.String folderName,
		com.liferay.portal.kernel.model.User user, java.lang.String permission) {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission(repositoryId,
			folderName, user, permission);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolder folder,
		com.liferay.portal.kernel.model.Role role, java.lang.String permission) {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission(folder,
			role, permission);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission createRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolder folder,
		com.liferay.portal.kernel.model.User user, java.lang.String permission) {
		return _repositoryFolderPermissionLocalService.createRepositoryFolderPermission(folder,
			user, permission);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryFolderPermissionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param permissionId the primary key of the repository folder permission
	* @return the repository folder permission that was removed
	* @throws PortalException if a repository folder permission with the primary key could not be found
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission deleteRepositoryFolderPermission(
		long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryFolderPermissionLocalService.deleteRepositoryFolderPermission(permissionId);
	}

	@Override
	public void deleteRepositoryFolderPermission(long repositoryId,
		long folderId, long roleId) {
		_repositoryFolderPermissionLocalService.deleteRepositoryFolderPermission(repositoryId,
			folderId, roleId);
	}

	/**
	* Deletes the repository folder permission from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryFolderPermission the repository folder permission
	* @return the repository folder permission that was removed
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission deleteRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolderPermission repositoryFolderPermission) {
		return _repositoryFolderPermissionLocalService.deleteRepositoryFolderPermission(repositoryFolderPermission);
	}

	@Override
	public void deleteRepositoryFolderPermissions(long repositoryId,
		long folderId) {
		_repositoryFolderPermissionLocalService.deleteRepositoryFolderPermissions(repositoryId,
			folderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _repositoryFolderPermissionLocalService.dynamicQuery();
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
		return _repositoryFolderPermissionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _repositoryFolderPermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _repositoryFolderPermissionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _repositoryFolderPermissionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _repositoryFolderPermissionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission fetchRepositoryFolderPermission(
		long permissionId) {
		return _repositoryFolderPermissionLocalService.fetchRepositoryFolderPermission(permissionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _repositoryFolderPermissionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _repositoryFolderPermissionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _repositoryFolderPermissionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryFolderPermissionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository folder permission with the primary key.
	*
	* @param permissionId the primary key of the repository folder permission
	* @return the repository folder permission
	* @throws PortalException if a repository folder permission with the primary key could not be found
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission getRepositoryFolderPermission(
		long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _repositoryFolderPermissionLocalService.getRepositoryFolderPermission(permissionId);
	}

	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission getRepositoryFolderPermission(
		long repositoryId, long folderId, long roleId) {
		return _repositoryFolderPermissionLocalService.getRepositoryFolderPermission(repositoryId,
			folderId, roleId);
	}

	/**
	* Returns a range of all the repository folder permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.worth.deltares.subversion.model.impl.RepositoryFolderPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository folder permissions
	* @param end the upper bound of the range of repository folder permissions (not inclusive)
	* @return the range of repository folder permissions
	*/
	@Override
	public java.util.List<com.worth.deltares.subversion.model.RepositoryFolderPermission> getRepositoryFolderPermissions(
		int start, int end) {
		return _repositoryFolderPermissionLocalService.getRepositoryFolderPermissions(start,
			end);
	}

	@Override
	public java.util.List<com.worth.deltares.subversion.model.RepositoryFolderPermission> getRepositoryFolderPermissions(
		long repositoryId, long folderId) {
		return _repositoryFolderPermissionLocalService.getRepositoryFolderPermissions(repositoryId,
			folderId);
	}

	/**
	* Returns the number of repository folder permissions.
	*
	* @return the number of repository folder permissions
	*/
	@Override
	public int getRepositoryFolderPermissionsCount() {
		return _repositoryFolderPermissionLocalService.getRepositoryFolderPermissionsCount();
	}

	/**
	* Updates the repository folder permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryFolderPermission the repository folder permission
	* @return the repository folder permission that was updated
	*/
	@Override
	public com.worth.deltares.subversion.model.RepositoryFolderPermission updateRepositoryFolderPermission(
		com.worth.deltares.subversion.model.RepositoryFolderPermission repositoryFolderPermission) {
		return _repositoryFolderPermissionLocalService.updateRepositoryFolderPermission(repositoryFolderPermission);
	}

	@Override
	public RepositoryFolderPermissionLocalService getWrappedService() {
		return _repositoryFolderPermissionLocalService;
	}

	@Override
	public void setWrappedService(
		RepositoryFolderPermissionLocalService repositoryFolderPermissionLocalService) {
		_repositoryFolderPermissionLocalService = repositoryFolderPermissionLocalService;
	}

	private RepositoryFolderPermissionLocalService _repositoryFolderPermissionLocalService;
}