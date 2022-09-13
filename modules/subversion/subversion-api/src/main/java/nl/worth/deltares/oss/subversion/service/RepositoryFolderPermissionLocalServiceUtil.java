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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;

/**
 * Provides the local service utility for RepositoryFolderPermission. This utility wraps
 * <code>nl.worth.deltares.oss.subversion.service.impl.RepositoryFolderPermissionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderPermissionLocalService
 * @generated
 */
public class RepositoryFolderPermissionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.worth.deltares.oss.subversion.service.impl.RepositoryFolderPermissionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static RepositoryFolderPermission addRepositoryFolderPermission(
		nl.worth.deltares.oss.subversion.model.RepositoryFolder
			repositoryFolder,
		com.liferay.portal.kernel.model.Role role, String permission,
		boolean recurse) {

		return getService().addRepositoryFolderPermission(
			repositoryFolder, role, permission, recurse);
	}

	public static RepositoryFolderPermission addRepositoryFolderPermission(
		nl.worth.deltares.oss.subversion.model.RepositoryFolder
			repositoryFolder,
		com.liferay.portal.kernel.model.User user, String permission,
		boolean recurse) {

		return getService().addRepositoryFolderPermission(
			repositoryFolder, user, permission, recurse);
	}

	/**
	 * Adds the repository folder permission to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderPermissionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 * @return the repository folder permission that was added
	 * @throws SystemException
	 */
	public static RepositoryFolderPermission addRepositoryFolderPermission(
			RepositoryFolderPermission repositoryFolderPermission)
		throws SystemException {

		return getService().addRepositoryFolderPermission(
			repositoryFolderPermission);
	}

	public static List<RepositoryFolderPermission>
			addRepositoryFolderPermissions(
				nl.worth.deltares.oss.subversion.model.RepositoryFolder
					repositoryFolder,
				List<RepositoryFolderPermission> permissions, boolean recurse)
		throws PortalException, SystemException {

		return getService().addRepositoryFolderPermissions(
			repositoryFolder, permissions, recurse);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static RepositoryFolderPermission
		createRepositoryFolderPermission() {

		return getService().createRepositoryFolderPermission();
	}

	/**
	 * Creates a new repository folder permission with the primary key. Does not add the repository folder permission to the database.
	 *
	 * @param permissionId the primary key for the new repository folder permission
	 * @return the new repository folder permission
	 */
	public static RepositoryFolderPermission createRepositoryFolderPermission(
		long permissionId) {

		return getService().createRepositoryFolderPermission(permissionId);
	}

	public static RepositoryFolderPermission createRepositoryFolderPermission(
		long repositoryId, String folderName,
		com.liferay.portal.kernel.model.Role role, String permission) {

		return getService().createRepositoryFolderPermission(
			repositoryId, folderName, role, permission);
	}

	public static RepositoryFolderPermission createRepositoryFolderPermission(
		long repositoryId, String folderName,
		com.liferay.portal.kernel.model.User user, String permission) {

		return getService().createRepositoryFolderPermission(
			repositoryId, folderName, user, permission);
	}

	public static RepositoryFolderPermission createRepositoryFolderPermission(
		nl.worth.deltares.oss.subversion.model.RepositoryFolder
			repositoryFolder,
		com.liferay.portal.kernel.model.Role role, String permission) {

		return getService().createRepositoryFolderPermission(
			repositoryFolder, role, permission);
	}

	public static RepositoryFolderPermission createRepositoryFolderPermission(
		nl.worth.deltares.oss.subversion.model.RepositoryFolder
			repositoryFolder,
		com.liferay.portal.kernel.model.User user, String permission) {

		return getService().createRepositoryFolderPermission(
			repositoryFolder, user, permission);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderPermissionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission that was removed
	 * @throws PortalException if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission deleteRepositoryFolderPermission(
			long permissionId)
		throws PortalException {

		return getService().deleteRepositoryFolderPermission(permissionId);
	}

	public static void deleteRepositoryFolderPermission(
		long repositoryId, long folderId, long roleId) {

		getService().deleteRepositoryFolderPermission(
			repositoryId, folderId, roleId);
	}

	/**
	 * Deletes the repository folder permission from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderPermissionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 * @return the repository folder permission that was removed
	 */
	public static RepositoryFolderPermission deleteRepositoryFolderPermission(
		RepositoryFolderPermission repositoryFolderPermission) {

		return getService().deleteRepositoryFolderPermission(
			repositoryFolderPermission);
	}

	public static void deleteRepositoryFolderPermissions(
		long repositoryId, long folderId) {

		getService().deleteRepositoryFolderPermissions(repositoryId, folderId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static RepositoryFolderPermission fetchRepositoryFolderPermission(
		long permissionId) {

		return getService().fetchRepositoryFolderPermission(permissionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the repository folder permission with the primary key.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission
	 * @throws PortalException if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission getRepositoryFolderPermission(
			long permissionId)
		throws PortalException {

		return getService().getRepositoryFolderPermission(permissionId);
	}

	public static RepositoryFolderPermission getRepositoryFolderPermission(
		long repositoryId, long folderId, long roleId) {

		return getService().getRepositoryFolderPermission(
			repositoryId, folderId, roleId);
	}

	/**
	 * Returns a range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @return the range of repository folder permissions
	 */
	public static List<RepositoryFolderPermission>
		getRepositoryFolderPermissions(int start, int end) {

		return getService().getRepositoryFolderPermissions(start, end);
	}

	public static List<RepositoryFolderPermission>
		getRepositoryFolderPermissions(long folderId) {

		return getService().getRepositoryFolderPermissions(folderId);
	}

	/**
	 * Returns the number of repository folder permissions.
	 *
	 * @return the number of repository folder permissions
	 */
	public static int getRepositoryFolderPermissionsCount() {
		return getService().getRepositoryFolderPermissionsCount();
	}

	/**
	 * Updates the repository folder permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderPermissionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 * @return the repository folder permission that was updated
	 */
	public static RepositoryFolderPermission updateRepositoryFolderPermission(
		RepositoryFolderPermission repositoryFolderPermission) {

		return getService().updateRepositoryFolderPermission(
			repositoryFolderPermission);
	}

	public static RepositoryFolderPermissionLocalService getService() {
		return _service;
	}

	private static volatile RepositoryFolderPermissionLocalService _service;

}