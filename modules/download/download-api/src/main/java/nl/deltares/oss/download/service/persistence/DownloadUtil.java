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

import nl.deltares.oss.download.model.Download;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the download service. This utility wraps <code>nl.deltares.oss.download.service.persistence.impl.DownloadPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadPersistence
 * @generated
 */
@ProviderType
public class DownloadUtil {

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
	public static void clearCache(Download download) {
		getPersistence().clearCache(download);
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
	public static Map<Serializable, Download> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Download> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Download> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Download> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Download update(Download download) {
		return getPersistence().update(download);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Download update(
		Download download, ServiceContext serviceContext) {

		return getPersistence().update(download, serviceContext);
	}

	/**
	 * Returns all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching downloads
	 */
	public static List<Download> findByDownloads(
		long groupId, long downloadId) {

		return getPersistence().findByDownloads(groupId, downloadId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end) {

		return getPersistence().findByDownloads(
			groupId, downloadId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByDownloads(
			groupId, downloadId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByDownloads(
			groupId, downloadId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDownloads_First(
			long groupId, long downloadId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloads_First(
			groupId, downloadId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDownloads_First(
		long groupId, long downloadId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDownloads_First(
			groupId, downloadId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDownloads_Last(
			long groupId, long downloadId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloads_Last(
			groupId, downloadId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDownloads_Last(
		long groupId, long downloadId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDownloads_Last(
			groupId, downloadId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByDownloads_PrevAndNext(
			long id, long groupId, long downloadId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloads_PrevAndNext(
			id, groupId, downloadId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 */
	public static void removeByDownloads(long groupId, long downloadId) {
		getPersistence().removeByDownloads(groupId, downloadId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the number of matching downloads
	 */
	public static int countByDownloads(long groupId, long downloadId) {
		return getPersistence().countByDownloads(groupId, downloadId);
	}

	/**
	 * Returns all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @return the matching downloads
	 */
	public static List<Download> findByDownloadsByShareId(
		long groupId, int shareId) {

		return getPersistence().findByDownloadsByShareId(groupId, shareId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end) {

		return getPersistence().findByDownloadsByShareId(
			groupId, shareId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByDownloadsByShareId(
			groupId, shareId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByDownloadsByShareId(
			groupId, shareId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDownloadsByShareId_First(
			long groupId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloadsByShareId_First(
			groupId, shareId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDownloadsByShareId_First(
		long groupId, int shareId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDownloadsByShareId_First(
			groupId, shareId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDownloadsByShareId_Last(
			long groupId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloadsByShareId_Last(
			groupId, shareId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDownloadsByShareId_Last(
		long groupId, int shareId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDownloadsByShareId_Last(
			groupId, shareId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByDownloadsByShareId_PrevAndNext(
			long id, long groupId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDownloadsByShareId_PrevAndNext(
			id, groupId, shareId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; and shareId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 */
	public static void removeByDownloadsByShareId(long groupId, int shareId) {
		getPersistence().removeByDownloadsByShareId(groupId, shareId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @return the number of matching downloads
	 */
	public static int countByDownloadsByShareId(long groupId, int shareId) {
		return getPersistence().countByDownloadsByShareId(groupId, shareId);
	}

	/**
	 * Returns all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @return the matching downloads
	 */
	public static List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId) {

		return getPersistence().findByUserDownloadsByShareId(
			groupId, userId, shareId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end) {

		return getPersistence().findByUserDownloadsByShareId(
			groupId, userId, shareId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByUserDownloadsByShareId(
			groupId, userId, shareId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserDownloadsByShareId(
			groupId, userId, shareId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByUserDownloadsByShareId_First(
			long groupId, long userId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloadsByShareId_First(
			groupId, userId, shareId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownloadsByShareId_First(
		long groupId, long userId, int shareId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByUserDownloadsByShareId_First(
			groupId, userId, shareId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByUserDownloadsByShareId_Last(
			long groupId, long userId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloadsByShareId_Last(
			groupId, userId, shareId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownloadsByShareId_Last(
		long groupId, long userId, int shareId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByUserDownloadsByShareId_Last(
			groupId, userId, shareId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByUserDownloadsByShareId_PrevAndNext(
			long id, long groupId, long userId, int shareId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloadsByShareId_PrevAndNext(
			id, groupId, userId, shareId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 */
	public static void removeByUserDownloadsByShareId(
		long groupId, long userId, int shareId) {

		getPersistence().removeByUserDownloadsByShareId(
			groupId, userId, shareId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @return the number of matching downloads
	 */
	public static int countByUserDownloadsByShareId(
		long groupId, long userId, int shareId) {

		return getPersistence().countByUserDownloadsByShareId(
			groupId, userId, shareId);
	}

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or throws a <code>NoSuchDownloadException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByUserDownload(
			long groupId, long userId, long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownload(groupId, userId, downloadId);
	}

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownload(
		long groupId, long userId, long downloadId) {

		return getPersistence().fetchByUserDownload(
			groupId, userId, downloadId);
	}

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownload(
		long groupId, long userId, long downloadId, boolean retrieveFromCache) {

		return getPersistence().fetchByUserDownload(
			groupId, userId, downloadId, retrieveFromCache);
	}

	/**
	 * Removes the download where groupId = &#63; and userId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the download that was removed
	 */
	public static Download removeByUserDownload(
			long groupId, long userId, long downloadId)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().removeByUserDownload(
			groupId, userId, downloadId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the number of matching downloads
	 */
	public static int countByUserDownload(
		long groupId, long userId, long downloadId) {

		return getPersistence().countByUserDownload(
			groupId, userId, downloadId);
	}

	/**
	 * Returns all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching downloads
	 */
	public static List<Download> findByUserDownloads(
		long groupId, long userId) {

		return getPersistence().findByUserDownloads(groupId, userId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end) {

		return getPersistence().findByUserDownloads(
			groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByUserDownloads(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserDownloads(
			groupId, userId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByUserDownloads_First(
			long groupId, long userId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloads_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownloads_First(
		long groupId, long userId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByUserDownloads_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByUserDownloads_Last(
			long groupId, long userId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloads_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByUserDownloads_Last(
		long groupId, long userId,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByUserDownloads_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByUserDownloads_PrevAndNext(
			long id, long groupId, long userId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByUserDownloads_PrevAndNext(
			id, groupId, userId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public static void removeByUserDownloads(long groupId, long userId) {
		getPersistence().removeByUserDownloads(groupId, userId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching downloads
	 */
	public static int countByUserDownloads(long groupId, long userId) {
		return getPersistence().countByUserDownloads(groupId, userId);
	}

	/**
	 * Returns all the downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching downloads
	 */
	public static List<Download> findByDirectDownloads(long groupId) {
		return getPersistence().findByDirectDownloads(groupId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByDirectDownloads(
		long groupId, int start, int end) {

		return getPersistence().findByDirectDownloads(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDirectDownloads(
		long groupId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByDirectDownloads(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByDirectDownloads(
		long groupId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByDirectDownloads(
			groupId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDirectDownloads_First(
			long groupId, OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDirectDownloads_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDirectDownloads_First(
		long groupId, OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDirectDownloads_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByDirectDownloads_Last(
			long groupId, OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDirectDownloads_Last(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByDirectDownloads_Last(
		long groupId, OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByDirectDownloads_Last(
			groupId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByDirectDownloads_PrevAndNext(
			long id, long groupId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByDirectDownloads_PrevAndNext(
			id, groupId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByDirectDownloads(long groupId) {
		getPersistence().removeByDirectDownloads(groupId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching downloads
	 */
	public static int countByDirectDownloads(long groupId) {
		return getPersistence().countByDirectDownloads(groupId);
	}

	/**
	 * Returns all the downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching downloads
	 */
	public static List<Download> findByGroupDownloads(long groupId) {
		return getPersistence().findByGroupDownloads(groupId);
	}

	/**
	 * Returns a range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public static List<Download> findByGroupDownloads(
		long groupId, int start, int end) {

		return getPersistence().findByGroupDownloads(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByGroupDownloads(
		long groupId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return getPersistence().findByGroupDownloads(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	public static List<Download> findByGroupDownloads(
		long groupId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByGroupDownloads(
			groupId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByGroupDownloads_First(
			long groupId, OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByGroupDownloads_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByGroupDownloads_First(
		long groupId, OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByGroupDownloads_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public static Download findByGroupDownloads_Last(
			long groupId, OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByGroupDownloads_Last(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public static Download fetchByGroupDownloads_Last(
		long groupId, OrderByComparator<Download> orderByComparator) {

		return getPersistence().fetchByGroupDownloads_Last(
			groupId, orderByComparator);
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download[] findByGroupDownloads_PrevAndNext(
			long id, long groupId,
			OrderByComparator<Download> orderByComparator)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByGroupDownloads_PrevAndNext(
			id, groupId, orderByComparator);
	}

	/**
	 * Removes all the downloads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupDownloads(long groupId) {
		getPersistence().removeByGroupDownloads(groupId);
	}

	/**
	 * Returns the number of downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching downloads
	 */
	public static int countByGroupDownloads(long groupId) {
		return getPersistence().countByGroupDownloads(groupId);
	}

	/**
	 * Caches the download in the entity cache if it is enabled.
	 *
	 * @param download the download
	 */
	public static void cacheResult(Download download) {
		getPersistence().cacheResult(download);
	}

	/**
	 * Caches the downloads in the entity cache if it is enabled.
	 *
	 * @param downloads the downloads
	 */
	public static void cacheResult(List<Download> downloads) {
		getPersistence().cacheResult(downloads);
	}

	/**
	 * Creates a new download with the primary key. Does not add the download to the database.
	 *
	 * @param id the primary key for the new download
	 * @return the new download
	 */
	public static Download create(long id) {
		return getPersistence().create(id);
	}

	/**
	 * Removes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download
	 * @return the download that was removed
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download remove(long id)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().remove(id);
	}

	public static Download updateImpl(Download download) {
		return getPersistence().updateImpl(download);
	}

	/**
	 * Returns the download with the primary key or throws a <code>NoSuchDownloadException</code> if it could not be found.
	 *
	 * @param id the primary key of the download
	 * @return the download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public static Download findByPrimaryKey(long id)
		throws nl.deltares.oss.download.exception.NoSuchDownloadException {

		return getPersistence().findByPrimaryKey(id);
	}

	/**
	 * Returns the download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the download
	 * @return the download, or <code>null</code> if a download with the primary key could not be found
	 */
	public static Download fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	 * Returns all the downloads.
	 *
	 * @return the downloads
	 */
	public static List<Download> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of downloads
	 */
	public static List<Download> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of downloads
	 */
	public static List<Download> findAll(
		int start, int end, OrderByComparator<Download> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of downloads
	 */
	public static List<Download> findAll(
		int start, int end, OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the downloads from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of downloads.
	 *
	 * @return the number of downloads
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DownloadPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DownloadPersistence, DownloadPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DownloadPersistence.class);

		ServiceTracker<DownloadPersistence, DownloadPersistence>
			serviceTracker =
				new ServiceTracker<DownloadPersistence, DownloadPersistence>(
					bundle.getBundleContext(), DownloadPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}