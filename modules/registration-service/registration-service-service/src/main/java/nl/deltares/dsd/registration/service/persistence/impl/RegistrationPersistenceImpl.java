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

package nl.deltares.dsd.registration.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.impl.RegistrationImpl;
import nl.deltares.dsd.registration.model.impl.RegistrationModelImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationPersistence;

/**
 * The persistence implementation for the registration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class RegistrationPersistenceImpl
	extends BasePersistenceImpl<Registration>
	implements RegistrationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegistrationUtil</code> to access the registration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegistrationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserRegistrations;
	private FinderPath _finderPathCountByUserRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId) {

		return findByUserRegistrations(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end) {

		return findByUserRegistrations(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserRegistrations(
			groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUserRegistrations;
			finderArgs = new Object[] {groupId, userId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUserRegistrations;
			finderArgs = new Object[] {
				groupId, userId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_First(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_First(
			groupId, userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_First(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserRegistrations(
			groupId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_Last(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_Last(
			groupId, userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_Last(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserRegistrations(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserRegistrations(
			groupId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserRegistrations_PrevAndNext(
				session, registration, groupId, userId, orderByComparator,
				true);

			array[1] = registration;

			array[2] = getByUserRegistrations_PrevAndNext(
				session, registration, groupId, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

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
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserRegistrations(long groupId, long userId) {
		for (Registration registration :
				findByUserRegistrations(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserRegistrations(long groupId, long userId) {
		FinderPath finderPath = _finderPathCountByUserRegistrations;

		Object[] finderArgs = new Object[] {groupId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_USERID_2 =
		"registration.userId = ?";

	private FinderPath _finderPathWithPaginationFindByArticleRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByArticleRegistrations;
	private FinderPath _finderPathCountByArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long articleId) {

		return findByArticleRegistrations(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end) {

		return findByArticleRegistrations(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByArticleRegistrations(
			groupId, articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByArticleRegistrations;
			finderArgs = new Object[] {groupId, articleId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByArticleRegistrations;
			finderArgs = new Object[] {
				groupId, articleId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(articleId != registration.getArticleId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_ARTICLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(articleId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_First(
			long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_First(
			groupId, articleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_First(
		long groupId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByArticleRegistrations(
			groupId, articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_Last(
			long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_Last(
			groupId, articleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_Last(
		long groupId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByArticleRegistrations(groupId, articleId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByArticleRegistrations(
			groupId, articleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, articleId, orderByComparator,
				true);

			array[1] = registration;

			array[2] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, articleId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long articleId, OrderByComparator<Registration> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_ARTICLEID_2);

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
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(articleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	@Override
	public void removeByArticleRegistrations(long groupId, long articleId) {
		for (Registration registration :
				findByArticleRegistrations(
					groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByArticleRegistrations(long groupId, long articleId) {
		FinderPath finderPath = _finderPathCountByArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, articleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_ARTICLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(articleId);

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

	private static final String _FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_ARTICLEREGISTRATIONS_ARTICLEID_2 =
			"registration.articleId = ?";

	private FinderPath _finderPathWithPaginationFindByUserArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserArticleRegistrations;
	private FinderPath _finderPathCountByUserArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long articleId) {

		return findByUserArticleRegistrations(
			groupId, userId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long articleId, int start, int end) {

		return findByUserArticleRegistrations(
			groupId, userId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserArticleRegistrations(
			groupId, userId, articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserArticleRegistrations;
			finderArgs = new Object[] {groupId, userId, articleId};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, articleId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(articleId != registration.getArticleId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_ARTICLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(articleId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_First(
			long groupId, long userId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_First(
			groupId, userId, articleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_First(
		long groupId, long userId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_Last(
			long groupId, long userId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_Last(
			groupId, userId, articleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_Last(
		long groupId, long userId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserArticleRegistrations(groupId, userId, articleId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, articleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, articleId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, articleId,
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

	protected Registration getByUserArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long articleId, OrderByComparator<Registration> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

		query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_ARTICLEID_2);

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
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(articleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 */
	@Override
	public void removeByUserArticleRegistrations(
		long groupId, long userId, long articleId) {

		for (Registration registration :
				findByUserArticleRegistrations(
					groupId, userId, articleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param articleId the article ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserArticleRegistrations(
		long groupId, long userId, long articleId) {

		FinderPath finderPath = _finderPathCountByUserArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, userId, articleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_ARTICLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(articleId);

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

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_ARTICLEID_2 =
			"registration.articleId = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserChildArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
	private FinderPath _finderPathCountByUserChildArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentArticleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId, int start, int end) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentArticleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentArticleId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
			finderArgs = new Object[] {groupId, userId, parentArticleId};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, parentArticleId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(parentArticleId !=
							registration.getParentArticleId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTARTICLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(parentArticleId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_First(
			long groupId, long userId, long parentArticleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_First(
			groupId, userId, parentArticleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", parentArticleId=");
		msg.append(parentArticleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_First(
		long groupId, long userId, long parentArticleId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentArticleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_Last(
			long groupId, long userId, long parentArticleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_Last(
			groupId, userId, parentArticleId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", parentArticleId=");
		msg.append(parentArticleId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_Last(
		long groupId, long userId, long parentArticleId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserChildArticleRegistrations(
			groupId, userId, parentArticleId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentArticleId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long parentArticleId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, parentArticleId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, parentArticleId,
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

	protected Registration getByUserChildArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long parentArticleId, OrderByComparator<Registration> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

		query.append(
			_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTARTICLEID_2);

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
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(parentArticleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 */
	@Override
	public void removeByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId) {

		for (Registration registration :
				findByUserChildArticleRegistrations(
					groupId, userId, parentArticleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and parentArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentArticleId the parent article ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserChildArticleRegistrations(
		long groupId, long userId, long parentArticleId) {

		FinderPath finderPath = _finderPathCountByUserChildArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, userId, parentArticleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTARTICLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(parentArticleId);

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

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTARTICLEID_2 =
			"registration.parentArticleId = ?";

	public RegistrationPersistenceImpl() {
		setModelClass(Registration.class);
	}

	/**
	 * Caches the registration in the entity cache if it is enabled.
	 *
	 * @param registration the registration
	 */
	@Override
	public void cacheResult(Registration registration) {
		entityCache.putResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey(), registration);

		registration.resetOriginalValues();
	}

	/**
	 * Caches the registrations in the entity cache if it is enabled.
	 *
	 * @param registrations the registrations
	 */
	@Override
	public void cacheResult(List<Registration> registrations) {
		for (Registration registration : registrations) {
			if (entityCache.getResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, registration.getPrimaryKey()) ==
						null) {

				cacheResult(registration);
			}
			else {
				registration.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all registrations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RegistrationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the registration.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Registration registration) {
		entityCache.removeResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Registration> registrations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Registration registration : registrations) {
			entityCache.removeResult(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationImpl.class, registration.getPrimaryKey());
		}
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	@Override
	public Registration create(long registrationId) {
		Registration registration = new RegistrationImpl();

		registration.setNew(true);
		registration.setPrimaryKey(registrationId);

		registration.setCompanyId(CompanyThreadLocal.getCompanyId());

		return registration;
	}

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration remove(long registrationId)
		throws NoSuchRegistrationException {

		return remove((Serializable)registrationId);
	}

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration remove(Serializable primaryKey)
		throws NoSuchRegistrationException {

		Session session = null;

		try {
			session = openSession();

			Registration registration = (Registration)session.get(
				RegistrationImpl.class, primaryKey);

			if (registration == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegistrationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(registration);
		}
		catch (NoSuchRegistrationException nsee) {
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
	protected Registration removeImpl(Registration registration) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(registration)) {
				registration = (Registration)session.get(
					RegistrationImpl.class, registration.getPrimaryKeyObj());
			}

			if (registration != null) {
				session.delete(registration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (registration != null) {
			clearCache(registration);
		}

		return registration;
	}

	@Override
	public Registration updateImpl(Registration registration) {
		boolean isNew = registration.isNew();

		if (!(registration instanceof RegistrationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(registration.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					registration);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in registration proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Registration implementation " +
					registration.getClass());
		}

		RegistrationModelImpl registrationModelImpl =
			(RegistrationModelImpl)registration;

		Session session = null;

		try {
			session = openSession();

			if (registration.isNew()) {
				session.save(registration);

				registration.setNew(false);
			}
			else {
				registration = (Registration)session.merge(registration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RegistrationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByUserRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getArticleId()
			};

			finderCache.removeResult(
				_finderPathCountByArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByArticleRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId(),
				registrationModelImpl.getArticleId()
			};

			finderCache.removeResult(
				_finderPathCountByUserArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserArticleRegistrations,
				args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId(),
				registrationModelImpl.getParentArticleId()
			};

			finderCache.removeResult(
				_finderPathCountByUserChildArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrations, args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrations, args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId(),
					registrationModelImpl.getOriginalArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByUserArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId(),
					registrationModelImpl.getArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByUserArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserArticleRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserChildArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId(),
					registrationModelImpl.getOriginalParentArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByUserChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId(),
					registrationModelImpl.getParentArticleId()
				};

				finderCache.removeResult(
					_finderPathCountByUserChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
					args);
			}
		}

		entityCache.putResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey(), registration, false);

		registration.resetOriginalValues();

		return registration;
	}

	/**
	 * Returns the registration with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegistrationException {

		Registration registration = fetchByPrimaryKey(primaryKey);

		if (registration == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegistrationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return registration;
	}

	/**
	 * Returns the registration with the primary key or throws a <code>NoSuchRegistrationException</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration findByPrimaryKey(long registrationId)
		throws NoSuchRegistrationException {

		return findByPrimaryKey((Serializable)registrationId);
	}

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	@Override
	public Registration fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Registration registration = (Registration)serializable;

		if (registration == null) {
			Session session = null;

			try {
				session = openSession();

				registration = (Registration)session.get(
					RegistrationImpl.class, primaryKey);

				if (registration != null) {
					cacheResult(registration);
				}
				else {
					entityCache.putResult(
						RegistrationModelImpl.ENTITY_CACHE_ENABLED,
						RegistrationImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return registration;
	}

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	@Override
	public Registration fetchByPrimaryKey(long registrationId) {
		return fetchByPrimaryKey((Serializable)registrationId);
	}

	@Override
	public Map<Serializable, Registration> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Registration> map =
			new HashMap<Serializable, Registration>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Registration registration = fetchByPrimaryKey(primaryKey);

			if (registration != null) {
				map.put(primaryKey, registration);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Registration)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_REGISTRATION_WHERE_PKS_IN);

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

			for (Registration registration : (List<Registration>)q.list()) {
				map.put(registration.getPrimaryKeyObj(), registration);

				cacheResult(registration);

				uncachedPrimaryKeys.remove(registration.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, primaryKey, nullModel);
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
	 * Returns all the registrations.
	 *
	 * @return the registrations
	 */
	@Override
	public List<Registration> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	@Override
	public List<Registration> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registrations
	 */
	@Override
	public List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of registrations
	 */
	@Override
	public List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator,
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

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REGISTRATION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATION;

				if (pagination) {
					sql = sql.concat(RegistrationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
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
	 * Removes all the registrations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Registration registration : findAll()) {
			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REGISTRATION);

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
		return RegistrationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
			RegistrationModelImpl.USERID_COLUMN_BITMASK |
			RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
			RegistrationModelImpl.ARTICLEID_COLUMN_BITMASK |
			RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.USERID_COLUMN_BITMASK |
				RegistrationModelImpl.ARTICLEID_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.USERID_COLUMN_BITMASK |
				RegistrationModelImpl.PARENTARTICLEID_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserChildArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserChildArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(RegistrationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REGISTRATION =
		"SELECT registration FROM Registration registration";

	private static final String _SQL_SELECT_REGISTRATION_WHERE_PKS_IN =
		"SELECT registration FROM Registration registration WHERE registrationId IN (";

	private static final String _SQL_SELECT_REGISTRATION_WHERE =
		"SELECT registration FROM Registration registration WHERE ";

	private static final String _SQL_COUNT_REGISTRATION =
		"SELECT COUNT(registration) FROM Registration registration";

	private static final String _SQL_COUNT_REGISTRATION_WHERE =
		"SELECT COUNT(registration) FROM Registration registration WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "registration.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Registration exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Registration exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationPersistenceImpl.class);

}