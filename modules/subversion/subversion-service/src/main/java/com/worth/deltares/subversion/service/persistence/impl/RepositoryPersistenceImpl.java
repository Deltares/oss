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

package com.worth.deltares.subversion.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.worth.deltares.subversion.exception.NoSuchRepositoryException;
import com.worth.deltares.subversion.model.Repository;
import com.worth.deltares.subversion.model.impl.RepositoryImpl;
import com.worth.deltares.subversion.model.impl.RepositoryModelImpl;
import com.worth.deltares.subversion.service.persistence.RepositoryPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the repository service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryPersistence
 * @see com.worth.deltares.subversion.service.persistence.RepositoryUtil
 * @generated
 */
@ProviderType
public class RepositoryPersistenceImpl extends BasePersistenceImpl<Repository>
	implements RepositoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RepositoryUtil} to access the repository persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RepositoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, RepositoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, RepositoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, RepositoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, RepositoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			RepositoryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the repositories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching repositories
	 */
	@Override
	public List<Repository> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repositories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @return the range of matching repositories
	 */
	@Override
	public List<Repository> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repositories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repositories
	 */
	@Override
	public List<Repository> findByGroupId(long groupId, int start, int end,
		OrderByComparator<Repository> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repositories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching repositories
	 */
	@Override
	public List<Repository> findByGroupId(long groupId, int start, int end,
		OrderByComparator<Repository> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<Repository> list = null;

		if (retrieveFromCache) {
			list = (List<Repository>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Repository repository : list) {
					if ((groupId != repository.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_REPOSITORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RepositoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<Repository>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Repository>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first repository in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository
	 * @throws NoSuchRepositoryException if a matching repository could not be found
	 */
	@Override
	public Repository findByGroupId_First(long groupId,
		OrderByComparator<Repository> orderByComparator)
		throws NoSuchRepositoryException {
		Repository repository = fetchByGroupId_First(groupId, orderByComparator);

		if (repository != null) {
			return repository;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchRepositoryException(msg.toString());
	}

	/**
	 * Returns the first repository in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository, or <code>null</code> if a matching repository could not be found
	 */
	@Override
	public Repository fetchByGroupId_First(long groupId,
		OrderByComparator<Repository> orderByComparator) {
		List<Repository> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository
	 * @throws NoSuchRepositoryException if a matching repository could not be found
	 */
	@Override
	public Repository findByGroupId_Last(long groupId,
		OrderByComparator<Repository> orderByComparator)
		throws NoSuchRepositoryException {
		Repository repository = fetchByGroupId_Last(groupId, orderByComparator);

		if (repository != null) {
			return repository;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchRepositoryException(msg.toString());
	}

	/**
	 * Returns the last repository in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository, or <code>null</code> if a matching repository could not be found
	 */
	@Override
	public Repository fetchByGroupId_Last(long groupId,
		OrderByComparator<Repository> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<Repository> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repositories before and after the current repository in the ordered set where groupId = &#63;.
	 *
	 * @param repositoryId the primary key of the current repository
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository
	 * @throws NoSuchRepositoryException if a repository with the primary key could not be found
	 */
	@Override
	public Repository[] findByGroupId_PrevAndNext(long repositoryId,
		long groupId, OrderByComparator<Repository> orderByComparator)
		throws NoSuchRepositoryException {
		Repository repository = findByPrimaryKey(repositoryId);

		Session session = null;

		try {
			session = openSession();

			Repository[] array = new RepositoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, repository, groupId,
					orderByComparator, true);

			array[1] = repository;

			array[2] = getByGroupId_PrevAndNext(session, repository, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Repository getByGroupId_PrevAndNext(Session session,
		Repository repository, long groupId,
		OrderByComparator<Repository> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(RepositoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(repository);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Repository> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repositories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (Repository repository : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(repository);
		}
	}

	/**
	 * Returns the number of repositories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching repositories
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "repository.groupId = ?";

	public RepositoryPersistenceImpl() {
		setModelClass(Repository.class);
	}

	/**
	 * Caches the repository in the entity cache if it is enabled.
	 *
	 * @param repository the repository
	 */
	@Override
	public void cacheResult(Repository repository) {
		entityCache.putResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryImpl.class, repository.getPrimaryKey(), repository);

		repository.resetOriginalValues();
	}

	/**
	 * Caches the repositories in the entity cache if it is enabled.
	 *
	 * @param repositories the repositories
	 */
	@Override
	public void cacheResult(List<Repository> repositories) {
		for (Repository repository : repositories) {
			if (entityCache.getResult(
						RepositoryModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryImpl.class, repository.getPrimaryKey()) == null) {
				cacheResult(repository);
			}
			else {
				repository.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all repositories.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepositoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the repository.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Repository repository) {
		entityCache.removeResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryImpl.class, repository.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Repository> repositories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Repository repository : repositories) {
			entityCache.removeResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryImpl.class, repository.getPrimaryKey());
		}
	}

	/**
	 * Creates a new repository with the primary key. Does not add the repository to the database.
	 *
	 * @param repositoryId the primary key for the new repository
	 * @return the new repository
	 */
	@Override
	public Repository create(long repositoryId) {
		Repository repository = new RepositoryImpl();

		repository.setNew(true);
		repository.setPrimaryKey(repositoryId);

		repository.setCompanyId(companyProvider.getCompanyId());

		return repository;
	}

	/**
	 * Removes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository that was removed
	 * @throws NoSuchRepositoryException if a repository with the primary key could not be found
	 */
	@Override
	public Repository remove(long repositoryId)
		throws NoSuchRepositoryException {
		return remove((Serializable)repositoryId);
	}

	/**
	 * Removes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository
	 * @return the repository that was removed
	 * @throws NoSuchRepositoryException if a repository with the primary key could not be found
	 */
	@Override
	public Repository remove(Serializable primaryKey)
		throws NoSuchRepositoryException {
		Session session = null;

		try {
			session = openSession();

			Repository repository = (Repository)session.get(RepositoryImpl.class,
					primaryKey);

			if (repository == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(repository);
		}
		catch (NoSuchRepositoryException nsee) {
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
	protected Repository removeImpl(Repository repository) {
		repository = toUnwrappedModel(repository);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repository)) {
				repository = (Repository)session.get(RepositoryImpl.class,
						repository.getPrimaryKeyObj());
			}

			if (repository != null) {
				session.delete(repository);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (repository != null) {
			clearCache(repository);
		}

		return repository;
	}

	@Override
	public Repository updateImpl(Repository repository) {
		repository = toUnwrappedModel(repository);

		boolean isNew = repository.isNew();

		RepositoryModelImpl repositoryModelImpl = (RepositoryModelImpl)repository;

		Session session = null;

		try {
			session = openSession();

			if (repository.isNew()) {
				session.save(repository);

				repository.setNew(false);
			}
			else {
				repository = (Repository)session.merge(repository);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RepositoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { repositoryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((repositoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repositoryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { repositoryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryImpl.class, repository.getPrimaryKey(), repository, false);

		repository.resetOriginalValues();

		return repository;
	}

	protected Repository toUnwrappedModel(Repository repository) {
		if (repository instanceof RepositoryImpl) {
			return repository;
		}

		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setNew(repository.isNew());
		repositoryImpl.setPrimaryKey(repository.getPrimaryKey());

		repositoryImpl.setRepositoryId(repository.getRepositoryId());
		repositoryImpl.setCompanyId(repository.getCompanyId());
		repositoryImpl.setGroupId(repository.getGroupId());
		repositoryImpl.setClassNameId(repository.getClassNameId());
		repositoryImpl.setClassPK(repository.getClassPK());
		repositoryImpl.setRepositoryName(repository.getRepositoryName());
		repositoryImpl.setCreatedDate(repository.getCreatedDate());
		repositoryImpl.setModifiedDate(repository.getModifiedDate());

		return repositoryImpl;
	}

	/**
	 * Returns the repository with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository
	 * @return the repository
	 * @throws NoSuchRepositoryException if a repository with the primary key could not be found
	 */
	@Override
	public Repository findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepositoryException {
		Repository repository = fetchByPrimaryKey(primaryKey);

		if (repository == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepositoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return repository;
	}

	/**
	 * Returns the repository with the primary key or throws a {@link NoSuchRepositoryException} if it could not be found.
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository
	 * @throws NoSuchRepositoryException if a repository with the primary key could not be found
	 */
	@Override
	public Repository findByPrimaryKey(long repositoryId)
		throws NoSuchRepositoryException {
		return findByPrimaryKey((Serializable)repositoryId);
	}

	/**
	 * Returns the repository with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository
	 * @return the repository, or <code>null</code> if a repository with the primary key could not be found
	 */
	@Override
	public Repository fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Repository repository = (Repository)serializable;

		if (repository == null) {
			Session session = null;

			try {
				session = openSession();

				repository = (Repository)session.get(RepositoryImpl.class,
						primaryKey);

				if (repository != null) {
					cacheResult(repository);
				}
				else {
					entityCache.putResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return repository;
	}

	/**
	 * Returns the repository with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository, or <code>null</code> if a repository with the primary key could not be found
	 */
	@Override
	public Repository fetchByPrimaryKey(long repositoryId) {
		return fetchByPrimaryKey((Serializable)repositoryId);
	}

	@Override
	public Map<Serializable, Repository> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Repository> map = new HashMap<Serializable, Repository>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Repository repository = fetchByPrimaryKey(primaryKey);

			if (repository != null) {
				map.put(primaryKey, repository);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Repository)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_REPOSITORY_WHERE_PKS_IN);

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

			for (Repository repository : (List<Repository>)q.list()) {
				map.put(repository.getPrimaryKeyObj(), repository);

				cacheResult(repository);

				uncachedPrimaryKeys.remove(repository.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RepositoryModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the repositories.
	 *
	 * @return the repositories
	 */
	@Override
	public List<Repository> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @return the range of repositories
	 */
	@Override
	public List<Repository> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repositories
	 */
	@Override
	public List<Repository> findAll(int start, int end,
		OrderByComparator<Repository> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of repositories
	 */
	@Override
	public List<Repository> findAll(int start, int end,
		OrderByComparator<Repository> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Repository> list = null;

		if (retrieveFromCache) {
			list = (List<Repository>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REPOSITORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORY;

				if (pagination) {
					sql = sql.concat(RepositoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Repository>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Repository>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the repositories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Repository repository : findAll()) {
			remove(repository);
		}
	}

	/**
	 * Returns the number of repositories.
	 *
	 * @return the number of repositories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPOSITORY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

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
		return RepositoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RepositoryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_REPOSITORY = "SELECT repository FROM Repository repository";
	private static final String _SQL_SELECT_REPOSITORY_WHERE_PKS_IN = "SELECT repository FROM Repository repository WHERE repositoryId IN (";
	private static final String _SQL_SELECT_REPOSITORY_WHERE = "SELECT repository FROM Repository repository WHERE ";
	private static final String _SQL_COUNT_REPOSITORY = "SELECT COUNT(repository) FROM Repository repository";
	private static final String _SQL_COUNT_REPOSITORY_WHERE = "SELECT COUNT(repository) FROM Repository repository WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "repository.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Repository exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Repository exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RepositoryPersistenceImpl.class);
}