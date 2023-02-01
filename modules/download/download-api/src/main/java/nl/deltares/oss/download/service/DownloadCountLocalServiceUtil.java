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
 * Provides the local service utility for DownloadCount. This utility wraps
 * <code>nl.deltares.oss.download.service.impl.DownloadCountLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadCountLocalService
 * @generated
 */
@ProviderType
public class DownloadCountLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.oss.download.service.impl.DownloadCountLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the download count to the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadCount the download count
	 * @return the download count that was added
	 */
	public static nl.deltares.oss.download.model.DownloadCount addDownloadCount(
		nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return getService().addDownloadCount(downloadCount);
	}

	/**
	 * Creates a new download count with the primary key. Does not add the download count to the database.
	 *
	 * @param id the primary key for the new download count
	 * @return the new download count
	 */
	public static nl.deltares.oss.download.model.DownloadCount
		createDownloadCount(long id) {

		return getService().createDownloadCount(id);
	}

	/**
	 * Deletes the download count from the database. Also notifies the appropriate model listeners.
	 *
	 * @param downloadCount the download count
	 * @return the download count that was removed
	 */
	public static nl.deltares.oss.download.model.DownloadCount
		deleteDownloadCount(
			nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return getService().deleteDownloadCount(downloadCount);
	}

	/**
	 * Deletes the download count with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the download count
	 * @return the download count that was removed
	 * @throws PortalException if a download count with the primary key could not be found
	 */
	public static nl.deltares.oss.download.model.DownloadCount
			deleteDownloadCount(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDownloadCount(id);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static nl.deltares.oss.download.model.DownloadCount
		fetchDownloadCount(long id) {

		return getService().fetchDownloadCount(id);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the download count with the primary key.
	 *
	 * @param id the primary key of the download count
	 * @return the download count
	 * @throws PortalException if a download count with the primary key could not be found
	 */
	public static nl.deltares.oss.download.model.DownloadCount getDownloadCount(
			long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDownloadCount(id);
	}

	public static nl.deltares.oss.download.model.DownloadCount getDownloadCount(
		long groupId, long downloadId) {

		return getService().getDownloadCount(groupId, downloadId);
	}

	public static int getDownloadCountByGroupId(long groupId) {
		return getService().getDownloadCountByGroupId(groupId);
	}

	/**
	 * Returns a range of all the download counts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.oss.download.model.impl.DownloadCountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of download counts
	 * @param end the upper bound of the range of download counts (not inclusive)
	 * @return the range of download counts
	 */
	public static java.util.List<nl.deltares.oss.download.model.DownloadCount>
		getDownloadCounts(int start, int end) {

		return getService().getDownloadCounts(start, end);
	}

	/**
	 * Returns the number of download counts.
	 *
	 * @return the number of download counts
	 */
	public static int getDownloadCountsCount() {
		return getService().getDownloadCountsCount();
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
	 * Updates the download count in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param downloadCount the download count
	 * @return the download count that was updated
	 */
	public static nl.deltares.oss.download.model.DownloadCount
		updateDownloadCount(
			nl.deltares.oss.download.model.DownloadCount downloadCount) {

		return getService().updateDownloadCount(downloadCount);
	}

	public static DownloadCountLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DownloadCountLocalService, DownloadCountLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DownloadCountLocalService.class);

		ServiceTracker<DownloadCountLocalService, DownloadCountLocalService>
			serviceTracker =
				new ServiceTracker
					<DownloadCountLocalService, DownloadCountLocalService>(
						bundle.getBundleContext(),
						DownloadCountLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}