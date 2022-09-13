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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import nl.worth.deltares.oss.subversion.model.RepositoryFolder;

/**
 * Provides the local service utility for RepositoryFolder. This utility wraps
 * <code>nl.worth.deltares.oss.subversion.service.impl.RepositoryFolderLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderLocalService
 * @generated
 */
public class RepositoryFolderLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.worth.deltares.oss.subversion.service.impl.RepositoryFolderLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static RepositoryFolder addRepositoryFolder(
		long repositoryId, String name) {

		return getService().addRepositoryFolder(repositoryId, name);
	}

	/**
	 * Adds the repository folder to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolder the repository folder
	 * @return the repository folder that was added
	 * @throws SystemException
	 */
	public static RepositoryFolder addRepositoryFolder(
			RepositoryFolder repositoryFolder)
		throws SystemException {

		return getService().addRepositoryFolder(repositoryFolder);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static RepositoryFolder createRepositoryFolder() {
		return getService().createRepositoryFolder();
	}

	/**
	 * Creates a new repository folder with the primary key. Does not add the repository folder to the database.
	 *
	 * @param folderId the primary key for the new repository folder
	 * @return the new repository folder
	 */
	public static RepositoryFolder createRepositoryFolder(long folderId) {
		return getService().createRepositoryFolder(folderId);
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
	 * Deletes the repository folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder that was removed
	 * @throws PortalException if a repository folder with the primary key could not be found
	 */
	public static RepositoryFolder deleteRepositoryFolder(long folderId)
		throws PortalException {

		return getService().deleteRepositoryFolder(folderId);
	}

	/**
	 * Deletes the repository folder from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolder the repository folder
	 * @return the repository folder that was removed
	 * @throws SystemException
	 */
	public static RepositoryFolder deleteRepositoryFolder(
			RepositoryFolder repositoryFolder)
		throws SystemException {

		return getService().deleteRepositoryFolder(repositoryFolder);
	}

	public static void deleteRepositoryFolders(List<RepositoryFolder> folders) {
		getService().deleteRepositoryFolders(folders);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderModelImpl</code>.
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

	public static RepositoryFolder fetchRepositoryFolder(long folderId) {
		return getService().fetchRepositoryFolder(folderId);
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
	 * Returns the repository folder with the primary key.
	 *
	 * @param folderId the primary key of the repository folder
	 * @return the repository folder
	 * @throws PortalException if a repository folder with the primary key could not be found
	 */
	public static RepositoryFolder getRepositoryFolder(long folderId)
		throws PortalException {

		return getService().getRepositoryFolder(folderId);
	}

	public static RepositoryFolder getRepositoryFolder(
		long repositoryId, String name) {

		return getService().getRepositoryFolder(repositoryId, name);
	}

	public static List<RepositoryFolder> getRepositoryFolderChildren(
		RepositoryFolder repositoryFolder) {

		return getService().getRepositoryFolderChildren(repositoryFolder);
	}

	public static RepositoryFolder getRepositoryFolderParent(
		long repositoryId, String folderName) {

		return getService().getRepositoryFolderParent(repositoryId, folderName);
	}

	/**
	 * Returns a range of all the repository folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>nl.worth.deltares.oss.subversion.model.impl.RepositoryFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository folders
	 * @param end the upper bound of the range of repository folders (not inclusive)
	 * @return the range of repository folders
	 */
	public static List<RepositoryFolder> getRepositoryFolders(
		int start, int end) {

		return getService().getRepositoryFolders(start, end);
	}

	public static List<RepositoryFolder> getRepositoryFolders(
		long repositoryId) {

		return getService().getRepositoryFolders(repositoryId);
	}

	@Deprecated
	public static List<RepositoryFolder> getRepositoryFolders(
		long repositoryId, String name) {

		return getService().getRepositoryFolders(repositoryId, name);
	}

	/**
	 * Returns the number of repository folders.
	 *
	 * @return the number of repository folders
	 */
	public static int getRepositoryFoldersCount() {
		return getService().getRepositoryFoldersCount();
	}

	/**
	 * Updates the repository folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryFolder the repository folder
	 * @return the repository folder that was updated
	 */
	public static RepositoryFolder updateRepositoryFolder(
		RepositoryFolder repositoryFolder) {

		return getService().updateRepositoryFolder(repositoryFolder);
	}

	public static RepositoryFolderLocalService getService() {
		return _service;
	}

	private static volatile RepositoryFolderLocalService _service;

}