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

package nl.worth.deltares.oss.subversion.service.impl;


import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import java.util.ArrayList;
import java.util.List;
import nl.worth.deltares.oss.subversion.constants.PropConstants;
import nl.worth.deltares.oss.subversion.model.Repository;
import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderPermission;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.RepositoryLocalServiceUtil;
import nl.worth.deltares.oss.subversion.service.base.RepositoryLocalServiceBaseImpl;


/**
 * The implementation of the repository local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.worth.deltares.oss.subversion.service.RepositoryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLocalServiceBaseImpl
 */
public class RepositoryLocalServiceImpl extends RepositoryLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>nl.worth.deltares.oss.subversion.service.RepositoryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.worth.deltares.oss.subversion.service.RepositoryLocalServiceUtil</code>.
	 */
	private static Log LOG = LogFactoryUtil.getLog(RepositoryLocalServiceImpl.class);

	public Repository createRepository() {

		Repository repository = null;

		try {
			repository = RepositoryLocalServiceUtil.createRepository(CounterLocalServiceUtil.increment(Repository.class.getName()));
		} catch (SystemException e) {
			LOG.error("Error creating Repository", e);
		}

		return repository;
	}

	public Repository addRepository(Repository newRepository) throws SystemException {

		for (Repository repository : RepositoryLocalServiceUtil.getRepositories(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
			if (repository.getRepositoryName().equals(newRepository.getRepositoryName())) {
				return repository;
			}
		}

		return super.addRepository(newRepository);
	}

	public List<Repository> getRepositories(long groupId) throws SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class)
				.add(PropertyFactoryUtil.forName(PropConstants.GROUP_ID).eq(groupId));

		return RepositoryLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	public List<Repository> getRepositories(String className) throws SystemException {

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(className);

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class)
				.add(PropertyFactoryUtil.forName(PropConstants.CLASS_NAME_ID).eq(className));

		return RepositoryLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	public List<RepositoryFolderPermission> getRepositoryPermissions(long repositoryId) {

		List<RepositoryFolder> repositoryFolders = RepositoryFolderLocalServiceUtil.getRepositoryFolders(repositoryId);

		List<RepositoryFolderPermission> permissions = new ArrayList<>();

		for (RepositoryFolder repositoryFolder : repositoryFolders) {
			permissions.addAll(RepositoryFolderPermissionLocalServiceUtil.getRepositoryFolderPermissions(repositoryFolder.getFolderId()));
		}

		return permissions;
	}
}