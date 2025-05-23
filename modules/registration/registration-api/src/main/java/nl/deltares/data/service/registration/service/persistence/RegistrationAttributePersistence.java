/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.deltares.data.service.registration.exception.NoSuchRegistrationAttributeException;
import nl.deltares.data.service.registration.model.RegistrationAttribute;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the registration attribute service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttributeUtil
 * @generated
 */
@ProviderType
public interface RegistrationAttributePersistence
	extends BasePersistence<RegistrationAttribute> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegistrationAttributeUtil} to access the registration attribute persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the matching registration attributes
	 */
	public java.util.List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId);

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
	public java.util.List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end);

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
	public java.util.List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator);

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
	public java.util.List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	public RegistrationAttribute findByRegistrationAttribute_First(
			long registrationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException;

	/**
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	public RegistrationAttribute fetchByRegistrationAttribute_First(
		long registrationId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator);

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	public RegistrationAttribute findByRegistrationAttribute_Last(
			long registrationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException;

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	public RegistrationAttribute fetchByRegistrationAttribute_Last(
		long registrationId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator);

	/**
	 * Returns the registration attributes before and after the current registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationAttributeId the primary key of the current registration attribute
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public RegistrationAttribute[] findByRegistrationAttribute_PrevAndNext(
			long registrationAttributeId, long registrationId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException;

	/**
	 * Removes all the registration attributes where registrationId = &#63; from the database.
	 *
	 * @param registrationId the registration ID
	 */
	public void removeByRegistrationAttribute(long registrationId);

	/**
	 * Returns the number of registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the number of matching registration attributes
	 */
	public int countByRegistrationAttribute(long registrationId);

	/**
	 * Caches the registration attribute in the entity cache if it is enabled.
	 *
	 * @param registrationAttribute the registration attribute
	 */
	public void cacheResult(RegistrationAttribute registrationAttribute);

	/**
	 * Caches the registration attributes in the entity cache if it is enabled.
	 *
	 * @param registrationAttributes the registration attributes
	 */
	public void cacheResult(
		java.util.List<RegistrationAttribute> registrationAttributes);

	/**
	 * Creates a new registration attribute with the primary key. Does not add the registration attribute to the database.
	 *
	 * @param registrationAttributeId the primary key for the new registration attribute
	 * @return the new registration attribute
	 */
	public RegistrationAttribute create(long registrationAttributeId);

	/**
	 * Removes the registration attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute that was removed
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public RegistrationAttribute remove(long registrationAttributeId)
		throws NoSuchRegistrationAttributeException;

	public RegistrationAttribute updateImpl(
		RegistrationAttribute registrationAttribute);

	/**
	 * Returns the registration attribute with the primary key or throws a <code>NoSuchRegistrationAttributeException</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	public RegistrationAttribute findByPrimaryKey(long registrationAttributeId)
		throws NoSuchRegistrationAttributeException;

	/**
	 * Returns the registration attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute, or <code>null</code> if a registration attribute with the primary key could not be found
	 */
	public RegistrationAttribute fetchByPrimaryKey(
		long registrationAttributeId);

	/**
	 * Returns all the registration attributes.
	 *
	 * @return the registration attributes
	 */
	public java.util.List<RegistrationAttribute> findAll();

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
	public java.util.List<RegistrationAttribute> findAll(int start, int end);

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
	public java.util.List<RegistrationAttribute> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator);

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
	public java.util.List<RegistrationAttribute> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationAttribute>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the registration attributes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of registration attributes.
	 *
	 * @return the number of registration attributes
	 */
	public int countAll();

}