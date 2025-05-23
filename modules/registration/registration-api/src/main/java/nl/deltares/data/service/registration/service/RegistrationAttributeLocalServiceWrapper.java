/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link RegistrationAttributeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationAttributeLocalService
 * @generated
 */
public class RegistrationAttributeLocalServiceWrapper
	implements RegistrationAttributeLocalService,
			   ServiceWrapper<RegistrationAttributeLocalService> {

	public RegistrationAttributeLocalServiceWrapper() {
		this(null);
	}

	public RegistrationAttributeLocalServiceWrapper(
		RegistrationAttributeLocalService registrationAttributeLocalService) {

		_registrationAttributeLocalService = registrationAttributeLocalService;
	}

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
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
		addRegistrationAttribute(
			nl.deltares.data.service.registration.model.RegistrationAttribute
				registrationAttribute) {

		return _registrationAttributeLocalService.addRegistrationAttribute(
			registrationAttribute);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationAttributeLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new registration attribute with the primary key. Does not add the registration attribute to the database.
	 *
	 * @param registrationAttributeId the primary key for the new registration attribute
	 * @return the new registration attribute
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
		createRegistrationAttribute(long registrationAttributeId) {

		return _registrationAttributeLocalService.createRegistrationAttribute(
			registrationAttributeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationAttributeLocalService.deletePersistedModel(
			persistedModel);
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
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
			deleteRegistrationAttribute(long registrationAttributeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationAttributeLocalService.deleteRegistrationAttribute(
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
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
		deleteRegistrationAttribute(
			nl.deltares.data.service.registration.model.RegistrationAttribute
				registrationAttribute) {

		return _registrationAttributeLocalService.deleteRegistrationAttribute(
			registrationAttribute);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _registrationAttributeLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _registrationAttributeLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _registrationAttributeLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _registrationAttributeLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _registrationAttributeLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _registrationAttributeLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _registrationAttributeLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _registrationAttributeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
		fetchRegistrationAttribute(long registrationAttributeId) {

		return _registrationAttributeLocalService.fetchRegistrationAttribute(
			registrationAttributeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _registrationAttributeLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _registrationAttributeLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _registrationAttributeLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationAttributeLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the registration attribute with the primary key.
	 *
	 * @param registrationAttributeId the primary key of the registration attribute
	 * @return the registration attribute
	 * @throws PortalException if a registration attribute with the primary key could not be found
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
			getRegistrationAttribute(long registrationAttributeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationAttributeLocalService.getRegistrationAttribute(
			registrationAttributeId);
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
	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationAttribute>
			getRegistrationAttributes(int start, int end) {

		return _registrationAttributeLocalService.getRegistrationAttributes(
			start, end);
	}

	/**
	 * Returns the number of registration attributes.
	 *
	 * @return the number of registration attributes
	 */
	@Override
	public int getRegistrationAttributesCount() {
		return _registrationAttributeLocalService.
			getRegistrationAttributesCount();
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
	@Override
	public nl.deltares.data.service.registration.model.RegistrationAttribute
		updateRegistrationAttribute(
			nl.deltares.data.service.registration.model.RegistrationAttribute
				registrationAttribute) {

		return _registrationAttributeLocalService.updateRegistrationAttribute(
			registrationAttribute);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _registrationAttributeLocalService.getBasePersistence();
	}

	@Override
	public RegistrationAttributeLocalService getWrappedService() {
		return _registrationAttributeLocalService;
	}

	@Override
	public void setWrappedService(
		RegistrationAttributeLocalService registrationAttributeLocalService) {

		_registrationAttributeLocalService = registrationAttributeLocalService;
	}

	private RegistrationAttributeLocalService
		_registrationAttributeLocalService;

}