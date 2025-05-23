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

import nl.deltares.data.service.registration.exception.NoSuchRegistrationAttributeException;
import nl.deltares.data.service.registration.model.RegistrationAttribute;
import nl.deltares.data.service.registration.model.RegistrationAttributeTable;
import nl.deltares.data.service.registration.model.impl.RegistrationAttributeImpl;
import nl.deltares.data.service.registration.model.impl.RegistrationAttributeModelImpl;
import nl.deltares.data.service.registration.service.persistence.RegistrationAttributePersistence;
import nl.deltares.data.service.registration.service.persistence.RegistrationAttributeUtil;
import nl.deltares.data.service.registration.service.persistence.impl.constants.Service_builderPersistenceConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the registration attribute service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RegistrationAttributePersistence.class)
public class RegistrationAttributePersistenceImpl
	extends BasePersistenceImpl<RegistrationAttribute>
	implements RegistrationAttributePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegistrationAttributeUtil</code> to access the registration attribute persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegistrationAttributeImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRegistrationAttribute;
	private FinderPath _finderPathWithoutPaginationFindByRegistrationAttribute;
	private FinderPath _finderPathCountByRegistrationAttribute;

	/**
	 * Returns all the registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the matching registration attributes
	 */
	@Override
	public List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId) {

		return findByRegistrationAttribute(
			registrationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end) {

		return findByRegistrationAttribute(registrationId, start, end, null);
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
	@Override
	public List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return findByRegistrationAttribute(
			registrationId, start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationAttribute> findByRegistrationAttribute(
		long registrationId, int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByRegistrationAttribute;
				finderArgs = new Object[] {registrationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRegistrationAttribute;
			finderArgs = new Object[] {
				registrationId, start, end, orderByComparator
			};
		}

		List<RegistrationAttribute> list = null;

		if (useFinderCache) {
			list = (List<RegistrationAttribute>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RegistrationAttribute registrationAttribute : list) {
					if (registrationId !=
							registrationAttribute.getRegistrationId()) {

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
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_REGISTRATIONATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_REGISTRATIONATTRIBUTE_REGISTRATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RegistrationAttributeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(registrationId);

				list = (List<RegistrationAttribute>)QueryUtil.list(
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
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	@Override
	public RegistrationAttribute findByRegistrationAttribute_First(
			long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException {

		RegistrationAttribute registrationAttribute =
			fetchByRegistrationAttribute_First(
				registrationId, orderByComparator);

		if (registrationAttribute != null) {
			return registrationAttribute;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationId=");
		sb.append(registrationId);

		sb.append("}");

		throw new NoSuchRegistrationAttributeException(sb.toString());
	}

	/**
	 * Returns the first registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	@Override
	public RegistrationAttribute fetchByRegistrationAttribute_First(
		long registrationId,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		List<RegistrationAttribute> list = findByRegistrationAttribute(
			registrationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute
	 * @throws NoSuchRegistrationAttributeException if a matching registration attribute could not be found
	 */
	@Override
	public RegistrationAttribute findByRegistrationAttribute_Last(
			long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException {

		RegistrationAttribute registrationAttribute =
			fetchByRegistrationAttribute_Last(
				registrationId, orderByComparator);

		if (registrationAttribute != null) {
			return registrationAttribute;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("registrationId=");
		sb.append(registrationId);

		sb.append("}");

		throw new NoSuchRegistrationAttributeException(sb.toString());
	}

	/**
	 * Returns the last registration attribute in the ordered set where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration attribute, or <code>null</code> if a matching registration attribute could not be found
	 */
	@Override
	public RegistrationAttribute fetchByRegistrationAttribute_Last(
		long registrationId,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		int count = countByRegistrationAttribute(registrationId);

		if (count == 0) {
			return null;
		}

		List<RegistrationAttribute> list = findByRegistrationAttribute(
			registrationId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public RegistrationAttribute[] findByRegistrationAttribute_PrevAndNext(
			long registrationAttributeId, long registrationId,
			OrderByComparator<RegistrationAttribute> orderByComparator)
		throws NoSuchRegistrationAttributeException {

		RegistrationAttribute registrationAttribute = findByPrimaryKey(
			registrationAttributeId);

		Session session = null;

		try {
			session = openSession();

			RegistrationAttribute[] array = new RegistrationAttributeImpl[3];

			array[0] = getByRegistrationAttribute_PrevAndNext(
				session, registrationAttribute, registrationId,
				orderByComparator, true);

			array[1] = registrationAttribute;

			array[2] = getByRegistrationAttribute_PrevAndNext(
				session, registrationAttribute, registrationId,
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

	protected RegistrationAttribute getByRegistrationAttribute_PrevAndNext(
		Session session, RegistrationAttribute registrationAttribute,
		long registrationId,
		OrderByComparator<RegistrationAttribute> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_REGISTRATIONATTRIBUTE_WHERE);

		sb.append(_FINDER_COLUMN_REGISTRATIONATTRIBUTE_REGISTRATIONID_2);

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
			sb.append(RegistrationAttributeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(registrationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						registrationAttribute)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RegistrationAttribute> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registration attributes where registrationId = &#63; from the database.
	 *
	 * @param registrationId the registration ID
	 */
	@Override
	public void removeByRegistrationAttribute(long registrationId) {
		for (RegistrationAttribute registrationAttribute :
				findByRegistrationAttribute(
					registrationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registrationAttribute);
		}
	}

	/**
	 * Returns the number of registration attributes where registrationId = &#63;.
	 *
	 * @param registrationId the registration ID
	 * @return the number of matching registration attributes
	 */
	@Override
	public int countByRegistrationAttribute(long registrationId) {
		FinderPath finderPath = _finderPathCountByRegistrationAttribute;

		Object[] finderArgs = new Object[] {registrationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REGISTRATIONATTRIBUTE_WHERE);

			sb.append(_FINDER_COLUMN_REGISTRATIONATTRIBUTE_REGISTRATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(registrationId);

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

	private static final String
		_FINDER_COLUMN_REGISTRATIONATTRIBUTE_REGISTRATIONID_2 =
			"registrationAttribute.registrationId = ?";

	public RegistrationAttributePersistenceImpl() {
		setModelClass(RegistrationAttribute.class);

		setModelImplClass(RegistrationAttributeImpl.class);
		setModelPKClass(long.class);

		setTable(RegistrationAttributeTable.INSTANCE);
	}

	/**
	 * Caches the registration attribute in the entity cache if it is enabled.
	 *
	 * @param registrationAttribute the registration attribute
	 */
	@Override
	public void cacheResult(RegistrationAttribute registrationAttribute) {
		entityCache.putResult(
			RegistrationAttributeImpl.class,
			registrationAttribute.getPrimaryKey(), registrationAttribute);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the registration attributes in the entity cache if it is enabled.
	 *
	 * @param registrationAttributes the registration attributes
	 */
	@Override
	public void cacheResult(
		List<RegistrationAttribute> registrationAttributes) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (registrationAttributes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RegistrationAttribute registrationAttribute :
				registrationAttributes) {

			if (entityCache.getResult(
					RegistrationAttributeImpl.class,
					registrationAttribute.getPrimaryKey()) == null) {

				cacheResult(registrationAttribute);
			}
		}
	}

	/**
	 * Clears the cache for all registration attributes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RegistrationAttributeImpl.class);

		finderCache.clearCache(RegistrationAttributeImpl.class);
	}

	/**
	 * Clears the cache for the registration attribute.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RegistrationAttribute registrationAttribute) {
		entityCache.removeResult(
			RegistrationAttributeImpl.class, registrationAttribute);
	}

	@Override
	public void clearCache(List<RegistrationAttribute> registrationAttributes) {
		for (RegistrationAttribute registrationAttribute :
				registrationAttributes) {

			entityCache.removeResult(
				RegistrationAttributeImpl.class, registrationAttribute);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RegistrationAttributeImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RegistrationAttributeImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new registration attribute with the primary key. Does not add the registration attribute to the database.
	 *
	 * @param registrationAttributeId the primary key for the new registration attribute
	 * @return the new registration attribute
	 */
	@Override
	public RegistrationAttribute create(long registrationAttributeId) {
		RegistrationAttribute registrationAttribute =
			new RegistrationAttributeImpl();

		registrationAttribute.setNew(true);
		registrationAttribute.setPrimaryKey(registrationAttributeId);

		return registrationAttribute;
	}

	/**
	 * Removes the registration attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute that was removed
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	@Override
	public RegistrationAttribute remove(long registrationAttributeId)
		throws NoSuchRegistrationAttributeException {

		return remove((Serializable)registrationAttributeId);
	}

	/**
	 * Removes the registration attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the registration attribute
	 * @return the registration attribute that was removed
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	@Override
	public RegistrationAttribute remove(Serializable primaryKey)
		throws NoSuchRegistrationAttributeException {

		Session session = null;

		try {
			session = openSession();

			RegistrationAttribute registrationAttribute =
				(RegistrationAttribute)session.get(
					RegistrationAttributeImpl.class, primaryKey);

			if (registrationAttribute == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegistrationAttributeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(registrationAttribute);
		}
		catch (NoSuchRegistrationAttributeException noSuchEntityException) {
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
	protected RegistrationAttribute removeImpl(
		RegistrationAttribute registrationAttribute) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(registrationAttribute)) {
				registrationAttribute = (RegistrationAttribute)session.get(
					RegistrationAttributeImpl.class,
					registrationAttribute.getPrimaryKeyObj());
			}

			if (registrationAttribute != null) {
				session.delete(registrationAttribute);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (registrationAttribute != null) {
			clearCache(registrationAttribute);
		}

		return registrationAttribute;
	}

	@Override
	public RegistrationAttribute updateImpl(
		RegistrationAttribute registrationAttribute) {

		boolean isNew = registrationAttribute.isNew();

		if (!(registrationAttribute instanceof
				RegistrationAttributeModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(registrationAttribute.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					registrationAttribute);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in registrationAttribute proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RegistrationAttribute implementation " +
					registrationAttribute.getClass());
		}

		RegistrationAttributeModelImpl registrationAttributeModelImpl =
			(RegistrationAttributeModelImpl)registrationAttribute;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(registrationAttribute);
			}
			else {
				registrationAttribute = (RegistrationAttribute)session.merge(
					registrationAttribute);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RegistrationAttributeImpl.class, registrationAttributeModelImpl,
			false, true);

		if (isNew) {
			registrationAttribute.setNew(false);
		}

		registrationAttribute.resetOriginalValues();

		return registrationAttribute;
	}

	/**
	 * Returns the registration attribute with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	@Override
	public RegistrationAttribute findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegistrationAttributeException {

		RegistrationAttribute registrationAttribute = fetchByPrimaryKey(
			primaryKey);

		if (registrationAttribute == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegistrationAttributeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return registrationAttribute;
	}

	/**
	 * Returns the registration attribute with the primary key or throws a <code>NoSuchRegistrationAttributeException</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws NoSuchRegistrationAttributeException if a registration attribute with the primary key could not be found
	 */
	@Override
	public RegistrationAttribute findByPrimaryKey(long registrationAttributeId)
		throws NoSuchRegistrationAttributeException {

		return findByPrimaryKey((Serializable)registrationAttributeId);
	}

	/**
	 * Returns the registration attribute with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute, or <code>null</code> if a registration attribute with the primary key could not be found
	 */
	@Override
	public RegistrationAttribute fetchByPrimaryKey(
		long registrationAttributeId) {

		return fetchByPrimaryKey((Serializable)registrationAttributeId);
	}

	/**
	 * Returns all the registration attributes.
	 *
	 * @return the registration attributes
	 */
	@Override
	public List<RegistrationAttribute> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<RegistrationAttribute> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<RegistrationAttribute> findAll(
		int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<RegistrationAttribute> findAll(
		int start, int end,
		OrderByComparator<RegistrationAttribute> orderByComparator,
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

		List<RegistrationAttribute> list = null;

		if (useFinderCache) {
			list = (List<RegistrationAttribute>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REGISTRATIONATTRIBUTE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATIONATTRIBUTE;

				sql = sql.concat(RegistrationAttributeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RegistrationAttribute>)QueryUtil.list(
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
	 * Removes all the registration attributes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RegistrationAttribute registrationAttribute : findAll()) {
			remove(registrationAttribute);
		}
	}

	/**
	 * Returns the number of registration attributes.
	 *
	 * @return the number of registration attributes
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
					_SQL_COUNT_REGISTRATIONATTRIBUTE);

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
		return "registrationAttributeId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REGISTRATIONATTRIBUTE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegistrationAttributeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration attribute persistence.
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

		_finderPathWithPaginationFindByRegistrationAttribute = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRegistrationAttribute",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"registrationId"}, true);

		_finderPathWithoutPaginationFindByRegistrationAttribute =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByRegistrationAttribute",
				new String[] {Long.class.getName()},
				new String[] {"registrationId"}, true);

		_finderPathCountByRegistrationAttribute = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRegistrationAttribute", new String[] {Long.class.getName()},
			new String[] {"registrationId"}, false);

		RegistrationAttributeUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		RegistrationAttributeUtil.setPersistence(null);

		entityCache.removeCache(RegistrationAttributeImpl.class.getName());
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

	private static final String _SQL_SELECT_REGISTRATIONATTRIBUTE =
		"SELECT registrationAttribute FROM RegistrationAttribute registrationAttribute";

	private static final String _SQL_SELECT_REGISTRATIONATTRIBUTE_WHERE =
		"SELECT registrationAttribute FROM RegistrationAttribute registrationAttribute WHERE ";

	private static final String _SQL_COUNT_REGISTRATIONATTRIBUTE =
		"SELECT COUNT(registrationAttribute) FROM RegistrationAttribute registrationAttribute";

	private static final String _SQL_COUNT_REGISTRATIONATTRIBUTE_WHERE =
		"SELECT COUNT(registrationAttribute) FROM RegistrationAttribute registrationAttribute WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"registrationAttribute.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RegistrationAttribute exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RegistrationAttribute exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationAttributePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}