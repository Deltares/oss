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

import nl.deltares.data.service.registration.model.RegistrationPeriod;

/**
 * The persistence utility for the registration period service. This utility wraps <code>nl.deltares.data.service.registration.service.persistence.impl.RegistrationPeriodPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriodPersistence
 * @generated
 */
public class RegistrationPeriodUtil {

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
	public static void clearCache(RegistrationPeriod registrationPeriod) {
		getPersistence().clearCache(registrationPeriod);
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
	public static Map<Serializable, RegistrationPeriod> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RegistrationPeriod> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RegistrationPeriod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RegistrationPeriod> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RegistrationPeriod update(
		RegistrationPeriod registrationPeriod) {

		return getPersistence().update(registrationPeriod);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RegistrationPeriod update(
		RegistrationPeriod registrationPeriod, ServiceContext serviceContext) {

		return getPersistence().update(registrationPeriod, serviceContext);
	}

	/**
	 * Returns all the registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration periods
	 */
	public static List<RegistrationPeriod> findByResource(
		long registrationResourceId) {

		return getPersistence().findByResource(registrationResourceId);
	}

	/**
	 * Returns a range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @return the range of matching registration periods
	 */
	public static List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end) {

		return getPersistence().findByResource(
			registrationResourceId, start, end);
	}

	/**
	 * Returns an ordered range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration periods
	 */
	public static List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return getPersistence().findByResource(
			registrationResourceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registration periods where registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration periods
	 */
	public static List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByResource(
			registrationResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	public static RegistrationPeriod findByResource_First(
			long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationPeriodException {

		return getPersistence().findByResource_First(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	public static RegistrationPeriod fetchByResource_First(
		long registrationResourceId,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return getPersistence().fetchByResource_First(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	public static RegistrationPeriod findByResource_Last(
			long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationPeriodException {

		return getPersistence().findByResource_Last(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	public static RegistrationPeriod fetchByResource_Last(
		long registrationResourceId,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return getPersistence().fetchByResource_Last(
			registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the registration periods before and after the current registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationPeriodId the primary key of the current registration period
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public static RegistrationPeriod[] findByResource_PrevAndNext(
			long registrationPeriodId, long registrationResourceId,
			OrderByComparator<RegistrationPeriod> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationPeriodException {

		return getPersistence().findByResource_PrevAndNext(
			registrationPeriodId, registrationResourceId, orderByComparator);
	}

	/**
	 * Removes all the registration periods where registrationResourceId = &#63; from the database.
	 *
	 * @param registrationResourceId the registration resource ID
	 */
	public static void removeByResource(long registrationResourceId) {
		getPersistence().removeByResource(registrationResourceId);
	}

	/**
	 * Returns the number of registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration periods
	 */
	public static int countByResource(long registrationResourceId) {
		return getPersistence().countByResource(registrationResourceId);
	}

	/**
	 * Caches the registration period in the entity cache if it is enabled.
	 *
	 * @param registrationPeriod the registration period
	 */
	public static void cacheResult(RegistrationPeriod registrationPeriod) {
		getPersistence().cacheResult(registrationPeriod);
	}

	/**
	 * Caches the registration periods in the entity cache if it is enabled.
	 *
	 * @param registrationPeriods the registration periods
	 */
	public static void cacheResult(
		List<RegistrationPeriod> registrationPeriods) {

		getPersistence().cacheResult(registrationPeriods);
	}

	/**
	 * Creates a new registration period with the primary key. Does not add the registration period to the database.
	 *
	 * @param registrationPeriodId the primary key for the new registration period
	 * @return the new registration period
	 */
	public static RegistrationPeriod create(long registrationPeriodId) {
		return getPersistence().create(registrationPeriodId);
	}

	/**
	 * Removes the registration period with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period that was removed
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public static RegistrationPeriod remove(long registrationPeriodId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationPeriodException {

		return getPersistence().remove(registrationPeriodId);
	}

	public static RegistrationPeriod updateImpl(
		RegistrationPeriod registrationPeriod) {

		return getPersistence().updateImpl(registrationPeriod);
	}

	/**
	 * Returns the registration period with the primary key or throws a <code>NoSuchRegistrationPeriodException</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public static RegistrationPeriod findByPrimaryKey(long registrationPeriodId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationPeriodException {

		return getPersistence().findByPrimaryKey(registrationPeriodId);
	}

	/**
	 * Returns the registration period with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period, or <code>null</code> if a registration period with the primary key could not be found
	 */
	public static RegistrationPeriod fetchByPrimaryKey(
		long registrationPeriodId) {

		return getPersistence().fetchByPrimaryKey(registrationPeriodId);
	}

	/**
	 * Returns all the registration periods.
	 *
	 * @return the registration periods
	 */
	public static List<RegistrationPeriod> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @return the range of registration periods
	 */
	public static List<RegistrationPeriod> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registration periods
	 */
	public static List<RegistrationPeriod> findAll(
		int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the registration periods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationPeriodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration periods
	 * @param end the upper bound of the range of registration periods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registration periods
	 */
	public static List<RegistrationPeriod> findAll(
		int start, int end,
		OrderByComparator<RegistrationPeriod> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the registration periods from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of registration periods.
	 *
	 * @return the number of registration periods
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegistrationPeriodPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RegistrationPeriodPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RegistrationPeriodPersistence _persistence;

}