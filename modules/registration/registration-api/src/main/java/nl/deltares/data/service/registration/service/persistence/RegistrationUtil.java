/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.data.service.registration.model.Registration;

/**
 * The persistence utility for the registration service. This utility wraps <code>nl.deltares.data.service.registration.service.persistence.impl.RegistrationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPersistence
 * @generated
 */
public class RegistrationUtil {

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
	public static void clearCache(Registration registration) {
		getPersistence().clearCache(registration);
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
	public static Map<Serializable, Registration> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Registration> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Registration> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Registration> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Registration update(Registration registration) {
		return getPersistence().update(registration);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Registration update(
		Registration registration, ServiceContext serviceContext) {

		return getPersistence().update(registration, serviceContext);
	}

	/**
	 * Returns all the registrations where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByRegistrations(
		long registrationResourceId) {

		return getPersistence().findByRegistrations(registrationResourceId);
	}

	/**
	 * Returns a range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end) {

		return getPersistence().findByRegistrations(
			registrationResourceId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByRegistrations(
			registrationResourceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByRegistrations(
		long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRegistrations(
			registrationResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByRegistrations_First(
			long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrations_First(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByRegistrations_First(
		long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByRegistrations_First(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByRegistrations_Last(
			long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrations_Last(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByRegistrations_Last(
		long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByRegistrations_Last(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByRegistrations_PrevAndNext(
			long registrationId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrations_PrevAndNext(
			registrationId, registrationResourceId, orderByComparator);
	}

	/**
	 * Removes all the registrations where registrationResourceId = &#63; from the database.
	 *
	 * @param registrationResourceId the registration resource ID
	 */
	public static void removeByRegistrations(long registrationResourceId) {
		getPersistence().removeByRegistrations(registrationResourceId);
	}

	/**
	 * Returns the number of registrations where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	public static int countByRegistrations(long registrationResourceId) {
		return getPersistence().countByRegistrations(registrationResourceId);
	}

	/**
	 * Returns all the registrations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserRegistrations(long userId) {
		return getPersistence().findByUserRegistrations(userId);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long userId, int start, int end) {

		return getPersistence().findByUserRegistrations(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserRegistrations(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserRegistrations(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrations_First(
			long userId, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_First(
			userId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrations_First(
		long userId, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrations_First(
			userId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrations_Last(
			long userId, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_Last(
			userId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrations_Last(
		long userId, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrations_Last(
			userId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_PrevAndNext(
			registrationId, userId, orderByComparator);
	}

	/**
	 * Removes all the registrations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserRegistrations(long userId) {
		getPersistence().removeByUserRegistrations(userId);
	}

	/**
	 * Returns the number of registrations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	public static int countByUserRegistrations(long userId) {
		return getPersistence().countByUserRegistrations(userId);
	}

	/**
	 * Returns all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		return getPersistence().findByUserResourceRegistrations(
			userId, registrationResourceId);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end) {

		return getPersistence().findByUserResourceRegistrations(
			userId, registrationResourceId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserResourceRegistrations(
			userId, registrationResourceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserResourceRegistrations(
		long userId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserResourceRegistrations(
			userId, registrationResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserResourceRegistrations_First(
			long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserResourceRegistrations_First(
			userId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserResourceRegistrations_First(
		long userId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserResourceRegistrations_First(
			userId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserResourceRegistrations_Last(
			long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserResourceRegistrations_Last(
			userId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserResourceRegistrations_Last(
		long userId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserResourceRegistrations_Last(
			userId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserResourceRegistrations_PrevAndNext(
			long registrationId, long userId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserResourceRegistrations_PrevAndNext(
			registrationId, userId, registrationResourceId, orderByComparator);
	}

	/**
	 * Removes all the registrations where userId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 */
	public static void removeByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		getPersistence().removeByUserResourceRegistrations(
			userId, registrationResourceId);
	}

	/**
	 * Returns the number of registrations where userId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param userId the user ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	public static int countByUserResourceRegistrations(
		long userId, long registrationResourceId) {

		return getPersistence().countByUserResourceRegistrations(
			userId, registrationResourceId);
	}

	/**
	 * Returns all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserGroupRegistrations(
		long userId, long groupId) {

		return getPersistence().findByUserGroupRegistrations(userId, groupId);
	}

	/**
	 * Returns a range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end) {

		return getPersistence().findByUserGroupRegistrations(
			userId, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserGroupRegistrations(
			userId, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserGroupRegistrations(
		long userId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserGroupRegistrations(
			userId, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserGroupRegistrations_First(
			long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserGroupRegistrations_First(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserGroupRegistrations_First(
		long userId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserGroupRegistrations_First(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserGroupRegistrations_Last(
			long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserGroupRegistrations_Last(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserGroupRegistrations_Last(
		long userId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserGroupRegistrations_Last(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserGroupRegistrations_PrevAndNext(
			long registrationId, long userId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserGroupRegistrations_PrevAndNext(
			registrationId, userId, groupId, orderByComparator);
	}

	/**
	 * Removes all the registrations where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 */
	public static void removeByUserGroupRegistrations(
		long userId, long groupId) {

		getPersistence().removeByUserGroupRegistrations(userId, groupId);
	}

	/**
	 * Returns the number of registrations where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching registrations
	 */
	public static int countByUserGroupRegistrations(long userId, long groupId) {
		return getPersistence().countByUserGroupRegistrations(userId, groupId);
	}

	/**
	 * Returns all the registrations where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByAuthorRegistrations(long authorId) {
		return getPersistence().findByAuthorRegistrations(authorId);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end) {

		return getPersistence().findByAuthorRegistrations(authorId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByAuthorRegistrations(
			authorId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorRegistrations(
		long authorId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAuthorRegistrations(
			authorId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorRegistrations_First(
			long authorId, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorRegistrations_First(
			authorId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorRegistrations_First(
		long authorId, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorRegistrations_First(
			authorId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorRegistrations_Last(
			long authorId, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorRegistrations_Last(
			authorId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorRegistrations_Last(
		long authorId, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorRegistrations_Last(
			authorId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByAuthorRegistrations_PrevAndNext(
			long registrationId, long authorId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorRegistrations_PrevAndNext(
			registrationId, authorId, orderByComparator);
	}

	/**
	 * Removes all the registrations where authorId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 */
	public static void removeByAuthorRegistrations(long authorId) {
		getPersistence().removeByAuthorRegistrations(authorId);
	}

	/**
	 * Returns the number of registrations where authorId = &#63;.
	 *
	 * @param authorId the author ID
	 * @return the number of matching registrations
	 */
	public static int countByAuthorRegistrations(long authorId) {
		return getPersistence().countByAuthorRegistrations(authorId);
	}

	/**
	 * Returns all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		return getPersistence().findByAuthorResourceRegistrations(
			authorId, registrationResourceId);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end) {

		return getPersistence().findByAuthorResourceRegistrations(
			authorId, registrationResourceId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByAuthorResourceRegistrations(
			authorId, registrationResourceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorResourceRegistrations(
		long authorId, long registrationResourceId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAuthorResourceRegistrations(
			authorId, registrationResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorResourceRegistrations_First(
			long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorResourceRegistrations_First(
			authorId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorResourceRegistrations_First(
		long authorId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorResourceRegistrations_First(
			authorId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorResourceRegistrations_Last(
			long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorResourceRegistrations_Last(
			authorId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorResourceRegistrations_Last(
		long authorId, long registrationResourceId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorResourceRegistrations_Last(
			authorId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByAuthorResourceRegistrations_PrevAndNext(
			long registrationId, long authorId, long registrationResourceId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorResourceRegistrations_PrevAndNext(
			registrationId, authorId, registrationResourceId,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where authorId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 */
	public static void removeByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		getPersistence().removeByAuthorResourceRegistrations(
			authorId, registrationResourceId);
	}

