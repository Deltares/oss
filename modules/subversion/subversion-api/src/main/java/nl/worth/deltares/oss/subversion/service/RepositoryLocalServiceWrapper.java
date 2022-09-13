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

package nl.worth.deltares.oss.subversion.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RepositoryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalService
 * @generated
 */
public class RepositoryLocalServiceWrapper
	implements RepositoryLocalService, ServiceWrapper<RepositoryLocalService> {

	public RepositoryLocalServiceWrapper(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Adds the repository to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was added
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository addRepository(
		nl.worth.deltares.oss.subversion.model.Repository repository) {

		return _repositoryLocalService.addRepository(repository);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new repository with the primary key. Does not add the repository to the database.
	 *
	 * @param repositoryId the primary key for the new repository
	 * @return the new repository
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository createRepository(
		long repositoryId) {

		return _repositoryLocalService.createRepository(repositoryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository that was removed
	 * @throws PortalException if a repository with the primary key could not be found
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository deleteRepository(
			long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryLocalService.deleteRepository(repositoryId);
	}

	/**
	 * Deletes the repository from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was removed
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository deleteRepository(
		nl.worth.deltares.oss.subversion.model.Repository repository) {

		return _repositoryLocalService.deleteRepository(repository);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _repositoryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _repositoryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _repositoryLocalService.dynamicQuery();
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

		return _repositoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryModelImpl</code>.
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

		return _repositoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryModelImpl</code>.
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

		return _repositoryLocalService.dynamicQuery(
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

		return _repositoryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _repositoryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.worth.deltares.oss.subversion.model.Repository fetchRepository(
		long repositoryId) {

		return _repositoryLocalService.fetchRepository(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _repositoryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _repositoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _repositoryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @return the range of repositories
	 */
	@Override
	public java.util.List<nl.worth.deltares.oss.subversion.model.Repository>
		getRepositories(int start, int end) {

		return _repositoryLocalService.getRepositories(start, end);
	}

	/**
	 * Returns the number of repositories.
	 *
	 * @return the number of repositories
	 */
	@Override
	public int getRepositoriesCount() {
		return _repositoryLocalService.getRepositoriesCount();
	}

	/**
	 * Returns the repository with the primary key.
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository
	 * @throws PortalException if a repository with the primary key could not be found
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository getRepository(
			long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _repositoryLocalService.getRepository(repositoryId);
	}

	/**
	 * Updates the repository in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was updated
	 */
	@Override
	public nl.worth.deltares.oss.subversion.model.Repository updateRepository(
		nl.worth.deltares.oss.subversion.model.Repository repository) {

		return _repositoryLocalService.updateRepository(repository);
	}

	@Override
	public RepositoryLocalService getWrappedService() {
		return _repositoryLocalService;
	}

	@Override
	public void setWrappedService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	private RepositoryLocalService _repositoryLocalService;

}