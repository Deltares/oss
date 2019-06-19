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

package nl.worth.deltares.oss.subversion.service.persistence.impl;

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

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryLogException;
import nl.worth.deltares.oss.subversion.model.RepositoryLog;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryLogImpl;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryLogModelImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryLogPersistence;

/**
 * The persistence implementation for the repository log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @generated
 */
@ProviderType
public class RepositoryLogPersistenceImpl
	extends BasePersistenceImpl<RepositoryLog>
	implements RepositoryLogPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RepositoryLogUtil</code> to access the repository log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RepositoryLogImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public RepositoryLogPersistenceImpl() {
		setModelClass(RepositoryLog.class);
	}

	/**
	 * Caches the repository log in the entity cache if it is enabled.
	 *
	 * @param repositoryLog the repository log
	 */
	@Override
	public void cacheResult(RepositoryLog repositoryLog) {
		entityCache.putResult(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogImpl.class, repositoryLog.getPrimaryKey(),
			repositoryLog);

		repositoryLog.resetOriginalValues();
	}

	/**
	 * Caches the repository logs in the entity cache if it is enabled.
	 *
	 * @param repositoryLogs the repository logs
	 */
	@Override
	public void cacheResult(List<RepositoryLog> repositoryLogs) {
		for (RepositoryLog repositoryLog : repositoryLogs) {
			if (entityCache.getResult(
					RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryLogImpl.class, repositoryLog.getPrimaryKey()) ==
						null) {

				cacheResult(repositoryLog);
			}
			else {
				repositoryLog.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all repository logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepositoryLogImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the repository log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RepositoryLog repositoryLog) {
		entityCache.removeResult(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogImpl.class, repositoryLog.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<RepositoryLog> repositoryLogs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RepositoryLog repositoryLog : repositoryLogs) {
			entityCache.removeResult(
				RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryLogImpl.class, repositoryLog.getPrimaryKey());
		}
	}

	/**
	 * Creates a new repository log with the primary key. Does not add the repository log to the database.
	 *
	 * @param logId the primary key for the new repository log
	 * @return the new repository log
	 */
	@Override
	public RepositoryLog create(long logId) {
		RepositoryLog repositoryLog = new RepositoryLogImpl();

		repositoryLog.setNew(true);
		repositoryLog.setPrimaryKey(logId);

		return repositoryLog;
	}

	/**
	 * Removes the repository log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param logId the primary key of the repository log
	 * @return the repository log that was removed
	 * @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog remove(long logId)
		throws NoSuchRepositoryLogException {

		return remove((Serializable)logId);
	}

	/**
	 * Removes the repository log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository log
	 * @return the repository log that was removed
	 * @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog remove(Serializable primaryKey)
		throws NoSuchRepositoryLogException {

		Session session = null;

		try {
			session = openSession();

			RepositoryLog repositoryLog = (RepositoryLog)session.get(
				RepositoryLogImpl.class, primaryKey);

			if (repositoryLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(repositoryLog);
		}
		catch (NoSuchRepositoryLogException nsee) {
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
	protected RepositoryLog removeImpl(RepositoryLog repositoryLog) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repositoryLog)) {
				repositoryLog = (RepositoryLog)session.get(
					RepositoryLogImpl.class, repositoryLog.getPrimaryKeyObj());
			}

			if (repositoryLog != null) {
				session.delete(repositoryLog);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (repositoryLog != null) {
			clearCache(repositoryLog);
		}

		return repositoryLog;
	}

	@Override
	public RepositoryLog updateImpl(RepositoryLog repositoryLog) {
		boolean isNew = repositoryLog.isNew();

		Session session = null;

		try {
			session = openSession();

			if (repositoryLog.isNew()) {
				session.save(repositoryLog);

				repositoryLog.setNew(false);
			}
			else {
				repositoryLog = (RepositoryLog)session.merge(repositoryLog);
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
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogImpl.class, repositoryLog.getPrimaryKey(),
			repositoryLog, false);

		repositoryLog.resetOriginalValues();

		return repositoryLog;
	}

	/**
	 * Returns the repository log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository log
	 * @return the repository log
	 * @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepositoryLogException {

		RepositoryLog repositoryLog = fetchByPrimaryKey(primaryKey);

		if (repositoryLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepositoryLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return repositoryLog;
	}

	/**
	 * Returns the repository log with the primary key or throws a <code>NoSuchRepositoryLogException</code> if it could not be found.
	 *
	 * @param logId the primary key of the repository log
	 * @return the repository log
	 * @throws NoSuchRepositoryLogException if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog findByPrimaryKey(long logId)
		throws NoSuchRepositoryLogException {

		return findByPrimaryKey((Serializable)logId);
	}

	/**
	 * Returns the repository log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository log
	 * @return the repository log, or <code>null</code> if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		RepositoryLog repositoryLog = (RepositoryLog)serializable;

		if (repositoryLog == null) {
			Session session = null;

			try {
				session = openSession();

				repositoryLog = (RepositoryLog)session.get(
					RepositoryLogImpl.class, primaryKey);

				if (repositoryLog != null) {
					cacheResult(repositoryLog);
				}
				else {
					entityCache.putResult(
						RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryLogImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryLogImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return repositoryLog;
	}

	/**
	 * Returns the repository log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param logId the primary key of the repository log
	 * @return the repository log, or <code>null</code> if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog fetchByPrimaryKey(long logId) {
		return fetchByPrimaryKey((Serializable)logId);
	}

	@Override
	public Map<Serializable, RepositoryLog> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RepositoryLog> map =
			new HashMap<Serializable, RepositoryLog>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RepositoryLog repositoryLog = fetchByPrimaryKey(primaryKey);

			if (repositoryLog != null) {
				map.put(primaryKey, repositoryLog);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryLogImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (RepositoryLog)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_REPOSITORYLOG_WHERE_PKS_IN);

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

			for (RepositoryLog repositoryLog : (List<RepositoryLog>)q.list()) {
				map.put(repositoryLog.getPrimaryKeyObj(), repositoryLog);

				cacheResult(repositoryLog);

				uncachedPrimaryKeys.remove(repositoryLog.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryLogImpl.class, primaryKey, nullModel);
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
	 * Returns all the repository logs.
	 *
	 * @return the repository logs
	 */
	@Override
	public List<RepositoryLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository logs
	 * @param end the upper bound of the range of repository logs (not inclusive)
	 * @return the range of repository logs
	 */
	@Override
	public List<RepositoryLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository logs
	 * @param end the upper bound of the range of repository logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository logs
	 */
	@Override
	public List<RepositoryLog> findAll(
		int start, int end,
		OrderByComparator<RepositoryLog> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository logs
	 * @param end the upper bound of the range of repository logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of repository logs
	 */
	@Override
	public List<RepositoryLog> findAll(
		int start, int end, OrderByComparator<RepositoryLog> orderByComparator,
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

		List<RepositoryLog> list = null;

		if (retrieveFromCache) {
			list = (List<RepositoryLog>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REPOSITORYLOG);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYLOG;

				if (pagination) {
					sql = sql.concat(RepositoryLogModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RepositoryLog>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RepositoryLog>)QueryUtil.list(
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
	 * Removes all the repository logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RepositoryLog repositoryLog : findAll()) {
			remove(repositoryLog);
		}
	}

	/**
	 * Returns the number of repository logs.
	 *
	 * @return the number of repository logs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPOSITORYLOG);

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
		return RepositoryLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository log persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogModelImpl.FINDER_CACHE_ENABLED,
			RepositoryLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogModelImpl.FINDER_CACHE_ENABLED,
			RepositoryLogImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			RepositoryLogModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	}

	public void destroy() {
		entityCache.removeCache(RepositoryLogImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REPOSITORYLOG =
		"SELECT repositoryLog FROM RepositoryLog repositoryLog";

	private static final String _SQL_SELECT_REPOSITORYLOG_WHERE_PKS_IN =
		"SELECT repositoryLog FROM RepositoryLog repositoryLog WHERE logId IN (";

	private static final String _SQL_COUNT_REPOSITORYLOG =
		"SELECT COUNT(repositoryLog) FROM RepositoryLog repositoryLog";

	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RepositoryLog exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryLogPersistenceImpl.class);

}