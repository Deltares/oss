/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.dsd.registration.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.deltares.dsd.registration.model.Registration;

/**
 * Provides the local service utility for Registration. This utility wraps
 * <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationLocalService
 * @generated
 */
public class RegistrationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the registration to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registration the registration
	 * @return the registration that was added
	 */
	public static Registration addRegistration(Registration registration) {
		return getService().addRegistration(registration);
	}

	public static void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date startTime,
		java.util.Date endTime, String preferences, long registeredByUserId) {

		getService().addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, startTime, endTime, preferences, registeredByUserId);
	}

	public static void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date transferDate,
		long registeredByUserId) {

		getService().addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, transferDate, registeredByUserId);
	}

	public static int countUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourceId) {

		return getService().countUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourceId);
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
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	public static Registration createRegistration(long registrationId) {
		return getService().createRegistration(registrationId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public static void deleteAllEventRegistrations(
		long groupId, long eventResourceId) {

		getService().deleteAllEventRegistrations(groupId, eventResourceId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 */
	public static void deleteAllRegistrationsAndChildRegistrations(
		long groupId, long resourceId) {

		getService().deleteAllRegistrationsAndChildRegistrations(
			groupId, resourceId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param userId User id
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public static void deleteAllUserEventRegistrations(
		long groupId, long userId, long eventResourceId) {

		getService().deleteAllUserEventRegistrations(
			groupId, userId, eventResourceId);
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
	 * Deletes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static Registration deleteRegistration(long registrationId)
		throws PortalException {

		return getService().deleteRegistration(registrationId);
	}

	/**
	 * Deletes the registration from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registration the registration
	 * @return the registration that was removed
	 */
	public static Registration deleteRegistration(Registration registration) {
		return getService().deleteRegistration(registration);
	}

	/**
	 * Delete user registrations for 'resourceId' and a start date equal to 'startDate'
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 * @param startDate Start date for which to remove registration
	 */
	public static void deleteUserRegistration(
			long groupId, long resourceId, long userId,
			java.util.Date startDate)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		getService().deleteUserRegistration(
			groupId, resourceId, userId, startDate);
	}

	/**
	 * Delete user registrations for 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 */
	public static void deleteUserRegistrationAndChildRegistrations(
		long groupId, long resourceId, long userId) {

		getService().deleteUserRegistrationAndChildRegistrations(
			groupId, resourceId, userId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>.
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

	public static Registration fetchRegistration(long registrationId) {
		return getService().fetchRegistration(registrationId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<Registration> getArticleRegistrations(
		long groupId, long articleResourceId) {

		return getService().getArticleRegistrations(groupId, articleResourceId);
	}

	public static List<Registration> getArticleRegistrations(
		long groupId, long articleResourceId, int start, int end) {

		return getService().getArticleRegistrations(
			groupId, articleResourceId, start, end);
	}

	public static List<Registration> getEventRegistrations(
		long groupId, long eventResourceId) {

		return getService().getEventRegistrations(groupId, eventResourceId);
	}

	public static List<Registration> getEventRegistrations(
		long groupId, long eventResourceId, int start, int end) {

		return getService().getEventRegistrations(
			groupId, eventResourceId, start, end);
	}

	public static int getEventRegistrationsCount(
		long groupId, long eventResourceId) {

		return getService().getEventRegistrationsCount(
			groupId, eventResourceId);
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
	 * Returns the registration with the primary key.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static Registration getRegistration(long registrationId)
		throws PortalException {

		return getService().getRegistration(registrationId);
	}

	public static List<java.util.Date> getRegistrationDates(
		long groupId, long userId, long resourceId) {

		return getService().getRegistrationDates(groupId, userId, resourceId);
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	public static List<Registration> getRegistrations(int start, int end) {
		return getService().getRegistrations(start, end);
	}

	public static List<Registration> getRegistrations(
		long groupId, java.util.Date start, java.util.Date end) {

		return getService().getRegistrations(groupId, start, end);
	}

	public static List<Registration> getRegistrations(
		long groupId, long userId, long resourceId) {

		return getService().getRegistrations(groupId, userId, resourceId);
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	public static int getRegistrationsCount() {
		return getService().getRegistrationsCount();
	}

	public static int getRegistrationsCount(long groupId, long resourceId) {
		return getService().getRegistrationsCount(groupId, resourceId);
	}

	public static int getRegistrationsCount(
		long groupId, long resourceId, java.util.Date startDate) {

		return getService().getRegistrationsCount(
			groupId, resourceId, startDate);
	}

	public static int getRegistrationsCount(
		long groupId, long userId, long resourceId) {

		return getService().getRegistrationsCount(groupId, userId, resourceId);
	}

	public static int getRegistrationsCount(
		long groupId, long userId, long resourceId, java.util.Date startDate) {

		return getService().getRegistrationsCount(
			groupId, userId, resourceId, startDate);
	}

	public static long[] getRegistrationsWithOverlappingPeriod(
		long groupId, long userId, java.util.Date startTime,
		java.util.Date endTime) {

		return getService().getRegistrationsWithOverlappingPeriod(
			groupId, userId, startTime, endTime);
	}

	public static List<Registration> getUserEventRegistrations(
		long groupId, long userId, long eventResourceId) {

		return getService().getUserEventRegistrations(
			groupId, userId, eventResourceId);
	}

	public static List<Registration> getUserEventRegistrationsMadeForOthers(
		long groupId, long registeredByUserId, long eventResourceId) {

		return getService().getUserEventRegistrationsMadeForOthers(
			groupId, registeredByUserId, eventResourceId);
	}

	public static List<Registration> getUserRegistrations(
		long groupId, long userId, java.util.Date start, java.util.Date end) {

		return getService().getUserRegistrations(groupId, userId, start, end);
	}

	public static List<Registration> getUserRegistrations(
		long groupId, long userId, int start, int end) {

		return getService().getUserRegistrations(groupId, userId, start, end);
	}

	public static int getUserRegistrationsCount(long groupId, long userId) {
		return getService().getUserRegistrationsCount(groupId, userId);
	}

	public static List<Registration> getUserRegistrationsMadeForOthers(
		long groupId, long registeredByUserId) {

		return getService().getUserRegistrationsMadeForOthers(
			groupId, registeredByUserId);
	}

	public static List<Registration> getUsersRegisteredByOtherUser(
		long groupId, long otherUserId, long registrationResourceId) {

		return getService().getUsersRegisteredByOtherUser(
			groupId, otherUserId, registrationResourceId);
	}

	/**
	 * Updates the registration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registration the registration
	 * @return the registration that was updated
	 */
	public static Registration updateRegistration(Registration registration) {
		return getService().updateRegistration(registration);
	}

	public static RegistrationLocalService getService() {
		return _service;
	}

	public static void setService(RegistrationLocalService service) {
		_service = service;
	}

	private static volatile RegistrationLocalService _service;

}