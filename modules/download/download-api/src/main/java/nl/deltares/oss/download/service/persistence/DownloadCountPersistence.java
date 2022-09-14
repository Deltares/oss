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

import nl.deltares.oss.download.exception.NoSuchDownloadCountException;
import nl.deltares.oss.download.model.DownloadCount;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the download count service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCountUtil
 * @generated
 */
@ProviderType
public interface DownloadCountPersistence
	extends BasePersistence<DownloadCount> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DownloadCountUtil} to access the download count persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	public DownloadCount findByDownloadCountByGroup(
			long groupId, long downloadId)
		throws NoSuchDownloadCountException;

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId);

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId, boolean useFinderCache);

	/**
	 * Removes the download count where groupId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	public DownloadCount removeByDownloadCountByGroup(
			long groupId, long downloadId)
		throws NoSuchDownloadCountException;

	/**
	 * Returns the number of download counts where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	public int countByDownloadCountByGroup(long groupId, long downloadId);

	/**
	 * Returns the download count where downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	public DownloadCount findByDownloadCount(long downloadId)
		throws NoSuchDownloadCountException;

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public DownloadCount fetchByDownloadCount(long downloadId);

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	public DownloadCount fetchByDownloadCount(
		long downloadId, boolean useFinderCache);

	/**
	 * Removes the download count where downloadId = &#63; from the database.
	 *
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	public DownloadCount removeByDownloadCount(long downloadId)
		throws NoSuchDownloadCountException;

	/**
	 * Returns the number of download counts where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	public int countByDownloadCount(long downloadId);

	/**
	 * Caches the download count in the entity cache if it is enabled.
	 *
	 * @param downloadCount the download count
	 */
	public void cacheResult(DownloadCount downloadCount);

	/**
	 * Caches the download counts in the entity cache if it is enabled.
	 *
	 * @param downloadCounts the download counts
	 */
	public void cacheResult(java.util.List<DownloadCount> downloadCounts);

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param id the primary key for the new download count
	 * @return the new download count
	 */
	public DownloadCount create(long id);

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public DownloadCount remove(long id) throws NoSuchDownloadCountException;

	public DownloadCount updateImpl(DownloadCount downloadCount);

	/**
	 * Returns the download count with the primary key or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	public DownloadCount findByPrimaryKey(long id)
		throws NoSuchDownloadCountException;

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	public DownloadCount fetchByPrimaryKey(long id);

	/**
	 * Returns all the download counts.
	 *
	 * @return the download counts
	 */
	public java.util.List<DownloadCount> findAll();

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
	public java.util.List<DownloadCount> findAll(int start, int end);

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
	public java.util.List<DownloadCount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DownloadCount>
			orderByComparator);

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
	public java.util.List<DownloadCount> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DownloadCount>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the download counts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of download counts.
	 *
	 * @return the number of download counts
	 */
	public int countAll();

}