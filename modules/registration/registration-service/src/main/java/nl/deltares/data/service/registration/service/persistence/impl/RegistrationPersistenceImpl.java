/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.deltares.data.service.registration.exception.NoSuchRegistrationException;
import nl.deltares.data.service.registration.model.Registration;
import nl.deltares.data.service.registration.model.RegistrationTable;
import nl.deltares.data.service.registration.model.impl.RegistrationImpl;
import nl.deltares.data.service.registration.model.impl.RegistrationModelImpl;
import nl.deltares.data.service.registration.service.persistence.RegistrationPersistence;
import nl.deltares.data.service.registration.service.persistence.RegistrationUtil;
import nl.deltares.data.service.registration.service.persistence.impl.constants.Service_builderPersistenceConstants;

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
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RegistrationPersistence.class)
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
	private FinderPath _finderPathWithPaginationFindByRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByRegistrations;
	private FinderPath _finderPathCountByRegistrations;

	/**
	 * Returns all the registrations where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByRegistrations(long registrationResourceId) {
		return findByRegistrations(
			registrationResourceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end) {

		return findByRegistrations(registrationResourceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByRegistrations(
			registrationResourceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRegistrations;
				finderArgs = new Object[] {registrationResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRegistrations;
			finderArgs = new Object[] {
				registrationResourceId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if (registrationResourceId !=
							registration.getRegistrationResourceId()) {

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

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_REGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

				queryPos.add(registrationResourceId);

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
	 * Returns the first registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByRegistrations_First(
			long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByRegistrations_First(
			registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByRegistrations_First(
		long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByRegistrations(
			registrationResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByRegistrations_Last(
			long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByRegistrations_Last(
			registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByRegistrations_Last(
		long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByRegistrations(registrationResourceId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByRegistrations(
			registrationResourceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByRegistrations_PrevAndNext(
			long registrationId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByRegistrations_PrevAndNext(
				session, registration, registrationResourceId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByRegistrations_PrevAndNext(
				session, registration, registrationResourceId,
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

	protected Registration getByRegistrations_PrevAndNext(
		Session session, Registration registration, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_REGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

		queryPos.add(registrationResourceId);

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
	 * Removes all the registrations where registrationResourceId = &#63; from the database.
	 *
	 * @param registrationResourceId the registration resource ID
	 */
	@Override
	public void removeByRegistrations(long registrationResourceId) {
		for (Registration registration :
				findByRegistrations(
					registrationResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByRegistrations(long registrationResourceId) {
		FinderPath finderPath = _finderPathCountByRegistrations;

		Object[] finderArgs = new Object[] {registrationResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_REGISTRATIONS_REGISTRATIONRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(registrationResourceId);

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
		_FINDER_COLUMN_REGISTRATIONS_REGISTRATIONRESOURCEID_2 =
			"registration.registrationResourceId = ?";

	private FinderPath _finderPathWithPaginationFindByUserRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserRegistrations;
	private FinderPath _finderPathCountByUserRegistrations;

	/**
	 * Returns all the registrations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(long userId) {
		return findByUserRegistrations(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long userId, int start, int end) {

		return findByUserRegistrations(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserRegistrations(
			userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserRegistrations;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserRegistrations;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if (userId != registration.getUserId()) {
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

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

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
	 * Returns the first registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_First(
			long userId, OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_First(
			userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_First(
		long userId, OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserRegistrations(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_Last(
			long userId, OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_Last(
			userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_Last(
		long userId, OrderByComparator<Registration> orderByComparator) {

		int count = countByUserRegistrations(userId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserRegistrations(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserRegistrations_PrevAndNext(
				session, registration, userId, orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserRegistrations_PrevAndNext(
				session, registration, userId, orderByComparator, false);

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
		Session session, Registration registration, long userId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

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
	 * Removes all the registrations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserRegistrations(long userId) {
		for (Registration registration :
				findByUserRegistrations(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserRegistrations(long userId) {
		FinderPath finderPath = _finderPathCountByUserRegistrations;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_USERID_2 =
		"registration.userId = ?";

	private FinderPath _finderPathWithPaginationFindByUserResourceRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserResourceRegistrations;
	private FinderPath _finderPathCountByUserResourceRegistrations;

	/**
	 * Returns all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		return findByUserResourceRegistrations(
			userId, registrationResourceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end) {

		return findByUserResourceRegistrations(
			userId, registrationResourceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserResourceRegistrations(
			userId, registrationResourceId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserResourceRegistrations;
				finderArgs = new Object[] {userId, registrationResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByUserResourceRegistrations;
			finderArgs = new Object[] {
				userId, registrationResourceId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((userId != registration.getUserId()) ||
						(registrationResourceId !=
							registration.getRegistrationResourceId())) {

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

			sb.append(_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

				queryPos.add(userId);

				queryPos.add(registrationResourceId);

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
	 * Returns the first registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserResourceRegistrations_First(
			long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserResourceRegistrations_First(
			userId, registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserResourceRegistrations_First(
		long userId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserResourceRegistrations(
			userId, registrationResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserResourceRegistrations_Last(
			long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserResourceRegistrations_Last(
			userId, registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserResourceRegistrations_Last(
		long userId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserResourceRegistrations(
			userId, registrationResourceId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserResourceRegistrations(
			userId, registrationResourceId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserResourceRegistrations_PrevAndNext(
			long registrationId, long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserResourceRegistrations_PrevAndNext(
				session, registration, userId, registrationResourceId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserResourceRegistrations_PrevAndNext(
				session, registration, userId, registrationResourceId,
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

	protected Registration getByUserResourceRegistrations_PrevAndNext(
		Session session, Registration registration, long userId,
		long registrationResourceId,
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

		sb.append(_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_USERID_2);

		sb.append(
			_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

		queryPos.add(userId);

		queryPos.add(registrationResourceId);

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
	 * Removes all the registrations where userId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 */
	@Override
	public void removeByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		for (Registration registration :
				findByUserResourceRegistrations(
					userId, registrationResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		FinderPath finderPath = _finderPathCountByUserResourceRegistrations;

		Object[] finderArgs = new Object[] {userId, registrationResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_USERID_2);

			sb.append(
				_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(registrationResourceId);

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
		_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2 =
			"registration.registrationResourceId = ?";

	private FinderPath _finderPathWithPaginationFindByUserGroupRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserGroupRegistrations;
	private FinderPath _finderPathCountByUserGroupRegistrations;

	/**
	 * Returns all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserGroupRegistrations(
		long userId, long groupId) {

		return findByUserGroupRegistrations(
			userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end) {

		return findByUserGroupRegistrations(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserGroupRegistrations(
			userId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByUserGroupRegistrations;
				finderArgs = new Object[] {userId, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserGroupRegistrations;
			finderArgs = new Object[] {
				userId, groupId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((userId != registration.getUserId()) ||
						(groupId != registration.getGroupId())) {

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

			sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_USERID_2);

			sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_GROUPID_2);

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

				queryPos.add(userId);

				queryPos.add(groupId);

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
	 * Returns the first registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserGroupRegistrations_First(
			long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserGroupRegistrations_First(
			userId, groupId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserGroupRegistrations_First(
		long userId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserGroupRegistrations(
			userId, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserGroupRegistrations_Last(
			long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserGroupRegistrations_Last(
			userId, groupId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserGroupRegistrations_Last(
		long userId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserGroupRegistrations(userId, groupId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserGroupRegistrations(
			userId, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserGroupRegistrations_PrevAndNext(
			long registrationId, long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserGroupRegistrations_PrevAndNext(
				session, registration, userId, groupId, orderByComparator,
				true);

			array[1] = registration;

			array[2] = getByUserGroupRegistrations_PrevAndNext(
				session, registration, userId, groupId, orderByComparator,
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

	protected Registration getByUserGroupRegistrations_PrevAndNext(
		Session session, Registration registration, long userId, long groupId,
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

		sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_USERID_2);

		sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_GROUPID_2);

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

		queryPos.add(userId);

		queryPos.add(groupId);

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
	 * Removes all the registrations where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUserGroupRegistrations(long userId, long groupId) {
		for (Registration registration :
				findByUserGroupRegistrations(
					userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserGroupRegistrations(long userId, long groupId) {
		FinderPath finderPath = _finderPathCountByUserGroupRegistrations;

		Object[] finderArgs = new Object[] {userId, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_USERID_2);

			sb.append(_FINDER_COLUMN_USERGROUPREGISTRATIONS_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_USERGROUPREGISTRATIONS_USERID_2 =
		"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERGROUPREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByAuthorRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByAuthorRegistrations;
	private FinderPath _finderPathCountByAuthorRegistrations;

	/**
	 * Returns all the registrations where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByAuthorRegistrations(long authorId) {
		return findByAuthorRegistrations(
			authorId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end) {

		return findByAuthorRegistrations(authorId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByAuthorRegistrations(
			authorId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAuthorRegistrations;
				finderArgs = new Object[] {authorId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAuthorRegistrations;
			finderArgs = new Object[] {authorId, start, end, orderByComparator};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if (authorId != registration.getAuthorId()) {
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

			sb.append(_SQL_SELECT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_AUTHORREGISTRATIONS_AUTHORID_2);

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

				queryPos.add(authorId);

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
	 * Returns the first registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorRegistrations_First(
			long authorId, OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorRegistrations_First(
			authorId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorRegistrations_First(
		long authorId, OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByAuthorRegistrations(
			authorId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorRegistrations_Last(
			long authorId, OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorRegistrations_Last(
			authorId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorRegistrations_Last(
		long authorId, OrderByComparator<Registration> orderByComparator) {

		int count = countByAuthorRegistrations(authorId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByAuthorRegistrations(
			authorId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByAuthorRegistrations_PrevAndNext(
			long registrationId, long authorId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByAuthorRegistrations_PrevAndNext(
				session, registration, authorId, orderByComparator, true);

			array[1] = registration;

			array[2] = getByAuthorRegistrations_PrevAndNext(
				session, registration, authorId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByAuthorRegistrations_PrevAndNext(
		Session session, Registration registration, long authorId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REGISTRATION_WHERE);

		sb.append(_FINDER_COLUMN_AUTHORREGISTRATIONS_AUTHORID_2);

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

		queryPos.add(authorId);

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
	 * Removes all the registrations where authorId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 */
	@Override
	public void removeByAuthorRegistrations(long authorId) {
		for (Registration registration :
				findByAuthorRegistrations(
					authorId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByAuthorRegistrations(long authorId) {
		FinderPath finderPath = _finderPathCountByAuthorRegistrations;

		Object[] finderArgs = new Object[] {authorId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_AUTHORREGISTRATIONS_AUTHORID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(authorId);

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

	private static final String _FINDER_COLUMN_AUTHORREGISTRATIONS_AUTHORID_2 =
		"registration.authorId = ?";

	private FinderPath
		_finderPathWithPaginationFindByAuthorResourceRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByAuthorResourceRegistrations;
	private FinderPath _finderPathCountByAuthorResourceRegistrations;

	/**
	 * Returns all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		return findByAuthorResourceRegistrations(
			authorId, registrationResourceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end) {

		return findByAuthorResourceRegistrations(
			authorId, registrationResourceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByAuthorResourceRegistrations(
			authorId, registrationResourceId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAuthorResourceRegistrations;
				finderArgs = new Object[] {authorId, registrationResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByAuthorResourceRegistrations;
			finderArgs = new Object[] {
				authorId, registrationResourceId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((authorId != registration.getAuthorId()) ||
						(registrationResourceId !=
							registration.getRegistrationResourceId())) {

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

			sb.append(_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_AUTHORID_2);

			sb.append(
				_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

				queryPos.add(authorId);

				queryPos.add(registrationResourceId);

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
	 * Returns the first registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorResourceRegistrations_First(
			long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorResourceRegistrations_First(
			authorId, registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorResourceRegistrations_First(
		long authorId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByAuthorResourceRegistrations(
			authorId, registrationResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorResourceRegistrations_Last(
			long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorResourceRegistrations_Last(
			authorId, registrationResourceId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorResourceRegistrations_Last(
		long authorId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByAuthorResourceRegistrations(
			authorId, registrationResourceId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByAuthorResourceRegistrations(
			authorId, registrationResourceId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByAuthorResourceRegistrations_PrevAndNext(
			long registrationId, long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByAuthorResourceRegistrations_PrevAndNext(
				session, registration, authorId, registrationResourceId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByAuthorResourceRegistrations_PrevAndNext(
				session, registration, authorId, registrationResourceId,
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

	protected Registration getByAuthorResourceRegistrations_PrevAndNext(
		Session session, Registration registration, long authorId,
		long registrationResourceId,
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

		sb.append(_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_AUTHORID_2);

		sb.append(
			_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

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

		queryPos.add(authorId);

		queryPos.add(registrationResourceId);

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
	 * Removes all the registrations where authorId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 */
	@Override
	public void removeByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		for (Registration registration :
				findByAuthorResourceRegistrations(
					authorId, registrationResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		FinderPath finderPath = _finderPathCountByAuthorResourceRegistrations;

		Object[] finderArgs = new Object[] {authorId, registrationResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_AUTHORID_2);

			sb.append(
				_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(authorId);

				queryPos.add(registrationResourceId);

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
		_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_AUTHORID_2 =
			"registration.authorId = ? AND ";

	private static final String
		_FINDER_COLUMN_AUTHORRESOURCEREGISTRATIONS_REGISTRATIONRESOURCEID_2 =
			"registration.registrationResourceId = ?";

	private FinderPath _finderPathWithPaginationFindByAuthorGroupRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByAuthorGroupRegistrations;
	private FinderPath _finderPathCountByAuthorGroupRegistrations;

	/**
	 * Returns all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId) {

		return findByAuthorGroupRegistrations(
			authorId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end) {

		return findByAuthorGroupRegistrations(
			authorId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByAuthorGroupRegistrations(
			authorId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAuthorGroupRegistrations;
				finderArgs = new Object[] {authorId, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByAuthorGroupRegistrations;
			finderArgs = new Object[] {
				authorId, groupId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (useFinderCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((authorId != registration.getAuthorId()) ||
						(groupId != registration.getGroupId())) {

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

			sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_AUTHORID_2);

			sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_GROUPID_2);

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

				queryPos.add(authorId);

				queryPos.add(groupId);

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
	 * Returns the first registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorGroupRegistrations_First(
			long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorGroupRegistrations_First(
			authorId, groupId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorGroupRegistrations_First(
		long authorId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByAuthorGroupRegistrations(
			authorId, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByAuthorGroupRegistrations_Last(
			long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByAuthorGroupRegistrations_Last(
			authorId, groupId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("authorId=");
		sb.append(authorId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchRegistrationException(sb.toString());
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByAuthorGroupRegistrations_Last(
		long authorId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByAuthorGroupRegistrations(authorId, groupId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByAuthorGroupRegistrations(
			authorId, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByAuthorGroupRegistrations_PrevAndNext(
			long registrationId, long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByAuthorGroupRegistrations_PrevAndNext(
				session, registration, authorId, groupId, orderByComparator,
				true);

			array[1] = registration;

			array[2] = getByAuthorGroupRegistrations_PrevAndNext(
				session, registration, authorId, groupId, orderByComparator,
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

	protected Registration getByAuthorGroupRegistrations_PrevAndNext(
		Session session, Registration registration, long authorId, long groupId,
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

		sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_AUTHORID_2);

		sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_GROUPID_2);

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

		queryPos.add(authorId);

		queryPos.add(groupId);

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
	 * Removes all the registrations where authorId = &#63; and groupId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByAuthorGroupRegistrations(long authorId, long groupId) {
		for (Registration registration :
				findByAuthorGroupRegistrations(
					authorId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByAuthorGroupRegistrations(long authorId, long groupId) {
		FinderPath finderPath = _finderPathCountByAuthorGroupRegistrations;

		Object[] finderArgs = new Object[] {authorId, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATION_WHERE);

			sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_AUTHORID_2);

			sb.append(_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(authorId);

				queryPos.add(groupId);

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
		_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_AUTHORID_2 =
			"registration.authorId = ? AND ";

	private static final String
		_FINDER_COLUMN_AUTHORGROUPREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ?";

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
				finderPath, finderArgs, this);
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
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

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

		_finderPathWithPaginationFindByRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRegistrations",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"registrationResourceId"}, true);

		_finderPathWithoutPaginationFindByRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRegistrations",
			new String[] {Long.class.getName()},
			new String[] {"registrationResourceId"}, true);

		_finderPathCountByRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRegistrations",
			new String[] {Long.class.getName()},
			new String[] {"registrationResourceId"}, false);

		_finderPathWithPaginationFindByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserRegistrations",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserRegistrations", new String[] {Long.class.getName()},
			new String[] {"userId"}, true);

		_finderPathCountByUserRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrations", new String[] {Long.class.getName()},
			new String[] {"userId"}, false);

		_finderPathWithPaginationFindByUserResourceRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserResourceRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"userId", "registrationResourceId"}, true);

		_finderPathWithoutPaginationFindByUserResourceRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserResourceRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"userId", "registrationResourceId"}, true);

		_finderPathCountByUserResourceRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserResourceRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "registrationResourceId"}, false);

		_finderPathWithPaginationFindByUserGroupRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserGroupRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "groupId"}, true);

		_finderPathWithoutPaginationFindByUserGroupRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserGroupRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"userId", "groupId"}, true);

		_finderPathCountByUserGroupRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserGroupRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "groupId"}, false);

		_finderPathWithPaginationFindByAuthorRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAuthorRegistrations",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"authorId"}, true);

		_finderPathWithoutPaginationFindByAuthorRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAuthorRegistrations", new String[] {Long.class.getName()},
			new String[] {"authorId"}, true);

		_finderPathCountByAuthorRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAuthorRegistrations", new String[] {Long.class.getName()},
			new String[] {"authorId"}, false);

		_finderPathWithPaginationFindByAuthorResourceRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByAuthorResourceRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"authorId", "registrationResourceId"}, true);

		_finderPathWithoutPaginationFindByAuthorResourceRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByAuthorResourceRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"authorId", "registrationResourceId"}, true);

		_finderPathCountByAuthorResourceRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAuthorResourceRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"authorId", "registrationResourceId"}, false);

		_finderPathWithPaginationFindByAuthorGroupRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByAuthorGroupRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				},
				new String[] {"authorId", "groupId"}, true);

		_finderPathWithoutPaginationFindByAuthorGroupRegistrations =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByAuthorGroupRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				new String[] {"authorId", "groupId"}, true);

		_finderPathCountByAuthorGroupRegistrations = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAuthorGroupRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"authorId", "groupId"}, false);

		RegistrationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		RegistrationUtil.setPersistence(null);

		entityCache.removeCache(RegistrationImpl.class.getName());
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

}