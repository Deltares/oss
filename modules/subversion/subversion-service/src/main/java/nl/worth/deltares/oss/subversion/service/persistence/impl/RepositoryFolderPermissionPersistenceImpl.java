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
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryFolderPermissionException;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermissionTable;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionImpl;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderPermissionModelImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryFolderPermissionPersistence;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryFolderPermissionUtil;
import nl.worth.deltares.oss.subversion.service.persistence.impl.constants.SubversionPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the repository folder permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		RepositoryFolderPermissionPersistence.class, BasePersistence.class
	}
)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findByFolderId(
		long folderId, int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFolderId;
				finderArgs = new Object[] {folderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFolderId;
			finderArgs = new Object[] {folderId, start, end, orderByComparator};
		}

		List<RepositoryFolderPermission> list = null;

		if (useFinderCache) {
			list = (List<RepositoryFolderPermission>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryFolderPermission repositoryFolderPermission :
						list) {

					if (folderId != repositoryFolderPermission.getFolderId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE);

			sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

				list = (List<RepositoryFolderPermission>)QueryUtil.list(
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchRepositoryFolderPermissionException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("folderId=");
		sb.append(folderId);

		sb.append("}");

		throw new NoSuchRepositoryFolderPermissionException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION_WHERE);

		sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(folderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						repositoryFolderPermission)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RepositoryFolderPermission> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REPOSITORYFOLDERPERMISSION_WHERE);

			sb.append(_FINDER_COLUMN_FOLDERID_FOLDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(folderId);

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

	private static final String _FINDER_COLUMN_FOLDERID_FOLDERID_2 =
		"repositoryFolderPermission.folderId = ?";

	public RepositoryFolderPermissionPersistenceImpl() {
		setModelClass(RepositoryFolderPermission.class);

		setModelImplClass(RepositoryFolderPermissionImpl.class);
		setModelPKClass(long.class);

		setTable(RepositoryFolderPermissionTable.INSTANCE);
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
			RepositoryFolderPermissionImpl.class,
			repositoryFolderPermission.getPrimaryKey(),
			repositoryFolderPermission);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the repository folder permissions in the entity cache if it is enabled.
	 *
	 * @param repositoryFolderPermissions the repository folder permissions
	 */
	@Override
	public void cacheResult(
		List<RepositoryFolderPermission> repositoryFolderPermissions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (repositoryFolderPermissions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RepositoryFolderPermission repositoryFolderPermission :
				repositoryFolderPermissions) {

			if (entityCache.getResult(
					RepositoryFolderPermissionImpl.class,
					repositoryFolderPermission.getPrimaryKey()) == null) {

				cacheResult(repositoryFolderPermission);
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

		finderCache.clearCache(RepositoryFolderPermissionImpl.class);
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
			RepositoryFolderPermissionImpl.class, repositoryFolderPermission);
	}

	@Override
	public void clearCache(
		List<RepositoryFolderPermission> repositoryFolderPermissions) {

		for (RepositoryFolderPermission repositoryFolderPermission :
				repositoryFolderPermissions) {

			entityCache.removeResult(
				RepositoryFolderPermissionImpl.class,
				repositoryFolderPermission);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RepositoryFolderPermissionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RepositoryFolderPermissionImpl.class, primaryKey);
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
		catch (NoSuchRepositoryFolderPermissionException
					noSuchEntityException) {

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
		catch (Exception exception) {
			throw processException(exception);
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

		Date date = new Date();

		if (isNew && (repositoryFolderPermission.getCreateDate() == null)) {
			if (serviceContext == null) {
				repositoryFolderPermission.setCreateDate(date);
			}
			else {
				repositoryFolderPermission.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!repositoryFolderPermissionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				repositoryFolderPermission.setModifiedDate(date);
			}
			else {
				repositoryFolderPermission.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(repositoryFolderPermission);
			}
			else {
				repositoryFolderPermission =
					(RepositoryFolderPermission)session.merge(
						repositoryFolderPermission);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RepositoryFolderPermissionImpl.class,
			repositoryFolderPermissionModelImpl, false, true);

		if (isNew) {
			repositoryFolderPermission.setNew(false);
		}

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
	 * @param permissionId the primary key of the repository folder permission
	 * @return the repository folder permission, or <code>null</code> if a repository folder permission with the primary key could not be found
	 */
	@Override
	public RepositoryFolderPermission fetchByPrimaryKey(long permissionId) {
		return fetchByPrimaryKey((Serializable)permissionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderPermissionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folder permissions
	 * @param end the upper bound of the range of repository folder permissions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of repository folder permissions
	 */
	@Override
	public List<RepositoryFolderPermission> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolderPermission> orderByComparator,
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

		List<RepositoryFolderPermission> list = null;

		if (useFinderCache) {
			list = (List<RepositoryFolderPermission>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REPOSITORYFOLDERPERMISSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYFOLDERPERMISSION;

				sql = sql.concat(
					RepositoryFolderPermissionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RepositoryFolderPermission>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_REPOSITORYFOLDERPERMISSION);

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
		return "permissionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REPOSITORYFOLDERPERMISSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RepositoryFolderPermissionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository folder permission persistence.
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

		_finderPathWithPaginationFindByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFolderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"folderId"}, true);

		_finderPathWithoutPaginationFindByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFolderId",
			new String[] {Long.class.getName()}, new String[] {"folderId"},
			true);

		_finderPathCountByFolderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFolderId",
			new String[] {Long.class.getName()}, new String[] {"folderId"},
			false);

		_setRepositoryFolderPermissionUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setRepositoryFolderPermissionUtilPersistence(null);

		entityCache.removeCache(RepositoryFolderPermissionImpl.class.getName());
	}

	private void _setRepositoryFolderPermissionUtilPersistence(
		RepositoryFolderPermissionPersistence
			repositoryFolderPermissionPersistence) {

		try {
			Field field = RepositoryFolderPermissionUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, repositoryFolderPermissionPersistence);
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

	private static final String _SQL_SELECT_REPOSITORYFOLDERPERMISSION =
		"SELECT repositoryFolderPermission FROM RepositoryFolderPermission repositoryFolderPermission";

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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RepositoryFolderPermissionModelArgumentsResolver
		_repositoryFolderPermissionModelArgumentsResolver;

}