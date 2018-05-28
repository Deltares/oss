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

package com.worth.deltares.subversion.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.worth.deltares.subversion.model.Repository;
import com.worth.deltares.subversion.model.RepositoryFolder;
import com.worth.deltares.subversion.model.RepositoryFolderPermission;
import com.worth.deltares.subversion.service.RepositoryFolderLocalServiceUtil;
import com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import com.worth.deltares.subversion.service.RepositoryLocalServiceUtil;
import com.worth.deltares.subversion.service.base.RepositoryLocalServiceBaseImpl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;


/**
 * The implementation of the repository local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.service.RepositoryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryLocalServiceBaseImpl
 * @see com.worth.deltares.subversion.service.RepositoryLocalServiceUtil
 */
public class RepositoryLocalServiceImpl extends RepositoryLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.worth.deltares.subversion.service.RepositoryLocalServiceUtil} to access the repository local service.
	 */

  private static Log _log = LogFactoryUtil.getLog(RepositoryLocalServiceImpl.class);

	public Repository createRepository() {

		Repository repository = null;
		
		try {
			repository = RepositoryLocalServiceUtil.createRepository(
					CounterLocalServiceUtil.increment(Repository.class.getName()));
		} catch (SystemException e) {
			_log.error("Error creating Repository.", e);
		}
		
		return repository;
	}

	@Override
	public Repository addRepository(Repository repository)
			throws SystemException {

		for (Repository repo : RepositoryLocalServiceUtil.getRepositories(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
			if (repo.getRepositoryName().equals(repository.getRepositoryName())) {
				return repo;
			}
    }

		return super.addRepository(repository);
	}

	public java.util.List<Repository> getRepositories(long groupId) throws SystemException {
		
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class)
			.add(PropertyFactoryUtil.forName("groupId").eq(groupId));
		
		@SuppressWarnings("unchecked")
    List<Repository> repositories = RepositoryLocalServiceUtil.dynamicQuery(dynamicQuery);

		return repositories;
	}
	
	public List<Repository> getRepositories(String className) throws SystemException {
		
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(className);
		
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class)
			  .add(PropertyFactoryUtil.forName("classNameId").eq(classNameId));
		
		@SuppressWarnings("unchecked")
    List<Repository> repositories = RepositoryLocalServiceUtil.dynamicQuery(dynamicQuery);

		return repositories;
	}
	
	@SuppressWarnings("unchecked")
	public List<RepositoryFolderPermission> getRepositoryPermissions(long repositoryId) {

		List<RepositoryFolderPermission> permissions = new ArrayList<>();
		
		List<RepositoryFolder> folders = new ArrayList<>();
		
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
				.add(PropertyFactoryUtil.forName("repositoryId").eq(repositoryId));
		
		try {
			folders = RepositoryFolderLocalServiceUtil.dynamicQuery(dynamicQuery);
		} catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolder.", e);
		}
		
		for (RepositoryFolder folder: folders) {
			dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryFolderPermission.class)
          .add(PropertyFactoryUtil.forName("folderId").eq(folder.getFolderId()));

			try {
				permissions.addAll(RepositoryFolderPermissionLocalServiceUtil.dynamicQuery(dynamicQuery));
			} catch (SystemException e) {
        _log.error("Error running DynamicQuery for RepositoryFolderPermission.", e);
			}
		}
		
		return permissions;	
	}
}
