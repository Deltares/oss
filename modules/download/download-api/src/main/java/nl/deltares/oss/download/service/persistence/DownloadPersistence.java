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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.deltares.oss.download.exception.NoSuchDownloadException;
import nl.deltares.oss.download.model.Download;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the download service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadUtil
 * @generated
 */
@ProviderType
public interface DownloadPersistence extends BasePersistence<Download> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DownloadUtil} to access the download persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByDownloads(
		long groupId, long downloadId);

	/**
	 * Returns a range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDownloads(
		long groupId, long downloadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDownloads_First(
			long groupId, long downloadId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDownloads_First(
		long groupId, long downloadId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDownloads_Last(
			long groupId, long downloadId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDownloads_Last(
		long groupId, long downloadId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

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
	public Download[] findByDownloads_PrevAndNext(
			long id, long groupId, long downloadId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 */
	public void removeByDownloads(long groupId, long downloadId);

	/**
	 * Returns the number of downloads where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the number of matching downloads
	 */
	public int countByDownloads(long groupId, long downloadId);

	/**
	 * Returns all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByDownloadsByShareId(
		long groupId, int shareId);

	/**
	 * Returns a range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDownloadsByShareId(
		long groupId, int shareId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDownloadsByShareId_First(
			long groupId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDownloadsByShareId_First(
		long groupId, int shareId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDownloadsByShareId_Last(
			long groupId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDownloadsByShareId_Last(
		long groupId, int shareId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

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
	public Download[] findByDownloadsByShareId_PrevAndNext(
			long id, long groupId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; and shareId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 */
	public void removeByDownloadsByShareId(long groupId, int shareId);

	/**
	 * Returns the number of downloads where groupId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param shareId the share ID
	 * @return the number of matching downloads
	 */
	public int countByDownloadsByShareId(long groupId, int shareId);

	/**
	 * Returns all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId);

	/**
	 * Returns a range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
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
	public java.util.List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByUserDownloadsByShareId(
		long groupId, long userId, int shareId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

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
	public Download findByUserDownloadsByShareId_First(
			long groupId, long userId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownloadsByShareId_First(
		long groupId, long userId, int shareId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

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
	public Download findByUserDownloadsByShareId_Last(
			long groupId, long userId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownloadsByShareId_Last(
		long groupId, long userId, int shareId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

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
	public Download[] findByUserDownloadsByShareId_PrevAndNext(
			long id, long groupId, long userId, int shareId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; and userId = &#63; and shareId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 */
	public void removeByUserDownloadsByShareId(
		long groupId, long userId, int shareId);

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63; and shareId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param shareId the share ID
	 * @return the number of matching downloads
	 */
	public int countByUserDownloadsByShareId(
		long groupId, long userId, int shareId);

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or throws a <code>NoSuchDownloadException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByUserDownload(
			long groupId, long userId, long downloadId)
		throws NoSuchDownloadException;

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownload(
		long groupId, long userId, long downloadId);

	/**
	 * Returns the download where groupId = &#63; and userId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownload(
		long groupId, long userId, long downloadId, boolean useFinderCache);

	/**
	 * Removes the download where groupId = &#63; and userId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the download that was removed
	 */
	public Download removeByUserDownload(
			long groupId, long userId, long downloadId)
		throws NoSuchDownloadException;

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param downloadId the download ID
	 * @return the number of matching downloads
	 */
	public int countByUserDownload(long groupId, long userId, long downloadId);

	/**
	 * Returns all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByUserDownloads(
		long groupId, long userId);

	/**
	 * Returns a range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByUserDownloads(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByUserDownloads_First(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownloads_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByUserDownloads_Last(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByUserDownloads_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

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
	public Download[] findByUserDownloads_PrevAndNext(
			long id, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public void removeByUserDownloads(long groupId, long userId);

	/**
	 * Returns the number of downloads where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching downloads
	 */
	public int countByUserDownloads(long groupId, long userId);

	/**
	 * Returns all the downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByDirectDownloads(long groupId);

	/**
	 * Returns a range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByDirectDownloads(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDirectDownloads(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByDirectDownloads(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDirectDownloads_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDirectDownloads_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByDirectDownloads_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByDirectDownloads_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public Download[] findByDirectDownloads_PrevAndNext(
			long id, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByDirectDownloads(long groupId);

	/**
	 * Returns the number of downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching downloads
	 */
	public int countByDirectDownloads(long groupId);

	/**
	 * Returns all the downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching downloads
	 */
	public java.util.List<Download> findByGroupDownloads(long groupId);

	/**
	 * Returns a range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	public java.util.List<Download> findByGroupDownloads(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByGroupDownloads(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching downloads
	 */
	public java.util.List<Download> findByGroupDownloads(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByGroupDownloads_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the first download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByGroupDownloads_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	public Download findByGroupDownloads_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Returns the last download in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	public Download fetchByGroupDownloads_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns the downloads before and after the current download in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current download
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public Download[] findByGroupDownloads_PrevAndNext(
			long id, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<Download>
				orderByComparator)
		throws NoSuchDownloadException;

	/**
	 * Removes all the downloads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupDownloads(long groupId);

	/**
	 * Returns the number of downloads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching downloads
	 */
	public int countByGroupDownloads(long groupId);

	/**
	 * Caches the download in the entity cache if it is enabled.
	 *
	 * @param download the download
	 */
	public void cacheResult(Download download);

	/**
	 * Caches the downloads in the entity cache if it is enabled.
	 *
	 * @param downloads the downloads
	 */
	public void cacheResult(java.util.List<Download> downloads);

	/**
	 * Creates a new download with the primary key. Does not add the download to the database.
	 *
	 * @param id the primary key for the new download
	 * @return the new download
	 */
	public Download create(long id);

	/**
	 * Removes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download
	 * @return the download that was removed
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public Download remove(long id) throws NoSuchDownloadException;

	public Download updateImpl(Download download);

	/**
	 * Returns the download with the primary key or throws a <code>NoSuchDownloadException</code> if it could not be found.
	 *
	 * @param id the primary key of the download
	 * @return the download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	public Download findByPrimaryKey(long id) throws NoSuchDownloadException;

	/**
	 * Returns the download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the download
	 * @return the download, or <code>null</code> if a download with the primary key could not be found
	 */
	public Download fetchByPrimaryKey(long id);

	/**
	 * Returns all the downloads.
	 *
	 * @return the downloads
	 */
	public java.util.List<Download> findAll();

	/**
	 * Returns a range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of downloads
	 */
	public java.util.List<Download> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of downloads
	 */
	public java.util.List<Download> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator);

	/**
	 * Returns an ordered range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of downloads
	 */
	public java.util.List<Download> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Download>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the downloads from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of downloads.
	 *
	 * @return the number of downloads
	 */
	public int countAll();

}