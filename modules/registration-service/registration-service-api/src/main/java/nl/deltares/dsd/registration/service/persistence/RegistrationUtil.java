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

package nl.deltares.dsd.registration.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.dsd.registration.model.Registration;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the registration service. This utility wraps <code>nl.deltares.dsd.registration.service.persistence.impl.RegistrationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationPersistence
 * @generated
 */
@ProviderType
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
	 * Returns all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		return getPersistence().findByEventRegistrations(
			groupId, eventResourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end) {

		return getPersistence().findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByEventRegistrations_First(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByEventRegistrations_First(
			groupId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByEventRegistrations_First(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByEventRegistrations_First(
			groupId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByEventRegistrations_Last(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByEventRegistrations_Last(
			groupId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByEventRegistrations_Last(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByEventRegistrations_Last(
			groupId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByEventRegistrations_PrevAndNext(
			registrationId, groupId, eventResourcePrimaryKey,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	public static void removeByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		getPersistence().removeByEventRegistrations(
			groupId, eventResourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		return getPersistence().countByEventRegistrations(
			groupId, eventResourcePrimaryKey);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		return getPersistence().findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end) {

		return getPersistence().findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserEventRegistrations_First(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserEventRegistrations_First(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserEventRegistrations_First(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserEventRegistrations_First(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserEventRegistrations_Last(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserEventRegistrations_Last(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserEventRegistrations_Last(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserEventRegistrations_Last(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserEventRegistrations_PrevAndNext(
			registrationId, groupId, userId, eventResourcePrimaryKey,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	public static void removeByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		getPersistence().removeByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		return getPersistence().countByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long groupId, long userId) {

		return getPersistence().findByUserRegistrations(groupId, userId);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end) {

		return getPersistence().findByUserRegistrations(
			groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserRegistrations(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserRegistrations(
			groupId, userId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrations_First(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrations_First(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrations_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrations_Last(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrations_Last(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrations_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrations_PrevAndNext(
			registrationId, groupId, userId, orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public static void removeByUserRegistrations(long groupId, long userId) {
		getPersistence().removeByUserRegistrations(groupId, userId);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	public static int countByUserRegistrations(long groupId, long userId) {
		return getPersistence().countByUserRegistrations(groupId, userId);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		return getPersistence().findByArticleRegistrations(
			groupId, resourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end) {

		return getPersistence().findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByArticleRegistrations_First(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_First(
			groupId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByArticleRegistrations_First(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByArticleRegistrations_First(
			groupId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByArticleRegistrations_Last(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_Last(
			groupId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByArticleRegistrations_Last(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByArticleRegistrations_Last(
			groupId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_PrevAndNext(
			registrationId, groupId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	public static void removeByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		getPersistence().removeByArticleRegistrations(
			groupId, resourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		return getPersistence().countByArticleRegistrations(
			groupId, resourcePrimaryKey);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		return getPersistence().findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start,
		int end) {

		return getPersistence().findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserArticleRegistrations_First(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserArticleRegistrations_First(
			groupId, userId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserArticleRegistrations_First(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserArticleRegistrations_First(
			groupId, userId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserArticleRegistrations_Last(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserArticleRegistrations_Last(
			groupId, userId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserArticleRegistrations_Last(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserArticleRegistrations_Last(
			groupId, userId, resourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserArticleRegistrations_PrevAndNext(
			registrationId, groupId, userId, resourcePrimaryKey,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	public static void removeByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		getPersistence().removeByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		return getPersistence().countByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		return getPersistence().findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end) {

		return getPersistence().findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserChildArticleRegistrations_First(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserChildArticleRegistrations_First(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserChildArticleRegistrations_First(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserChildArticleRegistrations_First(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserChildArticleRegistrations_Last(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserChildArticleRegistrations_Last(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserChildArticleRegistrations_Last(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserChildArticleRegistrations_Last(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[]
			findByUserChildArticleRegistrations_PrevAndNext(
				long registrationId, long groupId, long userId,
				long parentResourcePrimaryKey,
				OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserChildArticleRegistrations_PrevAndNext(
			registrationId, groupId, userId, parentResourcePrimaryKey,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	public static void removeByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		getPersistence().removeByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		return getPersistence().countByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	public static List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		return getPersistence().findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end) {

		return getPersistence().findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByChildArticleRegistrations_First(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByChildArticleRegistrations_First(
			groupId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByChildArticleRegistrations_First(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByChildArticleRegistrations_First(
			groupId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByChildArticleRegistrations_Last(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByChildArticleRegistrations_Last(
			groupId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByChildArticleRegistrations_Last(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByChildArticleRegistrations_Last(
			groupId, parentResourcePrimaryKey, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByChildArticleRegistrations_PrevAndNext(
			registrationId, groupId, parentResourcePrimaryKey,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	public static void removeByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		getPersistence().removeByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	public static int countByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		return getPersistence().countByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey);
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
		throws nl.deltares.dsd.registration.exception.
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
		throws nl.deltares.dsd.registration.exception.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of registrations
	 */
	public static List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
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
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RegistrationPersistence, RegistrationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(RegistrationPersistence.class);

		ServiceTracker<RegistrationPersistence, RegistrationPersistence>
			serviceTracker =
				new ServiceTracker
					<RegistrationPersistence, RegistrationPersistence>(
						bundle.getBundleContext(),
						RegistrationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}