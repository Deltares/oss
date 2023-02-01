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

package nl.deltares.oss.download.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DownloadCountLocalService}.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCountLocalService
 * @generated
 */
public class DownloadCountLocalServiceWrapper
	implements DownloadCountLocalService,
			   ServiceWrapper<DownloadCountLocalService> {

	public DownloadCountLocalServiceWrapper(
		DownloadCountLocalService downloadCountLocalService) {

		_downloadCountLocalService = downloadCountLocalService;
	}

	/**
	 * Adds the download count to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DownloadCountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param downloadCount the download count
	 * @return the download count that was added
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount addDownloadCount(
		nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return _downloadCountLocalService.addDownloadCount(downloadCount);
	}

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param id the primary key for the new download count
	 * @return the new download count
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount createDownloadCount(
		long id) {

		return _downloadCountLocalService.createDownloadCount(id);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadCountLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the download count from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DownloadCountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param downloadCount the download count
	 * @return the download count that was removed
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount deleteDownloadCount(
		nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return _downloadCountLocalService.deleteDownloadCount(downloadCount);
	}

	/**
	 * Deletes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DownloadCountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param id the primary key of the download count
	 * @return the download count that was removed
	 * @throws PortalException if a download count with the primary key could not be found
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount deleteDownloadCount(
			long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadCountLocalService.deleteDownloadCount(id);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadCountLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _downloadCountLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _downloadCountLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _downloadCountLocalService.dynamicQuery();
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

		return _downloadCountLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>.
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

		return _downloadCountLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>.
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

		return _downloadCountLocalService.dynamicQuery(
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

		return _downloadCountLocalService.dynamicQueryCount(dynamicQuery);
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

		return _downloadCountLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.oss.download.model.DownloadCount fetchDownloadCount(
		long id) {

		return _downloadCountLocalService.fetchDownloadCount(id);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _downloadCountLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the download count with the primary key.
	 *
	 * @param id the primary key of the download count
	 * @return the download count
	 * @throws PortalException if a download count with the primary key could not be found
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount getDownloadCount(
			long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadCountLocalService.getDownloadCount(id);
	}

	@Override
	public nl.deltares.oss.download.model.DownloadCount getDownloadCount(
		long groupId, long downloadId) {

		return _downloadCountLocalService.getDownloadCount(groupId, downloadId);
	}

	@Override
	public int getDownloadCountByGroupId(long groupId) {
		return _downloadCountLocalService.getDownloadCountByGroupId(groupId);
	}

	/**
	 * Returns a range of all the download counts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @return the range of download counts
	 */
	@Override
	public java.util.List<nl.deltares.oss.download.model.DownloadCount>
		getDownloadCounts(int start, int end) {

		return _downloadCountLocalService.getDownloadCounts(start, end);
	}

	/**
	 * Returns the number of download counts.
	 *
	 * @return the number of download counts
	 */
	@Override
	public int getDownloadCountsCount() {
		return _downloadCountLocalService.getDownloadCountsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _downloadCountLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _downloadCountLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadCountLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the download count in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DownloadCountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param downloadCount the download count
	 * @return the download count that was updated
	 */
	@Override
	public nl.deltares.oss.download.model.DownloadCount updateDownloadCount(
		nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return _downloadCountLocalService.updateDownloadCount(downloadCount);
	}

	@Override
	public DownloadCountLocalService getWrappedService() {
		return _downloadCountLocalService;
	}

	@Override
	public void setWrappedService(
		DownloadCountLocalService downloadCountLocalService) {

		_downloadCountLocalService = downloadCountLocalService;
	}

	private DownloadCountLocalService _downloadCountLocalService;

}