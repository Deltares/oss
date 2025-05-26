/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.deltares.data.service.registration.exception.NoSuchRegistrationPeriodException;
import nl.deltares.data.service.registration.model.RegistrationPeriod;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the registration period service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationPeriodUtil
 * @generated
 */
@ProviderType
public interface RegistrationPeriodPersistence
	extends BasePersistence<RegistrationPeriod> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegistrationPeriodUtil} to access the registration period persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration periods
	 */
	public java.util.List<RegistrationPeriod> findByResource(
		long registrationResourceId);

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
	public java.util.List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end);

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
	public java.util.List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator);

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
	public java.util.List<RegistrationPeriod> findByResource(
		long registrationResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	public RegistrationPeriod findByResource_First(
			long registrationResourceId,
			com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
				orderByComparator)
		throws NoSuchRegistrationPeriodException;

	/**
	 * Returns the first registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	public RegistrationPeriod fetchByResource_First(
		long registrationResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator);

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period
	 * @throws NoSuchRegistrationPeriodException if a matching registration period could not be found
	 */
	public RegistrationPeriod findByResource_Last(
			long registrationResourceId,
			com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
				orderByComparator)
		throws NoSuchRegistrationPeriodException;

	/**
	 * Returns the last registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration period, or <code>null</code> if a matching registration period could not be found
	 */
	public RegistrationPeriod fetchByResource_Last(
		long registrationResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator);

	/**
	 * Returns the registration periods before and after the current registration period in the ordered set where registrationResourceId = &#63;.
	 *
	 * @param registrationPeriodId the primary key of the current registration period
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public RegistrationPeriod[] findByResource_PrevAndNext(
			long registrationPeriodId, long registrationResourceId,
			com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
				orderByComparator)
		throws NoSuchRegistrationPeriodException;

	/**
	 * Removes all the registration periods where registrationResourceId = &#63; from the database.
	 *
	 * @param registrationResourceId the registration resource ID
	 */
	public void removeByResource(long registrationResourceId);

	/**
	 * Returns the number of registration periods where registrationResourceId = &#63;.
	 *
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration periods
	 */
	public int countByResource(long registrationResourceId);

	/**
	 * Caches the registration period in the entity cache if it is enabled.
	 *
	 * @param registrationPeriod the registration period
	 */
	public void cacheResult(RegistrationPeriod registrationPeriod);

	/**
	 * Caches the registration periods in the entity cache if it is enabled.
	 *
	 * @param registrationPeriods the registration periods
	 */
	public void cacheResult(
		java.util.List<RegistrationPeriod> registrationPeriods);

	/**
	 * Creates a new registration period with the primary key. Does not add the registration period to the database.
	 *
	 * @param registrationPeriodId the primary key for the new registration period
	 * @return the new registration period
	 */
	public RegistrationPeriod create(long registrationPeriodId);

	/**
	 * Removes the registration period with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period that was removed
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public RegistrationPeriod remove(long registrationPeriodId)
		throws NoSuchRegistrationPeriodException;

	public RegistrationPeriod updateImpl(RegistrationPeriod registrationPeriod);

	/**
	 * Returns the registration period with the primary key or throws a <code>NoSuchRegistrationPeriodException</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period
	 * @throws NoSuchRegistrationPeriodException if a registration period with the primary key could not be found
	 */
	public RegistrationPeriod findByPrimaryKey(long registrationPeriodId)
		throws NoSuchRegistrationPeriodException;

	/**
	 * Returns the registration period with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationPeriodId the primary key of the registration period
	 * @return the registration period, or <code>null</code> if a registration period with the primary key could not be found
	 */
	public RegistrationPeriod fetchByPrimaryKey(long registrationPeriodId);

	/**
	 * Returns all the registration periods.
	 *
	 * @return the registration periods
	 */
	public java.util.List<RegistrationPeriod> findAll();

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
	public java.util.List<RegistrationPeriod> findAll(int start, int end);

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
	public java.util.List<RegistrationPeriod> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator);

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
	public java.util.List<RegistrationPeriod> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationPeriod>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the registration periods from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of registration periods.
	 *
	 * @return the number of registration periods
	 */
	public int countAll();

}