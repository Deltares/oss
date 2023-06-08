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

package nl.deltares.oss.geolocation.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.oss.geolocation.model.GeoLocation;

/**
 * The persistence utility for the geo location service. This utility wraps <code>nl.deltares.oss.geolocation.service.persistence.impl.GeoLocationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocationPersistence
 * @generated
 */
public class GeoLocationUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(GeoLocation geoLocation) {
		getPersistence().clearCache(geoLocation);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, GeoLocation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<GeoLocation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<GeoLocation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<GeoLocation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static GeoLocation update(GeoLocation geoLocation) {
		return getPersistence().update(geoLocation);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static GeoLocation update(
		GeoLocation geoLocation, ServiceContext serviceContext) {

		return getPersistence().update(geoLocation, serviceContext);
	}

	/**
	 * Returns all the geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching geo locations
	 */
	public static List<GeoLocation> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<GeoLocation> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public static GeoLocation findByUuid_First(
			String uuid, OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByUuid_First(
		String uuid, OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public static GeoLocation findByUuid_Last(
			String uuid, OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByUuid_Last(
		String uuid, OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static GeoLocation[] findByUuid_PrevAndNext(
			long locationId, String uuid,
			OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_PrevAndNext(
			locationId, uuid, orderByComparator);
	}

	/**
	 * Removes all the geo locations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching geo locations
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching geo locations
	 */
	public static List<GeoLocation> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<GeoLocation> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static GeoLocation findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static GeoLocation findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static GeoLocation[] findByUuid_C_PrevAndNext(
			long locationId, String uuid, long companyId,
			OrderByComparator<GeoLocation> orderByComparator)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByUuid_C_PrevAndNext(
			locationId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the geo locations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching geo locations
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public static GeoLocation findByCity(long countryId, String cityName)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByCity(countryId, cityName);
	}

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByCity(long countryId, String cityName) {
		return getPersistence().fetchByCity(countryId, cityName);
	}

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchByCity(
		long countryId, String cityName, boolean useFinderCache) {

		return getPersistence().fetchByCity(
			countryId, cityName, useFinderCache);
	}

	/**
	 * Removes the geo location where countryId = &#63; and cityName = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the geo location that was removed
	 */
	public static GeoLocation removeByCity(long countryId, String cityName)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().removeByCity(countryId, cityName);
	}

	/**
	 * Returns the number of geo locations where countryId = &#63; and cityName = &#63;.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the number of matching geo locations
	 */
	public static int countByCity(long countryId, String cityName) {
		return getPersistence().countByCity(countryId, cityName);
	}

	/**
	 * Caches the geo location in the entity cache if it is enabled.
	 *
	 * @param geoLocation the geo location
	 */
	public static void cacheResult(GeoLocation geoLocation) {
		getPersistence().cacheResult(geoLocation);
	}

	/**
	 * Caches the geo locations in the entity cache if it is enabled.
	 *
	 * @param geoLocations the geo locations
	 */
	public static void cacheResult(List<GeoLocation> geoLocations) {
		getPersistence().cacheResult(geoLocations);
	}

	/**
	 * Creates a new geo location with the primary key. Does not add the geo location to the database.
	 *
	 * @param locationId the primary key for the new geo location
	 * @return the new geo location
	 */
	public static GeoLocation create(long locationId) {
		return getPersistence().create(locationId);
	}

	/**
	 * Removes the geo location with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location that was removed
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	public static GeoLocation remove(long locationId)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().remove(locationId);
	}

	public static GeoLocation updateImpl(GeoLocation geoLocation) {
		return getPersistence().updateImpl(geoLocation);
	}

	/**
	 * Returns the geo location with the primary key or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	public static GeoLocation findByPrimaryKey(long locationId)
		throws nl.deltares.oss.geolocation.exception.
			NoSuchGeoLocationException {

		return getPersistence().findByPrimaryKey(locationId);
	}

	/**
	 * Returns the geo location with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location, or <code>null</code> if a geo location with the primary key could not be found
	 */
	public static GeoLocation fetchByPrimaryKey(long locationId) {
		return getPersistence().fetchByPrimaryKey(locationId);
	}

	/**
	 * Returns all the geo locations.
	 *
	 * @return the geo locations
	 */
	public static List<GeoLocation> findAll() {
		return getPersistence().findAll();
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
	public static List<GeoLocation> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<GeoLocation> findAll(
		int start, int end, OrderByComparator<GeoLocation> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<GeoLocation> findAll(
		int start, int end, OrderByComparator<GeoLocation> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the geo locations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of geo locations.
	 *
	 * @return the number of geo locations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static GeoLocationPersistence getPersistence() {
		return _persistence;
	}

	private static volatile GeoLocationPersistence _persistence;

}