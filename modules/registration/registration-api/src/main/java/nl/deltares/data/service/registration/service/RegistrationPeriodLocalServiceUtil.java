/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.deltares.data.service.registration.model.RegistrationPeriod;

/**
 * Provides the local service utility for RegistrationPeriod. This utility wraps
 * <code>nl.deltares.data.service.registration.service.impl.RegistrationPeriodLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriodLocalService
 * @generated
 */
public class RegistrationPeriodLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.data.service.registration.service.impl.RegistrationPeriodLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static RegistrationPeriod addRegistrationPeriod(
		RegistrationPeriod registrationPeriod) {

		return getService().addRegistrationPeriod(registrationPeriod);
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
	 * Creates a new registration period with the primary key. Does not add the registration period to the database.
	 *
	 * @param registrationPeriodId the primary key for the new registration period
	 * @return the new registration period
	 */
	public static RegistrationPeriod createRegistrationPeriod(
		long registrationPeriodId) {

		return getService().createRegistrationPeriod(registrationPeriodId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static RegistrationPeriod deleteRegistrationPeriod(
			long registrationPeriodId)
		throws PortalException {

		return getService().deleteRegistrationPeriod(registrationPeriodId);
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
	public static RegistrationPeriod deleteRegistrationPeriod(
		RegistrationPeriod registrationPeriod) {

		return getService().deleteRegistrationPeriod(registrationPeriod);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationPeriodModelImpl</code>.
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

	public static RegistrationPeriod fetchRegistrationPeriod(
		long registrationPeriodId) {

		return getService().fetchRegistrationPeriod(registrationPeriodId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	public static List<RegistrationPeriod> getOverlappingPeriods(
		java.util.Date startTime, java.util.Date endTime) {

		return getService().getOverlappingPeriods(startTime, endTime);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration period with the primary key.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period
	 * @throws PortalException if a registration period with the primary key could not be found
	 */
	public static RegistrationPeriod getRegistrationPeriod(
			long registrationPeriodId)
		throws PortalException {

		return getService().getRegistrationPeriod(registrationPeriodId);
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
	public static List<RegistrationPeriod> getRegistrationPeriods(
		int start, int end) {

		return getService().getRegistrationPeriods(start, end);
	}

	/**
	 * Returns the number of registration periods.
	 *
	 * @return the number of registration periods
	 */
	public static int getRegistrationPeriodsCount() {
		return getService().getRegistrationPeriodsCount();
	}

	public static List<RegistrationPeriod> getWithinPeriod(
		java.util.Date startTime, java.util.Date endTime) {

		return getService().getWithinPeriod(startTime, endTime);
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
	public static RegistrationPeriod updateRegistrationPeriod(
		RegistrationPeriod registrationPeriod) {

		return getService().updateRegistrationPeriod(registrationPeriod);
	}

	public static RegistrationPeriodLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<RegistrationPeriodLocalService>
		_serviceSnapshot = new Snapshot<>(
			RegistrationPeriodLocalServiceUtil.class,
			RegistrationPeriodLocalService.class);

}