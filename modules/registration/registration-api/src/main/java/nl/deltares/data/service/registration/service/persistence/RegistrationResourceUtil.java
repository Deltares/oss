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

import nl.deltares.data.service.registration.model.RegistrationResource;

/**
 * The persistence utility for the registration resource service. This utility wraps <code>nl.deltares.data.service.registration.service.persistence.impl.RegistrationResourcePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResourcePersistence
 * @generated
 */
public class RegistrationResourceUtil {

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
	public static void clearCache(RegistrationResource registrationResource) {
		getPersistence().clearCache(registrationResource);
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
	public static Map<Serializable, RegistrationResource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RegistrationResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RegistrationResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RegistrationResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RegistrationResource update(
		RegistrationResource registrationResource) {

		return getPersistence().update(registrationResource);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RegistrationResource update(
		RegistrationResource registrationResource,
		ServiceContext serviceContext) {

		return getPersistence().update(registrationResource, serviceContext);
	}

	/**
	 * Returns all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the matching registration resources
	 */
	public static List<RegistrationResource> findByGroupAndEventResource(
		long groupId, long eventResourceId) {

		return getPersistence().findByGroupAndEventResource(
			groupId, eventResourceId);
	}

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
	public static List<RegistrationResource> findByGroupAndEventResource(
		long groupId, long eventResourceId, int start, int end) {

		return getPersistence().findByGroupAndEventResource(
			groupId, eventResourceId, start, end);
	}

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
	public static List<RegistrationResource> findByGroupAndEventResource(
		long groupId, long eventResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findByGroupAndEventResource(
			groupId, eventResourceId, start, end, orderByComparator);
	}

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
	public static List<RegistrationResource> findByGroupAndEventResource(
		long groupId, long eventResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupAndEventResource(
			groupId, eventResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndEventResource_First(
			long groupId, long eventResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventResource_First(
			groupId, eventResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndEventResource_First(
		long groupId, long eventResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndEventResource_First(
			groupId, eventResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndEventResource_Last(
			long groupId, long eventResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventResource_Last(
			groupId, eventResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndEventResource_Last(
		long groupId, long eventResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndEventResource_Last(
			groupId, eventResourceId, orderByComparator);
	}

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
	public static RegistrationResource[]
			findByGroupAndEventResource_PrevAndNext(
				long registrationResourceId, long groupId, long eventResourceId,
				OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventResource_PrevAndNext(
			registrationResourceId, groupId, eventResourceId,
			orderByComparator);
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and eventResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 */
	public static void removeByGroupAndEventResource(
		long groupId, long eventResourceId) {

		getPersistence().removeByGroupAndEventResource(
			groupId, eventResourceId);
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the number of matching registration resources
	 */
	public static int countByGroupAndEventResource(
		long groupId, long eventResourceId) {

		return getPersistence().countByGroupAndEventResource(
			groupId, eventResourceId);
	}

	/**
	 * Returns all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the matching registration resources
	 */
	public static List<RegistrationResource> findByGroupAndEventArticle(
		long groupId, long eventArticleId) {

		return getPersistence().findByGroupAndEventArticle(
			groupId, eventArticleId);
	}

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
	public static List<RegistrationResource> findByGroupAndEventArticle(
		long groupId, long eventArticleId, int start, int end) {

		return getPersistence().findByGroupAndEventArticle(
			groupId, eventArticleId, start, end);
	}

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
	public static List<RegistrationResource> findByGroupAndEventArticle(
		long groupId, long eventArticleId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findByGroupAndEventArticle(
			groupId, eventArticleId, start, end, orderByComparator);
	}

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
	public static List<RegistrationResource> findByGroupAndEventArticle(
		long groupId, long eventArticleId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupAndEventArticle(
			groupId, eventArticleId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndEventArticle_First(
			long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventArticle_First(
			groupId, eventArticleId, orderByComparator);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndEventArticle_First(
		long groupId, long eventArticleId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndEventArticle_First(
			groupId, eventArticleId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndEventArticle_Last(
			long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventArticle_Last(
			groupId, eventArticleId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndEventArticle_Last(
		long groupId, long eventArticleId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndEventArticle_Last(
			groupId, eventArticleId, orderByComparator);
	}

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
	public static RegistrationResource[] findByGroupAndEventArticle_PrevAndNext(
			long registrationResourceId, long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndEventArticle_PrevAndNext(
			registrationResourceId, groupId, eventArticleId, orderByComparator);
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and eventArticleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 */
	public static void removeByGroupAndEventArticle(
		long groupId, long eventArticleId) {

		getPersistence().removeByGroupAndEventArticle(groupId, eventArticleId);
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the number of matching registration resources
	 */
	public static int countByGroupAndEventArticle(
		long groupId, long eventArticleId) {

		return getPersistence().countByGroupAndEventArticle(
			groupId, eventArticleId);
	}

	/**
	 * Returns all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the matching registration resources
	 */
	public static List<RegistrationResource> findByGroupAndParentResource(
		long groupId, long parentResourceId) {

		return getPersistence().findByGroupAndParentResource(
			groupId, parentResourceId);
	}

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
	public static List<RegistrationResource> findByGroupAndParentResource(
		long groupId, long parentResourceId, int start, int end) {

		return getPersistence().findByGroupAndParentResource(
			groupId, parentResourceId, start, end);
	}

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
	public static List<RegistrationResource> findByGroupAndParentResource(
		long groupId, long parentResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findByGroupAndParentResource(
			groupId, parentResourceId, start, end, orderByComparator);
	}

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
	public static List<RegistrationResource> findByGroupAndParentResource(
		long groupId, long parentResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupAndParentResource(
			groupId, parentResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndParentResource_First(
			long groupId, long parentResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndParentResource_First(
			groupId, parentResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndParentResource_First(
		long groupId, long parentResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndParentResource_First(
			groupId, parentResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndParentResource_Last(
			long groupId, long parentResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndParentResource_Last(
			groupId, parentResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndParentResource_Last(
		long groupId, long parentResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndParentResource_Last(
			groupId, parentResourceId, orderByComparator);
	}

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
	public static RegistrationResource[]
			findByGroupAndParentResource_PrevAndNext(
				long registrationResourceId, long groupId,
				long parentResourceId,
				OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndParentResource_PrevAndNext(
			registrationResourceId, groupId, parentResourceId,
			orderByComparator);
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and parentResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 */
	public static void removeByGroupAndParentResource(
		long groupId, long parentResourceId) {

		getPersistence().removeByGroupAndParentResource(
			groupId, parentResourceId);
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the number of matching registration resources
	 */
	public static int countByGroupAndParentResource(
		long groupId, long parentResourceId) {

		return getPersistence().countByGroupAndParentResource(
			groupId, parentResourceId);
	}

	/**
	 * Returns all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration resources
	 */
	public static List<RegistrationResource> findByGroupAndResource(
		long groupId, long registrationResourceId) {

		return getPersistence().findByGroupAndResource(
			groupId, registrationResourceId);
	}

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
	public static List<RegistrationResource> findByGroupAndResource(
		long groupId, long registrationResourceId, int start, int end) {

		return getPersistence().findByGroupAndResource(
			groupId, registrationResourceId, start, end);
	}

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
	public static List<RegistrationResource> findByGroupAndResource(
		long groupId, long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findByGroupAndResource(
			groupId, registrationResourceId, start, end, orderByComparator);
	}

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
	public static List<RegistrationResource> findByGroupAndResource(
		long groupId, long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupAndResource(
			groupId, registrationResourceId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndResource_First(
			long groupId, long registrationResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndResource_First(
			groupId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndResource_First(
		long groupId, long registrationResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndResource_First(
			groupId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource
	 * @throws NoSuchRegistrationResourceException if a matching registration resource could not be found
	 */
	public static RegistrationResource findByGroupAndResource_Last(
			long groupId, long registrationResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByGroupAndResource_Last(
			groupId, registrationResourceId, orderByComparator);
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	public static RegistrationResource fetchByGroupAndResource_Last(
		long groupId, long registrationResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().fetchByGroupAndResource_Last(
			groupId, registrationResourceId, orderByComparator);
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 */
	public static void removeByGroupAndResource(
		long groupId, long registrationResourceId) {

		getPersistence().removeByGroupAndResource(
			groupId, registrationResourceId);
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration resources
	 */
	public static int countByGroupAndResource(
		long groupId, long registrationResourceId) {

		return getPersistence().countByGroupAndResource(
			groupId, registrationResourceId);
	}

	/**
	 * Caches the registration resource in the entity cache if it is enabled.
	 *
	 * @param registrationResource the registration resource
	 */
	public static void cacheResult(RegistrationResource registrationResource) {
		getPersistence().cacheResult(registrationResource);
	}

	/**
	 * Caches the registration resources in the entity cache if it is enabled.
	 *
	 * @param registrationResources the registration resources
	 */
	public static void cacheResult(
		List<RegistrationResource> registrationResources) {

		getPersistence().cacheResult(registrationResources);
	}

	/**
	 * Creates a new registration resource with the primary key. Does not add the registration resource to the database.
	 *
	 * @param registrationResourceId the primary key for the new registration resource
	 * @return the new registration resource
	 */
	public static RegistrationResource create(long registrationResourceId) {
		return getPersistence().create(registrationResourceId);
	}

	/**
	 * Removes the registration resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource that was removed
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public static RegistrationResource remove(long registrationResourceId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().remove(registrationResourceId);
	}

	public static RegistrationResource updateImpl(
		RegistrationResource registrationResource) {

		return getPersistence().updateImpl(registrationResource);
	}

	/**
	 * Returns the registration resource with the primary key or throws a <code>NoSuchRegistrationResourceException</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	public static RegistrationResource findByPrimaryKey(
			long registrationResourceId)
		throws nl.deltares.data.service.registration.exception.
			NoSuchRegistrationResourceException {

		return getPersistence().findByPrimaryKey(registrationResourceId);
	}

	/**
	 * Returns the registration resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource, or <code>null</code> if a registration resource with the primary key could not be found
	 */
	public static RegistrationResource fetchByPrimaryKey(
		long registrationResourceId) {

		return getPersistence().fetchByPrimaryKey(registrationResourceId);
	}

	/**
	 * Returns all the registration resources.
	 *
	 * @return the registration resources
	 */
	public static List<RegistrationResource> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<RegistrationResource> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<RegistrationResource> findAll(
		int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<RegistrationResource> findAll(
		int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the registration resources from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of registration resources.
	 *
	 * @return the number of registration resources
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegistrationResourcePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RegistrationResourcePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RegistrationResourcePersistence _persistence;

}