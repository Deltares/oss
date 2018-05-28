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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.worth.deltares.subversion.exception.DuplicateRepositoryFolderPermissionException;
import com.worth.deltares.subversion.model.PermissionConstants;
import com.worth.deltares.subversion.model.RepositoryFolder;
import com.worth.deltares.subversion.model.RepositoryFolderPermission;
import com.worth.deltares.subversion.service.RepositoryFolderLocalServiceUtil;
import com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalServiceUtil;
import com.worth.deltares.subversion.service.base.RepositoryFolderPermissionLocalServiceBaseImpl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.DateUtil;


/**
 * The implementation of the repository folder permission local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermissionLocalServiceBaseImpl
 * @see com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalServiceUtil
 */
public class RepositoryFolderPermissionLocalServiceImpl
	extends RepositoryFolderPermissionLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.worth.deltares.subversion.service.RepositoryFolderPermissionLocalServiceUtil} to access the repository folder permission local service.
	 */

  private static Log _log = LogFactoryUtil.getLog(
    RepositoryFolderPermissionLocalServiceImpl.class);

  public RepositoryFolderPermission createRepositoryFolderPermission() {
  
    RepositoryFolderPermission permission = null;
    
    try {
      permission = RepositoryFolderPermissionLocalServiceUtil.createRepositoryFolderPermission(
          CounterLocalServiceUtil.increment(RepositoryFolderPermission.class.getName()));			
    } catch (SystemException e) {
      _log.error("Error creating RepositoryFolderPermission.", e);
    }
  
    permission.setCreateDate(DateUtil.newDate());
    permission.setModifiedDate(DateUtil.newDate());
  
    return permission;
  }
  
  public RepositoryFolderPermission createRepositoryFolderPermission(
      long repositoryId, String folderName, User user, String permission) {
  
    RepositoryFolder folder = RepositoryFolderLocalServiceUtil.addRepositoryFolder(
        repositoryId, folderName);
    
    return createRepositoryFolderPermission(folder, user, permission);
  }
  
  public RepositoryFolderPermission createRepositoryFolderPermission(
      long repositoryId, String folderName, Role role, String permission) {
  
    RepositoryFolder folder = RepositoryFolderLocalServiceUtil.addRepositoryFolder(
        repositoryId, folderName);
    
    return createRepositoryFolderPermission(folder, role, permission);
  }
  
  public RepositoryFolderPermission createRepositoryFolderPermission(
      RepositoryFolder folder, User user, String permission) {
    
    RepositoryFolderPermission folderPermission = createRepositoryFolderPermission();
    
    folderPermission.setFolderId(folder.getFolderId());
    folderPermission.setPermission(permission);
    folderPermission.setEntityType(PermissionConstants.TYPE_USER);
    folderPermission.setEntityId(user.getUserId());
    
    return folderPermission;
  }
  
  public RepositoryFolderPermission createRepositoryFolderPermission(
      RepositoryFolder folder, Role role, String permission) {
    
    RepositoryFolderPermission folderPermission = createRepositoryFolderPermission();
    
    folderPermission.setFolderId(folder.getFolderId());
    folderPermission.setPermission(permission);
    folderPermission.setEntityType(PermissionConstants.TYPE_ROLE);
    folderPermission.setEntityId(role.getRoleId());
    
    return folderPermission;
  }
  
  public RepositoryFolderPermission addRepositoryFolderPermission(
      RepositoryFolder folder, User user, String permission, boolean recurse) {
    
    try {
      RepositoryFolderPermission folderPermission = createRepositoryFolderPermission(
          folder, user, permission);
      
      return super.addRepositoryFolderPermission(folderPermission);
    
    } catch (SystemException e) {
      _log.warn("Could not add repository", e);
  
      return null;
    }
  }
  
  public RepositoryFolderPermission addRepositoryFolderPermission(
      RepositoryFolder folder, Role role, String permission, boolean recurse) {
    
    try {	
      RepositoryFolderPermission folderPermission = createRepositoryFolderPermission(
          folder, role, permission);
  
      return addRepositoryFolderPermission(folderPermission);
      
    } catch (SystemException e) {
      _log.warn("Could not add repository", e);
  
      return null;
    }
  }
  
  @Override
  public RepositoryFolderPermission addRepositoryFolderPermission(
      RepositoryFolderPermission permission)
      throws SystemException {
  
    try {		
      if (permission.getRecurse()) {
        RepositoryFolderLocalServiceUtil.deleteRepositoryFolders(
            permission.getFolder().getChildren());
      }
  
      return super.addRepositoryFolderPermission(permission);
    } catch (PortalException e) {
      _log.error("Failed removing a child! from: " + permission.getFolderId(), e);
    }
    
    return permission;
  }
  
  public List<RepositoryFolderPermission> addRepositoryFolderPermissions(
      RepositoryFolder folder, List<RepositoryFolderPermission> permissions,
      boolean recurse)
      throws Exception, SystemException  {
    
    Set<String> tmpSet = new HashSet<String>(); 
    
    for (RepositoryFolderPermission permission : permissions) {
      String permHash = permission.getEntityType() + Long.toString(
          permission.getEntityId());
  
      if (!tmpSet.add(permHash)) {
        throw new DuplicateRepositoryFolderPermissionException(
            "Duplicate permissions detected for " + permHash);
      }
    }
  
    // we validated our permissions, now we can delete old ones
    RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermissions(
        folder.getRepositoryId(), folder.getFolderId());
  
    // if we want recurse, delete all subfolders of this folder (including permissions)
    if (recurse) {
      RepositoryFolderLocalServiceUtil.deleteRepositoryFolders(
          RepositoryFolderLocalServiceUtil.getRepositoryFolderChildren(folder));
    }
    
    for(RepositoryFolderPermission permission : permissions) {
      RepositoryFolderPermissionLocalServiceUtil.addRepositoryFolderPermission(permission);
  
      if (recurse) {
        permission.setRecurse(true);
      }
    }
    
    return permissions;		
  }
  
  @SuppressWarnings("unchecked")
  public List<RepositoryFolderPermission> getRepositoryFolderPermissions(
      long repositoryId, long folderId) {
  
    List<RepositoryFolderPermission> permissions = new ArrayList<RepositoryFolderPermission>();
    
    DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryFolderPermission.class)
        .add(PropertyFactoryUtil.forName("folderId").eq(folderId));
    
    try {
      permissions = RepositoryFolderPermissionLocalServiceUtil.dynamicQuery(dynamicQuery);
    } catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolderPermission", e);
    }
    
    return permissions;	
  }
  
  public RepositoryFolderPermission getRepositoryFolderPermission(
      long repositoryId, long folderId, long roleId) {
  
    RepositoryFolderPermission permission = null;
    
    DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryFolderPermission.class)
        .add(PropertyFactoryUtil.forName("folderId").eq(folderId))
        .add(PropertyFactoryUtil.forName("roleId").eq(roleId));
    
    try {
      permission = (RepositoryFolderPermission) RepositoryFolderPermissionLocalServiceUtil
          .dynamicQuery(dynamicQuery).get(0);
    } catch (SystemException e) {
      _log.error("Error running DynamicQuery for RepositoryFolderPermission.", e);
    }
    
    return permission;
  }
  
  public void deleteRepositoryFolderPermissions(long repositoryId, long folderId) {
  
    List<RepositoryFolderPermission> permissions = RepositoryFolderPermissionLocalServiceUtil
        .getRepositoryFolderPermissions(repositoryId, folderId);
    
    for (RepositoryFolderPermission permission : permissions) {
      try {
        RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermission(
            permission.getPermissionId());
      } catch (PortalException | SystemException e) {
        _log.error("Error deleting RepositoryFolderPermission.", e);
      }
    }
  }
  
  public void deleteRepositoryFolderPermission(
      long repositoryId, long folderId, long roleId) {
  
    RepositoryFolderPermission permission = RepositoryFolderPermissionLocalServiceUtil
        .getRepositoryFolderPermission(repositoryId, folderId, roleId);
    
    try {
      RepositoryFolderPermissionLocalServiceUtil.deleteRepositoryFolderPermission(
          permission.getPermissionId());
    } catch (PortalException | SystemException e) {
      _log.error("Error deleting RepositoryFolderPermission.", e);
    }
  }
}
