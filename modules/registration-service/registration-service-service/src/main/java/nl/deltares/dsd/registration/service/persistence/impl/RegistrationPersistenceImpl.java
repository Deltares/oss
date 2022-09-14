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

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.RegistrationTable;
import nl.deltares.dsd.registration.model.impl.RegistrationImpl;
import nl.deltares.dsd.registration.model.impl.RegistrationModelImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationPersistence;
import nl.deltares.dsd.registration.service.persistence.RegistrationUtil;
import nl.deltares.dsd.registration.service.persistence.impl.constants.RegistrationsPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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
@Component(service = {RegistrationPersistence.class, BasePersistence.class})
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
	private FinderPath _finderPathWithPaginationFindByEventRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByEventRegistrations;
	private FinderPath _finderPathCountByEventRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByEventRegistrations;
				finderArgs = new Object[] {groupId, eventResourcePrimaryKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEventRegistrations;
			finderArgs = new Object[] {
				groupId, eventResourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventResourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByEventRegistrations_First(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByEventRegistrations_First(
			groupId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByEventRegistrations_First(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByEventRegistrations(
			groupId, eventResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByEventRegistrations_Last(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByEventRegistrations_Last(
			groupId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByEventRegistrations_Last(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByEventRegistrations(groupId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByEventRegistrations(
			groupId, eventResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByEventRegistrations_PrevAndNext(
				session, registration, groupId, eventResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByEventRegistrations_PrevAndNext(
				session, registration, groupId, eventResourcePrimaryKey,
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

	protected Registration getByEventRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByEventRegistrations(
					groupId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByEventRegistrations;

		Object[] finderArgs = new Object[] {groupId, eventResourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventResourcePrimaryKey);

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

	private static final String _FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByUserEventRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserEventRegistrations;
	private FinderPath _finderPathCountByUserEventRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserEventRegistrations;
				finderArgs = new Object[] {
					groupId, userId, eventResourcePrimaryKey
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserEventRegistrations;
			finderArgs = new Object[] {
				groupId, userId, eventResourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(eventResourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrations_First(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserEventRegistrations_First(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrations_First(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrations_Last(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserEventRegistrations_Last(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrations_Last(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserEventRegistrations_PrevAndNext(
				session, registration, groupId, userId, eventResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserEventRegistrations_PrevAndNext(
				session, registration, groupId, userId, eventResourcePrimaryKey,
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

	protected Registration getByUserEventRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

		sb.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByUserEventRegistrations(
					groupId, userId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserEventRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, eventResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(eventResourcePrimaryKey);

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

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String _FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2 =
		"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserRegistrations;
				finderArgs = new Object[] {groupId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserRegistrations;
			finderArgs = new Object[] {
				groupId, userId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				list = (List<Registration>)QueryUtil.list(
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_USERID_2 =
		"registration.userId = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe;
	private FinderPath
		_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe;
	private FinderPath _finderPathCountByUserRegistrationsRegisteredByMe;

	/**
	 * Returns all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe;
				finderArgs = new Object[] {groupId, registeredByUserId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe;
			finderArgs = new Object[] {
				groupId, registeredByUserId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(registeredByUserId !=
							registration.getRegisteredByUserId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registeredByUserId);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrationsRegisteredByMe_First(
			long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserRegistrationsRegisteredByMe_First(
				groupId, registeredByUserId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registeredByUserId=");
		sb.append(registeredByUserId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrationsRegisteredByMe_First(
		long groupId, long registeredByUserId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrationsRegisteredByMe_Last(
			long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrationsRegisteredByMe_Last(
			groupId, registeredByUserId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registeredByUserId=");
		sb.append(registeredByUserId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrationsRegisteredByMe_Last(
		long groupId, long registeredByUserId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserRegistrationsRegisteredByMe_PrevAndNext(
			long registrationId, long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
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

	protected Registration getByUserRegistrationsRegisteredByMe_PrevAndNext(
		Session session, Registration registration, long groupId,
		long registeredByUserId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

		sb.append(
			_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(registeredByUserId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and registeredByUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 */
	@Override
	public void removeByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		for (Registration registration :
				findByUserRegistrationsRegisteredByMe(
					groupId, registeredByUserId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		FinderPath finderPath =
			_finderPathCountByUserRegistrationsRegisteredByMe;

		Object[] finderArgs = new Object[] {groupId, registeredByUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registeredByUserId);

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

	private static final String
		_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2 =
			"registration.registeredByUserId = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe;
	private FinderPath
		_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe;
	private FinderPath _finderPathCountByUserEventRegistrationsRegisteredByMe;

	/**
	 * Returns all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end, OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe;
				finderArgs = new Object[] {
					groupId, registeredByUserId, eventResourcePrimaryKey
				};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe;
			finderArgs = new Object[] {
				groupId, registeredByUserId, eventResourcePrimaryKey, start,
				end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(registeredByUserId !=
							registration.getRegisteredByUserId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registeredByUserId);

				queryPos.add(eventResourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrationsRegisteredByMe_First(
			long groupId, long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserEventRegistrationsRegisteredByMe_First(
				groupId, registeredByUserId, eventResourcePrimaryKey,
				orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registeredByUserId=");
		sb.append(registeredByUserId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrationsRegisteredByMe_First(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrationsRegisteredByMe_Last(
			long groupId, long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserEventRegistrationsRegisteredByMe_Last(
				groupId, registeredByUserId, eventResourcePrimaryKey,
				orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registeredByUserId=");
		sb.append(registeredByUserId);

		sb.append(", eventResourcePrimaryKey=");
		sb.append(eventResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrationsRegisteredByMe_Last(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[]
			findByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				long registrationId, long groupId, long registeredByUserId,
				long eventResourcePrimaryKey,
				OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				eventResourcePrimaryKey, orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				eventResourcePrimaryKey, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration
		getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
			Session session, Registration registration, long groupId,
			long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

		sb.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

		sb.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(registeredByUserId);

		queryPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByUserEventRegistrationsRegisteredByMe(
					groupId, registeredByUserId, eventResourcePrimaryKey,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		FinderPath finderPath =
			_finderPathCountByUserEventRegistrationsRegisteredByMe;

		Object[] finderArgs = new Object[] {
			groupId, registeredByUserId, eventResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			sb.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registeredByUserId);

				queryPos.add(eventResourcePrimaryKey);

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

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2 =
			"registration.registeredByUserId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByArticleRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByArticleRegistrations;
	private FinderPath _finderPathCountByArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByArticleRegistrations;
				finderArgs = new Object[] {groupId, resourcePrimaryKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByArticleRegistrations;
			finderArgs = new Object[] {
				groupId, resourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(resourcePrimaryKey !=
							registration.getResourcePrimaryKey())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(resourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_First(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_First(
			groupId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", resourcePrimaryKey=");
		sb.append(resourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_First(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByArticleRegistrations(
			groupId, resourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_Last(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_Last(
			groupId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", resourcePrimaryKey=");
		sb.append(resourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_Last(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByArticleRegistrations(groupId, resourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByArticleRegistrations(
			groupId, resourcePrimaryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, resourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, resourcePrimaryKey,
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

	protected Registration getByArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(resourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	@Override
	public void removeByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		for (Registration registration :
				findByArticleRegistrations(
					groupId, resourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, resourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(resourcePrimaryKey);

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

	private static final String _FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2 =
			"registration.resourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByUserArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserArticleRegistrations;
	private FinderPath _finderPathCountByUserArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start,
		int end) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserArticleRegistrations;
				finderArgs = new Object[] {groupId, userId, resourcePrimaryKey};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByUserArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, resourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(resourcePrimaryKey !=
							registration.getResourcePrimaryKey())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(resourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_First(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_First(
			groupId, userId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", resourcePrimaryKey=");
		sb.append(resourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_First(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_Last(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_Last(
			groupId, userId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", resourcePrimaryKey=");
		sb.append(resourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_Last(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, resourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, resourcePrimaryKey,
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

	protected Registration getByUserArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

		sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(resourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	@Override
	public void removeByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		for (Registration registration :
				findByUserArticleRegistrations(
					groupId, userId, resourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserArticleRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, resourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(resourcePrimaryKey);

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

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2 =
			"registration.resourcePrimaryKey = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserChildArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
	private FinderPath _finderPathCountByUserChildArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
				finderArgs = new Object[] {
					groupId, userId, parentResourcePrimaryKey
				};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByUserChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, parentResourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(parentResourcePrimaryKey !=
							registration.getParentResourcePrimaryKey())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(parentResourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_First(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_First(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", parentResourcePrimaryKey=");
		sb.append(parentResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_First(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_Last(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_Last(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", parentResourcePrimaryKey=");
		sb.append(parentResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_Last(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId,
				parentResourcePrimaryKey, orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId,
				parentResourcePrimaryKey, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserChildArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

		sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

		sb.append(
			_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(parentResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	@Override
	public void removeByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		for (Registration registration :
				findByUserChildArticleRegistrations(
					groupId, userId, parentResourcePrimaryKey,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserChildArticleRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, parentResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(parentResourcePrimaryKey);

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

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2 =
			"registration.parentResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByChildArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByChildArticleRegistrations;
	private FinderPath _finderPathCountByChildArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByChildArticleRegistrations;
				finderArgs = new Object[] {groupId, parentResourcePrimaryKey};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, parentResourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(parentResourcePrimaryKey !=
							registration.getParentResourcePrimaryKey())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(parentResourcePrimaryKey);

				list = (List<Registration>)QueryUtil.list(
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
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByChildArticleRegistrations_First(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByChildArticleRegistrations_First(
			groupId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", parentResourcePrimaryKey=");
		sb.append(parentResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByChildArticleRegistrations_First(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByChildArticleRegistrations_Last(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByChildArticleRegistrations_Last(
			groupId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", parentResourcePrimaryKey=");
		sb.append(parentResourcePrimaryKey);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByChildArticleRegistrations_Last(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, parentResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, parentResourcePrimaryKey,
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

	protected Registration getByChildArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

		sb.append(
			_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

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
			sb.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(parentResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	@Override
	public void removeByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		for (Registration registration :
				findByChildArticleRegistrations(
					groupId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByChildArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, parentResourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(parentResourcePrimaryKey);

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

	private static final String
		_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2 =
			"registration.parentResourcePrimaryKey = ?";

	public RegistrationPersistenceImpl() {
		setModelClass(Registration.class);

		setModelImplClass(RegistrationImpl.class);
		setModelPKClass(long.class);

		setTable(RegistrationTable.INSTANCE);
	}

	/**
	 * Caches the registration in the entity cache if it is enabled.
	 *
	 * @param registration the registration
	 */
	@Override
	public void cacheResult(Registration registration) {
		entityCache.putResult(
			RegistrationImpl.class, registration.getPrimaryKey(), registration);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the registrations in the entity cache if it is enabled.
	 *
	 * @param registrations the registrations
	 */
	@Override
	public void cacheResult(List<Registration> registrations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (registrations.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Registration registration : registrations) {
			if (entityCache.getResult(
					RegistrationImpl.class, registration.getPrimaryKey()) ==
						null) {

				cacheResult(registration);
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

		finderCache.clearCache(RegistrationImpl.class);
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
		entityCache.removeResult(RegistrationImpl.class, registration);
	}

	@Override
	public void clearCache(List<Registration> registrations) {
		for (Registration registration : registrations) {
			entityCache.removeResult(RegistrationImpl.class, registration);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RegistrationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RegistrationImpl.class, primaryKey);
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
		catch (NoSuchRegistrationException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

			if (isNew) {
				session.save(registration);
			}
			else {
				registration = (Registration)session.merge(registration);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RegistrationImpl.class, registrationModelImpl, false, true);

		if (isNew) {
			registration.setNew(false);
		}

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
	 * @param registrationId the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	@Override
	public Registration fetchByPrimaryKey(long registrationId) {
		return fetchByPrimaryKey((Serializable)registrationId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registrations
	 */
	@Override
	public List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator,
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

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REGISTRATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATION;

				sql = sql.concat(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Registration>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REGISTRATION);

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
		return "registrationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REGISTRATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegistrationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration persistence.
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

		_finderPathWithPaginationFindByEventRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "eventResourcePrimaryKey"}, true);

		_finderPathWithoutPaginationFindByEventRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByEventRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventResourcePrimaryKey"}, true);

		_finderPathCountByEventRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEventRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventResourcePrimaryKey"}, false);

		_finderPathWithPaginationFindByUserEventRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "eventResourcePrimaryKey"},
			true);

		_finderPathWithoutPaginationFindByUserEventRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserEventRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				new String[] {"groupId", "userId", "eventResourcePrimaryKey"},
				true);

		_finderPathCountByUserEventRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "eventResourcePrimaryKey"},
			false);

		_finderPathWithPaginationFindByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId"}, true);

		_finderPathWithoutPaginationFindByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"groupId", "registeredByUserId"}, true);

		_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserRegistrationsRegisteredByMe",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"groupId", "registeredByUserId"}, true);

		_finderPathCountByUserRegistrationsRegisteredByMe = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrationsRegisteredByMe",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "registeredByUserId"}, false);

		_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserEventRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {
					"groupId", "registeredByUserId", "eventResourcePrimaryKey"
				},
				true);

		_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserEventRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				new String[] {
					"groupId", "registeredByUserId", "eventResourcePrimaryKey"
				},
				true);

		_finderPathCountByUserEventRegistrationsRegisteredByMe = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserEventRegistrationsRegisteredByMe",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"groupId", "registeredByUserId", "eventResourcePrimaryKey"
			},
			false);

		_finderPathWithPaginationFindByArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "resourcePrimaryKey"}, true);

		_finderPathWithoutPaginationFindByArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "resourcePrimaryKey"}, true);

		_finderPathCountByArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "resourcePrimaryKey"}, false);

		_finderPathWithPaginationFindByUserArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"groupId", "userId", "resourcePrimaryKey"}, true);

		_finderPathWithoutPaginationFindByUserArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				new String[] {"groupId", "userId", "resourcePrimaryKey"}, true);

		_finderPathCountByUserArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "resourcePrimaryKey"}, false);

		_finderPathWithPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"groupId", "userId", "parentResourcePrimaryKey"},
				true);

		_finderPathWithoutPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				new String[] {"groupId", "userId", "parentResourcePrimaryKey"},
				true);

		_finderPathCountByUserChildArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserChildArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "userId", "parentResourcePrimaryKey"},
			false);

		_finderPathWithPaginationFindByChildArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"groupId", "parentResourcePrimaryKey"}, true);

		_finderPathWithoutPaginationFindByChildArticleRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByChildArticleRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"groupId", "parentResourcePrimaryKey"}, true);

		_finderPathCountByChildArticleRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByChildArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "parentResourcePrimaryKey"}, false);

		_setRegistrationUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setRegistrationUtilPersistence(null);

		entityCache.removeCache(RegistrationImpl.class.getName());
	}

	private void _setRegistrationUtilPersistence(
		RegistrationPersistence registrationPersistence) {

		try {
			Field field = RegistrationUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, registrationPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = RegistrationsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RegistrationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RegistrationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REGISTRATION =
		"SELECT registration FROM Registration registration";

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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RegistrationModelArgumentsResolver
		_registrationModelArgumentsResolver;

}