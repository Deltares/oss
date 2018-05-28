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

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.worth.deltares.subversion.model.RepositoryFolder;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the repository folder service. This utility wraps {@link com.worth.deltares.subversion.service.persistence.impl.RepositoryFolderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPersistence
 * @see com.worth.deltares.subversion.service.persistence.impl.RepositoryFolderPersistenceImpl
 * @generated
 */
@ProviderType
public class RepositoryFolderUtil {
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
	public static void clearCache(RepositoryFolder repositoryFolder) {
		getPersistence().clearCache(repositoryFolder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RepositoryFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RepositoryFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RepositoryFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RepositoryFolder update(RepositoryFolder repositoryFolder) {
		return getPersistence().update(repositoryFolder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RepositoryFolder update(RepositoryFolder repositoryFolder,
		ServiceContext serviceContext) {
		return getPersistence().update(repositoryFolder, serviceContext);
	}

	/**
	* Returns all the repository folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching repository folders
	*/
	public static List<RepositoryFolder> findByRepositoryId(long repositoryId) {
		return getPersistence().findByRepositoryId(repositoryId);
	}

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
	public static List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end) {
		return getPersistence().findByRepositoryId(repositoryId, start, end);
	}

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
	public static List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return getPersistence()
				   .findByRepositoryId(repositoryId, start, end,
			orderByComparator);
	}

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
	public static List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRepositoryId(repositoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository folder
	* @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	*/
	public static RepositoryFolder findByRepositoryId_First(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException {
		return getPersistence()
				   .findByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the first repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching repository folder, or <code>null</code> if a matching repository folder could not be found
	*/
	public static RepositoryFolder fetchByRepositoryId_First(
		long repositoryId, OrderByComparator<RepositoryFolder> orderByComparator) {
		return getPersistence()
				   .fetchByRepositoryId_First(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository folder
	* @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	*/
	public static RepositoryFolder findByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException {
		return getPersistence()
				   .findByRepositoryId_Last(repositoryId, orderByComparator);
	}

	/**
	* Returns the last repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching repository folder, or <code>null</code> if a matching repository folder could not be found
	*/
	public static RepositoryFolder fetchByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return getPersistence()
				   .fetchByRepositoryId_Last(repositoryId, orderByComparator);
	}

	/**
	* Returns the repository folders before and after the current repository folder in the ordered set where repositoryId = &#63;.
	*
	* @param folderId the primary key of the current repository folder
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next repository folder
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public static RepositoryFolder[] findByRepositoryId_PrevAndNext(
		long folderId, long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException {
		return getPersistence()
				   .findByRepositoryId_PrevAndNext(folderId, repositoryId,
			orderByComparator);
	}

	/**
	* Removes all the repository folders where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public static void removeByRepositoryId(long repositoryId) {
		getPersistence().removeByRepositoryId(repositoryId);
	}

	/**
	* Returns the number of repository folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching repository folders
	*/
	public static int countByRepositoryId(long repositoryId) {
		return getPersistence().countByRepositoryId(repositoryId);
	}

	/**
	* Caches the repository folder in the entity cache if it is enabled.
	*
	* @param repositoryFolder the repository folder
	*/
	public static void cacheResult(RepositoryFolder repositoryFolder) {
		getPersistence().cacheResult(repositoryFolder);
	}

	/**
	* Caches the repository folders in the entity cache if it is enabled.
	*
	* @param repositoryFolders the repository folders
	*/
	public static void cacheResult(List<RepositoryFolder> repositoryFolders) {
		getPersistence().cacheResult(repositoryFolders);
	}

	/**
	* Creates a new repository folder with the primary key. Does not add the repository folder to the database.
	*
	* @param folderId the primary key for the new repository folder
	* @return the new repository folder
	*/
	public static RepositoryFolder create(long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	* Removes the repository folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder that was removed
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public static RepositoryFolder remove(long folderId)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException {
		return getPersistence().remove(folderId);
	}

	public static RepositoryFolder updateImpl(RepositoryFolder repositoryFolder) {
		return getPersistence().updateImpl(repositoryFolder);
	}

	/**
	* Returns the repository folder with the primary key or throws a {@link NoSuchRepositoryFolderException} if it could not be found.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder
	* @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	*/
	public static RepositoryFolder findByPrimaryKey(long folderId)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException {
		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	* Returns the repository folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the repository folder
	* @return the repository folder, or <code>null</code> if a repository folder with the primary key could not be found
	*/
	public static RepositoryFolder fetchByPrimaryKey(long folderId) {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	public static java.util.Map<java.io.Serializable, RepositoryFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the repository folders.
	*
	* @return the repository folders
	*/
	public static List<RepositoryFolder> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<RepositoryFolder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<RepositoryFolder> findAll(int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<RepositoryFolder> findAll(int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the repository folders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of repository folders.
	*
	* @return the number of repository folders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RepositoryFolderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<RepositoryFolderPersistence, RepositoryFolderPersistence> _serviceTracker =
		ServiceTrackerFactory.open(RepositoryFolderPersistence.class);
}