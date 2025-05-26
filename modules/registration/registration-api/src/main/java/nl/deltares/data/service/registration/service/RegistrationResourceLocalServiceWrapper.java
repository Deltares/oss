/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package nl.deltares.data.service.registration.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link RegistrationResourceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RegistrationResourceLocalService
 * @generated
 */
public class RegistrationResourceLocalServiceWrapper
	implements RegistrationResourceLocalService,
			   ServiceWrapper<RegistrationResourceLocalService> {

	public RegistrationResourceLocalServiceWrapper() {
		this(null);
	}

	public RegistrationResourceLocalServiceWrapper(
		RegistrationResourceLocalService registrationResourceLocalService) {

		_registrationResourceLocalService = registrationResourceLocalService;
	}

	/**
	 * Adds the registration resource to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationResourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationResource the registration resource
	 * @return the registration resource that was added
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
		addRegistrationResource(
			nl.deltares.data.service.registration.model.RegistrationResource
				registrationResource) {

		return _registrationResourceLocalService.addRegistrationResource(
			registrationResource);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationResourceLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new registration resource with the primary key. Does not add the registration resource to the database.
	 *
	 * @param registrationResourceId the primary key for the new registration resource
	 * @return the new registration resource
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
		createRegistrationResource(long registrationResourceId) {

		return _registrationResourceLocalService.createRegistrationResource(
			registrationResourceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationResourceLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the registration resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationResourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource that was removed
	 * @throws PortalException if a registration resource with the primary key could not be found
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
			deleteRegistrationResource(long registrationResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationResourceLocalService.deleteRegistrationResource(
			registrationResourceId);
	}

	/**
	 * Deletes the registration resource from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationResourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationResource the registration resource
	 * @return the registration resource that was removed
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
		deleteRegistrationResource(
			nl.deltares.data.service.registration.model.RegistrationResource
				registrationResource) {

		return _registrationResourceLocalService.deleteRegistrationResource(
			registrationResource);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _registrationResourceLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _registrationResourceLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _registrationResourceLocalService.dynamicQuery();
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

		return _registrationResourceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationResourceModelImpl</code>.
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

		return _registrationResourceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationResourceModelImpl</code>.
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

		return _registrationResourceLocalService.dynamicQuery(
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

		return _registrationResourceLocalService.dynamicQueryCount(
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

		return _registrationResourceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
		fetchRegistrationResource(long registrationResourceId) {

		return _registrationResourceLocalService.fetchRegistrationResource(
			registrationResourceId);
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationResource>
			findByGroupAndEventArticle(long groupId, long eventResourceId) {

		return _registrationResourceLocalService.findByGroupAndEventArticle(
			groupId, eventResourceId);
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationResource>
			findByGroupAndEventResource(long groupId, long eventResourceId) {

		return _registrationResourceLocalService.findByGroupAndEventResource(
			groupId, eventResourceId);
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationResource>
			findByGroupAndParentResource(long groupId, long parentResourceId) {

		return _registrationResourceLocalService.findByGroupAndParentResource(
			groupId, parentResourceId);
	}

	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationResource>
			findByGroupAndResource(long groupId, long resourceId) {

		return _registrationResourceLocalService.findByGroupAndResource(
			groupId, resourceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _registrationResourceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _registrationResourceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _registrationResourceLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationResourceLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the registration resource with the primary key.
	 *
	 * @param registrationResourceId the primary key of the registration resource
	 * @return the registration resource
	 * @throws PortalException if a registration resource with the primary key could not be found
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
			getRegistrationResource(long registrationResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _registrationResourceLocalService.getRegistrationResource(
			registrationResourceId);
	}

	/**
	 * Returns a range of all the registration resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.data.service.registration.model.impl.RegistrationResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of registration resources
	 * @param end the upper bound of the range of registration resources (not inclusive)
	 * @return the range of registration resources
	 */
	@Override
	public java.util.List
		<nl.deltares.data.service.registration.model.RegistrationResource>
			getRegistrationResources(int start, int end) {

		return _registrationResourceLocalService.getRegistrationResources(
			start, end);
	}

	/**
	 * Returns the number of registration resources.
	 *
	 * @return the number of registration resources
	 */
	@Override
	public int getRegistrationResourcesCount() {
		return _registrationResourceLocalService.
			getRegistrationResourcesCount();
	}

	/**
	 * Updates the registration resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegistrationResourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param registrationResource the registration resource
	 * @return the registration resource that was updated
	 */
	@Override
	public nl.deltares.data.service.registration.model.RegistrationResource
		updateRegistrationResource(
			nl.deltares.data.service.registration.model.RegistrationResource
				registrationResource) {

		return _registrationResourceLocalService.updateRegistrationResource(
			registrationResource);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _registrationResourceLocalService.getBasePersistence();
	}

	@Override
	public RegistrationResourceLocalService getWrappedService() {
		return _registrationResourceLocalService;
	}

	@Override
	public void setWrappedService(
		RegistrationResourceLocalService registrationResourceLocalService) {

		_registrationResourceLocalService = registrationResourceLocalService;
	}

	private RegistrationResourceLocalService _registrationResourceLocalService;

}