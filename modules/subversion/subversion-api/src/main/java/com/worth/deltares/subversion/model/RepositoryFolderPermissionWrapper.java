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

package com.worth.deltares.subversion.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryFolderPermission}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderPermission
 * @generated
 */
@ProviderType
public class RepositoryFolderPermissionWrapper
	implements RepositoryFolderPermission,
		ModelWrapper<RepositoryFolderPermission> {
	public RepositoryFolderPermissionWrapper(
		RepositoryFolderPermission repositoryFolderPermission) {
		_repositoryFolderPermission = repositoryFolderPermission;
	}

	@Override
	public Class<?> getModelClass() {
		return RepositoryFolderPermission.class;
	}

	@Override
	public String getModelClassName() {
		return RepositoryFolderPermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("permissionId", getPermissionId());
		attributes.put("folderId", getFolderId());
		attributes.put("permission", getPermission());
		attributes.put("recurse", getRecurse());
		attributes.put("entityType", getEntityType());
		attributes.put("entityId", getEntityId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long permissionId = (Long)attributes.get("permissionId");

		if (permissionId != null) {
			setPermissionId(permissionId);
		}

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		String permission = (String)attributes.get("permission");

		if (permission != null) {
			setPermission(permission);
		}

		Boolean recurse = (Boolean)attributes.get("recurse");

		if (recurse != null) {
			setRecurse(recurse);
		}

		String entityType = (String)attributes.get("entityType");

		if (entityType != null) {
			setEntityType(entityType);
		}

		Long entityId = (Long)attributes.get("entityId");

		if (entityId != null) {
			setEntityId(entityId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RepositoryFolderPermissionWrapper((RepositoryFolderPermission)_repositoryFolderPermission.clone());
	}

	@Override
	public int compareTo(RepositoryFolderPermission repositoryFolderPermission) {
		return _repositoryFolderPermission.compareTo(repositoryFolderPermission);
	}

	/**
	* Returns the create date of this repository folder permission.
	*
	* @return the create date of this repository folder permission
	*/
	@Override
	public Date getCreateDate() {
		return _repositoryFolderPermission.getCreateDate();
	}

	/**
	* Returns the entity ID of this repository folder permission.
	*
	* @return the entity ID of this repository folder permission
	*/
	@Override
	public long getEntityId() {
		return _repositoryFolderPermission.getEntityId();
	}

	/**
	* Returns the entity type of this repository folder permission.
	*
	* @return the entity type of this repository folder permission
	*/
	@Override
	public java.lang.String getEntityType() {
		return _repositoryFolderPermission.getEntityType();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _repositoryFolderPermission.getExpandoBridge();
	}

	@Override
	public RepositoryFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermission.getFolder();
	}

	/**
	* Returns the folder ID of this repository folder permission.
	*
	* @return the folder ID of this repository folder permission
	*/
	@Override
	public long getFolderId() {
		return _repositoryFolderPermission.getFolderId();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermission.getGroup();
	}

	/**
	* Returns the modified date of this repository folder permission.
	*
	* @return the modified date of this repository folder permission
	*/
	@Override
	public Date getModifiedDate() {
		return _repositoryFolderPermission.getModifiedDate();
	}

	/**
	* Returns the permission of this repository folder permission.
	*
	* @return the permission of this repository folder permission
	*/
	@Override
	public java.lang.String getPermission() {
		return _repositoryFolderPermission.getPermission();
	}

	/**
	* Returns the permission ID of this repository folder permission.
	*
	* @return the permission ID of this repository folder permission
	*/
	@Override
	public long getPermissionId() {
		return _repositoryFolderPermission.getPermissionId();
	}

	/**
	* Returns the primary key of this repository folder permission.
	*
	* @return the primary key of this repository folder permission
	*/
	@Override
	public long getPrimaryKey() {
		return _repositoryFolderPermission.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _repositoryFolderPermission.getPrimaryKeyObj();
	}

	/**
	* Returns the recurse of this repository folder permission.
	*
	* @return the recurse of this repository folder permission
	*/
	@Override
	public boolean getRecurse() {
		return _repositoryFolderPermission.getRecurse();
	}

	@Override
	public Repository getRepository()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermission.getRepository();
	}

	@Override
	public com.liferay.portal.kernel.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermission.getRole();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryFolderPermission.getUser();
	}

	@Override
	public int hashCode() {
		return _repositoryFolderPermission.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _repositoryFolderPermission.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _repositoryFolderPermission.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _repositoryFolderPermission.isNew();
	}

	/**
	* Returns <code>true</code> if this repository folder permission is recurse.
	*
	* @return <code>true</code> if this repository folder permission is recurse; <code>false</code> otherwise
	*/
	@Override
	public boolean isRecurse() {
		return _repositoryFolderPermission.isRecurse();
	}

	@Override
	public void persist() {
		_repositoryFolderPermission.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_repositoryFolderPermission.setCachedModel(cachedModel);
	}

	/**
	* Sets the create date of this repository folder permission.
	*
	* @param createDate the create date of this repository folder permission
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_repositoryFolderPermission.setCreateDate(createDate);
	}

	/**
	* Sets the entity ID of this repository folder permission.
	*
	* @param entityId the entity ID of this repository folder permission
	*/
	@Override
	public void setEntityId(long entityId) {
		_repositoryFolderPermission.setEntityId(entityId);
	}

	/**
	* Sets the entity type of this repository folder permission.
	*
	* @param entityType the entity type of this repository folder permission
	*/
	@Override
	public void setEntityType(java.lang.String entityType) {
		_repositoryFolderPermission.setEntityType(entityType);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_repositoryFolderPermission.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_repositoryFolderPermission.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_repositoryFolderPermission.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the folder ID of this repository folder permission.
	*
	* @param folderId the folder ID of this repository folder permission
	*/
	@Override
	public void setFolderId(long folderId) {
		_repositoryFolderPermission.setFolderId(folderId);
	}

	/**
	* Sets the modified date of this repository folder permission.
	*
	* @param modifiedDate the modified date of this repository folder permission
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_repositoryFolderPermission.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_repositoryFolderPermission.setNew(n);
	}

	/**
	* Sets the permission of this repository folder permission.
	*
	* @param permission the permission of this repository folder permission
	*/
	@Override
	public void setPermission(java.lang.String permission) {
		_repositoryFolderPermission.setPermission(permission);
	}

	/**
	* Sets the permission ID of this repository folder permission.
	*
	* @param permissionId the permission ID of this repository folder permission
	*/
	@Override
	public void setPermissionId(long permissionId) {
		_repositoryFolderPermission.setPermissionId(permissionId);
	}

	/**
	* Sets the primary key of this repository folder permission.
	*
	* @param primaryKey the primary key of this repository folder permission
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_repositoryFolderPermission.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_repositoryFolderPermission.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this repository folder permission is recurse.
	*
	* @param recurse the recurse of this repository folder permission
	*/
	@Override
	public void setRecurse(boolean recurse) {
		_repositoryFolderPermission.setRecurse(recurse);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<RepositoryFolderPermission> toCacheModel() {
		return _repositoryFolderPermission.toCacheModel();
	}

	@Override
	public RepositoryFolderPermission toEscapedModel() {
		return new RepositoryFolderPermissionWrapper(_repositoryFolderPermission.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _repositoryFolderPermission.toString();
	}

	@Override
	public RepositoryFolderPermission toUnescapedModel() {
		return new RepositoryFolderPermissionWrapper(_repositoryFolderPermission.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _repositoryFolderPermission.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryFolderPermissionWrapper)) {
			return false;
		}

		RepositoryFolderPermissionWrapper repositoryFolderPermissionWrapper = (RepositoryFolderPermissionWrapper)obj;

		if (Objects.equals(_repositoryFolderPermission,
					repositoryFolderPermissionWrapper._repositoryFolderPermission)) {
			return true;
		}

		return false;
	}

	@Override
	public RepositoryFolderPermission getWrappedModel() {
		return _repositoryFolderPermission;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _repositoryFolderPermission.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _repositoryFolderPermission.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_repositoryFolderPermission.resetOriginalValues();
	}

	private final RepositoryFolderPermission _repositoryFolderPermission;
}