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

package nl.deltares.oss.download.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.oss.download.model.DownloadCount;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the download count service. This utility wraps <code>nl.deltares.oss.download.service.persistence.impl.DownloadCountPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCountPersistence
 * @generated
 */
@ProviderType
public class DownloadCountUtil {

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
	public static void clearCache(DownloadCount downloadCount) {
		getPersistence().clearCache(downloadCount);
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
	public static Map<Serializable, DownloadCount> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DownloadCount> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DownloadCount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DownloadCount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DownloadCount> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DownloadCount update(DownloadCount downloadCount) {
		return getPersistence().update(downloadCount);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DownloadCount update(
		DownloadCount downloadCount, ServiceContext serviceContext) {

		return getPersistence().update(downloadCount, serviceContext);
	}

	/**
	 * Caches the download count in the entity cache if it is enabled.
	 *
	 * @param downloadCount the download count
	 */
	public static void cacheResult(DownloadCount downloadCount) {
		getPersistence().cacheResult(downloadCount);
	}

	/**
	 * Caches the download counts in the entity cache if it is enabled.
	 *
	 * @param downloadCounts the download counts
	 */
	public static void cacheResult(List<DownloadCount> downloadCounts) {
		getPersistence().cacheResult(downloadCounts);
	}

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param downloadId the primary key for the new download count
	 * @return the new download count
	 */
	public static DownloadCount create(long downloadId) {
		return getPersistence().create(downloadId);
	}

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadId the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public static DownloadCount remove(long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().remove(downloadId);
	}

	public static DownloadCount updateImpl(DownloadCount downloadCount) {
		return getPersistence().updateImpl(downloadCount);
	}

	/**
	 * Returns the download count with the primary key or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param downloadId the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public static DownloadCount findByPrimaryKey(long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().findByPrimaryKey(downloadId);
	}

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param downloadId the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	public static DownloadCount fetchByPrimaryKey(long downloadId) {
		return getPersistence().fetchByPrimaryKey(downloadId);
	}

	/**
	 * Returns all the download counts.
	 *
	 * @return the download counts
	 */
	public static List<DownloadCount> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the download counts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @return the range of download counts
	 */
	public static List<DownloadCount> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the download counts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of download counts
	 */
	public static List<DownloadCount> findAll(
		int start, int end,
		OrderByComparator<DownloadCount> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the download counts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of download counts
	 */
	public static List<DownloadCount> findAll(
		int start, int end, OrderByComparator<DownloadCount> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the download counts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of download counts.
	 *
	 * @return the number of download counts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DownloadCountPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DownloadCountPersistence, DownloadCountPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DownloadCountPersistence.class);

		ServiceTracker<DownloadCountPersistence, DownloadCountPersistence>
			serviceTracker =
				new ServiceTracker
					<DownloadCountPersistence, DownloadCountPersistence>(
						bundle.getBundleContext(),
						DownloadCountPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}