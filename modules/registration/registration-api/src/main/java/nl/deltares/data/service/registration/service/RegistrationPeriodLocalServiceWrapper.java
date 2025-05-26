/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link RegistrationPeriodLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriodLocalService
 * @generated
 */
public class RegistrationPeriodLocalServiceWrapper
	implements RegistrationPeriodLocalService,
			   ServiceWrapper<RegistrationPeriodLocalService> {

	public RegistrationPeriodLocalServiceWrapper() {
		this(null);
	}

	public RegistrationPeriodLocalServiceWrapper(
		RegistrationPeriodLocalService registrationPeriodLocalService) {

		_registrationPeriodLocalService = registrationPeriodLocalService;
	}

	/**
	 * Adds the registration period to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationPeriodLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationPeriod the registration period
	 * @return the registration period that was added
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
		addRegistrationPeriod(
			nl.deltares.data.service.registration.model.RegistrationPeriod
				registrationPeriod) {

		return _registrationPeriodLocalService.addRegistrationPeriod(
			registrationPeriod);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationPeriodLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new registration period with the primary key. Does not add the registration period to the database.
	 *
	 * @param registrationPeriodId the primary key for the new registration period
	 * @return the new registration period
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
		createRegistrationPeriod(long registrationPeriodId) {

		return _registrationPeriodLocalService.createRegistrationPeriod(
			registrationPeriodId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationPeriodLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the registration period with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationPeriodLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period that was removed
	 * @throws PortalException if a registration period with the primary key could not be found
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
			deleteRegistrationPeriod(long registrationPeriodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationPeriodLocalService.deleteRegistrationPeriod(
			registrationPeriodId);
	}

	/**
	 * Deletes the registration period from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationPeriodLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationPeriod the registration period
	 * @return the registration period that was removed
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
		deleteRegistrationPeriod(
			nl.deltares.data.service.registration.model.RegistrationPeriod
				registrationPeriod) {

		return _registrationPeriodLocalService.deleteRegistrationPeriod(
			registrationPeriod);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _registrationPeriodLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _registrationPeriodLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _registrationPeriodLocalService.dynamicQuery();
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

		return _registrationPeriodLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl</code>.
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

		return _registrationPeriodLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl</code>.
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

		return _registrationPeriodLocalService.dynamicQuery(
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

		return _registrationPeriodLocalService.dynamicQueryCount(dynamicQuery);
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

		return _registrationPeriodLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
		fetchRegistrationPeriod(long registrationPeriodId) {

		return _registrationPeriodLocalService.fetchRegistrationPeriod(
			registrationPeriodId);
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationPeriod>
			findByResource(long resourceId) {

		return _registrationPeriodLocalService.findByResource(resourceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _registrationPeriodLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _registrationPeriodLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _registrationPeriodLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationPeriod>
			getOverlappingPeriods(
				java.util.Date startTime, java.util.Date endTime) {

		return _registrationPeriodLocalService.getOverlappingPeriods(
			startTime, endTime);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationPeriodLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration period with the primary key.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period
	 * @throws PortalException if a registration period with the primary key could not be found
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
			getRegistrationPeriod(long registrationPeriodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationPeriodLocalService.getRegistrationPeriod(
			registrationPeriodId);
	}

	/**
	 * Returns a range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @return the range of registration periods
	 */
	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationPeriod>
			getRegistrationPeriods(int start, int end) {

		return _registrationPeriodLocalService.getRegistrationPeriods(
			start, end);
	}

	/**
	 * Returns the number of registration periods.
	 *
	 * @return the number of registration periods
	 */
	@Override
	public int getRegistrationPeriodsCount() {
		return _registrationPeriodLocalService.getRegistrationPeriodsCount();
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationPeriod>
			getWithinPeriod(java.util.Date startTime, java.util.Date endTime) {

		return _registrationPeriodLocalService.getWithinPeriod(
			startTime, endTime);
	}

	@Override
	public void removeByResource(long resourceId) {
		_registrationPeriodLocalService.removeByResource(resourceId);
	}

	/**
	 * Updates the registration period in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationPeriodLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationPeriod the registration period
	 * @return the registration period that was updated
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationPeriod
		updateRegistrationPeriod(
			nl.deltares.data.service.registration.model.RegistrationPeriod
				registrationPeriod) {

		return _registrationPeriodLocalService.updateRegistrationPeriod(
			registrationPeriod);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _registrationPeriodLocalService.getBasePersistence();
	}

	@Override
	public RegistrationPeriodLocalService getWrappedService() {
		return _registrationPeriodLocalService;
	}

	@Override
	public void setWrappedService(
		RegistrationPeriodLocalService registrationPeriodLocalService) {

		_registrationPeriodLocalService = registrationPeriodLocalService;
	}

	private RegistrationPeriodLocalService _registrationPeriodLocalService;

}