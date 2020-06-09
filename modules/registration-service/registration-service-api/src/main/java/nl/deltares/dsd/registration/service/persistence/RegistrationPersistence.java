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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;

/**
 * The persistence interface for the registration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationUtil
 * @generated
 */
@ProviderType
public interface RegistrationPersistence extends BasePersistence<Registration> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegistrationUtil} to access the registration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, Registration> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	public java.util.List<Registration> findByUserRegistrations(
		long groupId, long userId);

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
	public java.util.List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end);

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
	public java.util.List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByUserRegistrations_First(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserRegistrations_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByUserRegistrations_Last(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserRegistrations_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public void removeByUserRegistrations(long groupId, long userId);

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	public int countByUserRegistrations(long groupId, long userId);

	/**
	 * Returns all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	public java.util.List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey);

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
	public java.util.List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end);

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
	public java.util.List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByArticleRegistrations_First(
			long groupId, long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByArticleRegistrations_First(
		long groupId, long resourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByArticleRegistrations_Last(
			long groupId, long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByArticleRegistrations_Last(
		long groupId, long resourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Removes all the registrations where groupId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	public void removeByArticleRegistrations(
		long groupId, long resourcePrimaryKey);

	/**
	 * Returns the number of registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	public int countByArticleRegistrations(
		long groupId, long resourcePrimaryKey);

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	public java.util.List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey);

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
	public java.util.List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end);

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
	public java.util.List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

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
	public Registration findByUserArticleRegistrations_First(
			long groupId, long userId, long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserArticleRegistrations_First(
		long groupId, long userId, long resourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration findByUserArticleRegistrations_Last(
			long groupId, long userId, long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserArticleRegistrations_Last(
		long groupId, long userId, long resourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration[] findByUserArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long resourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	public void removeByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey);

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	public int countByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey);

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	public java.util.List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey);

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
	public java.util.List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end);

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
	public java.util.List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

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
	public Registration findByUserChildArticleRegistrations_First(
			long groupId, long userId, long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserChildArticleRegistrations_First(
		long groupId, long userId, long parentResourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration findByUserChildArticleRegistrations_Last(
			long groupId, long userId, long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByUserChildArticleRegistrations_Last(
		long groupId, long userId, long parentResourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration[] findByUserChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	public void removeByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey);

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	public int countByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey);

	/**
	 * Returns all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	public java.util.List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey);

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
	public java.util.List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end);

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
	public java.util.List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByChildArticleRegistrations_First(
			long groupId, long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByChildArticleRegistrations_First(
		long groupId, long parentResourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	public Registration findByChildArticleRegistrations_Last(
			long groupId, long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	public Registration fetchByChildArticleRegistrations_Last(
		long groupId, long parentResourcePrimaryKey,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public Registration[] findByChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long parentResourcePrimaryKey,
			com.liferay.portal.kernel.util.OrderByComparator<Registration>
				orderByComparator)
		throws NoSuchRegistrationException;

	/**
	 * Removes all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	public void removeByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey);

	/**
	 * Returns the number of registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	public int countByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey);

	/**
	 * Caches the registration in the entity cache if it is enabled.
	 *
	 * @param registration the registration
	 */
	public void cacheResult(Registration registration);

	/**
	 * Caches the registrations in the entity cache if it is enabled.
	 *
	 * @param registrations the registrations
	 */
	public void cacheResult(java.util.List<Registration> registrations);

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	public Registration create(long registrationId);

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public Registration remove(long registrationId)
		throws NoSuchRegistrationException;

	public Registration updateImpl(Registration registration);

	/**
	 * Returns the registration with the primary key or throws a <code>NoSuchRegistrationException</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	public Registration findByPrimaryKey(long registrationId)
		throws NoSuchRegistrationException;

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	public Registration fetchByPrimaryKey(long registrationId);

	/**
	 * Returns all the registrations.
	 *
	 * @return the registrations
	 */
	public java.util.List<Registration> findAll();

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
	public java.util.List<Registration> findAll(int start, int end);

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
	public java.util.List<Registration> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator);

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
	public java.util.List<Registration> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Registration>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the registrations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	public int countAll();

}