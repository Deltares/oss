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

import nl.deltares.data.service.registration.exception.NoSuchRegistrationPeriodException;
import nl.deltares.data.service.registration.model.RegistrationPeriod;
import nl.deltares.data.service.registration.model.RegistrationPeriodTable;
import nl.deltares.data.service.registration.model.impl.RegistrationPeriodImpl;
import nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl;
import nl.deltares.data.service.registration.service.persistence.RegistrationPeriodPersistence;
import nl.deltares.data.service.registration.service.persistence.RegistrationPeriodUtil;
import nl.deltares.data.service.registration.service.persistence.impl.constants.Service_builderPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the registration period service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RegistrationPeriodPersistence.class)
public class RegistrationPeriodPersistenceImpl
	extends BasePersistenceImpl<RegistrationPeriod>
	implements RegistrationPeriodPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegistrationPeriodUtil</code> to access the registration period persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegistrationPeriodImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByResourcePeriods;
	private FinderPath _finderPathWithoutPaginationFindByResourcePeriods;
	private FinderPath _finderPathCountByResourcePeriods;

	/**
	 * Returns all the registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration periods
	 */
	@Override
	public List<RegistrationPeriod> findByResourcePeriods(
		long registrationResourceId) {

		return findByResourcePeriods(
			registrationResourceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @return the range of matching registration periods
	 */
	@Override
	public List<RegistrationPeriod> findByResourcePeriods(
		long registrationResourceId, int start, int end) {

		return findByResourcePeriods(registrationResourceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration periods
	 */
	@Override
	public List<RegistrationPeriod> findByResourcePeriods(
		long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return findByResourcePeriods(
			registrationResourceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration periods
	 */
	@Override
	public List<RegistrationPeriod> findByResourcePeriods(
		long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByResourcePeriods;
				finderArgs = new Object[] {registrationResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByResourcePeriods;
			finderArgs = new Object[] {
				registrationResourceId, start, end, orderByComparator
			};
		}

		List<RegistrationPeriod> list = null;

		if (useFinderCache) {
			list = (List<RegistrationPeriod>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationPeriod registrationPeriod : list) {
					if (registrationResourceId !=
							registrationPeriod.getRegistrationResourceId()) {

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

			sb.append(_SQL_SELECT_REGISTRATIONPERIOD_WHERE);

			sb.append(_FINDER_COLUMN_RESOURCEPERIODS_REGISTRATIONRESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationPeriodModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(registrationResourceId);

				list = (List<RegistrationPeriod>)QueryUtil.list(
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
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	@Override
	public RegistrationPeriod findByResourcePeriods_First(
			long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws NoSuchRegistrationPeriodException {

		RegistrationPeriod registrationPeriod = fetchByResourcePeriods_First(
			registrationResourceId, orderByComparator);

		if (registrationPeriod != null) {
			return registrationPeriod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationPeriodException(sb.toString());
	}

	/**
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	@Override
	public RegistrationPeriod fetchByResourcePeriods_First(
		long registrationResourceId,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		List<RegistrationPeriod> list = findByResourcePeriods(
			registrationResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	@Override
	public RegistrationPeriod findByResourcePeriods_Last(
			long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws NoSuchRegistrationPeriodException {

		RegistrationPeriod registrationPeriod = fetchByResourcePeriods_Last(
			registrationResourceId, orderByComparator);

		if (registrationPeriod != null) {
			return registrationPeriod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationPeriodException(sb.toString());
	}

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	@Override
	public RegistrationPeriod fetchByResourcePeriods_Last(
		long registrationResourceId,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		int count = countByResourcePeriods(registrationResourceId);

		if (count == 0) {
			return null;
		}

		List<RegistrationPeriod> list = findByResourcePeriods(
			registrationResourceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registration periods before and after the current registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationPeriodId the primary key of the current registration period
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod[] findByResourcePeriods_PrevAndNext(
			long registrationPeriodId, long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws NoSuchRegistrationPeriodException {

		RegistrationPeriod registrationPeriod = findByPrimaryKey(
			registrationPeriodId);

		Session session = null;

		try {
			session = openSession();

			RegistrationPeriod[] array = new RegistrationPeriodImpl[3];

			array[0] = getByResourcePeriods_PrevAndNext(
				session, registrationPeriod, registrationResourceId,
				orderByComparator, true);

			array[1] = registrationPeriod;

			array[2] = getByResourcePeriods_PrevAndNext(
				session, registrationPeriod, registrationResourceId,
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

	protected RegistrationPeriod getByResourcePeriods_PrevAndNext(
		Session session, RegistrationPeriod registrationPeriod,
		long registrationResourceId,
		OrderByComparator<RegistrationPeriod> orderByComparator,
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

		sb.append(_SQL_SELECT_REGISTRATIONPERIOD_WHERE);

		sb.append(_FINDER_COLUMN_RESOURCEPERIODS_REGISTRATIONRESOURCEID_2);

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
			sb.append(RegistrationPeriodModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(registrationResourceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						registrationPeriod)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegistrationPeriod> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registration periods where registrationResourceId = &#63; from the database.
	 *
	 * @param registrationResourceId the registration resource ID
	 */
	@Override
	public void removeByResourcePeriods(long registrationResourceId) {
		for (RegistrationPeriod registrationPeriod :
				findByResourcePeriods(
					registrationResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registrationPeriod);
		}
	}

	/**
	 * Returns the number of registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration periods
	 */
	@Override
	public int countByResourcePeriods(long registrationResourceId) {
		FinderPath finderPath = _finderPathCountByResourcePeriods;

		Object[] finderArgs = new Object[] {registrationResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGISTRATIONPERIOD_WHERE);

			sb.append(_FINDER_COLUMN_RESOURCEPERIODS_REGISTRATIONRESOURCEID_2);

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
		_FINDER_COLUMN_RESOURCEPERIODS_REGISTRATIONRESOURCEID_2 =
			"registrationPeriod.registrationResourceId = ?";

	public RegistrationPeriodPersistenceImpl() {
		setModelClass(RegistrationPeriod.class);

		setModelImplClass(RegistrationPeriodImpl.class);
		setModelPKClass(long.class);

		setTable(RegistrationPeriodTable.INSTANCE);
	}

	/**
	 * Caches the registration period in the entity cache if it is enabled.
	 *
	 * @param registrationPeriod the registration period
	 */
	@Override
	public void cacheResult(RegistrationPeriod registrationPeriod) {
		entityCache.putResult(
			RegistrationPeriodImpl.class, registrationPeriod.getPrimaryKey(),
			registrationPeriod);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the registration periods in the entity cache if it is enabled.
	 *
	 * @param registrationPeriods the registration periods
	 */
	@Override
	public void cacheResult(List<RegistrationPeriod> registrationPeriods) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (registrationPeriods.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RegistrationPeriod registrationPeriod : registrationPeriods) {
			if (entityCache.getResult(
					RegistrationPeriodImpl.class,
					registrationPeriod.getPrimaryKey()) == null) {

				cacheResult(registrationPeriod);
			}
		}
	}

	/**
	 * Clears the cache for all registration periods.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RegistrationPeriodImpl.class);

		finderCache.clearCache(RegistrationPeriodImpl.class);
	}

	/**
	 * Clears the cache for the registration period.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RegistrationPeriod registrationPeriod) {
		entityCache.removeResult(
			RegistrationPeriodImpl.class, registrationPeriod);
	}

	@Override
	public void clearCache(List<RegistrationPeriod> registrationPeriods) {
		for (RegistrationPeriod registrationPeriod : registrationPeriods) {
			entityCache.removeResult(
				RegistrationPeriodImpl.class, registrationPeriod);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RegistrationPeriodImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(RegistrationPeriodImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new registration period with the primary key. Does not add the registration period to the database.
	 *
	 * @param registrationPeriodId the primary key for the new registration period
	 * @return the new registration period
	 */
	@Override
	public RegistrationPeriod create(long registrationPeriodId) {
		RegistrationPeriod registrationPeriod = new RegistrationPeriodImpl();

		registrationPeriod.setNew(true);
		registrationPeriod.setPrimaryKey(registrationPeriodId);

		return registrationPeriod;
	}

	/**
	 * Removes the registration period with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period that was removed
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod remove(long registrationPeriodId)
		throws NoSuchRegistrationPeriodException {

		return remove((Serializable)registrationPeriodId);
	}

	/**
	 * Removes the registration period with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the registration period
	 * @return the registration period that was removed
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod remove(Serializable primaryKey)
		throws NoSuchRegistrationPeriodException {

		Session session = null;

		try {
			session = openSession();

			RegistrationPeriod registrationPeriod =
				(RegistrationPeriod)session.get(
					RegistrationPeriodImpl.class, primaryKey);

			if (registrationPeriod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegistrationPeriodException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(registrationPeriod);
		}
		catch (NoSuchRegistrationPeriodException noSuchEntityException) {
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
	protected RegistrationPeriod removeImpl(
		RegistrationPeriod registrationPeriod) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(registrationPeriod)) {
				registrationPeriod = (RegistrationPeriod)session.get(
					RegistrationPeriodImpl.class,
					registrationPeriod.getPrimaryKeyObj());
			}

			if (registrationPeriod != null) {
				session.delete(registrationPeriod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (registrationPeriod != null) {
			clearCache(registrationPeriod);
		}

		return registrationPeriod;
	}

	@Override
	public RegistrationPeriod updateImpl(
		RegistrationPeriod registrationPeriod) {

		boolean isNew = registrationPeriod.isNew();

		if (!(registrationPeriod instanceof RegistrationPeriodModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(registrationPeriod.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					registrationPeriod);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in registrationPeriod proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RegistrationPeriod implementation " +
					registrationPeriod.getClass());
		}

		RegistrationPeriodModelImpl registrationPeriodModelImpl =
			(RegistrationPeriodModelImpl)registrationPeriod;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(registrationPeriod);
			}
			else {
				registrationPeriod = (RegistrationPeriod)session.merge(
					registrationPeriod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RegistrationPeriodImpl.class, registrationPeriodModelImpl, false,
			true);

		if (isNew) {
			registrationPeriod.setNew(false);
		}

		registrationPeriod.resetOriginalValues();

		return registrationPeriod;
	}

	/**
	 * Returns the registration period with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration period
	 * @return the registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegistrationPeriodException {

		RegistrationPeriod registrationPeriod = fetchByPrimaryKey(primaryKey);

		if (registrationPeriod == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegistrationPeriodException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return registrationPeriod;
	}

	/**
	 * Returns the registration period with the primary key or throws a <code>NoSuchRegistrationPeriodException</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod findByPrimaryKey(long registrationPeriodId)
		throws NoSuchRegistrationPeriodException {

		return findByPrimaryKey((Serializable)registrationPeriodId);
	}

	/**
	 * Returns the registration period with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period, or <code>null</code> if a registration period with the primary key could not be found
	 */
	@Override
	public RegistrationPeriod fetchByPrimaryKey(long registrationPeriodId) {
		return fetchByPrimaryKey((Serializable)registrationPeriodId);
	}

	/**
	 * Returns all the registration periods.
	 *
	 * @return the registration periods
	 */
	@Override
	public List<RegistrationPeriod> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @return the range of registration periods
	 */
	@Override
	public List<RegistrationPeriod> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registration periods
	 */
	@Override
	public List<RegistrationPeriod> findAll(
		int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registration periods
	 */
	@Override
	public List<RegistrationPeriod> findAll(
		int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator,
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

		List<RegistrationPeriod> list = null;

		if (useFinderCache) {
			list = (List<RegistrationPeriod>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REGISTRATIONPERIOD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATIONPERIOD;

				sql = sql.concat(RegistrationPeriodModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RegistrationPeriod>)QueryUtil.list(
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
	 * Removes all the registration periods from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RegistrationPeriod registrationPeriod : findAll()) {
			remove(registrationPeriod);
		}
	}

	/**
	 * Returns the number of registration periods.
	 *
	 * @return the number of registration periods
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_REGISTRATIONPERIOD);

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
		return "registrationPeriodId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REGISTRATIONPERIOD;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegistrationPeriodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration period persistence.
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

		_finderPathWithPaginationFindByResourcePeriods = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePeriods",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"registrationResourceId"}, true);

		_finderPathWithoutPaginationFindByResourcePeriods = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePeriods",
			new String[] {Long.class.getName()},
			new String[] {"registrationResourceId"}, true);

		_finderPathCountByResourcePeriods = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByResourcePeriods",
			new String[] {Long.class.getName()},
			new String[] {"registrationResourceId"}, false);

		RegistrationPeriodUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		RegistrationPeriodUtil.setPersistence(null);

		entityCache.removeCache(RegistrationPeriodImpl.class.getName());
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

	private static final String _SQL_SELECT_REGISTRATIONPERIOD =
		"SELECT registrationPeriod FROM RegistrationPeriod registrationPeriod";

	private static final String _SQL_SELECT_REGISTRATIONPERIOD_WHERE =
		"SELECT registrationPeriod FROM RegistrationPeriod registrationPeriod WHERE ";

	private static final String _SQL_COUNT_REGISTRATIONPERIOD =
		"SELECT COUNT(registrationPeriod) FROM RegistrationPeriod registrationPeriod";

	private static final String _SQL_COUNT_REGISTRATIONPERIOD_WHERE =
		"SELECT COUNT(registrationPeriod) FROM RegistrationPeriod registrationPeriod WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "registrationPeriod.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RegistrationPeriod exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RegistrationPeriod exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationPeriodPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}