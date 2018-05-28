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

import com.worth.deltares.subversion.model.RepositoryFolder;
import com.worth.deltares.subversion.service.RepositoryFolderLocalServiceUtil;
import com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import com.worth.deltares.subversion.service.base.RepositoryFolderLocalServiceBaseImpl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;


/**
 * The implementation of the repository folder local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.service.RepositoryFolderLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderLocalServiceBaseImpl
 * @see com.worth.deltares.subversion.service.RepositoryFolderLocalServiceUtil
 */
public class RepositoryFolderLocalServiceImpl
	extends RepositoryFolderLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.worth.deltares.subversion.service.RepositoryFolderLocalServiceUtil} to access the repository folder local service.
	 */

  private static Log _log = LogFactoryUtil.getLog(RepositoryFolderLocalServiceImpl.class);

  public RepositoryFolder createRepositoryFolder() {

    RepositoryFolder folder = null;

    try {
      folder = RepositoryFolderLocalServiceUtil.createRepositoryFolder(
          CounterLocalServiceUtil.increment(RepositoryFolder.class.getName()));
    } catch (SystemException e) {
      _log.error("Error creating RepositoryFolder.", e);
    }

    folder.setCreateDate(DateUtil.newDate());
    folder.setModifiedDate(DateUtil.newDate());

    return folder;
  }

  @Override
  public RepositoryFolder addRepositoryFolder(RepositoryFolder repositoryFolder)
      throws SystemException {

    RepositoryFolder folder = getRepositoryFolder(
        repositoryFolder.getRepositoryId(), repositoryFolder.getName());

    if (folder == null) {
      folder = super.addRepositoryFolder(repositoryFolder);
    }

    return folder;
  }

  public RepositoryFolder addRepositoryFolder(long repositoryId, String name) {

    RepositoryFolder folder = getRepositoryFolder(repositoryId, name);

    if (folder == null) {
      folder = createRepositoryFolder();
      folder.setName(name);
    }

    return folder;
  }

  public RepositoryFolder getRepositoryFolder(long repositoryId, String name) {

    RepositoryFolder folder = null;
    List<RepositoryFolder> folders;

    DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
        .add(PropertyFactoryUtil.forName("repositoryId").eq(repositoryId))
        .add(PropertyFactoryUtil.forName("name").eq(name));

    try {
      folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);

      if (!folders.isEmpty()) {
        folder = (RepositoryFolder) folders.get(0);
      }
    } catch (SystemException e) {
      _log.error("Error running RepositoryFolder DynamicQuery.", e);
    }

    return folder;
  }

  @Deprecated
  public List<RepositoryFolder> getRepositoryFolders(long repositoryId, String name) {

    List<RepositoryFolder> folders = new ArrayList<>();

    DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
        .add(PropertyFactoryUtil.forName("repositoryId").eq(repositoryId))
        .add(RestrictionsFactoryUtil.like("name", name + "/%"));

    try {
      folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
    } catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolders.", e);
    }

    return folders;
  }

  public List<RepositoryFolder> getRepositoryFolderChildren(RepositoryFolder folder) {

    List<RepositoryFolder> folders = new ArrayList<>();

    DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
        .add(PropertyFactoryUtil.forName("repositoryId").eq(folder.getRepositoryId()))
        .add(RestrictionsFactoryUtil.like("name", folder.getName() + "/%"));

    try {
      folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
    } catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolders.", e);
    }

    return folders;
  }

  @Override
  public RepositoryFolder deleteRepositoryFolder(RepositoryFolder folder)
      throws SystemException {

    RepositoryFolder deleted = null;

    RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermissions(
        folder.getRepositoryId(), folder.getFolderId());

    try {
      deleted = super.deleteRepositoryFolder(folder.getFolderId());
    } catch (PortalException e) {
      _log.error("Error deleting RepositoryFolder", e);
    }

    return deleted;
  }

  public List<RepositoryFolder> getRepositoryFolders(long repositoryId) {

    List<RepositoryFolder> folders = new ArrayList<>();

    DynamicQuery query = DynamicQueryFactoryUtil.forClass(RepositoryFolder.class)
        .add(PropertyFactoryUtil.forName("repositoryId").eq(repositoryId));

    try {
      folders = RepositoryFolderLocalServiceUtil.dynamicQuery(query);
    } catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolders.", e);
    }

    return folders;
  }

  public void deleteRepositoryFolders(List<RepositoryFolder> folders) {

    for (RepositoryFolder folder: folders) {
      try {
        RepositoryFolderLocalServiceUtil.deleteRepositoryFolder(folder);
      } catch (SystemException e) {
        _log.error("Error deleting RepositoryFolder.", e);
      }
    }
  }

  public RepositoryFolder getRepositoryFolderParent(long repositoryId, String folderName) {

    RepositoryFolder folder = null;
    String[] folders = folderName.split("/");
    String path = "/";

    for (String name : folders) {
      if (path.endsWith("/")) {
        path += name;
      } else {
        path += "/" + name;
      }

      if (getRepositoryFolder(repositoryId, path) != null) {
        folder = getRepositoryFolder(repositoryId, path);
      }
    }

    if (folder == null) {
      folder = getRepositoryFolder(repositoryId, "");
    }

    return folder;
  }
}
