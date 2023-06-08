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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GeoLocationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GeoLocationLocalService
 * @generated
 */
public class GeoLocationLocalServiceWrapper
	implements GeoLocationLocalService,
			   ServiceWrapper<GeoLocationLocalService> {

	public GeoLocationLocalServiceWrapper() {
		this(null);
	}

	public GeoLocationLocalServiceWrapper(
		GeoLocationLocalService geoLocationLocalService) {

		_geoLocationLocalService = geoLocationLocalService;
	}

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
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation addGeoLocation(
		nl.deltares.oss.geolocation.model.GeoLocation geoLocation) {

		return _geoLocationLocalService.addGeoLocation(geoLocation);
	}

	/**
	 * Creates a new geo location with the primary key. Does not add the geo location to the database.
	 *
	 * @param locationId the primary key for the new geo location
	 * @return the new geo location
	 */
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation createGeoLocation(
		long locationId) {

		return _geoLocationLocalService.createGeoLocation(locationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation deleteGeoLocation(
		nl.deltares.oss.geolocation.model.GeoLocation geoLocation) {

		return _geoLocationLocalService.deleteGeoLocation(geoLocation);
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
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation deleteGeoLocation(
			long locationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.deleteGeoLocation(locationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _geoLocationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _geoLocationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _geoLocationLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _geoLocationLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _geoLocationLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _geoLocationLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _geoLocationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _geoLocationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation fetchByCity(
		long countryId, String cityName) {

		return _geoLocationLocalService.fetchByCity(countryId, cityName);
	}

	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation fetchGeoLocation(
		long locationId) {

		return _geoLocationLocalService.fetchGeoLocation(locationId);
	}

	/**
	 * Returns the geo location with the matching UUID and company.
	 *
	 * @param uuid the geo location's UUID
	 * @param companyId the primary key of the company
	 * @return the matching geo location, or <code>null</code> if a matching geo location could not be found
	 */
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation
		fetchGeoLocationByUuidAndCompanyId(String uuid, long companyId) {

		return _geoLocationLocalService.fetchGeoLocationByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _geoLocationLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _geoLocationLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the geo location with the primary key.
	 *
	 * @param locationId the primary key of the geo location
	 * @return the geo location
	 * @throws PortalException if a geo location with the primary key could not be found
	 */
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation getGeoLocation(
			long locationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.getGeoLocation(locationId);
	}

	/**
	 * Returns the geo location with the matching UUID and company.
	 *
	 * @param uuid the geo location's UUID
	 * @param companyId the primary key of the company
	 * @return the matching geo location
	 * @throws PortalException if a matching geo location could not be found
	 */
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation
			getGeoLocationByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.getGeoLocationByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<nl.deltares.oss.geolocation.model.GeoLocation>
		getGeoLocations(int start, int end) {

		return _geoLocationLocalService.getGeoLocations(start, end);
	}

	/**
	 * Returns the number of geo locations.
	 *
	 * @return the number of geo locations
	 */
	@Override
	public int getGeoLocationsCount() {
		return _geoLocationLocalService.getGeoLocationsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _geoLocationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _geoLocationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _geoLocationLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public nl.deltares.oss.geolocation.model.GeoLocation updateGeoLocation(
		nl.deltares.oss.geolocation.model.GeoLocation geoLocation) {

		return _geoLocationLocalService.updateGeoLocation(geoLocation);
	}

	@Override
	public GeoLocationLocalService getWrappedService() {
		return _geoLocationLocalService;
	}

	@Override
	public void setWrappedService(
		GeoLocationLocalService geoLocationLocalService) {

		_geoLocationLocalService = geoLocationLocalService;
	}

	private GeoLocationLocalService _geoLocationLocalService;

}