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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.deltares.oss.download.exception.NoSuchDownloadCountException;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.model.DownloadCountTable;
import nl.deltares.oss.download.model.impl.DownloadCountImpl;
import nl.deltares.oss.download.model.impl.DownloadCountModelImpl;
import nl.deltares.oss.download.service.persistence.DownloadCountPersistence;
import nl.deltares.oss.download.service.persistence.DownloadCountUtil;
import nl.deltares.oss.download.service.persistence.impl.constants.DownloadsPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the download count service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@Component(service = {DownloadCountPersistence.class, BasePersistence.class})
public class DownloadCountPersistenceImpl
	extends BasePersistenceImpl<DownloadCount>
	implements DownloadCountPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DownloadCountUtil</code> to access the download count persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DownloadCountImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByDownloadCountByGroup;
	private FinderPath _finderPathCountByDownloadCountByGroup;

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	@Override
	public DownloadCount findByDownloadCountByGroup(
			long groupId, long downloadId)
		throws NoSuchDownloadCountException {

		DownloadCount downloadCount = fetchByDownloadCountByGroup(
			groupId, downloadId);

		if (downloadCount == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", downloadId=");
			sb.append(downloadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDownloadCountException(sb.toString());
		}

		return downloadCount;
	}

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	@Override
	public DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId) {

		return fetchByDownloadCountByGroup(groupId, downloadId, true);
	}

	/**
	 * Returns the download count where groupId = &#63; and downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	@Override
	public DownloadCount fetchByDownloadCountByGroup(
		long groupId, long downloadId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, downloadId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByDownloadCountByGroup, finderArgs, this);
		}

		if (result instanceof DownloadCount) {
			DownloadCount downloadCount = (DownloadCount)result;

			if ((groupId != downloadCount.getGroupId()) ||
				(downloadId != downloadCount.getDownloadId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DOWNLOADCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_GROUPID_2);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_DOWNLOADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(downloadId);

				List<DownloadCount> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByDownloadCountByGroup, finderArgs,
							list);
					}
				}
				else {
					DownloadCount downloadCount = list.get(0);

					result = downloadCount;

					cacheResult(downloadCount);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DownloadCount)result;
		}
	}

	/**
	 * Removes the download count where groupId = &#63; and downloadId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	@Override
	public DownloadCount removeByDownloadCountByGroup(
			long groupId, long downloadId)
		throws NoSuchDownloadCountException {

		DownloadCount downloadCount = findByDownloadCountByGroup(
			groupId, downloadId);

		return remove(downloadCount);
	}

	/**
	 * Returns the number of download counts where groupId = &#63; and downloadId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	@Override
	public int countByDownloadCountByGroup(long groupId, long downloadId) {
		FinderPath finderPath = _finderPathCountByDownloadCountByGroup;

		Object[] finderArgs = new Object[] {groupId, downloadId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DOWNLOADCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_GROUPID_2);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_DOWNLOADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(downloadId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_GROUPID_2 =
		"downloadCount.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_DOWNLOADCOUNTBYGROUP_DOWNLOADID_2 =
			"downloadCount.downloadId = ?";

	private FinderPath _finderPathFetchByDownloadCount;
	private FinderPath _finderPathCountByDownloadCount;

	/**
	 * Returns the download count where downloadId = &#63; or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count
	 * @throws NoSuchDownloadCountException if a matching download count could not be found
	 */
	@Override
	public DownloadCount findByDownloadCount(long downloadId)
		throws NoSuchDownloadCountException {

		DownloadCount downloadCount = fetchByDownloadCount(downloadId);

		if (downloadCount == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("downloadId=");
			sb.append(downloadId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDownloadCountException(sb.toString());
		}

		return downloadCount;
	}

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param downloadId the download ID
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	@Override
	public DownloadCount fetchByDownloadCount(long downloadId) {
		return fetchByDownloadCount(downloadId, true);
	}

	/**
	 * Returns the download count where downloadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param downloadId the download ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching download count, or <code>null</code> if a matching download count could not be found
	 */
	@Override
	public DownloadCount fetchByDownloadCount(
		long downloadId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {downloadId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByDownloadCount, finderArgs, this);
		}

		if (result instanceof DownloadCount) {
			DownloadCount downloadCount = (DownloadCount)result;

			if (downloadId != downloadCount.getDownloadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_DOWNLOADCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNT_DOWNLOADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(downloadId);

				List<DownloadCount> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByDownloadCount, finderArgs, list);
					}
				}
				else {
					DownloadCount downloadCount = list.get(0);

					result = downloadCount;

					cacheResult(downloadCount);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DownloadCount)result;
		}
	}

	/**
	 * Removes the download count where downloadId = &#63; from the database.
	 *
	 * @param downloadId the download ID
	 * @return the download count that was removed
	 */
	@Override
	public DownloadCount removeByDownloadCount(long downloadId)
		throws NoSuchDownloadCountException {

		DownloadCount downloadCount = findByDownloadCount(downloadId);

		return remove(downloadCount);
	}

	/**
	 * Returns the number of download counts where downloadId = &#63;.
	 *
	 * @param downloadId the download ID
	 * @return the number of matching download counts
	 */
	@Override
	public int countByDownloadCount(long downloadId) {
		FinderPath finderPath = _finderPathCountByDownloadCount;

		Object[] finderArgs = new Object[] {downloadId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DOWNLOADCOUNT_WHERE);

			sb.append(_FINDER_COLUMN_DOWNLOADCOUNT_DOWNLOADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(downloadId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DOWNLOADCOUNT_DOWNLOADID_2 =
		"downloadCount.downloadId = ?";

	public DownloadCountPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("id", "id_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DownloadCount.class);

		setModelImplClass(DownloadCountImpl.class);
		setModelPKClass(long.class);

		setTable(DownloadCountTable.INSTANCE);
	}

	/**
	 * Caches the download count in the entity cache if it is enabled.
	 *
	 * @param downloadCount the download count
	 */
	@Override
	public void cacheResult(DownloadCount downloadCount) {
		entityCache.putResult(
			DownloadCountImpl.class, downloadCount.getPrimaryKey(),
			downloadCount);

		finderCache.putResult(
			_finderPathFetchByDownloadCountByGroup,
			new Object[] {
				downloadCount.getGroupId(), downloadCount.getDownloadId()
			},
			downloadCount);

		finderCache.putResult(
			_finderPathFetchByDownloadCount,
			new Object[] {downloadCount.getDownloadId()}, downloadCount);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the download counts in the entity cache if it is enabled.
	 *
	 * @param downloadCounts the download counts
	 */
	@Override
	public void cacheResult(List<DownloadCount> downloadCounts) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (downloadCounts.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DownloadCount downloadCount : downloadCounts) {
			if (entityCache.getResult(
					DownloadCountImpl.class, downloadCount.getPrimaryKey()) ==
						null) {

				cacheResult(downloadCount);
			}
		}
	}

	/**
	 * Clears the cache for all download counts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DownloadCountImpl.class);

		finderCache.clearCache(DownloadCountImpl.class);
	}

	/**
	 * Clears the cache for the download count.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DownloadCount downloadCount) {
		entityCache.removeResult(DownloadCountImpl.class, downloadCount);
	}

	@Override
	public void clearCache(List<DownloadCount> downloadCounts) {
		for (DownloadCount downloadCount : downloadCounts) {
			entityCache.removeResult(DownloadCountImpl.class, downloadCount);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DownloadCountImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DownloadCountImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DownloadCountModelImpl downloadCountModelImpl) {

		Object[] args = new Object[] {
			downloadCountModelImpl.getGroupId(),
			downloadCountModelImpl.getDownloadId()
		};

		finderCache.putResult(
			_finderPathCountByDownloadCountByGroup, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByDownloadCountByGroup, args,
			downloadCountModelImpl);

		args = new Object[] {downloadCountModelImpl.getDownloadId()};

		finderCache.putResult(
			_finderPathCountByDownloadCount, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByDownloadCount, args, downloadCountModelImpl);
	}

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param id the primary key for the new download count
	 * @return the new download count
	 */
	@Override
	public DownloadCount create(long id) {
		DownloadCount downloadCount = new DownloadCountImpl();

		downloadCount.setNew(true);
		downloadCount.setPrimaryKey(id);

		downloadCount.setCompanyId(CompanyThreadLocal.getCompanyId());

		return downloadCount;
	}

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount remove(long id) throws NoSuchDownloadCountException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount remove(Serializable primaryKey)
		throws NoSuchDownloadCountException {

		Session session = null;

		try {
			session = openSession();

			DownloadCount downloadCount = (DownloadCount)session.get(
				DownloadCountImpl.class, primaryKey);

			if (downloadCount == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDownloadCountException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(downloadCount);
		}
		catch (NoSuchDownloadCountException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DownloadCount removeImpl(DownloadCount downloadCount) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(downloadCount)) {
				downloadCount = (DownloadCount)session.get(
					DownloadCountImpl.class, downloadCount.getPrimaryKeyObj());
			}

			if (downloadCount != null) {
				session.delete(downloadCount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (downloadCount != null) {
			clearCache(downloadCount);
		}

		return downloadCount;
	}

	@Override
	public DownloadCount updateImpl(DownloadCount downloadCount) {
		boolean isNew = downloadCount.isNew();

		if (!(downloadCount instanceof DownloadCountModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(downloadCount.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					downloadCount);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in downloadCount proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DownloadCount implementation " +
					downloadCount.getClass());
		}

		DownloadCountModelImpl downloadCountModelImpl =
			(DownloadCountModelImpl)downloadCount;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(downloadCount);
			}
			else {
				downloadCount = (DownloadCount)session.merge(downloadCount);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DownloadCountImpl.class, downloadCountModelImpl, false, true);

		cacheUniqueFindersCache(downloadCountModelImpl);

		if (isNew) {
			downloadCount.setNew(false);
		}

		downloadCount.resetOriginalValues();

		return downloadCount;
	}

	/**
	 * Returns the download count with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDownloadCountException {

		DownloadCount downloadCount = fetchByPrimaryKey(primaryKey);

		if (downloadCount == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDownloadCountException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return downloadCount;
	}

	/**
	 * Returns the download count with the primary key or throws a <code>NoSuchDownloadCountException</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount findByPrimaryKey(long id)
		throws NoSuchDownloadCountException {

		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns all the download counts.
	 *
	 * @return the download counts
	 */
	@Override
	public List<DownloadCount> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<DownloadCount> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<DownloadCount> findAll(
		int start, int end,
		OrderByComparator<DownloadCount> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<DownloadCount> findAll(
		int start, int end, OrderByComparator<DownloadCount> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DownloadCount> list = null;

		if (useFinderCache) {
			list = (List<DownloadCount>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DOWNLOADCOUNT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DOWNLOADCOUNT;

				sql = sql.concat(DownloadCountModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DownloadCount>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the download counts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DownloadCount downloadCount : findAll()) {
			remove(downloadCount);
		}
	}

	/**
	 * Returns the number of download counts.
	 *
	 * @return the number of download counts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DOWNLOADCOUNT);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "id_";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DOWNLOADCOUNT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DownloadCountModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the download count persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByDownloadCountByGroup = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByDownloadCountByGroup",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "downloadId"}, true);

		_finderPathCountByDownloadCountByGroup = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDownloadCountByGroup",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "downloadId"}, false);

		_finderPathFetchByDownloadCount = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByDownloadCount",
			new String[] {Long.class.getName()}, new String[] {"downloadId"},
			true);

		_finderPathCountByDownloadCount = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDownloadCount",
			new String[] {Long.class.getName()}, new String[] {"downloadId"},
			false);

		_setDownloadCountUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setDownloadCountUtilPersistence(null);

		entityCache.removeCache(DownloadCountImpl.class.getName());
	}

	private void _setDownloadCountUtilPersistence(
		DownloadCountPersistence downloadCountPersistence) {

		try {
			Field field = DownloadCountUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, downloadCountPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = DownloadsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DownloadsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DownloadsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DOWNLOADCOUNT =
		"SELECT downloadCount FROM DownloadCount downloadCount";

	private static final String _SQL_SELECT_DOWNLOADCOUNT_WHERE =
		"SELECT downloadCount FROM DownloadCount downloadCount WHERE ";

	private static final String _SQL_COUNT_DOWNLOADCOUNT =
		"SELECT COUNT(downloadCount) FROM DownloadCount downloadCount";

	private static final String _SQL_COUNT_DOWNLOADCOUNT_WHERE =
		"SELECT COUNT(downloadCount) FROM DownloadCount downloadCount WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "downloadCount.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DownloadCount exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DownloadCount exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadCountPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"id"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}