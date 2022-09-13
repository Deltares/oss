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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryFolderPermissionException;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the repository folder permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderPermissionUtil
 * @generated
 */
@ProviderType
public interface RepositoryFolderPermissionPersistence
	extends BasePersistence<RepositoryFolderPermission> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryFolderPermissionUtil} to access the repository folder permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the matching repository folder permissions
	 */
	public java.util.List<RepositoryFolderPermission> findByFolderId(
		long folderId);

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
	public java.util.List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end);

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
	public java.util.List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator);

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
	public java.util.List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	public RepositoryFolderPermission findByFolderId_First(
			long folderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException;

	/**
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	public RepositoryFolderPermission fetchByFolderId_First(
		long folderId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator);

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	public RepositoryFolderPermission findByFolderId_Last(
			long folderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException;

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	public RepositoryFolderPermission fetchByFolderId_Last(
		long folderId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator);

	/**
	 * Returns the repository folder permissions before and after the current repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param permissionId the primary key of the current repository folder permission
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public RepositoryFolderPermission[] findByFolderId_PrevAndNext(
			long permissionId, long folderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException;

	/**
	 * Removes all the repository folder permissions where folderId = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 */
	public void removeByFolderId(long folderId);

	/**
	 * Returns the number of repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the number of matching repository folder permissions
	 */
	public int countByFolderId(long folderId);

	/**
	 * Caches the repository folder permission in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 */
	public void cacheResult(
		RepositoryFolderPermission repositoryFolderPermission);

	/**
	 * Caches the repository folder permissions in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermissions the repository folder permissions
	 */
	public void cacheResult(
		java.util.List<RepositoryFolderPermission> repositoryFolderPermissions);

	/**
	 * Creates a new repository folder permission with the primary key. Does not add the repository folder permission to the database.
	 *
	 * @param permissionId the primary key for the new repository folder permission
	 * @return the new repository folder permission
	 */
	public RepositoryFolderPermission create(long permissionId);

	/**
	 * Removes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission that was removed
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public RepositoryFolderPermission remove(long permissionId)
		throws NoSuchRepositoryFolderPermissionException;

	public RepositoryFolderPermission updateImpl(
		RepositoryFolderPermission repositoryFolderPermission);

	/**
	 * Returns the repository folder permission with the primary key or throws a <code>NoSuchRepositoryFolderPermissionException</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	public RepositoryFolderPermission findByPrimaryKey(long permissionId)
		throws NoSuchRepositoryFolderPermissionException;

	/**
	 * Returns the repository folder permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission, or <code>null</code> if a repository folder permission with the primary key could not be found
	 */
	public RepositoryFolderPermission fetchByPrimaryKey(long permissionId);

	/**
	 * Returns all the repository folder permissions.
	 *
	 * @return the repository folder permissions
	 */
	public java.util.List<RepositoryFolderPermission> findAll();

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
	public java.util.List<RepositoryFolderPermission> findAll(
		int start, int end);

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
	public java.util.List<RepositoryFolderPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator);

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
	public java.util.List<RepositoryFolderPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RepositoryFolderPermission> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the repository folder permissions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of repository folder permissions.
	 *
	 * @return the number of repository folder permissions
	 */
	public int countAll();

}