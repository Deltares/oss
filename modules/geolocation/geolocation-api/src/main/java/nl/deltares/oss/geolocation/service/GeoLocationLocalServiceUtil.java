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

package nl.deltares.oss.geolocation.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.deltares.oss.geolocation.model.GeoLocation;

/**
 * Provides the local service utility for GeoLocation. This utility wraps
 * <code>nl.deltares.oss.geolocation.service.impl.GeoLocationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocationLocalService
 * @generated
 */
public class GeoLocationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.oss.geolocation.service.impl.GeoLocationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the geo location to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GeoLocationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param geoLocation the geo location
	 * @return the geo location that was added
	 */
	public static GeoLocation addGeoLocation(GeoLocation geoLocation) {
		return getService().addGeoLocation(geoLocation);
	}

	/**
	 * Creates a new geo location with the primary key. Does not add the geo location to the database.
	 *
	 * @param locationId the primary key for the new geo location
	 * @return the new geo location
	 */
	public static GeoLocation createGeoLocation(long locationId) {
		return getService().createGeoLocation(locationId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the geo location from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GeoLocationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param geoLocation the geo location
	 * @return the geo location that was removed
	 */
	public static GeoLocation deleteGeoLocation(GeoLocation geoLocation) {
		return getService().deleteGeoLocation(geoLocation);
	}

	/**
	 * Deletes the geo location with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GeoLocationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location that was removed
	 * @throws PortalException if a geo location with the primary key could not be found
	 */
	public static GeoLocation deleteGeoLocation(long locationId)
		throws PortalException {

		return getService().deleteGeoLocation(locationId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static GeoLocation fetchByCity(long countryId, String cityName) {
		return getService().fetchByCity(countryId, cityName);
	}

	public static GeoLocation fetchGeoLocation(long locationId) {
		return getService().fetchGeoLocation(locationId);
	}

	/**
	 * Returns the geo location with the matching UUID and company.
	 *
	 * @param uuid the geo location's UUID
	 * @param companyId the primary key of the company
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	public static GeoLocation fetchGeoLocationByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchGeoLocationByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns the geo location with the primary key.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location
	 * @throws PortalException if a geo location with the primary key could not be found
	 */
	public static GeoLocation getGeoLocation(long locationId)
		throws PortalException {

		return getService().getGeoLocation(locationId);
	}

	/**
	 * Returns the geo location with the matching UUID and company.
	 *
	 * @param uuid the geo location's UUID
	 * @param companyId the primary key of the company
	 * @return the matching geo location
	 * @throws PortalException if a matching geo location could not be found
	 */
	public static GeoLocation getGeoLocationByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getGeoLocationByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the geo locations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.geolocation.model.impl.GeoLocationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of geo locations
	 * @param end the upper bound of the range of geo locations (not inclusive)
	 * @return the range of geo locations
	 */
	public static List<GeoLocation> getGeoLocations(int start, int end) {
		return getService().getGeoLocations(start, end);
	}

	/**
	 * Returns the number of geo locations.
	 *
	 * @return the number of geo locations
	 */
	public static int getGeoLocationsCount() {
		return getService().getGeoLocationsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the geo location in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GeoLocationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param geoLocation the geo location
	 * @return the geo location that was updated
	 */
	public static GeoLocation updateGeoLocation(GeoLocation geoLocation) {
		return getService().updateGeoLocation(geoLocation);
	}

	public static GeoLocationLocalService getService() {
		return _service;
	}

	private static volatile GeoLocationLocalService _service;

}