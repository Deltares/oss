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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryLogException;
import nl.worth.deltares.oss.subversion.model.RepositoryLog;
import nl.worth.deltares.oss.subversion.model.RepositoryLogTable;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryLogImpl;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryLogModelImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryLogPersistence;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryLogUtil;
import nl.worth.deltares.oss.subversion.service.persistence.impl.constants.SubversionPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the repository log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {RepositoryLogPersistence.class, BasePersistence.class})
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

		setModelImplClass(RepositoryLogImpl.class);
		setModelPKClass(long.class);

		setTable(RepositoryLogTable.INSTANCE);
	}

	/**
	 * Caches the repository log in the entity cache if it is enabled.
	 *
	 * @param repositoryLog the repository log
	 */
	@Override
	public void cacheResult(RepositoryLog repositoryLog) {
		entityCache.putResult(
			RepositoryLogImpl.class, repositoryLog.getPrimaryKey(),
			repositoryLog);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the repository logs in the entity cache if it is enabled.
	 *
	 * @param repositoryLogs the repository logs
	 */
	@Override
	public void cacheResult(List<RepositoryLog> repositoryLogs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (repositoryLogs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RepositoryLog repositoryLog : repositoryLogs) {
			if (entityCache.getResult(
					RepositoryLogImpl.class, repositoryLog.getPrimaryKey()) ==
						null) {

				cacheResult(repositoryLog);
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

		finderCache.clearCache(RepositoryLogImpl.class);
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
		entityCache.removeResult(RepositoryLogImpl.class, repositoryLog);
	}

	@Override
	public void clearCache(List<RepositoryLog> repositoryLogs) {
		for (RepositoryLog repositoryLog : repositoryLogs) {
			entityCache.removeResult(RepositoryLogImpl.class, repositoryLog);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RepositoryLogImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RepositoryLogImpl.class, primaryKey);
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
		catch (NoSuchRepositoryLogException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

			if (isNew) {
				session.save(repositoryLog);
			}
			else {
				repositoryLog = (RepositoryLog)session.merge(repositoryLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RepositoryLogImpl.class, repositoryLog, false, true);

		if (isNew) {
			repositoryLog.setNew(false);
		}

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
	 * @param logId the primary key of the repository log
	 * @return the repository log, or <code>null</code> if a repository log with the primary key could not be found
	 */
	@Override
	public RepositoryLog fetchByPrimaryKey(long logId) {
		return fetchByPrimaryKey((Serializable)logId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository logs
	 * @param end the upper bound of the range of repository logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of repository logs
	 */
	@Override
	public List<RepositoryLog> findAll(
		int start, int end, OrderByComparator<RepositoryLog> orderByComparator,
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

		List<RepositoryLog> list = null;

		if (useFinderCache) {
			list = (List<RepositoryLog>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REPOSITORYLOG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYLOG;

				sql = sql.concat(RepositoryLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RepositoryLog>)QueryUtil.list(
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

				Query query = session.createQuery(_SQL_COUNT_REPOSITORYLOG);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "logId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REPOSITORYLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RepositoryLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository log persistence.
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

		_setRepositoryLogUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setRepositoryLogUtilPersistence(null);

		entityCache.removeCache(RepositoryLogImpl.class.getName());
	}

	private void _setRepositoryLogUtilPersistence(
		RepositoryLogPersistence repositoryLogPersistence) {

		try {
			Field field = RepositoryLogUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, repositoryLogPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = SubversionPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SubversionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SubversionPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REPOSITORYLOG =
		"SELECT repositoryLog FROM RepositoryLog repositoryLog";

	private static final String _SQL_COUNT_REPOSITORYLOG =
		"SELECT COUNT(repositoryLog) FROM RepositoryLog repositoryLog";

	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RepositoryLog exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryLogPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}