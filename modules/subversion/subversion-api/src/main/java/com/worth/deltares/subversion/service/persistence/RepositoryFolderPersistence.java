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

package com.worth.deltares.subversion.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException;
import com.worth.deltares.subversion.model.RepositoryFolder;

/**
 * The persistence interface for the repository folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see com.worth.deltares.subversion.service.persistence.impl.RepositoryFolderPersistenceImpl
 * @see RepositoryFolderUtil
 * @generated
 */
@ProviderType
public interface RepositoryFolderPersistence extends BasePersistence<RepositoryFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryFolderUtil} to access the repository folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the repository folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching repository folders
	*/
	public java.util.List<RepositoryFolder> findByRepositoryId(
		long repositoryId);

	/**
	* Returns a range of all the repository folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @return the range of matching repository folders
	*/
	public java.util.List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end);

	/**
	* Returns an ordered range of all the repository folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching repository folders
	*/
	public java.util.List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator);

	/**
	* Returns an ordered range of all the repository folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching repository folders
	*/
	public java.util.List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository folder
	* @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	*/
	public RepositoryFolder findByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException;

	/**
	* Returns the first repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository folder, or <code>null</code> if a matching repository folder could not be found
	*/
	public RepositoryFolder fetchByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator);

	/**
	* Returns the last repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository folder
	* @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	*/
	public RepositoryFolder findByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException;

	/**
	* Returns the last repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository folder, or <code>null</code> if a matching repository folder could not be found
	*/
	public RepositoryFolder fetchByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator);

	/**
	* Returns the repository folders before and after the current repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param folderId the primary key of the current repository folder
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository folder
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public RepositoryFolder[] findByRepositoryId_PrevAndNext(long folderId,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException;

	/**
	* Removes all the repository folders where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public void removeByRepositoryId(long repositoryId);

	/**
	* Returns the number of repository folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching repository folders
	*/
	public int countByRepositoryId(long repositoryId);

	/**
	* Caches the repository folder in the entity cache if it is enabled.
	*
	* @param repositoryFolder the repository folder
	*/
	public void cacheResult(RepositoryFolder repositoryFolder);

	/**
	* Caches the repository folders in the entity cache if it is enabled.
	*
	* @param repositoryFolders the repository folders
	*/
	public void cacheResult(java.util.List<RepositoryFolder> repositoryFolders);

	/**
	* Creates a new repository folder with the primary key. Does not add the repository folder to the database.
	*
	* @param folderId the primary key for the new repository folder
	* @return the new repository folder
	*/
	public RepositoryFolder create(long folderId);

	/**
	* Removes the repository folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder that was removed
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public RepositoryFolder remove(long folderId)
		throws NoSuchRepositoryFolderException;

	public RepositoryFolder updateImpl(RepositoryFolder repositoryFolder);

	/**
	* Returns the repository folder with the primary key or throws a {@link NoSuchRepositoryFolderException} if it could not be found.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public RepositoryFolder findByPrimaryKey(long folderId)
		throws NoSuchRepositoryFolderException;

	/**
	* Returns the repository folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder, or <code>null</code> if a repository folder with the primary key could not be found
	*/
	public RepositoryFolder fetchByPrimaryKey(long folderId);

	@Override
	public java.util.Map<java.io.Serializable, RepositoryFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the repository folders.
	*
	* @return the repository folders
	*/
	public java.util.List<RepositoryFolder> findAll();

	/**
	* Returns a range of all the repository folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @return the range of repository folders
	*/
	public java.util.List<RepositoryFolder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the repository folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of repository folders
	*/
	public java.util.List<RepositoryFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator);

	/**
	* Returns an ordered range of all the repository folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository folders
	* @param end the upper bound of the range of repository folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of repository folders
	*/
	public java.util.List<RepositoryFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the repository folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of repository folders.
	*
	* @return the number of repository folders
	*/
	public int countAll();
}