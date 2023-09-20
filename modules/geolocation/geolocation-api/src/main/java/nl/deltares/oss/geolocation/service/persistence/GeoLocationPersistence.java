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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.deltares.oss.geolocation.exception.NoSuchGeoLocationException;
import nl.deltares.oss.geolocation.model.GeoLocation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the geo location service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocationUtil
 * @generated
 */
@ProviderType
public interface GeoLocationPersistence extends BasePersistence<GeoLocation> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GeoLocationUtil} to access the geo location persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching geo locations
	 */
	public java.util.List<GeoLocation> findByUuid(String uuid);

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
	public java.util.List<GeoLocation> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

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
	public java.util.List<GeoLocation> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public GeoLocation findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public GeoLocation findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

	/**
	 * Returns the geo locations before and after the current geo location in the ordered set where uuid = &#63;.
	 *
	 * @param locationId the primary key of the current geo location
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	public GeoLocation[] findByUuid_PrevAndNext(
			long locationId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Removes all the geo locations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of geo locations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching geo locations
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching geo locations
	 */
	public java.util.List<GeoLocation> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

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
	public java.util.List<GeoLocation> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public GeoLocation findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the first geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public GeoLocation findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the last geo location in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

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
	public GeoLocation[] findByUuid_C_PrevAndNext(
			long locationId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
				orderByComparator)
		throws NoSuchGeoLocationException;

	/**
	 * Removes all the geo locations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of geo locations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching geo locations
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location
	 * @throws NoSuchGeoLocationException if a matching geo location could not be found
	 */
	public GeoLocation findByCity(long countryId, String cityName)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByCity(long countryId, String cityName);

	/**
	 * Returns the geo location where countryId = &#63; and cityName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public GeoLocation fetchByCity(
		long countryId, String cityName, boolean useFinderCache);

	/**
	 * Removes the geo location where countryId = &#63; and cityName = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the geo location that was removed
	 */
	public GeoLocation removeByCity(long countryId, String cityName)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the number of geo locations where countryId = &#63; and cityName = &#63;.
	 *
	 * @param countryId the country ID
	 * @param cityName the city name
	 * @return the number of matching geo locations
	 */
	public int countByCity(long countryId, String cityName);

	/**
	 * Caches the geo location in the entity cache if it is enabled.
	 *
	 * @param geoLocation the geo location
	 */
	public void cacheResult(GeoLocation geoLocation);

	/**
	 * Caches the geo locations in the entity cache if it is enabled.
	 *
	 * @param geoLocations the geo locations
	 */
	public void cacheResult(java.util.List<GeoLocation> geoLocations);

	/**
	 * Creates a new geo location with the primary key. Does not add the geo location to the database.
	 *
	 * @param locationId the primary key for the new geo location
	 * @return the new geo location
	 */
	public GeoLocation create(long locationId);

	/**
	 * Removes the geo location with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location that was removed
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	public GeoLocation remove(long locationId)
		throws NoSuchGeoLocationException;

	public GeoLocation updateImpl(GeoLocation geoLocation);

	/**
	 * Returns the geo location with the primary key or throws a <code>NoSuchGeoLocationException</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location
	 * @throws NoSuchGeoLocationException if a geo location with the primary key could not be found
	 */
	public GeoLocation findByPrimaryKey(long locationId)
		throws NoSuchGeoLocationException;

	/**
	 * Returns the geo location with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location, or <code>null</code> if a geo location with the primary key could not be found
	 */
	public GeoLocation fetchByPrimaryKey(long locationId);

	/**
	 * Returns all the geo locations.
	 *
	 * @return the geo locations
	 */
	public java.util.List<GeoLocation> findAll();

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
	public java.util.List<GeoLocation> findAll(int start, int end);

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
	public java.util.List<GeoLocation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator);

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
	public java.util.List<GeoLocation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GeoLocation>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the geo locations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of geo locations.
	 *
	 * @return the number of geo locations
	 */
	public int countAll();

}