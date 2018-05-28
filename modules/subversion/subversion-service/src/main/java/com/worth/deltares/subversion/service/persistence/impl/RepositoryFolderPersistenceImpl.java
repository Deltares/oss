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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.worth.deltares.subversion.exception.NoSuchRepositoryFolderException;
import com.worth.deltares.subversion.model.RepositoryFolder;
import com.worth.deltares.subversion.model.impl.RepositoryFolderImpl;
import com.worth.deltares.subversion.model.impl.RepositoryFolderModelImpl;
import com.worth.deltares.subversion.service.persistence.RepositoryFolderPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the repository folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPersistence
 * @see com.worth.deltares.subversion.service.persistence.RepositoryFolderUtil
 * @generated
 */
@ProviderType
public class RepositoryFolderPersistenceImpl extends BasePersistenceImpl<RepositoryFolder>
	implements RepositoryFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RepositoryFolderUtil} to access the repository folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RepositoryFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRepositoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] { Long.class.getName() },
			RepositoryFolderModelImpl.REPOSITORYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_REPOSITORYID = new FinderPath(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the repository folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(repositoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @return the range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end) {
		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return findByRepositoryId(repositoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] { repositoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] {
					repositoryId,
					
					start, end, orderByComparator
				};
		}

		List<RepositoryFolder> list = null;

		if (retrieveFromCache) {
			list = (List<RepositoryFolder>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryFolder repositoryFolder : list) {
					if ((repositoryId != repositoryFolder.getRepositoryId())) {
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

			query.append(_SQL_SELECT_REPOSITORYFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RepositoryFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (!pagination) {
					list = (List<RepositoryFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RepositoryFolder>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder
	 * @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder findByRepositoryId_First(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {
		RepositoryFolder repositoryFolder = fetchByRepositoryId_First(repositoryId,
				orderByComparator);

		if (repositoryFolder != null) {
			return repositoryFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append("}");

		throw new NoSuchRepositoryFolderException(msg.toString());
	}

	/**
	 * Returns the first repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder, or <code>null</code> if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder fetchByRepositoryId_First(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		List<RepositoryFolder> list = findByRepositoryId(repositoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder
	 * @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder findByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {
		RepositoryFolder repositoryFolder = fetchByRepositoryId_Last(repositoryId,
				orderByComparator);

		if (repositoryFolder != null) {
			return repositoryFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append("}");

		throw new NoSuchRepositoryFolderException(msg.toString());
	}

	/**
	 * Returns the last repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder, or <code>null</code> if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder fetchByRepositoryId_Last(long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		int count = countByRepositoryId(repositoryId);

		if (count == 0) {
			return null;
		}

		List<RepositoryFolder> list = findByRepositoryId(repositoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository folders before and after the current repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param folderId the primary key of the current repository folder
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository folder
	 * @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder[] findByRepositoryId_PrevAndNext(long folderId,
		long repositoryId, OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {
		RepositoryFolder repositoryFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			RepositoryFolder[] array = new RepositoryFolderImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(session, repositoryFolder,
					repositoryId, orderByComparator, true);

			array[1] = repositoryFolder;

			array[2] = getByRepositoryId_PrevAndNext(session, repositoryFolder,
					repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RepositoryFolder getByRepositoryId_PrevAndNext(Session session,
		RepositoryFolder repositoryFolder, long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORYFOLDER_WHERE);

		query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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
			query.append(RepositoryFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(repositoryFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RepositoryFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repository folders where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByRepositoryId(long repositoryId) {
		for (RepositoryFolder repositoryFolder : findByRepositoryId(
				repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(repositoryFolder);
		}
	}

	/**
	 * Returns the number of repository folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching repository folders
	 */
	@Override
	public int countByRepositoryId(long repositoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_REPOSITORYID;

		Object[] finderArgs = new Object[] { repositoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORYFOLDER_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

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

	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 = "repositoryFolder.repositoryId = ?";

	public RepositoryFolderPersistenceImpl() {
		setModelClass(RepositoryFolder.class);
	}

	/**
	 * Caches the repository folder in the entity cache if it is enabled.
	 *
	 * @param repositoryFolder the repository folder
	 */
	@Override
	public void cacheResult(RepositoryFolder repositoryFolder) {
		entityCache.putResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderImpl.class, repositoryFolder.getPrimaryKey(),
			repositoryFolder);

		repositoryFolder.resetOriginalValues();
	}

	/**
	 * Caches the repository folders in the entity cache if it is enabled.
	 *
	 * @param repositoryFolders the repository folders
	 */
	@Override
	public void cacheResult(List<RepositoryFolder> repositoryFolders) {
		for (RepositoryFolder repositoryFolder : repositoryFolders) {
			if (entityCache.getResult(
						RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryFolderImpl.class,
						repositoryFolder.getPrimaryKey()) == null) {
				cacheResult(repositoryFolder);
			}
			else {
				repositoryFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all repository folders.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepositoryFolderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the repository folder.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RepositoryFolder repositoryFolder) {
		entityCache.removeResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderImpl.class, repositoryFolder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<RepositoryFolder> repositoryFolders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RepositoryFolder repositoryFolder : repositoryFolders) {
			entityCache.removeResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryFolderImpl.class, repositoryFolder.getPrimaryKey());
		}
	}

	/**
	 * Creates a new repository folder with the primary key. Does not add the repository folder to the database.
	 *
	 * @param folderId the primary key for the new repository folder
	 * @return the new repository folder
	 */
	@Override
	public RepositoryFolder create(long folderId) {
		RepositoryFolder repositoryFolder = new RepositoryFolderImpl();

		repositoryFolder.setNew(true);
		repositoryFolder.setPrimaryKey(folderId);

		return repositoryFolder;
	}

	/**
	 * Removes the repository folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder that was removed
	 * @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder remove(long folderId)
		throws NoSuchRepositoryFolderException {
		return remove((Serializable)folderId);
	}

	/**
	 * Removes the repository folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository folder
	 * @return the repository folder that was removed
	 * @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder remove(Serializable primaryKey)
		throws NoSuchRepositoryFolderException {
		Session session = null;

		try {
			session = openSession();

			RepositoryFolder repositoryFolder = (RepositoryFolder)session.get(RepositoryFolderImpl.class,
					primaryKey);

			if (repositoryFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(repositoryFolder);
		}
		catch (NoSuchRepositoryFolderException nsee) {
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
	protected RepositoryFolder removeImpl(RepositoryFolder repositoryFolder) {
		repositoryFolder = toUnwrappedModel(repositoryFolder);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repositoryFolder)) {
				repositoryFolder = (RepositoryFolder)session.get(RepositoryFolderImpl.class,
						repositoryFolder.getPrimaryKeyObj());
			}

			if (repositoryFolder != null) {
				session.delete(repositoryFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (repositoryFolder != null) {
			clearCache(repositoryFolder);
		}

		return repositoryFolder;
	}

	@Override
	public RepositoryFolder updateImpl(RepositoryFolder repositoryFolder) {
		repositoryFolder = toUnwrappedModel(repositoryFolder);

		boolean isNew = repositoryFolder.isNew();

		RepositoryFolderModelImpl repositoryFolderModelImpl = (RepositoryFolderModelImpl)repositoryFolder;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (repositoryFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				repositoryFolder.setCreateDate(now);
			}
			else {
				repositoryFolder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!repositoryFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				repositoryFolder.setModifiedDate(now);
			}
			else {
				repositoryFolder.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (repositoryFolder.isNew()) {
				session.save(repositoryFolder);

				repositoryFolder.setNew(false);
			}
			else {
				repositoryFolder = (RepositoryFolder)session.merge(repositoryFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RepositoryFolderModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					repositoryFolderModelImpl.getRepositoryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((repositoryFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						repositoryFolderModelImpl.getOriginalRepositoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);

				args = new Object[] { repositoryFolderModelImpl.getRepositoryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);
			}
		}

		entityCache.putResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderImpl.class, repositoryFolder.getPrimaryKey(),
			repositoryFolder, false);

		repositoryFolder.resetOriginalValues();

		return repositoryFolder;
	}

	protected RepositoryFolder toUnwrappedModel(
		RepositoryFolder repositoryFolder) {
		if (repositoryFolder instanceof RepositoryFolderImpl) {
			return repositoryFolder;
		}

		RepositoryFolderImpl repositoryFolderImpl = new RepositoryFolderImpl();

		repositoryFolderImpl.setNew(repositoryFolder.isNew());
		repositoryFolderImpl.setPrimaryKey(repositoryFolder.getPrimaryKey());

		repositoryFolderImpl.setFolderId(repositoryFolder.getFolderId());
		repositoryFolderImpl.setRepositoryId(repositoryFolder.getRepositoryId());
		repositoryFolderImpl.setName(repositoryFolder.getName());
		repositoryFolderImpl.setWorldRead(repositoryFolder.isWorldRead());
		repositoryFolderImpl.setWorldWrite(repositoryFolder.isWorldWrite());
		repositoryFolderImpl.setCreateDate(repositoryFolder.getCreateDate());
		repositoryFolderImpl.setModifiedDate(repositoryFolder.getModifiedDate());

		return repositoryFolderImpl;
	}

	/**
	 * Returns the repository folder with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository folder
	 * @return the repository folder
	 * @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepositoryFolderException {
		RepositoryFolder repositoryFolder = fetchByPrimaryKey(primaryKey);

		if (repositoryFolder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepositoryFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return repositoryFolder;
	}

	/**
	 * Returns the repository folder with the primary key or throws a {@link NoSuchRepositoryFolderException} if it could not be found.
	 *
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder
	 * @throws NoSuchRepositoryFolderException if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder findByPrimaryKey(long folderId)
		throws NoSuchRepositoryFolderException {
		return findByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns the repository folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository folder
	 * @return the repository folder, or <code>null</code> if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryFolderImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		RepositoryFolder repositoryFolder = (RepositoryFolder)serializable;

		if (repositoryFolder == null) {
			Session session = null;

			try {
				session = openSession();

				repositoryFolder = (RepositoryFolder)session.get(RepositoryFolderImpl.class,
						primaryKey);

				if (repositoryFolder != null) {
					cacheResult(repositoryFolder);
				}
				else {
					entityCache.putResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
						RepositoryFolderImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return repositoryFolder;
	}

	/**
	 * Returns the repository folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder, or <code>null</code> if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder fetchByPrimaryKey(long folderId) {
		return fetchByPrimaryKey((Serializable)folderId);
	}

	@Override
	public Map<Serializable, RepositoryFolder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RepositoryFolder> map = new HashMap<Serializable, RepositoryFolder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RepositoryFolder repositoryFolder = fetchByPrimaryKey(primaryKey);

			if (repositoryFolder != null) {
				map.put(primaryKey, repositoryFolder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (RepositoryFolder)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_REPOSITORYFOLDER_WHERE_PKS_IN);

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

			for (RepositoryFolder repositoryFolder : (List<RepositoryFolder>)q.list()) {
				map.put(repositoryFolder.getPrimaryKeyObj(), repositoryFolder);

				cacheResult(repositoryFolder);

				uncachedPrimaryKeys.remove(repositoryFolder.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RepositoryFolderModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderImpl.class, primaryKey, nullModel);
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
	 * Returns all the repository folders.
	 *
	 * @return the repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @return the range of repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll(int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RepositoryFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll(int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
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

		List<RepositoryFolder> list = null;

		if (retrieveFromCache) {
			list = (List<RepositoryFolder>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REPOSITORYFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYFOLDER;

				if (pagination) {
					sql = sql.concat(RepositoryFolderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RepositoryFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RepositoryFolder>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the repository folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RepositoryFolder repositoryFolder : findAll()) {
			remove(repositoryFolder);
		}
	}

	/**
	 * Returns the number of repository folders.
	 *
	 * @return the number of repository folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REPOSITORYFOLDER);

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
		return RepositoryFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository folder persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RepositoryFolderImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_REPOSITORYFOLDER = "SELECT repositoryFolder FROM RepositoryFolder repositoryFolder";
	private static final String _SQL_SELECT_REPOSITORYFOLDER_WHERE_PKS_IN = "SELECT repositoryFolder FROM RepositoryFolder repositoryFolder WHERE folderId IN (";
	private static final String _SQL_SELECT_REPOSITORYFOLDER_WHERE = "SELECT repositoryFolder FROM RepositoryFolder repositoryFolder WHERE ";
	private static final String _SQL_COUNT_REPOSITORYFOLDER = "SELECT COUNT(repositoryFolder) FROM RepositoryFolder repositoryFolder";
	private static final String _SQL_COUNT_REPOSITORYFOLDER_WHERE = "SELECT COUNT(repositoryFolder) FROM RepositoryFolder repositoryFolder WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RepositoryFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RepositoryFolder exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RepositoryFolderPersistenceImpl.class);
}