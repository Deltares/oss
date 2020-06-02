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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.dsd.registration.model.Registration;

import org.osgi.annotation.versioning.ProviderType;
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
 * @author Brian Wing Shun Chan
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
	 * Returns all the registrations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching registrations
	 */
	public static List<Registration> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the registrations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUuid_First(
			String uuid, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUuid_First(
		String uuid, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUuid_Last(
			String uuid, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUuid_Last(
		String uuid, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where uuid = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUuid_PrevAndNext(
			long registrationId, String uuid,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_PrevAndNext(
			registrationId, uuid, orderByComparator);
	}

	/**
	 * Removes all the registrations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of registrations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching registrations
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the registration where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRegistrationException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUUID_G(String uuid, long groupId)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the registration where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the registration where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	 * Removes the registration where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the registration that was removed
	 */
	public static Registration removeByUUID_G(String uuid, long groupId)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of registrations where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching registrations
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the registrations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the registrations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUuid_C_PrevAndNext(
			long registrationId, String uuid, long companyId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUuid_C_PrevAndNext(
			registrationId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the registrations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of registrations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching registrations
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
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
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the matching registrations
	 */
	public static List<Registration> findByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime) {

		return getPersistence().findByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime, int start,
		int end) {

		return getPersistence().findByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrationsBijPeriod_First(
			long groupId, long userId, Date startTime, Date endTime,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrationsBijPeriod_First(
			groupId, userId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrationsBijPeriod_First(
		long groupId, long userId, Date startTime, Date endTime,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrationsBijPeriod_First(
			groupId, userId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByUserRegistrationsBijPeriod_Last(
			long groupId, long userId, Date startTime, Date endTime,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrationsBijPeriod_Last(
			groupId, userId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByUserRegistrationsBijPeriod_Last(
		long groupId, long userId, Date startTime, Date endTime,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByUserRegistrationsBijPeriod_Last(
			groupId, userId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByUserRegistrationsBijPeriod_PrevAndNext(
			long registrationId, long groupId, long userId, Date startTime,
			Date endTime, OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByUserRegistrationsBijPeriod_PrevAndNext(
			registrationId, groupId, userId, startTime, endTime,
			orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 */
	public static void removeByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime) {

		getPersistence().removeByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the number of matching registrations
	 */
	public static int countByUserRegistrationsBijPeriod(
		long groupId, long userId, Date startTime, Date endTime) {

		return getPersistence().countByUserRegistrationsBijPeriod(
			groupId, userId, startTime, endTime);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long articleId) {

		return getPersistence().findByArticleRegistrations(groupId, articleId);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end) {

		return getPersistence().findByArticleRegistrations(
			groupId, articleId, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByArticleRegistrations(
			groupId, articleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByArticleRegistrations(
		long groupId, long articleId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByArticleRegistrations(
			groupId, articleId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByArticleRegistrations_First(
			long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_First(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByArticleRegistrations_First(
		long groupId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByArticleRegistrations_First(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByArticleRegistrations_Last(
			long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_Last(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByArticleRegistrations_Last(
		long groupId, long articleId,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByArticleRegistrations_Last(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long articleId,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByArticleRegistrations_PrevAndNext(
			registrationId, groupId, articleId, orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	public static void removeByArticleRegistrations(
		long groupId, long articleId) {

		getPersistence().removeByArticleRegistrations(groupId, articleId);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching registrations
	 */
	public static int countByArticleRegistrations(
		long groupId, long articleId) {

		return getPersistence().countByArticleRegistrations(groupId, articleId);
	}

	/**
	 * Returns all the registrations where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the matching registrations
	 */
	public static List<Registration> findByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime) {

		return getPersistence().findByRegistrationsBijPeriod(
			groupId, startTime, endTime);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	public static List<Registration> findByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime, int start, int end) {

		return getPersistence().findByRegistrationsBijPeriod(
			groupId, startTime, endTime, start, end);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().findByRegistrationsBijPeriod(
			groupId, startTime, endTime, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	public static List<Registration> findByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByRegistrationsBijPeriod(
			groupId, startTime, endTime, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByRegistrationsBijPeriod_First(
			long groupId, Date startTime, Date endTime,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrationsBijPeriod_First(
			groupId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByRegistrationsBijPeriod_First(
		long groupId, Date startTime, Date endTime,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByRegistrationsBijPeriod_First(
			groupId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public static Registration findByRegistrationsBijPeriod_Last(
			long groupId, Date startTime, Date endTime,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrationsBijPeriod_Last(
			groupId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public static Registration fetchByRegistrationsBijPeriod_Last(
		long groupId, Date startTime, Date endTime,
		OrderByComparator<Registration> orderByComparator) {

		return getPersistence().fetchByRegistrationsBijPeriod_Last(
			groupId, startTime, endTime, orderByComparator);
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public static Registration[] findByRegistrationsBijPeriod_PrevAndNext(
			long registrationId, long groupId, Date startTime, Date endTime,
			OrderByComparator<Registration> orderByComparator)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		return getPersistence().findByRegistrationsBijPeriod_PrevAndNext(
			registrationId, groupId, startTime, endTime, orderByComparator);
	}

	/**
	 * Removes all the registrations where groupId = &#63; and startTime = &#63; and endTime = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 */
	public static void removeByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime) {

		getPersistence().removeByRegistrationsBijPeriod(
			groupId, startTime, endTime);
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and startTime = &#63; and endTime = &#63;.
	 *
	 * @param groupId the group ID
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the number of matching registrations
	 */
	public static int countByRegistrationsBijPeriod(
		long groupId, Date startTime, Date endTime) {

		return getPersistence().countByRegistrationsBijPeriod(
			groupId, startTime, endTime);
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