	/**
	 * Returns the number of registrations where authorId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registrations
	 */
	public static int countByAuthorResourceRegistrations(
		long authorId, long registrationResourceId) {

		return getPersistence().countByAuthorResourceRegistrations(
			authorId, registrationResourceId);
	}

	/**
	 * Returns all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId) {

		return getPersistence().findByAuthorGroupRegistrations(
			authorId, groupId);
	}

	/**
	 * Returns a range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end) {

		return getPersistence().findByAuthorGroupRegistrations(
			authorId, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByAuthorGroupRegistrations(
			authorId, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByAuthorGroupRegistrations(
		long authorId, long groupId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAuthorGroupRegistrations(
			authorId, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorGroupRegistrations_First(
			long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorGroupRegistrations_First(
			authorId, groupId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorGroupRegistrations_First(
		long authorId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorGroupRegistrations_First(
			authorId, groupId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByAuthorGroupRegistrations_Last(
			long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorGroupRegistrations_Last(
			authorId, groupId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByAuthorGroupRegistrations_Last(
		long authorId, long groupId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByAuthorGroupRegistrations_Last(
			authorId, groupId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where authorId = &#63; and groupId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByAuthorGroupRegistrations_PrevAndNext(
			long registrationId, long authorId, long groupId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByAuthorGroupRegistrations_PrevAndNext(
			registrationId, authorId, groupId, orderByComparator);
	}

	/**
	 * Removes all the registrations where authorId = &#63; and groupId = &#63; from the database.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 */
	public static void removeByAuthorGroupRegistrations(
		long authorId, long groupId) {

		getPersistence().removeByAuthorGroupRegistrations(authorId, groupId);
	}

	/**
	 * Returns the number of registrations where authorId = &#63; and groupId = &#63;.
	 *
	 * @param authorId the author ID
	 * @param groupId the group ID
	 * @return the number of matching registrations
	 */
	public static int countByAuthorGroupRegistrations(
		long authorId, long groupId) {

		return getPersistence().countByAuthorGroupRegistrations(
			authorId, groupId);
	}

	/**
	 * Caches the registration in the entity cache if it is enabled.
	 *
	 * @param registration the registration
	 */
	public static void cacheResult(Registration registration) {
		getPersistence().cacheResult(registration);
	}

	/**
	 * Caches the registrations in the entity cache if it is enabled.
	 *
	 * @param registrations the registrations
	 */
	public static void cacheResult(List<Registration> registrations) {
		getPersistence().cacheResult(registrations);
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	public static Registration create(long registrationId) {
		return getPersistence().create(registrationId);
	}

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration remove(long registrationId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().remove(registrationId);
	}

	public static Registration updateImpl(Registration registration) {
		return getPersistence().updateImpl(registration);
	}

	/**
	 * Returns the registration with the primary key or throws a <code>NoSuchRegistrationException</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration findByPrimaryKey(long registrationId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByPrimaryKey(registrationId);
	}

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	public static Registration fetchByPrimaryKey(long registrationId) {
		return getPersistence().fetchByPrimaryKey(registrationId);
	}

	/**
	 * Returns all the registrations.
	 *
	 * @return the registrations
	 */
	public static List<Registration> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	public static List<Registration> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registrations
	 */
	public static List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registrations
	 */
	public static List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the registrations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegistrationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(RegistrationPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile RegistrationPersistence _persistence;

}