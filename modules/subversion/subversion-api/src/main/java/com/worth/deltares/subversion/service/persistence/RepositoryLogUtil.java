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

import com.worth.deltares.subversion.model.RepositoryLog;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the repository log service. This utility wraps {@link com.worth.deltares.subversion.service.persistence.impl.RepositoryLogPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLogPersistence
 * @see com.worth.deltares.subversion.service.persistence.impl.RepositoryLogPersistenceImpl
 * @generated
 */
@ProviderType
public class RepositoryLogUtil {
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
	public static void clearCache(RepositoryLog repositoryLog) {
		getPersistence().clearCache(repositoryLog);
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
	public static List<RepositoryLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RepositoryLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RepositoryLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RepositoryLog> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RepositoryLog update(RepositoryLog repositoryLog) {
		return getPersistence().update(repositoryLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RepositoryLog update(RepositoryLog repositoryLog,
		ServiceContext serviceContext) {
		return getPersistence().update(repositoryLog, serviceContext);
	}

	/**
	* Caches the repository log in the entity cache if it is enabled.
	*
	* @param repositoryLog the repository log
	*/
	public static void cacheResult(RepositoryLog repositoryLog) {
		getPersistence().cacheResult(repositoryLog);
	}

	/**
	* Caches the repository logs in the entity cache if it is enabled.
	*
	* @param repositoryLogs the repository logs
	*/
	public static void cacheResult(List<RepositoryLog> repositoryLogs) {
		getPersistence().cacheResult(repositoryLogs);
	}

	/**
	* Creates a new repository log with the primary key. Does not add the repository log to the database.
	*
	* @param logId the primary key for the new repository log
	* @return the new repository log
	*/
	public static RepositoryLog create(long logId) {
		return getPersistence().create(logId);
	}

	/**
	* Removes the repository log with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param logId the primary key of the repository log
	* @return the repository log that was removed
	* @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	*/
	public static RepositoryLog remove(long logId)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryLogException {
		return getPersistence().remove(logId);
	}

	public static RepositoryLog updateImpl(RepositoryLog repositoryLog) {
		return getPersistence().updateImpl(repositoryLog);
	}

	/**
	* Returns the repository log with the primary key or throws a {@link NoSuchRepositoryLogException} if it could not be found.
	*
	* @param logId the primary key of the repository log
	* @return the repository log
	* @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	*/
	public static RepositoryLog findByPrimaryKey(long logId)
		throws com.worth.deltares.subversion.exception.NoSuchRepositoryLogException {
		return getPersistence().findByPrimaryKey(logId);
	}

	/**
	* Returns the repository log with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param logId the primary key of the repository log
	* @return the repository log, or <code>null</code> if a repository log with the primary key could not be found
	*/
	public static RepositoryLog fetchByPrimaryKey(long logId) {
		return getPersistence().fetchByPrimaryKey(logId);
	}

	public static java.util.Map<java.io.Serializable, RepositoryLog> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the repository logs.
	*
	* @return the repository logs
	*/
	public static List<RepositoryLog> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<RepositoryLog> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<RepositoryLog> findAll(int start, int end,
		OrderByComparator<RepositoryLog> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<RepositoryLog> findAll(int start, int end,
		OrderByComparator<RepositoryLog> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the repository logs from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of repository logs.
	*
	* @return the number of repository logs
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RepositoryLogPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<RepositoryLogPersistence, RepositoryLogPersistence> _serviceTracker =
		ServiceTrackerFactory.open(RepositoryLogPersistence.class);
}