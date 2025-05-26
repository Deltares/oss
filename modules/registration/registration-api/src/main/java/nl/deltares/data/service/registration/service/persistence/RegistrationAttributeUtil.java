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

import nl.deltares.data.service.registration.model.RegistrationAttribute;

/**
 * The persistence utility for the registration attribute service. This utility wraps <code>nl.deltares.data.service.registration.service.persistence.impl.RegistrationAttributePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttributePersistence
 * @generated
 */
public class RegistrationAttributeUtil {

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
	public static void clearCache(RegistrationAttribute registrationAttribute) {
		getPersistence().clearCache(registrationAttribute);
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
	public static Map<Serializable, RegistrationAttribute> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RegistrationAttribute> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RegistrationAttribute> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RegistrationAttribute> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RegistrationAttribute update(
		RegistrationAttribute registrationAttribute) {

		return getPersistence().update(registrationAttribute);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RegistrationAttribute update(
		RegistrationAttribute registrationAttribute,
		ServiceContext serviceContext) {

		return getPersistence().update(registrationAttribute, serviceContext);
	}

	/**
	 * Returns all the registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the matching registration attributes
	 */
	public static List<RegistrationAttribute> findByRegistration(
		long registrationId) {

		return getPersistence().findByRegistration(registrationId);
	}

	/**
	 * Returns a range of all the registration attributes where registrationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param registrationId the registration ID
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @return the range of matching registration attributes
	 */
	public static List<RegistrationAttribute> findByRegistration(
		long registrationId, int start, int end) {

		return getPersistence().findByRegistration(registrationId, start, end);
	}

	/**
	 * Returns an ordered range of all the registration attributes where registrationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param registrationId the registration ID
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration attributes
	 */
	public static List<RegistrationAttribute> findByRegistration(
		long registrationId, int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return getPersistence().findByRegistration(
			registrationId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registration attributes where registrationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param registrationId the registration ID
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration attributes
	 */
	public static List<RegistrationAttribute> findByRegistration(
		long registrationId, int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRegistration(
			registrationId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	public static RegistrationAttribute findByRegistration_First(
			long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationAttributeException {

		return getPersistence().findByRegistration_First(
			registrationId, orderByComparator);
	}

	/**
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	public static RegistrationAttribute fetchByRegistration_First(
		long registrationId,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return getPersistence().fetchByRegistration_First(
			registrationId, orderByComparator);
	}

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	public static RegistrationAttribute findByRegistration_Last(
			long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationAttributeException {

		return getPersistence().findByRegistration_Last(
			registrationId, orderByComparator);
	}

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	public static RegistrationAttribute fetchByRegistration_Last(
		long registrationId,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return getPersistence().fetchByRegistration_Last(
			registrationId, orderByComparator);
	}

	/**
	 * Returns the registration attributes before and after the current registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationAttributeId the primary key of the current registration attribute
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute[] findByRegistration_PrevAndNext(
			long registrationAttributeId, long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationAttributeException {

		return getPersistence().findByRegistration_PrevAndNext(
			registrationAttributeId, registrationId, orderByComparator);
	}

	/**
	 * Removes all the registration attributes where registrationId = &#63; from the database.
	 *
	 * @param registrationId the registration ID
	 */
	public static void removeByRegistration(long registrationId) {
		getPersistence().removeByRegistration(registrationId);
	}

	/**
	 * Returns the number of registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the number of matching registration attributes
	 */
	public static int countByRegistration(long registrationId) {
		return getPersistence().countByRegistration(registrationId);
	}

	/**
	 * Caches the registration attribute in the entity cache if it is enabled.
	 *
	 * @param registrationAttribute the registration attribute
	 */
	public static void cacheResult(
		RegistrationAttribute registrationAttribute) {

		getPersistence().cacheResult(registrationAttribute);
	}

	/**
	 * Caches the registration attributes in the entity cache if it is enabled.
	 *
	 * @param registrationAttributes the registration attributes
	 */
	public static void cacheResult(
		List<RegistrationAttribute> registrationAttributes) {

		getPersistence().cacheResult(registrationAttributes);
	}

	/**
	 * Creates a new registration attribute with the primary key. Does not add the registration attribute to the database.
	 *
	 * @param registrationAttributeId the primary key for the new registration attribute
	 * @return the new registration attribute
	 */
	public static RegistrationAttribute create(long registrationAttributeId) {
		return getPersistence().create(registrationAttributeId);
	}

	/**
	 * Removes the registration attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute that was removed
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute remove(long registrationAttributeId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationAttributeException {

		return getPersistence().remove(registrationAttributeId);
	}

	public static RegistrationAttribute updateImpl(
		RegistrationAttribute registrationAttribute) {

		return getPersistence().updateImpl(registrationAttribute);
	}

	/**
	 * Returns the registration attribute with the primary key or throws a <code>NoSuchRegistrationAttributeException</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute findByPrimaryKey(
			long registrationAttributeId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationAttributeException {

		return getPersistence().findByPrimaryKey(registrationAttributeId);
	}

	/**
	 * Returns the registration attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute, or <code>null</code> if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute fetchByPrimaryKey(
		long registrationAttributeId) {

		return getPersistence().fetchByPrimaryKey(registrationAttributeId);
	}

	/**
	 * Returns all the registration attributes.
	 *
	 * @return the registration attributes
	 */
	public static List<RegistrationAttribute> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the registration attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @return the range of registration attributes
	 */
	public static List<RegistrationAttribute> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the registration attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registration attributes
	 */
	public static List<RegistrationAttribute> findAll(
		int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registration attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registration attributes
	 */
	public static List<RegistrationAttribute> findAll(
		int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the registration attributes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of registration attributes.
	 *
	 * @return the number of registration attributes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegistrationAttributePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RegistrationAttributePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RegistrationAttributePersistence _persistence;

}