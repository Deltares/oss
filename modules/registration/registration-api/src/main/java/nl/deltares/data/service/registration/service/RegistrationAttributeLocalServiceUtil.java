/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.deltares.data.service.registration.model.RegistrationAttribute;

/**
 * Provides the local service utility for RegistrationAttribute. This utility wraps
 * <code>nl.deltares.data.service.registration.service.impl.RegistrationAttributeLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttributeLocalService
 * @generated
 */
public class RegistrationAttributeLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.data.service.registration.service.impl.RegistrationAttributeLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the registration attribute to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationAttributeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationAttribute the registration attribute
	 * @return the registration attribute that was added
	 */
	public static RegistrationAttribute addRegistrationAttribute(
		RegistrationAttribute registrationAttribute) {

		return getService().addRegistrationAttribute(registrationAttribute);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new registration attribute with the primary key. Does not add the registration attribute to the database.
	 *
	 * @param registrationAttributeId the primary key for the new registration attribute
	 * @return the new registration attribute
	 */
	public static RegistrationAttribute createRegistrationAttribute(
		long registrationAttributeId) {

		return getService().createRegistrationAttribute(
			registrationAttributeId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the registration attribute with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationAttributeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute that was removed
	 * @throws PortalException if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute deleteRegistrationAttribute(
			long registrationAttributeId)
		throws PortalException {

		return getService().deleteRegistrationAttribute(
			registrationAttributeId);
	}

	/**
	 * Deletes the registration attribute from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationAttributeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationAttribute the registration attribute
	 * @return the registration attribute that was removed
	 */
	public static RegistrationAttribute deleteRegistrationAttribute(
		RegistrationAttribute registrationAttribute) {

		return getService().deleteRegistrationAttribute(registrationAttribute);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static RegistrationAttribute fetchRegistrationAttribute(
		long registrationAttributeId) {

		return getService().fetchRegistrationAttribute(registrationAttributeId);
	}

	public static List<RegistrationAttribute> findByRegistration(
		long registrationId) {

		return getService().findByRegistration(registrationId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration attribute with the primary key.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws PortalException if a registration attribute with the primary key could not be found
	 */
	public static RegistrationAttribute getRegistrationAttribute(
			long registrationAttributeId)
		throws PortalException {

		return getService().getRegistrationAttribute(registrationAttributeId);
	}

	/**
	 * Returns a range of all the registration attributes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationAttributeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration attributes
	 * @param end the upper bound of the range of registration attributes (not inclusive)
	 * @return the range of registration attributes
	 */
	public static List<RegistrationAttribute> getRegistrationAttributes(
		int start, int end) {

		return getService().getRegistrationAttributes(start, end);
	}

	/**
	 * Returns the number of registration attributes.
	 *
	 * @return the number of registration attributes
	 */
	public static int getRegistrationAttributesCount() {
		return getService().getRegistrationAttributesCount();
	}

	public static void removeByRegistration(long registrationId) {
		getService().removeByRegistration(registrationId);
	}

	/**
	 * Updates the registration attribute in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationAttributeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationAttribute the registration attribute
	 * @return the registration attribute that was updated
	 */
	public static RegistrationAttribute updateRegistrationAttribute(
		RegistrationAttribute registrationAttribute) {

		return getService().updateRegistrationAttribute(registrationAttribute);
	}

	public static RegistrationAttributeLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<RegistrationAttributeLocalService>
		_serviceSnapshot = new Snapshot<>(
			RegistrationAttributeLocalServiceUtil.class,
			RegistrationAttributeLocalService.class);

}