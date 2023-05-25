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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RegistrationLocalService}.
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationLocalService
 * @generated
 */
public class RegistrationLocalServiceWrapper
	implements RegistrationLocalService,
			   ServiceWrapper<RegistrationLocalService> {

	public RegistrationLocalServiceWrapper() {
		this(null);
	}

	public RegistrationLocalServiceWrapper(
		RegistrationLocalService registrationLocalService) {

		_registrationLocalService = registrationLocalService;
	}

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
	@Override
	public nl.deltares.dsd.registration.model.Registration addRegistration(
		nl.deltares.dsd.registration.model.Registration registration) {

		return _registrationLocalService.addRegistration(registration);
	}

	@Override
	public void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date startTime,
		java.util.Date endTime, String preferences, long registeredByUserId) {

		_registrationLocalService.addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, startTime, endTime, preferences, registeredByUserId);
	}

	@Override
	public void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date transferDate,
		long registeredByUserId) {

		_registrationLocalService.addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, transferDate, registeredByUserId);
	}

	@Override
	public int countUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourceId) {

		return _registrationLocalService.
			countUserEventRegistrationsRegisteredByMe(
				groupId, registeredByUserId, eventResourceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	@Override
	public nl.deltares.dsd.registration.model.Registration createRegistration(
		long registrationId) {

		return _registrationLocalService.createRegistration(registrationId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	@Override
	public void deleteAllEventRegistrations(
		long groupId, long eventResourceId) {

		_registrationLocalService.deleteAllEventRegistrations(
			groupId, eventResourceId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 */
	@Override
	public void deleteAllRegistrationsAndChildRegistrations(
		long groupId, long resourceId) {

		_registrationLocalService.deleteAllRegistrationsAndChildRegistrations(
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
	@Override
	public void deleteAllUserEventRegistrations(
		long groupId, long userId, long eventResourceId) {

		_registrationLocalService.deleteAllUserEventRegistrations(
			groupId, userId, eventResourceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationLocalService.deletePersistedModel(persistedModel);
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
	@Override
	public nl.deltares.dsd.registration.model.Registration deleteRegistration(
			long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationLocalService.deleteRegistration(registrationId);
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
	@Override
	public nl.deltares.dsd.registration.model.Registration deleteRegistration(
		nl.deltares.dsd.registration.model.Registration registration) {

		return _registrationLocalService.deleteRegistration(registration);
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
	@Override
	public void deleteUserRegistration(
			long groupId, long resourceId, long userId,
			java.util.Date startDate)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		_registrationLocalService.deleteUserRegistration(
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
	@Override
	public void deleteUserRegistrationAndChildRegistrations(
		long groupId, long resourceId, long userId) {

		_registrationLocalService.deleteUserRegistrationAndChildRegistrations(
			groupId, resourceId, userId);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _registrationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _registrationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _registrationLocalService.dynamicQuery();
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

		return _registrationLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _registrationLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _registrationLocalService.dynamicQuery(
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

		return _registrationLocalService.dynamicQueryCount(dynamicQuery);
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

		return _registrationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.dsd.registration.model.Registration fetchRegistration(
		long registrationId) {

		return _registrationLocalService.fetchRegistration(registrationId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _registrationLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getArticleRegistrations(long groupId, long articleResourceId) {

		return _registrationLocalService.getArticleRegistrations(
			groupId, articleResourceId);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getEventRegistrations(long groupId, long eventResourceId) {

		return _registrationLocalService.getEventRegistrations(
			groupId, eventResourceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _registrationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _registrationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration with the primary key.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	@Override
	public nl.deltares.dsd.registration.model.Registration getRegistration(
			long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationLocalService.getRegistration(registrationId);
	}

	@Override
	public java.util.List<java.util.Date> getRegistrationDates(
		long groupId, long userId, long resourceId) {

		return _registrationLocalService.getRegistrationDates(
			groupId, userId, resourceId);
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
	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getRegistrations(int start, int end) {

		return _registrationLocalService.getRegistrations(start, end);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getRegistrations(
			long groupId, java.util.Date start, java.util.Date end) {

		return _registrationLocalService.getRegistrations(groupId, start, end);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getRegistrations(long groupId, long userId, long resourceId) {

		return _registrationLocalService.getRegistrations(
			groupId, userId, resourceId);
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	@Override
	public int getRegistrationsCount() {
		return _registrationLocalService.getRegistrationsCount();
	}

	@Override
	public int getRegistrationsCount(long groupId, long resourceId) {
		return _registrationLocalService.getRegistrationsCount(
			groupId, resourceId);
	}

	@Override
	public int getRegistrationsCount(
		long groupId, long resourceId, java.util.Date startDate) {

		return _registrationLocalService.getRegistrationsCount(
			groupId, resourceId, startDate);
	}

	@Override
	public int getRegistrationsCount(
		long groupId, long userId, long resourceId) {

		return _registrationLocalService.getRegistrationsCount(
			groupId, userId, resourceId);
	}

	@Override
	public int getRegistrationsCount(
		long groupId, long userId, long resourceId, java.util.Date startDate) {

		return _registrationLocalService.getRegistrationsCount(
			groupId, userId, resourceId, startDate);
	}

	@Override
	public long[] getRegistrationsWithOverlappingPeriod(
		long groupId, long userId, java.util.Date startTime,
		java.util.Date endTime) {

		return _registrationLocalService.getRegistrationsWithOverlappingPeriod(
			groupId, userId, startTime, endTime);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getUserEventRegistrations(
			long groupId, long userId, long eventResourceId) {

		return _registrationLocalService.getUserEventRegistrations(
			groupId, userId, eventResourceId);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getUserEventRegistrationsMadeForOthers(
			long groupId, long registeredByUserId, long eventResourceId) {

		return _registrationLocalService.getUserEventRegistrationsMadeForOthers(
			groupId, registeredByUserId, eventResourceId);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getUserRegistrations(
			long groupId, long userId, java.util.Date start,
			java.util.Date end) {

		return _registrationLocalService.getUserRegistrations(
			groupId, userId, start, end);
	}

	@Override
	public java.util.List<nl.deltares.dsd.registration.model.Registration>
		getUsersRegisteredByOtherUser(
			long groupId, long otherUserId, long registrationResourceId) {

		return _registrationLocalService.getUsersRegisteredByOtherUser(
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
	@Override
	public nl.deltares.dsd.registration.model.Registration updateRegistration(
		nl.deltares.dsd.registration.model.Registration registration) {

		return _registrationLocalService.updateRegistration(registration);
	}

	@Override
	public RegistrationLocalService getWrappedService() {
		return _registrationLocalService;
	}

	@Override
	public void setWrappedService(
		RegistrationLocalService registrationLocalService) {

		_registrationLocalService = registrationLocalService;
	}

	private RegistrationLocalService _registrationLocalService;

}