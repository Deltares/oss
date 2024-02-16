/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.dsd.registration.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for Registration. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface RegistrationLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the registration local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link RegistrationLocalServiceUtil} if injection and service tracking are not available.
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
	@Indexable(type = IndexableType.REINDEX)
	public Registration addRegistration(Registration registration);

	public void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, Date startTime, Date endTime,
		String preferences, long registeredByUserId);

	public void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, Date transferDate,
		long registeredByUserId);

	public int countUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourceId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	@Transactional(enabled = false)
	public Registration createRegistration(long registrationId);

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public void deleteAllEventRegistrations(long groupId, long eventResourceId);

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 */
	public void deleteAllRegistrationsAndChildRegistrations(
		long groupId, long resourceId);

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param userId User id
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public void deleteAllUserEventRegistrations(
		long groupId, long userId, long eventResourceId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	@Indexable(type = IndexableType.DELETE)
	public Registration deleteRegistration(long registrationId)
		throws PortalException;

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
	@Indexable(type = IndexableType.DELETE)
	public Registration deleteRegistration(Registration registration);

	/**
	 * Delete user registrations for 'resourceId' and a start date equal to 'startDate'
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 * @param startDate Start date for which to remove registration
	 */
	public void deleteUserRegistration(
			long groupId, long resourceId, long userId, Date startDate)
		throws NoSuchRegistrationException;

	/**
	 * Delete user registrations for 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 */
	public void deleteUserRegistrationAndChildRegistrations(
		long groupId, long resourceId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Registration fetchRegistration(long registrationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getArticleRegistrations(
		long groupId, long articleResourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getArticleRegistrations(
		long groupId, long articleResourceId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getEventRegistrations(
		long groupId, long eventResourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getEventRegistrations(
		long groupId, long eventResourceId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEventRegistrationsCount(long groupId, long eventResourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the registration with the primary key.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Registration getRegistration(long registrationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Date> getRegistrationDates(
		long groupId, long userId, long resourceId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getRegistrations(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getRegistrations(
		long groupId, Date start, Date end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getRegistrations(
		long groupId, long userId, long resourceId);

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRegistrationsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRegistrationsCount(long groupId, long resourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRegistrationsCount(
		long groupId, long resourceId, Date startDate);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRegistrationsCount(
		long groupId, long userId, long resourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRegistrationsCount(
		long groupId, long userId, long resourceId, Date startDate);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getRegistrationsWithOverlappingPeriod(
		long groupId, long userId, Date startTime, Date endTime);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUserEventRegistrations(
		long groupId, long userId, long eventResourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUserEventRegistrationsMadeForOthers(
		long groupId, long registeredByUserId, long eventResourceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUserRegistrations(
		long groupId, long userId, Date start, Date end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUserRegistrations(
		long groupId, long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserRegistrationsCount(long groupId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUserRegistrationsMadeForOthers(
		long groupId, long registeredByUserId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Registration> getUsersRegisteredByOtherUser(
		long groupId, long otherUserId, long registrationResourceId);

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
	@Indexable(type = IndexableType.REINDEX)
	public Registration updateRegistration(Registration registration);

}