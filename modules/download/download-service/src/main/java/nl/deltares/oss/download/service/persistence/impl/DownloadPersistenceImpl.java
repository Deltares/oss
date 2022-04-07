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

package nl.deltares.oss.download.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.oss.download.exception.NoSuchDownloadException;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.model.impl.DownloadImpl;
import nl.deltares.oss.download.model.impl.DownloadModelImpl;
import nl.deltares.oss.download.service.persistence.DownloadPK;
import nl.deltares.oss.download.service.persistence.DownloadPersistence;

/**
 * The persistence implementation for the download service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class DownloadPersistenceImpl
	extends BasePersistenceImpl<Download> implements DownloadPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DownloadUtil</code> to access the download persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DownloadImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDownloads;
	private FinderPath _finderPathWithoutPaginationFindByDownloads;
	private FinderPath _finderPathCountByDownloads;

	/**
	 * Returns all the downloads where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @return the matching downloads
	 */
	@Override
	public List<Download> findByDownloads(long downloadId) {
		return findByDownloads(
			downloadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the downloads where downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	@Override
	public List<Download> findByDownloads(long downloadId, int start, int end) {
		return findByDownloads(downloadId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the downloads where downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByDownloads(
		long downloadId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return findByDownloads(downloadId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the downloads where downloadId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param downloadId the download ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByDownloads(
		long downloadId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByDownloads;
			finderArgs = new Object[] {downloadId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByDownloads;
			finderArgs = new Object[] {
				downloadId, start, end, orderByComparator
			};
		}

		List<Download> list = null;

		if (retrieveFromCache) {
			list = (List<Download>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Download download : list) {
					if ((downloadId != download.getDownloadId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_DOWNLOADS_DOWNLOADID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(DownloadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(downloadId);

				if (!pagination) {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first download in the ordered set where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByDownloads_First(
			long downloadId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByDownloads_First(
			downloadId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("downloadId=");
		msg.append(downloadId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the first download in the ordered set where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByDownloads_First(
		long downloadId, OrderByComparator<Download> orderByComparator) {

		List<Download> list = findByDownloads(
			downloadId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last download in the ordered set where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByDownloads_Last(
			long downloadId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByDownloads_Last(
			downloadId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("downloadId=");
		msg.append(downloadId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the last download in the ordered set where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByDownloads_Last(
		long downloadId, OrderByComparator<Download> orderByComparator) {

		int count = countByDownloads(downloadId);

		if (count == 0) {
			return null;
		}

		List<Download> list = findByDownloads(
			downloadId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where downloadId = &#63;.
	 *
	 * @param downloadPK the primary key of the current download
	 * @param downloadId the download ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download[] findByDownloads_PrevAndNext(
			DownloadPK downloadPK, long downloadId,
			OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = findByPrimaryKey(downloadPK);

		Session session = null;

		try {
			session = openSession();

			Download[] array = new DownloadImpl[3];

			array[0] = getByDownloads_PrevAndNext(
				session, download, downloadId, orderByComparator, true);

			array[1] = download;

			array[2] = getByDownloads_PrevAndNext(
				session, download, downloadId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Download getByDownloads_PrevAndNext(
		Session session, Download download, long downloadId,
		OrderByComparator<Download> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOWNLOAD_WHERE);

		query.append(_FINDER_COLUMN_DOWNLOADS_DOWNLOADID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DownloadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(downloadId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(download)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Download> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the downloads where downloadId = &#63; from the database.
	 *
	 * @param downloadId the download ID
	 */
	@Override
	public void removeByDownloads(long downloadId) {
		for (Download download :
				findByDownloads(
					downloadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(download);
		}
	}

	/**
	 * Returns the number of downloads where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @return the number of matching downloads
	 */
	@Override
	public int countByDownloads(long downloadId) {
		FinderPath finderPath = _finderPathCountByDownloads;

		Object[] finderArgs = new Object[] {downloadId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_DOWNLOADS_DOWNLOADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(downloadId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DOWNLOADS_DOWNLOADID_2 =
		"download.id.downloadId = ?";

	private FinderPath _finderPathWithPaginationFindByUserDownloads;
	private FinderPath _finderPathWithoutPaginationFindByUserDownloads;
	private FinderPath _finderPathCountByUserDownloads;

	/**
	 * Returns all the downloads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching downloads
	 */
	@Override
	public List<Download> findByUserDownloads(long userId) {
		return findByUserDownloads(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	@Override
	public List<Download> findByUserDownloads(long userId, int start, int end) {
		return findByUserDownloads(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByUserDownloads(
		long userId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return findByUserDownloads(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByUserDownloads(
		long userId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUserDownloads;
			finderArgs = new Object[] {userId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUserDownloads;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<Download> list = null;

		if (retrieveFromCache) {
			list = (List<Download>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Download download : list) {
					if ((userId != download.getUserId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_USERDOWNLOADS_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(DownloadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByUserDownloads_First(
			long userId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByUserDownloads_First(
			userId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the first download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByUserDownloads_First(
		long userId, OrderByComparator<Download> orderByComparator) {

		List<Download> list = findByUserDownloads(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByUserDownloads_Last(
			long userId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByUserDownloads_Last(
			userId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the last download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByUserDownloads_Last(
		long userId, OrderByComparator<Download> orderByComparator) {

		int count = countByUserDownloads(userId);

		if (count == 0) {
			return null;
		}

		List<Download> list = findByUserDownloads(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where userId = &#63;.
	 *
	 * @param downloadPK the primary key of the current download
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download[] findByUserDownloads_PrevAndNext(
			DownloadPK downloadPK, long userId,
			OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = findByPrimaryKey(downloadPK);

		Session session = null;

		try {
			session = openSession();

			Download[] array = new DownloadImpl[3];

			array[0] = getByUserDownloads_PrevAndNext(
				session, download, userId, orderByComparator, true);

			array[1] = download;

			array[2] = getByUserDownloads_PrevAndNext(
				session, download, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Download getByUserDownloads_PrevAndNext(
		Session session, Download download, long userId,
		OrderByComparator<Download> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOWNLOAD_WHERE);

		query.append(_FINDER_COLUMN_USERDOWNLOADS_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DownloadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(download)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Download> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the downloads where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserDownloads(long userId) {
		for (Download download :
				findByUserDownloads(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(download);
		}
	}

	/**
	 * Returns the number of downloads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching downloads
	 */
	@Override
	public int countByUserDownloads(long userId) {
		FinderPath finderPath = _finderPathCountByUserDownloads;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_USERDOWNLOADS_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERDOWNLOADS_USERID_2 =
		"download.id.userId = ?";

	private FinderPath _finderPathWithPaginationFindByPendingUserDownloads;
	private FinderPath _finderPathWithoutPaginationFindByPendingUserDownloads;
	private FinderPath _finderPathCountByPendingUserDownloads;

	/**
	 * Returns all the downloads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching downloads
	 */
	@Override
	public List<Download> findByPendingUserDownloads(long userId) {
		return findByPendingUserDownloads(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of matching downloads
	 */
	@Override
	public List<Download> findByPendingUserDownloads(
		long userId, int start, int end) {

		return findByPendingUserDownloads(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByPendingUserDownloads(
		long userId, int start, int end,
		OrderByComparator<Download> orderByComparator) {

		return findByPendingUserDownloads(
			userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the downloads where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching downloads
	 */
	@Override
	public List<Download> findByPendingUserDownloads(
		long userId, int start, int end,
		OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByPendingUserDownloads;
			finderArgs = new Object[] {userId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByPendingUserDownloads;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<Download> list = null;

		if (retrieveFromCache) {
			list = (List<Download>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Download download : list) {
					if ((userId != download.getUserId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_PENDINGUSERDOWNLOADS_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(DownloadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByPendingUserDownloads_First(
			long userId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByPendingUserDownloads_First(
			userId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the first download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByPendingUserDownloads_First(
		long userId, OrderByComparator<Download> orderByComparator) {

		List<Download> list = findByPendingUserDownloads(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download
	 * @throws NoSuchDownloadException if a matching download could not be found
	 */
	@Override
	public Download findByPendingUserDownloads_Last(
			long userId, OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = fetchByPendingUserDownloads_Last(
			userId, orderByComparator);

		if (download != null) {
			return download;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchDownloadException(msg.toString());
	}

	/**
	 * Returns the last download in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching download, or <code>null</code> if a matching download could not be found
	 */
	@Override
	public Download fetchByPendingUserDownloads_Last(
		long userId, OrderByComparator<Download> orderByComparator) {

		int count = countByPendingUserDownloads(userId);

		if (count == 0) {
			return null;
		}

		List<Download> list = findByPendingUserDownloads(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the downloads before and after the current download in the ordered set where userId = &#63;.
	 *
	 * @param downloadPK the primary key of the current download
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download[] findByPendingUserDownloads_PrevAndNext(
			DownloadPK downloadPK, long userId,
			OrderByComparator<Download> orderByComparator)
		throws NoSuchDownloadException {

		Download download = findByPrimaryKey(downloadPK);

		Session session = null;

		try {
			session = openSession();

			Download[] array = new DownloadImpl[3];

			array[0] = getByPendingUserDownloads_PrevAndNext(
				session, download, userId, orderByComparator, true);

			array[1] = download;

			array[2] = getByPendingUserDownloads_PrevAndNext(
				session, download, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Download getByPendingUserDownloads_PrevAndNext(
		Session session, Download download, long userId,
		OrderByComparator<Download> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOWNLOAD_WHERE);

		query.append(_FINDER_COLUMN_PENDINGUSERDOWNLOADS_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DownloadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(download)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Download> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the downloads where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByPendingUserDownloads(long userId) {
		for (Download download :
				findByPendingUserDownloads(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(download);
		}
	}

	/**
	 * Returns the number of downloads where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching downloads
	 */
	@Override
	public int countByPendingUserDownloads(long userId) {
		FinderPath finderPath = _finderPathCountByPendingUserDownloads;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DOWNLOAD_WHERE);

			query.append(_FINDER_COLUMN_PENDINGUSERDOWNLOADS_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PENDINGUSERDOWNLOADS_USERID_2 =
		"download.id.userId = ? AND download.shareId IS NULL";

	public DownloadPersistenceImpl() {
		setModelClass(Download.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("path", "path_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the download in the entity cache if it is enabled.
	 *
	 * @param download the download
	 */
	@Override
	public void cacheResult(Download download) {
		entityCache.putResult(
			DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
			download.getPrimaryKey(), download);

		download.resetOriginalValues();
	}

	/**
	 * Caches the downloads in the entity cache if it is enabled.
	 *
	 * @param downloads the downloads
	 */
	@Override
	public void cacheResult(List<Download> downloads) {
		for (Download download : downloads) {
			if (entityCache.getResult(
					DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
					download.getPrimaryKey()) == null) {

				cacheResult(download);
			}
			else {
				download.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all downloads.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DownloadImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the download.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Download download) {
		entityCache.removeResult(
			DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
			download.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Download> downloads) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Download download : downloads) {
			entityCache.removeResult(
				DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
				download.getPrimaryKey());
		}
	}

	/**
	 * Creates a new download with the primary key. Does not add the download to the database.
	 *
	 * @param downloadPK the primary key for the new download
	 * @return the new download
	 */
	@Override
	public Download create(DownloadPK downloadPK) {
		Download download = new DownloadImpl();

		download.setNew(true);
		download.setPrimaryKey(downloadPK);

		download.setCompanyId(CompanyThreadLocal.getCompanyId());

		return download;
	}

	/**
	 * Removes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadPK the primary key of the download
	 * @return the download that was removed
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download remove(DownloadPK downloadPK)
		throws NoSuchDownloadException {

		return remove((Serializable)downloadPK);
	}

	/**
	 * Removes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the download
	 * @return the download that was removed
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download remove(Serializable primaryKey)
		throws NoSuchDownloadException {

		Session session = null;

		try {
			session = openSession();

			Download download = (Download)session.get(
				DownloadImpl.class, primaryKey);

			if (download == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDownloadException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(download);
		}
		catch (NoSuchDownloadException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Download removeImpl(Download download) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(download)) {
				download = (Download)session.get(
					DownloadImpl.class, download.getPrimaryKeyObj());
			}

			if (download != null) {
				session.delete(download);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (download != null) {
			clearCache(download);
		}

		return download;
	}

	@Override
	public Download updateImpl(Download download) {
		boolean isNew = download.isNew();

		if (!(download instanceof DownloadModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(download.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(download);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in download proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Download implementation " +
					download.getClass());
		}

		DownloadModelImpl downloadModelImpl = (DownloadModelImpl)download;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (download.getCreateDate() == null)) {
			if (serviceContext == null) {
				download.setCreateDate(now);
			}
			else {
				download.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!downloadModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				download.setModifiedDate(now);
			}
			else {
				download.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (download.isNew()) {
				session.save(download);

				download.setNew(false);
			}
			else {
				download = (Download)session.merge(download);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DownloadModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {downloadModelImpl.getDownloadId()};

			finderCache.removeResult(_finderPathCountByDownloads, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDownloads, args);

			args = new Object[] {downloadModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserDownloads, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserDownloads, args);

			args = new Object[] {downloadModelImpl.getUserId()};

			finderCache.removeResult(
				_finderPathCountByPendingUserDownloads, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPendingUserDownloads, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((downloadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDownloads.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					downloadModelImpl.getOriginalDownloadId()
				};

				finderCache.removeResult(_finderPathCountByDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDownloads, args);

				args = new Object[] {downloadModelImpl.getDownloadId()};

				finderCache.removeResult(_finderPathCountByDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDownloads, args);
			}

			if ((downloadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserDownloads.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					downloadModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserDownloads, args);

				args = new Object[] {downloadModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserDownloads, args);
			}

			if ((downloadModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPendingUserDownloads.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					downloadModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(
					_finderPathCountByPendingUserDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPendingUserDownloads,
					args);

				args = new Object[] {downloadModelImpl.getUserId()};

				finderCache.removeResult(
					_finderPathCountByPendingUserDownloads, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPendingUserDownloads,
					args);
			}
		}

		entityCache.putResult(
			DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
			download.getPrimaryKey(), download, false);

		download.resetOriginalValues();

		return download;
	}

	/**
	 * Returns the download with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the download
	 * @return the download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDownloadException {

		Download download = fetchByPrimaryKey(primaryKey);

		if (download == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDownloadException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return download;
	}

	/**
	 * Returns the download with the primary key or throws a <code>NoSuchDownloadException</code> if it could not be found.
	 *
	 * @param downloadPK the primary key of the download
	 * @return the download
	 * @throws NoSuchDownloadException if a download with the primary key could not be found
	 */
	@Override
	public Download findByPrimaryKey(DownloadPK downloadPK)
		throws NoSuchDownloadException {

		return findByPrimaryKey((Serializable)downloadPK);
	}

	/**
	 * Returns the download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the download
	 * @return the download, or <code>null</code> if a download with the primary key could not be found
	 */
	@Override
	public Download fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Download download = (Download)serializable;

		if (download == null) {
			Session session = null;

			try {
				session = openSession();

				download = (Download)session.get(
					DownloadImpl.class, primaryKey);

				if (download != null) {
					cacheResult(download);
				}
				else {
					entityCache.putResult(
						DownloadModelImpl.ENTITY_CACHE_ENABLED,
						DownloadImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					DownloadModelImpl.ENTITY_CACHE_ENABLED, DownloadImpl.class,
					primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return download;
	}

	/**
	 * Returns the download with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param downloadPK the primary key of the download
	 * @return the download, or <code>null</code> if a download with the primary key could not be found
	 */
	@Override
	public Download fetchByPrimaryKey(DownloadPK downloadPK) {
		return fetchByPrimaryKey((Serializable)downloadPK);
	}

	@Override
	public Map<Serializable, Download> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Download> map = new HashMap<Serializable, Download>();

		for (Serializable primaryKey : primaryKeys) {
			Download download = fetchByPrimaryKey(primaryKey);

			if (download != null) {
				map.put(primaryKey, download);
			}
		}

		return map;
	}

	/**
	 * Returns all the downloads.
	 *
	 * @return the downloads
	 */
	@Override
	public List<Download> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<Download> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<Download> findAll(
		int start, int end, OrderByComparator<Download> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<Download> findAll(
		int start, int end, OrderByComparator<Download> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<Download> list = null;

		if (retrieveFromCache) {
			list = (List<Download>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DOWNLOAD);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DOWNLOAD;

				if (pagination) {
					sql = sql.concat(DownloadModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Download>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the downloads from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Download download : findAll()) {
			remove(download);
		}
	}

	/**
	 * Returns the number of downloads.
	 *
	 * @return the number of downloads
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DOWNLOAD);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DownloadModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the download persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDownloads",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDownloads",
			new String[] {Long.class.getName()},
			DownloadModelImpl.DOWNLOADID_COLUMN_BITMASK |
			DownloadModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDownloads",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserDownloads",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserDownloads",
			new String[] {Long.class.getName()},
			DownloadModelImpl.USERID_COLUMN_BITMASK |
			DownloadModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserDownloads",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByPendingUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPendingUserDownloads",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPendingUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, DownloadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPendingUserDownloads", new String[] {Long.class.getName()},
			DownloadModelImpl.USERID_COLUMN_BITMASK |
			DownloadModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByPendingUserDownloads = new FinderPath(
			DownloadModelImpl.ENTITY_CACHE_ENABLED,
			DownloadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPendingUserDownloads", new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(DownloadImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DOWNLOAD =
		"SELECT download FROM Download download";

	private static final String _SQL_SELECT_DOWNLOAD_WHERE =
		"SELECT download FROM Download download WHERE ";

	private static final String _SQL_COUNT_DOWNLOAD =
		"SELECT COUNT(download) FROM Download download";

	private static final String _SQL_COUNT_DOWNLOAD_WHERE =
		"SELECT COUNT(download) FROM Download download WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "download.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Download exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Download exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"path"});
	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"downloadId", "userId"});

}