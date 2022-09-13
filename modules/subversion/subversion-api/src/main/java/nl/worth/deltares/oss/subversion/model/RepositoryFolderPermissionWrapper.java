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

package nl.worth.deltares.oss.subversion.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryFolderPermission}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryFolderPermission
 * @generated
 */
public class RepositoryFolderPermissionWrapper
	extends BaseModelWrapper<RepositoryFolderPermission>
	implements ModelWrapper<RepositoryFolderPermission>,
			   RepositoryFolderPermission {

	public RepositoryFolderPermissionWrapper(
		RepositoryFolderPermission repositoryFolderPermission) {

		super(repositoryFolderPermission);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("permissionId", getPermissionId());
		attributes.put("folderId", getFolderId());
		attributes.put("permission", getPermission());
		attributes.put("recurse", isRecurse());
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
	public RepositoryFolderPermission cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the create date of this repository folder permission.
	 *
	 * @return the create date of this repository folder permission
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entity ID of this repository folder permission.
	 *
	 * @return the entity ID of this repository folder permission
	 */
	@Override
	public long getEntityId() {
		return model.getEntityId();
	}

	/**
	 * Returns the entity type of this repository folder permission.
	 *
	 * @return the entity type of this repository folder permission
	 */
	@Override
	public String getEntityType() {
		return model.getEntityType();
	}

	@Override
	public nl.worth.deltares.oss.subversion.model.RepositoryFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			   com.liferay.portal.kernel.exception.SystemException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this repository folder permission.
	 *
	 * @return the folder ID of this repository folder permission
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			   com.liferay.portal.kernel.exception.SystemException {

		return model.getGroup();
	}

	/**
	 * Returns the modified date of this repository folder permission.
	 *
	 * @return the modified date of this repository folder permission
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the permission of this repository folder permission.
	 *
	 * @return the permission of this repository folder permission
	 */
	@Override
	public String getPermission() {
		return model.getPermission();
	}

	/**
	 * Returns the permission ID of this repository folder permission.
	 *
	 * @return the permission ID of this repository folder permission
	 */
	@Override
	public long getPermissionId() {
		return model.getPermissionId();
	}

	/**
	 * Returns the primary key of this repository folder permission.
	 *
	 * @return the primary key of this repository folder permission
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the recurse of this repository folder permission.
	 *
	 * @return the recurse of this repository folder permission
	 */
	@Override
	public boolean getRecurse() {
		return model.getRecurse();
	}

	@Override
	public nl.worth.deltares.oss.subversion.model.Repository getRepository()
		throws com.liferay.portal.kernel.exception.PortalException,
			   com.liferay.portal.kernel.exception.SystemException {

		return model.getRepository();
	}

	@Override
	public com.liferay.portal.kernel.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			   com.liferay.portal.kernel.exception.SystemException {

		return model.getRole();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			   com.liferay.portal.kernel.exception.SystemException {

		return model.getUser();
	}

	/**
	 * Returns <code>true</code> if this repository folder permission is recurse.
	 *
	 * @return <code>true</code> if this repository folder permission is recurse; <code>false</code> otherwise
	 */
	@Override
	public boolean isRecurse() {
		return model.isRecurse();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the create date of this repository folder permission.
	 *
	 * @param createDate the create date of this repository folder permission
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entity ID of this repository folder permission.
	 *
	 * @param entityId the entity ID of this repository folder permission
	 */
	@Override
	public void setEntityId(long entityId) {
		model.setEntityId(entityId);
	}

	/**
	 * Sets the entity type of this repository folder permission.
	 *
	 * @param entityType the entity type of this repository folder permission
	 */
	@Override
	public void setEntityType(String entityType) {
		model.setEntityType(entityType);
	}

	/**
	 * Sets the folder ID of this repository folder permission.
	 *
	 * @param folderId the folder ID of this repository folder permission
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the modified date of this repository folder permission.
	 *
	 * @param modifiedDate the modified date of this repository folder permission
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the permission of this repository folder permission.
	 *
	 * @param permission the permission of this repository folder permission
	 */
	@Override
	public void setPermission(String permission) {
		model.setPermission(permission);
	}

	/**
	 * Sets the permission ID of this repository folder permission.
	 *
	 * @param permissionId the permission ID of this repository folder permission
	 */
	@Override
	public void setPermissionId(long permissionId) {
		model.setPermissionId(permissionId);
	}

	/**
	 * Sets the primary key of this repository folder permission.
	 *
	 * @param primaryKey the primary key of this repository folder permission
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this repository folder permission is recurse.
	 *
	 * @param recurse the recurse of this repository folder permission
	 */
	@Override
	public void setRecurse(boolean recurse) {
		model.setRecurse(recurse);
	}

	@Override
	protected RepositoryFolderPermissionWrapper wrap(
		RepositoryFolderPermission repositoryFolderPermission) {

		return new RepositoryFolderPermissionWrapper(
			repositoryFolderPermission);
	}

}