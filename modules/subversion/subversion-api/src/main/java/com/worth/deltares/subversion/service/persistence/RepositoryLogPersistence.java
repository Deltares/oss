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

import com.worth.deltares.subversion.exception.NoSuchRepositoryLogException;
import com.worth.deltares.subversion.model.RepositoryLog;

/**
 * The persistence interface for the repository log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see com.worth.deltares.subversion.service.persistence.impl.RepositoryLogPersistenceImpl
 * @see RepositoryLogUtil
 * @generated
 */
@ProviderType
public interface RepositoryLogPersistence extends BasePersistence<RepositoryLog> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryLogUtil} to access the repository log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the repository log in the entity cache if it is enabled.
	*
	* @param repositoryLog the repository log
	*/
	public void cacheResult(RepositoryLog repositoryLog);

	/**
	* Caches the repository logs in the entity cache if it is enabled.
	*
	* @param repositoryLogs the repository logs
	*/
	public void cacheResult(java.util.List<RepositoryLog> repositoryLogs);

	/**
	* Creates a new repository log with the primary key. Does not add the repository log to the database.
	*
	* @param logId the primary key for the new repository log
	* @return the new repository log
	*/
	public RepositoryLog create(long logId);

	/**
	* Removes the repository log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param logId the primary key of the repository log
	* @return the repository log that was removed
	* @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	*/
	public RepositoryLog remove(long logId) throws NoSuchRepositoryLogException;

	public RepositoryLog updateImpl(RepositoryLog repositoryLog);

	/**
	* Returns the repository log with the primary key or throws a {@link NoSuchRepositoryLogException} if it could not be found.
	*
	* @param logId the primary key of the repository log
	* @return the repository log
	* @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	*/
	public RepositoryLog findByPrimaryKey(long logId)
		throws NoSuchRepositoryLogException;

	/**
	* Returns the repository log with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param logId the primary key of the repository log
	* @return the repository log, or <code>null</code> if a repository log with the primary key could not be found
	*/
	public RepositoryLog fetchByPrimaryKey(long logId);

	@Override
	public java.util.Map<java.io.Serializable, RepositoryLog> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the repository logs.
	*
	* @return the repository logs
	*/
	public java.util.List<RepositoryLog> findAll();

	/**
	* Returns a range of all the repository logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository logs
	* @param end the upper bound of the range of repository logs (not inclusive)
	* @return the range of repository logs
	*/
	public java.util.List<RepositoryLog> findAll(int start, int end);

	/**
	* Returns an ordered range of all the repository logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository logs
	* @param end the upper bound of the range of repository logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of repository logs
	*/
	public java.util.List<RepositoryLog> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryLog> orderByComparator);

	/**
	* Returns an ordered range of all the repository logs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repository logs
	* @param end the upper bound of the range of repository logs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of repository logs
	*/
	public java.util.List<RepositoryLog> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RepositoryLog> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the repository logs from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of repository logs.
	*
	* @return the number of repository logs
	*/
	public int countAll();
}