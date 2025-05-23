/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import nl.deltares.data.service.registration.exception.NoSuchRegistrationResourceException;
import nl.deltares.data.service.registration.model.RegistrationResource;
import nl.deltares.data.service.registration.model.RegistrationResourceTable;
import nl.deltares.data.service.registration.model.impl.RegistrationResourceImpl;
import nl.deltares.data.service.registration.model.impl.RegistrationResourceModelImpl;
import nl.deltares.data.service.registration.service.persistence.RegistrationResourcePersistence;
import nl.deltares.data.service.registration.service.persistence.RegistrationResourceUtil;
import nl.deltares.data.service.registration.service.persistence.impl.constants.Service_builderPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the registration resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RegistrationResourcePersistence.class)
public class RegistrationResourcePersistenceImpl
	extends BasePersistenceImpl<RegistrationResource>
	implements RegistrationResourcePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegistrationResourceUtil</code> to access the registration resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegistrationResourceImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByEventResources;
	private FinderPath _finderPathWithoutPaginationFindByEventResources;
	private FinderPath _finderPathCountByEventResources;

	/**
	 * Returns all the registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the matching registration resources
	 */
	@Override
	public List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId) {

		return findByEventResources(
			groupId, eventResourceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	@Override
	public List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end) {

		return findByEventResources(groupId, eventResourceId, start, end, null);
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
	@Override
	public List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return findByEventResources(
			groupId, eventResourceId, start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationResource> findByEventResources(
		long groupId, long eventResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByEventResources;
				finderArgs = new Object[] {groupId, eventResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEventResources;
			finderArgs = new Object[] {
				groupId, eventResourceId, start, end, orderByComparator
			};
		}

		List<RegistrationResource> list = null;

		if (useFinderCache) {
			list = (List<RegistrationResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationResource registrationResource : list) {
					if ((groupId != registrationResource.getGroupId()) ||
						(eventResourceId !=
							registrationResource.getEventResourceId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_EVENTRESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_EVENTRESOURCES_EVENTRESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventResourceId);

				list = (List<RegistrationResource>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public RegistrationResource findByEventResources_First(
			long groupId, long eventResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByEventResources_First(
			groupId, eventResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventResourceId=");
		sb.append(eventResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByEventResources_First(
		long groupId, long eventResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		List<RegistrationResource> list = findByEventResources(
			groupId, eventResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource findByEventResources_Last(
			long groupId, long eventResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByEventResources_Last(
			groupId, eventResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventResourceId=");
		sb.append(eventResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByEventResources_Last(
		long groupId, long eventResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		int count = countByEventResources(groupId, eventResourceId);

		if (count == 0) {
			return null;
		}

		List<RegistrationResource> list = findByEventResources(
			groupId, eventResourceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource[] findByEventResources_PrevAndNext(
			long registrationResourceId, long groupId, long eventResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = findByPrimaryKey(
			registrationResourceId);

		Session session = null;

		try {
			session = openSession();

			RegistrationResource[] array = new RegistrationResourceImpl[3];

			array[0] = getByEventResources_PrevAndNext(
				session, registrationResource, groupId, eventResourceId,
				orderByComparator, true);

			array[1] = registrationResource;

			array[2] = getByEventResources_PrevAndNext(
				session, registrationResource, groupId, eventResourceId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RegistrationResource getByEventResources_PrevAndNext(
		Session session, RegistrationResource registrationResource,
		long groupId, long eventResourceId,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

		sb.append(_FINDER_COLUMN_EVENTRESOURCES_GROUPID_2);

		sb.append(_FINDER_COLUMN_EVENTRESOURCES_EVENTRESOURCEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(eventResourceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						registrationResource)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegistrationResource> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and eventResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 */
	@Override
	public void removeByEventResources(long groupId, long eventResourceId) {
		for (RegistrationResource registrationResource :
				findByEventResources(
					groupId, eventResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registrationResource);
		}
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourceId the event resource ID
	 * @return the number of matching registration resources
	 */
	@Override
	public int countByEventResources(long groupId, long eventResourceId) {
		FinderPath finderPath = _finderPathCountByEventResources;

		Object[] finderArgs = new Object[] {groupId, eventResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_EVENTRESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_EVENTRESOURCES_EVENTRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventResourceId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_EVENTRESOURCES_GROUPID_2 =
		"registrationResource.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_EVENTRESOURCES_EVENTRESOURCEID_2 =
			"registrationResource.eventResourceId = ?";

	private FinderPath _finderPathWithPaginationFindByEventArticle;
	private FinderPath _finderPathWithoutPaginationFindByEventArticle;
	private FinderPath _finderPathCountByEventArticle;

	/**
	 * Returns all the registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the matching registration resources
	 */
	@Override
	public List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId) {

		return findByEventArticle(
			groupId, eventArticleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	@Override
	public List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end) {

		return findByEventArticle(groupId, eventArticleId, start, end, null);
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
	@Override
	public List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return findByEventArticle(
			groupId, eventArticleId, start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationResource> findByEventArticle(
		long groupId, long eventArticleId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByEventArticle;
				finderArgs = new Object[] {groupId, eventArticleId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEventArticle;
			finderArgs = new Object[] {
				groupId, eventArticleId, start, end, orderByComparator
			};
		}

		List<RegistrationResource> list = null;

		if (useFinderCache) {
			list = (List<RegistrationResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationResource registrationResource : list) {
					if ((groupId != registrationResource.getGroupId()) ||
						(eventArticleId !=
							registrationResource.getEventArticleId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_EVENTARTICLE_GROUPID_2);

			sb.append(_FINDER_COLUMN_EVENTARTICLE_EVENTARTICLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventArticleId);

				list = (List<RegistrationResource>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public RegistrationResource findByEventArticle_First(
			long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByEventArticle_First(
			groupId, eventArticleId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventArticleId=");
		sb.append(eventArticleId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByEventArticle_First(
		long groupId, long eventArticleId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		List<RegistrationResource> list = findByEventArticle(
			groupId, eventArticleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource findByEventArticle_Last(
			long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByEventArticle_Last(
			groupId, eventArticleId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", eventArticleId=");
		sb.append(eventArticleId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByEventArticle_Last(
		long groupId, long eventArticleId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		int count = countByEventArticle(groupId, eventArticleId);

		if (count == 0) {
			return null;
		}

		List<RegistrationResource> list = findByEventArticle(
			groupId, eventArticleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource[] findByEventArticle_PrevAndNext(
			long registrationResourceId, long groupId, long eventArticleId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = findByPrimaryKey(
			registrationResourceId);

		Session session = null;

		try {
			session = openSession();

			RegistrationResource[] array = new RegistrationResourceImpl[3];

			array[0] = getByEventArticle_PrevAndNext(
				session, registrationResource, groupId, eventArticleId,
				orderByComparator, true);

			array[1] = registrationResource;

			array[2] = getByEventArticle_PrevAndNext(
				session, registrationResource, groupId, eventArticleId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RegistrationResource getByEventArticle_PrevAndNext(
		Session session, RegistrationResource registrationResource,
		long groupId, long eventArticleId,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

		sb.append(_FINDER_COLUMN_EVENTARTICLE_GROUPID_2);

		sb.append(_FINDER_COLUMN_EVENTARTICLE_EVENTARTICLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(eventArticleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						registrationResource)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegistrationResource> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and eventArticleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 */
	@Override
	public void removeByEventArticle(long groupId, long eventArticleId) {
		for (RegistrationResource registrationResource :
				findByEventArticle(
					groupId, eventArticleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registrationResource);
		}
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and eventArticleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventArticleId the event article ID
	 * @return the number of matching registration resources
	 */
	@Override
	public int countByEventArticle(long groupId, long eventArticleId) {
		FinderPath finderPath = _finderPathCountByEventArticle;

		Object[] finderArgs = new Object[] {groupId, eventArticleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_EVENTARTICLE_GROUPID_2);

			sb.append(_FINDER_COLUMN_EVENTARTICLE_EVENTARTICLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(eventArticleId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_EVENTARTICLE_GROUPID_2 =
		"registrationResource.groupId = ? AND ";

	private static final String _FINDER_COLUMN_EVENTARTICLE_EVENTARTICLEID_2 =
		"registrationResource.eventArticleId = ?";

	private FinderPath _finderPathWithPaginationFindByChildResources;
	private FinderPath _finderPathWithoutPaginationFindByChildResources;
	private FinderPath _finderPathCountByChildResources;

	/**
	 * Returns all the registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the matching registration resources
	 */
	@Override
	public List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId) {

		return findByChildResources(
			groupId, parentResourceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	@Override
	public List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end) {

		return findByChildResources(
			groupId, parentResourceId, start, end, null);
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
	@Override
	public List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return findByChildResources(
			groupId, parentResourceId, start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationResource> findByChildResources(
		long groupId, long parentResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByChildResources;
				finderArgs = new Object[] {groupId, parentResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByChildResources;
			finderArgs = new Object[] {
				groupId, parentResourceId, start, end, orderByComparator
			};
		}

		List<RegistrationResource> list = null;

		if (useFinderCache) {
			list = (List<RegistrationResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationResource registrationResource : list) {
					if ((groupId != registrationResource.getGroupId()) ||
						(parentResourceId !=
							registrationResource.getParentResourceId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_CHILDRESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_CHILDRESOURCES_PARENTRESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(parentResourceId);

				list = (List<RegistrationResource>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public RegistrationResource findByChildResources_First(
			long groupId, long parentResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByChildResources_First(
			groupId, parentResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", parentResourceId=");
		sb.append(parentResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByChildResources_First(
		long groupId, long parentResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		List<RegistrationResource> list = findByChildResources(
			groupId, parentResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource findByChildResources_Last(
			long groupId, long parentResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByChildResources_Last(
			groupId, parentResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", parentResourceId=");
		sb.append(parentResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByChildResources_Last(
		long groupId, long parentResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		int count = countByChildResources(groupId, parentResourceId);

		if (count == 0) {
			return null;
		}

		List<RegistrationResource> list = findByChildResources(
			groupId, parentResourceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource[] findByChildResources_PrevAndNext(
			long registrationResourceId, long groupId, long parentResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = findByPrimaryKey(
			registrationResourceId);

		Session session = null;

		try {
			session = openSession();

			RegistrationResource[] array = new RegistrationResourceImpl[3];

			array[0] = getByChildResources_PrevAndNext(
				session, registrationResource, groupId, parentResourceId,
				orderByComparator, true);

			array[1] = registrationResource;

			array[2] = getByChildResources_PrevAndNext(
				session, registrationResource, groupId, parentResourceId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RegistrationResource getByChildResources_PrevAndNext(
		Session session, RegistrationResource registrationResource,
		long groupId, long parentResourceId,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

		sb.append(_FINDER_COLUMN_CHILDRESOURCES_GROUPID_2);

		sb.append(_FINDER_COLUMN_CHILDRESOURCES_PARENTRESOURCEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(parentResourceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						registrationResource)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegistrationResource> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and parentResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 */
	@Override
	public void removeByChildResources(long groupId, long parentResourceId) {
		for (RegistrationResource registrationResource :
				findByChildResources(
					groupId, parentResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registrationResource);
		}
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and parentResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourceId the parent resource ID
	 * @return the number of matching registration resources
	 */
	@Override
	public int countByChildResources(long groupId, long parentResourceId) {
		FinderPath finderPath = _finderPathCountByChildResources;

		Object[] finderArgs = new Object[] {groupId, parentResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_CHILDRESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_CHILDRESOURCES_PARENTRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(parentResourceId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CHILDRESOURCES_GROUPID_2 =
		"registrationResource.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_CHILDRESOURCES_PARENTRESOURCEID_2 =
			"registrationResource.parentResourceId = ?";

	private FinderPath _finderPathWithPaginationFindByResources;
	private FinderPath _finderPathWithoutPaginationFindByResources;
	private FinderPath _finderPathCountByResources;

	/**
	 * Returns all the registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the matching registration resources
	 */
	@Override
	public List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId) {

		return findByResources(
			groupId, registrationResourceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end) {

		return findByResources(
			groupId, registrationResourceId, start, end, null);
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
	@Override
	public List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return findByResources(
			groupId, registrationResourceId, start, end, orderByComparator,
			true);
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
	@Override
	public List<RegistrationResource> findByResources(
		long groupId, long registrationResourceId, int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByResources;
				finderArgs = new Object[] {groupId, registrationResourceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByResources;
			finderArgs = new Object[] {
				groupId, registrationResourceId, start, end, orderByComparator
			};
		}

		List<RegistrationResource> list = null;

		if (useFinderCache) {
			list = (List<RegistrationResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationResource registrationResource : list) {
					if ((groupId != registrationResource.getGroupId()) ||
						(registrationResourceId !=
							registrationResource.getRegistrationResourceId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_RESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_RESOURCES_REGISTRATIONRESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registrationResourceId);

				list = (List<RegistrationResource>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public RegistrationResource findByResources_First(
			long groupId, long registrationResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByResources_First(
			groupId, registrationResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the first registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByResources_First(
		long groupId, long registrationResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		List<RegistrationResource> list = findByResources(
			groupId, registrationResourceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationResource findByResources_Last(
			long groupId, long registrationResourceId,
			OrderByComparator<RegistrationResource> orderByComparator)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByResources_Last(
			groupId, registrationResourceId, orderByComparator);

		if (registrationResource != null) {
			return registrationResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", registrationResourceId=");
		sb.append(registrationResourceId);

		sb.append("}");

		throw new NoSuchRegistrationResourceException(sb.toString());
	}

	/**
	 * Returns the last registration resource in the ordered set where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration resource, or <code>null</code> if a matching registration resource could not be found
	 */
	@Override
	public RegistrationResource fetchByResources_Last(
		long groupId, long registrationResourceId,
		OrderByComparator<RegistrationResource> orderByComparator) {

		int count = countByResources(groupId, registrationResourceId);

		if (count == 0) {
			return null;
		}

		List<RegistrationResource> list = findByResources(
			groupId, registrationResourceId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the registration resources where groupId = &#63; and registrationResourceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 */
	@Override
	public void removeByResources(long groupId, long registrationResourceId) {
		for (RegistrationResource registrationResource :
				findByResources(
					groupId, registrationResourceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registrationResource);
		}
	}

	/**
	 * Returns the number of registration resources where groupId = &#63; and registrationResourceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registrationResourceId the registration resource ID
	 * @return the number of matching registration resources
	 */
	@Override
	public int countByResources(long groupId, long registrationResourceId) {
		FinderPath finderPath = _finderPathCountByResources;

		Object[] finderArgs = new Object[] {groupId, registrationResourceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REGISTRATIONRESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_RESOURCES_GROUPID_2);

			sb.append(_FINDER_COLUMN_RESOURCES_REGISTRATIONRESOURCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(registrationResourceId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_RESOURCES_GROUPID_2 =
		"registrationResource.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_RESOURCES_REGISTRATIONRESOURCEID_2 =
			"registrationResource.registrationResourceId = ?";

	public RegistrationResourcePersistenceImpl() {
		setModelClass(RegistrationResource.class);

		setModelImplClass(RegistrationResourceImpl.class);
		setModelPKClass(long.class);

		setTable(RegistrationResourceTable.INSTANCE);
	}

	/**
	 * Caches the registration resource in the entity cache if it is enabled.
	 *
	 * @param registrationResource the registration resource
	 */
	@Override
	public void cacheResult(RegistrationResource registrationResource) {
		entityCache.putResult(
			RegistrationResourceImpl.class,
			registrationResource.getPrimaryKey(), registrationResource);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the registration resources in the entity cache if it is enabled.
	 *
	 * @param registrationResources the registration resources
	 */
	@Override
	public void cacheResult(List<RegistrationResource> registrationResources) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (registrationResources.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RegistrationResource registrationResource :
				registrationResources) {

			if (entityCache.getResult(
					RegistrationResourceImpl.class,
					registrationResource.getPrimaryKey()) == null) {

				cacheResult(registrationResource);
			}
		}
	}

	/**
	 * Clears the cache for all registration resources.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RegistrationResourceImpl.class);

		finderCache.clearCache(RegistrationResourceImpl.class);
	}

	/**
	 * Clears the cache for the registration resource.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RegistrationResource registrationResource) {
		entityCache.removeResult(
			RegistrationResourceImpl.class, registrationResource);
	}

	@Override
	public void clearCache(List<RegistrationResource> registrationResources) {
		for (RegistrationResource registrationResource :
				registrationResources) {

			entityCache.removeResult(
				RegistrationResourceImpl.class, registrationResource);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RegistrationResourceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RegistrationResourceImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new registration resource with the primary key. Does not add the registration resource to the database.
	 *
	 * @param registrationResourceId the primary key for the new registration resource
	 * @return the new registration resource
	 */
	@Override
	public RegistrationResource create(long registrationResourceId) {
		RegistrationResource registrationResource =
			new RegistrationResourceImpl();

		registrationResource.setNew(true);
		registrationResource.setPrimaryKey(registrationResourceId);

		registrationResource.setCompanyId(CompanyThreadLocal.getCompanyId());

		return registrationResource;
	}

	/**
	 * Removes the registration resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource that was removed
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	@Override
	public RegistrationResource remove(long registrationResourceId)
		throws NoSuchRegistrationResourceException {

		return remove((Serializable)registrationResourceId);
	}

	/**
	 * Removes the registration resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the registration resource
	 * @return the registration resource that was removed
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	@Override
	public RegistrationResource remove(Serializable primaryKey)
		throws NoSuchRegistrationResourceException {

		Session session = null;

		try {
			session = openSession();

			RegistrationResource registrationResource =
				(RegistrationResource)session.get(
					RegistrationResourceImpl.class, primaryKey);

			if (registrationResource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegistrationResourceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(registrationResource);
		}
		catch (NoSuchRegistrationResourceException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected RegistrationResource removeImpl(
		RegistrationResource registrationResource) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(registrationResource)) {
				registrationResource = (RegistrationResource)session.get(
					RegistrationResourceImpl.class,
					registrationResource.getPrimaryKeyObj());
			}

			if (registrationResource != null) {
				session.delete(registrationResource);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (registrationResource != null) {
			clearCache(registrationResource);
		}

		return registrationResource;
	}

	@Override
	public RegistrationResource updateImpl(
		RegistrationResource registrationResource) {

		boolean isNew = registrationResource.isNew();

		if (!(registrationResource instanceof RegistrationResourceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(registrationResource.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					registrationResource);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in registrationResource proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RegistrationResource implementation " +
					registrationResource.getClass());
		}

		RegistrationResourceModelImpl registrationResourceModelImpl =
			(RegistrationResourceModelImpl)registrationResource;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(registrationResource);
			}
			else {
				registrationResource = (RegistrationResource)session.merge(
					registrationResource);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RegistrationResourceImpl.class, registrationResourceModelImpl,
			false, true);

		if (isNew) {
			registrationResource.setNew(false);
		}

		registrationResource.resetOriginalValues();

		return registrationResource;
	}

	/**
	 * Returns the registration resource with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration resource
	 * @return the registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	@Override
	public RegistrationResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegistrationResourceException {

		RegistrationResource registrationResource = fetchByPrimaryKey(
			primaryKey);

		if (registrationResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegistrationResourceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return registrationResource;
	}

	/**
	 * Returns the registration resource with the primary key or throws a <code>NoSuchRegistrationResourceException</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource
	 * @throws NoSuchRegistrationResourceException if a registration resource with the primary key could not be found
	 */
	@Override
	public RegistrationResource findByPrimaryKey(long registrationResourceId)
		throws NoSuchRegistrationResourceException {

		return findByPrimaryKey((Serializable)registrationResourceId);
	}

	/**
	 * Returns the registration resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource, or <code>null</code> if a registration resource with the primary key could not be found
	 */
	@Override
	public RegistrationResource fetchByPrimaryKey(long registrationResourceId) {
		return fetchByPrimaryKey((Serializable)registrationResourceId);
	}

	/**
	 * Returns all the registration resources.
	 *
	 * @return the registration resources
	 */
	@Override
	public List<RegistrationResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<RegistrationResource> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<RegistrationResource> findAll(
		int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationResource> findAll(
		int start, int end,
		OrderByComparator<RegistrationResource> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<RegistrationResource> list = null;

		if (useFinderCache) {
			list = (List<RegistrationResource>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REGISTRATIONRESOURCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATIONRESOURCE;

				sql = sql.concat(RegistrationResourceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RegistrationResource>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the registration resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RegistrationResource registrationResource : findAll()) {
			remove(registrationResource);
		}
	}

	/**
	 * Returns the number of registration resources.
	 *
	 * @return the number of registration resources
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_REGISTRATIONRESOURCE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "registrationResourceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REGISTRATIONRESOURCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegistrationResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration resource persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByEventResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEventResources",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "eventResourceId"}, true);

		_finderPathWithoutPaginationFindByEventResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEventResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventResourceId"}, true);

		_finderPathCountByEventResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEventResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventResourceId"}, false);

		_finderPathWithPaginationFindByEventArticle = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEventArticle",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "eventArticleId"}, true);

		_finderPathWithoutPaginationFindByEventArticle = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEventArticle",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventArticleId"}, true);

		_finderPathCountByEventArticle = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEventArticle",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "eventArticleId"}, false);

		_finderPathWithPaginationFindByChildResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByChildResources",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "parentResourceId"}, true);

		_finderPathWithoutPaginationFindByChildResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByChildResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "parentResourceId"}, true);

		_finderPathCountByChildResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByChildResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "parentResourceId"}, false);

		_finderPathWithPaginationFindByResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResources",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "registrationResourceId"}, true);

		_finderPathWithoutPaginationFindByResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "registrationResourceId"}, true);

		_finderPathCountByResources = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByResources",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "registrationResourceId"}, false);

		RegistrationResourceUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		RegistrationResourceUtil.setPersistence(null);

		entityCache.removeCache(RegistrationResourceImpl.class.getName());
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = Service_builderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REGISTRATIONRESOURCE =
		"SELECT registrationResource FROM RegistrationResource registrationResource";

	private static final String _SQL_SELECT_REGISTRATIONRESOURCE_WHERE =
		"SELECT registrationResource FROM RegistrationResource registrationResource WHERE ";

	private static final String _SQL_COUNT_REGISTRATIONRESOURCE =
		"SELECT COUNT(registrationResource) FROM RegistrationResource registrationResource";

	private static final String _SQL_COUNT_REGISTRATIONRESOURCE_WHERE =
		"SELECT COUNT(registrationResource) FROM RegistrationResource registrationResource WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"registrationResource.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RegistrationResource exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RegistrationResource exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationResourcePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}