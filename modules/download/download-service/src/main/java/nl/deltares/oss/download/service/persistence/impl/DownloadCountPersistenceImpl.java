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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.oss.download.exception.NoSuchDownloadCountException;
import nl.deltares.oss.download.model.DownloadCount;
import nl.deltares.oss.download.model.impl.DownloadCountImpl;
import nl.deltares.oss.download.model.impl.DownloadCountModelImpl;
import nl.deltares.oss.download.service.persistence.DownloadCountPersistence;

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
@ProviderType
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

	public DownloadCountPersistenceImpl() {
		setModelClass(DownloadCount.class);
	}

	/**
	 * Caches the download count in the entity cache if it is enabled.
	 *
	 * @param downloadCount the download count
	 */
	@Override
	public void cacheResult(DownloadCount downloadCount) {
		entityCache.putResult(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountImpl.class, downloadCount.getPrimaryKey(),
			downloadCount);

		downloadCount.resetOriginalValues();
	}

	/**
	 * Caches the download counts in the entity cache if it is enabled.
	 *
	 * @param downloadCounts the download counts
	 */
	@Override
	public void cacheResult(List<DownloadCount> downloadCounts) {
		for (DownloadCount downloadCount : downloadCounts) {
			if (entityCache.getResult(
					DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
					DownloadCountImpl.class, downloadCount.getPrimaryKey()) ==
						null) {

				cacheResult(downloadCount);
			}
			else {
				downloadCount.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		entityCache.removeResult(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountImpl.class, downloadCount.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<DownloadCount> downloadCounts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DownloadCount downloadCount : downloadCounts) {
			entityCache.removeResult(
				DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
				DownloadCountImpl.class, downloadCount.getPrimaryKey());
		}
	}

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param downloadId the primary key for the new download count
	 * @return the new download count
	 */
	@Override
	public DownloadCount create(long downloadId) {
		DownloadCount downloadCount = new DownloadCountImpl();

		downloadCount.setNew(true);
		downloadCount.setPrimaryKey(downloadId);

		return downloadCount;
	}

	/**
	 * Removes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadId the primary key of the download count
	 * @return the download count that was removed
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount remove(long downloadId)
		throws NoSuchDownloadCountException {

		return remove((Serializable)downloadId);
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
		catch (NoSuchDownloadCountException nsee) {
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
		catch (Exception e) {
			throw processException(e);
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

		Session session = null;

		try {
			session = openSession();

			if (downloadCount.isNew()) {
				session.save(downloadCount);

				downloadCount.setNew(false);
			}
			else {
				downloadCount = (DownloadCount)session.merge(downloadCount);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountImpl.class, downloadCount.getPrimaryKey(),
			downloadCount, false);

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
	 * @param downloadId the primary key of the download count
	 * @return the download count
	 * @throws NoSuchDownloadCountException if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount findByPrimaryKey(long downloadId)
		throws NoSuchDownloadCountException {

		return findByPrimaryKey((Serializable)downloadId);
	}

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DownloadCount downloadCount = (DownloadCount)serializable;

		if (downloadCount == null) {
			Session session = null;

			try {
				session = openSession();

				downloadCount = (DownloadCount)session.get(
					DownloadCountImpl.class, primaryKey);

				if (downloadCount != null) {
					cacheResult(downloadCount);
				}
				else {
					entityCache.putResult(
						DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
						DownloadCountImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
					DownloadCountImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return downloadCount;
	}

	/**
	 * Returns the download count with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param downloadId the primary key of the download count
	 * @return the download count, or <code>null</code> if a download count with the primary key could not be found
	 */
	@Override
	public DownloadCount fetchByPrimaryKey(long downloadId) {
		return fetchByPrimaryKey((Serializable)downloadId);
	}

	@Override
	public Map<Serializable, DownloadCount> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DownloadCount> map =
			new HashMap<Serializable, DownloadCount>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DownloadCount downloadCount = fetchByPrimaryKey(primaryKey);

			if (downloadCount != null) {
				map.put(primaryKey, downloadCount);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
				DownloadCountImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DownloadCount)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_DOWNLOADCOUNT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DownloadCount downloadCount : (List<DownloadCount>)q.list()) {
				map.put(downloadCount.getPrimaryKeyObj(), downloadCount);

				cacheResult(downloadCount);

				uncachedPrimaryKeys.remove(downloadCount.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
					DownloadCountImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of download counts
	 */
	@Override
	public List<DownloadCount> findAll(
		int start, int end, OrderByComparator<DownloadCount> orderByComparator,
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

		List<DownloadCount> list = null;

		if (retrieveFromCache) {
			list = (List<DownloadCount>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DOWNLOADCOUNT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DOWNLOADCOUNT;

				if (pagination) {
					sql = sql.concat(DownloadCountModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DownloadCount>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DownloadCount>)QueryUtil.list(
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

				Query q = session.createQuery(_SQL_COUNT_DOWNLOADCOUNT);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return DownloadCountModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the download count persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountModelImpl.FINDER_CACHE_ENABLED,
			DownloadCountImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountModelImpl.FINDER_CACHE_ENABLED,
			DownloadCountImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			DownloadCountModelImpl.ENTITY_CACHE_ENABLED,
			DownloadCountModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	public void destroy() {
		entityCache.removeCache(DownloadCountImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DOWNLOADCOUNT =
		"SELECT downloadCount FROM DownloadCount downloadCount";

	private static final String _SQL_SELECT_DOWNLOADCOUNT_WHERE_PKS_IN =
		"SELECT downloadCount FROM DownloadCount downloadCount WHERE downloadId IN (";

	private static final String _SQL_COUNT_DOWNLOADCOUNT =
		"SELECT COUNT(downloadCount) FROM DownloadCount downloadCount";

	private static final String _ORDER_BY_ENTITY_ALIAS = "downloadCount.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DownloadCount exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadCountPersistenceImpl.class);

}