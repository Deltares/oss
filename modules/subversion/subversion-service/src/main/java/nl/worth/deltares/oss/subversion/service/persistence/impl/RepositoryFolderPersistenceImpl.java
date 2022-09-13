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

import nl.worth.deltares.oss.subversion.exception.NoSuchRepositoryFolderException;
import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderTable;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderImpl;
import nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderModelImpl;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryFolderPersistence;
import nl.worth.deltares.oss.subversion.service.persistence.RepositoryFolderUtil;
import nl.worth.deltares.oss.subversion.service.persistence.impl.constants.SubversionPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the repository folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {RepositoryFolderPersistence.class, BasePersistence.class})
public class RepositoryFolderPersistenceImpl
	extends BasePersistenceImpl<RepositoryFolder>
	implements RepositoryFolderPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RepositoryFolderUtil</code> to access the repository folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RepositoryFolderImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRepositoryId;
	private FinderPath _finderPathWithoutPaginationFindByRepositoryId;
	private FinderPath _finderPathCountByRepositoryId;

	/**
	 * Returns all the repository folders where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(
			repositoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @return the range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end) {

		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {

		return findByRepositoryId(
			repositoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository folders where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching repository folders
	 */
	@Override
	public List<RepositoryFolder> findByRepositoryId(
		long repositoryId, int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRepositoryId;
				finderArgs = new Object[] {repositoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRepositoryId;
			finderArgs = new Object[] {
				repositoryId, start, end, orderByComparator
			};
		}

		List<RepositoryFolder> list = null;

		if (useFinderCache) {
			list = (List<RepositoryFolder>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RepositoryFolder repositoryFolder : list) {
					if (repositoryId != repositoryFolder.getRepositoryId()) {
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

			sb.append(_SQL_SELECT_REPOSITORYFOLDER_WHERE);

			sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RepositoryFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

				list = (List<RepositoryFolder>)QueryUtil.list(
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
	 * Returns the first repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder
	 * @throws NoSuchRepositoryFolderException if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder findByRepositoryId_First(
			long repositoryId,
			OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {

		RepositoryFolder repositoryFolder = fetchByRepositoryId_First(
			repositoryId, orderByComparator);

		if (repositoryFolder != null) {
			return repositoryFolder;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchRepositoryFolderException(sb.toString());
	}

	/**
	 * Returns the first repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching repository folder, or <code>null</code> if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder fetchByRepositoryId_First(
		long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator) {

		List<RepositoryFolder> list = findByRepositoryId(
			repositoryId, 0, 1, orderByComparator);

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
	public RepositoryFolder findByRepositoryId_Last(
			long repositoryId,
			OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {

		RepositoryFolder repositoryFolder = fetchByRepositoryId_Last(
			repositoryId, orderByComparator);

		if (repositoryFolder != null) {
			return repositoryFolder;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("repositoryId=");
		sb.append(repositoryId);

		sb.append("}");

		throw new NoSuchRepositoryFolderException(sb.toString());
	}

	/**
	 * Returns the last repository folder in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching repository folder, or <code>null</code> if a matching repository folder could not be found
	 */
	@Override
	public RepositoryFolder fetchByRepositoryId_Last(
		long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator) {

		int count = countByRepositoryId(repositoryId);

		if (count == 0) {
			return null;
		}

		List<RepositoryFolder> list = findByRepositoryId(
			repositoryId, count - 1, count, orderByComparator);

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
	public RepositoryFolder[] findByRepositoryId_PrevAndNext(
			long folderId, long repositoryId,
			OrderByComparator<RepositoryFolder> orderByComparator)
		throws NoSuchRepositoryFolderException {

		RepositoryFolder repositoryFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			RepositoryFolder[] array = new RepositoryFolderImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(
				session, repositoryFolder, repositoryId, orderByComparator,
				true);

			array[1] = repositoryFolder;

			array[2] = getByRepositoryId_PrevAndNext(
				session, repositoryFolder, repositoryId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RepositoryFolder getByRepositoryId_PrevAndNext(
		Session session, RepositoryFolder repositoryFolder, long repositoryId,
		OrderByComparator<RepositoryFolder> orderByComparator,
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

		sb.append(_SQL_SELECT_REPOSITORYFOLDER_WHERE);

		sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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
			sb.append(RepositoryFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(repositoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						repositoryFolder)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RepositoryFolder> list = query.list();

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
		for (RepositoryFolder repositoryFolder :
				findByRepositoryId(
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
		FinderPath finderPath = _finderPathCountByRepositoryId;

		Object[] finderArgs = new Object[] {repositoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REPOSITORYFOLDER_WHERE);

			sb.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(repositoryId);

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

	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 =
		"repositoryFolder.repositoryId = ?";

	public RepositoryFolderPersistenceImpl() {
		setModelClass(RepositoryFolder.class);

		setModelImplClass(RepositoryFolderImpl.class);
		setModelPKClass(long.class);

		setTable(RepositoryFolderTable.INSTANCE);
	}

	/**
	 * Caches the repository folder in the entity cache if it is enabled.
	 *
	 * @param repositoryFolder the repository folder
	 */
	@Override
	public void cacheResult(RepositoryFolder repositoryFolder) {
		entityCache.putResult(
			RepositoryFolderImpl.class, repositoryFolder.getPrimaryKey(),
			repositoryFolder);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the repository folders in the entity cache if it is enabled.
	 *
	 * @param repositoryFolders the repository folders
	 */
	@Override
	public void cacheResult(List<RepositoryFolder> repositoryFolders) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (repositoryFolders.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RepositoryFolder repositoryFolder : repositoryFolders) {
			if (entityCache.getResult(
					RepositoryFolderImpl.class,
					repositoryFolder.getPrimaryKey()) == null) {

				cacheResult(repositoryFolder);
			}
		}
	}

	/**
	 * Clears the cache for all repository folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RepositoryFolderImpl.class);

		finderCache.clearCache(RepositoryFolderImpl.class);
	}

	/**
	 * Clears the cache for the repository folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RepositoryFolder repositoryFolder) {
		entityCache.removeResult(RepositoryFolderImpl.class, repositoryFolder);
	}

	@Override
	public void clearCache(List<RepositoryFolder> repositoryFolders) {
		for (RepositoryFolder repositoryFolder : repositoryFolders) {
			entityCache.removeResult(
				RepositoryFolderImpl.class, repositoryFolder);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RepositoryFolderImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RepositoryFolderImpl.class, primaryKey);
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

			RepositoryFolder repositoryFolder = (RepositoryFolder)session.get(
				RepositoryFolderImpl.class, primaryKey);

			if (repositoryFolder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRepositoryFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(repositoryFolder);
		}
		catch (NoSuchRepositoryFolderException noSuchEntityException) {
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
	protected RepositoryFolder removeImpl(RepositoryFolder repositoryFolder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(repositoryFolder)) {
				repositoryFolder = (RepositoryFolder)session.get(
					RepositoryFolderImpl.class,
					repositoryFolder.getPrimaryKeyObj());
			}

			if (repositoryFolder != null) {
				session.delete(repositoryFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = repositoryFolder.isNew();

		if (!(repositoryFolder instanceof RepositoryFolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(repositoryFolder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					repositoryFolder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in repositoryFolder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RepositoryFolder implementation " +
					repositoryFolder.getClass());
		}

		RepositoryFolderModelImpl repositoryFolderModelImpl =
			(RepositoryFolderModelImpl)repositoryFolder;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (repositoryFolder.getCreateDate() == null)) {
			if (serviceContext == null) {
				repositoryFolder.setCreateDate(date);
			}
			else {
				repositoryFolder.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!repositoryFolderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				repositoryFolder.setModifiedDate(date);
			}
			else {
				repositoryFolder.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(repositoryFolder);
			}
			else {
				repositoryFolder = (RepositoryFolder)session.merge(
					repositoryFolder);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RepositoryFolderImpl.class, repositoryFolderModelImpl, false, true);

		if (isNew) {
			repositoryFolder.setNew(false);
		}

		repositoryFolder.resetOriginalValues();

		return repositoryFolder;
	}

	/**
	 * Returns the repository folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchRepositoryFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return repositoryFolder;
	}

	/**
	 * Returns the repository folder with the primary key or throws a <code>NoSuchRepositoryFolderException</code> if it could not be found.
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
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder, or <code>null</code> if a repository folder with the primary key could not be found
	 */
	@Override
	public RepositoryFolder fetchByPrimaryKey(long folderId) {
		return fetchByPrimaryKey((Serializable)folderId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the repository folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of repository folders
	 */
	@Override
	public List<RepositoryFolder> findAll(
		int start, int end,
		OrderByComparator<RepositoryFolder> orderByComparator,
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

		List<RepositoryFolder> list = null;

		if (useFinderCache) {
			list = (List<RepositoryFolder>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REPOSITORYFOLDER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REPOSITORYFOLDER;

				sql = sql.concat(RepositoryFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RepositoryFolder>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REPOSITORYFOLDER);

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
		return "folderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REPOSITORYFOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RepositoryFolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the repository folder persistence.
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

		_finderPathWithPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRepositoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"repositoryId"}, true);

		_finderPathWithoutPaginationFindByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			true);

		_finderPathCountByRepositoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] {Long.class.getName()}, new String[] {"repositoryId"},
			false);

		_setRepositoryFolderUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setRepositoryFolderUtilPersistence(null);

		entityCache.removeCache(RepositoryFolderImpl.class.getName());
	}

	private void _setRepositoryFolderUtilPersistence(
		RepositoryFolderPersistence repositoryFolderPersistence) {

		try {
			Field field = RepositoryFolderUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, repositoryFolderPersistence);
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

	private static final String _SQL_SELECT_REPOSITORYFOLDER =
		"SELECT repositoryFolder FROM RepositoryFolder repositoryFolder";

	private static final String _SQL_SELECT_REPOSITORYFOLDER_WHERE =
		"SELECT repositoryFolder FROM RepositoryFolder repositoryFolder WHERE ";

	private static final String _SQL_COUNT_REPOSITORYFOLDER =
		"SELECT COUNT(repositoryFolder) FROM RepositoryFolder repositoryFolder";

	private static final String _SQL_COUNT_REPOSITORYFOLDER_WHERE =
		"SELECT COUNT(repositoryFolder) FROM RepositoryFolder repositoryFolder WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "repositoryFolder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RepositoryFolder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RepositoryFolder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryFolderPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RepositoryFolderModelArgumentsResolver
		_repositoryFolderModelArgumentsResolver;

}