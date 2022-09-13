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

package nl.worth.deltares.oss.subversion.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;

/**
 * The persistence utility for the repository folder permission service. This utility wraps <code>nl.worth.deltares.oss.subversion.service.persistence.impl.RepositoryFolderPermissionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderPermissionPersistence
 * @generated
 */
public class RepositoryFolderPermissionUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		RepositoryFolderPermission repositoryFolderPermission) {

		getPersistence().clearCache(repositoryFolderPermission);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, RepositoryFolderPermission>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RepositoryFolderPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RepositoryFolderPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RepositoryFolderPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RepositoryFolderPermission update(
		RepositoryFolderPermission repositoryFolderPermission) {

		return getPersistence().update(repositoryFolderPermission);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RepositoryFolderPermission update(
		RepositoryFolderPermission repositoryFolderPermission,
		ServiceContext serviceContext) {

		return getPersistence().update(
			repositoryFolderPermission, serviceContext);
	}

	/**
	 * Returns all the repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the matching repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findByFolderId(
		long folderId) {

		return getPersistence().findByFolderId(folderId);
	}

	/**
	 * Returns a range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @return the range of matching repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end) {

		return getPersistence().findByFolderId(folderId, start, end);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return getPersistence().findByFolderId(
			folderId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFolderId(
			folderId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	public static RepositoryFolderPermission findByFolderId_First(
			long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws nl.worth.deltares.oss.subversion.exception.
			NoSuchRepositoryFolderPermissionException {

		return getPersistence().findByFolderId_First(
			folderId, orderByComparator);
	}

	/**
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	public static RepositoryFolderPermission fetchByFolderId_First(
		long folderId,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return getPersistence().fetchByFolderId_First(
			folderId, orderByComparator);
	}

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	public static RepositoryFolderPermission findByFolderId_Last(
			long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws nl.worth.deltares.oss.subversion.exception.
			NoSuchRepositoryFolderPermissionException {

		return getPersistence().findByFolderId_Last(
			folderId, orderByComparator);
	}

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	public static RepositoryFolderPermission fetchByFolderId_Last(
		long folderId,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return getPersistence().fetchByFolderId_Last(
			folderId, orderByComparator);
	}

	/**
	 * Returns the repository folder permissions before and after the current repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param permissionId the primary key of the current repository folder permission
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission[] findByFolderId_PrevAndNext(
			long permissionId, long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws nl.worth.deltares.oss.subversion.exception.
			NoSuchRepositoryFolderPermissionException {

		return getPersistence().findByFolderId_PrevAndNext(
			permissionId, folderId, orderByComparator);
	}

	/**
	 * Removes all the repository folder permissions where folderId = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 */
	public static void removeByFolderId(long folderId) {
		getPersistence().removeByFolderId(folderId);
	}

	/**
	 * Returns the number of repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the number of matching repository folder permissions
	 */
	public static int countByFolderId(long folderId) {
		return getPersistence().countByFolderId(folderId);
	}

	/**
	 * Caches the repository folder permission in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 */
	public static void cacheResult(
		RepositoryFolderPermission repositoryFolderPermission) {

		getPersistence().cacheResult(repositoryFolderPermission);
	}

	/**
	 * Caches the repository folder permissions in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermissions the repository folder permissions
	 */
	public static void cacheResult(
		List<RepositoryFolderPermission> repositoryFolderPermissions) {

		getPersistence().cacheResult(repositoryFolderPermissions);
	}

	/**
	 * Creates a new repository folder permission with the primary key. Does not add the repository folder permission to the database.
	 *
	 * @param permissionId the primary key for the new repository folder permission
	 * @return the new repository folder permission
	 */
	public static RepositoryFolderPermission create(long permissionId) {
		return getPersistence().create(permissionId);
	}

	/**
	 * Removes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission that was removed
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission remove(long permissionId)
		throws nl.worth.deltares.oss.subversion.exception.
			NoSuchRepositoryFolderPermissionException {

		return getPersistence().remove(permissionId);
	}

	public static RepositoryFolderPermission updateImpl(
		RepositoryFolderPermission repositoryFolderPermission) {

		return getPersistence().updateImpl(repositoryFolderPermission);
	}

	/**
	 * Returns the repository folder permission with the primary key or throws a <code>NoSuchRepositoryFolderPermissionException</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission findByPrimaryKey(long permissionId)
		throws nl.worth.deltares.oss.subversion.exception.
			NoSuchRepositoryFolderPermissionException {

		return getPersistence().findByPrimaryKey(permissionId);
	}

	/**
	 * Returns the repository folder permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission, or <code>null</code> if a repository folder permission with the primary key could not be found
	 */
	public static RepositoryFolderPermission fetchByPrimaryKey(
		long permissionId) {

		return getPersistence().fetchByPrimaryKey(permissionId);
	}

	/**
	 * Returns all the repository folder permissions.
	 *
	 * @return the repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @return the range of repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of repository folder permissions
	 */
	public static List<RepositoryFolderPermission> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the repository folder permissions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of repository folder permissions.
	 *
	 * @return the number of repository folder permissions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RepositoryFolderPermissionPersistence getPersistence() {
		return _persistence;
	}

	private static volatile RepositoryFolderPermissionPersistence _persistence;

}