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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.oss.download.model.DownloadCount;

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
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	public static DownloadCount findByDownloadCountByGroup(
			long groupId, long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().findByDownloadCountByGroup(groupId, downloadId);
	}

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public static DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId) {

		return getPersistence().fetchByDownloadCountByGroup(
			groupId, downloadId);
	}

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public static DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId, boolean useFinderCache) {

		return getPersistence().fetchByDownloadCountByGroup(
			groupId, downloadId, useFinderCache);
	}

	/**
	 * Removes the download count where groupId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	public static DownloadCount removeByDownloadCountByGroup(
			long groupId, long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().removeByDownloadCountByGroup(
			groupId, downloadId);
	}

	/**
	 * Returns the number of download counts where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	public static int countByDownloadCountByGroup(
		long groupId, long downloadId) {

		return getPersistence().countByDownloadCountByGroup(
			groupId, downloadId);
	}

	/**
	 * Returns the download count where downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	public static DownloadCount findByDownloadCount(long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().findByDownloadCount(downloadId);
	}

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public static DownloadCount fetchByDownloadCount(long downloadId) {
		return getPersistence().fetchByDownloadCount(downloadId);
	}

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public static DownloadCount fetchByDownloadCount(
		long downloadId, boolean useFinderCache) {

		return getPersistence().fetchByDownloadCount(
			downloadId, useFinderCache);
	}

	/**
	 * Removes the download count where downloadId = &#63; from the database.
	 *
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	public static DownloadCount removeByDownloadCount(long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().removeByDownloadCount(downloadId);
	}

	/**
	 * Returns the number of download counts where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	public static int countByDownloadCount(long downloadId) {
		return getPersistence().countByDownloadCount(downloadId);
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
	 * @param id the primary key for the new download count
	 * @return the new download count
	 */
	public static DownloadCount create(long id) {
		return getPersistence().create(id);
	}

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public static DownloadCount remove(long id)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().remove(id);
	}

	public static DownloadCount updateImpl(DownloadCount downloadCount) {
		return getPersistence().updateImpl(downloadCount);
	}

	/**
	 * Returns the download count with the primary key or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public static DownloadCount findByPrimaryKey(long id)
		throws nl.deltares.oss.download.exception.NoSuchDownloadCountException {

		return getPersistence().findByPrimaryKey(id);
	}

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	public static DownloadCount fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of download counts
	 */
	public static List<DownloadCount> findAll(
		int start, int end, OrderByComparator<DownloadCount> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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
		return _persistence;
	}

	private static volatile DownloadCountPersistence _persistence;

}