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

package nl.deltares.oss.geolocation.service.persistence.impl;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import nl.deltares.oss.geolocation.exception.NoSuchGeoLocationException;
import nl.deltares.oss.geolocation.model.GeoLocation;
import nl.deltares.oss.geolocation.model.GeoLocationTable;
import nl.deltares.oss.geolocation.model.impl.GeoLocationImpl;
import nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl;
import nl.deltares.oss.geolocation.service.persistence.GeoLocationPersistence;
import nl.deltares.oss.geolocation.service.persistence.GeoLocationUtil;
import nl.deltares.oss.geolocation.service.persistence.impl.constants.GeoLocationsPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the geo location service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {GeoLocationPersistence.class, BasePersistence.class})
public class GeoLocationPersistenceImpl
	extends BasePersistenceImpl<GeoLocation> implements GeoLocationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>GeoLocationUtil</code> to access the geo location persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		GeoLocationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the geo locations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @return the range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the geo locations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the geo locations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<GeoLocation> list = null;

		if (useFinderCache) {
			list = (List<GeoLocation>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GeoLocation geoLocation : list) {
					if (!uuid.equals(geoLocation.getUuid())) {
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

			sb.append(_SQL_SELECT_GEOLOCATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(GeoLocationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<GeoLocation>)QueryUtil.list(
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
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	@Override
	public GeoLocation findByUuid_First(
			String uuid, OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByUuid_First(uuid, orderByComparator);

		if (geoLocation != null) {
			return geoLocation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchGeoLocationException(sb.toString());
	}

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByUuid_First(
		String uuid, OrderByComparator<GeoLocation> orderByComparator) {

		List<GeoLocation> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	@Override
	public GeoLocation findByUuid_Last(
			String uuid, OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByUuid_Last(uuid, orderByComparator);

		if (geoLocation != null) {
			return geoLocation;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchGeoLocationException(sb.toString());
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByUuid_Last(
		String uuid, OrderByComparator<GeoLocation> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<GeoLocation> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the geo locations before and after the current geo location in the ordered set where uuid = &#63;.
	 *
	 * @param locationId the primary key of the current geo location
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation[] findByUuid_PrevAndNext(
			long locationId, String uuid,
			OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		uuid = Objects.toString(uuid, "");

		GeoLocation geoLocation = findByPrimaryKey(locationId);

		Session session = null;

		try {
			session = openSession();

			GeoLocation[] array = new GeoLocationImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, geoLocation, uuid, orderByComparator, true);

			array[1] = geoLocation;

			array[2] = getByUuid_PrevAndNext(
				session, geoLocation, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected GeoLocation getByUuid_PrevAndNext(
		Session session, GeoLocation geoLocation, String uuid,
		OrderByComparator<GeoLocation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_GEOLOCATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			sb.append(GeoLocationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(geoLocation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<GeoLocation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the geo locations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (GeoLocation geoLocation :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(geoLocation);
		}
	}

	/**
	 * Returns the number of geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching geo locations
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_GEOLOCATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"geoLocation.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(geoLocation.uuid IS NULL OR geoLocation.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @return the range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching geo locations
	 */
	@Override
	public List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<GeoLocation> list = null;

		if (useFinderCache) {
			list = (List<GeoLocation>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GeoLocation geoLocation : list) {
					if (!uuid.equals(geoLocation.getUuid()) ||
						(companyId != geoLocation.getCompanyId())) {

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

			sb.append(_SQL_SELECT_GEOLOCATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(GeoLocationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<GeoLocation>)QueryUtil.list(
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
	 * Returns the first geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	@Override
	public GeoLocation findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (geoLocation != null) {
			return geoLocation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGeoLocationException(sb.toString());
	}

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<GeoLocation> orderByComparator) {

		List<GeoLocation> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	@Override
	public GeoLocation findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (geoLocation != null) {
			return geoLocation;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGeoLocationException(sb.toString());
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<GeoLocation> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<GeoLocation> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the geo locations before and after the current geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param locationId the primary key of the current geo location
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation[] findByUuid_C_PrevAndNext(
			long locationId, String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws NoSuchGeoLocationException {

		uuid = Objects.toString(uuid, "");

		GeoLocation geoLocation = findByPrimaryKey(locationId);

		Session session = null;

		try {
			session = openSession();

			GeoLocation[] array = new GeoLocationImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, geoLocation, uuid, companyId, orderByComparator, true);

			array[1] = geoLocation;

			array[2] = getByUuid_C_PrevAndNext(
				session, geoLocation, uuid, companyId, orderByComparator,
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

	protected GeoLocation getByUuid_C_PrevAndNext(
		Session session, GeoLocation geoLocation, String uuid, long companyId,
		OrderByComparator<GeoLocation> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_GEOLOCATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(GeoLocationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(geoLocation)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<GeoLocation> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the geo locations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (GeoLocation geoLocation :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(geoLocation);
		}
	}

	/**
	 * Returns the number of geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching geo locations
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_GEOLOCATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"geoLocation.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(geoLocation.uuid IS NULL OR geoLocation.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"geoLocation.companyId = ?";

	private FinderPath _finderPathFetchByCity;
	private FinderPath _finderPathCountByCity;

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	@Override
	public GeoLocation findByCity(long countryId, String cityName)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByCity(countryId, cityName);

		if (geoLocation == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("countryId=");
			sb.append(countryId);

			sb.append(", cityName=");
			sb.append(cityName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGeoLocationException(sb.toString());
		}

		return geoLocation;
	}

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByCity(long countryId, String cityName) {
		return fetchByCity(countryId, cityName, true);
	}

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public GeoLocation fetchByCity(
		long countryId, String cityName, boolean useFinderCache) {

		cityName = Objects.toString(cityName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {countryId, cityName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCity, finderArgs, this);
		}

		if (result instanceof GeoLocation) {
			GeoLocation geoLocation = (GeoLocation)result;

			if ((countryId != geoLocation.getCountryId()) ||
				!Objects.equals(cityName, geoLocation.getCityName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_GEOLOCATION_WHERE);

			sb.append(_FINDER_COLUMN_CITY_COUNTRYID_2);

			boolean bindCityName = false;

			if (cityName.isEmpty()) {
				sb.append(_FINDER_COLUMN_CITY_CITYNAME_3);
			}
			else {
				bindCityName = true;

				sb.append(_FINDER_COLUMN_CITY_CITYNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

				if (bindCityName) {
					queryPos.add(cityName);
				}

				List<GeoLocation> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCity, finderArgs, list);
					}
				}
				else {
					GeoLocation geoLocation = list.get(0);

					result = geoLocation;

					cacheResult(geoLocation);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (GeoLocation)result;
		}
	}

	/**
	 * Removes the geo location where countryId = &#63; and cityName = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the geo location that was removed
	 */
	@Override
	public GeoLocation removeByCity(long countryId, String cityName)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = findByCity(countryId, cityName);

		return remove(geoLocation);
	}

	/**
	 * Returns the number of geo locations where countryId = &#63; and cityName = &#63;.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the number of matching geo locations
	 */
	@Override
	public int countByCity(long countryId, String cityName) {
		cityName = Objects.toString(cityName, "");

		FinderPath finderPath = _finderPathCountByCity;

		Object[] finderArgs = new Object[] {countryId, cityName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_GEOLOCATION_WHERE);

			sb.append(_FINDER_COLUMN_CITY_COUNTRYID_2);

			boolean bindCityName = false;

			if (cityName.isEmpty()) {
				sb.append(_FINDER_COLUMN_CITY_CITYNAME_3);
			}
			else {
				bindCityName = true;

				sb.append(_FINDER_COLUMN_CITY_CITYNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(countryId);

				if (bindCityName) {
					queryPos.add(cityName);
				}

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

	private static final String _FINDER_COLUMN_CITY_COUNTRYID_2 =
		"geoLocation.countryId = ? AND ";

	private static final String _FINDER_COLUMN_CITY_CITYNAME_2 =
		"geoLocation.cityName = ?";

	private static final String _FINDER_COLUMN_CITY_CITYNAME_3 =
		"(geoLocation.cityName IS NULL OR geoLocation.cityName = '')";

	public GeoLocationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(GeoLocation.class);

		setModelImplClass(GeoLocationImpl.class);
		setModelPKClass(long.class);

		setTable(GeoLocationTable.INSTANCE);
	}

	/**
	 * Caches the geo location in the entity cache if it is enabled.
	 *
	 * @param geoLocation the geo location
	 */
	@Override
	public void cacheResult(GeoLocation geoLocation) {
		entityCache.putResult(
			GeoLocationImpl.class, geoLocation.getPrimaryKey(), geoLocation);

		finderCache.putResult(
			_finderPathFetchByCity,
			new Object[] {
				geoLocation.getCountryId(), geoLocation.getCityName()
			},
			geoLocation);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the geo locations in the entity cache if it is enabled.
	 *
	 * @param geoLocations the geo locations
	 */
	@Override
	public void cacheResult(List<GeoLocation> geoLocations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (geoLocations.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (GeoLocation geoLocation : geoLocations) {
			if (entityCache.getResult(
					GeoLocationImpl.class, geoLocation.getPrimaryKey()) ==
						null) {

				cacheResult(geoLocation);
			}
		}
	}

	/**
	 * Clears the cache for all geo locations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GeoLocationImpl.class);

		finderCache.clearCache(GeoLocationImpl.class);
	}

	/**
	 * Clears the cache for the geo location.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(GeoLocation geoLocation) {
		entityCache.removeResult(GeoLocationImpl.class, geoLocation);
	}

	@Override
	public void clearCache(List<GeoLocation> geoLocations) {
		for (GeoLocation geoLocation : geoLocations) {
			entityCache.removeResult(GeoLocationImpl.class, geoLocation);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(GeoLocationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(GeoLocationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		GeoLocationModelImpl geoLocationModelImpl) {

		Object[] args = new Object[] {
			geoLocationModelImpl.getCountryId(),
			geoLocationModelImpl.getCityName()
		};

		finderCache.putResult(_finderPathCountByCity, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCity, args, geoLocationModelImpl);
	}

	/**
	 * Creates a new geo location with the primary key. Does not add the geo location to the database.
	 *
	 * @param locationId the primary key for the new geo location
	 * @return the new geo location
	 */
	@Override
	public GeoLocation create(long locationId) {
		GeoLocation geoLocation = new GeoLocationImpl();

		geoLocation.setNew(true);
		geoLocation.setPrimaryKey(locationId);

		String uuid = _portalUUID.generate();

		geoLocation.setUuid(uuid);

		geoLocation.setCompanyId(CompanyThreadLocal.getCompanyId());

		return geoLocation;
	}

	/**
	 * Removes the geo location with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location that was removed
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation remove(long locationId)
		throws NoSuchGeoLocationException {

		return remove((Serializable)locationId);
	}

	/**
	 * Removes the geo location with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the geo location
	 * @return the geo location that was removed
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation remove(Serializable primaryKey)
		throws NoSuchGeoLocationException {

		Session session = null;

		try {
			session = openSession();

			GeoLocation geoLocation = (GeoLocation)session.get(
				GeoLocationImpl.class, primaryKey);

			if (geoLocation == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGeoLocationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(geoLocation);
		}
		catch (NoSuchGeoLocationException noSuchEntityException) {
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
	protected GeoLocation removeImpl(GeoLocation geoLocation) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(geoLocation)) {
				geoLocation = (GeoLocation)session.get(
					GeoLocationImpl.class, geoLocation.getPrimaryKeyObj());
			}

			if (geoLocation != null) {
				session.delete(geoLocation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (geoLocation != null) {
			clearCache(geoLocation);
		}

		return geoLocation;
	}

	@Override
	public GeoLocation updateImpl(GeoLocation geoLocation) {
		boolean isNew = geoLocation.isNew();

		if (!(geoLocation instanceof GeoLocationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(geoLocation.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(geoLocation);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in geoLocation proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom GeoLocation implementation " +
					geoLocation.getClass());
		}

		GeoLocationModelImpl geoLocationModelImpl =
			(GeoLocationModelImpl)geoLocation;

		if (Validator.isNull(geoLocation.getUuid())) {
			String uuid = _portalUUID.generate();

			geoLocation.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (geoLocation.getCreateDate() == null)) {
			if (serviceContext == null) {
				geoLocation.setCreateDate(date);
			}
			else {
				geoLocation.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!geoLocationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				geoLocation.setModifiedDate(date);
			}
			else {
				geoLocation.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(geoLocation);
			}
			else {
				geoLocation = (GeoLocation)session.merge(geoLocation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			GeoLocationImpl.class, geoLocationModelImpl, false, true);

		cacheUniqueFindersCache(geoLocationModelImpl);

		if (isNew) {
			geoLocation.setNew(false);
		}

		geoLocation.resetOriginalValues();

		return geoLocation;
	}

	/**
	 * Returns the geo location with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the geo location
	 * @return the geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGeoLocationException {

		GeoLocation geoLocation = fetchByPrimaryKey(primaryKey);

		if (geoLocation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGeoLocationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return geoLocation;
	}

	/**
	 * Returns the geo location with the primary key or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation findByPrimaryKey(long locationId)
		throws NoSuchGeoLocationException {

		return findByPrimaryKey((Serializable)locationId);
	}

	/**
	 * Returns the geo location with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location, or <code>null</code> if a geo location with the primary key could not be found
	 */
	@Override
	public GeoLocation fetchByPrimaryKey(long locationId) {
		return fetchByPrimaryKey((Serializable)locationId);
	}

	/**
	 * Returns all the geo locations.
	 *
	 * @return the geo locations
	 */
	@Override
	public List<GeoLocation> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the geo locations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @return the range of geo locations
	 */
	@Override
	public List<GeoLocation> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the geo locations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of geo locations
	 */
	@Override
	public List<GeoLocation> findAll(
		int start, int end, OrderByComparator<GeoLocation> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the geo locations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of geo locations
	 */
	@Override
	public List<GeoLocation> findAll(
		int start, int end, OrderByComparator<GeoLocation> orderByComparator,
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

		List<GeoLocation> list = null;

		if (useFinderCache) {
			list = (List<GeoLocation>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_GEOLOCATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_GEOLOCATION;

				sql = sql.concat(GeoLocationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<GeoLocation>)QueryUtil.list(
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
	 * Removes all the geo locations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (GeoLocation geoLocation : findAll()) {
			remove(geoLocation);
		}
	}

	/**
	 * Returns the number of geo locations.
	 *
	 * @return the number of geo locations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_GEOLOCATION);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "locationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_GEOLOCATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return GeoLocationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the geo location persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathFetchByCity = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCity",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"countryId", "cityName"}, true);

		_finderPathCountByCity = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCity",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"countryId", "cityName"}, false);

		_setGeoLocationUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setGeoLocationUtilPersistence(null);

		entityCache.removeCache(GeoLocationImpl.class.getName());
	}

	private void _setGeoLocationUtilPersistence(
		GeoLocationPersistence geoLocationPersistence) {

		try {
			Field field = GeoLocationUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, geoLocationPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = GeoLocationsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = GeoLocationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = GeoLocationsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_GEOLOCATION =
		"SELECT geoLocation FROM GeoLocation geoLocation";

	private static final String _SQL_SELECT_GEOLOCATION_WHERE =
		"SELECT geoLocation FROM GeoLocation geoLocation WHERE ";

	private static final String _SQL_COUNT_GEOLOCATION =
		"SELECT COUNT(geoLocation) FROM GeoLocation geoLocation";

	private static final String _SQL_COUNT_GEOLOCATION_WHERE =
		"SELECT COUNT(geoLocation) FROM GeoLocation geoLocation WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "geoLocation.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No GeoLocation exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No GeoLocation exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		GeoLocationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUIDUtil _portalUUID;

}