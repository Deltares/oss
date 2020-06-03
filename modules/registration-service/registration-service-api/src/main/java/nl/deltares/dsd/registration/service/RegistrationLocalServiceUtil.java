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

package nl.deltares.dsd.registration.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Registration. This utility wraps
 * <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationLocalService
 * @generated
 */
@ProviderType
public class RegistrationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the registration to the database. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was added
	 */
	public static nl.deltares.dsd.registration.model.Registration
		addRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().addRegistration(registration);
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	public static nl.deltares.dsd.registration.model.Registration
		createRegistration(long registrationId) {

		return getService().createRegistration(registrationId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
			deleteRegistration(long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRegistration(registrationId);
	}

	/**
	 * Deletes the registration from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was removed
	 */
	public static nl.deltares.dsd.registration.model.Registration
		deleteRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().deleteRegistration(registration);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static nl.deltares.dsd.registration.model.Registration
		fetchRegistration(long registrationId) {

		return getService().fetchRegistration(registrationId);
	}

	/**
	 * Returns the registration matching the UUID and group.
	 *
	 * @param uuid the registration's UUID
	 * @param groupId the primary key of the group
	 * @return the matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
		fetchRegistrationByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchRegistrationByUuidAndGroupId(uuid, groupId);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration with the primary key.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
			getRegistration(long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegistration(registrationId);
	}

	/**
	 * Returns the registration matching the UUID and group.
	 *
	 * @param uuid the registration's UUID
	 * @param groupId the primary key of the group
	 * @return the matching registration
	 * @throws PortalException if a matching registration could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
			getRegistrationByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegistrationByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getRegistrations(
			int start, int end) {

		return getService().getRegistrations(start, end);
	}

	/**
	 * Returns all the registrations matching the UUID and company.
	 *
	 * @param uuid the UUID of the registrations
	 * @param companyId the primary key of the company
	 * @return the matching registrations, or an empty list if no matches were found
	 */
	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getRegistrationsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getRegistrationsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of registrations matching the UUID and company.
	 *
	 * @param uuid the UUID of the registrations
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching registrations, or an empty list if no matches were found
	 */
	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getRegistrationsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<nl.deltares.dsd.registration.model.Registration>
						orderByComparator) {

		return getService().getRegistrationsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	public static int getRegistrationsCount() {
		return getService().getRegistrationsCount();
	}

	/**
	 * Updates the registration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was updated
	 */
	public static nl.deltares.dsd.registration.model.Registration
		updateRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().updateRegistration(registration);
	}

	public static void validateRegistration(
			long groupId, long articleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().validateRegistration(groupId, articleId, userId);
	}

	public static RegistrationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RegistrationLocalService, RegistrationLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(RegistrationLocalService.class);

		ServiceTracker<RegistrationLocalService, RegistrationLocalService>
			serviceTracker =
				new ServiceTracker
					<RegistrationLocalService, RegistrationLocalService>(
						bundle.getBundleContext(),
						RegistrationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}