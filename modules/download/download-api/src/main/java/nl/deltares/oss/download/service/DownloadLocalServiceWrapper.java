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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DownloadLocalService}.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadLocalService
 * @generated
 */
@ProviderType
public class DownloadLocalServiceWrapper
	implements DownloadLocalService, ServiceWrapper<DownloadLocalService> {

	public DownloadLocalServiceWrapper(
		DownloadLocalService downloadLocalService) {

		_downloadLocalService = downloadLocalService;
	}

	/**
	 * Adds the download to the database. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was added
	 */
	@Override
	public nl.deltares.oss.download.model.Download addDownload(
		nl.deltares.oss.download.model.Download download) {

		return _downloadLocalService.addDownload(download);
	}

	/**
	 * Creates a new download with the primary key. Does not add the download to the database.
	 *
	 * @param downloadPK the primary key for the new download
	 * @return the new download
	 */
	@Override
	public nl.deltares.oss.download.model.Download createDownload(
		nl.deltares.oss.download.service.persistence.DownloadPK downloadPK) {

		return _downloadLocalService.createDownload(downloadPK);
	}

	/**
	 * Deletes the download from the database. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was removed
	 */
	@Override
	public nl.deltares.oss.download.model.Download deleteDownload(
		nl.deltares.oss.download.model.Download download) {

		return _downloadLocalService.deleteDownload(download);
	}

	/**
	 * Deletes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadPK the primary key of the download
	 * @return the download that was removed
	 * @throws PortalException if a download with the primary key could not be found
	 */
	@Override
	public nl.deltares.oss.download.model.Download deleteDownload(
			nl.deltares.oss.download.service.persistence.DownloadPK downloadPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadLocalService.deleteDownload(downloadPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _downloadLocalService.dynamicQuery();
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

		return _downloadLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _downloadLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _downloadLocalService.dynamicQuery(
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

		return _downloadLocalService.dynamicQueryCount(dynamicQuery);
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

		return _downloadLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public nl.deltares.oss.download.model.Download fetchDownload(
		nl.deltares.oss.download.service.persistence.DownloadPK downloadPK) {

		return _downloadLocalService.fetchDownload(downloadPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _downloadLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the download with the primary key.
	 *
	 * @param downloadPK the primary key of the download
	 * @return the download
	 * @throws PortalException if a download with the primary key could not be found
	 */
	@Override
	public nl.deltares.oss.download.model.Download getDownload(
			nl.deltares.oss.download.service.persistence.DownloadPK downloadPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadLocalService.getDownload(downloadPK);
	}

	/**
	 * Returns a range of all the downloads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of downloads
	 * @param end the upper bound of the range of downloads (not inclusive)
	 * @return the range of downloads
	 */
	@Override
	public java.util.List<nl.deltares.oss.download.model.Download> getDownloads(
		int start, int end) {

		return _downloadLocalService.getDownloads(start, end);
	}

	/**
	 * Returns the number of downloads.
	 *
	 * @return the number of downloads
	 */
	@Override
	public int getDownloadsCount() {
		return _downloadLocalService.getDownloadsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _downloadLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _downloadLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<nl.deltares.oss.download.model.Download>
		getPendingUserDownloads(long userId) {

		return _downloadLocalService.getPendingUserDownloads(userId);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _downloadLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the download in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was updated
	 */
	@Override
	public nl.deltares.oss.download.model.Download updateDownload(
		nl.deltares.oss.download.model.Download download) {

		return _downloadLocalService.updateDownload(download);
	}

	@Override
	public DownloadLocalService getWrappedService() {
		return _downloadLocalService;
	}

	@Override
	public void setWrappedService(DownloadLocalService downloadLocalService) {
		_downloadLocalService = downloadLocalService;
	}

	private DownloadLocalService _downloadLocalService;

}