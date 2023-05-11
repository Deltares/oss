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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Download. This utility wraps
 * <code>nl.deltares.oss.download.service.impl.DownloadLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadLocalService
 * @generated
 */
@ProviderType
public class DownloadLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.oss.download.service.impl.DownloadLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the download to the database. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was added
	 */
	public static nl.deltares.oss.download.model.Download addDownload(
		nl.deltares.oss.download.model.Download download) {

		return getService().addDownload(download);
	}

	public static int countDirectDownloads(long groupId) {
		return getService().countDirectDownloads(groupId);
	}

	public static int countDownloads(long groupId) {
		return getService().countDownloads(groupId);
	}

	public static int countDownloadsByArticleId(long groupId, long articleId) {
		return getService().countDownloadsByArticleId(groupId, articleId);
	}

	public static int countDownloadsByShareId(long groupId, int shareId) {
		return getService().countDownloadsByShareId(groupId, shareId);
	}

	public static int countDownloadsByUserId(long groupId, long userId) {
		return getService().countDownloadsByUserId(groupId, userId);
	}

	/**
	 * Creates a new download with the primary key. Does not add the download to the database.
	 *
	 * @param id the primary key for the new download
	 * @return the new download
	 */
	public static nl.deltares.oss.download.model.Download createDownload(
		long id) {

		return getService().createDownload(id);
	}

	/**
	 * Deletes the download from the database. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was removed
	 */
	public static nl.deltares.oss.download.model.Download deleteDownload(
		nl.deltares.oss.download.model.Download download) {

		return getService().deleteDownload(download);
	}

	/**
	 * Deletes the download with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download
	 * @return the download that was removed
	 * @throws PortalException if a download with the primary key could not be found
	 */
	public static nl.deltares.oss.download.model.Download deleteDownload(
			long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDownload(id);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static nl.deltares.oss.download.model.Download fetchDownload(
		long id) {

		return getService().fetchDownload(id);
	}

	public static nl.deltares.oss.download.model.Download fetchUserDownload(
		long groupId, long userId, long downloadId) {

		return getService().fetchUserDownload(groupId, userId, downloadId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDirectDownloads(long groupId) {

		return getService().findDirectDownloads(groupId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDirectDownloads(long groupId, int start, int end) {

		return getService().findDirectDownloads(groupId, start, end);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloads(long groupId) {

		return getService().findDownloads(groupId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloads(long groupId, int start, int end) {

		return getService().findDownloads(groupId, start, end);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByArticleId(long groupId, long articleId) {

		return getService().findDownloadsByArticleId(groupId, articleId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByArticleId(
			long groupId, long articleId, int start, int end) {

		return getService().findDownloadsByArticleId(
			groupId, articleId, start, end);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByShareId(long groupId, int shareId) {

		return getService().findDownloadsByShareId(groupId, shareId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByShareId(long groupId, int shareId, int start, int end) {

		return getService().findDownloadsByShareId(
			groupId, shareId, start, end);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByUserId(long groupId, long userId) {

		return getService().findDownloadsByUserId(groupId, userId);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findDownloadsByUserId(long groupId, long userId, int start, int end) {

		return getService().findDownloadsByUserId(groupId, userId, start, end);
	}

	public static java.util.List<nl.deltares.oss.download.model.Download>
		findUserDownloadsByShareId(long groupId, long userId, int shareId) {

		return getService().findUserDownloadsByShareId(
			groupId, userId, shareId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the download with the primary key.
	 *
	 * @param id the primary key of the download
	 * @return the download
	 * @throws PortalException if a download with the primary key could not be found
	 */
	public static nl.deltares.oss.download.model.Download getDownload(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDownload(id);
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
	public static java.util.List<nl.deltares.oss.download.model.Download>
		getDownloads(int start, int end) {

		return getService().getDownloads(start, end);
	}

	/**
	 * Returns the number of downloads.
	 *
	 * @return the number of downloads
	 */
	public static int getDownloadsCount() {
		return getService().getDownloadsCount();
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the download in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param download the download
	 * @return the download that was updated
	 */
	public static nl.deltares.oss.download.model.Download updateDownload(
		nl.deltares.oss.download.model.Download download) {

		return getService().updateDownload(download);
	}

	public static DownloadLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DownloadLocalService, DownloadLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DownloadLocalService.class);

		ServiceTracker<DownloadLocalService, DownloadLocalService>
			serviceTracker =
				new ServiceTracker<DownloadLocalService, DownloadLocalService>(
					bundle.getBundleContext(), DownloadLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}