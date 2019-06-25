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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryFolderPermissionException;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionImpl;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionModelImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryFolderPermissionPersistence;

/**
 * The persistence implementation for the repository folder permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionPersistenceImpl
	extends BasePersistenceImpl<RepositoryFolderPermission>
	implements RepositoryFolderPermissionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RepositoryFolderPermissionUtil</code> to access the repository folder permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RepositoryFolderPermissionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFolderId;
	private FinderPath _finderPathWithoutPaginationFindByFolderId;
	private FinderPath _finderPathCountByFolderId;

	/**
	 * Returns all the repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the matching repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findByFolderId(long folderId) {
		return findByFolderId(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @return the range of matching repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end) {

		return findByFolderId(folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return findByFolderId(folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions where folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByFolderId;
			finderArgs = new Object[] {folderId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByFolderId;
			finderArgs = new Object[] {folderId, start, end, orderByComparator};
		}

		List<RepositoryFolderPermission> list = null;

		if (retrieveFromCache) {
			list = (List<RepositoryFolderPermission>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryFolderPermission repositoryFolderPermission :
						list) {

					if ((folderId !=
							repositoryFolderPermission.getFolderId())) {

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

			query.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<RepositoryFolderPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RepositoryFolderPermission>)QueryUtil.list(
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
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	@Override
	public RepositoryFolderPermission findByFolderId_First(
			long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException {

		RepositoryFolderPermission repositoryFolderPermission =
			fetchByFolderId_First(folderId, orderByComparator);

		if (repositoryFolderPermission != null) {
			return repositoryFolderPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append("}");

		throw new NoSuchRepositoryFolderPermissionException(msg.toString());
	}

	/**
	 * Returns the first repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	@Override
	public RepositoryFolderPermission fetchByFolderId_First(
		long folderId,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		List<RepositoryFolderPermission> list = findByFolderId(
			folderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a matching repository folder permission could not be found
	 */
	@Override
	public RepositoryFolderPermission findByFolderId_Last(
			long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException {

		RepositoryFolderPermission repositoryFolderPermission =
			fetchByFolderId_Last(folderId, orderByComparator);

		if (repositoryFolderPermission != null) {
			return repositoryFolderPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append("}");

		throw new NoSuchRepositoryFolderPermissionException(msg.toString());
	}

	/**
	 * Returns the last repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder permission, or <code>null</code> if a matching repository folder permission could not be found
	 */
	@Override
	public RepositoryFolderPermission fetchByFolderId_Last(
		long folderId,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		int count = countByFolderId(folderId);

		if (count == 0) {
			return null;
		}

		List<RepositoryFolderPermission> list = findByFolderId(
			folderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the repository folder permissions before and after the current repository folder permission in the ordered set where folderId = &#63;.
	 *
	 * @param permissionId the primary key of the current repository folder permission
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission[] findByFolderId_PrevAndNext(
			long permissionId, long folderId,
			OrderByComparator<RepositoryFolderPermission> orderByComparator)
		throws NoSuchRepositoryFolderPermissionException {

		RepositoryFolderPermission repositoryFolderPermission =
			findByPrimaryKey(permissionId);

		Session session = null;

		try {
			session = openSession();

			RepositoryFolderPermission[] array =
				new RepositoryFolderPermissionImpl[3];

			array[0] = getByFolderId_PrevAndNext(
				session, repositoryFolderPermission, folderId,
				orderByComparator, true);

			array[1] = repositoryFolderPermission;

			array[2] = getByFolderId_PrevAndNext(
				session, repositoryFolderPermission, folderId,
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

	protected RepositoryFolderPermission getByFolderId_PrevAndNext(
		Session session, RepositoryFolderPermission repositoryFolderPermission,
		long folderId,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

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
			query.append(RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(folderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						repositoryFolderPermission)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RepositoryFolderPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the repository folder permissions where folderId = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByFolderId(long folderId) {
		for (RepositoryFolderPermission repositoryFolderPermission :
				findByFolderId(
					folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(repositoryFolderPermission);
		}
	}

	/**
	 * Returns the number of repository folder permissions where folderId = &#63;.
	 *
	 * @param folderId the folder ID
	 * @return the number of matching repository folder permissions
	 */
	@Override
	public int countByFolderId(long folderId) {
		FinderPath finderPath = _finderPathCountByFolderId;

		Object[] finderArgs = new Object[] {folderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REPOSITORYFOLDERPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

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

	private static final String _FINDER_COLUMN_FOLDERID_FOLDERID_2 =
		"repositoryFolderPermission.folderId = ?";

	public RepositoryFolderPermissionPersistenceImpl() {
		setModelClass(RepositoryFolderPermission.class);
	}

	/**
	 * Caches the repository folder permission in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermission the repository folder permission
	 */
	@Override
	public void cacheResult(
		RepositoryFolderPermission repositoryFolderPermission) {

		entityCache.putResult(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			repositoryFolderPermission.getPrimaryKey(),
			repositoryFolderPermission);

		repositoryFolderPermission.resetOriginalValues();
	}

	/**
	 * Caches the repository folder permissions in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermissions the repository folder permissions
	 */
	@Override
	public void cacheResult(
		List<RepositoryFolderPermission> repositoryFolderPermissions) {

		for (RepositoryFolderPermission repositoryFolderPermission :
				repositoryFolderPermissions) {

			if (entityCache.getResult(
					RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderPermissionImpl.class,
					repositoryFolderPermission.getPrimaryKey()) == null) {

				cacheResult(repositoryFolderPermission);
			}
			else {
				repositoryFolderPermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all repository folder permissions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepositoryFolderPermissionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the repository folder permission.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		RepositoryFolderPermission repositoryFolderPermission) {

		entityCache.removeResult(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			repositoryFolderPermission.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<RepositoryFolderPermission> repositoryFolderPermissions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RepositoryFolderPermission repositoryFolderPermission :
				repositoryFolderPermissions) {

			entityCache.removeResult(
				RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryFolderPermissionImpl.class,
				repositoryFolderPermission.getPrimaryKey());
		}
	}

	/**
	 * Creates a new repository folder permission with the primary key. Does not add the repository folder permission to the database.
	 *
	 * @param permissionId the primary key for the new repository folder permission
	 * @return the new repository folder permission
	 */
	@Override
	public RepositoryFolderPermission create(long permissionId) {
		RepositoryFolderPermission repositoryFolderPermission =
			new RepositoryFolderPermissionImpl();

		repositoryFolderPermission.setNew(true);
		repositoryFolderPermission.setPrimaryKey(permissionId);

		return repositoryFolderPermission;
	}

	/**
	 * Removes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission that was removed
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission remove(long permissionId)
		throws NoSuchRepositoryFolderPermissionException {

		return remove((Serializable)permissionId);
	}

	/**
	 * Removes the repository folder permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the repository folder permission
	 * @return the repository folder permission that was removed
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission remove(Serializable primaryKey)
		throws NoSuchRepositoryFolderPermissionException {

		Session session = null;

		try {
			session = openSession();

			RepositoryFolderPermission repositoryFolderPermission =
				(RepositoryFolderPermission)session.get(
					RepositoryFolderPermissionImpl.class, primaryKey);

			if (repositoryFolderPermission == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryFolderPermissionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(repositoryFolderPermission);
		}
		catch (NoSuchRepositoryFolderPermissionException nsee) {
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
	protected RepositoryFolderPermission removeImpl(
		RepositoryFolderPermission repositoryFolderPermission) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repositoryFolderPermission)) {
				repositoryFolderPermission =
					(RepositoryFolderPermission)session.get(
						RepositoryFolderPermissionImpl.class,
						repositoryFolderPermission.getPrimaryKeyObj());
			}

			if (repositoryFolderPermission != null) {
				session.delete(repositoryFolderPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (repositoryFolderPermission != null) {
			clearCache(repositoryFolderPermission);
		}

		return repositoryFolderPermission;
	}

	@Override
	public RepositoryFolderPermission updateImpl(
		RepositoryFolderPermission repositoryFolderPermission) {

		boolean isNew = repositoryFolderPermission.isNew();

		if (!(repositoryFolderPermission instanceof
				RepositoryFolderPermissionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(repositoryFolderPermission.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					repositoryFolderPermission);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in repositoryFolderPermission proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RepositoryFolderPermission implementation " +
					repositoryFolderPermission.getClass());
		}

		RepositoryFolderPermissionModelImpl
			repositoryFolderPermissionModelImpl =
				(RepositoryFolderPermissionModelImpl)repositoryFolderPermission;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (repositoryFolderPermission.getCreateDate() == null)) {
			if (serviceContext == null) {
				repositoryFolderPermission.setCreateDate(now);
			}
			else {
				repositoryFolderPermission.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!repositoryFolderPermissionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				repositoryFolderPermission.setModifiedDate(now);
			}
			else {
				repositoryFolderPermission.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (repositoryFolderPermission.isNew()) {
				session.save(repositoryFolderPermission);

				repositoryFolderPermission.setNew(false);
			}
			else {
				repositoryFolderPermission =
					(RepositoryFolderPermission)session.merge(
						repositoryFolderPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RepositoryFolderPermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				repositoryFolderPermissionModelImpl.getFolderId()
			};

			finderCache.removeResult(_finderPathCountByFolderId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFolderId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((repositoryFolderPermissionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFolderId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					repositoryFolderPermissionModelImpl.getOriginalFolderId()
				};

				finderCache.removeResult(_finderPathCountByFolderId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFolderId, args);

				args = new Object[] {
					repositoryFolderPermissionModelImpl.getFolderId()
				};

				finderCache.removeResult(_finderPathCountByFolderId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFolderId, args);
			}
		}

		entityCache.putResult(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			repositoryFolderPermission.getPrimaryKey(),
			repositoryFolderPermission, false);

		repositoryFolderPermission.resetOriginalValues();

		return repositoryFolderPermission;
	}

	/**
	 * Returns the repository folder permission with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository folder permission
	 * @return the repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRepositoryFolderPermissionException {

		RepositoryFolderPermission repositoryFolderPermission =
			fetchByPrimaryKey(primaryKey);

		if (repositoryFolderPermission == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRepositoryFolderPermissionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return repositoryFolderPermission;
	}

	/**
	 * Returns the repository folder permission with the primary key or throws a <code>NoSuchRepositoryFolderPermissionException</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission
	 * @throws NoSuchRepositoryFolderPermissionException if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission findByPrimaryKey(long permissionId)
		throws NoSuchRepositoryFolderPermissionException {

		return findByPrimaryKey((Serializable)permissionId);
	}

	/**
	 * Returns the repository folder permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the repository folder permission
	 * @return the repository folder permission, or <code>null</code> if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		RepositoryFolderPermission repositoryFolderPermission =
			(RepositoryFolderPermission)serializable;

		if (repositoryFolderPermission == null) {
			Session session = null;

			try {
				session = openSession();

				repositoryFolderPermission =
					(RepositoryFolderPermission)session.get(
						RepositoryFolderPermissionImpl.class, primaryKey);

				if (repositoryFolderPermission != null) {
					cacheResult(repositoryFolderPermission);
				}
				else {
					entityCache.putResult(
						RepositoryFolderPermissionModelImpl.
							ENTITY_CACHE_ENABLED,
						RepositoryFolderPermissionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderPermissionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return repositoryFolderPermission;
	}

	/**
	 * Returns the repository folder permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission, or <code>null</code> if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission fetchByPrimaryKey(long permissionId) {
		return fetchByPrimaryKey((Serializable)permissionId);
	}

	@Override
	public Map<Serializable, RepositoryFolderPermission> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RepositoryFolderPermission> map =
			new HashMap<Serializable, RepositoryFolderPermission>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RepositoryFolderPermission repositoryFolderPermission =
				fetchByPrimaryKey(primaryKey);

			if (repositoryFolderPermission != null) {
				map.put(primaryKey, repositoryFolderPermission);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
				RepositoryFolderPermissionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey, (RepositoryFolderPermission)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE_PKS_IN);

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

			for (RepositoryFolderPermission repositoryFolderPermission :
					(List<RepositoryFolderPermission>)q.list()) {

				map.put(
					repositoryFolderPermission.getPrimaryKeyObj(),
					repositoryFolderPermission);

				cacheResult(repositoryFolderPermission);

				uncachedPrimaryKeys.remove(
					repositoryFolderPermission.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
					RepositoryFolderPermissionImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the repository folder permissions.
	 *
	 * @return the repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @return the range of repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository folder permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
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

		List<RepositoryFolderPermission> list = null;

		if (retrieveFromCache) {
			list = (List<RepositoryFolderPermission>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYFOLDERPERMISSION;

				if (pagination) {
					sql = sql.concat(
						RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RepositoryFolderPermission>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RepositoryFolderPermission>)QueryUtil.list(
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
	 * Removes all the repository folder permissions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RepositoryFolderPermission repositoryFolderPermission :
				findAll()) {

			remove(repositoryFolderPermission);
		}
	}

	/**
	 * Returns the number of repository folder permissions.
	 *
	 * @return the number of repository folder permissions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_REPOSITORYFOLDERPERMISSION);

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
		return RepositoryFolderPermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository folder permission persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFolderId = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFolderId = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			RepositoryFolderPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFolderId",
			new String[] {Long.class.getName()},
			RepositoryFolderPermissionModelImpl.FOLDERID_COLUMN_BITMASK);

		_finderPathCountByFolderId = new FinderPath(
			RepositoryFolderPermissionModelImpl.ENTITY_CACHE_ENABLED,
			RepositoryFolderPermissionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFolderId", new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(RepositoryFolderPermissionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REPOSITORYFOLDERPERMISSION =
		"SELECT repositoryFolderPermission FROM RepositoryFolderPermission repositoryFolderPermission";

	private static final String
		_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE_PKS_IN =
			"SELECT repositoryFolderPermission FROM RepositoryFolderPermission repositoryFolderPermission WHERE permissionId IN (";

	private static final String _SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE =
		"SELECT repositoryFolderPermission FROM RepositoryFolderPermission repositoryFolderPermission WHERE ";

	private static final String _SQL_COUNT_REPOSITORYFOLDERPERMISSION =
		"SELECT COUNT(repositoryFolderPermission) FROM RepositoryFolderPermission repositoryFolderPermission";

	private static final String _SQL_COUNT_REPOSITORYFOLDERPERMISSION_WHERE =
		"SELECT COUNT(repositoryFolderPermission) FROM RepositoryFolderPermission repositoryFolderPermission WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"repositoryFolderPermission.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RepositoryFolderPermission exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RepositoryFolderPermission exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryFolderPermissionPersistenceImpl.class);

}