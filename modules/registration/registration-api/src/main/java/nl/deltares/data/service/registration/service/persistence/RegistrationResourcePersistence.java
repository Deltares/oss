/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import nl.deltares.data.service.registration.exception.NoSuchRegistrationResourceException;
import nl.deltares.data.service.registration.model.RegistrationResource;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the registration resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResourceUtil
 * @generated
 */
@ProviderType
public interface RegistrationResourcePersistence
	extends BasePersistence<RegistrationResource> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegistrationResourceUtil} to access the registration resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId);

	/**
	 * Returns a range of all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByEventResources_First(
			long groupId, long eventResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByEventResources_First(
		long groupId, long eventResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByEventResources_Last(
			long groupId, long eventResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByEventResources_Last(
		long groupId, long eventResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the registration resources before and after the current registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param registrationResourceId the primary key of the current registration resource
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public RegistrationResource[] findByEventResources_PrevAndNext(
			long registrationResourceId, long groupId, long eventResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Removes all the registration resources where groupId = &#63; and eventResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 */
	public void removeByEventResources(long groupId, long eventResourceId);

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the number of matching registration resources
	 */
	public int countByEventResources(long groupId, long eventResourceId);

	/**
	 * Returns all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId);

	/**
	 * Returns a range of all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByEventArticle_First(
			long groupId, long eventArticleId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByEventArticle_First(
		long groupId, long eventArticleId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByEventArticle_Last(
			long groupId, long eventArticleId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByEventArticle_Last(
		long groupId, long eventArticleId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the registration resources before and after the current registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param registrationResourceId the primary key of the current registration resource
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public RegistrationResource[] findByEventArticle_PrevAndNext(
			long registrationResourceId, long groupId, long eventArticleId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Removes all the registration resources where groupId = &#63; and eventArticleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 */
	public void removeByEventArticle(long groupId, long eventArticleId);

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the number of matching registration resources
	 */
	public int countByEventArticle(long groupId, long eventArticleId);

	/**
	 * Returns all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the matching registration resources
	 */
	public java.util.List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId);

	/**
	 * Returns a range of all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByChildResources_First(
			long groupId, long parentResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByChildResources_First(
		long groupId, long parentResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByChildResources_Last(
			long groupId, long parentResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByChildResources_Last(
		long groupId, long parentResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the registration resources before and after the current registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param registrationResourceId the primary key of the current registration resource
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public RegistrationResource[] findByChildResources_PrevAndNext(
			long registrationResourceId, long groupId, long parentResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Removes all the registration resources where groupId = &#63; and parentResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 */
	public void removeByChildResources(long groupId, long parentResourceId);

	/**
	 * Returns the number of registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the number of matching registration resources
	 */
	public int countByChildResources(long groupId, long parentResourceId);

	/**
	 * Returns all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration resources
	 */
	public java.util.List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId);

	/**
	 * Returns a range of all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching registration resources
	 */
	public java.util.List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByResources_First(
			long groupId, long registrationResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByResources_First(
		long groupId, long registrationResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public RegistrationResource findByResources_Last(
			long groupId, long registrationResourceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public RegistrationResource fetchByResources_Last(
		long groupId, long registrationResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Removes all the registration resources where groupId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 */
	public void removeByResources(long groupId, long registrationResourceId);

	/**
	 * Returns the number of registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration resources
	 */
	public int countByResources(long groupId, long registrationResourceId);

	/**
	 * Caches the registration resource in the entity cache if it is enabled.
	 *
	 * @param registrationResource the registration resource
	 */
	public void cacheResult(RegistrationResource registrationResource);

	/**
	 * Caches the registration resources in the entity cache if it is enabled.
	 *
	 * @param registrationResources the registration resources
	 */
	public void cacheResult(
		java.util.List<RegistrationResource> registrationResources);

	/**
	 * Creates a new registration resource with the primary key. Does not add the registration resource to the database.
	 *
	 * @param registrationResourceId the primary key for the new registration resource
	 * @return the new registration resource
	 */
	public RegistrationResource create(long registrationResourceId);

	/**
	 * Removes the registration resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource that was removed
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public RegistrationResource remove(long registrationResourceId)
		throws NoSuchRegistrationResourceException;

	public RegistrationResource updateImpl(
		RegistrationResource registrationResource);

	/**
	 * Returns the registration resource with the primary key or throws a <code>NoSuchRegistrationResourceException</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public RegistrationResource findByPrimaryKey(long registrationResourceId)
		throws NoSuchRegistrationResourceException;

	/**
	 * Returns the registration resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource, or <code>null</code> if a registration resource with the primary key could not be found
	 */
	public RegistrationResource fetchByPrimaryKey(long registrationResourceId);

	/**
	 * Returns all the registration resources.
	 *
	 * @return the registration resources
	 */
	public java.util.List<RegistrationResource> findAll();

	/**
	 * Returns a range of all the registration resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of registration resources
	 */
	public java.util.List<RegistrationResource> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the registration resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registration resources
	 */
	public java.util.List<RegistrationResource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator);

	/**
	 * Returns an ordered range of all the registration resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of registration resources
	 */
	public java.util.List<RegistrationResource> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegistrationResource>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the registration resources from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of registration resources.
	 *
	 * @return the number of registration resources
	 */
	public int countAll();

}